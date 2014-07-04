<!-- top_nav -->
<div id="top_nav" class="clearfix">
    
    <!-- logo -->
    <div id="logo" class="left">
        <a href="/" title="返回首页"></a>
    </div>

    <!-- search -->
    <div id="search" class="left">
        <div id="city" class="show_more">城市
            <div id="more_city" class="hide more_item">
                <i class="tag_ico"></i>
                <ul class="clearfix">
                    <li><a href="javascript:;" title="芝加哥Chicago及周边">芝加哥Chicago及周边</a></li>
                    <li><a href="javascript:;" title="纽约NYC及周边">纽约NYC及周边</a></li>
                    <li><a href="javascript:;" title="洛杉矶LA及周边">洛杉矶LA及周边</a></li>
                    <li><a href="javascript:;" title="圣地亚哥San Diego及周边">圣地亚哥San Diego及周边</a></li>
                    <li><a href="javascript:;" title="旧金山San Francisco及周边">旧金山San Francisco及周边</a></li>
                    <li><a href="javascript:;" title="美国国家公园National Parks">美国国家公园National Parks</a></li>
                    <li><a href="javascript:;" title="西雅图Seattle及周边">西雅图Seattle及周边</a></li>
                    <li><a href="javascript:;" title="阿拉斯加Alaska">阿拉斯加Alaska</a></li>
                    <li><a href="javascript:;" title="游轮旅行Cruise">游轮旅行Cruise</a></li>
                    <li><a href="javascript:;" title="奥兰多Orlando及周边">奥兰多Orlando及周边</a></li>
                    <li><a href="javascript:;" title="大佛罗里达Florida">大佛罗里达Florida</a></li>
                    <li><a href="javascript:;" title="风情小镇Small Towns">风情小镇Small Towns</a></li>
                    <li><a href="javascript:;" title="加州湾区Bay Area及周边">加州湾区Bay Area及周边</a></li>
                    <li><a href="javascript:;" title="波士顿Boston及周边">波士顿Boston及周边</a></li>
                    <li><a href="javascript:;" title="加州一号公路Pacific Coast Highway">加州一号公路Pacific Coast Highway</a></li>
                    <li><a href="javascript:;" title="波多黎各Puerto Rico">波多黎各Puerto Rico</a></li>
                </ul>
            </div>
        </div>
        
        <input type="text" id="search_input" placeholder="请输入旅游城市或国家" />
        <a id="search_btn" href="javascript:;" title="搜索"></a>
    </div>
    
    <!-- nav -->
    <div id="nav" class="left">
        <ul class="clearfix">
            <li><a href="javascript:;">找游记</a></li>
            <li><a href="javascript:;">找规划师</a></li>
        </ul>
    </div>

    <!-- user -->
    <div id="user" class="right">
    	<#if loginUser??>
    		<ul class="clearfix">
	            <li>
	                <img src="/resources/imgs/user_face.jpg" width="20" height="20" alt="" />
	            </li>
	            <li class="wap_show bd_right">sheak</li>
	            <li class="wap_show bd_right"><a href="javascript:;">发布游记</a></li>
	            <li id="msg" class="wap_show bd_right show_more">
	                消息中心(5)
	                <div id="more_msg" class="more_item hide">
	                    <i class="tag_ico_r"></i>
	                    <ul class="msg_tab clearfix">
	                        <li class="cur"><a href="javascript:;">通知(2)</a></li>
	                        <li><a href="javascript:;">私信(3)</a></li>
	                    </ul>
	                    <div class="msg_item">
	                        <p>
	                            Hi~，欢迎加入懒人游，现在就开始享受你的旅程吧！在这里，...
	                        </p>
	                        <p>
	                            您发布的游记《美丽纽约客，美食者的天堂》被评为精彩游记！
	                        </p>
	                    </div>
	                    <div class="msg_item hide">
	                        <p>
	                            Hi~，欢迎加入懒人游，现在就开始享受你的旅程吧！在这里，...
	                        </p>
	                        <p>
	                            您发布的游记《美丽纽约客，美食者的天堂》被评为精彩游记！
	                        </p>
	                        <p>
	                            您发布的游记《美丽纽约客，美食者的天堂》被评为精彩游记！
	                        </p>
	                    </div>
	                    <div class="msg_bottom"><a href="javascript:;">全部通知</a></div>
	                </div>
	            </li>
	            <li>
	                <div class="user_more show_more">
	                    <div class="hide more_item">
	                        <i class="tag_ico"></i>
	                        <ul>
	                            <li class="wap_hide hide">sheak</li>
	                            <li class="wap_hide hide"><a href="javascript:;">发布游记</a></li>
	                            <li class="wap_hide hide"><a href="javascript:;">消息中心(5)</a></li>
	                            <li><a href="javascript:;" title="个人设置">个人设置</a></li>
	                            <li><a href="javascript:;" title="我的游记">我的游记</a></li>
	                            <li><a href="javascript:;" title="粉丝管理">粉丝管理</a></li>
	                            <li><a href="javascript:;" title="我的收藏">我的收藏</a></li>
	                            <li><a href="javascript:;" title="退出">退出</a></li>
	                        </ul>
	                    </div>
	                </div>
	            </li>
	        </ul>
    	<#else>
    		<ul class="clearfix">
	            <li class="bd_right"><a href="javascript:;">登陆</a></li>
	            <li><a href="javascript:;">注册</a></li>
        	</ul>
    	</#if>
    </div>
</div>