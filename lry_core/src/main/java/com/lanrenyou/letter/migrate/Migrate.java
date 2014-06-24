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

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Results;

import com.lanrenyou.user.model.UserInfo;

public class Migrate {
	
	public static String driver = "com.mysql.jdbc.Driver";

	UserInfo userInfo = new UserInfo();
	
	public static void main(String[] args) {
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
				String insertuserinfo = "insert into tb_user_planner (uid,target_city,price,charge_mode,user_intro,status,create_uid,create_time," +
						"create_ip,update_uid,update_time,update_ip) " +
						" values(?,?,?,?,?,?,?,?,?,?,?,?)";
				
					PreparedStatement stat = conn.prepareStatement(insertuserinfo);
					stat.setInt(1, map.get(user.getUser_login()));
					stat.setString(2,user.getAreas());
					stat.setBigDecimal(3, new BigDecimal(0.00));
					stat.setInt(4,3);
					stat.setString(5,user.getDescription());
					stat.setInt(6,1);
					stat.setInt(7,0);
					stat.setTimestamp(8,user.getUser_registered() );
					stat.setString(9,"127.0.0.1");
					stat.setInt(10,0 );
					stat.setTimestamp(11,user.getUser_registered());
					stat.setString(12,"127.0.0.1");
										
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

