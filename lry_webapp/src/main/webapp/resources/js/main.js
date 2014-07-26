/*
 * 懒人游 公用交互
 * mr.sheak@gmail.com 20140626
 */

;$(function(){
/*********************************************************/
    // 成为规划师
    $("#be_planner").change(function(){
        if($(this).attr("checked")){
            $("#planner_box").fadeIn();
        }else{
            $("#planner_box").fadeOut();
        }
    });
    
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

    // input标签
    if($("#last_home") && $("#last_home").length > 0){
        // 多标签分词显示
        var isNan = function(obj) {
            try {
                return obj == 0 ? true: !obj
            } catch(e) {
                return true;
            }
        }

        var deletes = function(id) {
            $("#" + id).remove();
            var li_id = $(".label li:last-child").attr('id');
            if (li_id == undefined) {
                $(".label_box").css("display", "none");
            }
        }

        $("#last_home").keyup(function(event) {
            if (event.keyCode == 186 || event.keyCode == 59) {
                var str = $("#last_home").val().substr(0 , $("#last_home").val().length-1);
                if (isNan(str) != true) {
                    var li_id = $(".label li:last-child").attr('id');
                    if (li_id != undefined) {
                        li_id = li_id.split('_');
                        li_id = parseInt(li_id[1]) + 1;
                    } else {
                        li_id = 0;
                    }
                    $(".label_box").css("display", "block");
                    var text = "<li id='li_" + li_id + "'>" + str + "<i class='close' title='删除'></i></li>";
                    $(".label").append(text).find(".close").click(function(){
                        var id = $(this).closest('li').attr("id");
                        deletes(id);
                    });
                }
                $("#last_home").val(" ");
            }
        });
    }


    // 全站发送私信
    if($(".msg_btn") && $(".msg_btn").length > 0){
        var _msgBtn = $(".msg_btn"),
            _msgForm = '<div class="msgForm"><div class="arrow_up"></div><div class="msgTo">给 <i>shell_corona</i> 发私信</div><textarea name="" id="" cols="30" rows="10"></textarea><div class="msgBot clearfix"><div id="msgNotice" class="left green hide">发送成功!</div><div class="right"><span class="sendMsgBtn">发送</span><span class="closeMsgBtn">关闭</span></div></div></div>';

        _msgBtn.click(function(){
            if($(this).parent().find(".msgForm").length > 0){
                $(this).parent().find(".msgForm").show();
            }else{
                $(this).parent().append(_msgForm);
            }

            $(".msgForm").find("textarea").focus();
            
            var sb = $(".sendMsgBtn");
            sb.click(function(){
                // 发送私信
                $("#msgNotice").show();
            });


            // 关闭浮层
            $(".closeMsgBtn").click(function(){
                $(".msgForm").hide();
            });
        });
    }

















/*********************************************************/
});