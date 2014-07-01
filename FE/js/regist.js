/*
 * 懒人游 首页交互
 * mr.sheak@gmail.com 20140626
 */
var lablId = -1;
;$(function(){
/*********************************************************/
    
    // 曾居住地
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
        if (event.keyCode == 186) {
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
    })


    // var addlabl = function(id) {
    //     if (lablId == id) {
    //         return;
    //     }
    //     lablId = id;
    //     var str = $("#add_" + id).text();
    //     var li_id = $(".label li:last-child").attr('id');
    //     if (li_id != undefined) {
    //         li_id = li_id.split('_');
    //         li_id = parseInt(li_id[1]) + 1;
    //     } else {
    //         li_id = 0;
    //     }
    //     $(".label_box").css("display", "block");
    //     var text = "<li id='li_" + li_id + "'>" + str + "<input type='hidden' name='label[" + li_id + "].name' value='" + str + "'></li>";
    //     $(".label").append(text).find("li a").click(function(){
    //         var id = $(this).attr("id");
    //         deletes(id);
    //     });
    // }


    // 成为规划师
    $("#be_planner").change(function(){
        if($(this).attr("checked")){
            $("#planner_box").fadeIn();
        }else{
            $("#planner_box").fadeOut();
        }
    });

    



















/*********************************************************/
});