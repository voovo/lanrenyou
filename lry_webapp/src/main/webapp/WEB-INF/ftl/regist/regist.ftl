<@page  title="注册－懒人游" description=""  keywords="" 
	assets=[
	"/resources/css/regist.css",
	"/resources/css/plug/style.css",
	"/resources/js/plug/Validform_v5.3.2_min.js",
	"/resources/js/regist.js"
	]
	>
<!-- regist_box -->
<div id="regist_box" class="login_bg">
    <div id="regist_form" class="sign_form">
        <div class="reg_tt clearfix">
            <h1 class="left">注册新账号</h1>
            <p class="right">已注册账号， 直接[<a href="">登录</a>]</p>
        </div>

        <div class="form_table registerform">
            <form class="registerform" action="/regist/submit">
                <table cellpadding="0" cellspacing="0" border="0">
                    <tr>
                        <td width="15%" align="right"><span class="ft_14">电子邮件：</span></td>
                        <td width="48%"><input id="reg_email" name="reg_email" type="text" class="input_220" datatype="e" errormsg="请输入正确的邮箱格式！"></td>
                    </tr>
                    <tr>
                        <td align="right"><span class="ft_14">密码：</span></td>
                        <td><input id="reg_pwd" name="reg_pwd" type="password" class="input_220" datatype="*6-16" errormsg="请输入6-16位密码！"></td>
                    </tr>
                    <tr>
                        <td align="right"><span class="ft_14">确认密码：</span></td>
                        <td><input id="reg_repwd" name="reg_repwd" type="password" class="input_220" datatype="e" errormsg="请重复以上密码！"></td>
                    </tr>
                    <tr class="reg_code_tr">
                        <td align="right"><span class="ft_14">验证码：</span></td>
                        <td colspan="2"><input id="reg_code" name="reg_code" type="text" class="input_70" maxlength="4" datatype="n4-4" errormsg="请输入正确的邮箱格式！"> <div id="form_code"><img src="/captcha/getCaptcha" alt=""> </div><span id="changeCode">看不清，<a href="javascript:;">换一张</a></span></td>
                    </tr>
                    <tr>
                        <td></td>
                        <td><a id="reg_btn" href="javascript:;" class="btn blue_btn">立即注册</a></td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
</div>
</@page>