package controladores.Establecimiento;

import controladores.Evaluacion.ControladorEInformeMonitoreo;
import modulos.Establecimiento.Permisos;
import modulos.Evaluacion.EAuditoriaAmbiental;
import modulos.Establecimiento.Establecimiento;
import modulos.Evaluacion.EInformeMonitoreo;
import modulos.Evaluacion.Evaluacion;
import modulos.Usuario.GestionUsuarios;
import modulos.Usuario.PropietarioEstablecimiento;
import modulos.Usuario.Usuario;
import vistas.Establecimiento.EstablecimientoGUI;
import modulos.Evaluacion.EInformeAmbientalCumplimiento;
import controladores.Evaluacion.ControladorEAuditoriaAmbiental;
import controladores.Evaluacion.ControladorEInformeAmbientalCumplimiento;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.Serializable;


public class ControladorEstablecimiento implements ActionListener, KeyListener {

    private JFrame vistaAnterior;
    private Establecimiento establecimiento;
    private PropietarioEstablecimiento usuario;
    private EstablecimientoGUI establecimientoGUI;

    public ControladorEstablecimiento(PropietarioEstablecimiento usuario, Establecimiento<Object> establecimiento, JFrame vistaAnterior){
        this.usuario = usuario;
        this.vistaAnterior = vistaAnterior;
        this.establecimiento = establecimiento;

        setVentana();
        setListeners();
    }

    //Comienzo funciones y metodos
    public void setVentana(){
        establecimientoGUI = new EstablecimientoGUI();

        //El establecmiento ya existe solo se da la modificacion
        if(establecimiento != null){
            establecimientoGUI.ruc.setEditable(false);
            establecimientoGUI.ruc.setText(establecimiento.getRuc());
            establecimientoGUI.correo.setText(establecimiento.getCorreo());
            establecimientoGUI.correo.setText(establecimiento.getCoordenadas());
            establecimientoGUI.telefono.setText("" + establecimiento.getTelefono());
            establecimientoGUI.coordenadas.setText(establecimiento.getCoordenadas());
            establecimientoGUI.nombreEstablecimiento.setText(establecimiento.getNombreEstablecimiento());

            for(int i = 0; i < establecimientoGUI.tipoEstablecimiento.getItemCount(); i++){
                if(establecimiento.getTipoEstablecimiento().equals(establecimientoGUI.tipoEstablecimiento.getItemAt(i).toString())){
                    establecimientoGUI.tipoEstablecimiento.setSelectedIndex(i);
                    establecimientoGUI.otroEstablecimiento.setSelected(false);
                    establecimientoGUI.tipoEstablecimientoIngreso.setEditable(false);
                    break;
                }
            }


            if(establecimiento.getEvaluacion() instanceof EInformeMonitoreo) establecimientoGUI.evaluacionInformeMonitoreo.setSelected(true);

            else if(establecimiento.getEvaluacion() instanceof EAuditoriaAmbiental) establecimientoGUI.evaluacionAuditoriaAmbiental.setSelected(true);

            else establecimientoGUI.evaluacionInformeAmbientalCumplimiento.setSelected(true);

            if(establecimientoGUI.otroEstablecimiento.isSelected()) establecimientoGUI.tipoEstablecimientoIngreso.setText(establecimiento.getTipoEstablecimiento());

        }

   }


    public void setListeners(){

        establecimientoGUI.atrasBoton.setToolTipText("Volver");

        establecimientoGUI.atrasBoton.addActionListener(this);
        establecimientoGUI.aceptarBoton.addActionListener(this);
        establecimientoGUI.cancelarBoton.addActionListener(this);
        establecimientoGUI.otroEstablecimiento.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == establecimientoGUI.atrasBoton) cerrarControlador();

        else if(e.getSource() == establecimientoGUI.aceptarBoton) {
            if (isCamposLLenos() && isIngresoValido())
                ingresarDatos();
            else
                System.out.println("No valido");

        }

        else if(e.getSource() == establecimientoGUI.cancelarBoton) establecimientoGUI.setDefaultValues();

        else if(e.getSource() == establecimientoGUI.otroEstablecimiento){

            if(establecimientoGUI.otroEstablecimiento.isSelected()) establecimientoGUI.setTipoEstablecimientoIngreso(true);

            else establecimientoGUI.setTipoEstablecimientoIngreso(false);
        }


    }

    public void nuevoEstablecimiento(){

        if(establecimientoGUI.otroEstablecimiento.isSelected()) establecimiento = new Establecimiento<>(establecimientoGUI.ruc.getText(), Integer.parseInt(establecimientoGUI.telefono.getText()), establecimientoGUI.correo.getText(), establecimientoGUI.coordenadas.getText(), establecimientoGUI.tipoEstablecimientoIngreso.getText(), establecimientoGUI.nombreEstablecimiento.getText(), new Evaluacion());

        else establecimiento = new Establecimiento<>(establecimientoGUI.ruc.getText(), Integer.parseInt(establecimientoGUI.telefono.getText()), establecimientoGUI.correo.getText(), establecimientoGUI.coordenadas.getText(),  (String) establecimientoGUI.tipoEstablecimiento.getSelectedItem(), establecimientoGUI.nombreEstablecimiento.getText(), new Evaluacion());

        if(establecimientoGUI.evaluacionInformeMonitoreo.isSelected()) establecimiento.setEvaluacion(new EInformeMonitoreo());

        else if(establecimientoGUI.evaluacionAuditoriaAmbiental.isSelected()) establecimiento.setEvaluacion(new EAuditoriaAmbiental());

        else if(establecimientoGUI.evaluacionInformeAmbientalCumplimiento.isSelected()) establecimiento.setEvaluacion(new EInformeAmbientalCumplimiento());

        establecimiento.setPermisos(new Permisos());

        usuario.addEstablecimiento(establecimiento.getRuc(), establecimiento);
    }

    public void modificarEstablecimiento(){
        String tipoEstablecimiento;

        if(establecimientoGUI.otroEstablecimiento.isSelected())
            tipoEstablecimiento = (String) establecimientoGUI.tipoEstablecimiento.getSelectedItem();

        else
            tipoEstablecimiento = establecimientoGUI.otroEstablecimiento.getText();


        establecimiento.setTipoEstablecimiento(tipoEstablecimiento);
        establecimiento.setCorreo(establecimientoGUI.correo.getText());
        establecimiento.setCoordenadas(establecimientoGUI.coordenadas.getText());
        establecimiento.setTelefono(Integer.parseInt(establecimientoGUI.telefono.getText()));
        establecimiento.setNombreEstablecimiento(establecimientoGUI.nombreEstablecimiento.getText());

        if(establecimientoGUI.evaluacionInformeMonitoreo.isSelected() && !(establecimiento.getEvaluacion() instanceof EInformeMonitoreo))
            establecimiento.setEvaluacion(new EInformeMonitoreo());

        else if(establecimientoGUI.evaluacionAuditoriaAmbiental.isSelected() && !(establecimiento.getEvaluacion() instanceof EAuditoriaAmbiental))
            establecimiento.setEvaluacion(new EAuditoriaAmbiental());

        else if(establecimientoGUI.evaluacionInformeAmbientalCumplimiento.isSelected() && !(establecimiento.getEvaluacion() instanceof EInformeAmbientalCumplimiento))
            establecimiento.setEvaluacion(new EInformeAmbientalCumplimiento());

    }



    public void ingresarDatos(){
        establecimientoGUI.setPanelPrincipalVisible(false);

        if(establecimiento != null)
            modificarEstablecimiento();
        else
            nuevoEstablecimiento();

        cerrarControlador();
    }


    public void cerrarControlador(){
        establecimientoGUI.cerrarVentana();
        GestionUsuarios.getInstance().notificarObservadores();
        vistaAnterior.setVisible(true);
    }


    public boolean isIngresoValido() {
        // Verifica si los campos requeridos están llenos y son válidos
        if (!new Establecimiento<>().isRucValid(establecimientoGUI.ruc.getText())){
            JOptionPane.showMessageDialog(null, "RUC no valido","ERROR",JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!new Establecimiento<>().isTelefonoValid(establecimientoGUI.telefono.getText())){
            JOptionPane.showMessageDialog(null, "Telefono no valido","ERROR",JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!new Establecimiento<>().isCorreoValid(establecimientoGUI.correo.getText())){
            JOptionPane.showMessageDialog(null, "Correo no valido","ERROR",JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    public boolean isCamposLLenos(){
        if(!establecimientoGUI.nombreEstablecimiento.getText().isEmpty() &&
                !establecimientoGUI.ruc.getText().isEmpty() &&
                !establecimientoGUI.correo.getText().isEmpty() &&
                !establecimientoGUI.telefono.getText().isEmpty() &&
                !establecimientoGUI.coordenadas.getText().isEmpty() &&
                establecimientoGUI.tiposEvaluacion.getSelection() == null &&
                (establecimientoGUI.otroEstablecimiento.isSelected() || establecimientoGUI.tipoEstablecimiento.getSelectedIndex() != -1)){

            JOptionPane.showMessageDialog(establecimientoGUI, "Campos sin llenar", "Error", JOptionPane.ERROR_MESSAGE);
            return false;

        }

        return true;

    }


    @Override
    public void keyTyped(KeyEvent e) {
        char keyPressed = e.getKeyChar();

        if (e.getSource() == establecimientoGUI.ruc) {
            if (!Character.isDigit(keyPressed) && establecimientoGUI.ruc.getText().length() > 13) {
                e.consume();
            }
        }
        else if(e.getSource() == establecimientoGUI.telefono){
            if (!Character.isDigit(keyPressed)) {
                e.consume();
            }
        }

    }
    @Override
    public void keyPressed(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}

}
