/*
 * 懒人游 注册页面交互
 * mr.sheak@gmail.com 20140626
 */



;$(function(){
/*********************************************************/
    

    // 修改密码 sb_pwd
    if($(".changepwd") && $(".changepwd").length > 0){
        var RegForm = $(".changepwd").Validform({
            tiptype:4,
            btnSubmit:"#sb_pwd",
            label:".label",
            showAllError:true,
            datatype:{
                "zh1-6":/^[\u4E00-\u9FA5\uf900-\ufa2d]{1,6}$/
            },
            ajaxPost:true,
            callback:function(data){
                if(data.status == "y"){
                   // window.location.href = "/regist/waitEmailVerify";
                    
                    $("#sb_pwd").closest("td").append(" 密码修改成功，请重新登录");
                    setTimeout(function(){
                        window.location.href = data.url;
                    } , 1000);
                }else{
                    alert(data.info);
                }
            }
        });

        RegForm.addRule([
            {
                ele:"#u_pwd",
                //ajaxurl:"/regist/checkPwd",
                datatype:"*6-16",
                nullmsg:"请输入当前密码！",
                errormsg:"请输入正确的当前密码！"
            },
            {
                ele:"#u_newPwd",
                datatype:"*6-16",
                nullmsg:"请输入新密码！",
                errormsg:"密码范围在6~16位之间！"
            },
            {
                ele:"#u_rePwd",
                datatype:"*",
                recheck:"new_passwd",
                nullmsg:"请再输入一次密码！",
                errormsg:"两次输入的密码不一致！"
            }
        ]);
    }

    /*
     * Jcorp 用户头像裁剪
     * sheak 20140925
     */
    var jcrop_api, boundx, boundy,

        // Grab some information about the preview pane
        $preview = $('#preview-pane'),
        $pcnt = $('#preview-pane .preview-container'),
        $pimg = $('#preview-pane .preview-container img'),

        xsize = $pcnt.width(),
        ysize = $pcnt.height();

    var lanCorp = function(img){
        // Create variables (in this scope) to hold the API and image size
        
        $("#target , #preview-pane .jcrop-preview").show().attr("src" , img);
        
        $('#target').Jcrop({
            onChange: updatePreview,
            onSelect: updatePreview,
            aspectRatio: xsize / ysize,
            boxWidth : 500,
            boxHeight : 500
        },
        function() {
            // Use the API to get the real image size
            var bounds = this.getBounds();
            boundx = bounds[0];
            boundy = bounds[1];
            // Store the API in the jcrop_api variable
            jcrop_api = this;

            // Move the preview into the jcrop container for css positioning
            $preview.appendTo(jcrop_api.ui.holder);
        });

        function updatePreview(c) {
            if (parseInt(c.w) > 0) {
                var rx = xsize / c.w;
                var ry = ysize / c.h;

                $pimg.css({
                    width: Math.round(rx * boundx) + 'px',
                    height: Math.round(ry * boundy) + 'px',
                    marginLeft: '-' + Math.round(rx * c.x) + 'px',
                    marginTop: '-' + Math.round(ry * c.y) + 'px'
                });
            }

            //console.log(jcrop_api.tellSelect());//获取选框的值（实际尺寸）
            //console.log(jcrop_api.tellScaled());//获取选框的值（界面尺寸）
            
        };
    }


    if($(".file_upload") && $(".file_upload").length > 0){

        var _this = $(this);

        $(".file_upload").uploadify({
            'height'        : 42, 
            'width'         : 391, 
            //'queueID'       : 'fileQueue',
            'multi'         : false,
            'folder'        : '20140714',
            'cancelImage'   : '/resources/imgs/plug/uploadify-cancel.png',
            'simUploadLimit': '5',
            'sizeLimit'     : '100000',
            'method'        : 'POST',
            'fileExt'       : '*.jpg;*.gif;*.png', //控制可上传文件的扩展名
            'fileDesc'      : 'jpg、gif、png文件', //控制可上传文件的扩展名描述，两者需要同时使用  
            'buttonText'    : '',
            'swf'           : '/resources/js/plug/uploadify.swf',
            'uploader'      : 'http://img.lanrenyou.com/upload/submit',
            'fileDataName'  : 'lan_fileData',
            'auto'          : true,
            'fileTypeDesc' : 'Image Files',
            'fileTypeExts' : '*.gif; *.jpg; *.png',
            'onUploadStart' : function(file) {
                //开始上传之前的校验工作
                //console.log("uploading");
            },
            'onUploadSuccess':function(file, data, response){
                var _d = jQuery.parseJSON(data);

                if(_d.status == "y"){
                    var _img = _d.url;
                    $("#u_now_face_l").attr("src" , _img);
                    $("#avatar").val(_img);

                    // 生成裁剪元素
                    lanCorp(_img);
                }else{
                    alert(_d.info);
                }
                
            },
            'onUploadError':function(file, errorCode, errorMsg, errorString){
                //console.log(file, errorCode, errorMsg, errorString)
            }
        });
        
    }



    // 更新用户头像
    // 提交相关数据
    $("#up_face_btn").click(function(){
        alert(jcrop_api.tellScaled());
        //lanCorp("http://www.lanrenyou.com/wp-content/uploads/2014/09/original_S4Nz_58c6000001ba125f_l.jpg");
    });


    



    


    

    











/*********************************************************/
});


