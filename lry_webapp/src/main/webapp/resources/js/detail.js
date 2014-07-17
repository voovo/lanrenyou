/*
 * 懒人游 游记详情
 * mr.sheak@gmail.com 20140706
 */

;$(function(){
/*********************************************************/
    // 收藏接口
    $(".detail_left h2 .right a").one("click" , function(){
        $(this).removeClass("add_fav").addClass("added_fav").find("span").text(parseInt($(this).find("span").text())+1);
        
        $.ajax({
            url : "",
            success : function(r){
                $(this).removeClass("add_fav").addClass("added_fav").find("span").text(parseInt($(this).find("span").text())+1);
            }
        });
    });
    
















/*********************************************************/
});