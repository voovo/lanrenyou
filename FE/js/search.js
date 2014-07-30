/*
 * 懒人游 搜索结果页
 * mr.sheak@gmail.com 20140706
 */

;$(function(){
/*********************************************************/

    // 加载规划师游记
    var getPlannerYj = function(){
        $(".planner_list > li").each(function(){
            var _this = $(this);

            setTimeout(function(){
                var _uid = _this.find(".msg_btn").attr("uid");

                //alert(_uid);
                $.ajax({
                    url : "/user/search/publishedTravels" , 
                    data : {"uid" : _uid},
                    success : function(r){
                        _this.find(".planner_yj_list").html(r);
                    }
                });
            } , 500);
        });
    }
    if($(".planner_list") && $(".planner_list").length > 0){
        getPlannerYj();
    }
    
  
















/*********************************************************/
});