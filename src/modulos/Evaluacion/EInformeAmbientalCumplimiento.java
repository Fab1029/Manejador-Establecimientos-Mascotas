package modulos.Evaluacion;

import java.io.Serializable;
import java.util.Date;

public class EInformeAmbientalCumplimiento extends Evaluacion implements Serializable {
    private int NroResolucion;
    private Date fechaResolucion;
    private Date periodoEvaluado;


    public EInformeAmbientalCumplimiento() {
        super();
    }

    public EInformeAmbientalCumplimiento(int codigoProyecto, Date fechaUltimaAceptacion, boolean oficioUltimaAprobacion, int nroResolucion, Date fechaResolucion, Date periodoEvaluado) {
        super(codigoProyecto, fechaUltimaAceptacion, oficioUltimaAprobacion);
        NroResolucion = nroResolucion;
        this.fechaResolucion = fechaResolucion;
        this.periodoEvaluado = periodoEvaluado;
    }

    //Comienzo funciones y metodos

    public int getNroResolucion() {
        return NroResolucion;
    }

    public Date getFechaResolucion() {
        return fechaResolucion;
    }

    public Date getPeriodoEvaluado() {
        return periodoEvaluado;
    }

    public void setNroResolucion(int nroResolucion) {
        NroResolucion = nroResolucion;
    }

    public void setFechaResolucion(Date fechaResolucion) {
        this.fechaResolucion = fechaResolucion;
    }

    public void setPeriodoEvaluado(Date periodoEvaluado) {
        this.periodoEvaluado = periodoEvaluado;
    }
}
