$(function(){

    function tipsShow($ele){
        var $poptip = $ele.parents(".ll").find(".poptip");
        $poptip.show();
    }
    function errorShow($ele,res){
        var $message = $ele.parents(".ll").find(".message");
        var $poptip = $ele.parents(".ll").find(".poptip");
        if(res.error){
            $ele.addClass("error");
            $poptip.show();
            if(res.message){
                $message.html(res.message);
            }
            $message.addClass("error");
        }else{
            $ele.removeClass("error");
            $poptip.hide();
            if(res.message){
                $message.html(res.message);
            }
            $message.removeClass("error");
        }
    }
    //validate-------------------------------------------------
    $("#userName").focus(function(){
        tipsShow($(this));
    }).blur(function(e) {
        var email = $(this).val();
        //var checkEmailAjaxUrl = SystemProp.trustServerUrl+"/user/check-username.json?ln=0";
        var checkEmailAjaxUrl = "/tradingAccounts/verifyEmail";
        var res = check_email(email,checkEmailAjaxUrl);
        errorShow($(this),res);
    });

    $("#password").focus(function(){
        tipsShow($(this));
    }).blur(function(e) {
        var password = $(this).val();
        var res = check_password(password);
        errorShow($(this),res);
    });
    $("#password2").focus(function(){
        tipsShow($(this));
    }).blur(function(e) {
        var password = $("#password").val();
        var password2 = $(this).val();
        if(!password2){
            errorShow($(this),{"error":true,"message":"请输入确认密码"});
            return;
        }
        var res = match_password(password,password2);

        errorShow($(this),res);
    });
    $("#accountType").click(function(){
        tipsShow($(this));
    }).blur(function(){
        if(!$(this).val()){
            errorShow($(this),{"error":true,"message":"请选择账户类型"});
            return;
        }else{
            errorShow($(this),{"error":false});
        }
    });

    $("#phone").focus(function(){
        tipsShow($(this));
    }).blur(function(e) {
        var phone = $(this).val();
        var res = check_phone(phone);

        errorShow($(this),res);
    });
    $("#bgPlan").blur(function(e) {
        var bgPlan = $(this).val();
        if(!$.trim(bgPlan)){
            errorShow($(this),{"error":true,"message":''});
        }
    });

    function formCheck(){
        var $form = $("#form_1");
        $form.find(".input").blur();
        if($form.find(".error:visible").length > 0){
            return false;
        }
        if(!$("#agreement1").is(":checked")){
            alert("请阅读并接受隐私条款 ");
            return false;
        }
        if(!$("#agreement2").is(":checked")){
            alert("请同意接受公司发出的电邮可能包含公司的广告信息 ");
            return false;
        }
//      if($form.find(":checkbox").length !== $form.find(":checkbox:checked").length){
//          alert("请您接受协议");
//          return false;
//      }
        var email = $("#userName").val();
        if(email.indexOf('@qq.com') != -1){
            var isCon = confirm('您填写的电邮可能无法正常接收AETOS发出的重要邮件，请确认是否继续使用该电邮');
            if(!isCon){
                return false;
            }
        }
        return true;
    }

    //提交-----------------------
    $("#submit").click(function(e) {
        e.preventDefault();
        if(!formCheck()){
            return;
        }
        var btnName = $(this).attr("name");
        //防止重复提交
        var isOk = $("#form_1").data("isOk") || true;
        if(!isOk){
            return;
        }else{
            $("#form_1").data("isOk",false);
        }

        var userInfo = form2object("form_1");
        if(btnName === 'demo'){
            userInfo.role = 'CLT';
            userInfo.mt4Type = 1;
        }else if(btnName === 'live'){
            userInfo.role = 'CLT';
            userInfo.mt4Type = 2;
        }else{
            userInfo.role = 'IB_';
            userInfo.mt4Type = 3;
        }
        userInfo.lang = 0;

        //fill utm_affiliatecode--------------------------------------
        // var affiliateCode = getUrlValue("utm_affiliatecode");
        // if(affiliateCode){
        //     setCookie("utm_affiliatecode",affiliateCode);
        // }else{
        //     affiliateCode = getCookie("utm_affiliatecode") || "AETOS";
        // }
        // userInfo.affiliateCode = affiliateCode;
        $("#submit").text("正在处理中");
        $("#form_1").submit();
        // $.ajax({
        //     url : $("#form_1").attr("action"),
        //     type : "POST",
        //     async : false,
        //     dataType:'json',
        //     data : userInfo,
        //     success: function(rs){
        //         if(rs == 0){
        //             $("#submit").text("完成注册");
        //             $("#form_1").data("isOk",true);
        //             if(btnName == 'demo'){
        //                 window.location.href = "/account/register-demo/register-demo-result.html";
        //             }else if(btnName == 'live'){   //live-register-2
        //                 window.location.href = "/account/register-live/register-live-info.html";
        //             }
        //         }else{
        //             alert(rs);
        //         }
        //     }
        // });

    });


});