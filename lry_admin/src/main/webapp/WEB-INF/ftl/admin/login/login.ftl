<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>懒人游管理后台</title>
<script src="/js/jquery.js" type="text/javascript" ></script>
<link rel="stylesheet" type="text/css" href="/css/register.css"/>

</head>
<body>



<div class='signup_container'>

    <h1 class='signup_title'>用户登陆</h1>
    <img src='/images/people.png' id='admin'/>
    <div id="signup_forms" class="signup_forms clearfix">
            <form class="signup_form_form" id="signup_form" method="post" action="">
                    <div class="form_row first_row">
                        <label for="signup_email">请输入用户名</label>
                        <input type="text" name="username" placeholder="请输入用户名"  />
                    </div>
                    <div class="form_row">
                        <label for="signup_password">请输入密码</label>
                        <input type="password" name="password" placeholder="请输入密码" id="signup_password" data-required="required" />
                    </div>
           </form>
    </div>

    <div class="login-btn-set"><a href='index.html' class='login-btn'></a></div>
</div>

</body>
</html>