﻿<link href="/images/skin.css" rel="stylesheet" type="text/css" />
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
	
	$("a[id='nopassBtn']").click(function(){
		var _tid = $(this).attr("tid");
	    $.ajax({
			url : "/audit/index_data/remove?tid="+_tid,
			success : function(r){
				var _d = jQuery.parseJSON(r);
				if(_d.status == "y"){
					alert('操作成功'); 
				} else {
					alert(_d.info);
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
            <form id="search_form" action="/audit/index_data/list" method="get" >
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="5%"><div align="center"><img src="/img/tb.gif" width="16" height="16" /></div></td>
                <td width="95%" class="STYLE1"><span class="STYLE3">你当前的位置</span>：[管理后台]-[首页设置]
                  <span style="margin-left:30px;">
                    游记ID:<input type="text" placeholder="游记ID" name="queryTid" value=${queryTid!''}>
                  </span>
                
                  <span style="margin-left:30px;">
                 类别：
                    <select name="queryType" id="queryType">
                      <option value="0" selected="selected">已推荐</option>
                      <option value="1">未推荐</option>
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
        <td width="8" background="img/tab_12.gif">&nbsp;</td>
        <td><table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="b5d6e6">
          <tr>
            <td width="20%" height="26" background="/img/bg.gif" bgcolor="#FFFFFF" class="STYLE1"><div align="center">标题</div></td>
            <td width="15%" height="26" background="/img/bg.gif" bgcolor="#FFFFFF" class="STYLE1"><div align="center">城市</div></td>
            <td width="5%" height="26" background="/img/bg.gif" bgcolor="#FFFFFF" class="STYLE1"><div align="center">作者</div></td>
            <td width="14%" height="26" background="/img/bg.gif" bgcolor="#FFFFFF" class="STYLE1"><div align="center">创建时间</div></td>
            <td width="15%" height="26" background="/img/bg.gif" bgcolor="#FFFFFF" class="STYLE1"><div align="center">缩略图</div></td>
            <td width="15%" height="26" background="/img/bg.gif" bgcolor="#FFFFFF" class="STYLE1"><div align="center">操作</div></td>
          </tr>
          <#if pageIter?? && pageIter.getData()??>
          <#list pageIter.getData() as indexTravel >
          <#if travelInfoMap?? && travelInfoMap.get(indexTravel.tid)??>
          <#assign travelInfo = travelInfoMap.get(indexTravel.tid) />
          <tr>
            <td height="22" bgcolor="#FFFFFF"><div align="center" class="STYLE1">
              <div align="center"><a href="http://www.lanrenyou.com/travel/${travelInfo.id}" target="_blank">${travelInfo.title!''}</a></div>
            </div></td>
            <td height="22" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">${travelInfo.city!''}</span></div></td>
            <td height="22" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1"><a href="http://www.lanrenyou.com/user/${travelInfo.uid}" target="_blank">sheak</a></span></div></td>
            <td height="22" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">${travelInfo.updateTime?string('yyyy-MM-dd HH:mm:ss')}</span></div></td>
            <td height="22" bgcolor="#FFFFFF"><div align="center"><img height="230" src="<#if srcUrlMap?? && srcUrlMap.get(travelInfo.id)??><#if srcUrlMap.get(travelInfo.id)?contains("wp-content")>http://www.lanrenyou.com${srcUrlMap.get(travelInfo.id)}<#else>${srcUrlMap.get(travelInfo.id)}</#if></#if>" alt=""></div></td>
            <td height="22" bgcolor="#FFFFFF"><div align="center"><img src="/images/pic23.gif" width="16" height="16" /><a href="javascript:;" id="nopassBtn" tid="${travelInfo.id}">取消推荐</a></div></td>
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
    <#if pageIter??><#assign totalCount=pageIter.totalCount /></#if>
    <@pageNav total="${totalPageCount!0}" totalCount="${totalCount!0}" current="${pageIter.page!0}" urlpattern="/audit/index_data/list?pageNo=%d&queryTid=${queryTid!'0'}&queryType=${queryType!'0'}"/>
</table>
  </div>


</html>
</body>

<#macro pageNav total = "1" totalCount = "" current = "1" urlpattern="#">
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