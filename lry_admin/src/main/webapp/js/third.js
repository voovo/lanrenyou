function loginSuccess(a) {
    $("#dd_login,#loginBox").hide();
    $("#dd_user").show();
    $("#userName_menu").html((a.userAccount.lastName + " " + a.userAccount.surName) || a.userName)
}
function changesever(c, b, a) {
    $(b).val($(c).html()).html($(c).html());
    $(a).attr("value", $(c).attr("data"))
}
function drop_down_div(b, a) {
    $(b).hover(function() {
        $(a).show()
    },
    function() {
        $(a).hide()
    })
}
function after_div(b, a) {
    $(b).click(function(c) {
        c.preventDefault();
        $(a).toggle()
    })
}
function dis_pic(c, b, a) {
    $(c).hover(function() {
        $(this).css("background-position", b)
    },
    function() {
        $(this).css("background-position", a)
    });
    $(c).click(function(d) {
        window.location.href = $(this).attr("d_url")
    })
}
function discolor(b, a) {
    $(b).hover(function() {
        $(this).addClass(a)
    },
    function() {
        $(this).removeClass(a)
    })
}
function discolor_click(d, b, c, a) {
    $(d).each(function() {
        $(this).removeClass(b)
    });
    if (typeof($(a)[0]) != "undefined") {
        $(a).each(function() {
            $(this).removeClass(b)
        })
    }
    $(c).addClass(b)
}
function click_discolor(c, a, b) {
    if ($(c).parent().hasClass(a)) {
        $(c).parent().removeClass(a);
        $(c).parent().addClass(b)
    } else {
        $(c).parent().removeClass(b);
        $(c).parent().addClass(a)
    }
}
function discolor_stylename(d, c, a, b) {
    $(d).find(c).each(function() {
        $(this).removeClass(b)
    });
    $(d).find(c).eq(a).addClass(b)
}
function show_table(b, a) {
    if (a == "[]") {
        $(b).find("ul").each(function() {
            var c = $(this);
            $(this).find("li").each(function() {
                $(this).css("display", "block")
            })
        })
    } else {
        if (a == "") {
            return false
        } else {
            $(b).find("ul").each(function() {
                var c = $(this);
                $.each(a,
                function() {
                    c.find("li").eq(this).css("display", "none")
                })
            })
        }
    }
}
function click_divs(d, c, a, b) {
    $(c).toggle();
    click_discolor(d, a, b)
}
function div_100() {
    var a = document.body.clientWidth;
    var c = document.documentElement.scrollHeight;
    if (typeof($("#jd_shadow")[0]) == "undefined") {
        $("body").prepend("<div id='jd_shadow'>&nbsp;</div>");
        var b = $("#jd_shadow");
        b.css("width", a + "px");
        b.css("height", c + "px")
    } else {
        if ($("#jd_shadow").css("display") == "none") {
            $("#jd_shadow").css({
                display: "block"
            })
        }
    }
}


function div_h(b, a) {
    $(b).height($(a).height())
}
function div_center(g) {
    var b = $(g).width();
    if ($(g).css("display") == "none") {
        $(g).css({
            display: "block"
        })
    }
    var a = $.browser.msie;
    var e = $.browser.msie && ("6.0" == $.browser.version);
    if (typeof($(g)[0]) != "undefined") {
        div_100();
        var d = ($(window).width() - $(g).width()) / 2;
        var c = ($(window).height() - $(g).height()) / 2;
        if (!e) {
            $(g).css({
                top: c,
                left: d,
                width: b
            })
        } else {
            $(g).css({
                top: c + $(document).scrollTop(),
                left: d + $(document).scrollLeft()
            })
        }
        if (e) {
            $(g).css("position", "absolute");
            var c = parseInt($(g).css("top")) - $(document).scrollTop();
            var d = parseInt($(g).css("left")) - $(document).scrollLeft();
            $(window).scroll(function() {
                $(g).css({
                    top: $(document).scrollTop() + c,
                    left: $(document).scrollLeft() + d
                })
            })
        }
        var f = $('<div id="jd_close" class="close png"></div>');
        $(g).append(f);
        $(g).find(".close").bind("click",
        function() {
            $(g).hide();
            $("#jd_shadow").hide()
        })
    }
}
function show_table_tab(f, a, e, d, c, b) {
    $("#prompt_t").html($(f).find(a).eq(e).html());
    $(f).find(a).each(function() {
        $(this).removeClass(d)
    });
    $("div[class^='l_d']").each(function() {
        $(this).css("display", "none")
    });
    $("div[class^='g_d']").each(function() {
        $(this).css("display", "none")
    });
    $(f).find(a).eq(e).addClass(d);
    discolor_stylename(c, "a", e, b);
    $("div[lid^='l_d']").each(function() {
        $(this).css("display", "none")
    });
    $("div[lid^='l_d']").eq(e).css("display", "block");
    $("div[id^='g_d']").eq(e).css("display", "block")
}
function show_table_tab_all(f, a, e, d, c, b) {
    $(f).find(a).each(function() {
        $(this).removeClass(d)
    });
    $("div[class^='pstype_list']").each(function() {
        $(this).css("display", "none")
    });
    $(f).find(a).eq(e).addClass(d);
    $("div[class^='pstype_list']").eq(e).css("display", "block")
}
function hidev3(a) {
    $(".mt_info").eq(a).toggle();
    if ($(".mt_info").eq(a).is(":visible")) {
        $(".mt5").eq(a).css({
            color: "#fff",
            background: "#909090",
            borderbottom: "#B8B8B8"
        });
        $(".mt_cjr").eq(a).css({
            color: "#fff"
        });
        $(".mt_redw").eq(a).css({
            color: "#000"
        });
        $(".mt7").eq(a).css({
            color: "#fff"
        });
        $(".mt_name").eq(a).css({
            color: "#000"
        })
    } else {
        $(".mt5").eq(a).css({
            color: "#D80C18",
            background: "",
            borderbottom: "none"
        });
        $(".mt_redw").eq(a).css({
            color: "#D80C18"
        });
        $(".mt_cjr").eq(a).css({
            color: "#000"
        });
        $(".mt_name").eq(a).css({
            color: "#D80C18"
        });
        $(".mt7").eq(a).css({
            color: "#000"
        })
    }
    div_h(".leftbox_bj", ".rightbox_bj")
}
function hidev2(a) {
    $(".sys_cont").eq(a).toggle();
    if ($(".sys_cont").eq(a).is(":visible")) {
        $(".sys_title").eq(a).css({
            color: "#ffffff",
            background: "url(" + SystemProp.appServerUrl + "/cn/static/images/kfxz/sys_bg3_03.jpg) no-repeat scroll 0 0 transparent"
        })
    } else {
        $(".sys_title").eq(a).css({
            color: "#4a4a4a",
            background: "url(" + SystemProp.appServerUrl + "/cn/static/images/kfxz/sys_bg1.png) no-repeat scroll 0 0 transparent"
        })
    }
    div_h(".leftbox_bj", ".rightbox_bj")
}
function hidev(a) {
    $(".qlist_c").eq(a).toggle();
    if ($(".qlist_c").eq(a).is(":visible")) {
        $(".q_list").eq(a).css({
            color: "#D80C18",
            "background-color": "#DDDDDD"
        })
    } else {
        $(".q_list").eq(a).css({
            color: "#4A4A4A",
            "background-color": ""
        })
    }
    div_h(".leftbox_bj", ".rightbox_bj")
}
function div_tab_all(f, b, e, d, c, a) {
    $(f).find(b).each(function() {
        $(this).removeClass(d)
    });
    $("div[class^=" + c + "]").each(function() {
        $(this).css("display", "none")
    });
    $(f).find(b).eq(e).addClass(d);
    $("div[class^=" + c + "]").eq(e).css("display", "block")
}
function div_tab_all2(f, b, e, d, c, a) {
    $(f).find(b).each(function() {
        $(this).removeClass(d);
        if ($(this).attr("class") == "") {
            $(this).addClass(a)
        }
    });
    $("div[class^=" + c + "]").each(function() {
        $(this).css("display", "none")
    });
    $(f).find(b).eq(e).removeClass(a);
    $(f).find(b).eq(e).addClass(d);
    $("div[class^=" + c + "]").eq(e).css("display", "block")
}
function copyToClipboard(b) {
    if (window.clipboardData) {
        window.clipboardData.clearData();
        window.clipboardData.setData("Text", b)
    } else {
        if (navigator.userAgent.indexOf("Opera") != -1) {
            window.location = b
        } else {
            if (window.netscape) {
                try {
                    netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect")
                } catch(g) {
                    alert("\u88ab\u6d4f\u89c8\u5668\u62d2\u7edd\uff01\n\u8bf7\u5728\u6d4f\u89c8\u5668\u5730\u5740\u680f\u8f93\u5165'about:config'\u5e76\u56de\u8f66\n\u7136\u540e\u5c06'signed.applets.codebase_principal_support'\u8bbe\u7f6e\u4e3a'true'")
                }
                var d = Components.classes["@mozilla.org/widget/clipboard;1"].createInstance(Components.interfaces.nsIClipboard);
                if (!d) {
                    return
                }
                var c = Components.classes["@mozilla.org/widget/transferable;1"].createInstance(Components.interfaces.nsITransferable);
                if (!c) {
                    return
                }
                c.addDataFlavor("text/unicode");
                var h = new Object();
                var a = new Object();
                var h = Components.classes["@mozilla.org/supports-string;1"].createInstance(Components.interfaces.nsISupportsString);
                var i = b;
                h.data = i;
                c.setTransferData("text/unicode", h, i.length * 2);
                var f = Components.interfaces.nsIClipboard;
                if (!d) {
                    return false
                }
                d.setData(c, null, f.kGlobalClipboard)
            }
        }
    }
    alert("\u590d\u5236\u5b8c\u6210\uff01")
}
function get_show_table(b, c) {
    var a = "";
    $(b).each(function() {
        if ($(this).attr("checked") == "checked") {
            a += "," + $(this).attr("value")
        }
    });
    a = "[" + a.substring(1) + "]";
    show_table(c, a);
    setCookie("num", a, "d1")
}
$(function() {
    div_h(".leftbox_bj", ".rightbox_bj");
    $(".deposit li").click(function(){
        $(".deposit li").find(".rjfs_tm2").each(function(){
            $(this).removeClass("rjfs_tm2");
            $(this).addClass("rjfs_tm oph");
        });
        $(this).find(".rjfs_tm").addClass("rjfs_tm2");
        $(this).find(".rjfs_tm").removeClass("rjfs_tm oph");
    });
});