
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="com.baidu.job.search.index.schedule.ExportAllWishs"%>
<%
	WebApplicationContext context= WebApplicationContextUtils.getWebApplicationContext(getServletContext());
ExportAllTravels exportAllWishs=context.getBean(ExportAllTravels.class);
exportAllWishs.executue();
%>