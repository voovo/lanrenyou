<html>
<head>
<title>管理页面</title>
<script type="text/javascript">
function logout(){
	if (confirm("您确定要退出控制面板吗？"))
	top.location = "../index.html";
	return false;
}
</script>

<base target="main">
<link href="/images/skin.css" rel="stylesheet" type="text/css">
</head>
<body leftmargin="0" topmargin="0">
<table width="100%" height="64" border="0" cellpadding="0" cellspacing="0" class="admin_topbg">
  <tr>
    <td width="75%" height="64"><img src="/img/logo.png"> <span class="admin_top_tt">后台管理</span></td>
    <td width="25%" valign="middle"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="70%" height="38" class="admin_txt">用户：<b>${loginAdmin.name}</b> 级别：管理员</td>
        <td width="14%"><a href="#" target="_self" onClick="logout();"><img src="/images/out.gif" alt="安全退出" width="46" height="20" border="0"></a></td>
        <td width="8%">&nbsp;</td>
      </tr>

    </table></td>
  </tr>
</table>
</body>
</html>
