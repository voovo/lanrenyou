<@page  title="注册－懒人游" description=""  keywords="" 
	assets=[
	"/resources/css/regist.css"
	]
	>
<!-- regist_box -->
<div id="regist_box">
    <div id="regist_form" class="sign_form">
        <div class="reg_tt clearfix">
            <h1 class="left">注册新账号</h1>
            <p class="right">已注册账号， 直接[<a href="/login/toPage">登录</a>]</p>
        </div>

        <div class="form_table">
        	<form id="" name="" method="POST" action="/regist/submit" >
            <table cellpadding="0" cellspacing="0" border="0">
                <tr>
                    <td width="18%" align="right"><span class="ft_14">电子邮件：</span></td>
                    <td width="40%"><input type="text" name="email" class="input_220"></td>
                    <td><span class="form_notice">该邮箱作为登录账号</span></td>
                </tr>
                <tr>
                    <td align="right"><span class="ft_14">密码：</span></td>
                    <td><input type="password" name="userPass"class="input_220"></td>
                    <td><span class="form_notice">请输入6-20位密码</span></td>
                </tr>
                <tr>
                    <td align="right"><span class="ft_14">确认密码：</span></td>
                    <td><input type="password" name="userPass2" class="input_220"></td>
                    <td><span class="error">请输入密码</span></td>
                </tr>
                <tr>
                    <td align="right"><span class="ft_14">验证码：</span></td>
                    <td colspan="2"><input type="text" name="captcha" class="input_70" maxlength="4"> <div id="form_code"><img src="/captcha/getCaptcha" alt=""> </div><span id="changeCode">看不清，<a href="javascript:;">换一张</a></span></td>
                </tr>
                <tr>
                    <td></td>
                    <td><input type="submit" class="btn blue_btn" value="立即注册" /></td>
                    <td></td>
                </tr>
            </table>
            </form>
        </div>
    </div>
</div>
</@page>