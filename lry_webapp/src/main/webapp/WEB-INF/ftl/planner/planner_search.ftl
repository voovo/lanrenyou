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
        <li class="cur">游记</li>
        <li><a href="javascript:;">规划师</a></li>
    </ul>

	<#if travelInfoList??>
    <div class="no_result">对不起，未找到匹配内容，请更换搜索条件</div>
    <#else>
    <ul class="planner_list clearfix">
        <!-- 规划师列表循环开始 -->
        <li class="planner_item">
            <dl class="clearfix">
                <dt class="planner_face">
                    <img src="imgs/user_face.jpg" alt="">
                </dt>
                <dd class="planner_name">
                    <span class="vip_ico"><a href="#">shell_corona</a></span>
                </dd>
                <dd class="planner_info">
                    <span>关注 20</span>
                    <span>粉丝 131</span>
                    <span>游记 <a href="#">15</a></span>

                    <a href="javascript:;" class="btn_s add_btn"></a>
                    <a href="javascript:;" class="btn_s msg_btn"></a>
                </dd>
            </dl>

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

            <!-- 规划师游记 -->
            <div class="planner_yj_list">
                <p class="more_yj"><a href="#">查看全部游记</a></p>
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
        <li class="planner_item">
            <dl class="clearfix">
                <dt class="planner_face">
                    <img src="imgs/user_face.jpg" alt="">
                </dt>
                <dd class="planner_name">
                    <span class="vip_ico"><a href="#">shell_corona</a></span>
                </dd>
                <dd class="planner_info">
                    <span>关注 20</span>
                    <span>粉丝 131</span>
                    <span>游记 <a href="#">15</a></span>

                    <a href="javascript:;" class="btn_s add_btn"></a>
                    <a href="javascript:;" class="btn_s msg_btn"></a>
                </dd>
            </dl>

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

            <!-- 规划师游记 -->
            <div class="planner_yj_list">
                <p class="more_yj"><a href="#">查看全部游记</a></p>
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
    </ul>
	</#if>

    <div id="page" class="page">
        <ul class="clearfix">
            <li><a class="cur" href="">1</a></li>
            <li><a href="">2</a></li>
            <li><a href="">3</a></li>
            <li><a href="">4</a></li>
            <li><a href="">5</a></li>
            <li><a href="">6</a></li>
            <li>...</li>
            <li><a href="">15</a></li>
            <li><a href="">16</a></li>
        </ul>
    </div>


</div>

</@page>