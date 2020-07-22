package dds.gesoc.model.organizaciones;

public class Empresa extends EntidadJuridica {

    private int ventasAnuales; //TODO hay que calcularlo?
    private TipoEmpresa tipoEmpresa;
    private Sector sector;


    public Empresa (String nombreFicticio, Categoria categoria, double montoEsperado, String razonSocial, String cuit, String direccionPostal, Sector sector, int ventasAnuales) {
        super(nombreFicticio, categoria, montoEsperado, razonSocial, cuit, direccionPostal);
        this.sector = sector;
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

