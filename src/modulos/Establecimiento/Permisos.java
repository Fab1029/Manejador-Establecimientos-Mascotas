package modulos.Establecimiento;

import java.io.Serializable;

public class Permisos implements Serializable {
    private boolean usoSuelo;
    private boolean rucValido;
    private boolean pagoTarifario;
    private boolean listadoPersonal;
    private boolean solicitudRegistro;
    private boolean responsableTecnico;
    private boolean inspeccionAprobadaUGA;
    private boolean croquisEstablecimiento;
    private boolean certificadoEnergiaAtomica;


    public Permisos(){
    }


    public boolean isUsoSuelo() {
        return usoSuelo;
    }

    public void setUsoSuelo(boolean usoSuelo) {
        this.usoSuelo = usoSuelo;
    }

    public boolean isRucValido() {
        return rucValido;
    }

    public void setRucValido(boolean rucValido) {
        this.rucValido = rucValido;
    }

    public boolean isPagoTarifario() {
        return pagoTarifario;
    }

    public void setPagoTarifario(boolean pagoTarifario) {
        this.pagoTarifario = pagoTarifario;
    }

    public boolean isListadoPersonal() {
        return listadoPersonal;
    }

    public void setListadoPersonal(boolean listadoPersonal) {
        this.listadoPersonal = listadoPersonal;
    }

    public boolean isSolicitudRegistro() {
        return solicitudRegistro;
    }

    public void setSolicitudRegistro(boolean solicitudRegistro) {
        this.solicitudRegistro = solicitudRegistro;
    }

    public boolean isResponsableTecnico() {
        return responsableTecnico;
    }

    public void setResponsableTecnico(boolean responsableTecnico) {
        this.responsableTecnico = responsableTecnico;
    }

    public boolean isInspeccionAprobadaUGA() {
        return inspeccionAprobadaUGA;
    }

    public void setInspeccionAprobadaUGA(boolean inspeccionAprobadaUGA) {
        this.inspeccionAprobadaUGA = inspeccionAprobadaUGA;
    }

    public boolean isCroquisEstablecimiento() {
        return croquisEstablecimiento;
    }

    public void setCroquisEstablecimiento(boolean croquisEstablecimiento) {
        this.croquisEstablecimiento = croquisEstablecimiento;
    }

    public boolean isCertificadoEnergiaAtomica() {
        return certificadoEnergiaAtomica;
    }

    public void setCertificadoEnergiaAtomica(boolean certificadoEnergiaAtomica) {
        this.certificadoEnergiaAtomica = certificadoEnergiaAtomica;
    }

    public boolean isTodosPermisos(){
        return usoSuelo && rucValido && pagoTarifario && listadoPersonal && solicitudRegistro && responsableTecnico &&
            inspeccionAprobadaUGA && croquisEstablecimiento && certificadoEnergiaAtomica;
    }
}
