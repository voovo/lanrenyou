<@page  title="我的收藏-懒人游" description=""  keywords="" 
	assets=[
	"/resources/css/waper.css",
	"/resources/js/waper.js"
	]
	>

<!-- waper_box -->
<div class="waper_box">
    <h1 class="warper_tt fav_icon">我的收藏</h1>
    
    <ul class="warper_tab clearfix">
        <li><a href="/user/${userInfo.id!''}/info">个人设置</a></li>
        <li><a href="/user/${userInfo.id!''}/travelList/list">我的游记</a></li>
        <li><a href="/user/${userInfo.id!''}/msg/list">消息中心 (<i>12</i>)</a></li>
        <li><a href="/user/${userInfo.id!''}/fans/list">粉丝管理</a></li>
        <li class="cur">我的收藏</li>
    </ul>
	
	<#if tidList?? && tidList?size gt 0>
    <ul id="u_yj_list" class="waper_box">
        <!-- 我的收藏列表循环开始 -->
        <#list tidList as tid>
        <#if travelInfoMap?? && travelInfoMap.get(tid)??>
        <#if infoMap?? && infoMap.get(tid)??>
        	<#assign contentInfo = infoMap.get(tid)>
        </#if>
        <li>
            <img class="u_yj_imgDetail" src="<#if contentInfo??>${contentInfo.get('src')!''}</#if>" alt="">
            <div class="u_yj_txtDetail">
                <h2><a href="/travel/${tid}">${travelInfoMap.get(tid).title!''}</a></h2>
                <#if infoMap?? && infoMap.get(tid)??><#assign travelMsg = infoMap.get(tid).get('info') /></#if>
                <p><@truncateChars value="${travelMsg!''}" length="104"/></p>
                <div class="u_yj_author">
                	<#if userInfoMap?? && userInfoMap.get(travelInfoMap.get(tid).uid)??>
                		<#assign author = userInfoMap.get(travelInfoMap.get(tid).uid) />
                	</#if>
                    <img src="<#if author??>${author.avatar!''}</#if>" alt="">
                    <a href="<#if author??>${author.id!''}</#if>"><#if author??>${author.name!''}</#if></a>
                </div>
            </div>
            <div class="u_yj_info">
                <span class="ico icon_time">${travelInfoMap.get(tid).createTime?string('yyyy-MM-dd')!''}</span>
                <span class="ico icon_eye"><#if travelVisitCntMap?? && travelVisitCntMap.get(travelInfo.id)??>${travelVisitCntMap.get(travelInfo.id)!''}<#else>0</#if></span>
                <a href="/travel/${tid!''}/uncollect"><i class="ico icon_del"></i>删除</a>
            </div>
        </li>
        </#if>
        </#list>
        <!-- 我的收藏列表循环结束 -->
    </ul>
    <#if pageIter.totalPages?? && pageIter.totalPages gt 100>
        <#assign totalPageCount = 100>
    <#else>
        <#assign totalPageCount = pageIter.totalPages!0>
    </#if>
    <@pageNav total="${totalPageCount!0}" current="${pageIter.page!0}" urlpattern="/user/${userInfo.id!''}/collect/list?pageNo=%d"/>
    <#else>
    	<div class="no_result">还没有收藏游记</div>
    </#if>

</div>
</@page>