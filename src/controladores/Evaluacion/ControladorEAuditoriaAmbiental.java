package controladores.Evaluacion;
import modulos.Establecimiento.Establecimiento;
import modulos.Evaluacion.EAuditoriaAmbiental;
import modulos.Usuario.PropietarioEstablecimiento;
import modulos.Usuario.Usuario;
import vistas.Evaluacion.EvaluacionAuditoriaAmbientalGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControladorEAuditoriaAmbiental implements ActionListener {

    private JFrame vistaAnterior;
    private Usuario GestorControlador;
    private EAuditoriaAmbiental evaluacion;
    private PropietarioEstablecimiento usuario;
    private Establecimiento<Object> establecimiento;
    private EvaluacionAuditoriaAmbientalGUI evaluacionAuditoriaAmbientalGUI;

    public ControladorEAuditoriaAmbiental(Usuario GestorControlador, PropietarioEstablecimiento usuario, Establecimiento<Object> establecimiento, EAuditoriaAmbiental evaluacion, JFrame vistaAnterior){
        this.usuario = usuario;
        this.evaluacion = evaluacion;
        this.vistaAnterior = vistaAnterior;
        this.establecimiento = establecimiento;
        this.GestorControlador = GestorControlador;

        setVentana();
        setListeners();
    }

    //Comienzo metodos y funciones
    public void setVentana(){
        evaluacionAuditoriaAmbientalGUI = new EvaluacionAuditoriaAmbientalGUI();

        if(GestorControlador instanceof PropietarioEstablecimiento)
            setControladorPropietarioEstablecimiento();
        else
            setControladorAdministrador();

        if(evaluacion.getCodigoProyecto() != 0){
            //Set texts
            setTexts();
            setChecks();
            //Set dates
            setDates();
        }

    }

    public void setControladorPropietarioEstablecimiento(){
        evaluacionAuditoriaAmbientalGUI.codigoProyecto.setEnabled(false);
        evaluacionAuditoriaAmbientalGUI.periodoAuditado.setEnabled(false);
        evaluacionAuditoriaAmbientalGUI.fechaAprobacionTDR.setEnabled(false);
        evaluacionAuditoriaAmbientalGUI.oficioAprobacionTDR.setEnabled(false);
        evaluacionAuditoriaAmbientalGUI.fechaEmisionLicencia.setEnabled(false);
        evaluacionAuditoriaAmbientalGUI.numLicenciaAmbiental.setEnabled(false);
        evaluacionAuditoriaAmbientalGUI.fechaUltimaAceptacion.setEnabled(false);
        evaluacionAuditoriaAmbientalGUI.oficioUltimaAprobacion.setEnabled(false);
        evaluacionAuditoriaAmbientalGUI.nombreConsultorUltimaAA.setEnabled(false);
    }


    public void setControladorAdministrador(){
        evaluacionAuditoriaAmbientalGUI.codigoProyecto.setEnabled(true);
        evaluacionAuditoriaAmbientalGUI.periodoAuditado.setEnabled(true);
        evaluacionAuditoriaAmbientalGUI.fechaAprobacionTDR.setEnabled(true);
        evaluacionAuditoriaAmbientalGUI.oficioAprobacionTDR.setEnabled(true);
        evaluacionAuditoriaAmbientalGUI.fechaEmisionLicencia.setEnabled(true);
        evaluacionAuditoriaAmbientalGUI.numLicenciaAmbiental.setEnabled(true);
        evaluacionAuditoriaAmbientalGUI.fechaUltimaAceptacion.setEnabled(true);
        evaluacionAuditoriaAmbientalGUI.oficioUltimaAprobacion.setEnabled(true);
        evaluacionAuditoriaAmbientalGUI.nombreConsultorUltimaAA.setEnabled(true);
    }

    public void setDates(){
        evaluacionAuditoriaAmbientalGUI.fechaEmisionPoliza.setDate(evaluacion.getFechaEmisionPoliza());
        evaluacionAuditoriaAmbientalGUI.fechaAprobacionTDR.setDate(evaluacion.getFechaAprobacionTDR());
        evaluacionAuditoriaAmbientalGUI.fechaEmisionLicencia.setDate(evaluacion.getFechaEmisionLicencia());
        evaluacionAuditoriaAmbientalGUI.fechaUltimaAceptacion.setDate(evaluacion.getFechaUltimaAceptacion());
        evaluacionAuditoriaAmbientalGUI.fechaVencimientoPoliza.setDate(evaluacion.getFechaVencimientoPoliza());
        evaluacionAuditoriaAmbientalGUI.periodoAuditado.setDate(evaluacion.getPeriodoAuditado());
    }

    public void setChecks(){
        if(evaluacion.isOficioUltimaAprobacion()) evaluacionAuditoriaAmbientalGUI.oficioUltimaAprobacion.setSelected(true);
        else evaluacionAuditoriaAmbientalGUI.oficioUltimaAprobacion.setSelected(false);

        if(evaluacion.isOficioAprobacionTDR()) evaluacionAuditoriaAmbientalGUI.oficioAprobacionTDR.setSelected(true);
        else evaluacionAuditoriaAmbientalGUI.oficioAprobacionTDR.setSelected(false);
    }


    public void setTexts(){
        evaluacionAuditoriaAmbientalGUI.codigoProyecto.setEditable(false);
        evaluacionAuditoriaAmbientalGUI.NroPoliza.setText("" + evaluacion.getNroPoliza());
        evaluacionAuditoriaAmbientalGUI.montoPoliza.setText("" + evaluacion.getMontoPoliza());
        evaluacionAuditoriaAmbientalGUI.codigoProyecto.setText("" + evaluacion.getCodigoProyecto());
        evaluacionAuditoriaAmbientalGUI.numLicenciaAmbiental.setText("" + evaluacion.getNumLicenciaAmbiental());
        evaluacionAuditoriaAmbientalGUI.nombreConsultorUltimaAA.setText(evaluacion.getNombreConsultorUltimaAA());
    }

    public void setListeners(){

        evaluacionAuditoriaAmbientalGUI.atrasBoton.setToolTipText("Volver");

        evaluacionAuditoriaAmbientalGUI.atrasBoton.addActionListener(this);
        evaluacionAuditoriaAmbientalGUI.aceptarBoton.addActionListener(this);
        evaluacionAuditoriaAmbientalGUI.cancelarButon.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == evaluacionAuditoriaAmbientalGUI.atrasBoton) cerrarControlador();
        else if(e.getSource() == evaluacionAuditoriaAmbientalGUI.aceptarBoton) {

            if (isCamposLLenos()) {
                ingresarDatos();
            }
            else {
                // Muestra un mensaje al usuario indicando que todos los campos son obligatorios.
                JOptionPane.showMessageDialog(evaluacionAuditoriaAmbientalGUI, "Todos los campos son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        else if(e.getSource() == evaluacionAuditoriaAmbientalGUI.cancelarButon) evaluacionAuditoriaAmbientalGUI.setDefaultValues();


    }

    public void ingresarDatos(){
        evaluacion = new EAuditoriaAmbiental(Integer.parseInt(evaluacionAuditoriaAmbientalGUI.codigoProyecto.getText()),
                evaluacionAuditoriaAmbientalGUI.fechaUltimaAceptacion.getDate(), evaluacionAuditoriaAmbientalGUI.oficioUltimaAprobacion.isSelected(),
                Integer.parseInt(evaluacionAuditoriaAmbientalGUI.NroPoliza.getText()), Float.parseFloat(evaluacionAuditoriaAmbientalGUI.montoPoliza.getText()),
                evaluacionAuditoriaAmbientalGUI.periodoAuditado.getDate(), evaluacionAuditoriaAmbientalGUI.fechaEmisionPoliza.getDate(),
                evaluacionAuditoriaAmbientalGUI.fechaAprobacionTDR.getDate(), Integer.parseInt(evaluacionAuditoriaAmbientalGUI.numLicenciaAmbiental.getText()),
                evaluacionAuditoriaAmbientalGUI.fechaEmisionLicencia.getDate(), evaluacionAuditoriaAmbientalGUI.fechaVencimientoPoliza.getDate(),
                evaluacionAuditoriaAmbientalGUI.oficioAprobacionTDR.isSelected(), evaluacionAuditoriaAmbientalGUI.nombreConsultorUltimaAA.getText());

        usuario.getEstablecimiento(establecimiento.getRuc()).setEvaluacion(evaluacion);

        cerrarControlador();
    }

    public void cerrarControlador(){
        evaluacionAuditoriaAmbientalGUI.cerrarVentana();
        vistaAnterior.setVisible(true);

    }

    private boolean isCamposLLenos(){
        if(GestorControlador instanceof PropietarioEstablecimiento){
            //Verifica si los campos estan llenos
            return !evaluacionAuditoriaAmbientalGUI.codigoProyecto.getText().isEmpty();
//                !evaluacionAuditoriaAmbientalGUI.nombreConsultorUltimaAA.getText().isEmpty() &&
//                !evaluacionAuditoriaAmbientalGUI.numLicenciaAmbiental.getText().isEmpty() &&
//                !evaluacionAuditoriaAmbientalGUI.NroPoliza.getText().isEmpty() &&
//                !evaluacionAuditoriaAmbientalGUI.montoPoliza.getText().isEmpty();
        }
        else{
            //Verifica si los campos estan llenos
            return
                evaluacionAuditoriaAmbientalGUI.NroPoliza.getText().isEmpty() &&
                evaluacionAuditoriaAmbientalGUI.montoPoliza.getText().isEmpty();
        }

    }

}
