<@page  title="找规划师-懒人游" description=""  keywords="" 
	assets=[
	"/resources/css/waper.css",
	"/resources/js/waper.js"
	]
	>
	
<!-- waper_box -->
<div class="waper_box">
    <h1 class="warper_tt yj_icon">找规划师
        <div class="planner_search">
        <form action="/user/search/list" method="GET">
            <input type="text" name="keyword" class="input_220">
            <input class="search_btn button" type="submit" value="搜索"/>
        </form>
        </div>
    </h1>
    
    <ul class="warper_tab clearfix">
        <li class="cur">热门规划师</li>
    </ul>


	<#if userInfoList?? && userInfoList?size gt 0>
	 <ul class="planner_list clearfix">
        <!-- 规划师列表循环开始 -->
        <#list userInfoList as userInfo >
        <li class="planner_item">
            <dl class="clearfix">
                <dt class="planner_face">
                    <img src="${userInfo.avatar!''}" alt="">
                </dt>
                <dd class="planner_name">
                    <span class="vip_ico"><a href="/user/${userInfo.id!''}">${userInfo.name!''}</a></span>
                </dd>
                <dd class="planner_info">
                	<span>关注 <#if starCntMap?? && starCntMap.get(userInfo.id)??>${starCntMap.get(userInfo.id)!''}<#else>0</#if></span>
                    <span>粉丝 <#if fansCntMap?? && fansCntMap.get(userInfo.id)??>${fansCntMap.get(userInfo.id)!''}<#else>0</#if></span>
                    <span>游记 <a href="/user/${userInfo.id!''}"><#if userPublishedTravelCntMap?? && userPublishedTravelCntMap.get(userInfo.id)??>${userPublishedTravelCntMap.get(userInfo.id)!''}<#else>0</#if></a></span>

                    <#if userStarMap?? && userStarMap.get(userInfo.id)??>
					<a href="javascript:;" class="btn_s added_btn" uid="${userInfo.id!''}"></a>
					<#else>
                    <a href="javascript:;" class="btn_s add_btn" uid="${userInfo.id!''}"></a>
					</#if>
                    <a href="javascript:;" class="btn_s msg_btn" uid="${userInfo.id!''}"></a>
                </dd>
            </dl>

			<#if targetCityMap?? && targetCityMap.get(userInfo.id)??>
            <dl class="plan_area clearfix">
                <dt>可策划地区：</dt>
                <dd>
                    <ul>
                    	<#list targetCityMap.get(userInfo.id) as city>
                        <li><a href="/travel/search?city=${city!''}">${city!''}</a></li>
                        </#list>
                    </ul>
                </dd>
            </dl>
            </#if>

            <!-- 规划师游记 -->
            <div class="planner_yj_list">
                <!-- ajax -->
            </div>
        </li>
        </#list>
    </ul>
    <#if pageIter.totalPages?? && pageIter.totalPages gt 100>
        <#assign totalPageCount = 100>
    <#else>
        <#assign totalPageCount = pageIter.totalPages!0>
    </#if>
    <@pageNav total="${totalPageCount!0}" current="${pageIter.page!0}" urlpattern="/user/search/list?pageNo=%d&keyword=${keyword!''}&city=${city!''}"/>
    <#else>
    <div class="no_result">对不起，未找到匹配内容，请更换搜索条件</div>
	</#if>
</div>

</@page>