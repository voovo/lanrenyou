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
                    //$.Hidemsg();
                   // window.location.href = "/regist/waitEmailVerify";
                }else{
                    //console.log(data.info);
                }
            }
        });

        RegForm.addRule([
            {
                ele:"#u_pwd",
                ajaxurl:"/regist/checkPwd",
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
                recheck:"u_newPwd",
                nullmsg:"请再输入一次密码！",
                errormsg:"两次输入的密码不一致！"
            }
        ]);
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
                //console.log(data)
                
            },
            'onUploadError':function(file, errorCode, errorMsg, errorString){
                //console.log(file, errorCode, errorMsg, errorString)
            }
        });
        
    }



    


    

    











/*********************************************************/
});