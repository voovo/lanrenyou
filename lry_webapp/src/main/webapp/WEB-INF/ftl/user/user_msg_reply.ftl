<@page  title="回复消息-懒人游" description=""  keywords="" 
	assets=[
	"/resources/css/waper.css",
	"/resources/js/msg.js"
	]
	>

<!-- waper_box -->
<div class="waper_box">
    <h1 class="warper_tt msg_icon">消息中心</h1>
    
    <ul class="warper_tab clearfix">
        <li><a href="/user/setting/info">个人设置</a></li>
        <li><a href="/user/${userInfo.id!''}/travelList/list">我的游记</a></li>
        <li class="cur">消息中心 (<i>${headerLetterCnt!'0'}</i>)</li>
        <li><a href="/user/${userInfo.id!''}/fans/list">粉丝管理</a></li>
        <li><a href="/user/${userInfo.id!''}/collect/list">我的收藏</a></li>
    </ul>
    
    <ul id="u_fans_tabs" class="clearfix waper_box">
        <li class="msgToName">我与 <span>${receiver.name!''}</span> 的私信</li>
        <li><a class="btn_s blue_btn" href="/user/${userInfo.id!''}/msg/list">返回列表</a></li>
    </ul>
    

    <ul id="u_msg_list">
        <!-- 消息中心列表循环开始 -->
        <#if letterList?? && letterList.size() gt 0>
        <#list letterList as letter>
        <#if userMap?? && userMap.get(letter.senderUid)?? >
        	<#assign user = userMap.get(letter.senderUid) />
        </#if>
        <li class="unread">
            <img src="<#if user??>${user.avatar!''}</#if>" alt="" />
            <div class="clearfix">
                <p class="left">
                    <span class="vip_ico"><a href="/user/${user.id!''}">${user.name!''}</a></span>
                </p>
                <p class="left">${letter.updateTime?string('yyyy-MM-dd')!''}</p>
            </div>
            <p class="u_msg_details">${letter.context!''}</p>
        </li>
        </#list>
        </#if>
		<form action="/user/${userInfo.id!''}/msg/reply" method="POST" >
		<input type="hidden" name="receiverUid" value=${receiver.id!''} />
        <li id="msg_replay">
            <textarea name="context" cols="30" rows="10"></textarea>
            <input type="submit" class="btn_s blue_btn" value="回复" />
        </li>
        </form>
        <!-- 消息中心列表循环结束 -->
    </ul>



</div>


</@page>