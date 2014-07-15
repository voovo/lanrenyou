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
import com.lanrenyou.travel.service.ITravelContentService;
import com.lanrenyou.user.model.UserInfo;
import com.lanrenyou.user.model.UserPlanner;
import com.lanrenyou.user.service.IUserInfoService;


/**
 * 规划师导出为索引
 */
@Component
public class ExportPlanners {
	
	@Autowired
	private IUserInfoService userInfoService;
	
	private Log log = LogFactory.getLog(ExportPlanners.class);
	
	private boolean isRunning = false;

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

	public void export(SolrServer server,List<UserPlanner> list) {
		if (isRunning) {
			log.info("export Planner is running.....");
			return;
		}
		try {
			isRunning = true;
			for (UserPlanner p : list) {
				SolrInputDocument doc = convertDoc(p);
				if (doc == null)
					continue;
				try {
					server.add(doc);
				} catch (Exception e) {
					log.error("id=" + p.getId()	+ "的Planner记录索引建立失败的!!异常信息：" + e.getMessage());
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

	private SolrInputDocument convertDoc(UserPlanner vo) {
		SolrInputDocument doc = new SolrInputDocument();
		try {
			//规划师表信息
			doc.addField("pid", vo.getId());//规划师ID
			doc.addField("targetCity", vo.getTargetCity());//可规划地市
			doc.addField("uid", vo.getUid());//用户UID
			doc.addField("price", vo.getPrice());//规划价格
			doc.addField("chargeMode", vo.getChargeMode());//收费模式[1:按天; 2:按周; 3:按次;]
			doc.addField("createUid", vo.getStatus());//创建者
			if(null != vo.getCreateTime()){
				doc.addField("createTime", sdf.format(vo.getCreateTime()));//创建时间
			}
			if(null != vo.getUpdateUid()){
				doc.addField("updateUid", vo.getUpdateUid());//修改UID
			}
			if(null != vo.getUpdateTime()){
				doc.addField("updateTime", sdf.format(vo.getUpdateTime()));//更新时间
			}
			
			UserInfo userInfo = userInfoService.getUserInfoByUid(vo.getId());
			if(null != userInfo){
				if(null != userInfo.getName()){
					doc.addField("name", userInfo.getName());//名称
				}
				doc.addField("email", userInfo.getEmail());//游记内容
				if(null != userInfo.getNickname()){
					doc.addField("nickname", userInfo.getNickname());//昵称
				}
				if(null != userInfo.getWeiboName()){
					doc.addField("weiboName", userInfo.getWeiboName());//微博账号
				}
				if(null != userInfo.getWechatName()){
					doc.addField("wechatName", userInfo.getWechatName());//微信账号
				}
				if(null != userInfo.getPresentAddress()){
					doc.addField("presentAddress", userInfo.getPresentAddress());//现住址
				}
				if(null != userInfo.getPreviousAddress()){
					doc.addField("previousAddress", userInfo.getPreviousAddress());//以前住址
				}
				if(null != userInfo.getUserIntro()){
					doc.addField("userIntro", userInfo.getUserIntro());//用户简介
				}
			}
			
			return doc;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
}
