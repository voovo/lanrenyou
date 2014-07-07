package com.lanrenyou.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.solr.client.solrj.SolrServer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lanrenyou.controller.base.BaseController;
import com.lanrenyou.search.index.ExportTravels;
import com.lanrenyou.search.index.util.SolrUtil;
import com.lanrenyou.travel.model.TravelInfo;

/**
 * 更新索引文件
 */
@Controller
@RequestMapping("/search_index")
public class UpdateTravelController extends BaseController {

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String password = request.getParameter("password");
		if (password != null && "zhonglixuntaqianbaidurencai".equals(password)) {
		} else {
			return;
		}
		ExportTravels exp = SpringTool.getBeanByClass(ExportTravels.class);
		SolrUtil solrUtil = SpringTool.getBeanByClass(SolrUtil.class);
		TravelInfoReository repo = SpringTool
				.getBeanByClass(TravelInfoReository.class);
		SolrServer[] servers = null;
		PrintWriter out = response.getWriter();
		String id = request.getParameter("id");
		try {
			servers = solrUtil.getLryServers();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		if (id != null && !"".equals(id)) {
			List<TravelInfo> wishs = new ArrayList<TravelInfo>();
			String[] idArr = id.split(",");
			for (int i = 0; i < idArr.length; i++) {
				List<TravelInfo> wish = repo.getCrWishApplyVos(
						"FROM CrWishApply WHERE id=" + idArr[i], null, null);
				if (wish != null && wish.size() > 0) {
					wishs.add(wish.get(0));
				} else {
					log.info("不存在这个id=[" + idArr[i] + "]的志愿");
					return;
				}
			}
			List<List<TravelInfo>> lists = assignServer(wishs, servers.length);

			for (int i = 0; i < servers.length; i++) {
				exp.export(servers[i], lists.get(i));
			}
			for (int i = 0; i < servers.length; i++) {
				try {
					servers[i].optimize();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		out.print("ok");
		out.flush();
		out.close();
	}

	private List<List<TravelInfo>> assignServer(List<TravelInfo> list,
			int size) {
		ArrayList<List<TravelInfo>> lists = new ArrayList<List<TravelInfo>>(
				size);
		for (int i = 0; i < size; i++) {
			lists.add(new ArrayList<TravelInfo>());
		}

		for (TravelInfo wish : list) {
			for (int i = 0; i < size; i++) {
				if (wish.getId() % size == i) {
					lists.get(i).add(wish);
					break;
				}
			}
		}

		return lists;
	}
}
