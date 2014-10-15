<#include "assets.ftl" />
<#macro page title="懒人游，您的旅游定制管家！" assets=[] useheader="default" usefooter="default" description="" keywords="">
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="x-ua-compatible" content="ie=7"/>
    <meta content="${description} 自助游,游记,攻略,经验,懒人游,旅游攻略,出境游,出境自助游" name="description"/>
    <meta content="${keywords} 自助游,游记,攻略,经验,懒人游,旅游攻略,出境游,出境自助游" name="keywords"/>
    <title>${title!''}</title>
    <!-- 全站通用 -->
    <link rel="icon" type="image/x-icon" href="/resources/imgs/favicon.ico">
    <link rel="shortcut icon" href="/resources/imgs/favicon.ico" type="image/x-icon" />
    <!-- 全站通用 -->
    <@AssetsImport assets />
</head>
<body>
	<#if useheader != "none">
		<#include "header.ftl"/>
	</#if>
	<#nested/>
	<#if usefooter != "none">
        <#include "footer.ftl"/>
    </#if>
</body>
</html>
</#macro>

<#macro pageNav total = "1" current = "1" urlpattern="#">
    <#assign totalInt = total?number?int />
    <#assign currentInt = current?number?int />
    <#if totalInt lte 1>
        <#return />
    </#if>
<div id="page" class="page">
	<ul class="clearfix">
    <#if currentInt == 1>
        <li class="cur"><a>1</a></li>
    <#else>
        <li><a href="${urlpattern?replace("%d", "1")}">1</a></li>
    </#if>
    <#assign p1 = 2 />
    <#if currentInt gte 3>
        <#assign p1 = (currentInt - 2) />
    </#if>
    <#assign p2 = 5 />
    <#if currentInt gte 3>
        <#assign p2 = (currentInt + 2) />
    </#if>
    <#if currentInt gt (totalInt - 2)>
        <#assign p1 = (totalInt - 4) />
    </#if>
    <#if p1 gt 2>
        <li>....</li>
    </#if>
    <#list p1 .. p2 as i>
        <#if i gte 2>
            <#if i gte totalInt>
                <#break />
            </#if>
            <#if currentInt == i>
                <li><a class="cur">${i}</a></li>
            <#else>
                <li><a href="${urlpattern?replace("%d", "" + i)}">${i}</a></li>
            </#if>
        </#if>
    </#list>
    <#if p2 lt (totalInt - 1)>
        <li>...</li>
    </#if>
    <#if currentInt == totalInt>
        <li><a class="cur">${totalInt}</a></li>
    <#else>
        <li><a href="${urlpattern?replace("%d", "" + totalInt)}">${totalInt}</a></li>
    </#if>
    </ul>
</div>
</#macro>