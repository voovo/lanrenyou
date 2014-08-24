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
        	<form action="/user/setting/infoSubmit" method="post">
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
                <#if !(userPlanner?? && userPlanner.status == 2)>
                <tr>
                    <td width="18%" align="right"></td>
                    <td>
                        <a href="javascript:;" class="btn gray_btn" id="be_planner">申请成为规划师</a>
                        <span><#if userPlanner?? && userPlanner.status == 1>(系统管理员会在24小时内审核完毕 ，请耐心等待)<#elseif userPlanner?? && userPlanner.status == 3>(审核不通过，拒绝原因：#{userPlanner.refuseReason!'信息不完整'})</#if></span>
                    </td>
                </tr>
                </#if>
                <tr id="planner_box" class="planner_form_label hide" <#if userPlanner?? && userPlanner.status == 2>style="display: table-row;"</#if>>
                    <td width="18%" align="right"><span class="ft_14">可策划地区：</span></td>
                    <td>
                    <div class="label_box hide"><ul class="label clearfix"></ul></div>
                    <input id="last_home" type="text" class="input_100" placeholder="用分号分隔多个地区" name="targetCity" value="<#if userPlanner??>${userPlanner.targetCity!''}</#if>"></td>
                </tr>
                <tr class="planner_form_label hide" <#if userPlanner?? && userPlanner.status == 2>style="display: table-row;"</#if>>
                    <td align="right"><span class="ft_14">收费标准：</span></td>
                    <td><input id="payfor" name="fees" type="text" class="input_100" value="<#if userPlanner??>${userPlanner.fees!''}</#if>"></td>
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