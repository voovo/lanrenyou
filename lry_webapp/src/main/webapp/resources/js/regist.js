/*
 * 懒人游 注册页面交互
 * mr.sheak@gmail.com 20140626
 */
var lablId = -1 , ele;
;$(function(){
/*********************************************************/

    //验证码
    var Captcha = function(){
        // 设置验证码
        var getCaptcha = function(){
            var d = new Date();
            $("#form_code img").attr("src" , "/captcha/getCaptcha?r="+d.getTime());
        }
        $("#form_code").hide();
        getCaptcha();
        setTimeout(function(){
            $("#form_code").trigger("click");
            $("#form_code").fadeIn();
        } , 200);
        // 刷新验证码
        $("#changeCode a , #form_code").click(function(){
            getCaptcha();
        });
    }


    // 注册表单校验
    //$(".registerform").Validform();
    if($(".registerform") && $(".registerform").length > 0){
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
                if(data.status == "y"){
                    //$.Hidemsg();
                    window.location.href = "/regist/waitEmailVerify";
                }else{
                    //console.log(data.info);
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
                errormsg:"两次输入的密码不一致！"
            },{
                ele:"#captcha",
                ajaxurl:"/captcha/checkCaptcha",
                datatype:"*4-4",
                nullmsg:"请输入验证码！",
                errormsg:"请检查验证码！"
            }
        ]);

        Captcha();

    }



    if($(".loginform") && $(".loginform").length > 0){
        var RegForm = $(".loginform").Validform({
            tiptype:4,
            btnSubmit:"#login_btn",
            label:".label",
            showAllError:true,
            datatype:{
                "zh1-6":/^[\u4E00-\u9FA5\uf900-\ufa2d]{1,6}$/
            },
            ajaxPost:true,
            callback:function(data){
                if(data.status == "y"){
                    //$.Hidemsg();
                    window.location.href = data.info;
                }else{
                    alert(data.info);
                }
            }
        });

        RegForm.addRule([
            // {
            //     ele:"#login_mail",
            //     datatype:"*",
            //     nullmsg:"请输入邮箱地址！"
            // },
            // {
            //     ele:"#login_pwd",
            //     datatype:"*6-16",
            //     nullmsg:"请填写密码！",
            //     errormsg:"密码范围在6~16位之间！"
            // },
            {
                ele:"#captcha",
                ajaxurl:"/captcha/checkCaptcha",
                datatype:"*4-4",
                nullmsg:"请输入验证码！",
                errormsg:"请检查验证码！"
            }
        ]);

        Captcha();
    }


    // 重发验证邮件
    $("#resend_mail").one("click" , function(){
        var u_mail = $(".user_reg_mail").text();

        $.ajax({
            url:"/regist/sendVerifyEmail",
            data: {"mail" : u_mail},
            success : function(r){
                console.log(r);
                if(r.code == 1){
                    $("#resend_mail").html("发送成功，请查收邮件！").unbind();
                }
            }
        })
    });
    


    

    











/*********************************************************/
});