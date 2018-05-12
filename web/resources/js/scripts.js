$(document).ready(function(){
	$('#tabla').dataTable();
});
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
	$('[data-toggle="tooltip"]').tooltip()
});
(function() {
	$('.navbar-sidenav [data-toggle="tooltip"]').tooltip({
		pag: '<div class="tooltip navbar-sidenav-tooltip" role="tooltip"><div class="arrow"></div><div class="tooltip-inner"></div></div>'
	})
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


    //registro
    function registro(){ 

      var Nombre    = $('#Name').val();
      var Apellido  = $('#Lname').val();
      var Documento = $('#Document').val();
      var Edad      = $('#age').val();
      var Telefono  = $('#phone').val();
      var Direccion = $('#dom').val();
      var Correo    = $('#mail').val();

      console.log(usuario);
      console.log(password);

      if (Nombre == "Santiago" && Apellido == "Jimenez" && Documento == "1030692021" && Edad == "19" && Telefono == "123456789") {     
        swal({
          type : 'success',
          title : 'Bien¡',
          text : 'Se registro el usuario, puede seguir registrando',
        });
      }
      else if(Nombre == "" || Apellido == "" || Documento == "" || Edad == "" || Telefono == "" || Direccion == "" || Correo == "" ){
        swal({
          type : 'error',
          title: 'Disculpe, pero',
          text : 'Debe ingresar los datos.',
          animation: false,
          customClass: 'animated tada',
          position:'bottom',
        });
      }
    }