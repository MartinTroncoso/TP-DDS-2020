package dds.gesoc.model.organizaciones;

import dds.gesoc.model.organizaciones.Exeptions.NoClasificaComoPymeException;

public class Sector {

    private int maxMicro;
    private int maxPequenia;
    private int maxMediana1;
    private int maxMediana2;

    public TipoEmpresa clasificarPorMonto(int ventasAnuales) {

        if(ventasAnuales <= maxMicro)
            return TipoEmpresa.MICRO;
        else if (ventasAnuales <= maxPequenia)
            return TipoEmpresa.PEQUEÑA;
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


class SectorConstruccion extends Sector {

    public SectorConstruccion() {
        setMaxMicro(15230000);
        setMaxPequenia(90310000);
        setMaxMediana1(503880000);
        setMaxMediana2(755740000);
    }
}

class SectorIndustriaMineria extends Sector {

    public SectorIndustriaMineria() {
        setMaxMicro(26540000);
        setMaxPequenia(190410000);
        setMaxMediana1(1190330000);
        setMaxMediana2(1739590000);
    }
}

class SectorServicios extends Sector {

    public SectorServicios() {
        setMaxMicro(8500000);
        setMaxPequenia(50950000);
        setMaxMediana1(425170000);
        setMaxMediana2(607210000);
    }
}

class SectorAgropecuario extends Sector {

    public SectorAgropecuario() {
        setMaxMicro(12890000);
        setMaxPequenia(48480000);
        setMaxMediana1(345430000);
        setMaxMediana2(547890000);
    }
}

class SectorComercio extends Sector {

    public SectorComercio() {
        setMaxMicro(29740000);
        setMaxPequenia(178860000);
        setMaxMediana1(1502750000);
        setMaxMediana2(2146810000);
    }
}

