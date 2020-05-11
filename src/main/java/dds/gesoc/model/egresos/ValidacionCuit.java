package dds.gesoc.model.egresos;

public class ValidacionCuit {
	public static boolean validate(String cuit){
        //Eliminamos todos los caracteres que no son n�meros
        cuit = cuit.replaceAll("[^\\d]", "");
        //Controlamos si son 11 n�meros los que quedaron, si no es el caso, ya devuelve falso
        if (cuit.length() != 11){
            return false;
        }
        
        //Convertimos la cadena que qued� en una matriz de caracteres
        String[] cuitArray = cuit.split("");
        //Inicializamos una matriz por la cual se multiplicar�n cada uno de los d�gitos
        Integer[] serie = {5, 4, 3, 2, 7, 6, 5, 4, 3, 2};
        //Creamos una variable auxiliar donde guardaremos los resultados del calculo del n�mero validador
        Integer aux = 0;
        //Recorremos las matrices de forma simultanea, sumando los productos de la serie por el n�mero en la misma posici�n
        for (int i=0; i<10; i++){
            aux += Integer.valueOf(cuitArray[i]) * serie[i];
        }
        
        //Hacemos como se especifica: 11 menos el resto de de la divisi�n de la suma de productos anterior por 11
        aux = 11 - (aux % 11);
        
        //Si el resultado anterior es 11 el c�digo es 0
        if (aux == 11){
            aux = 0;
        }
        
        //Si el resultado anterior es 10 el c�digo no tiene que validar, cosa que de todas formas pasa en la siguiente comparaci�n.
        //Devuelve verdadero si son iguales, falso si no lo son
        return Integer.valueOf(cuitArray[10]) == aux;
    }
}
