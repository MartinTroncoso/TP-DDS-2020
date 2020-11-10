package dds.gesoc.model.organizaciones;

import javax.persistence.Entity;

import dds.gesoc.entities.EntidadPersistente;
import dds.gesoc.exceptions.NoClasificaComoPymeException;

@Entity
class Sector extends EntidadPersistente{

    private int maxMicro;
    private int maxPequenia;
    private int maxMediana1;
    private int maxMediana2;

    public TipoEmpresa clasificarPorMonto(int ventasAnuales) {
        //Ventas anuales debe ser positivo

        if(ventasAnuales <= maxMicro)
            return TipoEmpresa.MICRO;
        else if (ventasAnuales <= maxPequenia)
            return TipoEmpresa.PEQUENIA;
        else if (ventasAnuales <= maxMediana1)
            return TipoEmpresa.MEDIANA_TRAMO1;
        else if (ventasAnuales <= maxMediana2)
            return TipoEmpresa.MEDIANA_TRAMO2;

        throw new NoClasificaComoPymeException("El Monto de ventas anuales no corresponde a una pyme");
       // return null;
    }

    public void setMaxMicro(int maxMicro) {
        this.maxMicro = maxMicro;
    }

    public void setMaxPequenia(int maxPequenia) {
        this.maxPequenia = maxPequenia;
    }

    public void setMaxMediana1(int maxMediana1) {
        this.maxMediana1 = maxMediana1;
    }

    public void setMaxMediana2(int maxMediana2) {
        this.maxMediana2 = maxMediana2;
    }

    public int getMaxMicro() {
        return maxMicro;
    }

    public int getMaxPequenia() {
        return maxPequenia;
    }

    public int getMaxMediana1() {
        return maxMediana1;
    }

    public int getMaxMediana2() {
        return maxMediana2;
    }
}


