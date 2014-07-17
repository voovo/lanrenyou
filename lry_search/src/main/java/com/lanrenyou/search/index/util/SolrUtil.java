package com.lanrenyou.search.index.util;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;

public class SolrUtil {

	private String lryTravelServer;
	
	private String lryPlannerServer;

	public String getLryTravelServer() {
		return lryTravelServer;
	}

	public void setLryTravelServer(String lryTravelServer) {
		this.lryTravelServer = lryTravelServer;
	}

	public String getLryPlannerServer() {
		return lryPlannerServer;
	}

	public void setLryPlannerServer(String lryPlannerServer) {
		this.lryPlannerServer = lryPlannerServer;
	}

	public SolrServer[] getLryTravelServers() {
		String[] array=lryTravelServer.split(",");
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
	
	public SolrServer[] getLryPlannerServers() {
		String[] array=lryPlannerServer.split(",");
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
