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
        <p>请输入您的注册邮箱地址，用于接收重设密码信息</p>
        <form action="/login/forgetPasswdSubmit" method="POST">
        <div class="mt_20">
            <input type="text" name="email" class="input_220">
        </div>

        <div class="clearfix mt_20">
            <div class="left">
                <input type="submit" class="btn blue_btn big_btn" value="确定" />
            </div>
        </div>
        </form>

        <div class="form_mail_msg">
            <h2>没收到确认信怎么办？</h2>
            <ol>
                <li>检查Email地址有没有写错</li>
                <li>看看是否在垃圾邮箱里</li>
                <li>如未收到重置邮件，请发送邮件至下面邮箱完成重设密码：<a href="mailto:info@lanrenyou.com">info@lanrenyou.com</a></li>
            </ol>
        </div>
    </div>
</div>

</@page>