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
                    <li><a href="/travel/search/list?city=芝加哥 Chicago" title="芝加哥Chicago及周边">芝加哥Chicago及周边</a></li>
                    <li><a href="/travel/search/list?city=纽约 NYC" title="纽约NYC及周边">纽约NYC及周边</a></li>
                    <li><a href="/travel/search/list?city=洛杉矶 LA" title="洛杉矶LA及周边">洛杉矶LA及周边</a></li>
                    <li><a href="/travel/search/list?city=圣地亚哥 San Diego" title="圣地亚哥San Diego及周边">圣地亚哥San Diego及周边</a></li>
                    <li><a href="/travel/search/list?city=旧金山 San Francisco" title="旧金山San Francisco及周边">旧金山San Francisco及周边</a></li>
                    <li><a href="/travel/search/list?city=美国国家公园 National Parks" title="美国国家公园National Parks">美国国家公园National Parks</a></li>
                    <li><a href="/travel/search/list?city=西雅图 Seattle" title="西雅图Seattle及周边">西雅图Seattle及周边</a></li>
                    <li><a href="/travel/search/list?city=阿拉斯加 Alaska" title="阿拉斯加Alaska">阿拉斯加Alaska</a></li>
                    <li><a href="/travel/search/list?city=游轮旅行 Cruise" title="游轮旅行Cruise">游轮旅行Cruise</a></li>
                    <li><a href="/travel/search/list?city=奥兰多 Orlando" title="奥兰多Orlando及周边">奥兰多Orlando及周边</a></li>
                    <li><a href="/travel/search/list?city=大佛罗里达 Florida" title="大佛罗里达Florida">大佛罗里达Florida</a></li>
                    <li><a href="/travel/search/list?city=风情小镇 Small Towns" title="风情小镇Small Towns">风情小镇Small Towns</a></li>
                    <li><a href="/travel/search/list?city=加州湾区 Bay Area" title="加州湾区Bay Area及周边">加州湾区Bay Area及周边</a></li>
                    <li><a href="/travel/search/list?city=波士顿 Boston" title="波士顿Boston及周边">波士顿Boston及周边</a></li>
                    <li><a href="/travel/search/list?city=加州一号公路 Pacific Coast Highway" title="加州一号公路Pacific Coast Highway">加州一号公路Pacific Coast Highway</a></li>
                    <li><a href="/travel/search/list?city=波多黎各 Puerto Rico" title="波多黎各Puerto Rico">波多黎各Puerto Rico</a></li>
                </ul>
            </div>
        </div>
        <form action="/travel/search/list" method="GET">
        <input type="text" id="search_input" placeholder="请输入旅游城市或国家" name="keyword"  />
        <input type="submit" id="search_btn" value=""/>
        </form>
    </div>
    
    <!-- nav -->
    <div id="nav" class="left">
        <ul class="clearfix">
            <li><a href="/travel/search/hot">找游记</a></li>
            <li><a href="/user/search/hot">找规划师</a></li>
        </ul>
    </div>

    <!-- user -->
    <div id="user" class="right">
    	<#if loginUser??>
    		<ul class="clearfix">
	            <li>
	                <img src="${loginUser.avatar!''}" width="20" height="20" alt="" />
	            </li>
	            <li class="wap_show bd_right vip_ico">${loginUser.name!''}</li>
	            <li class="wap_show bd_right"><a href="/travel/toAddPage">发布游记</a></li>
	            <li id="msg" class="wap_show bd_right show_more">
	                消息中心(${headerLetterCnt!'0'})
	                <#if headerLetterCnt?? && headerLetterCnt gt 0>
	                <div id="more_msg" class="more_item hide">
	                    <i class="tag_ico_r"></i>
	                    <ul class="msg_tab clearfix">
	                        <li class="cur"><a href="javascript:;">私信(${headerLetterCnt!'0'})</a></li>
	                    </ul>
	                    <div class="msg_item">
	                    	<#if headerLetterList?? && headerLetterList.size() gt 0>
	                    	<#list headerLetterList as letter>
	                        <p>
	                            <@truncateChars value="${letter.content!''}" length="30"/>
	                        </p>
	                        </#list>
	                        </#if>
	                    </div>
	                    <div class="msg_bottom"><a href="/user/${loginUser.id!''}/msg/list">全部私信</a></div>
	                </div>
	                </#if>
	            </li>
	            <li>
	                <div class="user_more show_more">
	                    <div class="hide more_item">
	                        <i class="tag_ico"></i>
	                        <ul>
	                            <li class="wap_hide hide">${loginUser.name!''}</li>
	                            <li class="wap_hide hide"><a href="/travel/toAddPage">发布游记</a></li>
	                            <li class="wap_hide hide"><a href="/user/${loginUser.id!''}/msg/list">消息中心(${headerLetterCnt!'0'})</a></li>
	                            <li><a href="/user/setting/info" title="个人设置">个人设置</a></li>
	                            <li><a href="/user/${loginUser.id!''}/travelList/list" title="我的游记">我的游记</a></li>
	                            <li><a href="/user/${loginUser.id!''}/fans/list" title="粉丝管理">粉丝管理</a></li>
	                            <li><a href="/user/${loginUser.id!''}/collect/list" title="我的收藏">我的收藏</a></li>
	                            <li><a href="/logout" title="退出">退出</a></li>
	                        </ul>
	                    </div>
	                </div>
	            </li>
	        </ul>
    	<#else>
    		<ul class="clearfix">
	            <li class="bd_right"><a href="/login">登陆</a></li>
	            <li><a href="/regist/toPage">注册</a></li>
        	</ul>
    	</#if>
    </div>
</div>