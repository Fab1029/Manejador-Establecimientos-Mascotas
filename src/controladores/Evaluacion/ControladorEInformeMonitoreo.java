package controladores.Evaluacion;

import modulos.Establecimiento.Establecimiento;
import modulos.Evaluacion.EInformeMonitoreo;
import modulos.Usuario.PropietarioEstablecimiento;
import modulos.Usuario.Usuario;
import vistas.Evaluacion.EvaluacionInformeMonitoreoGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ControladorEInformeMonitoreo implements ActionListener {
    private JFrame vistaAnterior;
    private Usuario GestorControlador;
    private EInformeMonitoreo evaluacion;
    private PropietarioEstablecimiento usuario;
    private Establecimiento<Object> establecimiento;
    private EvaluacionInformeMonitoreoGUI evaluacionInformeMonitoreoGUI;

    public ControladorEInformeMonitoreo(Usuario GestorControlador, PropietarioEstablecimiento usuario, Establecimiento<Object> establecimiento, EInformeMonitoreo evaluacion, JFrame vistaAnterior){
        this.usuario = usuario;
        this.evaluacion = evaluacion;
        this.vistaAnterior = vistaAnterior;
        this.establecimiento = establecimiento;
        this.GestorControlador = GestorControlador;

        setVentana();
        setListeners();
    }

    public void setControladorPropietarioEstablecimiento(){
        evaluacionInformeMonitoreoGUI.tipoMonitoreo.setEnabled(false);
        evaluacionInformeMonitoreoGUI.codigoProyecto.setEnabled(false);
        evaluacionInformeMonitoreoGUI.numeroResolucion.setEnabled(false);
        evaluacionInformeMonitoreoGUI.fechaUltimaAceptacion.setEnabled(false);
        evaluacionInformeMonitoreoGUI.oficioUltimaAprobacion.setEnabled(false);
    }

    public void setControladorAdministrador(){
        System.out.println(true);
        evaluacionInformeMonitoreoGUI.tipoMonitoreo.setEnabled(true);
        evaluacionInformeMonitoreoGUI.codigoProyecto.setEnabled(true);
        evaluacionInformeMonitoreoGUI.numeroResolucion.setEnabled(true);
        evaluacionInformeMonitoreoGUI.fechaUltimaAceptacion.setEnabled(true);
        evaluacionInformeMonitoreoGUI.oficioUltimaAprobacion.setEnabled(true);
    }

    //Comienzo funciones y metodos
    public void setVentana(){
        evaluacionInformeMonitoreoGUI = new EvaluacionInformeMonitoreoGUI();

        if(GestorControlador instanceof PropietarioEstablecimiento)
            setControladorPropietarioEstablecimiento();
        else
            setControladorAdministrador();

        if(evaluacion.getCodigoProyecto() != 0){
            evaluacionInformeMonitoreoGUI.tipoMonitoreo.setText(evaluacion.getTipoMonitoreo());
            evaluacionInformeMonitoreoGUI.codigoProyecto.setText("" + evaluacion.getCodigoProyecto());
            evaluacionInformeMonitoreoGUI.numeroResolucion.setText("" + evaluacion.getNroResolucion());

            if(evaluacion.isOficioUltimaAprobacion()) evaluacionInformeMonitoreoGUI.oficioUltimaAprobacion.setSelected(true);
            else evaluacionInformeMonitoreoGUI.oficioUltimaAprobacion.setSelected(false);

            evaluacionInformeMonitoreoGUI.fechaUltimaAceptacion.setDate(evaluacion.getFechaUltimaAceptacion());
        }

    }


    public void setListeners(){

        evaluacionInformeMonitoreoGUI.atrasBoton.setToolTipText("Volver");

        evaluacionInformeMonitoreoGUI.atrasBoton.addActionListener(this);
        evaluacionInformeMonitoreoGUI.aceptarBoton.addActionListener(this);
        evaluacionInformeMonitoreoGUI.cancelarBoton.addActionListener(this);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == evaluacionInformeMonitoreoGUI.atrasBoton) cerrarControlador();

        else if(e.getSource() == evaluacionInformeMonitoreoGUI.aceptarBoton) {
            if (isCamposLLenos()) {
                ingresarDatos();
            }
            else {
                // Muestra un mensaje al usuario indicando que todos los campos son obligatorios.
                JOptionPane.showMessageDialog(evaluacionInformeMonitoreoGUI, "Todos los campos son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        else if(e.getSource() == evaluacionInformeMonitoreoGUI.cancelarBoton) evaluacionInformeMonitoreoGUI.setDefaultValues();
    }

    public void ingresarDatos(){
        evaluacion = new EInformeMonitoreo(Integer.parseInt(evaluacionInformeMonitoreoGUI.codigoProyecto.getText()), evaluacionInformeMonitoreoGUI.fechaUltimaAceptacion.getDate(),
                evaluacionInformeMonitoreoGUI.oficioUltimaAprobacion.isSelected(), Integer.parseInt(evaluacionInformeMonitoreoGUI.numeroResolucion.getText()),
                evaluacionInformeMonitoreoGUI.tipoMonitoreo.getText());

        cerrarControlador();
    }

    public void cerrarControlador(){
        evaluacionInformeMonitoreoGUI.cerrarVentana();
        vistaAnterior.setVisible(true);
    }

    private boolean isCamposLLenos() {
        // Verifica si los campos requeridos están llenos
        return !evaluacionInformeMonitoreoGUI.codigoProyecto.getText().isEmpty() &&
                !evaluacionInformeMonitoreoGUI.numeroResolucion.getText().isEmpty() &&
                !evaluacionInformeMonitoreoGUI.tipoMonitoreo.getText().isEmpty();
    }

}
