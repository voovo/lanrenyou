<link href="/images/skin.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="/js/jquery.js" type="text/javascript"></script> 

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

-->
</style>

<script type="text/javascript">
$(function(){
	$("a[id='passBtn']").click(function(){
		var _tid = $(this).attr("tid");
	    $.ajax({
			url : "/audit/travel/pass?tid="+_tid,
			success : function(r){
				var _d = jQuery.parseJSON(r);
				if(_d.status == "y"){
					alert('操作成功'); 
				}   
			}   
		}); 
	});
	
	$("a[id='nopassBtn']").click(function(){
		var _tid = $(this).attr("tid");
	    $.ajax({
			url : "/audit/travel/nopass?tid="+_tid,
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
            <td width="66%" valign="middle">
            <form id="search_form" action="/audit/travel/list" method="get" >
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="5%"><div align="center"><img src="/img/tb.gif" width="16" height="16" /></div></td>
                <td width="95%" class="STYLE1"><span class="STYLE3">
                你当前的位置</span>：[管理后台]-[游记管理]
                <span style="margin-left:30px;">
                  <input type="text" placeholder="游记ID" name="queryTid" value="${queryTid!''}">
                </span>

                <span style="margin-left:30px;">
                  	状态：
                    <select name="sort" id="news_sort">
                      <option value="0">全部</option>
                      <option value="1" <#if queryStatus?? && queryStatus == 1>selected="selected"</#if>>已审核</option>
                      <option value="2" <#if queryStatus?? && queryStatus == 2>selected="selected"</#if>>已关闭</option>
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
        <td><table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="b5d6e6" >
          <thead>
          <tr>

            <td width="35%" height="26" background="/img/bg.gif" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">标题</span></div></td>
            <td width="10%" height="26" background="/img/bg.gif" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">城市</span></div></td>
            <td width="10%" height="26" background="/img/bg.gif" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">作者</span></div></td>
            <td width="15%" background="/img/bg.gif" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">创建时间</span></div></td>
            <td width="10%" background="/img/bg.gif" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">状态</span></div></td>
            <td  height="26" background="/img/bg.gif" bgcolor="#FFFFFF" class="STYLE1"><div align="center">基本操作</div></td>
          </tr>
          </thead>
          <tbody>
          <#if pageIter?? && pageIter.getData()??>
          <#list pageIter.getData() as travelInfo>
          <#if userInfoMap?? && userInfoMap.get(travelInfo.uid)?? >
          <#assign userInfo = userInfoMap.get(travelInfo.uid) />
          <tr>
            <td height="22" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1"><a target="_blank" href="http://www.lanrenyou.com/travel/${travelInfo.id!''}">${travelInfo.title!''}</a></span></div></td>
            <td height="22" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">${travelInfo.city!''}</span></div></td>
            <td height="22" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1"><a href="http://www.lanrenyou.com/user/${travelInfo.uid}" target="_blank">${userInfo.nickname!''}</a></span></div></td>
            <td bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">${travelInfo.updateTime?string('yyyy-MM-dd HH:mm:ss')}</span></div></td>
            <#if travelInfo.status??>
            	<#if travelInfo.status == -1 >
            		<#assign travelStatus = "删除" />
            	<#elseif travelInfo.status == 1>
            		<#assign travelStatus = "待审核" />
            	<#elseif travelInfo.status == 2>
            		<#assign travelStatus = "审核通过" />
            	<#elseif travelInfo.status == 3>
            		<#assign travelStatus = "审核不通过" />
            	</#if>
            </#if>
            <td bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">${travelStatus!''}</span></div></td>
            <td height="22" bgcolor="#FFFFFF">
            <#if travelInfo.status?? && travelInfo.status == 1>
            <div align="center"><img src="/img/del.gif" width="16" height="16" /><a href="javascript:;" id="nopassBtn" tid="${travelInfo.id}">不通过</a>&nbsp;&nbsp;<img src="/images/pic21.gif" width="16" height="16" /><a href="javascript:;" id="passBtn" tid="${travelInfo.id}">通过</a></div>
            </#if>
            </td>
          </tr>
          </#if>
          </#list>
          </#if>
          </tbody>
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
    <@pageNav total="${totalPageCount!0}" totalCount="${pageIter.totalCount!0}" current="${pageIter.page!0}" urlpattern="/audit/travel/list?pageNo=%d&queryTid=${queryTid!'-1'}&queryStatus=${queryStatus!-1}"/>
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
