<@page  title="修改游记-懒人游" description=""  keywords="" 
	assets=[
	"/resources/css/waper.css",
	"/resources/css/plug/reveal.css",
	"/resources/css/plug/uploadify.css",
	"/resources/js/create.js",
	"/resources/js/plug/jquery.uploadify.min.js",
	"/resources/js/plug/jquery.reveal.js"
	]
	>

<!-- waper_box -->
<div class="waper_box">
    <h1 class="warper_tt create_icon">编辑游记</h1>

    <div class="add_photo_form_box">
        <table>
        	<input type="hidden" name="tid" value="${travelInfo.id!''}" />
            <tr>
                <td width="130" align="right"><i class="rq">*</i>游记标题：</td>
                <td><i class="rq"><input id="c_title" type="text" class="input_620" placeholder="游记标题不超过20字" maxlength="20" value="${travelInfo.title!''}"></i></td>
            </tr>
            <tr>
                <td align="right"><i class="rq">*</i>城市：</td>
                <td>
                    <div class="label_box hide"><ul class="label clearfix"></ul></div>
                    <input id="last_home" type="text" class="input_620 placeholder" placeholder="每个城市请用分号结束" value="${travelInfo.city!''}">
                </td>
            </tr>
        </table>

        <!-- 添加照片 -->
        <div id="add_photo_box">
            <div class="album_list">
                    
                <div id="upBox" class="moveBox" style="top: 0px; z-index: 0;"></div>
                <div id="downBox" class="moveBox" style="z-index: 0; top: 0px;"></div>
				<#if contentList??>
            		<#list contentList as contentMap>
                <div class="album_block clone">
                    <div class="a_title">
                        <div class="left img_left">
                            <img src="${contentMap.get('src')!''}" alt="">
                        </div>
                        <div class="right">
                            <textarea name="" id="" cols="30" rows="10">${contentMap.get('info')!''}</textarea>
                            <a href="javascript:;" class="J_moveUp">向上</a>
                            <a href="javascript:;" class="J_moveDown">向下</a>
                            <a href="javascript:;" class="J_delete">删除</a>
                        </div>
                    </div>
                </div>
					</#list>
				</#if>

            </div>

            <div id="fileQueue"></div>
            <!-- <a href="javascript:;" id="add_yj_photo" class="add_yj_photo file_upload"></a> -->
            <input type="file" name="upload_file" id="add_yj_photo" class="add_yj_photo file_upload" />
        </div>
    </div>

    
    <a id="create_youji_btn" class="btn blue_btn" href="javascript:;" data-reveal-id="create_success" data-animation="fade">发布游记</a>
    

</div>

<!-- 发布成功弹层 -->
<div id="create_success" class="reveal-modal">
    <h2>游记已经发布成功!</h2>
    <p class="suc_link">
        <a href="#">查看该游记</a> | 
        <a href="#">我的游记</a> | 
        <a href="javascript:;" class="close_d">继续发游记</a>
    </p>
    <a class="close-reveal-modal close_d">&#215;</a>
</div>
</@page>