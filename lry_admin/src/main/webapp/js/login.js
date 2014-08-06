$(function() {
    $("#userName").blur(function(b) {
        var a = $(this).val();
        if (!a) {
            $("#message").html("Please input your username").show();
            return
        } else {
            $("#message").html("Invalid username or password").hide()
        }
    });
    $("#password").keyup(function(a) {
        a.stopPropagation();
        if (a.keyCode == 13) {
            a.preventDefault();
            $("#submit").click()
        }
    });
    $("#submit").click(function(c) {
        c.preventDefault();
        var b = $("#userName").val();
        var a = $("#password").val();
        if (!b || !a) {
            $("#message").html("Please enter username or password").show();
            return
        }
        $.ajax({
            url: "/login/loginSubmit",
            type: "POST",
            async: false,
            data: {
                userName: b,
                password: a
            },
            success: function(e) {
                if (e == "1") {
                    $("#message").html(e.message || "Access failed").show();
                } else {
                    window.location.href =  e;
                }
            }
        })
    })
});