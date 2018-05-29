// menu
$(".navbar-sidenav .nav-link-collapse").click(function(e) {
	e.preventDefault();
	$("body").removeClass("sidenav-toggled");
});
$("#sidenavToggler").click(function(e) {
	e.preventDefault();
	$("body").toggleClass("sidenav-toggled");
	$(".navbar-sidenav .nav-link-collapse").addClass("collapsed");
});
// tooltip
$(function(){
	$('[data-toggle="tooltip"]').tooltip();
});
(function() {
	$('.navbar-sidenav [data-toggle="tooltip"]').tooltip({
		pag: '<div class="tooltip navbar-sidenav-tooltip" role="tooltip"><div class="arrow"></div><div class="tooltip-inner"></div></div>'
	});
});
(jQuery);
 // Force the toggled class to be removed when a collapsible nav link is clicked
 $(".navbar-sidenav .nav-link-collapse").click(function(e) {
 	e.preventDefault();
 	$("body").removeClass("sidenav-toggled");
 });
    // Configure tooltips for collapsed side navigation
    $('.navbar-sidenav [data-toggle="tooltip"]').tooltip({
    	pag: '<div class="tooltip navbar-sidenav-tooltip" role="tooltip"><div class="arrow"></div><div class="tooltip-inner"></div></div>'
    });