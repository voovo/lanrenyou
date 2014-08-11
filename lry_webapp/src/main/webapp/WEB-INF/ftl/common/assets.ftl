<#assign defaultAssets={
"/resources/css/init.css":1,
"/resources/css/main.css":1,
"/resources/js/jquery.js":1,
"/resources/js/main.js":1,
"/resources/js/plug/jquery.reveal.js":1,
"/resources/js/search.js":1,
"/resources/css/plug/reveal.css":1
} />
<#function AssetsUrl file="">
	<#--用macro宏会导致空格的出现，所以换成function-->
    <#assign assetsDomain = "${configs.get('domains.ui')}"/>
    <#if file?index_of("/") == 0><#return "http://${assetsDomain}${file}"/><#else><#return "http://${assetsDomain}/${file}"/></#if>
</#function>
<#macro csselement file="">
    <#if file?length gt 0>
    <link rel="stylesheet" type="text/css" href="${AssetsUrl(file)}"/>
    </#if>
</#macro>
<#macro jselement file="">
    <#if file?length gt 0>
    <script type="text/javascript" src="${AssetsUrl(file)}"></script>
    </#if>
</#macro>
<#macro AssetsImport assets=[] needDefaultAssets=true>
    <#if needDefaultAssets>
        <#list defaultAssets?keys as file>
            <#if file?ends_with('.js')>
                <@jselement file='${file}' />
            <#else>
                <@csselement file='${file}' />
            </#if>
        </#list>
    </#if>
    <#list assets as file>
        <#if !defaultAssets[file]?? && (file?ends_with('.js') || file?ends_with('.css'))>
            <#if file?ends_with('.js')>
                <@jselement file='${file}' />
            <#else>
                <@csselement file='${file}' />
            </#if>
        </#if>
    </#list>
    <#if requiredJs??>
        <#list requiredJs as file>
            <@jselement file='${file}' />
        </#list>
    </#if>
</#macro>
