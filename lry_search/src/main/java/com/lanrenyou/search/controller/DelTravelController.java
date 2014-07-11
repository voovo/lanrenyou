package com.lanrenyou.search.controller;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lanrenyou.search.controller.base.BaseController;
import com.lanrenyou.search.index.util.SolrUtil;


/**
 * 删除索引文件
 */
@Controller
@RequestMapping("/search_index")
public class DelTravelController extends BaseController {
	
	@Autowired
	private SolrUtil solrUtil;

	@RequestMapping("/delTravel")
	public void delTravel(HttpServletRequest request, HttpServletResponse response) {
		String password = request.getParameter("password");
		if (password != null && "zhonglixuntaqianbaidurencai".equals(password)) {
		} else {
			return;
		}
		SolrServer[] servers = null;
		String id = request.getParameter("id");
		try {
			servers = solrUtil.getLryServers();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		if (id != null && !"".equals(id)) {

			List<String> ids = new ArrayList<String>();
			String[] idArr = id.split(",");
			for (int i = 0; i < idArr.length; i++) {
				ids.add(idArr[i]);
			}
			if (ids.size() > 0) {
				List<List<String>> lists = assignServer(ids, servers.length);

				for (int i = 0; i < servers.length; i++) {
					if(lists.get(i).size()<=0) continue;
					try {
						servers[i].deleteById(lists.get(i));
						servers[i].commit();
					} catch (Exception e) {
						e.printStackTrace();
						logger.error("删除索引文件[id=" + id + "]出错了！");
					}
				}
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

	private List<List<String>> assignServer(List<String> list, int size) {
		ArrayList<List<String>> lists = new ArrayList<List<String>>(size);
		for (int i = 0; i < size; i++) {
			lists.add(new ArrayList<String>());
		}

		for (String id : list) {
			for (int i = 0; i < size; i++) {
				if (Long.valueOf(id) % size == i) {
					lists.get(i).add(id);
					break;
				}
			}
		}

		return lists;
	}

}
