<%
	response.setStatus(503)	;
response.setHeader("HTTP/1.1","503 Service Temporarily Unavailable");
	response.setHeader("Status","503 Service Temporarily Unavailable");
	response.setHeader("Retry-After","Fri, 19 Apr 2013 07:00:00 GMT+8");
%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
  <meta content="" name="description"/>
  <meta content="" name="keywords"/>
 <title>系统升级中_大街</title>
 <link href="http://assets.lanrenyouimg.com/images/favicon.ico" rel="shortcut icon">
 <style>
 body, div, p, a, span {
  margin: 0;
  padding: 0;
}
 body{
  background:url("http://assets.lanrenyouimg.com/up/404/img/404background.png") repeat-x scroll 0 0 ;
 }
.wrapper {
  width:960px;
  margin:0 auto;
  border-left:none;
  min-height: 400px;
  position:relative;
 }
.logo {
  width:123px;
  height:37px;
  margin-top:20px;
  background:url("http://assets.lanrenyouimg.com/up/404/img/logo.png") no-repeat scroll 0 bottom transparent;
  }
 .cloud{
  width:440px;
  height:179px;
  background:url("http://assets.lanrenyouimg.com/up/404/img/cloud.png") no-repeat scroll 0 top transparent;
  margin:15px auto 0px;
 }
.error-404 {
  width:818px;
  height:300px;
  background:url("http://assets.lanrenyouimg.com/up/404/img/404error-update.png") no-repeat scroll 0 bottom transparent;
  margin-left:70px;
 }
 .content{
  padding:15px 0 0 15px;
  }
.text{
  font-family:"微软雅黑";
  padding-top:60px;
  margin-left:80px;
  font-size:22px;
 }
.text-info{
  margin-left:80px;
  font-size:12px;
	color:#999;
	line-height:25px;
 }
.button {
  font-size: 14px;
	color:#333333;
  height: 30px;
  vertical-align: middle;
	display:inline-block;
  text-decoration:none;
  text-align:center;
  }
.guest{
  background:url("http://assets.lanrenyouimg.com/up/404/img/button1.png") no-repeat scroll 0 bottom transparent;
  width:95px;
  }
.report{
  background:url("http://assets.lanrenyouimg.com/up/404/img/button2.png") no-repeat scroll 0 bottom transparent;
  width:115px;
  margin-left:10px;
  }
 .button b {
  display:inline-block;
  font-weight: 400;
  line-height:30px;
  }
 .buttonwrap {
   font-size:0;
   margin-top:19px;
   margin-left:72px;
  }
</style>
</head>
 <body>
  <div class="wrapper">
  <div class="logo"></div>
  <div class="cloud">
    <div class="content">
     <div class="text">系统升级中，敬请期待…</div>
		<p class="text-info">预计将于凌晨5:00升级完成</p>
    </div>
   </div>
   <div class="error-404"></div>
  </div>
 </body>
</html>