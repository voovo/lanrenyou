$(function(){$("#accountType").change(function(d){var b=$(this).val();var c=$(this).attr("mt4AccountId");if(!!b&&!!c){if(confirm("\u662f\u5426\u63d0\u4ea4?")){$.ajax({url:SystemProp.memberServerUrl+"/user/change-mt4-account-type.json?ln=0",type:"POST",async:false,dataType:"json",data:{mt4AccountId:c,accountType:b},success:function(e){if(e.code==200){alert("\u4fee\u6539\u6210\u529f\uff01")}else{alert(e.message)}}})}}});var a=function(c,b){$("#userAccountTip").offset({top:(c.top*1+30)+"px",left:c.left+"px"});$("#userAccountTip").find(".userStatus").html(b.status||"");$("#userAccountTip").find(".userName").html(b.userName||"");$("#userAccountTip").fadeIn(100)};$(".userAcc").hover(function(d){$(this).addClass("red");var c=$(this);var f=c.offset();var b=c.data("info")||null;if(b){a(f,b)}else{$.ajax({url:"",type:"POST",async:false,dataType:"json",data:{},success:function(e){if(e.code==200){c.data("info",e.data);a(f,e.data)}}})}},function(b){$(this).removeClass("red");$("#userAccountTip").fadeOut()})});