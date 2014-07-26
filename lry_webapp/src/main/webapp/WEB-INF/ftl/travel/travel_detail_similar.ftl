                <!-- 相关游记 -->
			<#if travelInfoList?? && travelInfoList?size gt 0>
                <div id="other_youji_list" class="detail_right_list">
                    <h3>相关游记</h3>
                    <ul>
                    	<#list travelInfoList as travelInfo>
                        <li><a href="/travel/${travelInfo.id!''}" title="${travelInfo.title!''}">${travelInfo.title!''}</a></li>
                        </#list>
                        <#if travelInfoList?size gt 5>
                        <li class="yj_more"><a href="/travel/search?city=${city!''}" title="查看更多游记">查看更多游记>></a></li>
                        </#if>
                    </ul>
                </div>
			</#if>