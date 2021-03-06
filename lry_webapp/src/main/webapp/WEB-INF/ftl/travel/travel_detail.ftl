<@page  title="${travelInfo.title!''}-懒人游" description="懒人游 ${travelInfo.city!''} 旅行日志"  keywords="${travelInfo.city!''} 懒人游" 
	assets=[
	"/resources/css/detail.css",
	"/resources/js/detail.js"
	]
	>

<!-- box -->
<div class="youji_detail_main box">
    <div class="bread_box"><a href="/index">首页</a> > <a href="/travel/search/list">找游记</a> >${travelInfo.title!''}</div>
    
    <div class="detail_main_box clearfix">
        <div class="detail_left left">
            <h1>${travelInfo.title!''}</h1>
            <h2 class="clearfix">
                <div class="left">日期：<i><#if travelInfo.createTime??>${travelInfo.createTime?string('yyyy-MM-dd')}</#if></i> 浏览：<i>${visitCnt!'0'}</i></div>
                <div class="right">
                	<#if collectTidMap?? && collectTidMap.get(travelInfo.id)??>
                    	<a href="javascript:;" class="added_fav button" tid="${travelInfo.id!''}"><i></i><span>${collectCnt!'0'}</span></a>
                    <#else>
                    	<a href="javascript:;" class="add_fav button" tid="${travelInfo.id!''}"><i></i><span>${collectCnt!'0'}</span></a>
                    </#if>
                </div>         
            </h2>

            <!-- 游记列表开始 -->
            <div class="youji_detail_list">
            	<#if contentList??>
            		<#list contentList as contentMap>
            	<div class="detail_item">
                    <div class="detail_img">
                        <i class="img_memb"></i>
                        <img src="${contentMap.get('src')!''}" alt="" width="640">
                    </div>
                    <div class="clearfix detail_info">
                        <p class="detail_img_info">${contentMap.get('info')!''}</p>
                        <div class="left ico icon_time"><#if travelInfo.createTime??>${travelInfo.createTime?string('yyyy-MM-dd HH:mm:ss')}</#if></div>
                        <div class="right">
                        	<!--
                            <a href="javascript:;" class="ico like_ico">喜欢(<i>18</i>)</a>
                            <a href="javascript:;" class="ico share_ico">分享(<i>30</i>)</a>
                             -->
                            <a href="javascript:;" class="ico share_ico">分享</a>
                        </div>
                        <div class="share_dialogbox">
                            <a href="javascript:;" class="weibo_ico">新浪微博</a>
                            <a href="javascript:;" class="qq_t_ico">腾讯微博</a>
                            <a href="javascript:;" class="rr_ico">人人</a>
                        </div>
                    </div>
                </div>
            		</#list>
            	</#if>
            </div>
            <!-- 游记列表结束 -->

            <!-- 百度分享 -->
            <div class="bdsharebuttonbox" data-tag="share_1">
                <a class="bds_mshare" data-cmd="mshare"></a>
                <a class="bds_qzone" data-cmd="qzone" href="#"></a>
                <a class="bds_tsina" data-cmd="tsina"></a>
                <a class="bds_baidu" data-cmd="baidu"></a>
                <a class="bds_renren" data-cmd="renren"></a>
                <a class="bds_tqq" data-cmd="tqq"></a>
                <a class="bds_more" data-cmd="more"></a>
                <!-- <a class="bds_count" data-cmd="count"></a> -->
            </div>
            <script>
                window._bd_share_config = {
                    share : [{
                        "bdSize" : 24
                    }]
                }
                with(document)0[(getElementsByTagName('head')[0]||body).appendChild(createElement('script')).src='http://bdimg.share.baidu.com/static/api/js/share.js?cdnversion='+~(-new Date()/36e5)];
            </script>
            <!-- 百度分享 --> 

        </div>
        <div class="detail_right right">
            <div class="detail_right_box">
                <!-- detail_author -->
                <div id="detail_author" class="clearfix">
                    <div class="left author_img">
                        <img src="${userInfo.avatar!''}" alt="">
                    </div>
                    <div class="left authot_info">
                        <h2 class="vip_b_ico"><a href="/user/${userInfo.id!''}">${userInfo.nickname!''}</a></h2>
                        <#assign isShow = true />
                        <#if loginUser?? && loginUser.id == userInfo.id>
                        	<#assign isShow = false />
                        </#if>
                        <#if isShow>
                        <p>
                            <a href="javascript:;" class="btn_s msg_btn right" uid="${userInfo.id!''}" username="${userInfo.nickname!''}"></a>
                            <#if userStarMap?? && userStarMap.get(userInfo.id)??>
							<a href="javascript:;" class="btn_s added_btn right" uid="${userInfo.id!''}"></a>
							<#else>
			                <a href="javascript:;" class="btn_s add_btn right" uid="${userInfo.id!''}"></a>
							</#if>
                        </p>
                        </#if>
                    </div>
                </div>
                <p id="author_produ"><@truncateChars value="${userInfo.userIntro!''}" length="520"/></p>
                
                <!-- 策划地区 -->
                <#if userPlanner?? >
                <#if plannCities??>
                <dl class="plan_area clearfix">
                    <dt>可策划地区：</dt>
                    <dd>
                        <ul>
                        	<#list plannCities as city >
	                            <li><a href="/travel/search/list?city=${city!''}">${city!''}</a></li>
                            </#list>
                        </ul>
                    </dd>
                </dl>
                </#if>
                <#if userPlanner.fees??>
                <dl class="plan_area clearfix">
                    <dt>收费标准：</dt>
                    <dd>
                        <ul>
	                        <li>${userPlanner.fees}</li>
                        </ul>
                    </dd>
                </dl>
                </#if>
                </#if>

				<#if userInfo.presentAddress??>
                <dl class="plan_area clearfix">
                    <dt>现居住地：</dt>
                    <dd>
                        <ul>
                            <li><@truncateChars value="${userInfo.presentAddress!''}" length="104"/></li>
                        </ul>
                    </dd>
                </dl>
                </#if>
                <!-- 居住地区 -->
                <#if userInfo.previousAddress??>
                <dl class="plan_area clearfix">
                    <dt>住过的地方：</dt>
                    <dd>
                        <ul>
                            <li><@truncateChars value="${userInfo.previousAddress!''}" length="104"/></li>
                        </ul>
                    </dd>
                </dl>
                </#if>


                <!-- TA的游记 -->
                <#if userTravelList?? && userTravelList?size gt 0>
                <div id="my_youji_list" class="detail_right_list">
                    <h3>Ta的游记</h3>
                    <ul>
                    	<#list userTravelList as userTravel>
	                        <li><a href="/travel/${userTravel.id!''}" title="${userTravel.title!''}">${userTravel.title!''}</a></li>
                        </#list>
                        <li class="yj_more"><a href="/user/${userInfo.id!''}" title="查看Ta的全部游记">查看Ta的全部游记>></a></li>
                    </ul>
                </div>
                </#if>

                <!-- 相关游记 -->
                <div id="other_youji_list" class="detail_right_list">
                </div>
            </div>

            <!-- 右侧广告 -->
            <!--
            <a id="detail_right_ad_1" href="#">
                <img src="imgs/detail/ad.jpg" alt="">
            </a>
             -->
        </div>

    </div>

</div>

</@page>