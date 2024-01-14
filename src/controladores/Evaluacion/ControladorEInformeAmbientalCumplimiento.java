package controladores.Evaluacion;

import modulos.Establecimiento.Establecimiento;
import modulos.Evaluacion.EInformeAmbientalCumplimiento;
import modulos.Usuario.PropietarioEstablecimiento;
import modulos.Usuario.Usuario;
import vistas.Evaluacion.EvaluacionInformeAmbientalCumplimientoGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ControladorEInformeAmbientalCumplimiento implements ActionListener {
    private JFrame vistaAnterior;
    private Usuario GestorControlador;
    private PropietarioEstablecimiento usuario;
    private Establecimiento<Object> establecimiento;
    private EInformeAmbientalCumplimiento evaluacion;

    private EvaluacionInformeAmbientalCumplimientoGUI evaluacionInformeAmbientalCumplimientoGUI;

    public ControladorEInformeAmbientalCumplimiento(Usuario GestorControlador, PropietarioEstablecimiento usuario, Establecimiento<Object> establecimiento, EInformeAmbientalCumplimiento evaluacion, JFrame vistaAnterior){
        this.usuario = usuario;
        this.evaluacion = evaluacion;
        this.vistaAnterior = vistaAnterior;
        this.establecimiento = establecimiento;
        this.GestorControlador = GestorControlador;

        setVentana();
        setListeners();

    }

    public void setControladorPropietarioEstablecimiento(){
        System.out.println(GestorControlador.getCedula());
        evaluacionInformeAmbientalCumplimientoGUI.codigoProyecto.setEnabled(false);
        evaluacionInformeAmbientalCumplimientoGUI.fechaResolucion.setEnabled(false);
        evaluacionInformeAmbientalCumplimientoGUI.periodoEvaluado.setEnabled(false);
        evaluacionInformeAmbientalCumplimientoGUI.numeroResolucion.setEnabled(false);
        evaluacionInformeAmbientalCumplimientoGUI.fechaUltimaAceptacion.setEnabled(false);
        evaluacionInformeAmbientalCumplimientoGUI.oficioUltimaAprobacion.setEnabled(false);
    }

    public void setControladorAdministrador(){
        evaluacionInformeAmbientalCumplimientoGUI.codigoProyecto.setEnabled(true);
        evaluacionInformeAmbientalCumplimientoGUI.fechaResolucion.setEnabled(true);
        evaluacionInformeAmbientalCumplimientoGUI.periodoEvaluado.setEnabled(true);
        evaluacionInformeAmbientalCumplimientoGUI.numeroResolucion.setEnabled(true);
        evaluacionInformeAmbientalCumplimientoGUI.fechaUltimaAceptacion.setEnabled(true);
        evaluacionInformeAmbientalCumplimientoGUI.oficioUltimaAprobacion.setEnabled(true);
    }
    //Comienzo funciones y metodos
    public void setVentana(){
        evaluacionInformeAmbientalCumplimientoGUI = new EvaluacionInformeAmbientalCumplimientoGUI();

        if(GestorControlador instanceof PropietarioEstablecimiento)
            setControladorPropietarioEstablecimiento();
        else
            setControladorAdministrador();

        if(evaluacion.getCodigoProyecto() != 0){
            evaluacionInformeAmbientalCumplimientoGUI.codigoProyecto.setText("" + evaluacion.getCodigoProyecto());
            evaluacionInformeAmbientalCumplimientoGUI.numeroResolucion.setText("" + evaluacion.getNroResolucion());

            if(evaluacion.isOficioUltimaAprobacion()) evaluacionInformeAmbientalCumplimientoGUI.oficioUltimaAprobacion.setSelected(true);
            else evaluacionInformeAmbientalCumplimientoGUI.oficioUltimaAprobacion.setSelected(false);

            evaluacionInformeAmbientalCumplimientoGUI.periodoEvaluado.setDate(evaluacion.getPeriodoEvaluado());
            evaluacionInformeAmbientalCumplimientoGUI.fechaResolucion.setDate(evaluacion.getFechaResolucion());
            evaluacionInformeAmbientalCumplimientoGUI.fechaUltimaAceptacion.setDate(evaluacion.getFechaUltimaAceptacion());
        }

    }

    public void setListeners(){

        evaluacionInformeAmbientalCumplimientoGUI.atrasBoton.setToolTipText("Volver");

        evaluacionInformeAmbientalCumplimientoGUI.atrasBoton.addActionListener(this);
        evaluacionInformeAmbientalCumplimientoGUI.aceptarBoton.addActionListener(this);
        evaluacionInformeAmbientalCumplimientoGUI.cancelarBoton.addActionListener(this);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == evaluacionInformeAmbientalCumplimientoGUI.atrasBoton) cerrarControlador();

        else if(e.getSource() == evaluacionInformeAmbientalCumplimientoGUI.aceptarBoton) {
            if (isCamposLLenos()) {
                ingresarDatos();
            } else {
                // Muestra un mensaje al usuario indicando que todos los campos son obligatorios.
                JOptionPane.showMessageDialog(evaluacionInformeAmbientalCumplimientoGUI, "Todos los campos son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        else if(e.getSource() == evaluacionInformeAmbientalCumplimientoGUI.cancelarBoton) evaluacionInformeAmbientalCumplimientoGUI.setDefaultValues();
    }

    public void ingresarDatos(){
        evaluacion = new EInformeAmbientalCumplimiento(Integer.parseInt(evaluacionInformeAmbientalCumplimientoGUI.codigoProyecto.getText()),
                evaluacionInformeAmbientalCumplimientoGUI.fechaUltimaAceptacion.getDate(), evaluacionInformeAmbientalCumplimientoGUI.oficioUltimaAprobacion.isSelected(),
                Integer.parseInt(evaluacionInformeAmbientalCumplimientoGUI.numeroResolucion.getText()), evaluacionInformeAmbientalCumplimientoGUI.fechaResolucion.getDate(),
                evaluacionInformeAmbientalCumplimientoGUI.periodoEvaluado.getDate());

        usuario.getEstablecimiento(establecimiento.getRuc()).setEvaluacion(evaluacion);

        cerrarControlador();
    }

    public void cerrarControlador(){
        evaluacionInformeAmbientalCumplimientoGUI.cerrarVentana();
        vistaAnterior.setVisible(true);
    }

    private boolean isCamposLLenos() {
        // Verifica si los campos requeridos están llenos
        return !evaluacionInformeAmbientalCumplimientoGUI.codigoProyecto.getText().isEmpty() &&
                !evaluacionInformeAmbientalCumplimientoGUI.numeroResolucion.getText().isEmpty();
    }

}
