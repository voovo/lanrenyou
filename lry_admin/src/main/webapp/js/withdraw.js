;$(function(){
    // 出金提交
    var checkCJform = function(){
        var _obj = $(".cjForm table td input"),
            _tag = true;
        _obj.each(function(){
            if($(this).val().length <= 0){
                _tag = false;
                $(this).addClass("err");
            }
        });
        return _tag;
    }
    $(".cjSbBtn").click(function(){
        var hassb = $(this).attr("hassb");
        if(checkCJform() && hassb == "0"){
            $.ajax({
                url : $("form").attr("action"),
                type : "POST",
                success : function(){
                    alert("提交成功，2-3个工作日即可处理完毕");
                    $(this).attr("hassb" , "1");
                },
                error : function(){
                    alert("提交失败，请稍后再试");
                }
            });
        }
    });

    $(".cjForm table td input").focus(function(){
        $(this).removeClass("err");
    });


});