<@page  title="修改头像-懒人游" description=""  keywords="" 
	assets=[
	"/resources/css/plug/uploadify.css",
	"/resources/js/plug/jquery.uploadify.min.js",
	"/resources/js/profile.js",
	"/resources/css/waper.css"
	]
	>

<!-- waper_box -->
<div class="waper_box">
    <h1 class="warper_tt">个人设置</h1>
    
    <ul class="warper_tab clearfix">
        <li class="cur">个人设置</li>
        <li><a href="/user/${userInfo.id!''}/travelList/list">我的游记</a></li>
        <li><a href="/user/${userInfo.id!''}/msg/list">消息中心 (<i>${headerLetterCnt!'0'}</i>)</a></li>
        <li><a href="/user/${userInfo.id!''}/fans/list">粉丝管理</a></li>
        <li><a href="/user/${userInfo.id!''}/collect/list">我的收藏</a></li>
    </ul>
  

    <!-- 个人信息修改 -->
    <div id="u_profile_box" class="clearfix">
        <ul class="u_info_tab left">
            <li><a href="/user/setting/info">基本资料</a></li>
            <li class="cur">更换头像</li>
            <li><a href="/user/setting/passwd">修改密码</a></li>
        </ul>
        <div class="left u_info_form_box">
        	<form name="Webform" action="/user/setting/avatarSubmit" method="post" enctype="multipart/form-data">
            <table cellpadding="0" cellspacing="0" border="0">
                <tr>
                    <td width="20%" align="right"><span class="ft_14">当前头像：</span></td>
                    <td class="u_face_img" valign="bottom">
                        <img id="u_now_face_l" src="${userInfo.avatar!''}" alt="">
                        <!--
                        <img id="u_now_face_m" src="imgs/user_face.jpg" alt="">
                        <img id="u_now_face_s" src="imgs/user_face.jpg" alt="">
                         -->
                    </td>
                </tr>
                <tr>
                    <td align="right"><span class="ft_14">修改头像：</span></td>
                    <td>
                    <input type="file" name="uploadFile" id="u_face" class="file_upload" />
                    </td>
                </tr>

                <tr>
                    <td></td>
                    <td><a href="javascript:;" class="btn blue_btn">提交修改</a></td>
                    <td></td>
                </tr>
            </table>
            </form>
        </div>
    </div>



</div>

</@page>