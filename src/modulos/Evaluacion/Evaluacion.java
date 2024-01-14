package modulos.Evaluacion;

import modulos.ObserverPattern.Observer;
import modulos.ObserverPattern.Subject;

import java.io.Serializable;
import java.util.Date;

public class Evaluacion implements Serializable{
    private int codigoProyecto;
    private Date fechaUltimaAceptacion;
    private boolean oficioUltimaAprobacion;
    private transient Observer controladorUsuario;

    public Evaluacion() {
        codigoProyecto = 0;
    }

    public Evaluacion(int codigoProyecto, Date fechaUltimaAceptacion, boolean oficioUltimaAprobacion) {
        this.codigoProyecto = codigoProyecto;
        this.fechaUltimaAceptacion = fechaUltimaAceptacion;
        this.oficioUltimaAprobacion = oficioUltimaAprobacion;
    }

    public int getCodigoProyecto() {
        return codigoProyecto;
    }

    public void setFechaUltimaAceptacion(Date fechaUltimaAceptacion) {
        this.fechaUltimaAceptacion = fechaUltimaAceptacion;
    }

    public Date getFechaUltimaAceptacion() {
        return fechaUltimaAceptacion;
    }

    public void setOficioUltimaAprobacion(boolean oficioUltimaAprobacion) {
        this.oficioUltimaAprobacion = oficioUltimaAprobacion;
    }

    public boolean isOficioUltimaAprobacion() {
        return oficioUltimaAprobacion;
    }

    public void setCodigoProyecto(int codigoProyecto) {
        this.codigoProyecto = codigoProyecto;
    }

}
