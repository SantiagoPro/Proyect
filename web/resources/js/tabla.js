$(document).ready(function(){
	$('#tabla').dataTable();
});
$(document).ready(function(){
	$("a").click(function() {
		var elem = $(this);
        // Remove all classes from tables.
        $("table").removeClass( "focused" )
        // Get a.href value and extract its fragment id.
        var id = elem.attr("href");
        // Highlight table using fragment id.
        $(id).addClass( "focused" );
    });
});
//eliminar
   $(document).on('click','.delete', function(){
    var row = $(this);
swal({
  title: 'Seguro?',
  text: "El usuario se eliminará",
  type: 'warning',
  showCancelButton: true,
  confirmButtonColor: 'red',
  cancelButtonColor: 'green',
  confirmButtonText: 'Borrar',
  cancelButtonText:'cancelar'
}).then((result) => {
  row.parent().parent().remove();
    swal("Listo", "El usuario esta eliminado", "success")
  
})
});


//idioma
var table = $('#tabla').DataTable({
	language: {
		"decimal": "",
		"emptyTable": "No hay información",
		"info": "Mostrando _START_ a _END_ de _TOTAL_ Entradas",
		"infoEmpty": "Mostrando 0 a 0 de 0 Entradas",
		"infoFiltered": "(Filtrado de _MAX_ total entradas)",
		"infoPostFix": "",
		"thousands": ",",
		"lengthMenu": "Mostrar _MENU_ Entradas",
		"loadingRecords": "Cargando...",
		"processing": "Procesando...",
		"search": "Buscar:",
		"zeroRecords": "Sin resultados encontrados",
		"paginate": 
		{
			"first": "Primero",
			"last": "Ultimo",
			"next": "Siguiente",
			"previous": "Anterior"
		}
	},
});
