;$(function(){
/**********************************************************/ 
    $(".list-bank li").click(function(){
        $(this).addClass("cur").siblings('li').removeClass("cur");
    });

    $(".link-more").toggle(function(){
        $(".list-bank").animate({"height" : "+=400"} , 200)
    },function(){
        $(".list-bank").animate({"height" : "-=400"} , 200)
    });




/**********************************************************/ 
});