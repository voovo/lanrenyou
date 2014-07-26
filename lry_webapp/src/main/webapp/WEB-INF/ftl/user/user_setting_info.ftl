<@page  title="修改基本资料-懒人游" description=""  keywords="" 
	assets=[
	"/resources/css/waper.css"
	]
	>


<!-- waper_box -->
<div class="waper_box">
    <h1 class="warper_tt">个人设置</h1>
    
    <ul class="warper_tab clearfix">
        <li class="cur">个人设置</li>
        <li><a href="/user/${userInfo.id!''}/travelList/list">我的游记</a></li>
        <li><a href="/user/${userInfo.id!''}/msg/list">消息中心 (<i>${headerLetterCnt!'0'}</i>)</a></li>
        <li><a href="/user/${userInfo.id!''}/fans/list">粉丝管理</a></li>
        <li><a href="/user/${userInfo.id!''}/collect/list">我的收藏</a></li>
    </ul>
  

    <!-- 个人信息修改 -->
    <div id="u_profile_box" class="clearfix">
        <ul class="u_info_tab left">
            <li class="cur">基本资料</li>
            <li><a href="/user/setting/avatar">更换头像</a></li>
            <li><a href="/user/setting/passwd">修改密码</a></li>
        </ul>
        <div class="left u_info_form_box">
        	<form action="user/setting/infoSubmit" method="post">
            <table cellpadding="0" cellspacing="0" border="0">
                <tr>
                    <td width="20%" align="right"><span class="ft_14">昵称：</span></td>
                    <td><input type="text" class="input_220" name="nickname" value="${userInfo.nickname!''}"></td>
                </tr>
                <tr>
                    <td align="right"><span class="ft_14">邮箱：</span></td>
                    <td><i>${userInfo.email}</i></td>
                </tr>
                <tr>
                    <td align="right"><span class="ft_14">自我介绍：</span></td>
                    <td><textarea class="textarea" name="userIntro" id="" cols="30" rows="10" placeholder="请输入自我介绍 不超过200字">${userInfo.userIntro!''}</textarea></td>
                </tr>
                <tr>
                    <td align="right"><span class="ft_14">现居住地：</span></td>
                    <td>
                        <input type="text" class="input_100" name="presentAddress" value="${userInfo.presentAddress!''}">
                    </td>
                </tr>
                <tr>
                    <td width="18%" align="right"><span class="ft_14">曾居住地：</span></td>
                    <td><input type="text" class="input_100" name="previousAddress" value="${userInfo.previousAddress!''}"></td>
                </tr>
                <tr>
                    <td width="18%" align="right"></td>
                    <td>
                        <input type="checkbox" id="be_planner" name="toBePlanner" value="1">
                        <label for="be_planner" class="planner_tt">我想成为规划师<span>( 高质量的游记能更快通过申请，现在就去<a href="/travel/toAddPage" target="_blank">写游记</a> )</span></label>
                    </td>
                </tr>
                <tr id="planner_box" class="hide">
                    <td width="18%" align="right"><span class="ft_14">可策划地区：</span></td>
                    <td>
                    <div class="label_box hide"><ul class="label clearfix"></ul></div>
                    <input id="last_home" type="text" class="input_100" placeholder="用分号分隔多个地区" name="targetCity" value="<#if userPlanner??>${userPlanner.targetCity!''}</#if>"></td>
                </tr>
                <tr>
                    <td></td>
                    <td><input type="submit" class="btn blue_btn" value="提交修改" /></td>
                    <td></td>
                </tr>
            </table>
            </form>
        </div>
    </div>

</div>
</@page>