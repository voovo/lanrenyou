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
    })


















/*********************************************************/
});