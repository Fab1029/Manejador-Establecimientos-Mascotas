package controladores.Mascota;
import modulos.Mascota.GestionChips;
import modulos.Mascota.InformacionMedica;
import modulos.Mascota.Mascota;
import modulos.Usuario.*;
import vistas.Mascota.MascotaGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;

public class ControladorMascota implements ActionListener, KeyListener {
    private Mascota mascota;
    private DuenoMascota usuario;
    private JFrame vistaAnterior;
    private MascotaGUI mascotaGUI;

    public ControladorMascota(DuenoMascota usuario, Mascota mascota, JFrame vistaAnterior){
        this.mascota = mascota;
        this.usuario = usuario;
        mascotaGUI = new MascotaGUI();
        this.vistaAnterior = vistaAnterior;

        setVentana();
        setListeners();
    }

    //Comienza funciones y metodos

    public void setVentana(){

        if(mascota != null){

            if(mascota.getTipoMascota().equals("Perro")) mascotaGUI.tipoMascota.setSelectedIndex(0);
            else if(mascota.getTipoMascota().equals("Gato")) mascotaGUI.tipoMascota.setSelectedIndex(1);
            else if(mascota.getTipoMascota().equals("Otro")) mascotaGUI.tipoMascota.setSelectedIndex(2);

            mascotaGUI.edadMascota.setValue(mascota.getEdad());
            mascotaGUI.nombreMascota.setText(mascota.getNombre());
            mascotaGUI.numeroChip.setText("" + mascota.getNumeroChip());

            if(mascota.getSexo().equals("Macho")) mascotaGUI.macho.setSelected(true);
            else if(mascota.getSexo().equals("Hembra")) mascotaGUI.hembra.setSelected(true);

            mascotaGUI.razaMascota.setText(mascota.getRaza());
            mascotaGUI.colorMascota.setText(mascota.getColor());

            mascotaGUI.fotoCarnet.setIcon(new ImageIcon(mascota.getPathFotoCarnet()));

        }

        else
            mascotaGUI.numeroChip.setText("" + GestionChips.getInstance().obtenerChip());

    }


    public void setListeners(){

        mascotaGUI.atrasBoton.setToolTipText("Volver");

        mascotaGUI.atrasBoton.addActionListener(this);
        mascotaGUI.agregarBoton.addActionListener(this);
        mascotaGUI.cancelarBoton.addActionListener(this);
        mascotaGUI.ingresarFotoBoton.addActionListener(this);

        mascotaGUI.nombreMascota.addKeyListener(this);
        mascotaGUI.razaMascota.addKeyListener(this);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == mascotaGUI.atrasBoton) cerrarControlador();

        else if(e.getSource() == mascotaGUI.agregarBoton){
            if(isCamposLlenos())
                agregarDatos();
            else
                JOptionPane.showMessageDialog(mascotaGUI, "Todos los campos deben estar llenos", "Campos incompletos", JOptionPane.WARNING_MESSAGE);
            // Mostrar un mensaje al usuario indicando que todos los campos deben estar llenos
        }

        else if(e.getSource() == mascotaGUI.cancelarBoton) mascotaGUI.setDefaultValues();

        else if(e.getSource() == mascotaGUI.ingresarFotoBoton) mascotaGUI.setFotoCarnet();

    }


    public void nuevaMascota(){
        String sexoMascota;
        LinkedList<InformacionMedica> informacionMedicas = new LinkedList<>();

        if (mascotaGUI.macho.isSelected())
            sexoMascota = "Macho";

        else
            sexoMascota = "Hembra";

        mascota = new Mascota((Integer) mascotaGUI.edadMascota.getValue(), sexoMascota,
                mascotaGUI.razaMascota.getText(), mascotaGUI.colorMascota.getText(), mascotaGUI.nombreMascota.getText(),
                Integer.parseInt(mascotaGUI.numeroChip.getText()), mascotaGUI.pathFotoCarnet, mascotaGUI.tipoMascota.getSelectedItem().toString(), informacionMedicas);


        usuario.addMascota(mascota.getNumeroChip(), mascota);
    }
    public void editarMascota(){
        String sexoMascota;

        if (mascotaGUI.macho.isSelected())
            sexoMascota = "Macho";

        else
            sexoMascota = "Hembra";

        mascota.setTipoMascota(mascotaGUI.tipoMascota.getSelectedItem().toString());
        mascota.setEdad((Integer) mascotaGUI.edadMascota.getValue());
        mascota.setNombre(mascotaGUI.nombreMascota.getText());
        mascota.setRaza(mascotaGUI.razaMascota.getText());
        mascota.setColor(mascotaGUI.colorMascota.getText());
        mascota.setSexo(sexoMascota);
    }

    public void agregarDatos() {

        if(mascota != null)
            editarMascota();
        else
            nuevaMascota();

        cerrarControlador();

    }

    // Método para verificar si todos los campos están llenos
    private boolean isCamposLlenos() {
        return !mascotaGUI.nombreMascota.getText().isEmpty() &&
                !mascotaGUI.razaMascota.getText().isEmpty() &&
                !mascotaGUI.colorMascota.getText().isEmpty() &&
                mascotaGUI.edadMascota.getValue() != null &&
                mascotaGUI.tipoMascota.getSelectedItem() != null;
    }

    public void cerrarControlador(){

        if(mascota == null) GestionChips.getInstance().removeChip(Integer.parseInt(mascotaGUI.numeroChip.getText()));

        mascotaGUI.cerrarVentana();
        GestionUsuarios.getInstance().notificarObservadores();
        vistaAnterior.setVisible(true);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        char keyPressed = e.getKeyChar();

        if (e.getSource() == mascotaGUI.nombreMascota || e.getSource() == mascotaGUI.razaMascota || e.getSource() == mascotaGUI.colorMascota) {
            // Permitir letras y espacios
            if (!(Character.isLetter(keyPressed) || keyPressed == KeyEvent.VK_SPACE)) {
                e.consume();
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}
}
