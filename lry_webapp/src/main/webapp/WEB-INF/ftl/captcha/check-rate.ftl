<html>
<head>
    <title>captcha-test</title>
</head>
<body>
<form action="/captcha/verify" method="POST">
    <input type="input" name="captcha"/>
    <img src="/captcha/code" title="验证码"/>
    <input type="submit"/>
</form>
</body>
</html>