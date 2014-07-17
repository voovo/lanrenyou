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

import com.lanrenyou.search.index.ExportTravels;
import com.lanrenyou.search.index.util.SolrUtil;
import com.lanrenyou.travel.model.TravelInfo;
import com.lanrenyou.travel.service.ITravelInfoService;

/**
 * 更新索引文件
 */
@Controller
@RequestMapping("/search_index/travel")
public class UpdateTravelController {
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private SolrUtil solrUtil;
	
	@Autowired
	private ExportTravels exportTravels;
	
	@Autowired
	private ITravelInfoService travelInfoService;

	@RequestMapping("/update")
	public void update(HttpServletResponse response,
			@RequestParam(value = "password", required = true) String password,
			@RequestParam(value = "tids", required = true) String tids)
			throws ServletException, IOException {
		if (password != null && "woailanrenyou".equals(password)) {
		} else {
			return;
		}
		SolrServer[] servers = null;
		try {
			servers = solrUtil.getLryTravelServers();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		if (StringUtils.isNotBlank(tids)) {
			List<TravelInfo> travls = new ArrayList<TravelInfo>();
			String[] idArr = tids.split(",");
			for (int i = 0; i < idArr.length; i++) {
				Integer tid = Integer.parseInt(idArr[i]);
				TravelInfo travelInfo = travelInfoService.getTravelInfoById(tid);
				if (null != travelInfo) {
					travls.add(travelInfo);
				} else {
					logger.info("不存在这个id=[" + idArr[i] + "]的游记");
					return;
				}
			}
			List<List<TravelInfo>> lists = assignServer(travls, servers.length);

			for (int i = 0; i < servers.length; i++) {
				exportTravels.export(servers[i], lists.get(i));
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

	private List<List<TravelInfo>> assignServer(List<TravelInfo> list,
			int size) {
		ArrayList<List<TravelInfo>> lists = new ArrayList<List<TravelInfo>>(
				size);
		for (int i = 0; i < size; i++) {
			lists.add(new ArrayList<TravelInfo>());
		}

		for (TravelInfo travel : list) {
			for (int i = 0; i < size; i++) {
				if (travel.getId() % size == i) {
					lists.get(i).add(travel);
					break;
				}
			}
		}
		return lists;
	}
}
