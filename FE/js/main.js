/*
 * 懒人游 公用交互
 * mr.sheak@gmail.com 20140626
 */

;$(function(){
/*********************************************************/
    // 更多
    $(".show_more").mouseenter( function(){
        $(this).find(".more_item").stop(true , true).fadeIn();
    });

    $(".show_more").mouseleave(function(){
        $(this).find(".more_item").stop(true , true).fadeOut();
    });

    // 首页消息中心
    $(".msg_tab li").click(function(){
        var index = $(this).index();
        $(this).addClass("cur").siblings("li").removeClass("cur");

        $(".msg_item").eq(index).show().siblings(".msg_item").hide();
    });

    // 显示二维码
    $(".wx").hover(function(){
        $("#erweima").stop(true , true).fadeIn();
    } , function(){
        $("#erweima").stop(true , true).fadeOut();
    });

    // input标签
    if($("#last_home") && $("#last_home").length > 0){
        // 多标签分词显示
        var isNan = function(obj) {
            try {
                return obj == 0 ? true: !obj
            } catch(e) {
                return true;
            }
        }

        var deletes = function(id) {
            $("#" + id).remove();
            var li_id = $(".label li:last-child").attr('id');
            if (li_id == undefined) {
                $(".label_box").css("display", "none");
            }
        }

        $("#last_home").keyup(function(event) {
            if (event.keyCode == 186) {
                var str = $("#last_home").val().substr(0 , $("#last_home").val().length-1);
                if (isNan(str) != true) {
                    var li_id = $(".label li:last-child").attr('id');
                    if (li_id != undefined) {
                        li_id = li_id.split('_');
                        li_id = parseInt(li_id[1]) + 1;
                    } else {
                        li_id = 0;
                    }
                    $(".label_box").css("display", "block");
                    var text = "<li id='li_" + li_id + "'>" + str + "<i class='close' title='删除'></i></li>";
                    $(".label").append(text).find(".close").click(function(){
                        var id = $(this).closest('li').attr("id");
                        deletes(id);
                    });
                }
                $("#last_home").val(" ");
            }
        });
    }


    // 上传组件
    $(".file_upload").uploadify({
        'height'        : 50, 
        'width'         : 155, 
        'queueID'       : 'fileQueue',
        'multi'         : true,
        'folder'        : '20140714',
        'cancelImage'   : '/imgs/plug/uploadify-cancel.png',
        'simUploadLimit': '5',
        'sizeLimit'     : '100000',
        'method'        : 'POST',
        'fileExt'       : '*.jpg;*.gif;*.png', //控制可上传文件的扩展名
        'fileDesc'      : 'jpg、gif、png文件', //控制可上传文件的扩展名描述，两者需要同时使用  
        'buttonText'    : '添加照片',
        'swf'           : 'js/plug/uploadify.swf',
        'uploader'      : 'http://www.lanrenyou.com/upload/submit',
        'fileDataName'  : 'lan_fileData',
        'auto'          : true,
        'fileTypeExts'  : '*.*',
        'onUploadStart' : function(file) {
            //开始上传之前的校验工作
            //test
        },
        'onComplete':function(event,ID,fileObj,response,data){
            alert(response) //response为服务器响应数据
        }
    });



















/*********************************************************/
});