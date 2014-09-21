package com.lanrenyou.letter.migrate;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang.StringUtils;

import com.google.gson.Gson;
import com.lanrenyou.user.model.UserInfo;

public class Migrate {
	
	public static String driver = "com.mysql.jdbc.Driver";

	UserInfo userInfo = new UserInfo();
	protected static final Gson gson = new Gson();
	
	public static void main(String[] args) {
		
//		importUsers();
//		 importTravel(exportPost());
		
	}
	
	public void importTravel(Map<Integer,WpPosts> postmap){
		for(Map.Entry<Integer, WpPosts> entry : postmap.entrySet()){
			 WpPosts post = entry.getValue();			 
			 insertPosts(post);
		 }
	}
	
	
	
	public Map<Integer,WpPosts> exportPost(){
		List<WpPosts> postslist = getPosts();
		
		splitOldPosts(postslist);
		Map<Integer,WpPosts> postsmap = filterposts(postslist,getPostmetas(postslist));
		return postsmap;
	}
	
	public void insertPosts(WpPosts post){
		Connection conn = getLryConn();
		ResultSet  rs = null;
		try{	
			
			String infosql = "insert into tb_travel_info (city,uid,title,is_elite,is_top,status,create_uid,create_time,create_ip,update_uid,update_time,update_ip) " +
					"values(?,?,?,?,?,?,?,?,?,?,?,?)";
			String contentsql = "insert into tb_travel_content (tid,travel_date,content,update_time) values (?,?,?,?)";
			String viewsql = "insert into tb_travel_visit_log (tid,uid,update_time) values (?,?,?) ";
			String statsql = "insert into tb_travel_info_stat (tid,view_cnt,like_cnt,update_time) values (?,?,?,?)";
			
			PreparedStatement infop =  conn.prepareStatement(infosql,Statement.RETURN_GENERATED_KEYS);
			infop.setString(1, convertCity(getTermByPostid(post.getId())));
			infop.setInt(2, post.getPost_author_id_new());
			String title = post.getPost_title();
			title = title.replace("&quot;", "\"").replace("&amp;", "&");
			infop.setString(3, title);
			infop.setInt(4, 0);
			infop.setInt(5, 0);
			infop.setInt(6, 2);
			infop.setInt(7,  post.getPost_author_id_new());
			infop.setTimestamp(8, post.getPost_date());
			infop.setString(9, "0.0.0.0");
			infop.setInt(10,  post.getPost_author_id_new());
			infop.setTimestamp(11, post.getPost_modified());
			infop.setString(12, "0.0.0.0");
			
			infop.executeUpdate();
											
			rs = infop.getGeneratedKeys();
			int id = 0;
			if(rs!=null&&rs.next()){
				id = rs.getInt(1);
			}
			infop.close();
			
			PreparedStatement contentp = conn.prepareStatement(contentsql);
			contentp.setInt(1, id);
			contentp.setTimestamp(2, post.getPost_date());			
			contentp.setString(3, filterHtmlTag(convertListToJson(post)));
			contentp.setTimestamp(4,post.getPost_modified());
			
			contentp.executeUpdate();
			contentp.close();
			
			PreparedStatement viewp = conn.prepareStatement(viewsql);
			viewp.setInt(1, id);
			viewp.setInt(2, 0);	
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
			viewp.setString(3, df.format(new Date()));
			viewp.executeUpdate();
			viewp.close();
			
			PreparedStatement statp = conn.prepareStatement(statsql);
			statp.setInt(1, id);
			statp.setInt(2, post.getPost_views_count());				
			statp.setInt(3, 0);
			statp.setString(4, df.format(new Date()));
			
			statp.executeUpdate();
			statp.close();
				
			conn.close();
		}catch(Exception e){
				e.printStackTrace();
		}	
		
	}
		
	public static String convertListToJson(WpPosts post){
		List<WpPosts> list = post.getChildlists();
		List<Map> clist = new ArrayList<Map>();
		for(WpPosts c:list){
			Map<String,String> map = new HashMap<String, String>();
			String img = c.getPost_attc_url();
			if(StringUtils.isNotBlank(img)){
				if(img.contains("lanrenyou")){
					if(img.startsWith("http://lanrenyou.com")){
						img = img.substring(20);
					}
				}
				if(img.contains("wp-content")){
					if(!img.startsWith("/wp-content/uploads/default")){
						img = img.replaceAll("-\\d+x\\d+", "");
						img = img.substring(0, img.length() -4) + "_s.jpg";
						map.put("src", img);
					}
				}
			}else{
				img="";
			}
			map.put("src", img);
			map.put("info", c.getPost_content());
			clist.add(map);
		}
		
		return gson.toJson(clist);
		
	}
	
	public static String filterHtmlTag(String content){
//		System.out.println("转换前："+content);
		Pattern blankp = Pattern.compile("&nbsp;");		
		Pattern htmlp = Pattern.compile("<[^>]+>");
		Pattern rnp = Pattern.compile("\\r\\n");
		
		Matcher blankm = blankp.matcher(content);
		content = blankm.replaceAll("");
		
		Matcher htmlm = htmlp.matcher(content);
		content = htmlm.replaceAll("");
		
		Matcher rnm = rnp.matcher(content);
		content = rnm.replaceAll("");
		
		content = content.replace("&quot;", "\"");
		content = content.replace("&amp;", "&");
		
		//System.out.println("转换后 ："+content);
		
		return content;
		
	}
	
//	public static String decodeUnicode(String unicodeStr){		
//		try {
//			String s = new String(unicodeStr.getBytes("ISO8859-1"),"UTF-8");
//			return s;
//		} catch (UnsupportedEncodingException e) {			
//			e.printStackTrace();
//		}
//		return unicodeStr;
//	}
	
	public String convertGallery(String content){
		
			Map<String,String> idsmap = new HashMap<String, String>();
			Pattern gpattern = Pattern.compile("\\[gallery ids=\"[0-9]{1,5},[0-9]{1,5},[0-9]{1,5},[0-9]{1,5},[0-9]{1,5},[0-9]{1,5}\"\\]");
			Matcher gmatcher = gpattern.matcher(content);
			while(gmatcher.find()){
				String ids = gmatcher.group();			
				idsmap.put(ids, "");
			}
			
			for(Entry<String, String> entry:idsmap.entrySet()){
				String key = entry.getKey();
				
				String[] postids = key.replaceAll("\\[gallery ids=\"", "").replaceAll("\"\\]", "").split(",");
				
				String urls = "";
				for(int a=0;a<postids.length;a++){
					urls = urls+"<img src=\""+getWppostById(Integer.parseInt(postids[a])).getPost_attc_url()+"\" />  ";
				}
				
				content = content.replaceAll("\\"+key.substring(0,key.length()-1)+"\\]", urls);
				
			}	
			System.out.println(content);
		return content;
	}
	
	public void splitOldPosts(List<WpPosts> postslist){
		for(int i=0;i<postslist.size();i++){
			
			WpPosts po = postslist.get(i);
			if(po.getId()<=5704){//处理13年的游记
				List<WpPosts> clist = new ArrayList<WpPosts>();
				String content = po.getPost_content();
				//处理[gallery ids="64,65,66,67,68,69"]，替换为<img <img src="http://lanrenyou.com/wp-content/uploads/2013/10/图片130-300x168.jpg"/>
				if(content.indexOf("[gallery ids=")>0){
					convertGallery(content);
				}
				
				Pattern p = Pattern.compile("<img[^>]+/>");  
				Matcher m = p.matcher(content);
				List<String> imglist = new ArrayList<String>();
			
				Pattern p1 = Pattern.compile("http://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?");			

				while(m.find()){
					String s0 = m.group();
					Matcher m1 = p1.matcher(s0);
					String url = "";
					while(m1.find()){
						url = m1.group();						
					}
					
					imglist.add(url);
				}
				String s = m.replaceAll("-----#####------");

				String[] contents = s.split("-----#####------");			
				Object[] imgs = imglist.toArray();
				
				if(StringUtils.isEmpty(contents[0].trim())){

					for(int idx = 1;idx<contents.length;idx++){
						WpPosts cpost = new WpPosts();
						cpost.setPost_content(filterHtmlTag(contents[idx]));							
						cpost.setPost_attc_url(imgs[idx-1].toString());						
						clist.add(cpost);
					}					
				}else{
					for(int idx = 0;idx<contents.length;idx++){
						WpPosts cpost = new WpPosts();
						cpost.setPost_content(filterHtmlTag(contents[idx]));
						if(imgs.length>idx){
							cpost.setPost_attc_url(imgs[idx].toString());
						}						
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
			
			if(post.getPost_parent()>5704){
				map.get(post.getPost_parent()).getChildlists().add(post);
			}
		}
		
		return map;
	}
	
	public List<WpPosts> getPostmetas(List<WpPosts> postlist){
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
					"where 	p.post_parent in ( "+ids.toString().substring(0, ids.length()-1)+" ) and p.post_type='attachment' ";
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
				if(!StringUtils.isEmpty(rs.getString("meta_value"))){
					post.setPost_attc_url("/wp-content/uploads/"+rs.getString("meta_value"));	
				}
				
				metalist.add(post);
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return metalist;
		
	}
	
	public List<WpPosts> getPosts(){
		Connection conn = getPressConn();
		List<WpPosts> postslist = new ArrayList<WpPosts>();
		 Map<String,Integer> map = getUserIdMap();
		
		try{
			ResultSet rs = null;
			Statement stat = conn.createStatement();
			String postssql = "select b.user_login user_login,a.id id,a.post_date post_date,a.post_author post_author,a.post_content post_content,a.post_title post_title," +
					"a.post_excerpt post_excerpt,a.post_modified post_modified,a.post_parent post_parent,c.meta_value meta_value from wp_posts a inner join wp_users b on " +
					"a.post_author=b.id  left join  (select * from wp_postmeta where meta_key='post_views_count')  c on a.id=c.post_id where a.post_status='publish' and post_type='post' ";
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
	
	public WpPosts getWppostById(int id){
		Connection conn = getPressConn();
		WpPosts post = new WpPosts();		
		try{
			ResultSet rs = null;
			Statement stat = conn.createStatement();
			String postssql = "select * from wp_posts  where id="+id ;
			
			rs = stat.executeQuery(postssql);
			while(rs.next()){
				
				post.setPost_attc_url(rs.getString("guid"));
				
			}
			rs.close();
			conn.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return post;
	}
	
	public int getTermByPostid(int id){
		Connection conn = getPressConn();
		int termid  = 1;	
		try{
			ResultSet rs = null;
			Statement stat = conn.createStatement();
			String sql = "select term_taxonomy_id from wp_term_relationships  where object_id="+id ;
			
			rs = stat.executeQuery(sql);
			while(rs.next()){				
				termid = rs.getInt("term_taxonomy_id")	;
			}
			rs.close();
			conn.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return termid;
	}
	
	public static String convertCity(int termid){
		String cityid = "其他,Other";
		if(termid==1){
			cityid = "其他,Other";
		}
		if(termid==2){
			cityid = "其他,Other";
		}
		if(termid==3){
			cityid = "圣地亚哥,San Diego";
		}
		if(termid==5){
			cityid = "洛杉矶,LA";
		}
		if(termid==6){
			cityid = "洛杉矶,LA";
		}
		if(termid==7){
			cityid = "其他,Other";
		}
		if(termid==8){
			cityid = "其他,Other";
		}
		if(termid==9){
			cityid = "芝加哥,Chicago";
		}
		if(termid==10){
			cityid = "其他,Other";
		}
		if(termid==11){
			cityid = "其他,Other";
		}
		if(termid==12){
			cityid = "其他,Other";
		}
		if(termid==13){
			cityid = "其他,Other";
		}
		if(termid==14){
			cityid = "其他,Other";
		}
		if(termid==16){
			cityid = "芝加哥,Chicago";
		}
		if(termid==17){
			cityid = "纽约,NYC";
		}
		if(termid==18){
			cityid = "洛杉矶,LA";
		}
		if(termid==19){
			cityid = "旧金山,San Francisco";
		}
		if(termid==20){
			cityid = "美国国家公园,National Parks";
		}
		if(termid==21){
			cityid = "西雅图,Seattle";
		}
		if(termid==22){
			cityid = "阿拉斯加,Alaska";
		}
		if(termid==23){
			cityid = "游轮旅行,Cruise";
		}
		if(termid==25){
			cityid = "奥兰多,Orlando";
		}
		if(termid==26){
			cityid = "佛罗里达,Florida";
		}
		if(termid==27){
			cityid = "风情小镇,Small Towns";
		}
		if(termid==28){
			cityid = "加州湾区,Bay Area";
		}
		if(termid==29){
			cityid = "其他,Other";
		}
		if(termid==30){
			cityid = "波士顿,Boston";
		}
		if(termid==31){
			cityid = "加州一号公路,Pacific Coast Highway";
		}
		if(termid==32){
			cityid = "波多黎各,Puerto Rico";
		}
		if(termid==33){
			cityid = "圣地亚哥,San Diego";
		}
		
		return cityid;
		
	}
	
	
	
	public void importUsers(){
		List<WpUsers> userlist = getWpUsers();
		userlist = getWpUsermeta(userlist);
		
		importUserInfos(userlist);
		importUserPlanners(userlist,getUserIdMap());
	}
	
	public void importUserPlanners(List<WpUsers> userlist,Map<String,Integer> map){
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
					stat.setInt(5,2);
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
	
	public void importUserInfos(List<WpUsers> userlist){
	
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
					stat.setInt(11, 3);
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
	
	public Map<String,Integer> getUserIdMap(){
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
	
	public  List<WpUsers> getWpUsers(){
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
				String avator = rs.getString("display_name");
				if(null != avator){
					if(avator.startsWith("http://lanrenyou.com")){
						user.setDisplay_name(avator.substring(20));
					}else{
						user.setDisplay_name(rs.getString("display_name"));
					}
				}
				
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
	
	public List<WpUsers> getWpUsermeta(List<WpUsers> userlist){
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
		
	public Connection getPressConn(){
		String pressurl = "jdbc:mysql://127.0.0.1:3306/db_lanrenyou";
		String pressuser = "root";
		String presspassword = "v7DKsuV3yWTh3CTw";
		Connection conn  = null;
		try{
			Class.forName(driver);
			conn = DriverManager.getConnection(pressurl,pressuser,presspassword);
		}catch(Exception e){
			e.printStackTrace();
		}
		return conn;
	}
	
	public Connection getLryConn(){
		String lryurl = "jdbc:mysql://127.0.0.1:3306/db_lry?autoCommit=true&autoReconnect=true&useUnicode=true&tinyInt1isBit=false&zeroDateTimeBehavior=round&characterEncoding=UTF-8&yearIsDateType=false";
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
	
}

