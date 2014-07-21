package com.lanrenyou.search.index;

import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lanrenyou.travel.model.TravelContent;
import com.lanrenyou.travel.model.TravelInfo;
import com.lanrenyou.travel.model.TravelInfoStat;
import com.lanrenyou.travel.service.ITravelContentService;
import com.lanrenyou.travel.service.ITravelInfoStatService;
import com.lanrenyou.user.model.UserInfo;
import com.lanrenyou.user.model.UserPlanner;
import com.lanrenyou.user.service.IUserInfoService;
import com.lanrenyou.user.service.IUserPlannerService;


/**
 * 游记导出为索引
 */
@Component
public class ExportTravels {
	
	private Log log = LogFactory.getLog(ExportTravels.class);
	
	@Autowired
	private ITravelContentService travelContentService;
	
	@Autowired
	private ITravelInfoStatService travelInfoStatService;
	
	@Autowired
	private IUserInfoService userInfoService;
	
	@Autowired
	private IUserPlannerService userPlannerService;
	
	private boolean isRunning = false;

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

	public void export(SolrServer server,List<TravelInfo> list) {
		if (isRunning) {
			log.info("export Travel is running.....");
			return;
		}
		try {
			isRunning = true;
			for (TravelInfo t : list) {
				SolrInputDocument doc = convertDoc(t);
				if (doc == null)
					continue;
				try {
					server.add(doc);
				} catch (Exception e) {
					log.error("id=" + t.getId()
							+ "的Travel记录索引建立失败的!!异常信息：" + e.getMessage());
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
			//游记表信息
			doc.addField("tid", vo.getId());//游记ID
			doc.addField("city", vo.getCity());//游记地市
			doc.addField("uid", vo.getUid());//发布者ID
			doc.addField("title", vo.getTitle());//标题
			doc.addField("isElite", vo.getIsElite());//是否精华
			doc.addField("isTop", vo.getIsTop());//是否置顶
			doc.addField("status", vo.getStatus());//状态
			doc.addField("createUid", vo.getCreateUid());//创建UID
			if(null != vo.getCreateTime()){
				doc.addField("createTime", sdf.format(vo.getCreateTime()));//创建时间
			}
			if(null != vo.getUpdateUid()){
				doc.addField("updateUid", vo.getUpdateUid());//修改UID
			}
			if(null != vo.getUpdateTime()){
				doc.addField("updateTime", sdf.format(vo.getUpdateTime()));//更新时间
			}
			
			TravelContent travelContent = travelContentService.getTravelContentByTid(vo.getId());
			if(null != travelContent){
				if(null != travelContent.getTravelDate()){
					doc.addField("travelDate", sdf.format(travelContent.getTravelDate()));//旅行时间
				}
				if(null != travelContent.getContent()){
					doc.addField("content", travelContent.getContent());//游记内容
				}
			}
			
			UserInfo userInfo = userInfoService.getUserInfoByUid(vo.getUid());
			doc.addField("user_email", userInfo.getEmail());
			if(null != userInfo.getName()){
				doc.addField("user_name", userInfo.getName());//名称
			}
			if(null != userInfo.getNickname()){
				doc.addField("user_nickname", userInfo.getNickname());//昵称
			}
			if(null != userInfo.getWeiboName()){
				doc.addField("user_weiboName", userInfo.getWeiboName());//微博账号
			}
			if(null != userInfo.getWechatName()){
				doc.addField("user_wechatName", userInfo.getWechatName());//微信账号
			}
			if(null != userInfo.getPresentAddress()){
				doc.addField("user_presentAddress", userInfo.getPresentAddress());//现住址
			}
			if(null != userInfo.getPreviousAddress()){
				doc.addField("user_previousAddress", userInfo.getPreviousAddress());//以前住址
			}
			if(null != userInfo.getUserIntro()){
				doc.addField("user_userIntro", userInfo.getUserIntro());//用户简介
			}
			
			
			UserPlanner userPlanner = userPlannerService.getUserPlannerByUid(vo.getUid());
			if(null != userPlanner){
				doc.addField("planner_targetCity", userPlanner.getTargetCity());
			}
			
			TravelInfoStat travelInfoStat = travelInfoStatService.getTravelInfoStatByTid(vo.getId());
			if(null != travelInfoStat){
				doc.addField("viewCnt", travelInfoStat.getViewCnt());
				doc.addField("likeCnt", travelInfoStat.getLikeCnt());
			}
			
			return doc;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
}
