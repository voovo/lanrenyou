/*
 * 懒人游 搜索结果页
 * mr.sheak@gmail.com 20140706
 */

;$(function(){
/*********************************************************/

    // 加载规划师游记
    var getPlannerYj = function(){
        $(".planner_list").each(function(){
            var _this = $(this),
                _uid = $(this).find(".add_btn , .added_btn").attr("uid");

            $.ajax({
                url : "/user/search/publishedTravels" , 
                data : {"uid" : _uid},
                success : function(r){
                    _this.find(".planner_yj_list").html(r);
                }
            })
        });
    }
    if($(".planner_list") && $(".planner_list").length > 0){
        getPlannerYj();
    }
    
  
















/*********************************************************/
});