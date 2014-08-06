

$(function(){

    // MT4S概览 系统要求
    $(".sys_title").toggle(function(){
        $(this).css({"background" : "url(/imgs/sys_bg3_03.jpg) left top no-repeat" , "color" : "#fff"}).next(".sys_cont").show();
    },function(){
        $(this).css({"background" : "url(/imgs/sys_bg1.png) left top no-repeat" , "color" : "#000"}).next(".sys_cont").hide();
    });


    // MT4下载
    $(".mt5").toggle(function(){
        $(this).css("background" , "#909090").next(".mt9").show();
    },function(){
        $(this).css("background" , "#fff").next(".mt9").hide();
    });
});