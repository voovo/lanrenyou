//package com.baidu.job.search.index.util;
//
//
//import java.sql.Timestamp;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.Map;
//
//import com.baidu.job.search.index.util.vo.School;
//import com.baidu.job.search.index.util.vo.Type;
//
//
//
///**
// * 展示简历信息转码使用 
// * @author hwz
// *
// */
//public class ResumeShowUtil {
//	
//	/**
//	 * 获得性别
//	 * @return
//	 */
//	public static String getGender(String gender){
//		if(!DateUtil.isNullOrNullStr(gender)){
//			return ResumeConstant.genderType.get(new Integer(gender));
//		}
//			return "";
//	}
//	
//	/**
//	 *时间格式化
//	 *出生日期
//	 */
//	public static String getBirthday(Timestamp time){
//		if(time!=null){
//			return DateUtil.dateToStrYYYY_MM_DD(time);
//		}
//			return "";
//	}
//
//	/**
//	 * 获得证件类型
//	 * @param document_type
//	 * @return
//	 */
//	public static String getDocument_type(String document_type) {
//	if(!DateUtil.isNullOrNullStr(document_type)){
//		return ResumeConstant.documentsType.get(new Integer(document_type));
//	}
//		return "";
//	}
//
//	/**
//	 * 获得民族
//	 * @param nation
//	 * @return
//	 */
//	public static String getNation(String nation) {
//		if(!DateUtil.isNullOrNullStr(nation)){
//			return ResumeConstant.nation.get(new Integer(nation));
//		}
//			return "";
//	}
//
//	/**
//	 * 婚姻状况
//	 * @param is_married
//	 * @return
//	 */
//	public static String getIs_married(String is_married) {
//		if(!DateUtil.isNullOrNullStr(is_married)){
//			return ResumeConstant.marriedType.get(new Integer(is_married));
//		}
//			return "";
//	}
//
//	/**
//	 * 政治面貌
//	 * @param politics_status
//	 * @return
//	 */
//	public static String getPolitics_status(String politics_status) {
//          if(!DateUtil.isNullOrNullStr(politics_status)){
//        	  return ResumeConstant.politics_status.get(new Integer(politics_status));
//          }
//        	  return "";
//	}
//
//	/**
//	 * 最高学历
//	 * @param highest_degree
//	 * @return
//	 */
//	public static String getHighest_degree(String highest_degree) {
//		if(!DateUtil.isNullOrNullStr(highest_degree)){
//      	  return ResumeConstant.academic.get(new Integer(highest_degree));
//        }
//      	  return "";
//	}
//
//	/**
//	 * 所在城市
//	 * @param current_city
//	 * @return
//	 */
//	public static String getCurrent_city(String current_city) {
//		if(!DateUtil.isNullOrNullStr(current_city)){
//			Type type= AddressFactory.getInstance().getTypeById(current_city);
//			if(type!=null){
//				 return type.getName();
//			}
//	        }
//		return "";
//	}
//
//	/**
//	 * 是否接受短信
//	 * @param is_receive_msg
//	 * @return
//	 */
//	public static String getIs_receive_msg(String is_receive_msg) {
//		if(!DateUtil.isNullOrNullStr(is_receive_msg)){
//	      	  return ResumeConstant.yesOrNo.get(new Integer(is_receive_msg));
//	        }
//		return "";
//	}
//
//	/**
//	 * 其他联系方式
//	 * @param other_type
//	 * @return
//	 */
//	public static String getOther_type(String other_type) {
//		if(!DateUtil.isNullOrNullStr(other_type)){
//	      	  return ResumeConstant.other_type.get(new Integer(other_type));
//	        }
//		return "";
//	}
//	
//	
//	
//	
//	/**
//	 * 获得自定义项的值
//	 * @param itemkey 表名__字段名
//	 * @param itemkey 表名__字段名
//	 * @param itemMap 所有自定义项并且需要转码的字段
//	 * @return
//	 */
////	public static String getFreeValue(String itemkey,String dateCode ,Map<String, CrAppformItemVo> itemMap){
////		if(itemMap!=null){
////			CrAppformItemVo vo = itemMap.get(itemkey);
////			if(vo!=null){
////				//获得数据字典
////				Map map = RmGlobalReference.get(vo.getCode());
////				
////				//多选框
////				if("4".equals(vo.getItem_data_type())){
////					String[] dateCodes = dateCode.split(",");
////					StringBuffer sb = new StringBuffer("");
////					for(String code:dateCodes){
////						sb.append((String)map.get(code)==null?"":(String)map.get(code)+"+");
////					}
////					String value = sb.toString();
////					if(!DateUtil.isNullOrNullStr(value)){
////						value = value.substring(0, value.length()-1);
////					}
////					return value;
////					
////				}else{//下拉框 单选框 ......
////					//返回转义的字符
////					return (String)map.get(dateCode)==null?"":(String)map.get(dateCode);
////				}
////			}
////		}
////		//直接返回要转义的编码
////		return dateCode;
////		
////	}
//
//	/**
//	 * 获得学校
//	 * @param school
//	 * @param school_other 如果是其他的话 用户填的什么？
//	 * @return
//	 */
//	public static String getSchool(String school, String school_other) {
//		if(!DateUtil.isNullOrNullStr(school)){
//			School ss = SchoolFactory.getInstance().getSchoolById(school);
//			if(ss!=null){
//				 String schoolName = ss.getShortName();
//				 if(schoolName!=null&&schoolName.startsWith("其他")){
//					 return school_other;
//				 }else{
//					 return schoolName;
//				 }
//			}
//		
//	      	 
//	        }
//		return "";
//	}
//
//	/**
//	 * 获得专业
//	 * @param major
//	 * @param major_other
//	 * @return
//	 */
//	public static String getMajor(String major, String major_other) {
////		System.out.println("返回的专业;;;;;;;;;;;;;"+major);
//		if(!DateUtil.isNullOrNullStr(major)){
//			major = major.replaceAll("\\[", "").replaceAll("\\]", "");
//			Type ss = MajorTypeFactory.getInstance().getTypeById(major);
//			if(ss!=null){
//				 String majorName = ss.getName();
//				 if(majorName.startsWith("其他")){
//					 return major_other;
//				 }else{
//					 return majorName;
//				 }
//			}
//	        }
//		return "";
//	}
//
//	
//	/**
//	 * 获得专业类别和专业
//	 * @param major
//	 * @param major_other
//	 * @return
//	 */
//	public static String getMajorType(String major) {
//		if(!DateUtil.isNullOrNullStr(major)){
//			major = major.replaceAll("\\[", "").replaceAll("\\]", "");
//			Type ss = MajorTypeFactory.getInstance().getTypeById(major);
//			if(ss!=null){
//				if(ss.getParent()!=null){
//					 return ss.getParent().getName()==null?"":ss.getParent().getName();
//				 }else{
//					 return "";
//				 }
//			}
//	        }
//		return "";
//	}
//	/**
//	 * 获得专业类别 和专业
//	 * @return
//	 */
//	public static String getMajorAndType(String major, String major_other){
//		String majorType = ResumeShowUtil.getMajorType(major);
//		String majorvalue = ResumeShowUtil.getMajor(major, major_other);
//		if(!DateUtil.isNullOrNullStr(majorType)&&!DateUtil.isNullOrNullStr(majorvalue)){
//			return majorType+"/"+majorvalue;
//		}
//		if(!DateUtil.isNullOrNullStr(majorType)){
//			return majorType;
//		}
//		if(!DateUtil.isNullOrNullStr(majorvalue)){
//			return majorvalue;
//		}
//		return "";
//		
//		
//	}
//	
//	/**
//	 * 获得学历
//	 * @param education
//	 * @return
//	 */
//	public static String getEducation(String education) {
//		if(!DateUtil.isNullOrNullStr(education)){
//	      	  return ResumeConstant.academic.get(new Integer(education));
//	        }
//		return "";
//	}
//
//	/**
//	 * 获得学位
//	 * @param degree
//	 * @return
//	 */
//	public static String getDegree(String degree) {
//		if(!DateUtil.isNullOrNullStr(degree)){
//	      	  return ResumeConstant.degree.get(new Integer(degree));
//	        }
//		return "";
//	}
//
//	/**
//	 * 获得年纪排名
//	 * @param rank
//	 * @return
//	 */
//	public static String getRank(String rank) {
//		if(!DateUtil.isNullOrNullStr(rank)){
//	      	  return ResumeConstant.grade_order.get(new Integer(rank));
//	        }
//		return "";
//	}
//
//	/**
//	 * 海外学习经历
//	 * @param is_oversea
//	 * @return
//	 */
//	public static String getIs_oversea(String is_oversea) {
//		if(!DateUtil.isNullOrNullStr(is_oversea)){
//	      	  return ResumeConstant.is_oversea.get(new Integer(is_oversea));
//	        }
//		return "";
//	}
//
//	/**
//	 * 英语等级
//	 * @param eng_level
//	 * @return
//	 */
//	public static String getEng_level(String eng_level) {
//		if(!DateUtil.isNullOrNullStr(eng_level)){
//	      	  return ResumeConstant.eng_level.get(new Integer(eng_level));
//	        }
//		return "";
//	}
//
//	/**
//	 * 外语语种
//	 * @param forein_lan1
//	 * @return
//	 */
//	public static String getForein_lan(String forein_lan1) {
//		if(!DateUtil.isNullOrNullStr(forein_lan1)){
//	      	  return ResumeConstant.lang_type.get(new Integer(forein_lan1));
//	        }
//		return "";
//	}
//
//	/**
//	 * 外语证书
//	 * @param certificate1
//	 * @return
//	 */
//	public static String getCertificate(String certificate1) {
//		if(!DateUtil.isNullOrNullStr(certificate1)){
//	      	  return ResumeConstant.all_certificate.get(new Integer(certificate1));
//	        }
//		return "";
//	}
//
//	/**
//	 * 读写能力
//	 * @param read_write1
//	 * @return
//	 */
//	public static String getRead_write(String read_write1) {
//		if(!DateUtil.isNullOrNullStr(read_write1)){
//	      	  return ResumeConstant.rw_ability.get(new Integer(read_write1));
//	        }
//		return "";
//	}
//
//	/**
//	 * 听说能力
//	 * @param listen_speak1
//	 * @return
//	 */
//	public static String getListen_speak(String listen_speak1) {
//		if(!DateUtil.isNullOrNullStr(listen_speak1)){
//	      	  return ResumeConstant.lt_ability.get(new Integer(listen_speak1));
//	        }
//		return "";
//	}
//
//	/**
//	 * 公司规模
//	 * @param corp_size
//	 * @return
//	 */
//	public static String getCorp_size(String corp_size) {
//		if(!DateUtil.isNullOrNullStr(corp_size)){
//	      	  return ResumeConstant.companyscale.get(new Integer(corp_size));
//	        }
//		return "";
//	}
//
//	/**
//	 * 公司性质
//	 * @param corp_type
//	 * @return
//	 */
//	public static String getCorp_type(String corp_type) {
//		if(!DateUtil.isNullOrNullStr(corp_type)){
//	      	  return ResumeConstant.companytype.get(new Integer(corp_type));
//	        }
//		return "";
//	}
//
//	/**
//	 * 所属行业
//	 * @param industry
//	 * @return
//	 */
//	public static String getIndustry(String industry) {
//		if(!DateUtil.isNullOrNullStr(industry)){
//			Type tye = IndustryTypeFactory.getInstance().getTypeById(industry);
//			if(tye!=null){
//				 return tye.getName();
//			}
//	      	 
//	        }
//		return "";
//	}
//
//	/**
//	 * 申请者类别
//	 * @param app_type
//	 * @return
//	 */
//	public static String getApp_type(String app_type) {
//		if(!DateUtil.isNullOrNullStr(app_type)){
//			 return ResumeConstant.proposer_type.get(new Integer(app_type));
//	        }
//		return "";
//	}
//
//	/**
//	 * 是否接受岗位调配
//	 * @param is_canassign
//	 * @return
//	 */
//	public static String getIs_canassign(String is_canassign) {
//		if(!DateUtil.isNullOrNullStr(is_canassign)){
//			 return ResumeConstant.is_canassign.get(new Integer(is_canassign));
//	        }
//		return "";
//	}
//
//	/**
//	 * 期望月薪
//	 * @param salary
//	 * @return
//	 */
//	public static String getSalary(String salary) {
//		if(!DateUtil.isNullOrNullStr(salary)){
//			 return ResumeConstant.salary.get(new Integer(salary));
//	        }
//		return "";
//	}
//
//	/**
//	 * 是否有亲友在公司
//	 * @param is_friend_in
//	 * @return
//	 */
//	public static String getIs_friend_in(String is_friend_in) {
//		if(!DateUtil.isNullOrNullStr(is_friend_in)){
//			 return ResumeConstant.is_friend_in.get(new Integer(is_friend_in));
//	        }
//		return "";
//	}
//
//	/**
//	 * 奖学金级别
//	 * @param level1
//	 * @return
//	 */
//	public static String getLevel(String level1) {
//		if(!DateUtil.isNullOrNullStr(level1)){
//			 return ResumeConstant.scholarship_level.get(new Integer(level1));
//	        }
//		return "";
//	}
//
//	/**
//	 * 奖学金
//	 * @param scholarship1
//	 * @return
//	 */
//	public static String getScholarship(String scholarship1) {
//		if(!DateUtil.isNullOrNullStr(scholarship1)){
//			 return ResumeConstant.scholarship.get(new Integer(scholarship1));
//	        }
//		return "";
//	}
//	
//	/**
//	 * 担任职务
//	 * @param is_stu_leader
//	 * @return
//	 */
//	public static String getIs_stu_leader(String is_stu_leader,String duties) {
//		if(!DateUtil.isNullOrNullStr(is_stu_leader)){
//			String value = ResumeConstant.is_stu_leader.get(new Integer(is_stu_leader));
//			 if(value!=null&&value.startsWith("其他")){
//				 return duties;
//			 }else{
//				 return value;
//			 }
//	        }
//		return "";
//	}
//	
//	/**
//	 * 获得评分
//	 */
//	public static String getScore(String score1,String score2,String score3){
//		int result = 0;
//		if(!DateUtil.isNullOrNullStr(score1)){
//			result+=new Integer(score1);
//		}
//		if(!DateUtil.isNullOrNullStr(score2)){
//			result+=new Integer(score2);
//		}
//		if(!DateUtil.isNullOrNullStr(score3)){
//			result+=new Integer(score3);
//		}
//		return result==0?"":result+"";
//	}
//	
//	/**
//	 * 赵垒返回的时间转换
//	 * @param str
//	 * @return
//	 * @throws ParseException
//	 */
//	public static String getDate(String str){
//		if(!DateUtil.isNullOrNullStr(str)){
//			Date date = new Date(str);
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//			return sdf.format(date);
//		}else{
//			return "";
//		}
//	}
//	
//	
//
//}
