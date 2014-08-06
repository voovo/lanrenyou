<!doctype html public "-//w3c//dtd html 4.0 transitional//en">
<html>
    
    <head>
        <meta http-equiv=content-type content="text/html; charset=utf-8">
        <link href="/css/admin.css" type="text/css" rel="stylesheet">
        <script type='text/javascript' src='/wp-includes/js/jquery/jquery.js'></script>
        <script type='text/javascript'>
        function changeLanguage(language)
		{
		    $.ajax({
		        type: "POST",
		        url: "/admin/changelanguage",
		        data: "new_lang="+language,
		        dataType:"text",
		        async: true,
		        error: function() {
		        	alert("change lang error!");
		        },
		        success: function(data) {
		            top.location.reload();
		        }
		    });
		 }
        </script>
    </head>
    
    <body>
    	<span><a href="javascript:void(0)" onclick="changeLanguage('en_US')">English</a></span>
    	<span><a href="javascript:void(0)" onclick="changeLanguage('zh_CN')">中文版</a></span>
        <table cellspacing=0 cellpadding=0 width="100%" background="/imgs/admin/header_bg.jpg"
        border=0>
            <tr height=56>
                <td width=260>
                    <img height=56 src="/imgs/admin/header_left.jpg" width=260>
                </td>
                <td style="font-weight: bold; color: #fff; padding: 0 20px 0 0" align="right">
                    当前用户：${adminUser.name!''} &nbsp;&nbsp;
                    <a style="color: #fff" href="/admin/login/to_modify_password" target=main>
                        修改口令
                    </a>
                    &nbsp;&nbsp;
                    <a style="color: #fff" onclick="if (confirm('确定要退出吗？')) return true; else return false;"
                    href="/admin/login/logout" target=_top>
                        退出系统
                    </a>
                </td>
            </tr>
        </table>
        <table cellspacing=0 cellpadding=0 width="100%" border=0>
            <tr bgcolor=#1c5db6 height=4>
                <td>
                </td>
            </tr>
        </table>
	</body>

</html>