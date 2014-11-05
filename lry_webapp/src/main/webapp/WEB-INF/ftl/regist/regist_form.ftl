<@page  title="注册成功-完善资料-懒人游" description=""  keywords="" 
	assets=[
	"/resources/css/regist.css",
	"/resources/js/regist.js"
	]
	>

<!-- regist_box -->
<div id="regist_box">
    <div class="form_success_box">
        <h1 class="success suc_ico">恭喜你已经成为懒人游正式用户</h1>
        <span class="gray">为了更好的体验懒人游，请先完善自己的基本资料！</span>

        <div class="form_table">
        	<form action="/regist/submitInfo" method="POST">
            <table cellpadding="0" cellspacing="0" border="0">
            	<input type="hidden" name="uid" value="${loginUser.id!''}" />
                <tr>
                    <td width="18%" align="right"><span class="ft_14">昵称：</span></td>
                    <td><input type="text" name="nickname" class="input_220"></td>
                </tr>
                <tr>
                    <td align="right"><span class="ft_14">邮箱：</span></td>
                    <td><i>${loginUser.email!''}</i></td>
                </tr>
                <tr>
                    <td align="right"><span class="ft_14">自我介绍：</span></td>
                    <td><textarea class="textarea" name="userIntro" id="userIntro" cols="30" rows="10" placeholder="请输入自我介绍 不超过200字"></textarea></td>
                </tr>
                <tr>
                    <td align="right"><span class="ft_14">现居住地：</span></td>
                    <td><input type="text" name="presentAddress" class="input_100"></td>
                </tr>
                <tr>
                    <td width="18%" align="right"><span class="ft_14">曾居住地：</span></td>
                    <td><input type="text" name="previousAddress" class="input_100"></td>
                </tr>
                <tr>
                    <td align="right"><span class="ft_14">微信账号：</span></td>
                    <td><input type="text" name="wechat" class="input_100"></td>
                </tr>
                <tr>
                    <td align="right"><span class="ft_14">微博账号：</span></td>
                    <td><input type="text" name="weiboName" class="input_100"></td>
                </tr>
                <tr>
                    <td align="right"><span class="ft_14">微博链接：</span></td>
                    <td><input type="text" name="weiboUrl" class="input_100"></td>
                </tr>
               <tr>
                    <td width="18%" align="right"></td>
                    <td>
                        <a href="javascript:;" class="btn gray_btn" id="be_planner">申请成为规划师</a>
                    </td>
                </tr>
                <tr id="planner_box" class="planner_form_label hide">
                    <td width="18%" align="right"><span class="ft_14">可规划地区：</span></td>
                    <td>
                    <div class="label_box hide"><ul class="label clearfix"></ul></div>
                    <input id="last_home" type="text" name="targetCity" class="input_100" placeholder="用分号分隔多个地区"></td>
                </tr>
                <tr class="planner_form_label hide">
                    <td align="right"><span class="ft_14">收费标准：</span></td>
                    <td><input id="payfor" name="fees" type="text" class="input_100"></td>
                </tr>
                <tr>
                    <td></td>
                    <td><input type="submit" class="btn blue_btn" value="提交修改" /></td>
                    <td></td>
                </tr>
            </table>
            </form>
        </div>
    </div>
</div>
</@page>