<@page  title="找回密码－懒人游" description=""  keywords="" 
	assets=[
	"/resources/css/regist.css",
	"/resources/js/regist.js"
	]
	>

<!-- regist_box -->
<div id="regist_box">
    <div class="form_mail_box">
        <h1>找回密码</h1>
        <p>请输入新的密码</p>
        
        <form action="/login/resetPasswdSubmit" method="POST" >
        		<input type="hidden" name="code" value="${code!''}" />
                <table cellpadding="0" cellspacing="0" border="0">
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

</@page>