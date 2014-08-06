$(function() {
    $("a[page]").live("click",
    function(c) {
        pageNo = parseInt($(this).attr("page"));
        a(pageNo)
    });
    function a(c) {
        var e = (c - 1) * 10;
        var d = 10;
        var g = {};
        g.catId = 1;
        g.begin = e;
        g.limit = d;
        alert("网络错误，通知加载失败");
        return;
        var f = SystemProp.memberServerUrl + "/content/get-ann-content!page.json?ln=0";
        $.ajax({
            url: f,
            type: "GET",
            async: false,
            data: g,
            success: function(h) {
                if (!h) {
                    return
                }
                if (h.code == 200 && !!h.data && !!h.data.annList && h.data.annList.length > 0) {
                    var l = h.data.annList;
                    var k = "";
                    var n = "";
                    for (var j = 0; j < l.length; j++) {
                        var m = l[j];
                        if (j % 2 == 0) {
                            k += "<li " + (m.isNew == 1 ? 'class="new"': "") + ' annId="' + m.id + '">\u25aa<span class="time">' + m.createTimeFormat + "</span>" + m.title + (m.isNew == 1 ? '<b class="newIcon"></b>': "") + "</li>"
                        } else {
                            k += '<li class="gray' + (m.isNew == 1 ? " new": "") + '" annId="' + m.id + '">\u25aa<span class="time">' + m.createTimeFormat + "</span>" + m.title + (m.isNew == 1 ? '<b class="newIcon"></b>': "") + "</li>"
                        }
                        n += '<div id="ann_' + m.id + '">' + m.content + "</div>"
                    }
                    $("#announcementList").empty();
                    $("#announcementList").html(k);
                    $("#contentHide").empty();
                    $("#contentHide").html(n)
                } else {}
            }
        });
        b(c)
    }
    $("#turnLeft").bind("click",
    function(c) {
        if (pageNo < 2) {
            return
        } else {
            pageNo = pageNo - 1;
            $("a[page=" + pageNo + "]").click()
        }
    });
    $("#turnRight").bind("click",
    function(c) {
        if (pageNo >= totalPage) {
            return
        } else {
            pageNo = pageNo + 1;
            $("a[page=" + pageNo + "]").click()
        }
    });
    function b(d) {
        if (totalPage >= 10) {
            $("div.pages a[page]").remove();
            var g = 1;
            var e = totalPage;
            if ((d - 4) <= 1) {
                g = 1;
                e = 9
            } else {
                if ((d + 4) >= totalPage) {
                    g = totalPage - 8;
                    e = totalPage
                } else {
                    g = d - 4;
                    e = d + 4
                }
            }
            var f = "";
            for (var c = g; c <= e; c++) {
                f += '<a page="' + c + '" href="javascript:void(0)"  >' + c + "</a>"
            }
            $("#turnLeft").after(f)
        }
        $("a[page]").removeClass("current");
        $("a[page=" + d + "]").addClass("current");
        pageNo = d
    }
    $("#announcementList li").live("click",
    function(d) {
        var c = $(this).attr("annId");
        $("#annContent").html($("#ann_" + c).html());
        $("#announcement").fancybox()
    })
});