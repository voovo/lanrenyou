$(function(){
    $('.date-pick').datePicker({clickInput:true,startDate:'1900-01-01'});

    var selectNationality = $("#nationality option:selected").val();
    if(!selectNationality || selectNationality==""){
        $("#nationality option[value=CN]").attr("selected",true);
    }

    var selectCountry = $("#country option:selected").val();
    if(!selectCountry || selectCountry==""){
        $("#country option[value=CN]").attr("selected",true);
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

    var $dateInput = $("#birthday");
    $dateInput.blur(function(e){
        var dateRegex = /^((((1[6-9]|[2-9]\d)\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\d|3[01]))|(((1[6-9]|[2-9]\d)\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\d|30))|(((1[6-9]|[2-9]\d)\d{2})-0?2-(0?[1-9]|1\d|2[0-8]))|(((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))$/;
        if(!$(this).val()){
            errorShow($(this),{"error":true,"message":"请选择出生日期"});
        }else if(!dateRegex.test( $.trim($(this).val()) ) ){
            errorShow($(this),{"error":true,"message":"输入日期格式有误，如：1970-09-28"});
        }else{
            var value = $(this).val();
            var year = value.split('-')[0]*1;
            if((new Date().getFullYear()*1 - year)<17){
                errorShow($(this),{"error":true,"message":"公司不接受18岁以下客户进行交易"});
            }else{
                errorShow($(this),{"error":false,"message":"公司不接受18岁以下客户进行交易"});
            }
        }
    });
    $dateInput.datepicker({
        dateFormat:"yy-mm-dd",
        dayNamesMin:[ "日", "一", "二", "三", "四", "五", "六" ],
        changeMonth: true,
        changeYear: true,
        showOtherMonths: true,
        selectOtherMonths: true,
        yearRange: "1930:2013",
        monthNamesShort:["01","02","03","04","05","06","07","08","09","10","11","12"],
        onSelect:function(dateText){
            var year = dateText.split('-')[0]*1;
            if((new Date().getFullYear()*1 - year) < 18){
                errorShow($dateInput,{"error":true,"message":"公司不接受18岁以下客户进行交易"});
            }else{
                errorShow($dateInput,{"error":false,"message":"公司不接受18岁以下客户进行交易"});
            }
        }
    });

    //bind--------------------------
    $("#nationality,#country").change(function(e) {
        //var nationality = $(this).val();
        //var nations = ['1','2']; //无法服务的国家的值的集合
        //if($.inArray(nationality,nations) == -1 ){
        //  errorShow($(this),{"error":false,"message":""});
        //}else{
        //  errorShow($(this),{"error":true,"message":"目前AETOS无法服务你所在的国家或地区"});
        //}
        if($(this).val()!=''){
            var status = $(this).find("option:selected").attr("status");
            if(status!='1'){
                errorShow($(this),{"error":true,"message":"目前AETOS无法服务您所在的国家或地区"});
            }
            else{
                errorShow($(this),{"error":false,"message":""});
            }
        }
    });

    $("#surName").blur(function(e) {
        var name = $(this).val();
        var res = check_name_2(name);
        errorShow($(this),res);
    });
    $("#lastName").blur(function(e) {
        var name = $(this).val();
        var res = check_name(name);
        errorShow($(this),res);
    });
    // $("#userName").blur(function(e) {
    //     var email = $(this).val();
    //     var checkEmailAjaxUrl = "/tradingAccounts/verifyEmail";
    //     var res = check_email(email,checkEmailAjaxUrl);

    //     errorShow($(this),res);
    // });
    $("#phone").blur(function(e) {
        var phone = $(this).val();
        var res = check_phone(phone);

        errorShow($(this),res);
    });
    //投资经验
    $("#forExp,#otherExp").change(function(e) {
        e.preventDefault();
        if($("#forExp").val() == 1 || $("#otherExp").val() == 1){
            $("#riskRule").show();
        }else{
            $("#riskRule").hide();
        }
    });

    //证件类型
    $("#form_2").find("input[name='identityType']").change(function(e) {
        var value = $("#form_2").find("input[name='identityType']:checked").eq(0).val();
        if(value == 9){
            $("#identityOthers").show();
        }else{
            $("#identityOthers").val('').hide();
        }
    });

    $("#identityOthers").blur(function(e) {
        if(!$(this).val()){
            $(this).addClass("error");
            alert("请输入证件类型");
        }else{
            $(this).removeClass("error");
        }
    });


    $("#city").blur(function(e) {
        var res = check_city($(this).val());
        errorShow($(this),res);
    });
    $("#zipCode").blur(function(e) {
        var res = check_zipcode($(this).val());
        errorShow($(this),res);
    });
    $("#nickName").blur(function(e) {
        var res = check_cnname($.trim($(this).val()));
        errorShow($(this),res);
    });
    $("#identity").blur(function(e) {
        if(!$(this).val()){
            errorShow($(this),{"error":true,"message":""});
        }else{
            errorShow($(this),{"error":false,"message":""});
        }
    });

    //-----------------ib注册----------------------------
    /*
    $("#form_2").find("input[name='isIbSigned']").change(function(e) {
        var value = $("#form_2").find("input[name='isIbSigned']:checked").eq(0).val();
        if(value == 1){
            $("#box100,#box101").show();
        }else{
            $("#box100,#box101").hide();
        }
    });
    $("#form_2").find("input[name='agentType']").change(function(e) {
        var value = $("#form_2").find("input[name='agentType']:checked").eq(0).val();
        if(value == 0){
            $("#box102").hide();
        }else{
            $("#box102").show();
        }
    });

    $("#accMr").blur(function(e) {
        if(!(/^\d+$/.test($(this).val())) ){
            $(this).addClass("error");
            alert("请输入数字类型的客户经理编码");
        }else{
            $(this).removeClass("error");
        }
    });
    $("#orgLicense").blur(function(e) {
        if(/^[a-zA-Z0-9]+$/.test($(this).val())){
            $(this).removeClass("error");
        }else{
            $(this).addClass("error");
            alert("请输入合法的机构注册码");
        }
    });
    */
    $("#form_2").find("select").blur(function(index, element) {
        var id = $(this).attr("id");
        if(id == 'nationality'|| id=='country'){
            if($(this).val() === ''){
                var message = (id == 'nationality') ? '请选择国籍' : '请选择居住国家'
                errorShow($(this),{"error":true,"message":message});
            }
            return;
        }
        if($(this).val() === ''){
            errorShow($(this),{"error":true,"message":""});
        }else{
            errorShow($(this),{"error":false,"message":""});
        }
    });
    //浏览器刷新---radio---select-----
    if($("#form_2").find("input[name='identityType']:checked").eq(0).val() == 9){
        $("#identityOthers").show();
    }
    if($("#forExp").val() == '1' || $("#otherExp").val()== '1'){
        $("#riskRule").show();
    }
    /*
    if($("#form_2").find("input[name='isIbSigned']:checked").val() == 0){
        $("#box100,#box101").hide();
    }else{
        $("#box100,#box101").show();
    }
    */
    //select----刷新------
    $("#nationality,#country").change();

    function formCheck(){
        //select--------------------
        var $form = $("#form_2");
        $form.find("select:visible,.input:visible").blur();
        if($form.find(".error:visible").length > 0){
            $("#topcontrol").click();
            return false;
        }
        if($form.find("input[name='idType']:checked").length<1){
            alert("请选择证件类型");
            $("#topcontrol").click()
            return false;
        }
        if($("#agreement0").is(":visible") && !$("#agreement0").is(":checked")){
            alert("请选择风险接受 ");
            return false;
        }
        if(!$("#agreement1").is(":checked")){
            alert("请阅读并接受客户协议 ");
            return false;
        }
        if(!$("#agreement2").is(":checked")){
            alert("请阅读并接受风险提示 ");
            return false;
        }
        if(!$("#agreement3").is(":checked")){
            alert("请阅读并接受金融服务导向 ");
            return false;
        }
        if(!$("#agreement4").is(":checked")){
            alert("请阅读并接受产品披露声明");
            return false;
        }
        if(!$("#agreement5").is(":checked")){
            alert("请阅读并接受使用条款");
            return false;
        }
//      if($form.find(":checkbox:visible").length !== $form.find(":checkbox:checked:visible").length){
//          alert("请你接受协议");
//          return false;
//      }

        return true;
    }
    //submit----------------------------------
    $("#submit").click(function(e) {
        e.preventDefault();
        //var regType = $(this).attr("name");
        if(!formCheck()){
            return;
        }
        //防止重复提交
        var isOk = $("#form_2").data("isOk") || true;
        if(!isOk){
            return;
        }else{
            $("#form_2").data("isOk",false);
        }
        var userInfo = form2object("form_2");
        //if(regType === 'live'){
            userInfo.profileName = 'LIVE';
            userInfo.mt4Type = 2;
        //}

        userInfo.lang = 0;
        userInfo.ln = 0;
        $("#submit").text("正在处理中");
        $("#form_2").submit();
        // $.ajax({
        //     url : SystemProp.trustServerUrl+"/user/register-live-info.json",
        //     type : "POST",
        //     async : false,
        //     dataType:'json',
        //     data : userInfo,
        //     success: function(rs){
        //         $("#submit").text("完善个人信息");
        //         $("#form_2").data("isOk","true");
        //         if(rs.code == 200){
        //             window.location.href = SystemProp.trustServerUrl + "/cn/register-live-result.html";
        //         }
        //         else{
        //             alert(rs.message);
        //         }
        //     }
        // });

    });






});