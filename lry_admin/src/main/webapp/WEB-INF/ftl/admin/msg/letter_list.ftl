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
                <td width="95%" class="STYLE1"><span class="STYLE3">你当前的位置</span>：[管理后台]-[私信管理]
                  
                  <span style="margin-left:30px;">
                    <input type="text" placeholder="关键字/id">
                    <a href="javascript:;">搜索</a>
                  </span>

                </td>
              </tr>
            </table></td>

          </tr>
        </table></td>
        <td width="16"><img src="/img/tab_07.gif" width="16" height="30" /></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="8" background="/img/tab_12.gif">&nbsp;</td>
        <td><table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="b5d6e6">
          <tr>
            <td width="10%" height="26" background="/img/bg.gif" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">发送者</span></div></td>
            <td width="30%" height="26" background="/img/bg.gif" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">接收者</span></div></td>
            <td width="15%" height="26" background="/img/bg.gif" bgcolor="#FFFFFF" class="STYLE1"><div align="center">全部私信</div></td>
          </tr>
          <#if pageIter?? && pageIter.getData()?? && pageIter.getData()?size gt 0>
          <#list pageIter.getData() as data>
	          <#assign senderUid = data.get('sender_uid')/>
	          <#assign receiverUid = data.get('receiver_uid') />
	          <#if userInfoMap?? && userInfoMap.get(senderUid)??>
				<#assign sender = userInfoMap.get(senderUid) />
	          </#if>
	          <#if userInfoMap?? && userInfoMap.get(receiverUid)??>
	          	<#assign receiver = userInfoMap.get(receiverUid) />
	          </#if>
          	<td height="22" bgcolor="#FFFFFF">
          		<div align="center" class="STYLE1">
              		<div align="center"><a href="http://new.lanrenyou.com/user/${senderUid}" target="_blank"><#if sender??>${sender.name!''}</#if></a></div>
            	</div>
            </td>
            <td height="22" bgcolor="#FFFFFF">
          		<div align="center" class="STYLE1">
              		<div align="center"><a href="http://new.lanrenyou.com/user/${receiverUid}" target="_blank"><#if receiver??>${receiver.name!''}</#if></a></div>
            	</div>
            </td>
            <td height="22" bgcolor="#FFFFFF"><div align="center"><span class="STYLE4"><a href="/msg/showMsg?senderUid=${senderUid}&receiverUid=${receiverUid}">查看</a></span></div></td>
          </#list>
          </#if>
        </table></td>
        <td width="8" background="/img/tab_15.gif">&nbsp;</td>
      </tr>
    </table></td>
  </tr>
  <#if pageIter.totalPages?? && pageIter.totalPages gt 100>
        <#assign totalPageCount = 100>
    <#else>
        <#assign totalPageCount = pageIter.totalPages!0>
    </#if>
    <@pageNav total="${totalPageCount!0}" current="${pageIter.page!0}" urlpattern="/msg/list?pageNo=%d&queryUid=${queryUid!}"/>
</table>
  </div>


</html>
</body>

<#macro pageNav total = "1" current = "1" urlpattern="#">
    <#assign totalInt = total?number?int />
    <#assign currentInt = current?number?int />
    <#if totalInt lte 1>
        <#return />
    </#if>
<tr>
    <td height="35" background="/img/tab_19.gif">
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
	      <tr>
	        <td width="12" height="35"><img src="/img/tab_18.gif" width="12" height="35" /></td>
	        <td>
	        	<table width="100%" border="0" cellspacing="0" cellpadding="0">
	          		<tr>
	          			<td class="STYLE4">&nbsp;&nbsp;共有 120 条记录，当前第 1/10 页</td>
            			<td>
            				<table border="0" align="right" cellpadding="0" cellspacing="0">
                				<tr>
				    				<td width="40"><a href="${urlpattern?replace("%d", "1")}"><img src="/img/first.gif" width="37" height="15" />1</a></td>
							    	<#if currentInt gt 1>
							    	<td width="45"><a href="${urlpattern?replace("%d", "${currentInt - 1}")}"><img src="/img/back.gif" width="43" height="15" />${currentInt - 1}</a></td>
							    	</#if>
							    	<#if currentInt lt totalInt>
							    	<td width="45"><a href="${urlpattern?replace("%d", "${currentInt + 1}")}"><img src="/img/next.gif" width="43" height="15" />${currentInt + 1}</a></td>
							    	</#if>
							    	<td width="40"><a href="${urlpattern?replace("%d", "${totalInt}")}"><img src="/img/last.gif" width="37" height="15" />${totalInt}</a></td>
							    </tr>
							</table>
						</td>
			  		</tr>
				</table>
			</td>
			<td width="16"><img src="/img/tab_20.gif" width="16" height="35" /></td>
	      </tr>
      </table>
    </td>
</tr>
</#macro>