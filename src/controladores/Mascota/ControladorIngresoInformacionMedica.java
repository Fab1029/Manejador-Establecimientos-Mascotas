package controladores.Mascota;

import modulos.Mascota.InformacionMedica;
import modulos.Mascota.Mascota;
import vistas.Mascota.IngresoInformacionMedicaGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ControladorIngresoInformacionMedica implements ActionListener {
    private Mascota mascota;
    private JFrame vistaAnterior;
    private String tipoInformacion;
    private InformacionMedica informacionMedica;
    private  IngresoInformacionMedicaGUI informacionMedicaGUI;


    public ControladorIngresoInformacionMedica(Mascota mascota, InformacionMedica informacionMedica, String tipoInformacion, JFrame vistaAnterior){
        this.mascota = mascota;
        this.vistaAnterior = vistaAnterior;
        this.tipoInformacion = tipoInformacion;
        this.informacionMedica = informacionMedica;

        setVentana();
        setListeners();
    }

    //Comienzo funciones y metodos
    public void setVentana(){
        informacionMedicaGUI = new IngresoInformacionMedicaGUI();

        informacionMedicaGUI.fechaInformacionMedica.setDate(informacionMedica.getFecha());
        informacionMedicaGUI.descripcionInformacionMedica.setText(informacionMedica.getDescripcion());

    }

    public void setListeners(){

        informacionMedicaGUI.atrasBoton.setToolTipText("Volver");
        informacionMedicaGUI.ingresarBoton.setToolTipText("Ingresar información médica");

        informacionMedicaGUI.atrasBoton.addActionListener(this);
        informacionMedicaGUI.cancelarBoton.addActionListener(this);
        informacionMedicaGUI.ingresarBoton.addActionListener(this);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == informacionMedicaGUI.atrasBoton) cerrarControlador();

        else if(e.getSource() == informacionMedicaGUI.cancelarBoton) informacionMedicaGUI.setDefaultValues();

        else if(e.getSource() == informacionMedicaGUI.ingresarBoton) {

            if (camposValidos()) {
                ingresarDatos();
            } else {
                // Muestra un mensaje al usuario indicando que los campos son obligatorios.
                JOptionPane.showMessageDialog(informacionMedicaGUI, "Todos los campos son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }


    }

    public void cerrarControlador(){
        informacionMedicaGUI.cerrarVentana();
        //Implementa observer
        mascota.notificarObservadores();
        vistaAnterior.setVisible(true);
    }

    public void ingresarDatos(){
        if(mascota.getInformacionMedicas() != null) mascota.getInformacionMedicas().remove(informacionMedica);

        informacionMedica = new InformacionMedica(informacionMedicaGUI.fechaInformacionMedica.getDate(), tipoInformacion, informacionMedicaGUI.descripcionInformacionMedica.getText());

        mascota.getInformacionMedicas().add(informacionMedica);
        cerrarControlador();
    }

    private boolean camposValidos() {
        // Verifica si los campos requeridos están llenos
        return !informacionMedicaGUI.fechaInformacionMedica.getDate().equals(null) &&
                !informacionMedicaGUI.descripcionInformacionMedica.getText().trim().isEmpty();
    }

}
