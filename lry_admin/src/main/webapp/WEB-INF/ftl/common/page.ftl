<#include "elements.ftl" />
<#include "dialog.ftl" />
<#include "header.ftl" />
<#include "assets.ftl" />
<#macro page title="大街网_预见新自己！" assets=[] static={} useheader="default" usefooter="default" currentChannel="NONE" bodyclass="none" menu="0" description="" keywords="" flash=[] crossdomains=[] footerassets=[]>
<!DOCTYPE html>
<html class="${uaOS!''} ${uaBrowser!''}">
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="x-ua-compatible" content="ie=7"/>
    <meta name="saoatech-site-verification" content="N2Y5MzU2M2YyNmVhOTllMGQwYmI4NzAwMTIxYmE2ZDU=" />
    <title><#assign aTitle = title?replace('职业人脉从大街开始！','预见新自己！','ir')/><#if isSaeConfigContext?? && isSaeConfigContext?is_boolean && isSaeConfigContext><#assign aTitle = aTitle?replace('大街网_预见新自己','职业圈_大街网与新浪微博联手打造的人脉拓展应用','ir')/></#if>${aTitle}</title>
    <link rel="shortcut icon" href="${AssetsUrl("/images/favicon.ico")}" />
    <meta content="${description}" name="description"/>
    <meta content="${keywords}" name="keywords"/>
    <script type="text/javascript">
        var _head_start = new Date();<#if currentDate??>var _current_date = '${currentDate?string('yyyy-MM-dd HH:mm:ss')}';</#if>
            <#if (request.djBaseCSRFToken)??>
                <#assign tmpDjBaseCSRFToken = request.djBaseCSRFToken/>
            <#elseif djBaseCSRFToken??>
                <#assign tmpDjBaseCSRFToken = djBaseCSRFToken/>
            <#else>
                <#assign tmpDjBaseCSRFToken = ""/>
            </#if>
        var CSRFToken = '${tmpDjBaseCSRFToken}';
    </script>
    <@AssetsImport assets />
    <#if static?? && static?size gt 0>
        <@jselement file="/static/libs/seajs/sea.js"/>
        <script id="djnew" type="text/javascript" src="${AssetsUrl("${WrapperAssetsWithVersion('/static/libs/street/street.js')}")}"></script>
        <script type="text/javascript">
            var seaConfig = {
            'base':'<#if static.base?? && static.base?length gt 0>${static.base}<#else>http://${configs['domains.static']!"static.dajieimg.com"}</#if>'
                <#if static.alias?? && static.alias?size gt 0 && static.alias?is_hash_ex>
                    , 'alias':{
                    <#list static.alias?keys as key>
                        <#assign staticValue= static.alias[key]/>
                        <#if staticValue?ends_with('.css')>
                            <#assign staticValuePrefix=staticValue?substring(0,staticValue?last_index_of(".css"))/>
                        <#elseif staticValue?ends_with('.js')>
                            <#assign staticValuePrefix=staticValue?substring(0,staticValue?last_index_of(".js"))/>
                        <#else>
                            <#assign staticValuePrefix=staticValue/>
                        </#if>
                        <#assign assetsPath='/static/' + staticValue/>
                        <#if assetsDataMap[assetsPath]?? && shouldMergeAssets>
                            '${key}':'${staticValuePrefix}.${assetsDataMap[assetsPath]}.<#if staticValue?ends_with('.css')>css<#else>js</#if>'<#if key_has_next>,</#if>
                        <#else>
                            '${key}':'${staticValue}'<#if key_has_next>,</#if>
                        </#if>
                    </#list>
                }
                </#if>
                <#if static.preload?? && static.preload?size gt 0 && static.preload?is_sequence>
                    , 'preload':[<#list static.preload as item>"${item}"<#if item_has_next>,</#if></#list>]
                </#if>
            };
            seajs.config(seaConfig);
            street.sea.insertStyle(seaConfig);
        </script>
    </#if>
    <script type="text/javascript">
        $.domains = {
            www : "${configs['domains.www']}",
            assets : "${configs['domains.assets']}"
        };
            <#if uaBrowser?? && uaBrowser=='ua-ie6'>
            try {document.execCommand('BackgroundImageCache', false, true);} catch(e) {}
            </#if>
    </script>
</head>
<body <#if bodyclass != "none">class="${bodyclass}"</#if>>
<script type="text/javascript">var  _body_start = new Date();</script>
    <#if crossdomains?size!=0>
        <#list crossdomains as domain>
        <iframe src="http://${configs['domains.'+domain]}/crossdomain/proxy.html<#if currentDate??>?t=${currentDate.getTime()}</#if>" id="${domain}-ajax-frame" style="display:none;"></iframe>
        </#list>
    <script type="text/javascript">
        document.domain = '${configs['domains.root']}';
    </script>
    </#if>
    <div id="J_djResourcesVersion" style="display: none;">
        <input type="hidden" name="env" value="${configs['deploy.env']!''}"/>
        <input type="hidden" name="bigVersion" value="${configs['assets.major.version']!''}"/>
        <#if assetsMap??>
            <input type="hidden" name="rollbackId" value="${assetsMap['//rollbackId']!''}"/>
        </#if>
    </div>
    <#assign ChannelEnum = {
    'NONE':{'navClass':'','description':'','noSubTitle':false},
    'HOME':{'navClass':'dj-index','description':'','noSubTitle':false},
    'PROFILE':{'navClass':'dj-profile-mini','description':'','noSubTitle':false},
    'CARD':{'navClass':'dj-networks clearfix','description':'人&nbsp;脉','noSubTitle':false,'enName':'CONTACT'},
    'JOB':{'navClass':'dj-job','description':'&nbsp;','noSubTitle':false},
    'RESUME':{'navClass':'dj-resume','description':'简&nbsp;历','noSubTitle':false},
    'CORP':{'navClass':'','description':'','noSubTitle':true},
    'ASK':{'navClass':'dj-ask','description':'问&nbsp;答<span style="font-size:10px; vertical-align:baseline; position:relative; top:-20px; left:10px; font-family:tahoma">BETA</span>','noSubTitle':false},
    'NOTICE':{'navClass':'dj-notice','description':'通&nbsp;知','noSubTitle':false},
    'SETTING':{'navClass':'dj-account','description':'设&nbsp;置','noSubTitle':false},
    'CIRCLE':{'navClass':'dj-job-circle','description':'职业圈','noSubTitle':true,'enName':'CIRCLE'},
    'CIRCLE-SEARCH':{'navClass':'dj-job-circle-sch','description':'职业圈','noSubTitle':false},
    'CIRCLE-GUIDE':{'navClass':'dj-job-circle-reg','description':'职业圈','noSubTitle':false},
    'CIRCLE-NEW-GUIDE':{'navClass':'dj-job-circle-hi201','description':'职业圈','noSubTitle':false},
    'CLUB':{'navClass':'dj-club','description':'名企招聘','noSubTitle':false},
    'IDENTIFY':{'navClass':'dj-job-value-site','description':'','noSubTitle':false},
    'JOB-HR':{'navClass':'dj-employment','description':'','noSubTitle':true},
    'ENTERPRISE':{'navClass':'dj-enterprise','description':'','noSubTitle':true},
    'GROUP':{'navClass':'dj-group','description':'圈子','noSubTitle':false,'enName':'Group<span class="status">Beta</span>'},
     'SKILLGO':{'navClass':'dj-skillgo','description':'技能闯关','noSubTitle':true},
    'TALENT':{'navClass':'dj-job','description':'人才库<em>TALENT&nbsp;POOL</em>','noSubTitle':false}
    }/>
    <#assign realChannel = 'NONE'/>
    <#if ChannelEnum[currentChannel]??><#assign realChannel = currentChannel/></#if>
    <#if useheader != "none">
        <@header vary="${useheader}" menu="${menu}" currentChannel="${realChannel}"/>
    </#if>
<div class="dj-content-wrap <#if useheader = 'default'>${ChannelEnum[realChannel].navClass}</#if>" id="dj-content-wrap">
    <div class="dj-content-inner">
        <#if ChannelEnum[realChannel].noSubTitle>
            <div class="dj-content-shadow">
                <#nested/>
            </div>
        <#else>
            <#if useheader = 'default' && ChannelEnum[realChannel]?? && ChannelEnum[realChannel].navClass!=''>
                <div class="dj-sub-title">
                <#--每个频道的具体内容在header.ftl中控制-->
                    <#if ChannelEnum[realChannel].description?length gt 0><h2>${ChannelEnum[realChannel].description}<#if (ChannelEnum[realChannel].enName)??><em>${ChannelEnum[realChannel].enName}</em></#if></h2><#else><h2 style="display:none;"></h2></#if>
                </div>
            </#if>
            <div class="dj-content-shadow">
                <#nested/>
            </div>
        </#if>
    </div>
</div>
    <#if usefooter != "none">
        <#include "footer.ftl"/>
    </#if>
    <#if flash?seq_contains("upload") || flash?seq_contains("swfupload")>
        <@jselement file='/lib/flash/swfupload.js' />
    </#if>
    <#if flash?size != 0>
        <@jselement file='/js/jquery.swfajax.js' />
    </#if>
    <#if flash?seq_contains("ajax") || flash?seq_contains("swfajax")>
    <object
            id="swfhttprequest" class="hidden"
            classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000"
            codebase="http://fpdownload.adobe.com/pub/shockwave/cabs/flash/swflash.cab#version=9,0,0,0">
        <param name="movie" value="${AssetsUrl("/lib/flash/swfhttprequest.swf")}"/>
        <param name="allowScriptAccess" value="always"/>
        <embed
                class="hidden"
                src="${AssetsUrl("/lib/flash/swfhttprequest.swf")}"
                allowScriptAccess="always"
                type="application/x-shockwave-flash"
                pluginspage="http://www.adobe.com/shockwave/download/index.cgi?P1_Prod_Version=ShockwaveFlash">
        </embed>
    </object>
    </#if>

<script type="text/javascript">
    var root_canShowRegFloat = true;
    $(function(){
        var is_login = $.cookie('dj_auth') != null || $.cookie('dj_auth_v3') != null;
    });
</script>
    <#assign domain = request.getServerName()>
    <#if !(dontShowAccountPop?? && dontShowAccountPop) && ((domain?? && (!user?? || request.requestURL.toString()?index_of('/job/index') gt 0) && (domain == configs['domains.job'] || domain == configs['domains.club'] || clubInfo?? )) || (forceLogin?? && forceLogin) || domain == configs['domains.campus'] || (request.requestURL.toString()?index_of('/corp/search') gt 0) ) >
        <@csselement file='/css/modules/register-pop.css' />
        <#if current_corp?? || domain == configs['domains.campus'] || (request.requestURL.toString()?index_of('/corp/search') gt 0)>
        <#include 'account/account-pop-test6.ftl'/>
        <#elseif domain == configs['domains.job']>
            <#include 'account/account-pop-test7.ftl'/>
        <#else>
        <#include 'account/account-pop.ftl'/>
        </#if>

        <@popreg defaultPop='common-reg' hasDm=true/>
    <script type="text/javascript">
            <#if !(noShowLogin?? && noShowLogin)>
            $(function () {
            	forceLogin();
        	});
            </#if>
        function forceLogin(){
            if(document.referrer.indexOf("hao123.com")>0){
                return;
            }
            var auth = $.cookie('dj_auth_v3');
            if (auth == null || auth.length <= 0) {
                auth = $.cookie('dj_auth');
            }
            var login_tips = $.cookie("login_tips");
            if(auth==null || auth.length <= 0){
                if (login_tips == null) {
                    var currentDate = new Date();
                    var cookieExpiredTime=new Date(currentDate.getFullYear(),currentDate.getMonth(),currentDate.getDate(),currentDate.getHours() + 1,currentDate.getMinutes(),currentDate.getMinutes());
                    $.cookie('login_tips','1',{ expires: cookieExpiredTime, path: '/', domain: '${configs["domains.root"]}' });
                    pop_reg('/','common-reg',true);
                    root_canShowRegFloat =false;
                }
            }
        }
    </script>
    </#if>

	<#-- current_corp?? && !(CorpBusinessOrder??) 有订单的公司不出现此浮层
		(current_corp.id != 3491454) @宋平辉
		-->
    <#if domain?? && (!user??) &&
    ((domain == configs['domains.job'] && request.requestURL.toString()?index_of('http://job.c.dajie.com') < 0)||
    (current_corp?? && (current_corp.id != 3491454) ) ||
    (clubInfo?? && (!((clubInfo.getCustomType())??) || clubInfo.getCustomType() == 0) ) ||
    	(request.requestURL.toString()?index_of('/corp/search') gt 0) ||
	    (
	    	domain == configs['domains.campus'] &&
	    	(
	    		(request.requestURL.toString() == "/") ||
	    		(request.requestURL.toString()?index_of('/campusIndex') gt 0) ||
	    		(request.requestURL.toString()?index_of('/square') gt 0) ||
	    		(request.requestURL.toString()?index_of('/project/catch') gt 0)
	    	)
	    )
    )
    >
        <#include 'account/account-reg.ftl'/>
        <@csselement file='/up/login-lay/css/login.css' />
        <@popRegFloat />
    </#if>
    <#if !loginUser?? && (!dontShowAccountPop?? || !dontShowAccountPop)>
        <#include 'account/account-pop-new.ftl'/>
    </#if>

    <#if holderDeposit??>
    ${holderDeposit}
    </#if>

    <#list footerassets as file>
        <#if file?ends_with('.js') && !defaultAssets[file]??>
            <@jselement file='${file}' />
        </#if>
    </#list>
</body>
</html>
</#macro>

<#macro dialog>
    <#nested/>
    <#if holderDeposit??>
    ${holderDeposit}
    </#if>
</#macro>