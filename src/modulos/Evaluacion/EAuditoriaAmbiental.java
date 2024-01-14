package modulos.Evaluacion;

import java.io.Serializable;
import java.util.Date;

public class EAuditoriaAmbiental extends Evaluacion implements Serializable {
    private int NroPoliza;
    private float montoPoliza;
    private Date periodoAuditado;
    private Date fechaEmisionPoliza;
    private Date fechaAprobacionTDR;
    private int numLicenciaAmbiental;
    private Date fechaEmisionLicencia;
    private Date fechaVencimientoPoliza;
    private boolean oficioAprobacionTDR;
    private String nombreConsultorUltimaAA;

    public EAuditoriaAmbiental() {
        super();
    }

    public EAuditoriaAmbiental(int codigoProyecto, Date fechaUltimaAceptacion, boolean oficioUltimaAprobacion, int nroPoliza, float montoPoliza, Date periodoAuditado, Date fechaEmisionPoliza, Date fechaAprobacionTDR, int numLicenciaAmbiental, Date fechaEmisionLicencia, Date fechaVencimientoPoliza, boolean oficioAprobacionTDR, String nombreConsultorUltimaAA) {
        super(codigoProyecto, fechaUltimaAceptacion, oficioUltimaAprobacion);
        NroPoliza = nroPoliza;
        this.montoPoliza = montoPoliza;
        this.periodoAuditado = periodoAuditado;
        this.fechaEmisionPoliza = fechaEmisionPoliza;
        this.fechaAprobacionTDR = fechaAprobacionTDR;
        this.numLicenciaAmbiental = numLicenciaAmbiental;
        this.fechaEmisionLicencia = fechaEmisionLicencia;
        this.fechaVencimientoPoliza = fechaVencimientoPoliza;
        this.oficioAprobacionTDR = oficioAprobacionTDR;
        this.nombreConsultorUltimaAA = nombreConsultorUltimaAA;
    }

    public int getNroPoliza() {
        return NroPoliza;
    }
    public float getMontoPoliza() {
        return montoPoliza;
    }

    public void setNroPoliza(int nroPoliza) {
        NroPoliza = nroPoliza;
    }

    public Date getFechaEmisionPoliza() {
        return fechaEmisionPoliza;
    }

    public Date getFechaAprobacionTDR() {
        return fechaAprobacionTDR;
    }

    public int getNumLicenciaAmbiental() {
        return numLicenciaAmbiental;
    }

    public Date getFechaEmisionLicencia() {
        return fechaEmisionLicencia;
    }

    public boolean isOficioAprobacionTDR() {
        return oficioAprobacionTDR;
    }

    public void setFechaVencimientoPoliza(Date fechaVencimientoPoliza) {
        this.fechaVencimientoPoliza = fechaVencimientoPoliza;
    }

    public Date getFechaVencimientoPoliza() {
        return fechaVencimientoPoliza;
    }

    public Date getPeriodoAuditado(){return periodoAuditado;}
    public void setNombreConsultorUltimaAA(String nombreConsultorUltimaAA) {
        this.nombreConsultorUltimaAA = nombreConsultorUltimaAA;
    }

    public String getNombreConsultorUltimaAA() {
        return nombreConsultorUltimaAA;
    }

    public void setMontoPoliza(float montoPoliza) {
        this.montoPoliza = montoPoliza;
    }

    public void setPeriodoAuditado(Date periodoAuditado) {
        this.periodoAuditado = periodoAuditado;
    }

    public void setFechaAprobacionTDR(Date fechaAprobacionTDR) {
        this.fechaAprobacionTDR = fechaAprobacionTDR;
    }

    public void setFechaEmisionPoliza(Date fechaEmisionPoliza) {
        this.fechaEmisionPoliza = fechaEmisionPoliza;
    }

    public void setNumLicenciaAmbiental(int numLicenciaAmbiental) {
        this.numLicenciaAmbiental = numLicenciaAmbiental;
    }

    public void setOficioAprobacionTDR(boolean oficioAprobacionTDR) {
        this.oficioAprobacionTDR = oficioAprobacionTDR;
    }

    public void setFechaEmisionLicencia(Date fechaEmisionLicencia) {
        this.fechaEmisionLicencia = fechaEmisionLicencia;
    }
}
