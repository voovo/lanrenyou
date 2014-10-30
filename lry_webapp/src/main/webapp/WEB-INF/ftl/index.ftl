<@page  title="首页-懒人游" description=""  keywords="" 
	assets=[
	"/resources/css/index.css",
	"/resources/css/plug/flexslider.css",
	"/resources/js/index.js",
	"/resources/js/plug/jquery.flexslider.js"
	]
	>

<!-- slide -->
<section class="slider">
    <div class="flexslider">
      <ul class="slides">
      	<#if bannerList??>
      	<#list bannerList as banner>
	        <li>
	            <a href="${banner.linkUrl!'javascript:;'}"><img src="${banner.picUrl!'/resources/imgs/slide/new_page.jpg'}" /></a>
	        </li>
        </#list>
        </#if>
      </ul>
    </div>
</section>

<!-- tips -->
<div id="tips"></div>

<!-- index_main -->
<div id="index_main">

</div>
</@page>