package com.lanrenyou.search.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lanrenyou.search.index.ExportPlanners;
import com.lanrenyou.search.index.util.SolrUtil;
import com.lanrenyou.user.model.UserPlanner;
import com.lanrenyou.user.service.IUserPlannerService;

/**
 * 更新索引文件
 */
@Controller
@RequestMapping("/search_index/planner")
public class UpdatePlannerController {
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private SolrUtil solrUtil;
	
	@Autowired
	private ExportPlanners exportPlanners;
	
	@Autowired
	private IUserPlannerService userPlannerService;

	@RequestMapping("/update")
	public void update(HttpServletResponse response,
			@RequestParam(value = "password", required = true) String password,
			@RequestParam(value = "uids", required = true) String uids)
			throws ServletException, IOException {
		if (password != null && "woailanrenyou".equals(password)) {
		} else {
			return;
		}
		SolrServer[] servers = null;
		try {
			servers = solrUtil.getLryPlannerServers();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		if (StringUtils.isNotBlank(uids)) {
			List<UserPlanner> planners = new ArrayList<UserPlanner>();
			String[] idArr = uids.split(",");
			for (int i = 0; i < idArr.length; i++) {
				Integer uid = Integer.parseInt(idArr[i]);
				UserPlanner userPlanner = userPlannerService.getUserPlannerByUid(uid);
				if (null != userPlanner) {
					planners.add(userPlanner);
				} else {
					logger.info("不存在这个id=[" + idArr[i] + "]的规划师");
					return;
				}
			}
			List<List<UserPlanner>> lists = assignServer(planners, servers.length);

			for (int i = 0; i < servers.length; i++) {
				exportPlanners.export(servers[i], lists.get(i));
			}
			for (int i = 0; i < servers.length; i++) {
				try {
					servers[i].optimize();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	private List<List<UserPlanner>> assignServer(List<UserPlanner> list,
			int size) {
		ArrayList<List<UserPlanner>> lists = new ArrayList<List<UserPlanner>>(
				size);
		for (int i = 0; i < size; i++) {
			lists.add(new ArrayList<UserPlanner>());
		}

		for (UserPlanner planner : list) {
			for (int i = 0; i < size; i++) {
				if (planner.getId() % size == i) {
					lists.get(i).add(planner);
					break;
				}
			}
		}
		return lists;
	}
}
