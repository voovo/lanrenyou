/*
 * 懒人游 首页交互
 * mr.sheak@gmail.com 20140626
 */

;$(function(){
/*********************************************************/
    
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

    // slide
    $('.flexslider').flexslider({
        animation: "slide",
        start: function(slider){
          $('body').removeClass('loading');
        }
    });

    // 首页加载游记
    var _l_i = 0,
        _s_i = 0,
        _n_i = 0,
        _f_i = 0;

    var index_templete = function(r){
        // 默认8种模板
        var _T = new Array();

        var _t0 = '<div class="row row-0 clearfix"><div class="cell h"><dl class="mg_r"><dt><a href="{YJ_url1}" target="_blank"><img src="{YJ_img1}" height="390px;" alt=""></a></dt><dd><i class="img_memb"></i><div class="img_info"><a href="{YJ_p_url1}" target="_blank"><img src="{YJ_p_img1}" alt=""></a><p><a href="{YJ_url1}" target="_blank">{YJ_title1}</a></p></div></dd></dl></div><div class="cell w"><dl class="mg_r"><dt><a href="{YJ_url2}" target="_blank"><img src="{YJ_img2}" width="590" height="390" alt=""></a></dt><dd><i class="img_memb"></i><div class="img_info"><a href="{YJ_p_url2}" target="_blank"><img src="{YJ_p_img2}" alt=""></a><p><a href="{YJ_url2}" target="_blank">{YJ_title2}</a></p></div></dd></dl></div><div class="cell h"><dl class="mg_r"><dt><a href="{YJ_url3}" target="_blank"><img src="{YJ_img3}" height="390" alt=""></a></dt><dd><i class="img_memb"></i><div class="img_info"><a href="{YJ_p_url3}" target="_blank"><img src="{YJ_p_img3}" alt=""></a><p><a href="{YJ_url3}" target="_blank">{YJ_title3}</a></p></div></dd></dl></div></div>';

        var _t1 = '<div class="row row-1 clearfix"><div class="cell w"><dl class="mg_r"><dt><a href="{YJ_url1}"target="_blank"><img src="{YJ_img1}"height="390"alt=""></a></dt><dd><i class="img_memb"></i><div class="img_info"><a href="{YJ_p_url1}"target="_blank"><img src="{YJ_p_img1}"alt=""></a><p><a href="{YJ_url1}"target="_blank">{YJ_title1}</a></p></div></dd></dl></div><div class="cell w"><dl class="mg_r mg_b"><dt><a href="{YJ_url2}"target="_blank"><img src="{YJ_img2}"height="185"alt=""></a></dt><dd><i class="img_memb"></i><div class="img_info"><a href="{YJ_p_url2}"target="_blank"><img src="{YJ_p_img2}"alt=""></a><p><a href="{YJ_url2}"target="_blank">{YJ_title2}</a></p></div></dd></dl><dl class="mg_r mg_b"><dt><a href="{YJ_url3}"target="_blank"><img src="{YJ_img3}"height="185"alt=""></a></dt><dd><i class="img_memb"></i><div class="img_info"><a href="{YJ_p_url3}"target="_blank"><img src="{YJ_p_img3}"alt=""></a><p><a href="{YJ_url3}"target="_blank">{YJ_title3}</a></p></div></dd></dl></div></div>';

        var _t2 = '<div class="row row-2 clearfix"><div class="cell h"><dl class="mg_b mg_r"><dt><a href="{YJ_url1}"target="_blank"><img src="{YJ_img1}"height="185"width="285"alt=""></a></dt><dd><i class="img_memb"></i><div class="img_info"><a href="{YJ_p_url1}"target="_blank"><img src="{YJ_p_img1}"alt=""></a><p><a href="{YJ_url1}"target="_blank">{YJ_title1}</a></p></div></dd></dl><dl class="mg_b mg_r"><dt><a href="{YJ_url2}"target="_blank"><img src="{YJ_img2}"height="185"alt=""></a></dt><dd><i class="img_memb"></i><div class="img_info"><a href="{YJ_p_url2}"target="_blank"><img src="{YJ_p_img2}"alt=""></a><p><a href="{YJ_url2}"target="_blank">{YJ_title2}</a></p></div></dd></dl></div><div class="cell w"><dl class="mg_r mg_b"><dt><a href="{YJ_url3}"target="_blank"><img src="{YJ_img3}"width="590"height="390"alt=""></a></dt><dd><i class="img_memb"></i><div class="img_info"><a href="{YJ_p_url3}"target="_blank"><img src="{YJ_p_img3}"alt=""></a><p><a href="{YJ_url3}"target="_blank">{YJ_title3}</a></p></div></dd></dl></div><div class="cell h"><dl class="mg_r mg_b"><dt><a href="{YJ_url4}"target="_blank"><img src="{YJ_img4}"width="280"height="390"alt=""></a></dt><dd><i class="img_memb"></i><div class="img_info"><a href="{YJ_p_url4}"target="_blank"><img src="{YJ_p_img4}"alt=""></a><p><a href="{YJ_url4}"target="_blank">{YJ_title4}</a></p></div></dd></dl></div></div>';

        var _t3 = '<div class="row row-3 clearfix"><div class="cell w"><dl class="mg_r mg_b"><dt><a href="{YJ_url1}"target="_blank"><img src="{YJ_img1}"height="390"alt=""></a></dt><dd><i class="img_memb"></i><div class="img_info"><a href="{YJ_p_url1}"target="_blank"><img src="{YJ_p_img1}"alt=""></a><p><a href="{YJ_url1}"target="_blank">{YJ_title1}</a></p></div></dd></dl></div><div class="cell w"><dl class="mg_r mg_b"><dt><a href="{YJ_url2}"target="_blank"><img src="{YJ_img2}"width="590"height="185"alt=""></a></dt><dd><i class="img_memb"></i><div class="img_info"><a href="{YJ_p_url2}"target="_blank"><img src="{YJ_p_img2}"alt=""></a><p><a href="{YJ_url2}"target="_blank">{YJ_title2}</a></p></div></dd></dl><div class="w left"><dl class="mg_r mg_b"><dt><a href="{YJ_url3}"target="_blank"><img src="{YJ_img3}"width="590"height="185"alt=""></a></dt><dd><i class="img_memb"></i><div class="img_info"><a href="{YJ_p_url3}"target="_blank"><img src="{YJ_p_img3}"alt=""></a><p><a href="{YJ_url3}"target="_blank">{YJ_title3}</a></p></div></dd></dl></div><div class="w left"><dl class="mg_r mg_b"><dt><a href="{YJ_url4}"target="_blank"><img src="{YJ_img4}"width=""height="185"alt=""></a></dt><dd><i class="img_memb"></i><div class="img_info"><a href="{YJ_p_url4}"target="_blank"><img src="{YJ_p_img4}"alt=""></a><p><a href="{YJ_url4}"target="_blank">{YJ_title4}</a></p></div></dd></dl></div></div></div>';

        var _t4 = '<div class="row row-4 clearfix"><div class="cell w"><dl class="mg_r mg_b"><dt><a href="{YJ_url1}"target="_blank"><img src="{YJ_img1}"height="390"alt=""></a></dt><dd><i class="img_memb"></i><div class="img_info"><a href="{YJ_p_url1}"target="_blank"><img src="{YJ_p_img1}"alt=""></a><p><a href="{YJ_url1}"target="_blank">{YJ_title1}</a></p></div></dd></dl></div><div class="cell w"><dl class="mg_r mg_b"><dt><a href="{YJ_url2}"target="_blank"><img src="{YJ_img2}"width="590"height="390"alt=""></a></dt><dd><i class="img_memb"></i><div class="img_info"><a href="{YJ_p_url2}"target="_blank"><img src="{YJ_p_img2}"alt=""></a><p><a href="{YJ_url2}"target="_blank">{YJ_title2}</a></p></div></dd></dl></div></div>';

        var _t5 = '<div class="row row-5 clearfix"><div class="cell h"><dl class="mg_r mg_b"><dt><a href="{YJ_url1}"target="_blank"><img src="{YJ_img1}"height="390"alt=""></a></dt><dd><i class="img_memb"></i><div class="img_info"><a href="{YJ_p_url1}"target="_blank"><img src="{YJ_p_img1}"alt=""></a><p><a href="{YJ_url1}"target="_blank">{YJ_title1}</a></p></div></dd></dl></div><div class="cell w"><dl class="mg_r mg_b"><dt><a href="{YJ_url2}"target="_blank"><img src="{YJ_img2}"width="590"height="185"alt=""></a></dt><dd><i class="img_memb"></i><div class="img_info"><a href="{YJ_p_url2}"target="_blank"><img src="{YJ_p_img2}"alt=""></a><p><a href="{YJ_url2}"target="_blank">{YJ_title2}</a></p></div></dd></dl><dl class="mg_r mg_b"><dt><a href="{YJ_url3}"target="_blank"><img src="{YJ_img3}"height="185"width="590"alt=""></a></dt><dd><i class="img_memb"></i><div class="img_info"><a href="{YJ_p_url3}"target="_blank"><img src="{YJ_p_img3}"alt=""></a><p><a href="{YJ_url3}"target="_blank">{YJ_title3}</a></p></div></dd></dl></div><div class="cell h"><dl class="mg_r mg_b"><dt><a href="{YJ_url4}"target="_blank"><img src="{YJ_img4}"height="390"alt=""></a></dt><dd><i class="img_memb"></i><div class="img_info"><a href="{YJ_p_url4}"target="_blank"><img src="{YJ_p_img4}"alt=""></a><p><a href="{YJ_url4}"target="_blank">{YJ_title4}</a></p></div></dd></dl></div></div>';

        var _t6 = '<div class="row row-5 clearfix"><div class="cell h"><dl class="mg_b mg_r"><dt><a href="{YJ_url1}"target="_blank"><img src="{YJ_img1}"height="185"width="285"alt=""></a></dt><dd><i class="img_memb"></i><div class="img_info"><a href="{YJ_p_url1}"target="_blank"><img src="{YJ_p_img1}"alt=""></a><p><a href="{YJ_url1}"target="_blank">{YJ_title1}</a></p></div></dd></dl><dl class="mg_r mg_b"><dt><a href="{YJ_url2}"target="_blank"><img src="{YJ_img2}"height="185"alt=""></a></dt><dd><i class="img_memb"></i><div class="img_info"><a href="{YJ_p_url2}"target="_blank"><img src="{YJ_p_img2}"alt=""></a><p><a href="{YJ_url2}"target="_blank">{YJ_title2}</a></p></div></dd></dl></div><div class="cell h"><dl class="mg_r mg_b"><dt><a href="{YJ_url3}"target="_blank"><img src="{YJ_img3}"width="285"height="390"alt=""></a></dt><dd><i class="img_memb"></i><div class="img_info"><a href="{YJ_p_url3}"target="_blank"><img src="{YJ_p_img3}"alt=""></a><p><a href="{YJ_url3}"target="_blank">{YJ_title3}</a></p></div></dd></dl></div><div class="cell w"><dl class="mg_r mg_b"><dt><a href="{YJ_url4}"target="_blank"><img src="{YJ_img4}"height="390"alt=""></a></dt><dd><i class="img_memb"></i><div class="img_info"><a href="{YJ_p_url4}"target="_blank"><img src="{YJ_p_img4}"alt=""></a><p><a href="{YJ_url4}"target="_blank">{YJ_title4}</a></p></div></dd></dl></div></div>';

        var _t7 = '<div class="row row-2 clearfix"><div class="cell w"><dl class="mg_r mg_b"><dt><a href="{YJ_url1}"target="_blank"><img src="{YJ_img1}"width="590"height="390"alt=""></a></dt><dd><i class="img_memb"></i><div class="img_info"><a href="{YJ_p_url1}"target="_blank"><img src="{YJ_p_img1}"alt=""></a><p><a href="{YJ_url1}"target="_blank">{YJ_title1}</a></p></div></dd></dl></div><div class="cell h"><dl class="mg_r mg_b"><dt><a href="{YJ_url2}"target="_blank"><img src="{YJ_img2}"height="390"alt=""></a></dt><dd><i class="img_memb"></i><div class="img_info"><a href="{YJ_p_url2}"target="_blank"><img src="{YJ_p_img2}"alt=""></a><p><a href="{YJ_url2}"target="_blank">{YJ_title2}</a></p></div></dd></dl></div><div class="cell h"><dl class="mg_b mg_r"><dt><a href="{YJ_url3}"target="_blank"><img src="{YJ_img3}"height="185"width="285"alt=""></a></dt><dd><i class="img_memb"></i><div class="img_info"><a href="{YJ_p_url3}"target="_blank"><img src="{YJ_p_img3}"alt=""></a><p><a href="{YJ_url3}"target="_blank">{YJ_title3}</a></p></div></dd></dl><dl class="mg_b mg_r"><dt><a href="{YJ_url4}"target="_blank"><img src="{YJ_img4}"height="185"alt=""></a></dt><dd><i class="img_memb"></i><div class="img_info"><a href="{YJ_p_url4}"target="_blank"><img src="{YJ_p_img4}"alt=""></a><p><a href="{YJ_url4}"target="_blank">{YJ_title4}</a></p></div></dd></dl></div></div>';
       
        _T.push(_t0 , _t1 , _t2 , _t3 , _t4 , _t5 , _t6 , _t7);

        return _T[r];

    },set_templete = function(_D , _r){
        var _Templete = index_templete(_r),
            _l = _D.l,
            _s = _D.s,
            _n = _D.n,
            _f = _D.f,
            templete_str = "";
            
        switch(_r){
            case 0:
                var YJ_url1 = _n[_n_i].url,
                    YJ_img1 = _n[_n_i].img,
                    YJ_p_url1 = "/user/" + _n[_n_i].uid,
                    YJ_p_img1 = _n[_n_i].user_avatar,
                    YJ_title1 = _n[_n_i].title;

                _n_i++;

                var YJ_url2 = _l[_l_i].url,
                    YJ_img2 = _l[_l_i].img,
                    YJ_p_url2 = "/user/" + _l[_l_i].uid,
                    YJ_p_img2 = _l[_l_i].user_avatar,
                    YJ_title2 = _l[_l_i].title;

                _l_i++;

                var YJ_url3 = _n[_n_i].url,
                    YJ_img3 = _n[_n_i].img,
                    YJ_p_url3 = "/user/" + _n[_n_i].uid,
                    YJ_p_img3 = _n[_n_i].user_avatar,
                    YJ_title3 = _n[_n_i].title;

                _n_i++;

                templete_str = '<div class="row row-0 clearfix"><div class="cell h"><dl class="mg_r"><dt><a href="'+YJ_url1+'" target="_blank"><img src="'+YJ_img1+'" height="390px;" alt=""></a></dt><dd><i class="img_memb"></i><div class="img_info"><a href="'+YJ_p_url1+'" target="_blank"><img src="'+YJ_p_img1+'" alt=""></a><p><a href="'+YJ_url1+'" target="_blank">'+YJ_title1+'</a></p></div></dd></dl></div><div class="cell w"><dl class="mg_r"><dt><a href="'+YJ_url2+'" target="_blank"><img src="'+YJ_img2+'" width="590" height="390" alt=""></a></dt><dd><i class="img_memb"></i><div class="img_info"><a href="'+YJ_p_url2+'" target="_blank"><img src="'+YJ_p_img2+'" alt=""></a><p><a href="'+YJ_url2+'" target="_blank">'+YJ_title2+'</a></p></div></dd></dl></div><div class="cell h"><dl class="mg_r"><dt><a href="'+YJ_url3+'" target="_blank"><img src="'+YJ_img3+'" height="390" alt=""></a></dt><dd><i class="img_memb"></i><div class="img_info"><a href="'+YJ_p_url3+'" target="_blank"><img src="'+YJ_p_img3+'" alt=""></a><p><a href="'+YJ_url3+'" target="_blank">'+YJ_title3+'</a></p></div></dd></dl></div></div>';
                
            break;
            case 1:
                var YJ_url1 = _l[_l_i].url,
                    YJ_img1 = _l[_l_i].img,
                    YJ_p_url1 = "/user/" + _l[_l_i].uid,
                    YJ_p_img1 = _l[_l_i].user_avatar,
                    YJ_title1 = _l[_l_i].title;

                _l_i++;

                var YJ_url2 = _f[_f_i].url,
                    YJ_img2 = _f[_f_i].img,
                    YJ_p_url2 = "/user/" + _f[_f_i].uid,
                    YJ_p_img2 = _f[_f_i].user_avatar,
                    YJ_title2 = _f[_f_i].title;

                _f_i++;

                var YJ_url3 = _f[_f_i].url,
                    YJ_img3 = _f[_f_i].img,
                    YJ_p_url3 = "/user/" + _f[_f_i].uid,
                    YJ_p_img3 = _f[_f_i].user_avatar,
                    YJ_title3 = _f[_f_i].title;

                _f_i++;

                templete_str = '<div class="row row-1 clearfix"><div class="cell w"><dl class="mg_r"><dt><a href="'+YJ_url1+'"target="_blank"><img src="'+YJ_img1+'"height="390"alt=""></a></dt><dd><i class="img_memb"></i><div class="img_info"><a href="'+YJ_p_url1+'"target="_blank"><img src="'+YJ_p_img1+'"alt=""></a><p><a href="'+YJ_url1+'"target="_blank">'+YJ_title1+'</a></p></div></dd></dl></div><div class="cell w"><dl class="mg_r mg_b"><dt><a href="'+YJ_url2+'"target="_blank"><img src="'+YJ_img2+'"height="185"alt=""></a></dt><dd><i class="img_memb"></i><div class="img_info"><a href="'+YJ_p_url2+'"target="_blank"><img src="'+YJ_p_img2+'"alt=""></a><p><a href="'+YJ_url2+'"target="_blank">'+YJ_title2+'</a></p></div></dd></dl><dl class="mg_r mg_b"><dt><a href="'+YJ_url3+'"target="_blank"><img src="'+YJ_img3+'"height="185"alt=""></a></dt><dd><i class="img_memb"></i><div class="img_info"><a href="'+YJ_p_url3+'"target="_blank"><img src="'+YJ_p_img3+'"alt=""></a><p><a href="'+YJ_url3+'"target="_blank">'+YJ_title3+'</a></p></div></dd></dl></div></div>';

            break;

            case 2:
                var YJ_url1 = _s[_s_i].url,
                    YJ_img1 = _s[_s_i].img,
                    YJ_p_url1 = "/user/" + _s[_s_i].uid,
                    YJ_p_img1 = _s[_s_i].user_avatar,
                    YJ_title1 = _s[_s_i].title;

                _s_i++;

                var YJ_url2 = _s[_s_i].url,
                    YJ_img2 = _s[_s_i].img,
                    YJ_p_url2 = "/user/" + _s[_s_i].uid,
                    YJ_p_img2 = _s[_s_i].user_avatar,
                    YJ_title2 = _s[_s_i].title;

                _s_i++;

                var YJ_url3 = _l[_l_i].url,
                    YJ_img3 = _l[_l_i].img,
                    YJ_p_url3 = "/user/" + _l[_l_i].uid,
                    YJ_p_img3 = _l[_l_i].user_avatar,
                    YJ_title3 = _l[_l_i].title;

                _l_i++;

                var YJ_url4 = _n[_f_i].url,
                    YJ_img4 = _n[_f_i].img,
                    YJ_p_url4 = "/user/" + _n[_f_i].uid,
                    YJ_p_img4 = _n[_f_i].user_avatar,
                    YJ_title4 = _n[_f_i].title;

                _n_i++;

                templete_str = '<div class="row row-2 clearfix"><div class="cell h"><dl class="mg_b mg_r"><dt><a href="'+YJ_url1+'"target="_blank"><img src="'+YJ_img1+'"height="185"width="285"alt=""></a></dt><dd><i class="img_memb"></i><div class="img_info"><a href="'+YJ_p_url1+'"target="_blank"><img src="'+YJ_p_img1+'"alt=""></a><p><a href="'+YJ_url1+'"target="_blank">'+YJ_title1+'</a></p></div></dd></dl><dl class="mg_b mg_r"><dt><a href="'+YJ_url2+'"target="_blank"><img src="'+YJ_img2+'"height="185"alt=""></a></dt><dd><i class="img_memb"></i><div class="img_info"><a href="'+YJ_p_url2+'"target="_blank"><img src="'+YJ_p_img2+'"alt=""></a><p><a href="'+YJ_url2+'"target="_blank">'+YJ_title2+'</a></p></div></dd></dl></div><div class="cell w"><dl class="mg_r mg_b"><dt><a href="'+YJ_url3+'"target="_blank"><img src="'+YJ_img3+'"width="590"height="390"alt=""></a></dt><dd><i class="img_memb"></i><div class="img_info"><a href="'+YJ_p_url3+'"target="_blank"><img src="'+YJ_p_img3+'"alt=""></a><p><a href="'+YJ_url3+'"target="_blank">'+YJ_title3+'</a></p></div></dd></dl></div><div class="cell h"><dl class="mg_r mg_b"><dt><a href="'+YJ_url4+'"target="_blank"><img src="'+YJ_img4+'"width="280"height="390"alt=""></a></dt><dd><i class="img_memb"></i><div class="img_info"><a href="'+YJ_p_url4+'"target="_blank"><img src="'+YJ_p_img4+'"alt=""></a><p><a href="'+YJ_url4+'"target="_blank">'+YJ_title4+'</a></p></div></dd></dl></div></div>';

            break;

            case 3:
                var YJ_url1 = _l[_l_i].url,
                    YJ_img1 = _l[_l_i].img,
                    YJ_p_url1 = "/user/" + _l[_l_i].uid,
                    YJ_p_img1 = _l[_l_i].user_avatar,
                    YJ_title1 = _l[_l_i].title;

                _l_i++;

                var YJ_url2 = _f[_f_i].url,
                    YJ_img2 = _f[_f_i].img,
                    YJ_p_url2 = "/user/" + _f[_f_i].uid,
                    YJ_p_img2 = _f[_f_i].user_avatar,
                    YJ_title2 = _f[_f_i].title;

                _f_i++;

                var YJ_url3 = _s[_s_i].url,
                    YJ_img3 = _s[_s_i].img,
                    YJ_p_url3 = "/user/" + _s[_s_i].uid,
                    YJ_p_img3 = _s[_s_i].user_avatar,
                    YJ_title3 = _s[_s_i].title;

                _s_i++;

                var YJ_url4 = _s[_s_i].url,
                    YJ_img4 = _s[_s_i].img,
                    YJ_p_url4 = "/user/" + _s[_s_i].uid,
                    YJ_p_img4 = _s[_s_i].user_avatar,
                    YJ_title4 = _s[_s_i].title;

                _s_i++;

                templete_str = '<div class="row row-3 clearfix"><div class="cell w"><dl class="mg_r mg_b"><dt><a href="'+YJ_url1+'"target="_blank"><img src="'+YJ_img1+'"height="390"alt=""></a></dt><dd><i class="img_memb"></i><div class="img_info"><a href="'+YJ_p_url1+'"target="_blank"><img src="'+YJ_p_img1+'"alt=""></a><p><a href="'+YJ_url1+'"target="_blank">'+YJ_title1+'</a></p></div></dd></dl></div><div class="cell w"><dl class="mg_r mg_b"><dt><a href="'+YJ_url2+'"target="_blank"><img src="'+YJ_img2+'"width="590"alt=""></a></dt><dd><i class="img_memb"></i><div class="img_info"><a href="'+YJ_p_url2+'"target="_blank"><img src="'+YJ_p_img2+'"alt=""></a><p><a href="'+YJ_url2+'"target="_blank">'+YJ_title2+'</a></p></div></dd></dl><div class="w left"><dl class="mg_r mg_b"><dt><a href="'+YJ_url3+'"target="_blank"><img src="'+YJ_img3+'"width="280"height="185"alt=""></a></dt><dd><i class="img_memb"></i><div class="img_info"><a href="'+YJ_p_url3+'"target="_blank"><img src="'+YJ_p_img3+'"alt=""></a><p><a href="'+YJ_url3+'"target="_blank">'+YJ_title3+'</a></p></div></dd></dl></div><div class="w left"><dl class="mg_r mg_b"><dt><a href="'+YJ_url4+'"target="_blank"><img src="'+YJ_img4+'"width="280"height="185"alt=""></a></dt><dd><i class="img_memb"></i><div class="img_info"><a href="'+YJ_p_url4+'"target="_blank"><img src="'+YJ_p_img4+'"alt=""></a><p><a href="'+YJ_url4+'"target="_blank">'+YJ_title4+'</a></p></div></dd></dl></div></div></div>';

            break;

            case 4:
                var YJ_url1 = _l[_l_i].url,
                    YJ_img1 = _l[_l_i].img,
                    YJ_p_url1 = "/user/" + _l[_l_i].uid,
                    YJ_p_img1 = _l[_l_i].user_avatar,
                    YJ_title1 = _l[_l_i].title;

                _l_i++;

                var YJ_url2 = _l[_l_i].url,
                    YJ_img2 = _l[_l_i].img,
                    YJ_p_url2 = "/user/" + _l[_l_i].uid,
                    YJ_p_img2 = _l[_l_i].user_avatar,
                    YJ_title2 = _l[_l_i].title;

                _l_i++;

                templete_str = '<div class="row row-4 clearfix"><div class="cell w"><dl class="mg_r mg_b"><dt><a href="'+YJ_url1+'"target="_blank"><img src="'+YJ_img1+'"height="390"alt=""></a></dt><dd><i class="img_memb"></i><div class="img_info"><a href="'+YJ_p_url1+'"target="_blank"><img src="'+YJ_p_img1+'"alt=""></a><p><a href="'+YJ_url1+'"target="_blank">'+YJ_title1+'</a></p></div></dd></dl></div><div class="cell w"><dl class="mg_r mg_b"><dt><a href="'+YJ_url2+'"target="_blank"><img src="'+YJ_img2+'"width="590"height="390"alt=""></a></dt><dd><i class="img_memb"></i><div class="img_info"><a href="'+YJ_p_url2+'"target="_blank"><img src="'+YJ_p_img2+'"alt=""></a><p><a href="'+YJ_url2+'"target="_blank">'+YJ_title2+'</a></p></div></dd></dl></div></div>';

            break;

            case 5:
                var YJ_url1 = _n[_n_i].url,
                    YJ_img1 = _n[_n_i].img,
                    YJ_p_url1 = "/user/" + _n[_n_i].uid,
                    YJ_p_img1 = _n[_n_i].user_avatar,
                    YJ_title1 = _n[_n_i].title;

                _n_i++;

                var YJ_url2 = _f[_f_i].url,
                    YJ_img2 = _f[_f_i].img,
                    YJ_p_url2 = "/user/" + _f[_f_i].uid,
                    YJ_p_img2 = _f[_f_i].user_avatar,
                    YJ_title2 = _f[_f_i].title;

                _f_i++;

                var YJ_url3 = _f[_f_i].url,
                    YJ_img3 = _f[_f_i].img,
                    YJ_p_url3 = "/user/" + _f[_f_i].uid,
                    YJ_p_img3 = _f[_f_i].user_avatar,
                    YJ_title3 = _f[_f_i].title;

                _f_i++;

                var YJ_url4 = _n[_n_i].url,
                    YJ_img4 = _n[_n_i].img,
                    YJ_p_url4 = "/user/" + _n[_n_i].uid,
                    YJ_p_img4 = _n[_n_i].user_avatar,
                    YJ_title4 = _n[_n_i].title;

                _n_i++;

                templete_str = '<div class="row row-5 clearfix"><div class="cell h"><dl class="mg_r mg_b"><dt><a href="'+YJ_url1+'"target="_blank"><img src="'+YJ_img1+'"height="390"alt=""></a></dt><dd><i class="img_memb"></i><div class="img_info"><a href="'+YJ_p_url1+'"target="_blank"><img src="'+YJ_p_img1+'"alt=""></a><p><a href="'+YJ_url1+'"target="_blank">'+YJ_title1+'</a></p></div></dd></dl></div><div class="cell w"><dl class="mg_r mg_b"><dt><a href="'+YJ_url2+'"target="_blank"><img src="'+YJ_img2+'"width="590"height="185"alt=""></a></dt><dd><i class="img_memb"></i><div class="img_info"><a href="'+YJ_p_url2+'"target="_blank"><img src="'+YJ_p_img2+'"alt=""></a><p><a href="'+YJ_url2+'"target="_blank">'+YJ_title2+'</a></p></div></dd></dl><dl class="mg_r mg_b"><dt><a href="'+YJ_url3+'"target="_blank"><img src="'+YJ_img3+'"height="185"width="590"alt=""></a></dt><dd><i class="img_memb"></i><div class="img_info"><a href="'+YJ_p_url3+'"target="_blank"><img src="'+YJ_p_img3+'"alt=""></a><p><a href="'+YJ_url3+'"target="_blank">'+YJ_title3+'</a></p></div></dd></dl></div><div class="cell h"><dl class="mg_r mg_b"><dt><a href="'+YJ_url4+'"target="_blank"><img src="'+YJ_img4+'"height="390"alt=""></a></dt><dd><i class="img_memb"></i><div class="img_info"><a href="'+YJ_p_url4+'"target="_blank"><img src="'+YJ_p_img4+'"alt=""></a><p><a href="'+YJ_url4+'"target="_blank">'+YJ_title4+'</a></p></div></dd></dl></div></div>';

            break;

            case 6:
                var YJ_url1 = _s[_s_i].url,
                    YJ_img1 = _s[_s_i].img,
                    YJ_p_url1 = "/user/" + _s[_s_i].uid,
                    YJ_p_img1 = _s[_s_i].user_avatar,
                    YJ_title1 = _s[_s_i].title;

                _s_i++;

                var YJ_url2 = _s[_s_i].url,
                    YJ_img2 = _s[_s_i].img,
                    YJ_p_url2 = "/user/" + _s[_s_i].uid,
                    YJ_p_img2 = _s[_s_i].user_avatar,
                    YJ_title2 = _s[_s_i].title;

                _s_i++;

                var YJ_url3 = _n[_n_i].url,
                    YJ_img3 = _n[_n_i].img,
                    YJ_p_url3 = "/user/" + _n[_n_i].uid,
                    YJ_p_img3 = _n[_n_i].user_avatar,
                    YJ_title3 = _n[_n_i].title;

                _n_i++;

                var YJ_url4 = _l[_l_i].url,
                    YJ_img4 = _l[_l_i].img,
                    YJ_p_url4 = "/user/" + _l[_l_i].uid,
                    YJ_p_img4 = _l[_l_i].user_avatar,
                    YJ_title4 = _l[_l_i].title;

                _l_i++;

                templete_str = '<div class="row row-5 clearfix"><div class="cell h"><dl class="mg_b mg_r"><dt><a href="'+YJ_url1+'"target="_blank"><img src="'+YJ_img1+'"height="185"width="285"alt=""></a></dt><dd><i class="img_memb"></i><div class="img_info"><a href="'+YJ_p_url1+'"target="_blank"><img src="'+YJ_p_img1+'"alt=""></a><p><a href="'+YJ_url1+'"target="_blank">'+YJ_title1+'</a></p></div></dd></dl><dl class="mg_r mg_b"><dt><a href="'+YJ_url2+'"target="_blank"><img src="'+YJ_img2+'"height="185"alt=""></a></dt><dd><i class="img_memb"></i><div class="img_info"><a href="'+YJ_p_url2+'"target="_blank"><img src="'+YJ_p_img2+'"alt=""></a><p><a href="'+YJ_url2+'"target="_blank">'+YJ_title2+'</a></p></div></dd></dl></div><div class="cell h"><dl class="mg_r mg_b"><dt><a href="'+YJ_url3+'"target="_blank"><img src="'+YJ_img3+'"width="285"height="390"alt=""></a></dt><dd><i class="img_memb"></i><div class="img_info"><a href="'+YJ_p_url3+'"target="_blank"><img src="'+YJ_p_img3+'"alt=""></a><p><a href="'+YJ_url3+'"target="_blank">'+YJ_title3+'</a></p></div></dd></dl></div><div class="cell w"><dl class="mg_r mg_b"><dt><a href="'+YJ_url4+'"target="_blank"><img src="'+YJ_img4+'"height="390"alt=""></a></dt><dd><i class="img_memb"></i><div class="img_info"><a href="'+YJ_p_url4+'"target="_blank"><img src="'+YJ_p_img4+'"alt=""></a><p><a href="'+YJ_url4+'"target="_blank">'+YJ_title4+'</a></p></div></dd></dl></div></div>';

            break;

            case 7:
                var YJ_url1 = _l[_l_i].url,
                    YJ_img1 = _l[_l_i].img,
                    YJ_p_url1 = "/user/" + _l[_l_i].uid,
                    YJ_p_img1 = _l[_l_i].user_avatar,
                    YJ_title1 = _l[_l_i].title;

                _l_i++;

                var YJ_url2 = _n[_n_i].url,
                    YJ_img2 = _n[_n_i].img,
                    YJ_p_url2 = "/user/" + _n[_n_i].uid,
                    YJ_p_img2 = _n[_n_i].user_avatar,
                    YJ_title2 = _n[_n_i].title;

                _n_i++;

                var YJ_url3 = _s[_s_i].url,
                    YJ_img3 = _s[_s_i].img,
                    YJ_p_url3 = "/user/" + _s[_s_i].uid,
                    YJ_p_img3 = _s[_s_i].user_avatar,
                    YJ_title3 = _s[_s_i].title;

                _s_i++;

                var YJ_url4 = _s[_s_i].url,
                    YJ_img4 = _s[_s_i].img,
                    YJ_p_url4 = "/user/" + _s[_s_i].uid,
                    YJ_p_img4 = _s[_s_i].user_avatar,
                    YJ_title4 = _s[_s_i].title;

                _s_i++;

                templete_str = '<div class="row row-2 clearfix"><div class="cell w"><dl class="mg_r mg_b"><dt><a href="'+YJ_url1+'"target="_blank"><img src="'+YJ_img1+'"width="590"height="390"alt=""></a></dt><dd><i class="img_memb"></i><div class="img_info"><a href="'+YJ_p_url1+'"target="_blank"><img src="'+YJ_p_img1+'"alt=""></a><p><a href="'+YJ_url1+'"target="_blank">'+YJ_title1+'</a></p></div></dd></dl></div><div class="cell h"><dl class="mg_r mg_b"><dt><a href="'+YJ_url2+'"target="_blank"><img src="'+YJ_img2+'"height="390"alt=""></a></dt><dd><i class="img_memb"></i><div class="img_info"><a href="'+YJ_p_url2+'"target="_blank"><img src="'+YJ_p_img2+'"alt=""></a><p><a href="'+YJ_url2+'"target="_blank">'+YJ_title2+'</a></p></div></dd></dl></div><div class="cell h"><dl class="mg_b mg_r"><dt><a href="'+YJ_url3+'"target="_blank"><img src="'+YJ_img3+'"width="285"height="185"alt=""></a></dt><dd><i class="img_memb"></i><div class="img_info"><a href="'+YJ_p_url3+'"target="_blank"><img src="'+YJ_p_img3+'"alt=""></a><p><a href="'+YJ_url3+'"target="_blank">'+YJ_title3+'</a></p></div></dd></dl><dl class="mg_b mg_r"><dt><a href="'+YJ_url4+'"target="_blank"><img src="'+YJ_img4+'"width="285"height="185"alt=""></a></dt><dd><i class="img_memb"></i><div class="img_info"><a href="'+YJ_p_url4+'"target="_blank"><img src="'+YJ_p_img4+'"alt=""></a><p><a href="'+YJ_url4+'"target="_blank">'+YJ_title4+'</a></p></div></dd></dl></div></div>';

            break;
        }

        return templete_str;
    },index_recommend = function(){
        $("#index_main").empty();

        var _url = "/index_data";
        
        // 开始读接口，获取数据
        $.ajax({
            url : _url,
            success: function(d){
                var _D = jQuery.parseJSON(d),
                    templete_str = "";

                //console.log(_D)

                // 循环游记模块，可指定模块数量
                var Num = 8;
                for( i=0 ; i < Num ; i++){
                    // 获取模板
                    var _r = parseInt(Math.random()*7+1);

                    //templete_str += set_templete(_D , _r);

                    $("#index_main").append(set_templete(_D , _r));
                }

                //console.log(templete_str);

               
            }
        })

        
    }

    // 开始获取首页推荐游记
    index_recommend();

















/*********************************************************/
});