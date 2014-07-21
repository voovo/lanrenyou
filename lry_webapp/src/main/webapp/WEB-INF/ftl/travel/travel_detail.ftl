<@page  title="${travelInfo.title!''}-懒人游" description=""  keywords="" 
	assets=[
	"/resources/css/detail.css",
	"/resources/js/detail.js"
	]
	>

<!-- box -->
<div class="youji_detail_main box">
    <div class="bread_box"><a href="/index">首页</a> > <a href="/travel/search/toPage">找游记</a> >${travelInfo.title!''}</div>
    
    <div class="detail_main_box clearfix">
        <div class="detail_left left">
            <h1>${travelInfo.title!''}</h1>
            <h2 class="clearfix">
                <div class="left">日期：<i><#if travelInfo.createTime??>${travelInfo.createTime?string('yyyy-MM-dd')}</#if></i> 浏览：<i>${visitCnt!'0'}</i></div>
                <div class="right">
                    <a href="javascript:;" class="add_fav button"><i></i><span>${collectCnt!'0'}</span></a>
                </div>         
            </h2>

            <!-- 游记列表开始 -->
            <div class="youji_detail_list">
            	<#if contentList??>
            		<#list contentList as contentMap>
            	<div class="detail_item">
                    <div class="detail_img">
                        <i class="img_memb"></i>
                        <img src="${contentMap.get('src')!''}" alt="">
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
                        <img src="imgs/user_face.jpg" alt="">
                    </div>
                    <div class="left authot_info">
                        <h2 class="vip_b_ico"><a href="#">shell_corona</a></h2>
                        <p>
                            <a href="javascript:;" class="btn_s msg_btn right"></a>
                            <a href="javascript:;" class="btn_s add_btn right"></a>
                        </p>
                    </div>
                </div>
                <p id="author_produ">文艺小女子一枚，十九岁之前常和母亲一起旅行，曾去过十七个国家，十九岁赴美留学之后开始独自旅行，旅行途中导游摄影翻译搬行李皆自己负责，喜欢记录旅行途中的小故事，希望与大家分享更多关于爱，关于十九岁独旅，关于温暖，关于这个诺大世界我们所不知道的人和事。去旅行，为了带着爱回来。让我为你策划一场关于爱的旅程，不论是独旅或是和朋友家人一起，和我一起，带着初心走世界吧。</p>
                
                <!-- 策划地区 -->
                <dl class="plan_area clearfix">
                    <dt>可策划地区：</dt>
                    <dd>
                        <ul>
                            <li><a href="javascript:;">美国纽约</a></li>
                            <li><a href="javascript:;">美国纽约</a></li>
                            <li><a href="javascript:;">美国纽约</a></li>
                            <li><a href="javascript:;">美国纽约</a></li>
                            <li><a href="javascript:;">美国纽约</a></li>
                            <li><a href="javascript:;">美国纽约</a></li>
                            <li><a href="javascript:;">美国纽约</a></li>
                            <li><a href="javascript:;">美国纽约</a></li>
                        </ul>
                    </dd>
                </dl>

                <!-- 居住地区 -->
                <dl class="plan_area clearfix">
                    <dt>住过的地方：</dt>
                    <dd>
                        <ul>
                            <li>美国纽约、美国纽约、美国纽约、美国纽约</li>
                        </ul>
                    </dd>
                </dl>


                <!-- TA的游记 -->
                <div id="my_youji_list" class="detail_right_list">
                    <h3>Ta的游记</h3>
                    <ul>
                        <li><a href="#" title="美国西海岸一号公路外加一小段66号公路野马自驾15天">美国西海岸一号公路外加一小段66号公路野马自驾15天</a></li>
                        <li><a href="#" title="周扒皮西游第三季：骑行美国66号公路">周扒皮西游第三季：骑行美国66号公路</a></li>
                        <li><a href="#" title="好想去66号公路的寻梦">好想去66号公路的寻梦</a></li>
                        <li class="yj_more"><a href="#" title="查看Ta的全部游记">查看Ta的全部游记>></a></li>
                    </ul>
                </div>

                <!-- 相关游记 -->
                <div id="other_youji_list" class="detail_right_list">
                    <h3>相关游记</h3>
                    <ul>
                        <li><a href="#" title="美国西海岸一号公路外加一小段66号公路野马自驾15天">美国西海岸一号公路外加一小段66号公路野马自驾15天</a></li>
                        <li><a href="#" title="周扒皮西游第三季：骑行美国66号公路">周扒皮西游第三季：骑行美国66号公路</a></li>
                        <li><a href="#" title="好想去66号公路的寻梦">好想去66号公路的寻梦</a></li>
                        <li><a href="#" title="周扒皮西游第三季：骑行美国66号公路">周扒皮西游第三季：骑行美国66号公路</a></li>
                        <li><a href="#" title="好想去66号公路的寻梦">好想去66号公路的寻梦</a></li>
                        <li class="yj_more"><a href="#" title="查看更多游记">查看更多游记>></a></li>
                    </ul>
                </div>
            </div>

            <!-- 右侧广告 -->
            <a id="detail_right_ad_1" href="#">
                <img src="imgs/detail/ad.jpg" alt="">
            </a>
        </div>

    </div>

</div>

</@page>