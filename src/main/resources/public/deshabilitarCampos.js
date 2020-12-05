$( function() {
  $("#tipo_entidad").change( function() {
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
});