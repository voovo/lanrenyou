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



    // 图片上传成功后重新渲染页面，创建图片列表
    var setPhotoList = function(imgSrc){
        var imgListStr = '';
    }


    //移动相册的时候传相册顺序数据
    var arryAlbumOrder = [];
    var postAlbumOrder = function(){
         for(var i =0;i<$('.clone').length;i++){
           if($('.clone').eq(i).attr('album_id') != ""){
               arryAlbumOrder.push($('.clone').eq(i).attr('album_id'));
           }
         };
         console.log(arryAlbumOrder)

        arryAlbumOrder = [];
    };
    //移动函数
    function move(downTop,up,down,upBox,downBox){
        var upHeight,moveTop,moveDown;
        upHeight = up.height();
        moveTop = downTop +"px";
        moveDown = downTop +upHeight + 10 + "px"  ;
        upBox.animate({top:moveTop},200,function(){
            $(this).html("").css({"z-index":"0","top":"0"});
        });
        downBox.animate({top:moveDown},200,function(){
            $(this).html("").css({"z-index":"0","top":"0"});
            up.insertBefore(down).css({"opacity":"1","filter":"alpha(opacity=100)"});
            down.css({"opacity":"1","filter":"alpha(opacity=100)"});
        });
    };
    var setDisable = function(){
        var sum = $('.J_moveDown').length ;
        $('.J_moveUp').eq(0).addClass('g9').unbind('click');
        $('.J_moveUp').eq(1).removeClass('g9').bind('click');
        $('.J_moveDown').eq(sum-1).addClass('g9').unbind('click');
        $('.J_moveDown').eq(sum-2).removeClass('g9').bind('click');
    };


    //移动元素
    function moveEle(obj){
        var up,down,upTop,downTop,upBox,downBox;
        var father = obj.closest('.album_block'),
            ancestor = father.closest('.album_list'),
            boxHeight = ancestor.height()+"px",
            downBox = $('#downBox'),
            upBox = $('#upBox');
        if(obj.hasClass("J_moveUp")){
            up = father;
            down = father.prev(".album_block");
            another = down.next('.album_block');
            if(down.length == 0){
//                alert("第一个不能上移！");
                return;
            }else{
                upBox.css({"z-index":"1"})
            }
        }else{
            down = father;
            up = father.next(".album_block");
            if(up.length == 0){
//                alert("最后一个不能向下移动");
                return;
            }else{
                downBox.css({"z-index":"1"})
            }

        }
        upTop = up.get(0).offsetTop;
        downTop = down.get(0).offsetTop;
        upBox.css("top",(upTop +"px"));
        downBox.css("top",(downTop+"px"));
        up.clone().addClass("throw-spacing").appendTo(upBox);
        down.clone().addClass("throw-spacing").appendTo(downBox);
        up.css({"opacity":"0","filter":"alpha(opacity=0)"});
        down.css({"opacity":"0","filter":"alpha(opacity=0)"});
        move(downTop,up,down,upBox,downBox);
        setTimeout(function(){
            postAlbumOrder();
            setDisable();
        },300);

    };

    setDisable();

    //move up
    $(document).delegate('.J_moveUp','click',function(){
        var that = $(this);
        moveEle(that);
    });
    // move down
    $(document).delegate('.J_moveDown','click',function(){
        var that = $(this);
        moveEle(that);
    });
    // delete
    $(document).delegate('.J_delete','click',function(){
        var that = $(this);
        that.closest(".album_block").fadeOut();
    });




    // 上传组件
    if($(".file_upload") && $(".file_upload").length > 0){
        var _this = $(this),
            imgArr = new Array();
        $(".file_upload").uploadify({
            'height'        : 50, 
            'width'         : 155, 
            'queueID'       : 'fileQueue',
            'multi'         : true,
            'folder'        : '20140714',
            'cancelImage'   : '/resources/imgs/plug/uploadify-cancel.png',
            'simUploadLimit': '5',
            'sizeLimit'     : '100000',
            'method'        : 'POST',
            'fileExt'       : '*.jpg;*.gif;*.png', //控制可上传文件的扩展名
            'fileDesc'      : 'jpg、gif、png文件', //控制可上传文件的扩展名描述，两者需要同时使用  
            'buttonText'    : '添加照片',
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
                    var imgSrc = _d.url;
                    // 图片上传成功后，创建dom结构
                    setPhotoList(imgSrc);
                }
                
            },
            'onUploadError':function(file, errorCode, errorMsg, errorString){
                console.log(file, errorCode, errorMsg, errorString)
            }
        });
        
    }
    



















/*********************************************************/
});