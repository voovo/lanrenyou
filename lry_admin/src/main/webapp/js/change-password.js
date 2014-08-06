function errorShow(c, b) {
    var a = c.parents(".ll").find(".message");
    var d = c.parents(".ll").find(".poptip");
    if (b.error) {
        c.addClass("error");
        d.show();
        if (b.message) {
            a.html(b.message)
        }
        a.addClass("error")
    } else {
        c.removeClass("error");
        d.hide();
        if (b.message) {
            a.html(b.message)
        }
        a.removeClass("error")
    }
}
$(function() {
    $("input[name='pstype']").change(function(c) {
        $(".password_type").find(".checked").removeClass("checked");
        var b = $("input[name='pstype']:checked");
        b.parents(".label").addClass("checked");
        $(".password_content>div").hide();
        $("#" + b.val()).show()
    });
    $("input[name='pstype']").change();
    $("#oldPassword,#newPassword,#loginPassword,#investPassword,#newPassword1").focus(function(){
        $(this).closest('em').next("em.cx").find(".poptip").css("display","block");
    }).blur(function(d) {
        var b = $(this).val();
        var c = check_password(b);
        errorShow($(this), c)
    });
    $("#newPassword1").blur(function(b) {
        if ($(this).val() != $("#newPassword").val()) {
            errorShow($(this), {
                error: true,
                message: "\u4e24\u6b21\u5bc6\u7801\u4e0d\u4e00\u81f4"
            })
        } else {
            errorShow($(this), {
                error: false,
                message: "\u8bf7\u518d\u6b21\u8f93\u5165\u65b0\u767b\u5f55\u5bc6\u7801"
            })
        }
    });
    $("#investPassword1").blur(function(b) {
        if ($(this).val() != $("#investPassword").val()) {
            errorShow($(this), {
                error: true,
                message: "\u4e24\u6b21\u5bc6\u7801\u4e0d\u4e00\u81f4"
            })
        } else {
            errorShow($(this), {
                error: false,
                message: "\u8bf7\u518d\u6b21\u8f93\u5165\u65b0\u6295\u8d44\u5bc6\u7801"
            })
        }
    });
    $("#phonePassword").blur(function(c) {
        var b = $(this).val();
        if (!/^\d{6,12}$/.test(b)) {
            errorShow($(this), {
                error: true,
                message: "\u81f3\u5c116\u4e2a\u5b57\u7b26\uff0c\u4ec5\u63a5\u53d7\u6570\u5b57"
            })
        } else {
            errorShow($(this), {
                error: false,
                message: "\u8bf7\u8f93\u5165\u65b0\u7535\u8bdd\u5bc6\u7801"
            })
        }
    });
    function a(b, c) {
        $.ajax({
            url: b,
            type: "POST",
            async: false,
            dataType: "json",
            data: c,
            success: function(d) {
                if (d.code == 200) {
                    alert("\u4fee\u6539\u5bc6\u7801\u6210\u529f\uff01");
                    $("#oldPassword").val("");
                    $("#newPassword").val("");
                    $("#newPassword1").val("");
                    $("#loginPassword").val("");
                    $("#investPassword").val("");
                    $("#investPassword1").val("");
                    $("#phonePassword").val("")
                } else {
                    alert(d.message)
                }
            }
        })
    }
    $("#loginPsBtn").click(function(f) {
        f.preventDefault();
        $("#logBox").find("input.input:visible").blur();
        if ($("#logBox").find("input.error").length > 0) {
            alert("\u8bf7\u5b8c\u5584\u9519\u8bef\u4fe1\u606f");
            return
        }
        var c = SystemProp.trustServerUrl + "/user/change-password.json?ln=0",
        d = $("#oldPassword").val(),
        b = $("#newPassword").val();
        a(c, {
            oldPassword: d,
            newPassword: b
        })
    });
    $("#investPsBtn").click(function(d) {
        d.preventDefault();
        $("#investBox").find("input.input:visible").blur();
        if ($("#investBox").find("input.error").length > 0) {
            alert("\u8bf7\u5b8c\u5584\u9519\u8bef\u4fe1\u606f");
            return
        }
        var b = SystemProp.trustServerUrl + "/user/change-invest-password.json?ln=0",
        c = $("#loginPassword").val(),
        f = $("#investPassword").val();
        a(b, {
            oldPassword: c,
            newInvestPassword: f
        })
    });
    $("#phonePsBtn").click(function(d) {
        d.preventDefault();
        $("#phoneBox").find("input.input:visible").blur();
        if ($("#phoneBox").find("input.error").length > 0) {
            alert("\u8bf7\u5b8c\u5584\u9519\u8bef\u4fe1\u606f");
            return
        }
        var c = SystemProp.trustServerUrl + "/user/change-phone-password.json?ln=0",
        b = $("#phonePassword").val();
        a(c, {
            phonePass: b
        })
    })
});