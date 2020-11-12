var baseInput = document.getElementById('baseInput');
var juridicaInput = document.getElementById('baseInput');

function deshabilitar(elemento) {
  d = elemento.value;
  
  if(d == "0"){
    baseInput.disabled = true;
  }else{
    juridicaInput.disabled = true;
  }

  if(d == "1"){
    baseInput.disabled = false;
  }else{
    juridicaInput.disabled = true;
  }

  if(d == "2"){
    baseInput.disabled = true;
  }else{
    juridicaInput.disabled = false;
  }
}