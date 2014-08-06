$(function(){
    $("a[name=upload],#otherInfo").bind("click",function(e){
        e.preventDefault();
        //传入参数到iframe中
        var docType = $(this).attr("docType");
        if(!docType || docType==""){
            alert("error");
            return;
        }
        var documentId = $(this).attr("documentId")||"";
        var cardId = $(this).attr("cardId")||"";
        //var src = SystemProp.memberServerUrl+"/cn/upload-box.html?docType="+docType+"&cardId="+cardId+"&documentId="+documentId+"&timestamp="+(new Date()).getTime();
        //var src = "";

        if(docType=="OT"){
            $("#uploadTitle").text("上传其他信息");
        }
        else if(docType=="CC2"){
            $("#uploadTitle").text("补充信用卡资料");
        }
        else{
            $("#uploadTitle").text("上传文件");
        }

        $("#uploadPop").fancybox();
        $("#upload_iframe").attr("src", src);
    });

    var bankCount = $(".bankAccountTr").length + $(".creditCardTr").length;
    if(bankCount==0){
        $("#getWithDrawal").addClass("disable");
        $("#addWithDrawal").addClass("disable");
        $("a[docType^=WD]").unbind('click').bind('click',function(){alert("请先填写您的出金银行信息！");});
    }
    else{
        $("#getWithDrawal").removeClass("disable");
        $("#addWithDrawal").removeClass("disable");
    }


    $("a[name=preview]").bind("click",function(e){
        e.preventDefault();
        var fileName = $(this).attr("fileName");
        if(!!fileName){
            var ext = fileName.substr(fileName.lastIndexOf(".")+1);
            if(ext=="pdf"){
                window.open(SystemProp.memberServerUrl+"/doc/view-doc!pdf.action?fileName="+fileName);
            }
            else{
                $("#previewImg").attr("src",SystemProp.memberServerUrl+"/doc/view-doc.action?fileName="+fileName);
            }
        }
    });

    $("a[name=delete]").bind("click",function(e){
        e.preventDefault();
        var documentId = $(this).attr("documentId");
        if(!!documentId){
            if(confirm('确定删除吗?')){
                $.ajax({
                    url : SystemProp.memberServerUrl+"/doc/delete-doc.json?ln=0",
                    type : "POST",
                    async : false,
                    dataType:'json',
                    data : {"documentId":documentId},
                    success: function(rs){
                        if(rs.code == 200){
                            alert("删除成功！");
                            location.reload();
                        }else{
                            alert(rs.message);
                        }
                    }
                });
            }
        }
    });

    $("#getWithDrawal").bind("click",function(e) {
        e.preventDefault();
        var ccCount = $("tr.creditCardTr").length + $("tr.unipayCardTr").length;
        if(ccCount==0){
            alert("由于没有您曾用信用卡入金记录，所以无需下载信用卡付款授权书。");
            return;
        }
        window.open(SystemProp.memberServerUrl+"/doc/download-doc.action?ln=0");
    });

    $("#bankInfo").bind("click",function(e){
        e.preventDefault();
        var len = $(".bankAccountTr").length;
        if(len>=3){
            alert("最多只能添加三条银行信息");
        }
        else{
            $("#submitBankAccount").show();
            $("#bankInfoForm input").attr("disabled",false);
            //$("#bankInfoForm input").attr("readonly",false);
            $("#bankInfoForm input").val("");
            var userName = $("#bankInfoForm input[name=accName]").attr("userName");
            if(!!userName && userName!=''){
                $("#bankInfoForm input[name=accName]").val(userName);
            }
            $("#bankPop .popTitle h4").text("添加银行信息");
            $('#bankPop').fancybox();
        }
    });
    //直辖市
    $("#city").blur(function(e) {
        var city = $(this).val().replace(/\s/g,'');
        if(/^(beijing|shanghai|chongqing|tianjin)$/i.test(city)){
            $("#state").val(city).attr('disabled',true);
        }else{
            $("#state").attr('disabled',false);
        }

    });
    //银行信息验证
    $("#bankInfoForm").find("input[name='bankNumber']").live("blur",function(e){
        e.preventDefault();
        var val = $.trim($(this).val());
        var reg = /^[a-zA-Z0-9]{4,30}$/,
            $errorMess = $(this).parents("div").eq(0).find(".errorMess");
        if(val !== '' && reg.test(val)){
            $(this).removeClass('error');
            $errorMess.hide();
        }else{
            $(this).addClass('error');
            $errorMess.html('请输入不少于4位数字和字母').show();
        }
    });
    $("#bankInfoForm").find("input[name='swift']").live("blur",function(e){
        e.preventDefault();
        var val = $.trim($(this).val());
        var reg = /^([a-zA-Z0-9]{8}|[a-zA-Z0-9]{11})$/,
        $errorMess = $(this).parents("div").eq(0).find(".errorMess");
        if(val !== '' && reg.test(val)){
            $(this).removeClass('error');
            $errorMess.hide();
        }else{
            $(this).addClass('error');
            $errorMess.html('请输入正确的Swift码').show();
        }
    });

    $("#bankInfoForm").find("input[name='accName']").live("change",function(e){
        e.preventDefault();
        var val = $.trim($(this).val());
        alert("请注意我们只接受注册用户本人的银行卡，请使用您自己的银行卡。");
    });
    $("#bankInfoForm").find("input.cnull").bind('blur',function(e){
        e.preventDefault();
        var val = $.trim($(this).val());
        var $errorMess = $(this).parents("div").eq(0).find(".errorMess");
        if(val!= ''){
            if(!/^[a-zA-Z0-9\s]*$/.test(val)){
                $(this).addClass('error');
                $errorMess.html("只能输入数字和字母").show();
            }else{
                $(this).removeClass('error');
                $errorMess.hide();
            }
        }else{
            $(this).addClass('error');
            $errorMess.html("请输入数字和字母，不能为空").show();
        }
    });
    $("#address").blur(function(e) {
        var $errorMess = $(this).parents("div").eq(0).find(".errorMess");
        if(/[\u4e00-\u9fa5]/g.test($(this).val())){
            $(this).addClass('error');
            $errorMess.html("只能输入字母，数字和空格").show();
        }else{
            $(this).removeClass('error');
            $errorMess.hide();
        }
    });

    var banInfoFormCheck = function (){
        $("#bankInfoForm").find("input.input").blur();
        if($("#bankInfoForm").find("input.error:visible").length>0){
            return false;
        }
        return true;
    };

    $("#submitBankAccount").bind("click",function(e) {
        e.preventDefault();

        if(!banInfoFormCheck()){
            alert('请完善错误信息！');
            return;
        }
        var bankInfo = form2object("bankInfoForm");

        var url = SystemProp.memberServerUrl+"/doc/bank-account!add.json?ln=0";
        var id = $("#bankInfoForm input[name=id]").val();
        var msg = "添加银行信息成功！";
        if(!!id && id!=''){
            url = SystemProp.memberServerUrl+"/doc/bank-account!update.json?ln=0";
            msg = "更新银行信息成功！";
        }

        //防止重复提交
        var isOk = $("#bankInfoForm").data("isOk") || true;
        if(!isOk){
            return;
        }else{
            $("#bankInfoForm").data("isOk",false);
        }

        var strBankNum = '';
        var strNum = $("#bankInfoForm input[name=bankNumber]").val();
        if(strNum.length < 9){
            strBankNum = strNum;
        }else{
            strBankNum = strNum.slice(0,4) + 'XXXXXXXX' + strNum.slice(-4);
        }
        var aliasName = prompt("是否需要给您的银行卡起个别名？",strBankNum);
        if (aliasName!=null && aliasName!="")
        {
            bankInfo.aliasName=aliasName;
        }

        $.ajax({
            url : url,
            type : "POST",
            async : false,
            dataType:'json',
            data : bankInfo,
            success: function(rs){
                $("#bankInfoForm").data("isOk",true);
                if(rs.code == 200){
                    alert(msg);
                    //$("#bankInfoForm input[name=id]").val("");
                    $("#bankInfoForm input").val("");
                    //var today = new Date();
                    //var day = today.getDate();
                    //var month = today.getMonth() + 1;
                    //var year = today.getFullYear();
                    //var today = year + "." + month + "." + day;
                    //$("#bankRow").after("<tr><td>银行卡#"+rs.data.bankAccount.bankNumber.substr(-4,4)+"</td><td>"+today+"</td><td>待审核</td><td><a class=\"btn btn1\" href=\"javascript:void(0)\" docType=\"BK\" cardId=\""+rs.data.bankAccount.id+"\" name=\"upload\">上传</a></td></tr>");
                    //$.fancybox.close();
                    location.reload();
                }else{
                    alert(rs.message);
                }
            }
        });
    });

    $("a[name=updateBankAccount]").bind("click",function(e) {
        e.preventDefault();

        var bankAccountId = $(this).attr("bankAccountId");
        $.ajax({
            url : SystemProp.memberServerUrl+"/doc/bank-account.json",
            type : "GET",
            async : false,
            dataType:'json',
            data : {"id":bankAccountId,"ln":0},
            success: function(rs){
                if(rs.code == 200){
                    var bank = rs.data.bankAccount;
                    $("#bankInfoForm input[name=id]").val(bank.id);
                    $("#bankInfoForm input[name=aliasName]").val(bank.aliasName);
                    $("#bankInfoForm input[name=bankNumber]").val(bank.bankNumber);
                    $("#bankInfoForm input[name=accName]").val(bank.accName);
                    $("#bankInfoForm input[name=swift]").val(bank.swift);
                    $("#bankInfoForm input[name=bankName]").val(bank.bankName);
                    $("#bankInfoForm input[name=branch]").val(bank.branch);
                    $("#bankInfoForm input[name=city]").val(bank.city);
                    $("#bankInfoForm input[name=state]").val(bank.state);
                    $("#bankInfoForm input[name=country]").val(bank.country);
                    $("#bankInfoForm input[name=address]").val(bank.address||'');
                    $("#bankInfoForm input").attr("disabled",false);
//                  //“收款人银行账号”、“姓名”、“国家” 该3项不可修改
//                  $("#bankInfoForm input[name=bankNumber]").attr("readonly",true);
//                  $("#bankInfoForm input[name=accName]").attr("readonly",true);
//                  $("#bankInfoForm input[name=country]").attr("readonly",true);

                    $("#submitBankAccount").show();
                    $("#bankPop .popTitle h4").text("更新银行信息");
                    $('#bankPop').fancybox();
                }else{
                    alert(rs.message);
                }
            }
        });
    });

    $("a[name=viewBankAccount]").bind("click",function(e) {
        e.preventDefault();

        var bankAccountId = $(this).attr("bankAccountId");
        $.ajax({
            url : SystemProp.memberServerUrl+"/doc/bank-account.json",
            type : "GET",
            async : false,
            dataType:'json',
            data : {"id":bankAccountId,"ln":0},
            success: function(rs){
                if(rs.code == 200){
                    var bank = rs.data.bankAccount;
                    $("#bankInfoForm input[name=id]").val(bank.id);
                    $("#bankInfoForm input[name=bankNumber]").val(bank.bankNumber);
                    $("#bankInfoForm input[name=accName]").val(bank.accName);
                    $("#bankInfoForm input[name=swift]").val(bank.swift);
                    $("#bankInfoForm input[name=bankName]").val(bank.bankName);
                    $("#bankInfoForm input[name=branch]").val(bank.branch);
                    $("#bankInfoForm input[name=city]").val(bank.city);
                    $("#bankInfoForm input[name=state]").val(bank.state);
                    $("#bankInfoForm input[name=country]").val(bank.country);
                    $("#bankInfoForm input[name=address]").val(bank.address||'');
                    $("#bankInfoForm input").attr("disabled",true);
                    $("#submitBankAccount").hide();

                    $("#bankPop .popTitle h4").text("查看银行信息");
                    $('#bankPop').fancybox();
                }else{
                    alert(rs.message);
                }
            }
        });
    });

    $("a[name=deleteBankAccount]").bind("click",function(e) {
        e.preventDefault();
        if(confirm("您确定删除吗?") == false){
            return;
        }
        var bankAccountId = $(this).attr("bankAccountId");
        $.ajax({
            url : SystemProp.memberServerUrl+"/doc/bank-account!delete.json",
            type : "POST",
            async : false,
            dataType:'json',
            data : {"id":bankAccountId,"ln":0},
            success: function(rs){
                if(rs.code == 200){
                    alert("删除银行信息成功！");
                    location.reload();

                }else{
                    alert(rs.message);
                }
            }
        });
    });

    //tip提示
    $("td.tip").hover(
        function(e){
            var offset = $(this).offset();
            var tip = $("#tableTip");
            var remark = $(this).attr("remark");
            if(!!remark && remark!=''){
                tip.find(".message").text(remark);
                tip.css({"top":offset.top+$(this).height()+8,"left":offset.left}).show();
            }
        },function(e){
            $("#tableTip").hide();
        });

    //新增信用卡信息------------------------------------------------------------
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
            $(this).parents(".item1").find(".message").removeClass("error");
            $(this).parents(".item1").find(".poptip").hide();
        }else{
            $(this).addClass("error");
            $(this).parents(".item1").find(".message").addClass("error");
            $(this).parents(".item1").find(".poptip").show();
        }
    });

    $("#holderName").blur(function(e){
        var name = $(this).val();
        if(!/^[a-zA-Z\s]+$/.test(name)){
            $(this).addClass("error");
            $(this).parents(".item1").find(".message").html("请输入与信用卡正面姓名相同的拼音").addClass("error");
        }else{
            $(this).removeClass("error");
            $(this).parents(".item1").find(".message").html("请注意我们只接受注册用户本人的信用卡，请使用您自己的信用卡").removeClass("error");
            $(this).parents("item1").find(".poptip").hide();
        }
    });
    $("#country").blur(function(e){
        $(this).removeClass("error");
        $(this).parents(".item1").find(".message").removeClass("error");
        $(this).parents(".item1").find(".poptip").hide();
    });


    if ($.browser.msie){
        // IE suspends timeouts until after the file dialog closes
        $(".uploadFile").bind('click change',function(event){
            setTimeout(function(){
                $(this).next().val($(this).val());
            }, 1);
        });
    }else{
        // All other browsers behave
        $(".uploadFile").bind("change",function(e){
            $(this).next().val($(this).val());
        });
    }

    $("#addCardInfo").bind("click",function(e){
        e.preventDefault();
        $("#uploadCardTitle").text("添加信用卡信息");
        $("#uploadform input").attr("readonly",false);
        $("#uploadform select").attr("disabled",false);
        $("#uploadform input[name=cardId]").val("");
        $("#uploadform input[name=documentId]").val("");
        $("#uploadform input[id^=cardNumber]").val("");
        $("#addNewCardPop").fancybox();
    });

    $("a[name=changeCardInfo]").bind("click",function(e){
        e.preventDefault();
        $("#uploadCardTitle").text("更新信用卡信息");
        var cardId = $(this).attr("cardId");
        var documentId = $(this).attr("documentId");
        $("#uploadform input[name=cardId]").val(cardId);
        if(!!documentId && documentId!=''){
            $("#uploadform input[name=documentId]").val(documentId);
        }
        $("#uploadform input[id^=cardNumber]").attr("readonly",true);
        $("#uploadform input[name=holderName]").attr("readonly",true);
        $("#uploadform select[name=country]").attr("disabled",true);

        $.ajax({
            url : SystemProp.memberServerUrl+"/doc/credit-card.json",
            type : "GET",
            async : false,
            dataType:'json',
            data : {"cardId":cardId,"ln":0},
            success: function(rs){
                if(rs.code == 200){
                    var card = rs.data.creditCard;
                    $("#cardNumber1").val(card.cardNumberReal.substr(0,4));
                    $("#cardNumber2").val(card.cardNumberReal.substr(4,4));
                    $("#cardNumber3").val(card.cardNumberReal.substr(8,4));
                    $("#cardNumber4").val(card.cardNumberReal.substr(12,4));
                    $("#holderName").val(card.holderName);
                    $("select[name=year]").val(rs.data.year);
                    $("select[name=month]").val(rs.data.month);
                    $("select[name=country]").val(card.country);
                    if(!card.isPaid || card.isPaid!=1){
                        $("#uploadform input").attr("readonly",false);
                        $("#uploadform select").attr("disabled",false);
                    }
                    $("#addNewCardPop").fancybox();
                }else{
                    alert(rs.message);
                }
            }
        });

    });

    $("a[name=deleteCardInfo]").bind("click",function(e){
        e.preventDefault();
        var cardId = $(this).attr("cardId");
        if(!!cardId){
            if(confirm('将从系统中删除该信用卡，确定吗?')){
                $.ajax({
                    url : SystemProp.memberServerUrl+"/doc/credit-card!delete.json?ln=0",
                    type : "POST",
                    async : false,
                    dataType:'json',
                    data : {"cardId":cardId},
                    success: function(rs){
                        if(rs.code == 200){
                            alert("删除成功！");
                            location.reload();
                        }else{
                            alert(rs.message);
                        }
                    }
                });
            }
        }
    });

    var isUpload = false;
    //取消上传
    $("#newCardCancel").bind("click",function(e) {
        e.preventDefault();
        if(isUpload){
            if(confirm("资料正在提交，确定取消吗？")){
                window.location.reload();
            }
        }else{
            $.fancybox.close();
        }
    });


    window.checkNewCardInfo = function(){
        var checkFileName = function(name){
             if(!name) return true;
             var pos = name.lastIndexOf(".");
             var lastname = name.substring(pos,name.length).toLowerCase();  //此处文件后缀名也可用数组方式获得str.split(".")
             if (lastname!=".jpg" && lastname!=".jpeg" && lastname!=".gif" && lastname!=".png" && lastname!=".pdf")
             {
                 alert("您上传的文件类型为"+lastname+"，只接受jpg、jpeg、png、gif、pdf文件，文件大小请小于2M");
                 return false;
             }else{
                 return true;
             }
        };

        //验证文件名
        //if(!checkFileName($("#frontCardImg").val())){
        //  return false;
        //}
        //if(!checkFileName($("#backCardImg").val())){
        //  return false;
        //}

        $(".cardbox .input").blur();
        if($(".cardbox").find("input.error:visible").length > 0){
            alert("请完善错误信息");
            return false;
        }
        return true;

    };

});



