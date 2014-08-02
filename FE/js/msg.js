/*
 * 懒人游 消息中心
 * mr.sheak@gmail.com 20140721
 */

;$(function(){
/*********************************************************/
    // 鼠标滑过显示边框
    $("#u_fans_list li").hover(function(){
        $(this).css("border" , "3px solid #adc9e4");
    }, function(){
        $(this).css("border" , "3px solid #d6d6d6");
    });

    // 全选
    $("#sel_all").click(function(){
        if($(this).attr("checked") == "checked"){
            $("#u_msg_list input").attr("checked",true); 
        }else{
            $("#u_msg_list input").attr("checked",false); 
        }
        
    });

    // 删除消息
    var deleteMsg = function(ele , uid , sid){
        $.ajax({
            url : "/user/"+uid+"/msg/receiverDel?id="+sid,
            success : function(r){
                var _d = jQuery.parseJSON(r);

                if(_d.status == "y"){
                    ele.closest("li").fadeOut(function(){
                        $(this).remove();
                    });
                }
            }
        });
    }
    $("#u_msg_list li a.delete_ico").click(function(){
        var _this = $(this),
            _sid = _this.closest("li").attr("id"),
            _uid = $("#uid").val();

        deleteMsg(_this , _uid , _sid );
    });

    // 批量删除
    $(".removeAll").click(function(){
        var _uid = $("#uid").val();
       // console.log($("#u_msg_list input:checkbox:checked").length)
        $("#u_msg_list input:checkbox:checked").each(function(){
            var _this = $(this),
                _sid = _this.closest("li").attr("id");
            deleteMsg(_this , _uid , _sid );
        });

    });


    // 设置已读样式
    $(".setRead").click(function(){
        var _sids = new Array(),
            _uid = $("#uid").val();

        $("#u_msg_list input:checkbox:checked").each(function(){
            var _this = $(this),
                _sid = _this.closest("li").attr("id");
            
            $.ajax({
                url : "/user/"+_uid+"/msg/hasRead?ids="+_sid,
                success : function(r){
                    var _d = jQuery.parseJSON(r);

                    if(_d.status == "y"){
                        _this.closest("li").removeClass("unread");
                    }
                }
            });

        });


    });


















/*********************************************************/
});