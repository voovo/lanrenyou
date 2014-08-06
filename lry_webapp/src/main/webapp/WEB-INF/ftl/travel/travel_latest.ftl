<@page  title="最新游记-懒人游" description=""  keywords="" 
	assets=[
	"/resources/css/waper.css",
	"/resources/js/waper.js"
	]
	>

<!-- waper_box -->
<div class="waper_box">
    <h1 class="warper_tt yj_icon">找游记</h1>
    
    <ul class="warper_tab clearfix">
        <li><a href="/travel/search/hot">热门游记</a></li>
        <li class="cur">最新游记</li>
    </ul>

    <#if travelInfoList?? && travelInfoList?size gt 0>
    <ul class="yj_list clearfix">
        <!-- 游记列表循环开始 -->
        <#list travelInfoList as travelInfo>
        <#if userInfoMap??>
        	<#assign userInfo = userInfoMap.get(travelInfo.uid) />
        </#if>
        <li class="yj_item">
            <dl>
                <dt>
                	<a href="/travel/${travelInfo.id!''}" target="_blank">
                    <h2>${travelInfo.title!''}</h2>
                    <div class="yj_author">
                        <img class="u_sm" src="${userInfo.avatar!''}" alt="" width="24" height="24">
                        <i class="author_link" href="javascript:;"><a href="/user/${userInfo.id!''}">${userInfo.name!''}</a>
                            <div class="author_info hide">
                                <div class="author_tt">
                                    <i class="ico up_ico"></i>
                                </div>

                                <div class="author_detail clearfix">
                                    <dl>
                                        <dt><img src="${userInfo.avatar!''}" alt=""></dt>
                                        <dt class="add_form">
                                        	<#assign isShow = true />
						                    <#if loginUser?? && loginUser.id == userInfo.id>
						                    	<#assign isShow = false />
						                    </#if>
						                    <#if isShow>
                                            <#if userStarMap?? && userStarMap.get(userInfo.id)??>
											<a href="javascript:;" class="added_btn" uid="${userInfo.id!''}"></a>
											<#else>
							                <a href="javascript:;" class="add_btn" uid="${userInfo.id!''}"></a>
											</#if>
											</#if>
                                        </dt>
                                        <dd>
                                            <span class="vip_ico"><a href="/user/${userInfo.id!''}">${userInfo.name!''}</a></span>
                                            <p>
                                                <span>关注 <#if starCntMap?? && starCntMap.get(travelInfo.uid)??>${starCntMap.get(travelInfo.uid)!''}<#else>0</#if></span>
                                                <span>粉丝 <#if fansCntMap?? && fansCntMap.get(travelInfo.uid)??>${fansCntMap.get(travelInfo.uid)!''}<#else>0</#if></span>
                                                <span>游记 <a href="/user/${travelInfo.uid!''}"><#if userPublishedTravelCntMap?? && userPublishedTravelCntMap.get(travelInfo.uid)??>${userPublishedTravelCntMap.get(travelInfo.uid)!''}<#else>0</#if></a></span>
                                            </p>
                                            
                                        </dd>
                                    </dl>

                                </div>
                            </div>
                        </i>
                        <span class="ico icon_time"><#if travelInfo.createTime??>${travelInfo.createTime?string('yyyy-MM-dd')}</#if></span>
                        <span class="ico icon_eye"><#if travelVisitCntMap?? && travelVisitCntMap.get(travelInfo.id)??>${travelVisitCntMap.get(travelInfo.id)!''}<#else>0</#if></span>
                    </div>

					<#if infoMap?? && infoMap.get(travelInfo.id)??><#assign travelMsg = infoMap.get(travelInfo.id).get('info') /></#if>
                    <div class="yj_info hide">
                    <@truncateChars value="${travelMsg!''}" length="104"/>
                    </div>
                    </a>
                </dt>
                <dd><a href="#"><img src="<#if infoMap?? && infoMap.get(travelInfo.id)??>${infoMap.get(travelInfo.id).get('src')!''}</#if>" alt=""></a></dd>
            </dl>
        </li>
        </#list>
        <!-- 游记列表循环结束 -->
    </ul>
    <#if pageIter.totalPages?? && pageIter.totalPages gt 100>
        <#assign totalPageCount = 100>
    <#else>
        <#assign totalPageCount = pageIter.totalPages!0>
    </#if>
    <@pageNav total="${totalPageCount!0}" current="${pageIter.page!0}" urlpattern="/travel/search/latest?pageNo=%d&keyword=${keyword!''}&city=${city!''}"/>
    <#else>
    <div class="no_result">对不起，未找到匹配内容，请更换搜索条件</div>
	</#if>

</div>

</@page>