//入金menus
$.fn.tabs = function(options){
    var defaults = {'select':1};
    var opt = $.extend({},defaults,options);
    var tabsTitle = $(this).children(".tb_title"),
        tabsInfo = $(this).children(".tb_content");
        tabsInfo.children().hide();
    tabsTitle.find("li>a").bind('click',function(e){
            e.preventDefault();
            var id = $(this).attr("href");
            //换图标
            var imgIcon = tabsInfo.find(".visa_head span");
            if(id == "#a1"){
                imgIcon.removeClass().addClass("visa_image");   
            }else if(id == '#a3'){
                id = '#a1';
                imgIcon.removeClass().addClass("securePay_image");
            }else if(id == '#a4'){
                alert('Eway支付网关在维护中。请稍后再尝试。');
                return;             
//              id = '#a1';
//              imgIcon.removeClass().addClass("eway_image");
            }else if(id == '#a5'){
//              alert('服务正在维护中');
//              return;
            }else if(id == '#a6'){
                alert('Skrill支付网关在维护中。请稍后再尝试。');
                return;
            }           
            tabsTitle.find("li.current").removeClass("current");
            $(this).parents("li").eq(0).addClass("current");
            tabsInfo.find(">div").hide();
            id = (id=='#a3' || id=='#a4')? '#a1' : id;
            tabsInfo.find(id).show();
            
            
        });
    var select = (opt.select > tabsTitle.find("li>a").length) ? 1 : opt.select;
    tabsTitle.find("li>a").eq(select-1).click();            
};



$(function($){
    var selectCountry = $("#country option:selected").val();
    if(!selectCountry || selectCountry==""){
        $("#country option[value=CN]").attr("selected",true);
    }   
    
    //入金的默认menu
    $("#tabs").tabs({'select':1});
    //电汇
    $("#wire_content").tabs();
    $("#wire_content2").tabs();
    
    //**********银行选择***********
    $('.tab_tit > li').click(function(){
        var ii = $(this).index();
        if(ii>0){
            $('.tab_tit > li').removeClass('tab_on');
            $(this).addClass('tab_on');
            
            $('.wire_content').hide().eq(ii-1).toggle();
        }
    });
    //是否选择已有信用卡卡号
    $("input[name='isNewCardNum']").change(function(e) {
        if($("input[name='isNewCardNum']:checked").val() == '1'){
            $("#newCardInfo").show();   
        }else{
            $("#newCardInfo").hide();       
        }
    });
    if($("input[name='isNewCardNum']:checked").val() == '1'){
        $("#newCardInfo").show();   
    }   
    
    //信用卡卡号输入
    $(".cardNum").keyup(function(e) {
        var value = $(this).val();
        if(value.length >3){
            $(this).next(".cardNum").focus();
        }
        if(e.keyCode == 8 && value.length==0){
            if($(this).prev().length === 0) return;
            var elm = $(this).prev().get(0);
            var length = elm.value.length;
            if(elm.createTextRange) {   // IE 
                var textRange = elm.createTextRange(); 
                textRange.moveStart('character', length);             
                textRange.collapse(false);        
                textRange.select();      
            } else if(elm.setSelectionRange) { // Firefox 
                elm.setSelectionRange(length, length); 
                elm.focus(); 
            } 
        }
    });
    $(".cardNum").blur(function(e) {
        if(/^\d{4}$/.test($(this).val())){
            $(this).removeClass("error");
            $(this).parents(".ll").find(".message").removeClass("error");
            $(this).parents(".ll").find(".poptip").hide();
        }else{
            $(this).addClass("error");
            $(this).parents(".ll").find(".message").addClass("error");
        }
    });
    $("#amount,#amount01,#amount02").focus(function(){
        $(this).parents(".ll").find(".poptip").show();
    }).blur(function(e){
        var amount = $(this).val();
        if(isNaN(amount) || amount*1 <= 0){
            $(this).addClass("error");
            $(this).parents(".ll").find(".message").addClass("error");
        }else{
            $(this).val((amount).replace(/^(.*\..{2}).*$/,"$1"));
            $(this).removeClass("error");
            $(this).parents(".ll").find(".message").removeClass("error");
            $(this).parents(".ll").find(".poptip").hide();
        }
    });
    
    $("#holderName").blur(function(e){
        var name = $(this).val();
        if(!/^[a-zA-Z\s]+$/.test(name)){
            $(this).addClass("error");
            $(this).parents(".ll").find(".message").html("请输入与信用卡正面姓名相同的拼音").addClass("error");
        }else{
            $(this).removeClass("error");
            $(this).parents(".ll").find(".message").html("请注意我们只接受注册用户本人的信用卡，请使用您自己的信用卡").removeClass("error");
            $(this).parents(".ll").find(".poptip").hide();
        }
    });
    $("#country").blur(function(e){
        $(this).removeClass("error");
        $(this).parents(".ll").find(".message").removeClass("error");
        $(this).parents(".ll").find(".poptip").hide();
    });
    $("#cvvCode").blur(function(e) {
        var code = $(this).val();
        if(isNaN(code) || code.length !=3){
            $(this).addClass("error");
            $(this).parents(".ll").find(".message").html("请输入信用卡片背面签名栏的后3位数字").addClass("error");
        }else{
            $(this).removeClass("error");
            $(this).parents(".ll").find(".message").html("CVV安全码是您信用卡背面签名栏的后 三位数字").removeClass("error");
        }
    });
    
    var isSubmit = true;
    $("#submit").click(function(e){
        e.preventDefault();
        
        $("#depositForm").find("input.input:visible").blur();
        if($("#depositForm").find("input.error").length >0){
            return false;
        }
        
        var payInfo = form2object("depositForm");
        payInfo.ln = 0;
        payInfo.depositType = '1';
        //payInfo.gateWayId = 1;
        payInfo.cardType = 1;
        payInfo.validDate = payInfo.year+payInfo.month;
        payInfo.cardNumber = payInfo.cardNumber1+payInfo.cardNumber2+payInfo.cardNumber3+payInfo.cardNumber4;
        
        var currentGateWay = $("#tabTitle").find("li.current").find("a").attr("class");
        if(!!currentGateWay){
            if(currentGateWay=="securePay"){
                payInfo.gateWayId = 1;
            }
            else if(currentGateWay=="eway"){
                payInfo.gateWayId = 2;
            }
        }
        
        //2013-11-08新增逻辑，如果needSecurePay=1则仍然通过securepay支付
        var needSecurePay = "0";
        var isNewCardNum = $("#depositForm input[name=isNewCardNum]:checked").val();
        if(isNewCardNum=="0"){
            needSecurePay = $("#depositForm select[name=creditCardId] :selected").attr("needSecurePay");
        }
        if(!payInfo.gateWayId && needSecurePay=="0"){
            var action = SystemProp.trustServerUrl+"/pay/web-pay.action";
            $("#depositForm input[name=validDate]").val(payInfo.validDate);
            $("#depositForm input[name=cardNumber]").val(payInfo.cardNumber);

            $("#depositForm").attr("action", action);
            $("#depositForm").attr("onSubmit", "");
            $("#depositForm").submit();
            return;
        }
        
        if(isSubmit){
            showWaitingBox();
            $("#submit").text("正在处理中");
            isSubmit = false;
            $.ajax({
              url : SystemProp.trustServerUrl+"/pay/activate.json",
//            async : false,
              type : "POST",
              dataType : 'json',
              data : payInfo,
              success : function(rs){
                if(rs.code ==200){
                    var userId = rs.data.user.id;
                    var mt4LoginId = rs.data.user.mt4Account.mt4LoginId;
                    if(!!userId){
                        //sendTrack(userId,'Active',payInfo.amount,mt4LoginId);
                        window.location.href = SystemProp.trustServerUrl + "/cn/activate-result.html";
                    }
                    //线程暂停1秒
                    //setTimeout(function(){window.location.href = SystemProp.trustServerUrl + "/cn/activate-result.html";},1000);
                }else{
                    hideWaitingBox();
                    alert(rs.message);
                }
                $("#submit").text("确认入金");
                isSubmit = true;
              }
            });
        }
        else{
            alert("后台正在处理中，请耐心等待......");
        }
    });
    
    //----------------------cashU-submit--------------------
    $("#submit_cashu").click(function(e){
        e.preventDefault();
        if($("#amount01").hasClass("error")){
            return;
        }
        var action = SystemProp.trustServerUrl+"/pay/web-pay.action?ln=0";
        $("#depositForm_cashu").attr("action", action);
        $("#depositForm_cashu").submit();
    }); 
    
    //----------------------skrill-submit--------------------
    $("#submit_skrill").click(function(e){
        e.preventDefault();
        if($("#amount02").hasClass("error")){
            return;
        }
        var action = SystemProp.trustServerUrl+"/pay/web-pay.action?ln=0";
        $("#depositForm_skrill").attr("action", action);
        $("#depositForm_skrill").submit();
    });
    //----------------------unionpay-submit--------------------
    $("#submit_unionpay").click(function(e){
        e.preventDefault();
        var action = SystemProp.trustServerUrl+"/pay/web-pay.action?ln=0";
        $("#depositForm_unionpay").attr('target', '_blank');
        $("#depositForm_unionpay").attr("action", action);
        $("#depositForm_unionpay").submit();
        $('.yl_overlay,.yl_mess').show();
    }); 


});
