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
                    当前位置: <a href="main.html">管理首页</a> - <a href="yhgl.html">管理员列表</a> - 修改管理员
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
                <h4>管理员账户信息</h4>
                <form action="/admin/administrator/updatesubmit" method="post" id="updateAdminForm"/>
                <input type="hidden" name="id" value="${admin.id!''}">
                <table width="100%" border="0" cellspacing="2" cellpadding="4">
                    <tr>
                        <td width="100" align="right">用户名：</td>
                        <td><input class="s_input" type="text" name="name" value="${admin.name}"></td>
                        <td></td>
                        <td></td>
                    </tr>
                    <!--
                    <tr>
                        <td width="100" align="right">邮箱：</td>
                        <td><input class="s_input" type="text" value="name@lanrenyou.com"></td>
                        <td></td>
                        <td></td>
                    </tr>
                     -->
                    <tr>
                        <td align="right">新密码：</td>
                        <td><input class="s_input" type="password" name="password"></td>
                        <td align="right"></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td align="right">确认新密码：</td>
                        <td><input class="s_input" type="password" name="password2"></td>
                        <td align="right"></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td align="right">角色：</td>
                        <td>
                            <select name="roleId" id="">
                                <#if roleList??>
                        		<#list roleList as role>
									<option value ="${role.id!''}">${role.name!''}</option>
                        		</#list>
                        		</#if>
                            </select>
                        </td>
                        <td align="right"></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td></td>
                        <td>
                            <input type="submit" class="s_btn" value="提交">
                        </td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                    </tr>
                </table>
                </form>
            </div>


        </div>

        
    </body>

</html>