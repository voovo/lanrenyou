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


    /*
     * 返回登陆页弹层
     * 
     */
    var backToLogin = function(url){
        if(!$("#back_to_login") || $("#back_to_login").length == 0){
            $("body").append('<div id="back_to_login" class="reveal-modal"><h2>请先登陆懒人游!</h2><p><a class="fromUrl" href="">马上登陆</a> | <a target="_blank" href="http://www.lanrenyou.com/regist/toPage">立即注册</a></p><a class="close-reveal-modal close_d">&#215;</a></div>');
        }

        $("#back_to_login .fromUrl").attr("href" , "http://www.lanrenyou.com/login?redir="+url);
        setTimeout(function(){
            $('#back_to_login').reveal($(this).data());
        } , 300);
    }


    // 通用循环游记作者信息浮层
    if($(".yj_list") && $(".yj_list").length > 0){
        var yjItems = $(".yj_list > li");

        yjItems.each(function(){
            var _this = $(this),
                uid = $(this).attr("uid");

            // 开始获取游记作者信息
            $.ajax({
                url : "/user/"+uid+"/lastTravels",
                success : function(r){
                    //console.log(r);
                    _this.find(".author_detail").append(r);

                    // 加关注按钮
                    _this.find(".add_btn").click(function(){
                        $.ajax({
                            url : "/user/"+uid+"/fans/add",
                            success : function(r){
                                var _d = jQuery.parseJSON(r);

                                if(_d.status == "n" && _d.info == "请先登录"){
                                    backToLogin(window.location.href);
                                }else {
                                    $(this).attr("class" , "added_btn").unbind("click");
                                    //console.log("ok");
                                }
                                console.log(r);
                            }
                        })
                    });
                }
            });
        });

    }


















/*********************************************************/
});