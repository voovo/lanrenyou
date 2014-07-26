<@page  title="查看确认邮件-懒人游" description=""  keywords="" 
	assets=[
	"/resources/css/regist.css",
	"/resources/js/regist.js"
	]
	>
<!-- regist_box -->
<div id="regist_box">
    <div class="form_mail_box">
        <h1>激活邮箱</h1>
        <h3>您好！您刚才的验证链接已经失效，系统刚刚为您重发了验证邮件，请登录<a class="user_reg_mail">${userInfo.email!''}</a>查看激活邮件</h3>

        <div class="clearfix">
            <div class="left">
                <a href="" class="btn blue_btn big_btn">登陆邮箱查收确认邮件</a>
            </div>
            <div class="left">没收到邮件？<a id="resend_mail" href="javascript:;">再发一次</a></div>
        </div>

        <div class="form_mail_msg">
            <h2>没收到确认信怎么办？</h2>
            <ol>
                <li>检查Email地址有没有写错</li>
                <li>看看是否在垃圾邮箱里</li>
                <li>如未收到激活邮件，请发送邮件至下面邮箱完成激活：<a href="mailto:info@lanrenyou.com">info@lanrenyou.com</a></li>
            </ol>
        </div>
    </div>
</div>
</@page>