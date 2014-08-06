<!doctype html public "-//w3c//dtd html 4.0 transitional//en">
<html>
    
    <head>
        <meta http-equiv=content-type content="text/html; charset=utf-8">
        <link href="/css/admin.css" type="text/css" rel="stylesheet">
        <link rel="stylesheet" href="/css/datePicker.css">
        <script type='text/javascript' src='/wp-includes/js/jquery/jquery.js'></script>
        <link rel="stylesheet" href="/css/jquery-ui.css">
        <script src="/js/jquery-ui.js"></script>
        <script type="text/javascript" src="/js/admin.js"></script>
    </head>
    
    <body>
        <table cellspacing=0 cellpadding=0 width="100%" align=center border=0 >
            <tr height=28>
                <td background=/imgs/admin/title_bg1.jpg style="padding-left:10px;">
                    当前位置: <a href="main.html">管理首页</a> - <a href="yhgl.html">角色列表</a> - 新增角色
                </td>
            </tr>
            <tr>
                <td bgcolor=#b1ceef height=1>
                </td>
            </tr>
            <tr height=20>
                <td background=/imgs/admin/shadow_bg.jpg>
                </td>
            </tr>
        </table>

        <div class="main_content">
            <!-- 搜索 -->
            <div class="main_search">
                <h4>角色信息</h4>
                <form action="/admin/role/role_addsubmit" method="post" id="addRoleForm"/>
                	<ul class="clearfix">
	                    <li>
	                        	角色名称：<input type="text" name="name" class="s_input" value="" />
	                    </li>
	                </ul>
	               
	                <p class="role_tt">审核管理</p>
	                <ul class="clearfix rolesItem">
	                    <li><input class="ins" name="powerItemId" id="roles_1_1" type="checkbox" value="1"> <label for="roles_1_1">个人信息审核</label></li>
	                </ul>
	
	                <p class="role_tt">资金操作管理</p>
	                <ul class="clearfix rolesItem">
	                    <li><input class="ins" name="powerItemId" id="roles_2_1" type="checkbox" value="2"> <label for="roles_2_1">资金操作管理</label></li>
	                </ul>
	                
	                <p class="role_tt">管理员信息</p>
	                <ul class="clearfix rolesItem">
	                    <li><input class="ins" name="powerItemId" id="roles_2_1" type="checkbox" value="3"> <label for="roles_2_1">管理员信息</label></li>
	                </ul>
	                
	                <p class="role_tt">角色权限信息</p>
	                <ul class="clearfix rolesItem">
	                    <li><input class="ins" name="powerItemId" id="roles_2_1" type="checkbox" value="4"> <label for="roles_2_1">角色权限信息</label></li>
	                </ul>
	                <ul class="clearfix">
		                <li>
	                        <input type="submit" value="确定">
	                    </li>
                    </ul>
                </form>
            </div>


        </div>
    </body>

</html>