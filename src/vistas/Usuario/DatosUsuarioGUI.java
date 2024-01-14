package vistas.Usuario;

import com.formdev.flatlaf.intellijthemes.FlatArcOrangeIJTheme;
import vistas.ComponentsModified.RoundButton;
import vistas.ComponentsModified.RoundJPassword;
import vistas.ComponentsModified.RoundJTextField;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class DatosUsuarioGUI extends JFrame{
    public JTextField cedula;
    public JButton fotoBoton;
    public JTextField nombre;
    public JTextField correo;
    public JLabel fotoUsuario;

    public JButton atrasBoton;
    public JTextField telefono;
    public JTextField direccion;
    public JComboBox tipoUsuario;
    public JPanel PanelPrincipal;
    public JButton ingresarBoton;
    public JButton cancelarBoton;
    public String pathFotoUsuario;
    public JPasswordField contrasena;


    public DatosUsuarioGUI(boolean enableTipoUsuario, boolean enableCedula, boolean enableCorreo) {

        setIcono();
        setTheme();
        setPanelPrincipal(enableTipoUsuario, enableCedula, enableCorreo);

    }

    public void setTheme(){

        try {
            // Puedes elegir entre FlatLightLaf o FlatDarkLaf
            UIManager.setLookAndFeel(new FlatArcOrangeIJTheme());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

    }

    //Comienzo de metodos y funciones
    public void setDefaultValues(String cedula, String correo){
        nombre.setText("");
        telefono.setText("");
        direccion.setText("");
        contrasena.setText("");
        this.correo.setText(correo);
        this.cedula.setText(cedula);
        pathFotoUsuario = "src/vistas/complements/defaultProfileIcono.png";
        fotoUsuario.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource(pathFotoUsuario))));
    }

    public void setPanelPrincipal(boolean enableTipoUsuario, boolean enableCedula, boolean enableCorreo){

        cedula.setEnabled(enableCedula);
        correo.setEnabled(enableCorreo);
        tipoUsuario.setEnabled(enableTipoUsuario);
        pathFotoUsuario = "src/vistas/complements/defaultProfileIcono.png";

        setSize(800, 500);//tamanio de la ventana
        setLocationRelativeTo(null); // Para centrar la ventana en la pantalla

        setVisible(true);
        setResizable(false);
        setContentPane(PanelPrincipal);
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

    public void setPathFotoUsuario(){
        String[] extensionesImagen = {"png", "jpg", "jpeg", "gif"};

        JFileChooser fotoUsuario = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos de Imagen", extensionesImagen);

        try {
            int result = fotoUsuario.showOpenDialog(this);

            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fotoUsuario.getSelectedFile();

                if (selectedFile.exists()) {
                    pathFotoUsuario = selectedFile.getPath().replace('\\', '/');
                } else {
                    // El archivo seleccionado no existe
                    throw new Exception("El archivo seleccionado no existe.");
                }
            } else {
                // El usuario canceló la selección, puedes manejarlo según tus necesidades
                throw new Exception("Selección cancelada por el usuario.");
            }
        } catch (Exception e) {
            // Manejar la excepción (por ejemplo, mostrar un mensaje de error o establecer un valor predeterminado)
            System.err.println("Error al seleccionar el archivo: " + e.getMessage());
            pathFotoUsuario = "src/vistas/complements/defaultProfileIcono.png";
        }

        setFotoUsuario(pathFotoUsuario);


    }

    public void cerrarVentana(){
        dispose();

    }
    public String getCedula() {
        return cedula.getText(); // Devuelve el texto ingresado en el campo de cédula
    }

    public String getNombre() {
        return nombre.getText();
    }

    public String getDireccion(){
        return direccion.getText();
    }

    public String getTelefono() {
        return telefono.getText();
    }

    public String getCorreoElectronico(){
        return correo.getText();
    }

    public String getTipoUsuario() {
        return (String) tipoUsuario.getSelectedItem();
    }

    public void setFotoUsuario(String pathFotoUsuario){
        // Se carga la imagen original
        ImageIcon originalIcon = new ImageIcon(pathFotoUsuario);
        Image originalImage = originalIcon.getImage();

        // Se ajusta el tamaño de la imagen a 100x60 pixeles
        Image resizedImage = originalImage.getScaledInstance(85, 100, Image.SCALE_SMOOTH);

        // Se crea un nuevo ImageIcon con la imagen ajustada
        ImageIcon resizedIcon = new ImageIcon(resizedImage);

        // Se establece el nuevo tamaño y se asigna al JLabel
        fotoUsuario.setSize(70, 100);
        fotoUsuario.setIcon(resizedIcon);
    }

    private void createUIComponents() {
        cedula = new RoundJTextField(21);
        nombre = new RoundJTextField(21);
        telefono = new RoundJTextField(21);
        direccion = new RoundJTextField(21);
        correo = new RoundJTextField(21);
        contrasena = new RoundJPassword(21);

        fotoBoton = new RoundButton("Seleccionar foto", new Dimension(160, 35));
        ingresarBoton = new RoundButton("Ingresar datos", new Dimension(160, 35));
        cancelarBoton = new RoundButton("Cancelar", new Dimension(160, 35));
        atrasBoton = new RoundButton("", new Dimension(40,35));

    }
}

