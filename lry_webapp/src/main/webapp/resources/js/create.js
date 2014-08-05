/*
 *
 * 
 */

$(function(){
    //移动相册的时候传相册顺序数据
    var arryAlbumOrder = [];
    var postAlbumOrder = function(){
         for(var i =0;i<$('.clone').length;i++){
           if($('.clone').eq(i).attr('album_id') != ""){
               arryAlbumOrder.push($('.clone').eq(i).attr('album_id'));
           }
         };
         //console.log(arryAlbumOrder)

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

    // 获取指定规格图片
    var getResizeImg = function(src , tag){
        var srcLen = src.length,
            srcName = src.substr(0 , srcLen-4),
            srcType = src.substr(-4 , 4),
            newSrc = srcName+"_"+tag+".jpg";

        return newSrc;
    }


    // placeholder
    var placeholder = function(nodes,pcolor) {
        if(nodes.length > 0 && !('placeholder' in document.createElement('input'))){
            nodes.each(function(){
                var holder = $(this).attr("placeholder");
                console.log(holder)
            });
        }

        
    }
    //placeholder($(".placeholder") , "#ccc");

    // 图片上传成功后重新渲染页面，创建图片列表
    var setPhotoList = function(imgSrc){
        var newSrc = getResizeImg(imgSrc , "s")

        var imgListStr = '<div class="album_block clone"><div class="a_title"><div class="left img_left"><img src="'+newSrc+'" alt=""></div><div class="right"><textarea name="" id="" cols="30" rows="10"></textarea><a href="javascript:;" class="J_moveUp">向上</a><a href="javascript:;" class="J_moveDown">向下</a><a href="javascript:;" class="J_delete">删除</a></div></div></div>';

        $("#add_photo_box .album_list").append(imgListStr);

        setDisable();
    }

    setDisable();


    // 上传组件
    if($(".file_upload") && $(".file_upload").length > 0){
        $("#c_title , #last_home").val("");

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
                    setTimeout(function(){
                        setPhotoList(imgSrc);
                    } , 800);
                }
                
            },
            'onUploadError':function(file, errorCode, errorMsg, errorString){
                //console.log(file, errorCode, errorMsg, errorString)
            }
        });
        
    }


    // 提交游记
    if($("#create_youji_btn") && $("#create_youji_btn").length > 0){
        $("#create_youji_btn").click(function(){
            var c_title = $("#c_title").val(),
                c_area = $(".label_box").find("li").text() || $("#last_home").val(),
                c_len = $(".album_block:visible").length,
                c_yj = "[";

            if($(".album_block:visible").length > 0){
                $(".album_block:visible").each(function(){
                    var img_src = $(this).find(".img_left img").attr("src"),
                        img_info = $(this).find(".right textarea").val();
                    c_yj +='{"src":"'+img_src+'" , "info" : "'+img_info+'"},';
                }); 
            }
            c_yj+= "]";
            c_yj.replace(",]" , "]");
            //console.log(c_title , c_area , c_yj);

            //return;


            if(!c_title){
                alert("游记标题不能为空!");
                return;
            }if(!c_area){
                alert("请用分号分隔城市！");
                return;
            }if(c_len == 0){
                alert("请上传游记照片");
                return;
            }else{
                $.ajax({
                    url: "/travel/add",
                    type: "POST",
                    data : "title="+c_title+"&area="+c_area+"&imgs="+c_yj,
                    success : function(r){
                        var _d = jQuery.parseJSON(r);
                        if(_d.status == "y"){
                            // 显示弹层
                            //e.preventDefault();
                            //var modalLocation = $(this).attr('data-reveal-id');
                            var t_url = _d.info;
                            $('#create_success .suc_link a').eq(0).attr("href" , t_url);
                            $('#create_success').reveal($(this).data());
                        }else{
                            alert(_d.info);
                        }
                    },error : function(){
                        alert("提交失败，请稍后再试!");
                    }
                });
            }
        });
    }

    // 继续发游记
    $(".suc_link .close_d").click(function(){
         location.reload();
    });


    // 弹层
    // $('a[data-reveal-id]').live('click', function(e) {
    //     e.preventDefault();
    //     var modalLocation = $(this).attr('data-reveal-id');
    //     $('#'+modalLocation).reveal($(this).data());
    // });








});