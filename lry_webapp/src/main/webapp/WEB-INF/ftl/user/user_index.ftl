<@page  title="个人主页-懒人游" description=""  keywords="" 
	assets=[
	"/resources/css/waper.css",
	"/resources/js/waper.js"
	]
	>

<!-- waper_box -->
<div class="waper_box">
    <div id="my_box">
        <!-- detail_author -->
        <div class="author_img">
            <img src="${userInfo.avatar!''}" alt="">
        </div>
        <div class="authot_info">
            <h2 class="vip_b_ico"><a href="/user/${userInfo.id!''}">${userInfo.name!''}</a></h2>
            <div class="my_add">
                <a href="javascript:;" class="btn_s msg_btn right" uid="${userInfo.id!''}"></a>
                <a href="javascript:;" class="btn_s add_btn right" uid="${userInfo.id!''}"></a>
                <span class="right">粉丝：${fansCount!'0'}</span>
            </div>
        </div>
        
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

        <!-- 居住地区 -->
        <dl class="plan_area clearfix">
            <dt>住过的地方：</dt>
            <dd>
                <ul>
                    <li>${userInfo.previousAddress!''}</li>
                </ul>
            </dd>
        </dl>

        <p id="author_produ">${userInfo.userIntro!''}</p>
    </div>
                
                
    
    <ul class="warper_tab clearfix">
        <li class="cur">Ta的游记(<#if pageIter??>${pageIter.totalCount!'0'}<#else>0</#if>)</li>
    </ul>

    <ul id="u_yj_list" class="waper_box">
        <!-- 用户游记列表循环开始 -->
        <#if tidList??>
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
            </div>
            <div class="u_yj_info">
                <span class="ico icon_time">${travelInfoMap.get(tid).createTime?string('yyyy-MM-dd')!''}</span>
                <span class="ico icon_eye"><#if travelVisitCntMap?? && travelVisitCntMap.get(tid)??>${travelVisitCntMap.get(tid)!''}<#else>0</#if></span>
                <#if collectTidMap?? && collectTidMap.get(tid)??>
                	<a href="javascript:;"><i class="ico icon_faved"></i>取消收藏</a>
                <#else>
                	<a href="javascript:;" class="add_fav" tid="${tid!''}"><i class="ico icon_fav"></i>收藏</a>
                </#if>
            </div>
        </li>
        </#if>
        </#list>
        </#if>
        <!-- 用户游记列表循环结束 -->
    </ul>
	<#if pageIter.totalPages?? && pageIter.totalPages gt 100>
        <#assign totalPageCount = 100>
    <#else>
        <#assign totalPageCount = pageIter.totalPages!0>
    </#if>
    <@pageNav total="${totalPageCount!0}" current="${pageIter.page!0}" urlpattern="/user/${userInfo.id!''}?pageNo=%d"/>
</div>


</@page>