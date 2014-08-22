<@page  title="申请成为规划师-懒人游" description=""  keywords="" 
	assets=[
	"/resources/css/regist.css",
	"/resources/js/regist.js"
	]
	>


<!-- regist_box -->
<div id="regist_box">
    <div class="form_mail_box">
        <h1>成为规划师！</h1>
        <h3>您好！您目前还不是规划师，暂时不能发游记，请先申请成为规划师！</h3>

        <div class="form_mail_msg">
        	<form action="/user/setting/applyToPlanner" method="POST" >
            <table class="be_planner_form" cellpadding="0" cellspacing="0" border="0" width="90%">
                <tr id="planner_box">
                    <td width="18%" align="right"><span class="ft_14">可策划地区：</span></td>
                    <td>
                    <div class="label_box hide"><ul class="label clearfix"></ul></div>
                    <input id="last_home" name="targetCity" type="text" class="input_100" placeholder="用分号分隔多个地区"></td>
                </tr>
                <tr>
                    <td width="18%" align="right"><span class="ft_14">收费标准：</span></td>
                    <td><input id="payfor" name="fees" type="text" class="input_100"></td>
                </tr>
                <tr>
                    <td>
                    <input type="submit" class="btn blue_btn big_btn" value="申请成为规划师" />
                    </td>
                </tr>
            </table>
            </form>
        </div>

    </div>
</div>


</@page>