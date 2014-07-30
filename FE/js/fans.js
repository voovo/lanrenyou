/*
 * 懒人游 公用交互
 * mr.sheak@gmail.com 20140722
 */

;$(function(){
/*********************************************************/
    // 鼠标滑过显示边框
    $("#u_fans_list li").hover(function(){
        $(this).css("border" , "3px solid #adc9e4");
    }, function(){
        $(this).css("border" , "3px solid #d6d6d6");
    });


    // 取消关注
    var delFriend = function(uid , ele){
        $.ajax({
            url : "/user/"+uid+"/fans/del",
            success : function(r){
                var _d = jQuery.parseJSON(r);
                console.log(r);
                if(_d.status == "n" && _d.info == "请先登陆"){
                    backToLogin(window.location.href);
                }else {
                    ele.closest("li").fadeOut(function(){
                        $(this).remove();
                    });
                    //console.log("ok");
                }
                //console.log(r);
            }
        });
    }

    $(".remove_add").click(function(){
        var _this = $(this),
            _li = _this.closest("li"),
            _uid = _li.attr("id");

        delFriend(_uid , _this);
    });


















/*********************************************************/
});