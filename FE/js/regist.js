/*
 * 懒人游 首页交互
 * mr.sheak@gmail.com 20140626
 */
var lablId = -1 , ele;
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


    // 成为规划师
    $("#be_planner").change(function(){
        if($(this).attr("checked")){
            $("#planner_box").fadeIn();
        }else{
            $("#planner_box").fadeOut();
        }
    });


    // 注册表单校验
    //$(".registerform").Validform();


    var RegForm = $(".registerform").Validform({
        tiptype:4,
        btnSubmit:"#reg_btn",
        label:".label",
        showAllError:true,
        datatype:{
            "zh1-6":/^[\u4E00-\u9FA5\uf900-\ufa2d]{1,6}$/
        },
        ajaxPost:true,
        callback:function(data){

            console.log(data);
            if(data.code == 1){
                $.Hidemsg();
                window.location.href = "/regsit/waitEmailVerify";
            }else{
                alert(data.msg);
            }
        }
    });


    RegForm.addRule([
    {
        ele:"#reg_email",
        ajaxurl:"/regist/checkEmail",
        datatype:"e",
        nullmsg:"请输入邮箱地址！",
        errormsg:"请输入正确的邮箱地址！"
    },
    {
        ele:"#reg_pwd",
        datatype:"*6-16",
        nullmsg:"请设置密码！",
        errormsg:"密码范围在6~16位之间！"
    },
    {
        ele:"#reg_repwd",
        datatype:"*",
        recheck:"reg_pwd",
        nullmsg:"请再输入一次密码！",
        errormsg:"您两次输入的账号密码不一致！"
    },{
        ele:"#reg_code",
        datatype:"n4-4",
        ajaxurl:"valid.php",
        nullmsg:"请输入验证码！",
        errormsg:"请检查验证码！"
    }
]);

    











/*********************************************************/
});