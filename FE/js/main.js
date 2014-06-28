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



















/*********************************************************/
});