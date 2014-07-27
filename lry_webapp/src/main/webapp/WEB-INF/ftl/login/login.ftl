<@page  title="登录－懒人游" description=""  keywords="" 
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
            <h1 class="left">登录懒人游</h1>
            <p class="right">还没有账号？马上[<a href="/regist/toPage">注册</a>]</p>
        </div>
        <#assign username="" password="">
        <#assign cookies = request.getCookies()>
        	<#list cookies as cookie>
        		<#if cookie.name = "lry_email">        		  
        		<#assign username = cookie.value>
        		</#if>
        	</#list>
     
		<#if Request["loginUser"]?exists>
		<#assign username = Request["loginUser"].getEmail()>
		<#assign password = Request["loginUser"].getUserPass()>
		</#if>
		
        <div class="form_table">
        	<form id="" name="" method="POST" action="/login/loginSubmit" class="loginform">
	            <table cellpadding="0" cellspacing="0" border="0">
	                <tr>
	                    <td width="18%" align="right"><span class="ft_14">电子邮件：</span></td>
	                    <td width="40%"><input type="text" name="userName" class="input_220" value="${username}"></td>
	                    <td><span class="form_notice"></span></td>
	                </tr>
	                <tr>
	                    <td align="right"><span class="ft_14">密码：</span></td>
	                    <td><input type="password"  name="password" class="input_220" value="${password}"></td>
	                    <td><span class="form_notice"></span></td>
	                </tr>
	                <tr class="login_code_tr">
	                    <td align="right"><span class="ft_14">验证码：</span></td>
	                    <td colspan="2"><input type="text" name="captcha" id="captcha" class="input_70" maxlength="4"> <div id="form_code"><img /> </div><span id="changeCode">看不清，<a href="javascript:;">换一张</a></span></td>	                  
	                </tr>
	                <tr>
	                    <td></td>
	                    <td><a id="login_btn" href="javascript:;" class="btn blue_btn">登&nbsp;&nbsp;录</a> <a id="get_pwd" href="/login/forgetPasswd">忘记密码?</a></td>
	                </tr>
	            </table>
            </form>
        </div>
    </div>
</div>
</@page>