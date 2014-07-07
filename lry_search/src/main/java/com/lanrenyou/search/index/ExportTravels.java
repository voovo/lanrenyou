package com.lanrenyou.search.index;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * 志愿导出为索引
 * @author zhaolei
 * create time:2011-06-16
 * 修改记录：
 *
 */
@Component
public class ExportTravels {
	
	@Autowired
	private UserPlannerRepository repoResumRepository;

	@Autowired
	private TravelInfoReository repo;
	
	@Autowired
	private CrJobRepository jobrepo;
	
	@Autowired
	private UserInfoRepository tabrepo;
	
	@Autowired
	private CrAreaRepository arearepo;
	
	@Autowired
	private CrAssessmentRepository assrepo;
	
	private Log log = LogFactory.getLog(ExportTravels.class);
	
	private boolean isRunning = false;

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

	public void export(SolrServer server,List<TravelInfo> list) {
		if (isRunning) {
			log.info("export crWishApply is running.....");
			return;
		}
		try {
			isRunning = true;
			for (TravelInfo pst : list) {
				SolrInputDocument doc = convertDoc(pst);
				if (doc == null)
					continue;
				try {
					server.add(doc);
				} catch (Exception e) {
					log.error("id=" + pst.getId()
							+ "的CrWishApply记录索引建立失败的!!异常信息：" + e.getMessage());
					continue;
				}
			}
			log.info("导入部份doc内容,size: "+list.size()+"!");
			UpdateResponse upres = server.commit(true, true);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			isRunning = false;
		}
	}

	private SolrInputDocument convertDoc(TravelInfo vo) {
		SolrInputDocument doc = new SolrInputDocument();
		try {
			//志愿主表信息
			doc.addField("wishid", vo.getId());//志愿申请ID
			doc.addField("wishno", vo.getApplyNo());//志愿编号
			doc.addField("compid", vo.getCorpId());//公司ID
			if(vo.getApplyTime()!=null && !"".equals(vo.getApplyTime()))
			doc.addField("applydate", sdf.format(vo.getApplyTime()));//申请时间
			doc.addField("jobid", vo.getJobId());//职位ID
			if(vo.getWishOrder()!=null && !"".equals(vo.getWishOrder()))
			doc.addField("wishorder", vo.getWishOrder());//志愿顺序
			doc.addField("wishstageid", vo.getWishStage());//志愿阶段ID
			if(vo.getApplyStatus()!=null && !"".equals(vo.getApplyStatus()))
			doc.addField("applystatus", vo.getApplyStatus());//志愿状态
			doc.addField("userid", vo.getUserId());//申请人Id
			doc.addField("autoscore", vo.getAutoscore());
			//简历主表信息
			List<CrResume> ls_resume=repoResumRepository.getCrResumVos(vo.getCorpId(), vo.getUserId());
			if(ls_resume!=null&&ls_resume.size()>0){
				CrResume rVo=(CrResume)ls_resume.get(0);//因为一对一关系所以会查询到一条记录
				if(!"1".equals(rVo.getResumeStatus())){
					return null;
				}
				doc.addField("rid", rVo.getId());
				doc.addField("rcode", rVo.getResumeCode());
				if(rVo.getCrRPersonalInfos()!=null&&rVo.getCrRPersonalInfos().size()>0){
					//简历个人信息
					CrRPersonalInfo persionVo=(CrRPersonalInfo)rVo.getCrRPersonalInfos().toArray()[0];
					if(persionVo!=null){
						doc.addField("applyusername", persionVo.getName());
						doc.addField("currentcity", persionVo.getCurrentCity());//现在居住城市
						doc.addField("currentcityname", ResumeShowUtil.getCurrent_city(persionVo.getCurrentCity()));//现在居住城市
						Type type = null;
						if(persionVo.getCurrentCity()!=null){//取父节点地点
							type = AddressFactory.getInstance().getTypeById(persionVo.getCurrentCity());
							if(type!=null){
								if("0000000000".equals(type.getParent().getId())){
									doc.addField("parentcurrentcity", persionVo.getCurrentCity());
								}else{
									doc.addField("parentcurrentcity",type.getParent().getId());
									doc.addField("childcurrentcity", persionVo.getCurrentCity());
								}	
							}else{
								doc.addField("parentcurrentcity", persionVo.getCurrentCity());
							}
						}
						doc.addField("registeredcity", persionVo.getRegisteredCity());//户口所在地
						if(persionVo.getRegisteredCity()!=null){//取父节点地点
							type = AddressFactory.getInstance().getTypeById(persionVo.getRegisteredCity());
							if(type!=null){
								if("0000000000".equals(type.getParent().getId())){
									doc.addField("parentregisteredcity", persionVo.getRegisteredCity());
								}else{
									doc.addField("parentregisteredcity",type.getParent().getId());
									doc.addField("childregisteredcity", persionVo.getRegisteredCity());
								}	
							}else{
								doc.addField("parentregisteredcity", persionVo.getRegisteredCity());
							}
						}
						doc.addField("registeredcityname", ResumeShowUtil.getCurrent_city(persionVo.getRegisteredCity()));//户口所在地
						doc.addField("personalevaluation", persionVo.getPersonalEvaluation());//个人评价
						doc.addField("email", persionVo.getEmail());//邮件
						doc.addField("mobileno", persionVo.getMobileNo());//电话
						doc.addField("ged", persionVo.getGender());//性别
						if(persionVo.getBirthday()!=null && !"".equals(persionVo.getBirthday()))
						doc.addField("birthday", sdf.format(persionVo.getBirthday()));//生日
						doc.addField("documenttype", persionVo.getDocumentType());//证件类型
						doc.addField("documentnumber", persionVo.getDocumentNumber());//证件号码
						doc.addField("nation", persionVo.getNation());//民族 
						if(persionVo.getHeight()!=null && !"".equals(persionVo.getHeight()))
						doc.addField("height", persionVo.getHeight());//身高
						if(persionVo.getWeight()!=null && !"".equals(persionVo.getWeight()))
						doc.addField("weight", persionVo.getWeight());//体重
						doc.addField("ismarried", persionVo.getIsMarried());//是否已婚
						doc.addField("politicsstatus", persionVo.getPoliticsStatus());//政治面貌
						if(persionVo.getGraduateDate()!=null && !"".equals(persionVo.getGraduateDate()))
						doc.addField("graduatedate", sdf.format(persionVo.getGraduateDate()));//毕业时间
						doc.addField("isreceivemsg", persionVo.getIsReceiveMsg());//是否接受短信
						doc.addField("othercontactinfotype", persionVo.getOtherType());//其他联系方式类型
						doc.addField("othercontactinfo", persionVo.getOtherManner());//其他联系方式
						doc.addField("persionhighestdegree", persionVo.getHighestDegree());//个人信息中的最高学历
						doc.addField("userfree1", persionVo.getFree1());//获取自定义项1
						doc.addField("userfree2", persionVo.getFree2());//获取自定义项1
						doc.addField("userfree3", persionVo.getFree3());//获取自定义项1
						doc.addField("userfree4", persionVo.getFree4());//获取自定义项1
						doc.addField("userfree5", persionVo.getFree5());//获取自定义项1
					}
				}
		     if(rVo.getCrROtherses()!=null&&rVo.getCrROtherses().size()>0){
		    	//简历其它信息
					CrROthers othersVo=(CrROthers)rVo.getCrROtherses().toArray()[0];//因为一对一关系所以会查询到一条记录
					if(othersVo!=null){
						doc.addField("apptype", othersVo.getAppType());//申请者类别
						doc.addField("iscanassign", othersVo.getIsCanassign());//是否接受岗位调配
						if(othersVo.getSalary()!=null && !"".equals(othersVo.getSalary()))
						doc.addField("salary", othersVo.getSalary());//期望月薪
						doc.addField("worktime", othersVo.getWorkTime());//何时可以上班
						doc.addField("isfriendin", othersVo.getIsFriendIn());//是否新友受雇于本公司
						doc.addField("informationfrom", othersVo.getInformationFrom());//从哪里获取招聘信息
						if(othersVo.getCity()!=null){
							String[] citys = othersVo.getCity().split(",");
							for(String city : citys){
								doc.addField("resumeothercity", city);//期望工作城市
								doc.addField("resumeothercityname", ResumeShowUtil.getCurrent_city(city));//期望工作城市
							}
						}
						doc.addField("resumeotherfriendinfo", othersVo.getFriendInfo());//亲友的姓名和部门信息
						doc.addField("otherfree1", othersVo.getFree1());//获取自定义项1
						doc.addField("otherfree2", othersVo.getFree2());//获取自定义项1
						doc.addField("otherfree3", othersVo.getFree3());//获取自定义项1
						doc.addField("otherfree4", othersVo.getFree4());//获取自定义项1
						doc.addField("otherfree5", othersVo.getFree5());//获取自定义项1
						
					
					} 
		     }
			if(rVo.getCrREducations()!=null&&rVo.getCrREducations().size()>0){
				//教育背景
				Object[] educationVos= rVo.getCrREducations().toArray();
				int maxEdu = -1;
				int maxIndex = 0;
				if(educationVos!=null&&educationVos.length>0){
					for(int i=0;i<educationVos.length;i++){//如果教育背景是其他且唯一那么最高为其他否则为除去其他中的最高
						int edu = -1;
						if(((CrREducation)educationVos[i]).getEducation()!=null){
						    edu = Integer.parseInt(((CrREducation)educationVos[i]).getEducation());
							if(educationVos.length==1 && edu==11){
								maxEdu = 11;
								maxIndex = 0;
								
							}
							
							if(edu>maxEdu && edu<11){
								maxEdu = edu;
								maxIndex = i;
							}
						}
						doc.addField("school", ((CrREducation)educationVos[i]).getSchool());//学校
						doc.addField("schoolname", (StringUtils.isNotBlank(((CrREducation)educationVos[i]).getOtherschool()))?((CrREducation)educationVos[i]).getOtherschool():ResumeShowUtil.getSchool(((CrREducation)educationVos[i]).getSchool(),null));//学校名称
//						if(edu==11){
//							doc.addField("schoolname", ((CrREducation)educationVos[i]).getOtherschool());//学校名称
//						}else{
//							doc.addField("schoolname", ResumeShowUtil.getSchool(((CrREducation)educationVos[i]).getSchool(), null));//学校名称
//						}
						doc.addField("major", ((CrREducation)educationVos[i]).getMajor());//专业
						doc.addField("majorname", ResumeShowUtil.getMajor(((CrREducation)educationVos[i]).getMajor(), null));//专业名称
						doc.addField("majorcategory", ((CrREducation)educationVos[i]).getMajorcategory());//专业类别
						doc.addField("majorcategoryname", (StringUtils.isNotBlank(((CrREducation)educationVos[i]).getOthermajor())?((CrREducation)educationVos[i]).getOthermajor():ResumeShowUtil.getMajorType(((CrREducation)educationVos[i]).getMajorcategory())));//专业类别名称
						doc.addField("majorclass", ((CrREducation)educationVos[i]).getMajorClass());//主修课程
						if(((CrREducation)educationVos[i]).getEducation()!=null && !"".equals(((CrREducation)educationVos[i]).getEducation())){
							doc.addField("education", ((CrREducation)educationVos[i]).getEducation());//学历
							doc.addField("educationname", ResumeShowUtil.getEducation(((CrREducation)educationVos[i]).getEducation()));//学历
						}
						if(((CrREducation)educationVos[i]).getDegree()!=null && !"".equals(((CrREducation)educationVos[i]).getDegree()))
						doc.addField("degree", ((CrREducation)educationVos[i]).getDegree());//学位
						if(((CrREducation)educationVos[i]).getRank()!=null && !"".equals(((CrREducation)educationVos[i]).getRank()))
						doc.addField("rank", ((CrREducation)educationVos[i]).getRank());//年级排名
						if(((CrREducation)educationVos[i]).getGpa()!=null && !"".equals(((CrREducation)educationVos[i]).getGpa()))
						doc.addField("gpa", ((CrREducation)educationVos[i]).getGpa());//gpa
						if(((CrREducation)educationVos[i]).getIsOversea()!=null && !"".equals(((CrREducation)educationVos[i]).getIsOversea()))
						doc.addField("isoversea", ((CrREducation)educationVos[i]).getIsOversea());//是否海外学习经历
						doc.addField("proveperson", ((CrREducation)educationVos[i]).getProvePerson());//证明人
						doc.addField("educfree1", ((CrREducation)educationVos[i]).getFree1());//获取自定义项1
						doc.addField("educfree2", ((CrREducation)educationVos[i]).getFree2());//获取自定义项1
						doc.addField("educfree3", ((CrREducation)educationVos[i]).getFree3());//获取自定义项1
						doc.addField("educfree4", ((CrREducation)educationVos[i]).getFree4());//获取自定义项1
						doc.addField("educfree5", ((CrREducation)educationVos[i]).getFree5());//获取自定义项1
					}
					if(maxEdu!=-1){
						doc.addField("highesteducation", ((CrREducation)educationVos[maxIndex]).getEducation());//最高学历
						doc.addField("highestschool", ((CrREducation)educationVos[maxIndex]).getSchool());
						doc.addField("highestmajorcategory", ((CrREducation)educationVos[maxIndex]).getMajorcategory());
						doc.addField("highestmajor", ((CrREducation)educationVos[maxIndex]).getMajor());
						doc.addField("highestgpa", ((CrREducation)educationVos[maxIndex]).getGpa());
						doc.addField("otherschool", ((CrREducation)educationVos[maxIndex]).getOtherschool());//其他学校
						doc.addField("othermajor", ((CrREducation)educationVos[maxIndex]).getOthermajor());//其他专业
						if(StringUtils.isNotBlank(((CrREducation)educationVos[maxIndex]).getEducation().trim()))
							try{
								doc.addField("highesteducationint", Integer.parseInt(((CrREducation)educationVos[maxIndex]).getEducation()));//最高学历
							}catch(Exception e){
								e.printStackTrace();
							}
					}
				}
			}
			if(rVo.getCrRLanguages()!=null&&rVo.getCrRLanguages().size()>0){
				//语言能力
				CrRLanguage languageVo=(CrRLanguage)rVo.getCrRLanguages().toArray()[0];
				if(languageVo!=null){
					if(languageVo.getEngLevel()!=null && !"".equals(languageVo.getEngLevel()))
					doc.addField("englevel", languageVo.getEngLevel());// 英语等级
					if(languageVo.getEngScore()!=null && !"".equals(languageVo.getEngScore()))
					doc.addField("engscore", languageVo.getEngScore());//英语分数
					if(languageVo.getToefl()!=null && !"".equals(languageVo.getToefl()))
					doc.addField("toefl", languageVo.getToefl());//toefl
					if(languageVo.getGre()!=null && !"".equals(languageVo.getGre()))
					doc.addField("gre", languageVo.getGre());//gre
					if(languageVo.getGmat()!=null && !"".equals(languageVo.getGmat()))
					doc.addField("gmat", languageVo.getGmat());//gmat
					if(languageVo.getIelts()!=null && !"".equals(languageVo.getIelts()))
					doc.addField("ieltc", languageVo.getIelts());//ieltc
					if(languageVo.getToeic()!=null && !"".equals(languageVo.getToeic()))
					doc.addField("toeic", languageVo.getToeic());//toeic
					doc.addField("foreinlan", languageVo.getForeinLan1());//外语语种（多值）
					doc.addField("foreinlan", languageVo.getForeinLan2());//外语语种（多值）
					doc.addField("foreinlan", languageVo.getForeinLan3());//外语语种（多值）
					doc.addField("certificate", languageVo.getCertificate1());//证书(多值)
					doc.addField("certificate", languageVo.getCertificate2());//证书（多值）
					doc.addField("certificate", languageVo.getCertificate3());//证书（多值）
					doc.addField("readwrite", languageVo.getReadWrite1());//读写能力（多值）
					doc.addField("readwrite", languageVo.getReadWrite2());//读写能力(多值)
					doc.addField("readwrite", languageVo.getReadWrite3());//读写能力（多值）
					doc.addField("listenspeak", languageVo.getListenSpeak1());//听说能力（多值）
					doc.addField("listenspeak", languageVo.getListenSpeak2());//听说能力（多值）
					doc.addField("listenspeak", languageVo.getListenSpeak3());//听说能力（多值）
					doc.addField("languagefree1", languageVo.getFree1());//获取自定义项1
					doc.addField("languagefree2", languageVo.getFree2());//获取自定义项1
					doc.addField("languagefree3", languageVo.getFree3());//获取自定义项1
					doc.addField("languagefree4", languageVo.getFree4());//获取自定义项1
					doc.addField("languagefree5", languageVo.getFree5());//获取自定义项1
				}
			}
		if(rVo.getCrRRewards()!=null&&rVo.getCrRRewards().size()>0){
			//奖励活动
			CrRReward rewardVo=(CrRReward)rVo.getCrRRewards().toArray()[0];
			if(rewardVo!=null){
				doc.addField("duties", rewardVo.getDuties());//职位
				doc.addField("isstuleader", rewardVo.getIsStuLeader());//是否学生会/班干部
				doc.addField("scholarship", rewardVo.getScholarship1());//奖学金1
				doc.addField("scholarship", rewardVo.getScholarship2());//奖学金2
				doc.addField("scholarship", rewardVo.getScholarship3());//奖学金3
				doc.addField("level", rewardVo.getLevel1());//奖学金级别1
				doc.addField("level", rewardVo.getLevel2());//奖学金级别2
				doc.addField("level", rewardVo.getLevel3());//奖学金级别3
				if(rewardVo.getReceiveTime1()!=null && !"".equals(rewardVo.getReceiveTime1()))
				doc.addField("receivetime1", sdf.format(rewardVo.getReceiveTime1()));//获奖时间1
				if(rewardVo.getReceiveTime2()!=null && !"".equals(rewardVo.getReceiveTime2()))
				doc.addField("receivetime2", sdf.format(rewardVo.getReceiveTime2()));//获奖时间2
				if(rewardVo.getReceiveTime3()!=null && !"".equals(rewardVo.getReceiveTime3()))
				doc.addField("receivetime3", sdf.format(rewardVo.getReceiveTime3()));//获奖时间3
				doc.addField("otherreward", rewardVo.getOtherReward());//其它奖励
				doc.addField("schoolactivity", rewardVo.getSchoolActivity());//其它校内活动
				doc.addField("rewardfree1", rewardVo.getFree1());//获取自定义项1
				doc.addField("rewardfree2", rewardVo.getFree2());//获取自定义项1
				doc.addField("rewardfree3", rewardVo.getFree3());//获取自定义项1
				doc.addField("rewardfree4", rewardVo.getFree4());//获取自定义项1
				doc.addField("rewardfree5", rewardVo.getFree5());//获取自定义项1
				
			}
		}
		 if(rVo.getCrRInternships()!=null&&rVo.getCrRInternships().size()>0){
			//实习经验
				Object[] intershipVos=rVo.getCrRInternships().toArray();
				if(intershipVos!=null&&intershipVos.length>0){
					for(int i=0;i<intershipVos.length;i++){
						doc.addField("internshipcorpname", ((CrRInternship)intershipVos[i]).getCorpName());//实习公司名称
						doc.addField("corpsize", ((CrRInternship)intershipVos[i]).getCorpSize());//实习公司规模
						doc.addField("corptype", ((CrRInternship)intershipVos[i]).getCorpType());//实习公司性质
						doc.addField("industry", ((CrRInternship)intershipVos[i]).getIndustry());//实习公司所属行业
						if(((CrRInternship)intershipVos[i]).getBeginTime()!=null && !"".equals(((CrRInternship)intershipVos[i]).getBeginTime()))
						doc.addField("internshipbegintime", sdf.format(((CrRInternship)intershipVos[i]).getBeginTime()));//实习开始时间
						if(((CrRInternship)intershipVos[i]).getEndTime()!=null && !"".equals(((CrRInternship)intershipVos[i]).getEndTime()))
						doc.addField("internshipendtime", sdf.format(((CrRInternship)intershipVos[i]).getEndTime()));//实习结束时间
						doc.addField("internshipcorpdept", ((CrRInternship)intershipVos[i]).getDept());//实习公司部门
						doc.addField("internshipcorpposition", ((CrRInternship)intershipVos[i]).getJobName());//实习职务
						doc.addField("internshipcorpworkdiscription", ((CrRInternship)intershipVos[i]).getJobDesc());//工作描术
						doc.addField("internshipcorpersonandtel", ((CrRInternship)intershipVos[i]).getProvePerson());//证名人及联系电话
						doc.addField("internshipisoversea", ((CrRInternship)intershipVos[i]).getIsOversea());//是否有海外实习经验
						doc.addField("internshipfree1", ((CrRInternship)intershipVos[i]).getFree1());//获取自定义项1
						doc.addField("internshipfree2", ((CrRInternship)intershipVos[i]).getFree2());//获取自定义项1
						doc.addField("internshipfree3", ((CrRInternship)intershipVos[i]).getFree3());//获取自定义项1
						doc.addField("internshipfree4", ((CrRInternship)intershipVos[i]).getFree4());//获取自定义项1
						doc.addField("internshipfree5", ((CrRInternship)intershipVos[i]).getFree5());//获取自定义项1
					}
				} 
		 }
			if(rVo.getCrRProjects()!=null&&rVo.getCrRProjects().size()>0){
				//项目经验
				Object []  projectVos=rVo.getCrRProjects().toArray();
				 if(projectVos!=null&&projectVos.length>0){
					 for(int i=0;i<projectVos.length;i++){
						 if(((CrRProject)projectVos[i]).getBeginTime()!=null && !"".equals(((CrRProject)projectVos[i]).getBeginTime()))
						    doc.addField("projectbegintime", sdf.format(((CrRProject)projectVos[i]).getBeginTime()));//项目开始时间
						 if(((CrRProject)projectVos[i]).getEndTime()!=null && !"".equals(((CrRProject)projectVos[i]).getEndTime()))
						    doc.addField("projectendtime", sdf.format(((CrRProject)projectVos[i]).getEndTime()));//项目结束时间
						    doc.addField("projectname", ((CrRProject)projectVos[i]).getProjectName());//项目名称
							doc.addField("projectposition", ((CrRProject)projectVos[i]).getJobName());//职位名称
							doc.addField("projectdiscription", ((CrRProject)projectVos[i]).getProjectDesc());//项目描述
							doc.addField("projectpersontel", ((CrRProject)projectVos[i]).getProvePerson());//证明人与联系电话
							doc.addField("projectfree1", ((CrRProject)projectVos[i]).getFree1());//获取自定义项1
							doc.addField("projectfree2", ((CrRProject)projectVos[i]).getFree2());//获取自定义项1
							doc.addField("projectfree3", ((CrRProject)projectVos[i]).getFree3());//获取自定义项1
							doc.addField("projectfree4", ((CrRProject)projectVos[i]).getFree4());//获取自定义项1
							doc.addField("projectfree5", ((CrRProject)projectVos[i]).getFree5());//获取自定义项1
					 }
				 }
			}
			if(rVo.getCrRFamilies()!=null&&rVo.getCrRFamilies().size()>0){
				//其它家庭成员
				Object [] familyVos=rVo.getCrRFamilies().toArray();
				if(familyVos!=null&&familyVos.length>0){
					for(int i=0;i<familyVos.length;i++){
						doc.addField("familyusername", ((CrRFamily)familyVos[i]).getName());//家庭成员名称
						doc.addField("familyrelation", ((CrRFamily)familyVos[i]).getRelation());//家庭成员关系
						doc.addField("familyrelationtel", ((CrRFamily)familyVos[i]).getTel());//家庭成员电话
						doc.addField("familyemail", ((CrRFamily)familyVos[i]).getEmail());//联系email
						doc.addField("familypersoncompany", ((CrRFamily)familyVos[i]).getCorp());//工作单位名称
						doc.addField("familypersonposition", ((CrRFamily)familyVos[i]).getDuties());//工作单位名称
						doc.addField("familyfree1", ((CrRFamily)familyVos[i]).getFree1());//获取自定义项1
						doc.addField("familyfree2", ((CrRFamily)familyVos[i]).getFree2());//获取自定义项1
						doc.addField("familyfree3", ((CrRFamily)familyVos[i]).getFree3());//获取自定义项1
						doc.addField("familyfree4", ((CrRFamily)familyVos[i]).getFree4());//获取自定义项1
						doc.addField("familyfree5", ((CrRFamily)familyVos[i]).getFree5());//获取自定义项1
					}
				}
			}

			
			}else{
				return null;
			}
			
			//其他申请记录
			List<TravelInfo> otherwishs = repo.getCrWishApplyVos("FROM CrWishApply WHERE userId="+vo.getUserId()+" AND id!="+vo.getId(), null, null);
			if(otherwishs!=null && otherwishs.size()>0){
				for(TravelInfo otherwish : otherwishs){
					doc.addField("otherwishid", otherwish.getId());
				}
				
			}
			//所属部门信息
			List<TravelContent> jobs = jobrepo.getJobs("FROM Job WHERE id="+vo.getJobId(), null, null);
			if(jobs!=null && jobs.size()==1){
				doc.addField("corporgid", jobs.get(0).getCorpOrgId());
				doc.addField("jobcode", jobs.get(0).getJobCode());
				doc.addField("jobname", jobs.get(0).getJobTitle());
			}
			//面试笔试分数
			if(vo.getCrInterviewScores()!=null&&vo.getCrInterviewScores().size()>0){
				Object [] interviewScore=vo.getCrInterviewScores().toArray();
				List<Long> oldstageids = new ArrayList<Long>();
				for(int i=0;i<interviewScore.length;i++){
//				doc.addField("areaid", ((CrInterviewScore)interviewScore[i]).getAreaId());//场地ID
					Long stageid = ((CrInterviewScore)interviewScore[i]).getStageId();
					if(stageid==null){
						continue;
					}else if(oldstageids.contains(stageid)){
					    continue;
					}else{
						oldstageids.add(stageid);
					}
					doc.addField("myareaid_"+stageid, ((CrInterviewScore)interviewScore[i]).getAreaId());//场地ID
					if(((CrInterviewScore)interviewScore[i]).getAreaId()!=null){
						List<CrArea> areas = arearepo.getTabs("FROM CrArea WHERE id="+((CrInterviewScore)interviewScore[i]).getAreaId(), 0, 1);
						if(areas!=null && areas.size()>0)
						doc.addField("myareaname_"+stageid, areas.get(0).getAreaName());//场地名称
					}
					if(((CrInterviewScore)interviewScore[i]).getScore1()!=null && !"".equals(((CrInterviewScore)interviewScore[i]).getScore1())){
						doc.addField("myscore1_"+stageid, ((CrInterviewScore)interviewScore[i]).getScore1());//分数1
//						doc.addField("score1", ((CrInterviewScore)interviewScore[i]).getScore1());//分数1
					}
					if(((CrInterviewScore)interviewScore[i]).getScore2()!=null && !"".equals(((CrInterviewScore)interviewScore[i]).getScore2())){
						doc.addField("myscore2_"+stageid, ((CrInterviewScore)interviewScore[i]).getScore2());//分数2
						doc.addField("score2", ((CrInterviewScore)interviewScore[i]).getScore2());//分数2
					}
					if(((CrInterviewScore)interviewScore[i]).getScore3()!=null && !"".equals(((CrInterviewScore)interviewScore[i]).getScore3())){
						doc.addField("myscore3_"+stageid, ((CrInterviewScore)interviewScore[i]).getScore3());//分数3
						doc.addField("score3", ((CrInterviewScore)interviewScore[i]).getScore3());//分数3
					}
				}
				oldstageids = null;
			}
			//职位申请标签
			if(vo.getCrApplyTabs()!=null&&vo.getCrApplyTabs().size()>0){
				Object [] applyTabVos=vo.getCrApplyTabs().toArray();
				for(int i=0;i<applyTabVos.length;i++){
					List<CrTab> tabs = tabrepo.getTabs("FROM CrTab WHERE id="+((CrApplyTab)applyTabVos[i]).getNoteId(), null, null);
					if(tabs!=null && tabs.size()>0)
					doc.addField("applytabid", tabs.get(0).getId());//标签ID
				}
			}
			//评估分数
			List<CrAssessmentDetail> assessmentDetails = assrepo.getAsss("FROM CrAssessmentDetail WHERE WISH_ID="+vo.getId(), null, null);
			if(assessmentDetails!=null && assessmentDetails.size()>0){
				for(CrAssessmentDetail ass:assessmentDetails){
					if(ass.getStepId()==null)continue;
					doc.addField("assscore_"+ass.getStepId(),ass.getScore());
					doc.addField("assurl_"+ass.getStepId(),ass.getReportUrl());
					doc.addField("assdetail_"+ass.getStepId(),ass.getResult());
				}
			}
			
			return doc;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
}
