<@page  title="消息中心-懒人游" description=""  keywords="" 
	assets=[
	"/resources/css/waper.css",
	"/resources/js/msg.js"
	]
	>

<!-- waper_box -->
<div class="waper_box">
    <h1 class="warper_tt msg_icon">消息中心</h1>
    
    <ul class="warper_tab clearfix">
        <li><a href="/user/${userInfo.id!''}/info">个人设置</a></li>
        <li><a href="/user/${userInfo.id!''}/travelList/list">我的游记</a></li>
        <li class="cur">消息中心 (<i>${headerLetterCnt!'0'}</i>)</li>
        <li><a href="/user/${userInfo.id!''}/fans/list">粉丝管理</a></li>
        <li><a href="/user/${userInfo.id!''}/collect/list">我的收藏</a></li>
    </ul>
    
    <!--
    <ul id="u_fans_tabs" class="clearfix waper_box">
        <li><a class="btn_s blue_btn" href="javascript:;">未读消息(3)</a></li>
        <li><a class="btn_s gray_btn" href="javascript:;">私信(17)</a></li>
        <li><a class="btn_s gray_btn" href="javascript:;">通知(2)</a></li>
    </ul>
     -->
     
	<#if letterList?? && letterList?size() gt 0>
    <p>
        <input id="sel_all" type="checkbox"> <label for="sel_all">全选</label>
        <a href="javascript:;">删除</a>
        <a href="javascript:;">标记为已读</a>
    </p>

    <ul id="u_msg_list">
        <!-- 小心中心列表循环开始 -->
        <#list letterList as letter>
        <li <#if letter.hasRead==1>class="unread"</#if>>
            <input type="checkbox" />
            <img src="<#if userInfoMap?? && userInfoMap.get(letter.senderUid)??>${userInfoMap.get(letter.senderUid).avatar!''}</#if>" alt="" />
            <div class="clearfix">
                <p class="left">
                    <span class="vip_ico"><a href="/user/${letter.senderUid!''}"><#if userInfoMap?? && userInfoMap.get(letter.senderUid)??>${userInfoMap.get(letter.senderUid).name!''}</#if></a></span>
                </p>
                <p class="left">${letter.updateTime?string('yyyy-MM-dd')!''}</p>
                <p class="right">
                    <a href="javascript:;" class="ico delete_ico">删除</a>
                    <a href="javascript:;" class="ico edit_icon">回复</a>
                </p>
            </div>
            <p class="u_msg_details">${letter.context!''}</p>
        </li>
        </#list>
        <!-- 小心中心列表循环结束 -->
    </ul>

	<#if pageIter.totalPages?? && pageIter.totalPages gt 100>
        <#assign totalPageCount = 100>
    <#else>
        <#assign totalPageCount = pageIter.totalPages!0>
    </#if>
    <@pageNav total="${totalPageCount!0}" current="${pageIter.page!0}" urlpattern="/user/${userInfo.id!''}/msg/list?pageNo=%d"/>
    <#else>
    	<div class="no_result">还没有收到任何私信</div>
    </#if>

</div>

</@page>