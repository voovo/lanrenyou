/*
 * 懒人游 游记详情
 * mr.sheak@gmail.com 20140706
 */

;$(function(){
/*********************************************************/

    // 喜欢
    $(".like_ico").one("click" , function(){
        $(this).removeClass("like_ico").addClass("liked_ico").find("i").text(parseInt($(this).find("i").text())+1);
    });

    // 分享
    $(".share_ico").click(function(){
        $(this).closest(".detail_info").find(".share_dialogbox").slideDown();
    });


    // 动态设置分享按钮链接
    $(".share_dialogbox a").click(function(){
        var clsN = $(this).attr("class"),
            shareTxt = encodeURIComponent($(this).closest(".detail_item").find(".detail_info p").text()),
            url = window.location.href,
            pic = $(this).closest(".detail_item").find(".detail_img img").attr("src");

        if(shareTxt.length == 0) shareTxt = "关注 #懒人游# ， 看当地人如何旅行，让游记作者为你规划行程，死人定制你的专属旅程！";

        switch(clsN){
            case "weibo_ico" :
                window.open("http://service.weibo.com/share/share.php?title="+shareTxt+"&url="+url+"&pic="+pic);
                break;

            case "qq_t_ico" :
                window.open("http://v.t.qq.com/share/share.php?title="+shareTxt+"&url="+url+"&site=http://www.lanrenyou.com&pic="+pic);
                break;

            case "rr_ico" :
                window.open("http://share.renren.com/share/buttonshare.do?link="+url+"&title="+shareTxt);
                break;
        }
    });

    // 设置悬浮分享
    if($(".bdsharebuttonbox") && $(".bdsharebuttonbox").length > 0){
        var setShareTips = function(){
            var d_left = $(".detail_left").offset().left;
            //console.log(d_left)
            $(".bdsharebuttonbox").css("left" , d_left+800);
        }
        setShareTips();

        $(window).resize(function() {
            setShareTips();
        });
    }
    
  
















/*********************************************************/
});