<@page  title="登录－懒人游" description=""  keywords="" 
	assets=[
	"/resources/css/regist.css"
	]
	>
	
<!-- regist_box -->
<div id="regist_box">
    <div id="regist_form" class="sign_form">
        <div class="reg_tt clearfix">
            <h1 class="left">登录懒人游</h1>
            <p class="right">还没有账号？马上[<a href="/regist/toPage">注册</a>]</p>
        </div>

        <div class="form_table">
        	<form id="" name="" method="POST" action="/login/loginSubmit">
	            <table cellpadding="0" cellspacing="0" border="0">
	                <tr>
	                    <td width="18%" align="right"><span class="ft_14">电子邮件：</span></td>
	                    <td width="40%"><input type="text" name="userName" class="input_220"></td>
	                    <td><span class="form_notice"></span></td>
	                </tr>
	                <tr>
	                    <td align="right"><span class="ft_14">密码：</span></td>
	                    <td><input type="password"  name="password" class="input_220"></td>
	                    <td><span class="form_notice"></span></td>
	                </tr>
	                <tr>
	                    <td align="right"><span class="ft_14">验证码：</span></td>
	                    <td colspan="2"><input type="text" name="captcha" class="input_70" maxlength="4"> <div id="form_code"><img src="/captcha/getCaptcha" alt=""> </div><span id="changeCode">看不清，<a href="javascript:;">换一张</a></span></td>	                  
	                </tr>
	                <tr>
	                    <td></td>
	                    <td><input type="submit" class="btn blue_btn" value="登&nbsp;&nbsp;录">  <a id="get_pwd" href="">忘记密码?</a></td>
	                    <td></td>
	                </tr>
	            </table>
            </form>
        </div>
    </div>
</div>
</@page>