package controladores.Establecimiento;

import modulos.Establecimiento.Establecimiento;
import modulos.Establecimiento.DocumentoPermisos;
import modulos.Usuario.PropietarioEstablecimiento;
import modulos.Usuario.Usuario;
import vistas.Permisos.PermisosGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControladorPermisos implements ActionListener {
    private JFrame vistaAnterior;
    private PermisosGUI permisosGUI;
    private Establecimiento establecimiento;
    private DocumentoPermisos permisosEstablecimiento;
    private PropietarioEstablecimiento propietarioEstablecimiento;

    public ControladorPermisos(Usuario usuario, PropietarioEstablecimiento propietarioEstablecimiento, Establecimiento establecimiento, JFrame vistaAnterior) {
        permisosGUI = new PermisosGUI();
        permisosEstablecimiento = new DocumentoPermisos();

        this.vistaAnterior = vistaAnterior;
        this.establecimiento = establecimiento;
        this.propietarioEstablecimiento = propietarioEstablecimiento;

        setControlador(usuario);
        setListeners();
    }

    public void setControlador(Usuario usuario){
        permisosGUI.usoSuelo.setSelected(establecimiento.getPermisos().isUsoSuelo());
        permisosGUI.rucValido.setSelected(establecimiento.getPermisos().isRucValido());
        permisosGUI.pagoTarifario.setSelected(establecimiento.getPermisos().isPagoTarifario());
        permisosGUI.listadoPersonal.setSelected(establecimiento.getPermisos().isListadoPersonal());
        permisosGUI.solicitudRegistro.setSelected(establecimiento.getPermisos().isSolicitudRegistro());
        permisosGUI.responsableTecnico.setSelected(establecimiento.getPermisos().isResponsableTecnico());
        permisosGUI.inspeccionAprobadaUGA.setSelected(establecimiento.getPermisos().isInspeccionAprobadaUGA());
        permisosGUI.croquisEstablecimiento.setSelected(establecimiento.getPermisos().isCroquisEstablecimiento());
        permisosGUI.certificadoEnergiaAtomica.setSelected(establecimiento.getPermisos().isCertificadoEnergiaAtomica());

        if(usuario instanceof PropietarioEstablecimiento){
            permisosGUI.usoSuelo.setEnabled(false);
            permisosGUI.rucValido.setEnabled(false);
            permisosGUI.pagoTarifario.setEnabled(false);
            permisosGUI.listadoPersonal.setEnabled(false);
            permisosGUI.solicitudRegistro.setEnabled(false);
            permisosGUI.responsableTecnico.setEnabled(false);
            permisosGUI.inspeccionAprobadaUGA.setEnabled(false);
            permisosGUI.croquisEstablecimiento.setEnabled(false);
            permisosGUI.certificadoEnergiaAtomica.setEnabled(false);

            permisosGUI.btnGenerarReporte.setVisible(false);

        }

    }

    public void setListeners() {
        permisosGUI.btnRegresar.setToolTipText("Volver");

        permisosGUI.btnRegresar.addActionListener(this);
        permisosGUI.btnGenerarReporte.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == permisosGUI.btnGenerarReporte)
            verificarAutorizacion();

        else if (e.getSource() == permisosGUI.btnRegresar)
            cerrarControlador();

    }

    // Método para verificar la autorización
    private void verificarAutorizacion() {
        boolean todosSeleccionados = permisosGUI.usoSuelo.isSelected()
                && permisosGUI.solicitudRegistro.isSelected()
                && permisosGUI.pagoTarifario.isSelected()
                && permisosGUI.inspeccionAprobadaUGA.isSelected()
                && permisosGUI.croquisEstablecimiento.isSelected()
                && permisosGUI.responsableTecnico.isSelected()
                && permisosGUI.certificadoEnergiaAtomica.isSelected()
                && permisosGUI.listadoPersonal.isSelected()
                && permisosGUI.rucValido.isSelected();

        if (todosSeleccionados)
            permisosEstablecimiento.generarDocumentoPDF(propietarioEstablecimiento, establecimiento, true);
        else
            permisosEstablecimiento.generarDocumentoPDF(propietarioEstablecimiento, establecimiento, false);

        cerrarControlador();
    }

    public void cerrarControlador(){
        guardarInformacion();
        permisosGUI.cerrarVentana();
        vistaAnterior.setVisible(true);
    }


    public void guardarInformacion(){

        establecimiento.getPermisos().setUsoSuelo(permisosGUI.usoSuelo.isSelected());
        establecimiento.getPermisos().setRucValido(permisosGUI.rucValido.isSelected());
        establecimiento.getPermisos().setPagoTarifario(permisosGUI.pagoTarifario.isSelected());
        establecimiento.getPermisos().setListadoPersonal(permisosGUI.listadoPersonal.isSelected());
        establecimiento.getPermisos().setSolicitudRegistro(permisosGUI.solicitudRegistro.isSelected());
        establecimiento.getPermisos().setResponsableTecnico(permisosGUI.responsableTecnico.isSelected());
        establecimiento.getPermisos().setInspeccionAprobadaUGA(permisosGUI.inspeccionAprobadaUGA.isSelected());
        establecimiento.getPermisos().setCroquisEstablecimiento(permisosGUI.croquisEstablecimiento.isSelected());
        establecimiento.getPermisos().setCertificadoEnergiaAtomica(permisosGUI.certificadoEnergiaAtomica.isSelected());

    }

}

