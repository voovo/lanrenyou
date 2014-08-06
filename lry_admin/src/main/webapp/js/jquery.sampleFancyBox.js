(function(f) {
    var b, i, e, g, c, a, h;
    var d = f.browser.msie && f.browser.version < 7 && !window.XMLHttpRequest;
    f.fancybox = {};
    f.fancybox.init = function() {
        if (f("#fancybox-wrap").length) {
            return
        }
        f("body").append(e = f('<div id="fancybox-overlay"></div>'), b = f('<div id="fancybox-wrap" class="pop"></div>'));
        g = f('<div id="fancybox-content"></div>').appendTo(b);
        h = f('<a id="fancybox-close" class="del"></a>').appendTo(b);
        h.click(function(j) {
            j.preventDefault();
            f.fancybox.close()
        });
        if (!f.support.opacity) {}
        if (d) {}
        f(window).scroll(function() {
            f("#fancybox-overlay").css({
                width: f(document).width()
            })
        })
    };
    f.fn.fancybox = function(j) {
        if (!f(this).length) {
            return this
        }
        var k = {
            padding: 0,
            margin: 0,
            width: 560,
            height: 340,
            overlayShow: true,
            overlayOpacity: 0.7,
            overlayColor: "#000",
            hideOnOverlayClick: false,
            centerOnScroll: false,
            onStart: function() {
                if (d) {
                    f("select").css({
                        visibility: "hidden"
                    })
                }
            },
            onCancel: function() {},
            onComplete: function() {},
            onCleanup: function() {},
            onClosed: function() {
                f("select").css({
                    visibility: "visible"
                })
            },
            onError: function() {}
        };
        c = f.extend({},
        k, j);
        _get_viewport = function() {
            return [f(window).width() - (c.margin * 2), f(window).height() - (c.margin * 2), f(document).scrollLeft() + c.margin, f(document).scrollTop() + c.margin]
        };
        _get_obj_pos = function(l) {
            var m = l.offset();
            m.top += parseInt(l.css("paddingTop"), 10) || 0;
            m.left += parseInt(l.css("paddingLeft"), 10) || 0;
            m.top += parseInt(l.css("border-top-width"), 10) || 0;
            m.left += parseInt(l.css("border-left-width"), 10) || 0;
            m.width = l.outerWidth(true);
            m.height = l.outerHeight(true);
            return m
        },
        _finish = function() {
            f("#fancybox-close").unbind("click").bind("click", f.fancybox.close);
            if (c.hideOnOverlayClick) {
                e.bind("click", f.fancybox.close)
            }
            f(window).bind("resize.fb", f.fancybox.resize);
            if (c.centerOnScroll) {
                f(window).bind("scroll.fb", f.fancybox.center)
            }
            b.show();
            f("#fancybox-content").find("select").css({
                visibility: "visible"
            });
            f.fancybox.center(true)
        };
        _show = function() {
            var o, m;
            if (b.is(":visible")) {
                return
            }
            c.onStart();
            f(g.add(e)).unbind();
            f(window).unbind("resize.fb scroll.fb");
            f(document).unbind("keydown.fb");
            if (c.overlayShow) {
                e.css({
                    "background-color": c.overlayColor,
                    opacity: c.overlayOpacity,
                    width: "100%",
                    height: "100%"
                });
                if (!e.is(":visible")) {
                    e.show()
                }
            } else {
                e.hide()
            }
            b.removeAttr("style");
            g.css("border-width", c.padding);
            o = _get_obj_pos(f(this));
            g.css({
                width: o.width,
                height: o.height
            }).html(f(this).show());
            var n = o.width + c.padding * 2,
            l = o.height + c.padding * 2;
            b.css({
                width: n,
                height: l
            });
            _finish()
        };
        _show.call(f(this));
        c.onComplete();
        return f(this)
    };
    f.fancybox.center = function(j, p) {
        var k;
        var o = 100;
        k = _get_viewport();
        if (e.is(":visible")) {
            e.css({
                height: f(document).height(),
                width: f(window).width()
            })
        }
        if ((b.width() > k[0] && b.height() > k[1])) {
            return
        }
        var n = parseInt(Math.max(k[2], k[2] + ((k[0] - g.width()) * 0.5) - c.padding));
        var m = parseInt(Math.max(k[3], k[3] + ((k[1] - g.height()) * 0.5) - c.padding));
        if (j) {
            b.css({
                top: 0,
                left: n
            });
            o = 300
        }
        var l = {
            top: m,
            left: n
        };
        b.stop().animate(l, o)
    };
    f.fancybox.close = function() {
        g.children().hide().appendTo(f("body"));
        if (b.is(":hidden")) {
            return
        }
        e.fadeOut(500);
        if (d) {
            b.hide();
            c.onClosed()
        } else {
            b.fadeOut(400,
            function() {
                c.onClosed()
            })
        }
    };
    f.fancybox.resize = function() {
        if (!a) {
            a = setTimeout(function() {
                f.fancybox.center();
                a = null
            },
            300)
        }
        if (f(".datepicker").length > 0) {
            f(".datepicker").hide()
        }
    };
    f(document).ready(function() {
        f.fancybox.init()
    })
})(jQuery);