$(document).ready(function(){
	$('.table').dataTable();
});
$(document).ready(function(){
	$("a").click(function() {
		var elem = $(this);
        // Remove all classes from tables.
        $("table").removeClass( "focused" );
        // Get a.href value and extract its fragment id.
        var id = elem.attr("href");
        // Highlight table using fragment id.
        $(id).addClass( "focused" );
    });
});

//idioma
var table = $('.table').DataTable({
	language: {
		"decimal": "",
		"emptyTable": "No hay información",
		"info": "Mostrando _START_ a _END_ de _TOTAL_ Registros",
		"infoEmpty": "Mostrando 0 a 0 de 0 Registros",
		"infoFiltered": "(Filtrado de _MAX_ total de registros)",
		"infoPostFix": "",
		"thousands": ",",
		"lengthMenu": "Mostrar _MENU_ Registros",
		"loadingRecords": "Cargando...",
		"processing": "Procesando...",
		"search": "Buscar:",
		"zeroRecords": "Sin registros encontrados",
		"paginate": 
		{
			"first": "Primero",
			"last": "Ultimo",
			"next": "Seguir",
			"previous": "Volver"
		}
	}
});
