package controladores.Sesion;
import controladores.Usuario.ControladorDatosUsuario;
import controladores.Usuario.ControladorUsuario;
import modulos.Alertas.GestionNotificaciones;
import modulos.Mascota.GestionChips;
import modulos.Usuario.GestionUsuarios;
import vistas.Sesion.InicioSesionGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class ControladorInicioSesion implements ActionListener, KeyListener {
    private GestionUsuarios gestionUsuarios;
    private InicioSesionGUI inicioSesionGUI;
    private GestionNotificaciones gestionNotificaciones;

    public ControladorInicioSesion(){
        inicioSesionGUI = new InicioSesionGUI();
        gestionUsuarios = GestionUsuarios.getInstance();
        gestionNotificaciones = GestionNotificaciones.getInstance();

        setListeners();
        gestionUsuarios.cargarDatos();
        gestionNotificaciones.cargarDatos();
        GestionChips.getInstance().cargarInstanciaChips();
        GestionChips.getInstance().cargarChipsDisponibles();

        cerrarControlador();
    }


    //Comienzo funciones y metodos

    public void setListeners(){

        inicioSesionGUI.usuario.addKeyListener(this);
        inicioSesionGUI.iniciarSesionBoton.addActionListener(this);
        inicioSesionGUI.registrarUsuarioButton.addActionListener(this);

        TextUsuario();
        TextContrasena();
    }


    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == inicioSesionGUI.iniciarSesionBoton){
            iniciarSesion();
            inicioSesionGUI.setDefaultValues();
        }

        else if(e.getSource() == inicioSesionGUI.registrarUsuarioButton){
            registroUsuario();
            inicioSesionGUI.setDefaultValues();
        }

    }

    public void TextUsuario(){

        // Establecer texto predefinido y color gris claro para el JTextField usuario
        inicioSesionGUI.usuario.setForeground(Color.GRAY);
        inicioSesionGUI.usuario.setText("Usuario");

        // Agregar FocusListener para el JTextField usuario
        inicioSesionGUI.usuario.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (inicioSesionGUI.usuario.getForeground().equals(Color.GRAY)) {
                    inicioSesionGUI.usuario.setText("");
                    inicioSesionGUI.usuario.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (inicioSesionGUI.usuario.getText().isEmpty()) {
                    inicioSesionGUI.usuario.setText("Usuario");
                    inicioSesionGUI.usuario.setForeground(Color.GRAY);
                }
            }
        });

    }

    public void TextContrasena(){

        // Establecer texto predefinido y color gris claro para el JPasswordField contrasena
        inicioSesionGUI.contrasena.setForeground(Color.GRAY);
        inicioSesionGUI.contrasena.setEchoChar((char) 0); // Hacer visible el texto predefinido
        inicioSesionGUI.contrasena.setText("Contraseña");


        // Agregar FocusListener para el JPasswordField contrasena
        inicioSesionGUI.contrasena.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (inicioSesionGUI.contrasena.getForeground().equals(Color.GRAY)) {
                    inicioSesionGUI.contrasena.setText("");
                    inicioSesionGUI.contrasena.setForeground(Color.BLACK);
                    inicioSesionGUI.contrasena.setEchoChar('*'); // Hacer visible el texto ingresado
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (inicioSesionGUI.contrasena.getPassword().length == 0) {
                    inicioSesionGUI.contrasena.setText("Contraseña");
                    inicioSesionGUI.contrasena.setForeground(Color.GRAY);
                    inicioSesionGUI.contrasena.setEchoChar((char) 0); // Hacer visible el texto predefinido
                }
            }
        });

    }

    public void registroUsuario(){
        inicioSesionGUI.setPanelPrincipalVisible(false);
        new ControladorDatosUsuario(0, null, inicioSesionGUI);

    }

    public void iniciarSesion(){

        if(gestionUsuarios.isUsuario(inicioSesionGUI.usuario.getText())){

            if(new String(inicioSesionGUI.contrasena.getPassword()).equals(gestionUsuarios.getUsuario(inicioSesionGUI.usuario.getText()).getContrasena())){
                inicioSesionGUI.setPanelPrincipalVisible(false);
                new ControladorUsuario(gestionUsuarios.getUsuario(inicioSesionGUI.usuario.getText()), inicioSesionGUI);
            }

            inicioSesionGUI.setDefaultValues();

        }

        else JOptionPane.showMessageDialog(null, "Usuario o Contraseña incorrectos","ERROR", JOptionPane.ERROR_MESSAGE);

    }


    public void cerrarControlador(){
        inicioSesionGUI.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                gestionUsuarios.guardarDatos();
                gestionNotificaciones.guardarDatos();
                GestionChips.getInstance().guardarInstanciaChips();
                GestionChips.getInstance().guardarChipsDisponibles();
            }
        });
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if(e.getSource() == inicioSesionGUI.usuario){
            char caracter = e.getKeyChar();

            if(!Character.isDigit(caracter) || inicioSesionGUI.usuario.getText().length() > 9)
                e.consume();

        }
    }

    @Override
    public void keyPressed(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}
}
