var descripcion_input = document.getElementById('descripcion_input');
var razonsocial_input = document.getElementById('razonsocial_input');
var cuit_input = document.getElementById('cuit_input');
var codigoinscripcionigj_input = document.getElementById('codigoinscripcionigj_input');

function deshabilitar(elemento) {
  id = elemento.value;
  
  if(id == "0"){
  	descripcion_input.disabled = true;
	razonsocial_input.disabled = true;
	cuit_input.disabled = true;
	codigoinscripcionigj_input.disabled = true;
  }

  if(id == "1"){
    descripcion_input.disabled = false;
	razonsocial_input.disabled = true;
	cuit_input.disabled = true;
	codigoinscripcionigj_input.disabled = true;
  }

  if(id == "2"){
    descripcion_input.disabled = true;
	razonsocial_input.disabled = false;
	cuit_input.disabled = false;
	codigoinscripcionigj_input.disabled = false;
  }
}

/*$( function() {
    $("#id_categoria").change( function() {
    	if ($(this).val() === "0") {
            $("#razonsocial_input").prop("disabled", true);
            $("#cuit_input").prop("disabled", true);
            $("#codigoinscripcionigj_input").prop("disabled", true);
            $("#descripcion_input").prop("disabled", true);
        }

        if ($(this).val() === "1") {
            $("#razonsocial_input").prop("disabled", true);
            $("#cuit_input").prop("disabled", true);
            $("#codigoinscripcionigj_input").prop("disabled", true);
            $("#descripcion_input").prop("disabled", false);
        }

        if ($(this).val() === "2") {
            $("#razonsocial_input").prop("disabled", false);
            $("#cuit_input").prop("disabled", false);
            $("#codigoinscripcionigj_input").prop("disabled", false);
            $("#descripcion_input").prop("disabled", true);
        }
    });
});*/