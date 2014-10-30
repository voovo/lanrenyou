<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
   <head>
      <title>上传图片</title>
      <meta http-equiv="pragma" content="no-cache">
      <meta http-equiv="cache-control" content="no-cache">
      <meta http-equiv="expires" content="0">
      <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
      <meta http-equiv="description" content="This is my page">
	﻿<link href="/images/skin.css" rel="stylesheet" type="text/css" />
   </head>

   <body>
	<div class="right_bg">

	<form name="Webform" action="/audit/index_banner/add" method="post" enctype="multipart/form-data">
		Banner图片:<input type="file" name="upfile">
		<br />
		图片Alt:<input type="text" name="alt" />
		<br />
        Banner链接:<input type="text" name="linkUrl" />
		<br />
        <input type="submit" name="Submit" value="Submit Files" />
	</form>
	</div>
   </body>
</html>