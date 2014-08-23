<@page  title="最新游记-懒人游" description=""  keywords="" 
	assets=[
	"/resources/css/waper.css",
	"/resources/js/waper.js",
	"/resources/js/search.js",
	"/resources/css/plug/float.css",
	"/resources/js/plug/jquery.imagesloaded.js",
	"/resources/js/plug/jquery.wookmark.js",
	"/resources/js/float.js"
	]
	>

<!-- waper_box -->
<div class="waper_box">
    <h1 class="warper_tt search_icon">搜索“${city!''} ${keyword!''}”的相关结果</h1>
    
    <ul class="warper_tab clearfix">
        <li class="cur">游记</li>
        <li><a href="/user/search/list?keyword=${keyword!''}&city=${city!''}">规划师</a></li>
    </ul>
    
    <div id="container">
    
    <ol id="sortbys">
      <!-- <li data-sortby="name">名称</li> -->
      <li data-sortby="time" class="active">发布时间</li>
      <li data-sortby="popularity">浏览量</li>
    </ol>

	<#if travelInfoList?? && travelInfoList?size gt 0>
	<div id="float_yj" role="main">

      <ul id="tiles">
        <!-- 游记列表循环开始 -->
        <#list travelInfoList as travelInfo>
        <#if userInfoMap??>
        	<#assign userInfo = userInfoMap.get(travelInfo.uid) />
        </#if>
        <li data-time="<#if travelInfo.createTime??>${travelInfo.createTime?string('yyyy-MM-dd')}</#if>" data-popularity="<#if travelVisitCntMap?? && travelVisitCntMap.get(travelInfo.id)??>${travelVisitCntMap.get(travelInfo.id)!''}<#else>0</#if>" data-name="${travelInfo.title!''}">
          <a href="/travel/${travelInfo.id!''}" target="_blank">
            <img class="float_img" src="<#if infoMap?? && infoMap.get(travelInfo.id)??>${infoMap.get(travelInfo.id).get('src')!''}</#if>" height="140" width="200">
            <h3 class="float_img_tt">
              <span>${travelInfo.title!''}</span>
            </h3>
          </a>
          <#if infoMap?? && infoMap.get(travelInfo.id)??><#assign travelMsg = infoMap.get(travelInfo.id).get('info') /></#if>
          <div class="float_img_info"><@truncateChars value="${travelMsg!''}" length="116"/></div>

          <div class="float_img_author clearfix">
            <img class="u_sm" src="${userInfo.avatar!''}" alt="" width="24" height="24">
            <span><a href="/user/${userInfo.id!''}">${userInfo.name!''}</a></span>
            <span class="ico icon_time"><#if travelInfo.createTime??>${travelInfo.createTime?string('yyyy-MM-dd')}</#if></span>
            <span class="ico icon_eye"><#if travelVisitCntMap?? && travelVisitCntMap.get(travelInfo.id)??>${travelVisitCntMap.get(travelInfo.id)!''}<#else>0</#if></span>
          </div>
        </li>
        </#list>
        <!-- 游记列表循环结束 -->
      </ul>
    </div>
    <#else>
    <div class="no_result">对不起，未找到匹配内容，请更换搜索条件</div>
	</#if>
</div>

</@page>