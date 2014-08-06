// 左侧菜单
function expand(el) {
    childobj = document.getElementById("child" + el);

    if (childobj.style.display == 'none') {
        childobj.style.display = 'block';
    } else {
        childobj.style.display = 'none';
    }
    return;
}



$(function(){
    // 表格鼠标经过
    $(".mytab tr:not(.hide):even").addClass("tb");
    $(".mytab tr").not("thead tr , tr.hide").hover(function(){
        $(this).addClass("tbHover");
    }, function(){
        $(this).removeClass("tbHover");
    });

    $(".UserInfo").click(function(){
        var UserInfo = $(this).closest('tr').next("tr.hide");

        if(UserInfo.is(":visible")){
            UserInfo.fadeOut();
        }else{
            $(this).closest("table").find(".hide").hide();
            UserInfo.fadeIn();
        }
    });

    // 搜索  日期
    $('.date-pick').datePicker({clickInput:true,startDate:'1900-01-01'});
    $('#start-date').bind(
        'dpClosed',
        function(e, selectedDates)
        {
            var d = selectedDates[0];
            if (d) {
                d = new Date(d);
                $('#end-date').dpSetStartDate(d.addDays(1).asString());
            }
        }
    );
    $('#end-date').bind(
        'dpClosed',
        function(e, selectedDates)
        {
            var d = selectedDates[0];
            if (d) {
                d = new Date(d);
                $('#start-date').dpSetEndDate(d.addDays(-1).asString());
            }
        }
    );
});