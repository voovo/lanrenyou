var v_messages = {
"R1-1":"该电邮将成为您的网站用户名",
"R1-2":"请输入电邮",
"R1-3":"请输入真实有效的电邮",
//"R1-4":"您输入的电邮已被注册，是否<a href='"+SystemProp.appServerUrl+"/cn/forget-password.html' style='color:#D80C18;text-decoration:underline;'>忘记密码？</a>",
"R1-4":"该电邮已被注册，请使用其他电邮",
"R1-5":"至少6个字符，包含字母和数字",
"R1-6":"请输入字母和数字",
"R1-7":"密码只能含字母与数字，请重新输入",
"R1-8":"密码不能少于6位，请重新输入",
"R1-9":"密码为至少6位的字母与数字组合",
"R1-10":"请再次输入密码",
"R1-11":"两次输入的密码不一致，请重新输入",
"R1-12":"密码只能含字母与数字，请重新输入",
"R1-13":"密码不能少于6位，请重新输入",
"R1-14":"请输入字母",
"R1-15":"请用拼音输入您身份证或护照上的姓氏",
"R1-16":"只接受拼音形式的姓氏，请重新输入",
"R1-17":"输入您身份证或护照上名字的拼音",
"R1-18":"请用拼音输入您身份证或护照上的名字",
"R1-19":"只接受拼音形式的名字，请重新输入",
"R1-20":"请输入真实有效的电话号码",
"R1-21":"请输入电话号码",
"R1-22":"请输入真实有效的电话号码",
"R2-3":"可输入中文或英文城市名",
"R2-4":"请输入中文或英文",
"R2-5":"请输入真实有效的邮编",
"R2-6":"请输入中文",
"R2-7":"不接受18岁以下客户",
"R2-8":"您还未达到公司规定的可交易年龄",
"R2-9":"请输入其他类型的证件名称",
"R2-10":"证件号码只能含有数字与字母",
"R2-11":"请输入证件的号码",
"R2-12":"证件号码格式有误，请重新输入"};

//check_Email-------sendAjax----------------------------------------
function check_email(email,ajaxUrl){
    var emailRegex = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.|-]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,6}$/;
    if(!email){
        return {"error":true,"message":v_messages['R1-3']};
    }
    if(!emailRegex.test(email)){
        return {"error":true,"message":v_messages['R1-3']};
    }   
    if(!ajaxUrl){
        return  {"error":false,"message":v_messages['R1-1']};
    }
    var result = {};
    var callBack = function(rs){
        //var code = rs.code;
        if(rs != 0){
            result.error = true;
            result.message = v_messages['R1-4'];
        }else{
            result.error = false;
            result.message = v_messages['R1-1'];
        }
    }
    $.ajax({
        url : ajaxUrl,
        type : "POST",
        async: false,
        dataType:'json',
        data : {"userName":email},
        success: callBack
    });
    return result;
}

//check_password----------------------------------
function check_password(password){
    var passwordRegex = /^(?![0-9]*$)(?![a-zA-Z]*$)[a-zA-Z0-9]{6,16}$/;
    if(!password){
        return {"error":true,"message":v_messages['R1-6']};
    }       
    return  passwordRegex.test(password) ? {"error":false,"message":v_messages['R1-5']} : {"error":true,"message":v_messages['R1-5']};
}

//match_password----------------------------------
function match_password(password,password2){
    if(!password2){
        return {"error":true,"message":v_messages['R1-6']};
    }
    return (password === password2) ? {"error":false,"message":v_messages['R1-5']} : {"error":true,"message":v_messages['R1-11']};
}

//check_name--------------------------------------
function check_name(name){
    var letterRegex = /^[a-zA-Z\s]{1,30}$/;
    if(!name){
        return {"error":true,"message":v_messages['R1-15']};
    }
    return letterRegex.test(name) ? {"error":false,"message":v_messages['R1-14']} : {"error":true,"message":v_messages['R1-14']};
}

//check_name--------------------------------------
function check_name_2(name){
    var letterRegex = /^[a-zA-Z\s]{1,30}$/;
    if(!name){
        return {"error":true,"message":v_messages['R1-18']};
    }
    return letterRegex.test(name) ? {"error":false,"message":v_messages['R1-14']} : {"error":true,"message":v_messages['R1-14']};
}

//check_phone--------------------------------------
function check_phone(phone){
    var letterRegex = /^\+?[0-9-()\s]{0,50}$/;
    if(!phone){
        return {"error":true,"message":v_messages['R1-21']};
    }
    return letterRegex.test(phone) ? {"error":false,"message":v_messages['R1-20']} : {"error":true,"message":v_messages['R1-22']};
}
//check_city----------------------------------------
function check_city(city){
    var cityRegex = /^[a-zA-Z\s\u4e00-\u9fa5]*$/;
    return cityRegex.test(city) ? {"error":false,"message":v_messages['R2-3']} : {"error":true,"message":v_messages['R2-4']};
}
//check_zipcode-------------------------------------
function check_zipcode(zipcode){
    var zipcodeRegex = /^[a-zA-Z0-9]*$/;
    return zipcodeRegex.test(zipcode) ? {"error":false,"message":''} : {"error":true,"message":v_messages['R2-5']}; 
}
//check_cnname--------------------------------------
function check_cnname(name){
    var cnRegex = /^[\u4e00-\u9fa5]*$/;
    return cnRegex.test(name) ? {"error":false,"message":''} : {"error":true,"message":v_messages['R2-6']}; 
}





