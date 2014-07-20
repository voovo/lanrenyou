<#include "assets.ftl" />
<#macro page title="懒人游，您的旅游定制管家！" assets=[] useheader="default" usefooter="default" description="" keywords="">
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="x-ua-compatible" content="ie=7"/>
    <meta content="${description}" name="description"/>
    <meta content="${keywords}" name="keywords"/>
    <title>${titile!''}</title>
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
<div class="paging">
    <#if currentInt gt 1>
        <a class="prev" href="${urlpattern?replace("%d", "" + (currentInt - 1))}"></a>
    </#if>
    <#if currentInt == 1>
        <span class="current">1</span>
    <#else>
        <a href="${urlpattern?replace("%d", "1")}">1</a>
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
        <a class="dot" href="${urlpattern?replace("%d", "" + (currentInt - 3))}" title="第${currentInt - 3}页"></a>
    </#if>
    <#list p1 .. p2 as i>
        <#if i gte 2>
            <#if i gte totalInt>
                <#break />
            </#if>
            <#if currentInt == i>
                <span class="current">${i}</span>
            <#else>
                <a href="${urlpattern?replace("%d", "" + i)}">${i}</a>
            </#if>
        </#if>
    </#list>
    <#if p2 lt (totalInt - 1)>
        <a class="dot" href="${urlpattern?replace("%d", "" + (p2 + 1))}" title="第${p2 + 1}页"></a>
    </#if>
    <#if currentInt == totalInt>
        <span class="current">${totalInt}</span>
    <#else>
        <a href="${urlpattern?replace("%d", "" + totalInt)}">${totalInt}</a>
    </#if>
    <#if currentInt lt totalInt>
        <a class="next" href="${urlpattern?replace("%d", "" + (currentInt + 1))}"></a>
    </#if>
</div>
</#macro>