<link href="/images/skin.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />


<style type="text/css">
<!--
body {
    margin-left: 0px;
    margin-top: 0px;
    margin-right: 0px;
    margin-bottom: 0px;
}
.STYLE1 {font-size: 12px}
.STYLE3 {font-size: 12px; font-weight: bold; }
.STYLE4 {
    color: #03515d;
    font-size: 12px;
    position: relative;
}
.STYLE4 img{ position: absolute; margin-left: -20px;}
-->
</style>


<body>

  <div class="right_bg">
    
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="30" background="/img/tab_05.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="12" height="30"><img src="/img/tab_03.gif" width="12" height="30" /></td>
        <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="46%" valign="middle"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="5%"><div align="center"><img src="/img/tb.gif" width="16" height="16" /></div></td>
                <td width="95%" class="STYLE1"><span class="STYLE3">你当前的位置</span>：[管理后台]-[<a href="/msg/list">私信管理</a>]-[${sender.name!''}与${receiver.name!''}间的全部私信]
                </td>
              </tr>
            </table></td>

          </tr>
        </table></td>
        <td width="16"><img src="img/tab_07.gif" width="16" height="30" /></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="8" background="/img/tab_12.gif">&nbsp;</td>
        <td><table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="b5d6e6">
          <tr>
            <td width="10%" height="26" background="/img/bg.gif" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">用户</span></div></td>
            <td width="50%" height="26" background="/img/bg.gif" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">内容</span></div></td>
            <td width="10%" height="26" background="/img/bg.gif" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">日期</span></div></td>
          </tr>
          <#if letterList??>
          <#list letterList as letter >
	          <tr>
	            <td height="22" bgcolor="#FFFFFF"><div align="center" class="STYLE1">
	              <div align="center"><#if userMap.get(letter.senderUid)??>${userMap.get(letter.senderUid).name!''}</#if></div>
	            </div></td>
	            <td height="22" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1"><a href="detail.html">${letter.content!''}</a></span></div></td>
	            <td height="22" bgcolor="#FFFFFF"><div align="center" class="STYLE1">${letter.updateTime?string('yyyy-MM-dd HH:mm:ss')}</div></td>
	          </tr>
          </#list>
          </#if>
        </table></td>
        <td width="8" background="/img/tab_15.gif">&nbsp;</td>
      </tr>
    </table></td>
  </tr>
</table>
  </div>


</html>
</body>
