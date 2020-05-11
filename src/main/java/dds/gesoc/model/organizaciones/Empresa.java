package dds.gesoc.model.organizaciones;

//import grupo8.Exeptions.NoClasificaComoPymeException;

import java.math.BigInteger;

public class Empresa extends EntidadJuridica {

    private int ventasAnuales; //TODO hay que calcularlo?
    private TipoEmpresa tipoEmpresa;
    private Sector sector;


    public Empresa (String nombreFicticio, String razonSocial,String cuit,String direccionPostal, Sector sector, int ventasAnuales) {
        super(nombreFicticio, razonSocial, cuit, direccionPostal);
        this.sector = sector;
        this.ventasAnuales = ventasAnuales;
        tipoEmpresa = sector.clasificarPorMonto(ventasAnuales);
    }



}


enum TipoEmpresa {
    MICRO,
    PEQUEÑA,
    MEDIANA_TRAMO1,
    MEDIANA_TRAMO2;
}

