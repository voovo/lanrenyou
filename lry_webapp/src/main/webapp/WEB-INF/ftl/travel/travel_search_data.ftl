    <#if travelInfoList?? && travelInfoList?size gt 0>
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
    <#else>
    <div class="no_result">对不起，未找到匹配内容，请更换搜索条件</div>
	</#if>
