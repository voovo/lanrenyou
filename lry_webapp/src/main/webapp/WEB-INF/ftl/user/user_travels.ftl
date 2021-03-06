<@page  title="我的游记-懒人游" description="规划师游记 策划师游记 自助游指南"  keywords="规划师游记 策划师游记 自助游指南" 
	assets=[
	"/resources/css/waper.css",
	"/resources/js/waper.js"
	]
	>

<!-- waper_box -->
<div class="waper_box">
    <h1 class="warper_tt yj_icon">我的游记</h1>
    
    <ul class="warper_tab clearfix">
    	<li><a href="/user/setting/info">个人设置</a></li>
        <li class="cur">我的游记</li>
        <li><a href="/user/${userInfo.id!''}/msg/list">消息中心 (<i>${headerLetterCnt!'0'}</i>)</a></li>
        <li><a href="/user/${userInfo.id!''}/fans/list">粉丝管理</a></li>
        <li><a href="/user/${userInfo.id!''}/collect/list">我的收藏</a></li>
    </ul>

	<#if travelInfoList?? && travelInfoList?size gt 0>
    <ul id="u_yj_list" class="waper_box">
        <!-- 我的游记列表循环开始 -->
        <#list travelInfoList as travelInfo>
        <#if infoMap?? && infoMap.get(travelInfo.id)??>
        	<#assign contentInfo = infoMap.get(travelInfo.id)>
        </#if>
        <li id="${travelInfo.id!''}">
        	<div class="u_yj_imgBox">
        	<span>
            <img class="u_yj_imgDetail" src="<#if contentInfo??>${contentInfo.get('src')!''}</#if>" alt="">
            </span>
            </div>
            <div class="u_yj_txtDetail">
                <h2><a href="/travel/${travelInfo.id}">${travelInfo.title!''}</a><#if travelInfo.status?? && travelInfo.status == 3><span class="red" >（审核未通过）</span></#if></h2>
                <#if infoMap?? && infoMap.get(travelInfo.id)??><#assign travelMsg = infoMap.get(travelInfo.id).get('info') /></#if>
                <p><@truncateChars value="${travelMsg!''}" length="104"/></p>
            </div>
            <div class="u_yj_info">
                <span class="ico icon_time">${travelInfo.createTime?string('yyyy-MM-dd')!''}</span>
                <span class="ico icon_eye"><#if travelVisitCntMap?? && travelVisitCntMap.get(travelInfo.id)??>${travelVisitCntMap.get(travelInfo.id)!''}<#else>0</#if></span>
                <a href="/travel/${travelInfo.id!''}/toEdit"><i class="ico icon_edit"></i>编辑</a>
                <a href="javascript:;" class="remove_yj"><i class="ico icon_del"></i>删除</a>
            </div>
        </li>
        </#list>
        <!-- 我的游记列表循环结束 -->
    </ul>
		<#if pageIter.totalPages?? && pageIter.totalPages gt 100>
	        <#assign totalPageCount = 100>
	    <#else>
	        <#assign totalPageCount = pageIter.totalPages!0>
	    </#if>
    	<@pageNav total="${totalPageCount!0}" current="${pageIter.page!0}" urlpattern="/user/${userInfo.id!''}/travelList/list?pageNo=%d"/>
    <#else>
    	<div class="no_result">还没有发布游记</div>
    </#if>

</div>

</@page>