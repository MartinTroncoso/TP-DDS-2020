package dds.gesoc.model.organizaciones;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Transient;

@Entity
@DiscriminatorValue("E")
public class Empresa extends EntidadJuridica {

    private int ventasAnuales; //TODO hay que calcularlo?
    
    @Enumerated
    private TipoEmpresa tipoEmpresa;
    
    @Transient
    private Sector sector;

    public Empresa(){
    }

    public Empresa (String nombreFicticio, Categoria categoria, double montoEsperado, String razonSocial, String cuit, String direccionPostal, Sector sector, int ventasAnuales) {
<<<<<<< HEAD
        super(nombreFicticio, categoria, montoEsperado, razonSocial, cuit, direccionPostal, -1);
        this.sector = sector; //TODO revisar el código de inscripción IGJ en empresa
=======
        super(nombreFicticio, categoria, montoEsperado, razonSocial, cuit, direccionPostal, ventasAnuales);
        this.sector = sector;
>>>>>>> a050d7259f6c511b724b3ef07baeb0c789d550d9
        this.ventasAnuales = ventasAnuales;
        tipoEmpresa = sector.clasificarPorMonto(ventasAnuales);
    }

    public int getVentasAnuales() {
        return ventasAnuales;
    }

    public TipoEmpresa getTipoEmpresa() {
        return tipoEmpresa;
    }

    public Sector getSector() {
        return sector;
    }

    public void setVentasAnuales(int ventasAnuales) {  //nada más para hacer el test
        this.ventasAnuales = ventasAnuales;
        tipoEmpresa = sector.clasificarPorMonto(ventasAnuales);
    }
}

