<@page  title="粉丝管理-懒人游" description=""  keywords="" 
	assets=[
	"/resources/css/waper.css",
	"/resources/js/fans.js"
	]
	>

<!-- waper_box -->
<div class="waper_box">
    <h1 class="warper_tt fans_icon">粉丝管理</h1>
    
    <ul class="warper_tab clearfix">
        <li><a href="/user/${userInfo.id!''}/info">个人设置</a></li>
        <li><a href="/user/${userInfo.id!''}/travelList/list">我的游记</a></li>
        <li><a href="/user/${userInfo.id!''}/msg/list">消息中心 (<i>${headerLetterCnt!'0'}</i>)</a></li>
        <li class="cur">粉丝管理</li>
        <li><a href="/user/${userInfo.id!''}/collect/list">我的收藏</a></li>
    </ul>
    
    <ul id="u_fans_tabs" class="clearfix waper_box">
        <li><a class="btn_s blue_btn" href="javascript:;">全部关注(1206)</a></li>
        <li><a class="btn_s gray_btn" href="javascript:;">我关注的人(127)</a></li>
    </ul>

	<#if uidList?? && uidList?size gt 0>
    <ul id="u_fans_list" class="clearfix">
        <!-- 粉丝管理列表循环开始 -->
        <#list uidList as uid>
        <li>
            <div class="u_fans_box clearfix">
                <img class="left" src="<#if userInfoMap?? && userInfoMap.get(uid)??>${userInfoMap.get(uid).avatar!''}</#if>" alt="">
                <div class="left">
                    <p><span class="vip_ico"><a href="/user/${uid!''}"><#if userInfoMap?? && userInfoMap.get(uid)??>${userInfoMap.get(uid).name!''}</#if></a></span></p>
                    <#if starMap?? && starMap.get(uid)??>
                    	<p class="u_fans_links"><a href="javascript:;" class="ico addeach_icon">已互听</a> | <a href="#">取消关注</a></p>
                    <#else>
                    	<p class="u_fans_links"><a href="javascript:;" class="ico add_icon">关注</a></p>
                    </#if>
                </div>
            </div>
            <p class="u_fans_author_info">简介：${userInfoMap.get(uid).intro!''}</p>
        </li>
        </#list>
        <!-- 粉丝管理列表循环结束 -->
    </ul>

    <#if pageIter.totalPages?? && pageIter.totalPages gt 100>
        <#assign totalPageCount = 100>
    <#else>
        <#assign totalPageCount = pageIter.totalPages!0>
    </#if>
    <@pageNav total="${totalPageCount!0}" current="${pageIter.page!0}" urlpattern="/user/${userInfo.id!''}/fans/list?pageNo=%d"/>
    <#else>
    	<div class="no_result">还没有关注用户</div>
    </#if>

</div>


</@page>