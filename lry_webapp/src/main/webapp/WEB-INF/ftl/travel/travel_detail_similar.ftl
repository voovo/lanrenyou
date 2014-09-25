                <!-- 相关游记 -->
			<#if travelInfoList?? && travelInfoList?size gt 0>
                    <h3>相关游记</h3>
                    <ul>
                    	<#list travelInfoList as travelInfo>
                        <li><a href="/travel/${travelInfo.id!''}" title="${travelInfo.title!''}">${travelInfo.title!''}</a></li>
                        </#list>
                        <#if travelInfoList?size gt 5>
                        <li class="yj_more"><a href="/travel/search/list?city=${city!''}" title="查看更多游记">查看更多游记>></a></li>
                        </#if>
                    </ul>
			</#if>