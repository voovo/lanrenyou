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
    <link rel="shortcut icon" type="image/ico" href="/resources/imgs/favicon.ico">
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