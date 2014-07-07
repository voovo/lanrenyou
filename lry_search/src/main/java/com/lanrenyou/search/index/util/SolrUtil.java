package com.lanrenyou.search.index.util;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.springframework.stereotype.Component;

public class SolrUtil {

	private String lry_server;

	public void setLryServers(String campusServer) {
		lry_server = campusServer;
	}

	public SolrServer[] getLryServers() {
		String[] array=lry_server.split(",");
		CommonsHttpSolrServer[] servers = new CommonsHttpSolrServer[array.length];
		
		for(int i=0;i<array.length;i++){
			try {
				servers[i] = new CommonsHttpSolrServer(array[i]);
				servers[i].setConnectionTimeout(100); // 1/10th sec
				servers[i].setDefaultMaxConnectionsPerHost(100);
				servers[i].setMaxTotalConnections(100);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return servers;
	}

}
