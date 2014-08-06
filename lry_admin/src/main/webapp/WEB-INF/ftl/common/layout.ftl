<#macro master>
    <#global layouting="master" />
<div id="content" class="clearfix">
    <#nested/>
</div>
</#macro>
<#macro spanning>
<div id="spanning">
    <#nested/>
</div>
</#macro>
<#macro maincolumn>
<div id="maincolumn">
    <#if layouting="article">
        <div class="top"></div>
    </#if>
    <#nested/>
    <#if layouting="article">
        <div class="bottom"></div>
    </#if>
</div>
</#macro>
<#macro leftcolumn>
<div id="leftcolumn">
    <#nested/>
</div>
</#macro>
<#macro rightcolumn>
<div id="rightcolumn">
    <#if layouting="article">
        <div class="top"></div>
    </#if>
    <#nested/>
    <#if layouting="article">
        <div class="bottom"></div>
    </#if>
</div>
</#macro>

<#macro article>
    <#global layouting="article" />
<div id="content">
    <div class="content-top"></div>
    <#nested />
    <div class="content-bottom"></div>
</div>
</#macro>

<#macro fullwidth>
    <#global layouting="fullwidth" />
<div id="content">
    <#nested />
</div>
</#macro>

<#macro resume>
<div id="content">
    <#global layouting="master" />
    <#nested/>
    <div class="floor"></div>
</div>
</#macro>

<#macro job>
<div id="content" class="content-blank clearfix">
    <#global layouting="job" />
     <#nested/>
</div>
</#macro>

<#macro invite>
<div id="content" class="content-blank clearfix">
    <#global layouting="invite" />
     <#nested/>
</div>
</#macro>

<#macro reg>
<div id="body">
    <#nested/>
</div>
</#macro>

<#macro account>
<div id="content">
    <div id="maincolumn">
        <div id="setting">
            <#nested/>
        </div>
    </div>
</div>
</#macro>