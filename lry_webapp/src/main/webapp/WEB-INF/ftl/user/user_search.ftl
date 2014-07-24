<@page  title="找规划师-懒人游" description=""  keywords="" 
	assets=[
	"/resources/css/waper.css",
	"/resources/js/waper.js"
	]
	>
	
<!-- waper_box -->
<div class="waper_box">
    <h1 class="warper_tt search_icon">搜索“${keyword!''}”的相关结果</h1>
    
    <ul class="warper_tab clearfix">
        <li><a href="javascript:;">游记</a></li>
        <li class="cur">规划师</li>
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
                    <span>游记 <a href="/user/${travelInfo.uid!''}/travelList"><#if userPublishedTravelCntMap?? && userPublishedTravelCntMap.get(userInfo.id)??>${userPublishedTravelCntMap.get(userInfo.id)!''}<#else>0</#if></a></span>

                    <a href="javascript:;" class="btn_s add_btn"></a>
                    <a href="javascript:;" class="btn_s msg_btn"></a>
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
                <p class="more_yj"><a href="/user/${userInfo.id!''}/travelList/list">查看全部游记</a></p>
                <div class="planner_yj_detail clearfix">
                    <div class="left">
                        <a href="#">
                            <img src="imgs/yj_detail_img.jpg" alt="">
                        </a>
                    </div>
                    <div class="right">
                        <ul>
                            <li><a href="#">【美国66号公路自驾全攻略】翻滚吧，青春！</a><span>2014-01-01</span></li>
                            <li><a href="#">美国梦东西海岸的浮光掠影</a><span>2014-01-01</span></li>
                            <li><a href="#">【美国66号公路自驾全攻略】翻滚吧，青春！</a><span>2014-01-01</span></li>
                            <li><a href="#">美国梦东西海岸的浮光掠影</a><span>2014-01-01</span></li>
                        </ul>
                    </div>
                </div>
            </div>
        </li>
        </#list>
    </ul>
    <#if pageIter.totalPages?? && pageIter.totalPages gt 100>
        <#assign totalPageCount = 100>
    <#else>
        <#assign totalPageCount = pageIter.totalPages!0>
    </#if>
    <@pageNav total="${totalPageCount!0}" current="${pageIter.page!0}" urlpattern="/user/search?pageNo=%d&keyword=${keyword!''}&city=${city!''}"/>
    <#else>
    <div class="no_result">对不起，未找到匹配内容，请更换搜索条件</div>
	</#if>
</div>

</@page>