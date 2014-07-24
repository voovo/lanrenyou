            <!-- 规划师游记 -->
        <#if travelInfoList?? && travelInfoList?size gt 0>
            <div class="planner_yj_list">
                <p class="more_yj"><a href="/user/${uid!''}/travelList/list">查看全部游记</a></p>
                <div class="planner_yj_detail clearfix">
                    <div class="left">
                        <a href="/travel/${firstTid!''}">
                            <img src="${firstImg!''}" alt="">
                        </a>
                    </div>
                    <div class="right">
                        <ul>
                        	<#list travelInfoList as travelInfo>
                            <li><a href="/travel/${travelInfo.id!''}">${travelInfo.title!''}</a><span><#if travelInfo.createTime??>${travelInfo.createTime?string('yyyy-MM-dd')}</#if></span></li>
                            </#list>
                        </ul>
                    </div>
                </div>
            </div>
        </#if>