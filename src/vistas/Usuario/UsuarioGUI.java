package vistas.Usuario;

import com.formdev.flatlaf.intellijthemes.FlatArcOrangeIJTheme;
import modulos.Usuario.*;
import vistas.ComponentsModified.RoundButton;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class UsuarioGUI extends JFrame{
    public JLabel fotoUsuario;
    public JMenuItem gestionCGA;

    public JPanel panelPrincipal;
    public JMenuItem cerrarSesion;
    public JTable notificaciones;
    public JMenuBar menuPrincipal;
    public JLabel nombreUsuario;
    public JLabel correoUsuario;
    public JLabel telefonoUsuario;
    public JButton evaluacionBoton;
    public JMenuItem gestionMascota;
    public JMenuItem gestionarCuenta;
    public JMenuItem gestionDuenoMascota;
    public JMenuItem gestionEstablecimiento;
    public JMenuItem gestionDuenoEstablecimiento;
    public DefaultTableModel notificacionesModel;

    public UsuarioGUI(Usuario usuario){

        setIcono();
        setTheme();
        setMenuItems();
        setnotificaciones();
        setNotificaciones();
        setMenuPrincipal(usuario);
        setFotoUsuario(usuario.getPathFoto());
        setInformacionUsuario(usuario.getNombre(), usuario.getCorreo(), usuario.getTelefono());

        setPanelPrincipal();

    }

    //Comienzo funciones y metodos
    public void setnotificaciones(){
        notificaciones.setDefaultEditor(Object.class, null);
    }


    public void cerrarVentana(){
        dispose();
    }

    public void setMenuItems(){
        gestionCGA = new JMenuItem("Gestionar CGA");
        cerrarSesion = new JMenuItem("Cerrar sesion");
        gestionarCuenta = new JMenuItem("Gestionar cuenta");
        gestionMascota = new JMenuItem("Gestionar  mascotas");
        gestionEstablecimiento = new JMenuItem("Gestionar establecimientos");
        gestionDuenoMascota = new JMenuItem("Gestionar duenos mascotas");
        gestionDuenoEstablecimiento = new JMenuItem("Gestionar dueno establecimientos");

    }

    public void setFotoUsuario(String pathFoto){

        if(pathFoto == null)
            pathFoto = "src/vistas/complements/defaultProfileIcono.png";

        // Se carga la imagen original
        ImageIcon originalIcon = new ImageIcon(pathFoto);
        Image originalImage = originalIcon.getImage();

        // Se ajusta el tamaño de la imagen a 100x80 pixeles
        Image resizedImage = originalImage.getScaledInstance(80, 100, Image.SCALE_SMOOTH);

        // Se crea un nuevo ImageIcon con la imagen ajustada
        ImageIcon resizedIcon = new ImageIcon(resizedImage);

        // Se establece el nuevo tamaño y se asigna al JLabel
        fotoUsuario.setSize(80, 100);
        fotoUsuario.setIcon(resizedIcon);
    }

    public void setNotificaciones(){
        notificacionesModel = new DefaultTableModel(new String[][]{}, new String[]{"Notificaciones"});
        notificaciones.setModel(notificacionesModel);
    }

    public void setPanelPrincipal(){

        setVisible(true);
        setResizable(false);
        setSize(800, 500);

        setLocationRelativeTo(null);
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

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

    public void setTheme(){

        try {
            // Puedes elegir entre FlatLightLaf o FlatDarkLaf
            UIManager.setLookAndFeel(new FlatArcOrangeIJTheme());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

    }

    public void setMenuPrincipal(Usuario usuario){

        JMenu cuenta = new JMenu("Cuenta");
        JMenu gestionar = new JMenu("Gestionar");


        cuenta.add(gestionarCuenta);
        cuenta.add(cerrarSesion);

        if(usuario instanceof Administrador || usuario instanceof PersonalCGA){

            if(usuario instanceof  Administrador) gestionar.add(gestionCGA);

            gestionar.add(gestionEstablecimiento);
            gestionar.add(gestionDuenoEstablecimiento);
            gestionar.add(gestionDuenoMascota);
            gestionar.add(gestionMascota);

        }

        else if(usuario instanceof PropietarioEstablecimiento) gestionar.add(gestionEstablecimiento);

        else gestionar.add(gestionMascota);

        menuPrincipal.add(gestionar);
        menuPrincipal.add(cuenta);
    }

    public void setPanelPrincipalVisible(boolean status){
        setVisible(status);
    }

    public void setInformacionUsuario(String nombreUsuario, String correoUsuario, String telefonoUsuario){
        this.nombreUsuario.setText(nombreUsuario);
        this.correoUsuario.setText(correoUsuario);
        this.telefonoUsuario.setText(telefonoUsuario);
    }

    private void createUIComponents() {
        evaluacionBoton = new RoundButton("", new Dimension(40,35));
    }
}
