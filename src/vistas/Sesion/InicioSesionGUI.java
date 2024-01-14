package vistas.Sesion;

import com.formdev.flatlaf.intellijthemes.FlatArcOrangeIJTheme;
import vistas.ComponentsModified.RoundButton;
import vistas.ComponentsModified.RoundJPassword;
import vistas.ComponentsModified.RoundJTextField;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;


public class InicioSesionGUI extends JFrame{
    public JTextField usuario;
    public JPanel PanelPrincipal;
    public JPasswordField contrasena;
    public JButton iniciarSesionBoton;
    public JButton registrarUsuarioButton;
    public JPanel PanelInfo;
    private JLabel IconoUsuario;
    private JLabel IconoContrasena;
    private JLabel Icono;

    public InicioSesionGUI() {
        setTheme();
        setPanelPrincipal();

    }

    //Comienzo funciones y metodos

    public void setDefaultValues(){
        usuario.setText("");
        contrasena.setText("");
    }

    public void setPanelPrincipal(){

        //Color ColorBase = new Color(0xF3FEF2);

        setIcono();
        setVisible(true);
        setResizable(false);
        setSize(600, 400);//tamanio de la ventana
        setLocationRelativeTo(null); // Para centrar la ventana en la pantalla


        setContentPane(PanelPrincipal);
        PanelPrincipal.requestFocusInWindow();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public void setPanelPrincipalVisible(boolean status){
        setVisible(status);
    }


    public void setTheme() {
        try {
            // Puedes elegir entre FlatLightLaf o FlatDarkLaf
            UIManager.setLookAndFeel(new FlatArcOrangeIJTheme());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }


    public void setIcono() {
        // Cambiamos el icono de la ventana
        try {
            // Cargamos la imagen desde el recurso
            InputStream resourceAsStream = getClass().getResourceAsStream("/vistas/Complements/pata.png");
            BufferedImage icon = ImageIO.read(resourceAsStream);

            // Establecemos el icono de la ventana
            setIconImage(icon);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private void createUIComponents() {
        usuario = new RoundJTextField(10);// TODO: place custom component creation code here
        contrasena = new RoundJPassword(10);

        iniciarSesionBoton =  new RoundButton("Iniciar Sesion", new Dimension(170,40));
        registrarUsuarioButton = new RoundButton("Registrar Usuario",new Dimension(170, 40));

    }





}
