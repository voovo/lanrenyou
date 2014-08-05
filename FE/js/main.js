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

    /*
     * 返回登陆页弹层
     * 
     */
    var backToLogin = function(url){
        if(!$("#back_to_login") || $("#back_to_login").length == 0){
            $("body").append('<div id="back_to_login" class="reveal-modal"><h2>请先登陆懒人游!</h2><p><a class="fromUrl" href="">马上登陆</a> | <a target="_blank" href="http://www.lanrenyou.com/regist/toPage">立即注册</a></p><a class="close-reveal-modal close_d">&#215;</a></div>');
        }

        $("#back_to_login .fromUrl").attr("href" , "http://www.lanrenyou.com/login?redir="+url);
        setTimeout(function(){
            $('#back_to_login').reveal($(this).data());
        } , 300);
    }


    // 全站发送私信
    if($(".msg_btn") && $(".msg_btn").length > 0){
        var _msgBtn = $(".msg_btn"),
            _uid = _msgBtn.attr("uid"),
            _msgForm = '<div class="msgForm"><div class="arrow_up"></div><div class="msgTo">给 <i></i> 发私信</div><textarea name="" id="" cols="30" rows="10"></textarea><div class="msgBot clearfix"><div class="msgNotice left green hide">发送成功!</div><div class="right"><span class="sendMsgBtn">发送</span><span class="closeMsgBtn">关闭</span></div></div></div>';

        _msgBtn.click(function(){
            $(".msgForm").hide();
            var _uname = $(this).attr("username");

            if($(this).parent().find(".msgForm").length > 0){
                $(this).parent().find(".msgForm").show();
            }else{
                $(this).parent().append(_msgForm);
            }

            $(this).parent().find(".msgForm .msgTo i").html(_uname);

            $(".msgForm").find("textarea").focus();
            $(".msgForm").find(".msgNotice").hide();
            
            var sb = $(".sendMsgBtn");
            sb.one("click" , function(){
                // 发送私信
                var _cont = $(this).closest(".msgForm").find("textarea").val() , 
                    _notice = $(this).closest(".msgForm").find(".msgNotice");
                if(_cont.length == 0){
                    _notice.show().html('<span class="error">请输入私信内容</span>');
                }else{
                    $.ajax({
                        url : "/user/"+_uid+"/msg/add",
                        data : {"toUid" : _uid , "content" : _cont},
                        success : function(r){
                            var _d = jQuery.parseJSON(r);
                            console.log(r)
                            if(_d.status == "y"){
                                _notice.show().html('发送成功');
                                setTimeout(function(){
                                    $(".msgForm").hide();
                                } , 500);
                            }else{
                                _notice.show().html('<span class="error">'+_d.info+'</span>');
                            }
                        }
                    });
                    
                }
                
            });


            // 关闭浮层
            $(".closeMsgBtn").click(function(){
                $(".msgForm").hide();
            });
        });
    }


    /*
     * 关注
     */
    var addFun = function(uid , ele){
        $.ajax({
            url : "/user/"+uid+"/fans/add",
            success : function(r){
                var _d = jQuery.parseJSON(r);

                if(_d.status == "n" && _d.info == "请先登陆"){
                    backToLogin(window.location.href);
                }else {
                    ele.removeClass("add_btn").addClass("added_btn");
                    //console.log("ok");
                }
                //console.log(r);
            }
        });
    }
    var delFun = function(uid , ele){
        $.ajax({
            url : "/user/"+uid+"/fans/del",
            success : function(r){
                var _d = jQuery.parseJSON(r);

                if(_d.status == "n" && _d.info == "请先登陆"){
                    backToLogin(window.location.href);
                }else {
                    ele.removeClass("added_btn").addClass("add_btn");
                    //console.log("ok");
                }
                //console.log(r);
            }
        });
    }

    $(".add_btn , .added_btn").click(function(){
        var _this = $(this),
            _uid = $(this).attr("uid");

        if(_this.hasClass("add_btn")){
            addFun(_uid , _this);
        }else{
            delFun(_uid , _this);
        }
    });

    /*
     * 游记收藏
     * 
     */
    // 添加收藏
    $(".add_fav , .added_fav").click(function(){
        var _this = $(this),
            _tid = $(this).attr("tid");


        if(_this.hasClass("add_fav")){
            $.ajax({
                url : "/travel/"+_tid+"/collect",
                success : function(r){
                    var _d = jQuery.parseJSON(r);

                    if(_d.status == "y"){
                        _this.removeClass("add_fav").attr("title" , "取消收藏").addClass("added_fav").find("span").text(parseInt(_this.find("span").text())+1);
                        if(_this.find("i")){
                            _this.html('<i class="ico icon_faved"></i>取消收藏');
                        }
                    }else{
                        if(_d.info == "没有获取当前用户信息"){
                            backToLogin(window.location.href);
                        }else{
                            alert(_d.info);    
                        }
                        
                    }
                }
            });
        }else{
            $.ajax({
                url : "/travel/"+_tid+"/uncollect",
                success : function(r){
                    var _d = jQuery.parseJSON(r);

                    if(_d.status == "y"){
                        _this.removeClass("added_fav").attr("title" , "加入收藏").addClass("add_fav").find("span").text(parseInt(_this.find("span").text())-1);
                        if(_this.find("i")){
                            _this.html('<i class="ico icon_fav"></i>收藏');
                        }
                    }else{
                        if(_d.info == "没有获取当前用户信息"){
                            backToLogin(window.location.href);
                        }else{
                            alert(_d.info);    
                        }
                        
                    }
                }
            });
        }
    });


















    // 百度统计代码
    var _hmt = _hmt || [];
    (function() {
      var hm = document.createElement("script");
      hm.src = "//hm.baidu.com/hm.js?d25d1f37eff48959747a5a9cd62a4d68";
      var s = document.getElementsByTagName("script")[0]; 
      s.parentNode.insertBefore(hm, s);
    })();

/*********************************************************/
});