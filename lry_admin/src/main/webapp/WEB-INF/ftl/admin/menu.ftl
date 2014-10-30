<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>管理页面</title>

<script src="/js/jquery.js" type="text/javascript"></script>
<script>
    $(function(){
        $(".type").click(function(){
            $(this).addClass("cur").siblings('.type').removeClass("cur");
        });
    })
</script>


<style>
body {
	font:12px Arial, Helvetica, sans-serif;
	color: #000;
	background-color: #282828;
	margin: 0px;
}
#container {
	width: 270px;
    margin-top: 20px;
}

div.type {
	font-size: 16px;
	margin: 0px;
	width: 270px;
	cursor: pointer;
	height: 42px;
	line-height: 42px;	
    margin-bottom: 2px;
    font-family: "微软雅黑";
}
div.type a {
	display: block;
	width: 160px;
	color: #fff;
	height: 42px;
	text-decoration: none;
	moz-outline-style: none;
	background: #282828;
	line-height: 42px;
	text-align: left;
	margin: 0px;
	padding: 0 0 0 110px;
}
#container .type.cur a , #container .type a:hover{ background-color: #0b54a3;}
.content{
	width: 182px;
	height: 26px;
	
}
.MM ul {
	list-style-type: none;
	margin: 0px;
	padding: 0px;
	display: block;
}
.MM li {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 12px;
	line-height: 26px;
	color: #333333;
	list-style-type: none;
	display: block;
	text-decoration: none;
	height: 26px;
	width: 182px;
	padding-left: 0px;
}
.MM {
	width: 182px;
	margin: 0px;
	padding: 0px;
	left: 0px;
	top: 0px;
	right: 0px;
	bottom: 0px;
	clip: rect(0px,0px,0px,0px);
}
.MM a:link {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 12px;
	line-height: 26px;
	color: #333333;
	background-image: url(/images/menu_bg1.gif);
	background-repeat: no-repeat;
	height: 26px;
	width: 182px;
	display: block;
	text-align: center;
	margin: 0px;
	padding: 0px;
	overflow: hidden;
	text-decoration: none;
}
.MM a:visited {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 12px;
	line-height: 26px;
	color: #333333;
	background-image: url(/images/menu_bg1.gif);
	background-repeat: no-repeat;
	display: block;
	text-align: center;
	margin: 0px;
	padding: 0px;
	height: 26px;
	width: 182px;
	text-decoration: none;
}
.MM a:active {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 12px;
	line-height: 26px;
	color: #333333;
	background-image: url(/images/menu_bg1.gif);
	background-repeat: no-repeat;
	height: 26px;
	width: 182px;
	display: block;
	text-align: center;
	margin: 0px;
	padding: 0px;
	overflow: hidden;
	text-decoration: none;
}
.MM a:hover {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 12px;
	line-height: 26px;
	font-weight: bold;
	color: #006600;
	background-image: url(/images/menu_bg2.gif);
	background-repeat: no-repeat;
	text-align: center;
	display: block;
	margin: 0px;
	padding: 0px;
	height: 26px;
	width: 182px;
	text-decoration: none;
}
</style>
</head>

<body>
<table width="100%" height="280" border="0" cellpadding="0" cellspacing="0" bgcolor="#282828">
  <tr>
    <td width="270" valign="top"><div id="container">
      <div class="type cur"><a style="background-image:url(/images/admin_index_left_icon.png); background-position: 60px 2px; background-repeat: no-repeat;" href="/admin/main" target="main">管理首页</a></div>
      <div class="type"><a style="background-image:url(/images/admin_index_left_icon.png); background-position: 60px -126px; background-repeat: no-repeat;" href="/audit/index_data/list" target="main">首页设置</a></div>
      <div class="type"><a style="background-image:url(/images/admin_index_left_icon.png); background-position: 60px -126px; background-repeat: no-repeat;" href="/audit/index_banner/list" target="main">Banner设置</a></div>
      <div class="type"><a style="background-image:url(/images/admin_index_left_icon.png); background-position: 60px -42px; background-repeat: no-repeat;" href="/msg/list" target="main">私信管理</a></div>
      <div class="type"><a style="background-image:url(/images/admin_index_left_icon.png); background-position: 60px -84px; background-repeat: no-repeat;" href="/audit/travel/list" target="main">游记列表</a></div>
      <div class="type"><a style="background-image:url(/images/admin_index_left_icon.png); background-position: 60px -166px; background-repeat: no-repeat;" href="/audit/planner/list" target="main">规划师管理</a></div>
      <div class="type"><a style="background-image:url(/images/admin_index_left_icon.png); background-position: 60px -206px; background-repeat: no-repeat;" href="/admin/login/to_modify_password" target="main">修改密码</a></div>
      <div class="type"><a style="background-image:url(/images/admin_index_left_icon.png); background-position: 60px -248px; background-repeat: no-repeat;" href="/user/list" target="main">用户管理</a></div>
      </div>
    </td>
  </tr>
</table>
</body>
</html>
