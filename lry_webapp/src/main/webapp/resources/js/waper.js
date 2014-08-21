/*
 * 懒人游 公用交互
 * mr.sheak@gmail.com 20140706
 */

;$(function(){
/*********************************************************/
    // 显示作者名片
    $(".author_link").mouseenter(function(){
        $(this).find(".author_info").stop(true , true).fadeIn();
    });
    $(".author_link").mouseleave(function(){
        $(this).find(".author_info").stop(true , true).fadeOut();
    });

    // 鼠标划过显示游记简介
    $(".yj_item").hover(function(){
        $(this).find(".yj_info").stop(true , true).slideDown(100);
    } , function(){
        $(this).find(".yj_info").stop(true , true).slideUp(100);
    });


    // 通用循环游记作者信息浮层
    if($(".yj_list") && $(".yj_list").length > 0){
        var yjItems = $(".yj_list > li");

        yjItems.each(function(){
            var _this = $(this),
                uid = $(this).find(".added_btn , .add_btn").attr("uid");

            // 开始获取游记作者信息
            $.ajax({
                url : "/user/"+uid+"/lastTravels",
                success : function(r){
                    //console.log(r);
                    _this.find(".author_detail").append(r);

                    // 加关注按钮
                    _this.find(".add_btn").click(function(){
                        addFun(uid);
                    });
                }
            });
        });

    }



    // 删除游记
    $(".remove_yj").click(function(){
        var _li = $(this).closest("li"),
            _yId = _li.attr("id");

        $.ajax({
            url : "/travel/"+_yId+"/del",
            success : function(r){
                var _d = jQuery.parseJSON(r);
                console.log(_d)
                if(_d.status == "y"){
                    _li.fadeOut(function(){
                        $(this).remove();
                    });
                }
            }
        })
    });


















/*********************************************************/
});