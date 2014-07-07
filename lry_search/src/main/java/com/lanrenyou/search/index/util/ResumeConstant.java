//package com.baidu.job.search.index.util;
//
//
//import java.util.HashMap;
//import java.util.LinkedHashMap;
//import java.util.Map;
//
///**
// * @author ZhuFeng
// * 该文件的变量请不要改动（ScoreRuleUtil关联本类）
// */
//public class ResumeConstant {
//	
//	//性别
//	public static Map<Integer, String> genderType = new LinkedHashMap<Integer, String>();
//	static{
//		genderType.put(2, "男");	
//		genderType.put(1, "女");
//	}
//	
//	//婚姻状况
//	public static Map<Integer, String> marriedType = new LinkedHashMap<Integer, String>();
//	static{
//		marriedType.put(1, "未婚");
//		marriedType.put(2, "已婚");	
//		marriedType.put(3, "保密");
//	}
//	
//	//证件类型
//	public static Map<Integer, String> documentsType = new LinkedHashMap<Integer, String>();
//	static{
//		documentsType.put(1, "身份证");
//		documentsType.put(2, "护照");	
//		documentsType.put(3, "军官证");
//		documentsType.put(6, "其他");
//	}
//	
//	//政治面貌
//	public static Map<Integer, String> politics_status = new LinkedHashMap<Integer, String>();
//	static{
//		politics_status.put(3, "群众");	
//		politics_status.put(2, "团员");	
//		politics_status.put(1, "中共党员");
//		politics_status.put(4, "民主党派");	
//		politics_status.put(6, "其它");	
//	}
//	
//	//学历
//	public static Map<Integer, String> academic = new LinkedHashMap<Integer, String>();
//	static{
//		academic.put(1, "初中");
//		academic.put(2, "高中");
//		academic.put(4, "中专");
//		academic.put(5, "大专");
//		academic.put(6, "本科");
//		academic.put(7, "硕士");
//		academic.put(10, "博士");
//		academic.put(11, "其他");
//	}
//	
//	//学位
//	public static Map<Integer, String> degree = new LinkedHashMap<Integer, String>();
//	static{
//		degree.put(1, "学士");
//		degree.put(2, "双学士");
//		degree.put(3, "硕士");
//		degree.put(4, "MBA");
//		degree.put(5, "EMBA");
//		degree.put(6, "博士");
//		degree.put(7, "其他");
//	}
//	
//	//其他联系方式
//	public static Map<Integer, String> other_type = new LinkedHashMap<Integer, String>();
//	static{
//		other_type.put(1, "宿舍电话");
//		other_type.put(2, "家庭电话");	
//		other_type.put(3, "MSN");	
//		other_type.put(4, "QQ");	
//		other_type.put(5, "百度HI");	
//		other_type.put(6, "其它");	
//	}
//	
//	//年级排名
//	public static Map<Integer, String> grade_order = new LinkedHashMap<Integer, String>();
//	static{
//		grade_order.put(1, "5%");
//		grade_order.put(2, "10%");
//		grade_order.put(3, "20%");
//		grade_order.put(4, "30%");
//		grade_order.put(5, "其他");
//	}
//	
//	//英语等级
//	public static Map<Integer, String> eng_level = new LinkedHashMap<Integer, String>();
//	static{
//		eng_level.put(5, "未参加");
//		eng_level.put(1, "四级");
//		eng_level.put(2, "六级");
//		eng_level.put(3, "专业四级");
//		eng_level.put(4, "专业八级");
//	}
//	
//	//读写能力
//	public static Map<Integer, String> rw_ability = new LinkedHashMap<Integer, String>();
//	static{
//		rw_ability.put(1, "一般");	
//		rw_ability.put(2, "良好");
//		rw_ability.put(3, "熟练");
//		rw_ability.put(4, "精通");
//	}
//	
//	//听说能力
//	public static Map<Integer, String> lt_ability = new LinkedHashMap<Integer, String>();
//	static{
//		lt_ability.put(1, "一般");	
//		lt_ability.put(2, "良好");
//		lt_ability.put(3, "熟练");
//		lt_ability.put(4, "精通");		
//	}
//	
//	//公司规模
//	public static Map<Integer, String> companyscale = new LinkedHashMap<Integer, String>();
//	static{
//		companyscale.put(1, "少于50人");
//		companyscale.put(2, "50-150人");
//		companyscale.put(3, "150-500人");
//		companyscale.put(4, "500-2000人");
//		companyscale.put(5, "2000人以上");
//	}
//	
//	//公司性质
//	public static Map<Integer, String> companytype = new LinkedHashMap<Integer, String>();
//	static{
//		companytype.put(1, "国企");
//		companytype.put(3, "合资");
//		companytype.put(4, "外资");
//		companytype.put(5, "民营");
//		companytype.put(6, "政府机构");
//		companytype.put(11, "非盈利机构");
//		companytype.put(9, "事业单位");
//		companytype.put(10, "其他");
//	}
//	
//	//奖学金
//	public static Map<Integer, String> scholarship = new LinkedHashMap<Integer, String>();
//	static{
//		scholarship.put(1, "院系");
//		scholarship.put(2, "学校");
//		scholarship.put(3, "省市级");
//		scholarship.put(4, "国家级");
//		scholarship.put(5, "国际级");
//	}
//	
//	//奖学金级别
//	public static Map<Integer, String> scholarship_level = new LinkedHashMap<Integer, String>();
//	static{
//		scholarship_level.put(1, "特等奖");
//		scholarship_level.put(2, "一等奖");
//		scholarship_level.put(3, "二等奖");
//		scholarship_level.put(4, "三等奖");
//	}
//	
//	//申请者类别
//	public static Map<Integer, String> proposer_type = new LinkedHashMap<Integer, String>();
//	static{
//		proposer_type.put(1, "在校学生");
//		proposer_type.put(2, "应届毕业生");
//		proposer_type.put(3, "社会人士");
//	}
//	
//	//是否接受岗位调配
//	public static Map<Integer, String> is_canassign = new LinkedHashMap<Integer, String>();
//	static{
//		is_canassign.put(1, "是");
//		is_canassign.put(2, "否");
//	}
//	
//	//海外学习经历
//	public static Map<Integer, String> is_oversea = new LinkedHashMap<Integer, String>();
//	static{
//		is_oversea.put(1, "是");
//		is_oversea.put(2, "否");
//	}
//	
//	//是否有亲友在本公司
//	public static Map<Integer, String> is_friend_in = new LinkedHashMap<Integer, String>();
//	static{
//		is_friend_in.put(1, "是");
//		is_friend_in.put(0, "否");
//	}
//	
//	//期望月薪
//	public static Map<Integer, String> salary = new LinkedHashMap<Integer, String>();
//	static{
//		salary.put(1, "1500元及以下");
//		salary.put(2, "1501-3000元");
//		salary.put(3, "3001-5000元");
//		salary.put(4, "5001-8001元");
//		salary.put(5, "8001-12000元");
//		salary.put(6, "12001-18000元");
//		salary.put(7, "18001-25000元");
//		salary.put(8, "25000元以上");
//	} 
//	
//	//外语语种
//	public static Map<Integer, String> lang_type = new LinkedHashMap<Integer, String>();
//	static{
//		lang_type.put(1, "日语");
//		lang_type.put(2, "法语");		
//		lang_type.put(3, "德语");		
//		lang_type.put(4, "俄语");		
//		lang_type.put(5, "韩语");		
//	}
//	
//	//日语证书
//	public static Map<Integer, String> japanese_certificate = new LinkedHashMap<Integer, String>();
//	static{
//		japanese_certificate.put(1, "日语一级");
//		japanese_certificate.put(2, "日语二级");
//		japanese_certificate.put(3, "日语三级");
//		japanese_certificate.put(4, "日语四级");
//	}
//	
//	//法语证书
//	public static Map<Integer, String> france_certificate = new LinkedHashMap<Integer, String>();
//	static{
//		france_certificate.put(10, "TEF");
//		france_certificate.put(11, "TCF");
//	}
//	
//	//德语证书
//	public static Map<Integer, String> german_certificate = new LinkedHashMap<Integer, String>();
//	static{
//		german_certificate.put(20, "大学德语四级");
//		german_certificate.put(21, "大学德语六级");
//		german_certificate.put(22, "德语专业四级");
//		german_certificate.put(23, "德语专业八级");
//	}
//	
//	//俄语证书
//	public static Map<Integer, String> russia_certificate = new LinkedHashMap<Integer, String>();
//	static{
//		russia_certificate.put(30, "大学俄语四级");
//		russia_certificate.put(31, "大学俄语六级");
//		russia_certificate.put(32, "俄语专业四级");
//		russia_certificate.put(33, "俄语专业八级");
//	}
//	
//	//韩语证书
//	public static Map<Integer, String> korea_certificate = new LinkedHashMap<Integer, String>();
//	static{
//		korea_certificate.put(40, "TOPIK-初级");
//		korea_certificate.put(41, "TOPIK-中级");
//		korea_certificate.put(42, "TOPIK-高级");
//	}
//	
//	/**
//	 * 所有证书类型 hwz用
//	 */
//	public static Map<Integer, String> all_certificate = new LinkedHashMap<Integer, String>();
//	static{
//		
//		all_certificate.putAll(japanese_certificate);
//		all_certificate.putAll(france_certificate);
//		all_certificate.putAll(german_certificate);
//		all_certificate.putAll(russia_certificate);
//		all_certificate.putAll(korea_certificate);
//		
//	}
//	
//	//语种和证书关联关系
//	//不要修改，谢谢！
//	public static Map<Integer, Map<Integer, String>> linkage_lang = new HashMap<Integer, Map<Integer, String>>();
//	static{
//		linkage_lang.put(1, japanese_certificate);
//		linkage_lang.put(2, france_certificate);
//		linkage_lang.put(3, german_certificate);
//		linkage_lang.put(4, russia_certificate);
//		linkage_lang.put(5, korea_certificate);
//	}
//	
//	//是否
//	public static Map<Integer, String> yesOrNo = new LinkedHashMap<Integer, String>();
//	static{
//		yesOrNo.put(1, "是");	
//		yesOrNo.put(0, "否");
//	}
//	
//	//民族
//	public static Map<Integer, String> nation = new LinkedHashMap<Integer, String>();
//	static{
//		nation.put(1, "汉族");
//		nation.put(2, "壮族");
//		nation.put(3, "满族");
//		nation.put(4, "回族");
//		nation.put(5, "苗族");
//		nation.put(6, "维吾尔族");
//		nation.put(7, "土家族");
//		nation.put(8, "彝族");
//		nation.put(9, "蒙古族");
//		nation.put(10, "藏族");
//		nation.put(11, "布依族");
//		nation.put(12, "侗族");
//		nation.put(13, "瑶族");
//		nation.put(14, "朝鲜族");
//		nation.put(15, "白族");
//		nation.put(16, "哈尼族");
//		nation.put(17, "哈萨克族");
//		nation.put(18, "黎族");
//		nation.put(19, "傣族");
//		nation.put(20, "畲族");
//		nation.put(21, "傈僳族");
//		nation.put(22, "仡佬族");
//		nation.put(23, "东乡族");
//		nation.put(24, "拉祜族");
//		nation.put(25, "水族");
//		nation.put(26, "佤族");
//		nation.put(27, "纳西族");
//		nation.put(28, "羌族");
//		nation.put(29, "土族");
//		nation.put(30, "仫佬族");
//		nation.put(31, "锡伯族");
//		nation.put(32, "柯尔克孜族");
//		nation.put(33, "达斡尔族");
//		nation.put(34, "景颇族");
//		nation.put(35, "毛南族");
//		nation.put(36, "撒拉族");
//		nation.put(37, "布朗族");
//		nation.put(38, "塔吉克族");
//		nation.put(39, "阿昌族");
//		nation.put(40, "普米族");
//		nation.put(41, "鄂温克族");
//		nation.put(42, "怒族");
//		nation.put(43, "京族");
//		nation.put(44, "基诺族");
//		nation.put(45, "德昂族");
//		nation.put(46, "保安族");
//		nation.put(47, "俄罗斯族");
//		nation.put(48, "裕固族");
//		nation.put(49, "乌孜别克族");
//		nation.put(50, "门巴族");
//		nation.put(51, "鄂伦春族");
//		nation.put(52, "独龙族");
//		nation.put(53, "塔塔尔族");
//		nation.put(54, "赫哲族");
//		nation.put(55, "高山族");
//		nation.put(56, "珞巴族");
//	}
//	
//	//担任班级、学生会干部
//	public static Map<Integer, String> is_stu_leader = new LinkedHashMap<Integer, String>();
//	static{
//		is_stu_leader.put(1, "未担任");
//		is_stu_leader.put(2, "学生会主席");
//		is_stu_leader.put(3, "学生会部长");
//		is_stu_leader.put(4, "社团主席");
//		is_stu_leader.put(5, "校竞赛获奖者");
//		is_stu_leader.put(6, "学生会干事");
//		is_stu_leader.put(7, "班长团支书");
//		is_stu_leader.put(8, "班干部");
//		is_stu_leader.put(9, "其他职务");
//	}
//	
//	//筛选状态
//	public static Map<Integer, String> status = new LinkedHashMap<Integer, String>();
//	static{
//		status.put(0, "未筛选");
//		status.put(1, "筛选通过");
//		status.put(2, "筛选不通过（未发信）");
//		status.put(3, "筛选不通过（已发信）");
//		status.put(4, "无笔试记录");
//		status.put(5, "已安排笔试（未发信）");
//		status.put(6, "已发笔试通知（未确认）");
//		status.put(7, "已发笔试通知（已确认）");
//		status.put(8, "已发笔试通知（调整）");
//		status.put(9, "已发笔试通知（放弃）");
//		status.put(10, "笔试不通过（未发信）");
//		status.put(11, "笔试不通过（已发信）");
//		status.put(12, "笔试通过");
//		status.put(13, "无面试记录");
//		status.put(14, "已安排面试（未发信）");
//		status.put(15, "已发面试通知（未确认）");
//		status.put(16, "已发面试通知（已确认）");
//		status.put(17, "已发面试通知（调整）");
//		status.put(18, "已发面试通知（放弃）");
//		status.put(19, "面试不通过（未发信）");
//		status.put(20, "面试不通过（已发信）");
//		status.put(21, "面试通过");
//		status.put(22, "未发录用通知");
//		status.put(23, "已发录用通知（未确认）");
//		status.put(24, "已发录用通知（已确认）");
//		status.put(25, "已发录用通知（放弃）");
//	}
//	
//	//简历预览时筛选状态显示颜色
//	public static Map<Integer, String> status_color = new LinkedHashMap<Integer, String>();
//	static{
//		status_color.put(0, "black");
//		status_color.put(1, "green");
//		status_color.put(2, "red");
//		status_color.put(3, "red");
//		status_color.put(4, "black");
//		status_color.put(5, "blue");
//		status_color.put(6, "blue");
//		status_color.put(7, "blue");
//		status_color.put(8, "blue");
//		status_color.put(9, "red");
//		status_color.put(10, "red");
//		status_color.put(11, "red");
//		status_color.put(12, "green");
//		status_color.put(13, "black");
//		status_color.put(14, "blue");
//		status_color.put(15, "blue");
//		status_color.put(16, "blue");
//		status_color.put(17, "blue");
//		status_color.put(18, "gray");
//		status_color.put(19, "red");
//		status_color.put(20, "red");
//		status_color.put(21, "green");
//		status_color.put(22, "black");
//		status_color.put(23, "blue");
//		status_color.put(24, "green");
//		status_color.put(25, "gray");
//	}
//	
//}
