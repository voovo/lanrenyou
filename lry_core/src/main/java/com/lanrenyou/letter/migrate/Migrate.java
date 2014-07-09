package com.lanrenyou.letter.migrate;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.lanrenyou.user.model.UserInfo;

public class Migrate {
	
	public static String driver = "com.mysql.jdbc.Driver";

	UserInfo userInfo = new UserInfo();
	
	public static void main(String[] args) {
		//migrate users
		//importUsers();
		
//		Map<Integer,WpPosts> a = filterposts(getPosts(),getPostmetas(getPosts()));
//		
//		WpPosts po = a.get(6440);		
//		System.out.println("id:"+po.getId()+" content:"+po.getPost_content()+" title:"+po.getPost_title()+" autohr:"+po.getPost_author_name()+" author-new-id:"+po.getPost_author_id_new()+" viewcount:"+po.getPost_views_count());
//		
//		List<WpPosts> list = po.getChildlists();
//		if(list!=null&&list.size()>0){
//			for(int i=0;i<list.size();i++){
//				WpPosts t = list.get(i);
//				System.out.println("child-id:"+t.getId()+" child-content:"+t.getPost_content()+" child-title:"+t.getPost_title()+" child-impurl:"+t.getPost_attc_url());					
//			}			
//		}		
//		System.out.println();
		
		
		
		splitOldPosts(getPosts());
		
	}
	
	public static void splitOldPosts(List<WpPosts> postslist){
		for(int i=0;i<postslist.size();i++){
			
			WpPosts po = postslist.get(i);
			if(po.getId()<=5704){//处理13年的游记
				List<WpPosts> clist = new ArrayList<WpPosts>();
				String content = po.getPost_content();
				Pattern p = Pattern.compile("<img[^>]+/>");  
				Matcher m = p.matcher(content);
				List<String> imglist = new ArrayList<String>();

				while(m.find()){
					String s0 = m.group();
					imglist.add(s0);
				}
				String s = m.replaceAll("--------");

				String[] contents = s.split("--------");			
				Object[] imgs = imglist.toArray();
				
				if(contents.length>imgs.length){
					for(int idx = 0;idx<imgs.length;idx++){
						WpPosts cpost = new WpPosts();
						cpost.setPost_content(contents[idx+1]);
						cpost.setPost_attc_url(imgs[idx].toString());
						clist.add(cpost);
					
					}
				}else{
					for(int idx = 0;idx<contents.length;idx++){
						WpPosts cpost = new WpPosts();
						cpost.setPost_content(contents[idx]);
						cpost.setPost_attc_url(imgs[idx].toString());
						clist.add(cpost);
					}
				}
				po.setChildlists(clist);
				
			}
		}
	}
	
	public static Map<Integer,WpPosts> filterposts(List<WpPosts> postslist,List<WpPosts> metalist){
		Map<Integer,WpPosts> map = new HashMap<Integer, WpPosts>();
		
		for(int i=0;i<postslist.size();i++){
			WpPosts post = postslist.get(i);
			if(!map.containsKey(post.getId())){
				map.put(post.getId(), post);
			}
		}
		
		for(int i=0;i<metalist.size();i++){
			WpPosts post = metalist.get(i);
			
			if(post.getPost_parent()>0){
				map.get(post.getPost_parent()).getChildlists().add(post);
			}
		}
		
		return map;
	}
	
	public static List<WpPosts> getPostmetas(List<WpPosts> postlist){
		StringBuffer ids = new StringBuffer();
		for (int i=0;i<postlist.size();i++){
			ids.append(postlist.get(i).getId()).append(",");
		}
		
		List<WpPosts> metalist = new ArrayList<WpPosts>();
		 Map<String,Integer> map = getUserIdMap();
		try{
			Connection conn = getPressConn();
			ResultSet rs = null;
			Statement stat = conn.createStatement();
			String metasql = "	select p.id id,p.post_parent post_parent,p.post_date post_date,p.post_author post_author,p.post_content post_content,p.post_title post_title" +
					",p.post_excerpt post_excerpt,p.post_modified post_modified,p.post_parent post_parent,f.meta_value meta_value from 	wp_posts  p left join (select * from wp_postmeta where meta_key='_wp_attached_file')  f on p.id=f.post_id " +
					"where 	p.post_parent in ( "+ids.toString().substring(0, ids.length()-1)+" )";
			rs = stat.executeQuery(metasql);
			while(rs.next()){
				WpPosts post = new WpPosts();
				post.setId(rs.getInt("id"));
				post.setPost_author(rs.getInt("post_author"));
				post.setPost_content(rs.getString("post_content"));
				post.setPost_excerpt(rs.getString("post_excerpt"));
				post.setPost_title(rs.getString("post_title"));
				post.setPost_date(rs.getTimestamp("post_date"));
				post.setPost_modified(rs.getTimestamp("post_modified"));
				post.setPost_parent(rs.getInt("post_parent"));
				post.setPost_attc_url(rs.getString("meta_value"));	
				metalist.add(post);
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return metalist;
		
	}
	
	public static List<WpPosts> getPosts(){
		Connection conn = getPressConn();
		List<WpPosts> postslist = new ArrayList<WpPosts>();
		 Map<String,Integer> map = getUserIdMap();
		
		try{
			ResultSet rs = null;
			Statement stat = conn.createStatement();
			String postssql = "select b.user_login user_login,a.id id,a.post_date post_date,a.post_author post_author,a.post_content post_content,a.post_title post_title," +
					"a.post_excerpt post_excerpt,a.post_modified post_modified,a.post_parent post_parent,c.meta_value meta_value from wp_posts a inner join wp_users b on " +
					"a.post_author=b.id  left join  (select * from wp_postmeta where meta_key='post_views_count')  c on a.id=c.post_id where a.post_status='publish'  ";
			rs = stat.executeQuery(postssql);
			while(rs.next()){
				WpPosts post = new WpPosts();
				post.setId(rs.getInt("id"));
				post.setPost_author(rs.getInt("post_author"));
				post.setPost_author_name(rs.getString("user_login"));
				post.setPost_author_id_new(map.get(rs.getString("user_login")));
				post.setPost_content(rs.getString("post_content"));
				post.setPost_excerpt(rs.getString("post_excerpt"));
				post.setPost_title(rs.getString("post_title"));
				post.setPost_date(rs.getTimestamp("post_date"));
				post.setPost_modified(rs.getTimestamp("post_modified"));
				post.setPost_parent(rs.getInt("post_parent"));
				post.setPost_views_count(rs.getInt("meta_value"));
				
				postslist.add(post);
				
			}
			rs.close();
			conn.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return postslist;
	}
	
	
	
	
	
	
	public static void importUsers(){
		List<WpUsers> userlist = getWpUsers();
		userlist = getWpUsermeta(userlist);
		
		importUserInfos(userlist);
		importUserPlanners(userlist,getUserIdMap());
	}
	
	public static void importUserPlanners(List<WpUsers> userlist,Map<String,Integer> map){
		Connection conn = getLryConn();
		try{	
			for(int i=0;i<userlist.size();i++){
				WpUsers user = userlist.get(i);
				String insertuserinfo = "insert into tb_user_planner (uid,target_city,price,charge_mode,status,create_uid,create_time," +
						"create_ip,update_uid,update_time,update_ip) " +
						" values(?,?,?,?,?,?,?,?,?,?,?)";
				
					PreparedStatement stat = conn.prepareStatement(insertuserinfo);
					stat.setInt(1, map.get(user.getUser_login()));
					stat.setString(2,user.getAreas());
					stat.setBigDecimal(3, new BigDecimal(0.00));
					stat.setInt(4,3);
					stat.setInt(5,1);
					stat.setInt(6,0);
					stat.setTimestamp(7,user.getUser_registered() );
					stat.setString(8,"127.0.0.1");
					stat.setInt(9,0 );
					stat.setTimestamp(10,user.getUser_registered());
					stat.setString(11,"127.0.0.1");
										
					stat.executeUpdate();					
					stat.close();
					
				}
			conn.close();
		}catch(Exception e){
				e.printStackTrace();
		}	
	}
	
	public static void importUserInfos(List<WpUsers> userlist){
	
		Connection conn = getLryConn();
		try{	
			for(int i=0;i<userlist.size();i++){
				WpUsers user = userlist.get(i);
				String insertuserinfo = "insert into tb_user_info (name,email,user_pass,history_passwd,avatar,nickname,user_type," +
						"weibo_name,weibo_url,wechat_name,status,create_uid,create_time,create_ip,update_uid,update_time,update_ip,user_intro) " +
						" values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				
					PreparedStatement stat = conn.prepareStatement(insertuserinfo);
					stat.setString(1, user.getUser_login());
					stat.setString(2,user.getUser_email() );
					stat.setString(3,user.getUser_pass() );
					stat.setString(4,user.getUser_pass() );
					stat.setString(5,user.getAvatar());
					stat.setString(6,user.getDisplay_name() );
					stat.setInt(7, 1);
					stat.setString(8,"");
					stat.setString(9,"" );
					stat.setString(10,"" );
					stat.setInt(11, 1);
					stat.setInt(12,0);
					stat.setTimestamp(13,user.getUser_registered());
					stat.setString(14,"127.0.0.1" );
					stat.setInt(15,0 );
					stat.setTimestamp(16,user.getUser_registered());
					stat.setString(17,"127.0.0.1");
					stat.setString(18, user.getDescription());						
					stat.executeUpdate();					
					stat.close();
					
				}
			conn.close();
		}catch(Exception e){
				e.printStackTrace();
		}	
	}
	
	public static Map<String,Integer> getUserIdMap(){
		Map<String,Integer> map = new HashMap<String,Integer>();
		
		try{
			Connection conn = getLryConn();
			Statement stat = conn.createStatement();
			ResultSet rs = null;
			String sql = "select id,name from tb_user_info";
			rs = stat.executeQuery(sql);
			while(rs.next()){
				map.put(rs.getString("name"),rs.getInt("id"));
			}
			rs.close();
			conn.close();			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		return map;
	}
	
	public static List<WpUsers> getWpUsers(){
		Connection conn = null;
		ResultSet rs = null;
		List<WpUsers> userlist = new ArrayList<WpUsers>();
		try{			
			conn = getPressConn();
			Statement statement = conn.createStatement();

			String sql = "select * from wp_users";
			rs = statement.executeQuery(sql);	
			while (rs.next()){
				WpUsers user = new WpUsers();
				user.setId(rs.getInt("ID"));
				String user_login = rs.getString("user_login");
				user.setUser_login(user_login);
				user.setUser_pass(rs.getString("user_pass"));
				user.setUser_nicename(rs.getString("user_nicename"));
				user.setUser_email(rs.getString("user_email"));
				user.setUser_url(rs.getString("user_url"));
				user.setUser_registered(rs.getTimestamp("user_registered"));
				user.setUser_activation_key(rs.getString("user_activation_key"));
				user.setUser_status(rs.getInt("user_status"));
				user.setDisplay_name(rs.getString("display_name"));
				
				userlist.add(user);				
			}
			System.out.println(userlist.size());
			rs.close();
			conn.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return userlist;
	}
	
	public static List<WpUsers> getWpUsermeta(List<WpUsers> userlist){
		Connection conn = getPressConn();
		ResultSet rs = null;
		try{
			Statement statement = conn.createStatement();
			for(int i=0;i<userlist.size();i++){
				WpUsers user = userlist.get(i);
				String sql = "select * from wp_usermeta where user_id="+user.getId()+";";
				rs = statement.executeQuery(sql);
				while(rs.next()){
					if("nickname".equals(rs.getString("meta_key"))){
						user.setNickname(rs.getString("meta_value"));
					}
					if("description".equals(rs.getString("meta_key"))){
						user.setDescription(rs.getString("meta_value"));
					}
					if("areas".equals(rs.getString("meta_key"))){
						user.setAreas(rs.getString("meta_value"));
					}
					if("avatar".equals(rs.getString("meta_key"))){
						user.setAvatar(rs.getString("meta_value"));
					}
				}
				rs.close();
			}
			conn.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		for(int i=0;i<userlist.size();i++){
			System.out.println(userlist.get(i).getAreas());
			System.out.println(userlist.get(i).getDescription());
			System.out.println(userlist.get(i).getNickname());
			System.out.println(userlist.get(i).getAvatar());
			
		}
		
		return userlist;
	}
		
	public static Connection getPressConn(){
		String pressurl = "jdbc:mysql://127.0.0.1:3306/db_wordpress";
		String pressuser = "lanrenyou";
		String presspassword = "111111";
		Connection conn  = null;
		try{
			Class.forName(driver);
			conn = DriverManager.getConnection(pressurl,pressuser,presspassword);
		}catch(Exception e){
			e.printStackTrace();
		}
		return conn;
	}
	
	public static Connection getLryConn(){
		String lryurl = "jdbc:mysql://127.0.0.1:3306/DB_LRY";
		String lryuser = "lanrenyou";
		String lrypassword = "111111";
		Connection conn  = null;
		try{
			Class.forName(driver);
			conn = DriverManager.getConnection(lryurl,lryuser,lrypassword);
		}catch(Exception e){
			e.printStackTrace();
		}
		return conn;
	}
	
//	private static String areaCodeTrans(String area){
//		if(StringUtils.isEmpty(area)){
//			return "";
//		}
//		String[] areas  =  area.split(" ");
//		String areacodes = "";
//		for(int i=0;i<areas.length;i++){
//			
//			if("纽约NYC及周边".equals(area)){
//				areacodes = areacodes+"1001"+" ";
//			}
//			if("洛杉矶LA及周边".equals(area)){
//				areacodes = areacodes+"1002"+" ";
//			}
//			if("旧金山San Francisco及周边".equals(area)){
//				areacodes = areacodes+"1003"+" ";
//			}
//			if("".equals(area)){
//				areacodes = areacodes+""+" ";
//			}
//			if("".equals(area)){
//				areacodes = areacodes+""+" ";
//			}
//			if("".equals(area)){
//				areacodes = areacodes+""+" ";
//			}
//			if("".equals(area)){
//				areacodes = areacodes+""+" ";
//			}
//			
//			
//		}
//		
//		
//		
//		return "";
//		
//	}
	
}

