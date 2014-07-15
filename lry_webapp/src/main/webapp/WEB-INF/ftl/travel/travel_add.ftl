<@page  title="新建游记-懒人游" description=""  keywords="" 
	assets=[
	"/resources/css/waper.css",
	"/resources/js/waper.js",
	"/resources/css/plug/uploadify.css",
	"/resources/js/plug/jquery.uploadify.min.js"
	]
	>


<!-- waper_box -->
<div class="waper_box">
    <h1 class="warper_tt create_icon">撰写游记</h1>

    <div class="add_photo_form_box">
        <table>
            <tr>
                <td width="130" align="right"><i class="rq">*</i>游记标题：</td>
                <td><i class="rq"><input type="text" name="title" class="input_620" placeholder="游记标题不超过20字" maxlength="20"></i></td>
            </tr>
            <tr>
                <td align="right"><i class="rq">*</i>城市：</td>
                <td>
                    <div class="label_box hide"><ul class="label clearfix"></ul></div>
                    <input id="last_home" type="text" name="city" class="input_620" placeholder="用分号分隔多个城市">
                </td>
            </tr>
        </table>

        <!-- 添加照片 -->
        <div id="add_photo_box">
            <div id="fileQueue"></div>
            <div>
            </div>
            <a href="javascript:;" id="add_yj_photo" class="add_yj_photo file_upload">添加照片</a>
        </div>
    </div>
</div>
</@page>