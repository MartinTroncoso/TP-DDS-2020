$( function() {
    $("#tipo_entidad").change( function() {
        if ($(this).val() === "1") {
            $("#juridicaInput").prop("disabled", true);
            $("#baseInput").prop("disabled", true);
        }
        if ($(this).val() === "1") {
            $("#juridicaInput").prop("disabled", true);
            $("#baseInput").prop("disabled", false);
        } 
        if ($(this).val() === "2"){
            $("#baseInput").prop("disabled", true);
            $("#juridicaInput").prop("disabled", false);
        }
    });
});