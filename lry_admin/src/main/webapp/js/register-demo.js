;$(function(){
    var url = "/tradingAccounts/getDemoMT4Account",
         uid = $("#uid").val();
    $.ajax({
        url: "/tradingAccounts/getDemoMT4Account",
        type: "POST",
         async: false,
         data : {uid : uid},
         success : function(data){
            var userName = data.demoMT4Account,
                password = data.demoMT4Password;
            $("#loadingBox").html("<p>请牢记MT4模拟账户信息</p><p>用户名："+userName+"</p><p>密码："+password+"</p>");
         },
         error : function(){
            alert("网络错误，请刷新页面");
         }
    });
});