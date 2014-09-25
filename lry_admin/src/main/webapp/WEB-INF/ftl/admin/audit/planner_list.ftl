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
<script src="/js/third-party/jquery-1.10.2.min.js" type="text/javascript"></script>
<script type="text/javascript">
$(function(){
	$("a[id='passBtn']").click(function(){
		var _uid = $(this).attr("uid");
	    $.ajax({
			url : "/audit/planner/pass?uid="+_uid,
			success : function(r){
				var _d = jQuery.parseJSON(r);
				if(_d.status == "y"){
					alert('操作成功'); 
				}   
			}   
		}); 
	});
	
	$("a[id='nopassBtn']").click(function(){
		var _uid = $(this).attr("uid");
	    $.ajax({
			url : "/audit/planner/nopass?uid="+_uid,
			success : function(r){
				var _d = jQuery.parseJSON(r);
				if(_d.status == "y"){
					alert('操作成功'); 
				}   
			}   
		}); 
	});
});
</script>

<body>

  <div class="right_bg">
    
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="30" background="/img/tab_05.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="12" height="30"><img src="/img/tab_03.gif" width="12" height="30" /></td>
        <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="46%" valign="middle">
            <form id="search_form" action="/audit/planner/list" method="get" >
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="5%"><div align="center"><img src="/img/tb.gif" width="16" height="16" /></div></td>
                <td width="95%" class="STYLE1"><span class="STYLE3">你当前的位置</span>：[管理后台]-[规划师管理]
                
                <span style="margin-left:30px;">
       规划师ID：          <input type="text" placeholder="UID" name="queryUid" value=${queryUid!''}>
                  </span>

                  <span style="margin-left:30px;">
                  状态：
                    <select name="queryStatus" id="news_sort" >
                      <option value="-1">全部</option>
                      <option value="1" <#if queryStatus?? && queryStatus == 1>selected="selected"</#if>>已审核</option>
                      <option value="2" <#if queryStatus?? && queryStatus == 2>selected="selected"</#if>>待审核</option>
                    </select>
                  </span>
                  
                  <span style="margin-left:30px;">
                    <input type="submit" value="搜索" />
                  </span>

                </td>
              </tr>
            </table>
            </form>
            </td>

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
            <td width="10%" height="26" background="/img/bg.gif" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">规划师</span></div></td>
            <td width="14%" height="26" background="/img/bg.gif" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">邮箱</span></div></td>
            <td width="20%" height="26" background="/img/bg.gif" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">可策划地</span></div></td>
            <td width="5%" height="26" background="/img/bg.gif" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">价格</span></div></td>
            <td width="14%" height="26" background="/img/bg.gif" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">创建时间</span></div></td>
            <td width="5%" height="26" background="/img/bg.gif" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">状态</span></div></td>
            <td width="15%" height="26" background="/img/bg.gif" bgcolor="#FFFFFF" class="STYLE1"><div align="center">基本操作</div></td>
          </tr>
          <#if pageIter?? && pageIter.getData()??>
          <#list pageIter.getData() as userPlanner>
          <#assign uid = userPlanner.uid />
          <#if userInfoMap?? && userInfoMap.get(uid)??>
          <#assign userInfo = userInfoMap.get(uid) />
          <tr>
            <td height="22" bgcolor="#FFFFFF"><div align="center" class="STYLE1">
              <a href="http://www.lanrenyou.com/user/${userInfo.id!''}" target="_blank" title="${userInfo.userIntro!''}">${userInfo.name!''}</a>
            </div></td>
            <td height="22" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">${userInfo.email!''}</span></div></td>
            <td height="22" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">${userPlanner.targetCity!''}</span></div></td>
            <td height="22" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">${userPlanner.fees!''}</span></div></td>
            <td height="22" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">${userPlanner.createTime?string('yyyy-MM-dd')}</span></div></td>
            <#assign plannerStatus='' />
            <#if userPlanner.status??>
	            <#if userPlanner.status == 1>
	            	<#assign plannerStatus = "待审核" />
	            <#elseif userPlanner.status == 2>
	            	<#assign plannerStatus = "审核通过" />
	            <#elseif userPlanner.status == 3>
	            	<#assign plannerStatus = "审核不通过" />
	            </#if>
            <#else>
            	<#assign plannerStatus = "待审核" />
            </#if>
            <td height="22" bgcolor="#FFFFFF"><span class="STYLE1">${plannerStatus!''}</span></td>
            <td height="22" bgcolor="#FFFFFF"><div align="center"><span class="STYLE4"><a href="javascript:;" id="passBtn" uid="${uid}"><img src="/images/pic21.gif" width="16" height="16" />通过</a>&nbsp; &nbsp; &nbsp;<a href="javascript:;" id="nopassBtn" uid="${uid}"><img src="/images/pic22.gif" width="16" height="16" />拒绝</a></span></div></td>
          </tr>
          </#if>
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
    <@pageNav total="${totalPageCount!0}" totalCount="${pageIter.totalCount!0}" current="${pageIter.page!0}" urlpattern="/audit/planner/list?pageNo=%d&queryUid=${queryUid!'-1'}&queryStatus=${queryStatus!-1}"/>
</table>
  </div>


</html>
</body>


<#macro pageNav total = "1" totalCount="" current = "1" urlpattern="#">
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
	          			<td class="STYLE4">&nbsp;&nbsp;共有 ${totalCount} 条记录，当前第 ${currentInt}/${totalInt} 页</td>
            			<td>
            				<table border="0" align="right" cellpadding="0" cellspacing="0">
                				<tr>
				    				<td width="40"><a href="${urlpattern?replace("%d", "1")}"><img src="/img/first.gif" width="37" height="15" /></a></td>
							    	<#if currentInt gt 1>
							    	<td width="45"><a href="${urlpattern?replace("%d", "${currentInt - 1}")}"><img src="/img/back.gif" width="43" height="15" /></a></td>
							    	</#if>
							    	<#if currentInt lt totalInt>
							    	<td width="45"><a href="${urlpattern?replace("%d", "${currentInt + 1}")}"><img src="/img/next.gif" width="43" height="15" /></a></td>
							    	</#if>
							    	<td width="40"><a href="${urlpattern?replace("%d", "${totalInt}")}"><img src="/img/last.gif" width="37" height="15" /></a></td>
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