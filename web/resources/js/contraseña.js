//contraseñas
function Validar(){ 
  
  var usuario = $('#user').val(); 
  var password = $('#pass').val();

  console.log(usuario);
  console.log(password);

  if (usuario == "1030692021" && password == "1030692021" ) {     
    $('#formLogin').attr('action', 'Usuarios/Principal.entrenador.xhtml');
  }
  else if(usuario == "876543219" && password == "876543219" ){
    $('#formLogin').attr('action', 'deportista.html');
  }
  else if(usuario == "1234567890" && password == "1234567890" ){
    $('#formLogin').attr('action', 'administrador.html');
  }
  else if(usuario == "" && password == ""){
    swal({
      type : 'error',
      title: 'Oops',
      text : 'You have enter the password!.',
      animation: false,
      customClass: 'animated tada',
      position:'bottom',
    });
  }
  else if(usuario != "1030692021" && usuario != "876543219" && usuario != "1234567890" ){
    swal({
      type : 'error',
      title: 'Verify the data',
      text : 'User worng.',
      animation: false,
      customClass: 'animated rotateIn',
      position:'bottom',
    });
  }
  else if(password != "1030692021" && password != "876543219" && password != "1234567890" ){
    swal({
      type : 'error',
      title: 'Verify the data',
      text : 'Password worng.',
      animation: false,
      customClass: 'animated rotateIn',
      position:'bottom',
    });
  }
  else if(password != "1030692021" && password != "876543219" && password != "1234567890" && usuario != "1030692021" && usuario != "876543219" && usuario != "1234567890"  ){
    swal({
      type : 'error',
      title: 'Verify yors data',
      text : 'The data are worng.',
      animation: false,
      customClass: 'animated rotateIn',
      position:'bottom',
    });
  }
}

//cambio de contraseña

function verificarclave(){

  var pass1 = $('#clave1').val();
  var pass2 = $('#clave2').val();
  var Doc = $('#Doc').val();

  if (pass1 == "" && pass2 == "" && Doc == ""){
   swal({
    type : 'error',
    title: 'Si quiere cambiar la contraseña',
    text : 'Ingrese los datos.',
    animation: false,
    customClass: 'animated jackInTheBox',
    position:'bottom',
  });
 }

 else if (pass2 == pass1 && Doc == ""){
   swal({
    type : 'error',
    title: 'Para cambiar la contraseña',
    text : 'Ingrese el documento.',
    animation: false,
    customClass: 'animated jackInTheBox',
    position:'bottom',
  });
 }

 else if (pass2 != pass1 ){
   swal({
    type : 'error',
    title: 'Las contraseñas no son iguales.',
    text : 'Por favor verifique las contraseñas.',
    animation: false,
    customClass: 'animated jackInTheBox',
    position:'bottom',
  });
 }

 else if (pass1 == "" && pass2 ==""){
   swal({
    type : 'error',
    title: 'Ingrese las contraseñas',
    animation: false,
    customClass: 'animated jackInTheBox',
    position:'bottom',
  });
 }

 else if(pass1 == pass2 && Doc !== "") {
   $(document).ready(function() {
    swal({ 
      type: "success", 
      title:"Felicitaciones,",
      text: "Ya puede ingresar con la nueva contraseña.",
      animation: false,
      customClass: 'animated fadeInDownBig', 
    },
    function(){
     $('#recuperar').attr('href', 'Pag_inicio.html');
   });
  });
 }
}