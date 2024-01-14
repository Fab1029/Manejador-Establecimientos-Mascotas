package modulos.Evaluacion;

import java.io.Serializable;
import java.util.Date;

public class EInformeMonitoreo extends Evaluacion implements Serializable {

    private int NroResolucion;
    private String tipoMonitoreo;

    public EInformeMonitoreo() {
        super();
    }

    public EInformeMonitoreo(int codigoProyecto, Date fechaUltimaAceptacion, boolean oficioUltimaAprobacion, int nroResolucion, String tipoMonitoreo) {
        super(codigoProyecto, fechaUltimaAceptacion, oficioUltimaAprobacion);
        NroResolucion = nroResolucion;
        this.tipoMonitoreo = tipoMonitoreo;
    }

    //Comienzo funciones y metodos


    public int getNroResolucion() {
        return NroResolucion;
    }

    public String getTipoMonitoreo() {
        return tipoMonitoreo;
    }

    public void setNroResolucion(int nroResolucion) {
        NroResolucion = nroResolucion;
    }

    public void setTipoMonitoreo(String tipoMonitoreo) {
        this.tipoMonitoreo = tipoMonitoreo;
    }
}
