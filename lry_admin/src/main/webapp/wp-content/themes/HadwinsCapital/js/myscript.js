jQuery(document).ready(function($){
	$(".slider").bxSlider({
		pager:false,
		controls:false,
		auto:true
	});
	$(".mainnav").superfish();
	$(".mainnav ul li").first().addClass('firstNav');
});
