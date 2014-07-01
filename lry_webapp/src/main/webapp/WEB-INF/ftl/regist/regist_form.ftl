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
            <table cellpadding="0" cellspacing="0" border="0">
                <tr>
                    <td width="18%" align="right"><span class="ft_14">昵称：</span></td>
                    <td><input type="text" class="input_220"></td>
                </tr>
                <tr>
                    <td align="right"><span class="ft_14">邮箱：</span></td>
                    <td><i>mr.sheak@gmail.com</i></td>
                </tr>
                <tr>
                    <td align="right"><span class="ft_14">自我介绍：</span></td>
                    <td><textarea class="textarea" name="" id="" cols="30" rows="10" placeholder="请输入自我介绍 不超过200字"></textarea></td>
                </tr>
                <tr>
                    <td align="right"><span class="ft_14">现居住地：</span></td>
                    <td>
                        <select name="" id="" class="select">
                            <option value="0">城市</option>
                            <option value="1">加州</option>
                            <option value="2">旧金山</option>
                            <option value="3">纽约</option>
                        </select>

                        <select name="" id="" class="select">
                            <option value="0">地区</option>
                            <option value="1">加州</option>
                            <option value="2">旧金山</option>
                            <option value="3">纽约</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td width="18%" align="right"><span class="ft_14">曾居住地：</span></td>
                    <td><input type="text" class="input_100"></td>
                </tr>
                <tr>
                    <td width="18%" align="right"></td>
                    <td>
                        <input type="checkbox" id="be_planner">
                        <label for="be_planner" class="planner_tt">我想成为规划师<span>( 高质量的游记能更快通过申请，现在就去<a href="javascript:;" target="_blank">写游记</a> )</span></label>
                    </td>
                </tr>
                <tr id="planner_box" class="hide">
                    <td width="18%" align="right"><span class="ft_14">可策划地区：</span></td>
                    <td>
                    <div class="label_box hide"><ul class="label clearfix"></ul></div>
                    <input id="last_home" type="text" class="input_100" placeholder="用分号分隔多个地区"></td>
                </tr>
                <tr>
                    <td></td>
                    <td><a href="javascript:;" class="btn blue_btn">提交修改</a></td>
                    <td></td>
                </tr>
            </table>
        </div>
    </div>
</div>
</@page>