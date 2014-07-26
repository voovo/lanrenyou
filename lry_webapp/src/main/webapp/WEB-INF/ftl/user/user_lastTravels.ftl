								<#if travelInfoList??>
									<ul class="author_yj_last">
                                        <li class="gray">最新游记</li>
								<#list travelInfoList as travelInfo>
                                        <li><a href="/travel/${travelInfo.id!''}" target="_blank">${travelInfo.title!''}</a></li>
                                 </#list>
                                    </ul>
                                </#if>