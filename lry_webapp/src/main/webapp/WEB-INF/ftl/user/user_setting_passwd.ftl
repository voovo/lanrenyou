<@page  title="修改密码-懒人游" description=""  keywords="" 
	assets=[
	"/resources/css/waper.css",
	"/resources/css/plug/style.css",
	"/resources/js/plug/Validform_v5.3.2_min.js",
	"/resources/js/profile.js"
	]
	>


<!-- waper_box -->
<div class="waper_box">
    <h1 class="warper_tt">个人设置</h1>
    
    <ul class="warper_tab clearfix">
        <li class="cur">个人设置</li>
        <li><a href="/user/${userInfo.id!''}/travelList/list">我的游记</a></li>
        <li><a href="/user/${userInfo.id!''}/msg/list">消息中心 (<i>22</i>)</a></li>
        <li><a href="/user/${userInfo.id!''}/fans/list">粉丝管理</a></li>
        <li><a href="/user/${userInfo.id!''}/collect/list">我的收藏</a></li>
    </ul>
  

    <!-- 个人信息修改 -->
    <div id="u_profile_box" class="clearfix">
        <ul class="u_info_tab left">
            <li><a href="/user/setting/info">基本资料</a></li>
            <li><a href="/user/setting/avatar">更换头像</a></li>
            <li class="cur">修改密码</li>
        </ul>
        <div class="left u_info_form_box">
            <form action="/user/setting/changePasswd" method="POST" class="changepwd">
                <table cellpadding="0" cellspacing="0" border="0">
                    <tr>
                        <td width="20%" align="right"><span class="ft_14">当前密码：</span></td>
                        <td><input id="u_pwd" name="old_passwd" type="password" class="input_220"></td>
                    </tr>
                    <tr>
                        <td align="right"><span class="ft_14">新密码：</span></td>
                        <td><input id="u_newPwd" name="new_passwd" type="password" class="input_220"></td>
                    </tr>
                    <tr>
                        <td align="right"><span class="ft_14">确认新密码：</span></td>
                        <td><input id="u_rePwd" name="new_repasswd" type="password" class="input_220"></td>
                    </tr>
                    <tr>
                        <td></td>
                        <td><input type="submit" id="sb_pwd" class="btn blue_btn" value="提交修改" /></td>
                        <td></td>
                    </tr>
                </table>
            </form>
        </div>
    </div>



</div>

</@page>