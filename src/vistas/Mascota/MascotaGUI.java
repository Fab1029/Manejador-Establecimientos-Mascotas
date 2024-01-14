package vistas.Mascota;

import com.formdev.flatlaf.intellijthemes.FlatArcOrangeIJTheme;
import vistas.ComponentsModified.RoundButton;
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

public class MascotaGUI extends JFrame{
    public JCheckBox macho;
    public JCheckBox hembra;
    public JLabel fotoCarnet;

    public JButton atrasBoton;
    public JSpinner edadMascota;
    public JButton agregarBoton;
    public JPanel PanelPrincipal;
    public String pathFotoCarnet;
    public JComboBox tipoMascota;

    public JButton cancelarBoton;
    public JTextField razaMascota;
    public ButtonGroup sexoMascota;
    public JTextField colorMascota;
    public JTextField nombreMascota;
    public JButton ingresarFotoBoton;
    public JTextField numeroChip;

    public MascotaGUI() {

        setIcono();
        setTheme();
        setSexoMascotaMascota();
        setPanelPrincipal();

    }

    //Comienzo funciones y metodos
    public void setFotoCarnet(){
        setPathFotoCarnet();

        // Se carga la imagen original
        ImageIcon originalIcon = new ImageIcon(pathFotoCarnet);
        Image originalImage = originalIcon.getImage();

        // Se ajusta el tamaño de la imagen a 100x60 pixeles
        Image resizedImage = originalImage.getScaledInstance(70, 100, Image.SCALE_SMOOTH);

        // Se crea un nuevo ImageIcon con la imagen ajustada
        ImageIcon resizedIcon = new ImageIcon(resizedImage);

        // Se establece el nuevo tamaño y se asigna al JLabel
        fotoCarnet.setIcon(resizedIcon);
    }

    public void setDefaultValues(){

        edadMascota.setValue(0);
        razaMascota.setText("");
        colorMascota.setText("");
        nombreMascota.setText("");
        sexoMascota.clearSelection();
        pathFotoCarnet = "/vistas/Complements/mascotasIcono.png";
        fotoCarnet.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource(pathFotoCarnet))));

    }
    public void setPathFotoCarnet(){
       String[] extensionesImagen = {"png", "jpg", "jpeg", "gif"};

        JFileChooser fotoMascota = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos de Imagen", extensionesImagen);

        fotoMascota.setFileFilter(filter);
        try {
            int result = fotoMascota.showOpenDialog(this);

            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fotoMascota.getSelectedFile();

                if (selectedFile.exists()) {
                    pathFotoCarnet = selectedFile.getPath();
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
            pathFotoCarnet = ""; // O establecer un valor predeterminado según tus necesidades
        }

   }
    public void setPanelPrincipal(){

        setVisible(true);
        setResizable(false);
        setSize(800, 500);//tamanio de la ventana
        setLocationRelativeTo(null); // Para centrar la ventana en la pantalla

        setContentPane(PanelPrincipal);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

    }

    public void setTheme(){

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

    public void setSexoMascotaMascota(){
        sexoMascota = new ButtonGroup();

        sexoMascota.add(macho);
        sexoMascota.add(hembra);
    }

    public void cerrarVentana(){
        dispose();
    }

    private void createUIComponents() {
       numeroChip = new RoundJTextField(21);
       nombreMascota = new RoundJTextField(21);
       razaMascota = new RoundJTextField(21);
       colorMascota = new RoundJTextField(21);

        ingresarFotoBoton = new RoundButton("Seleccionar foto Carnet", new Dimension(160, 35));
        agregarBoton = new RoundButton("Agregar mascota", new Dimension(160, 35));
        cancelarBoton = new RoundButton("Cancelar", new Dimension(160, 35));
        atrasBoton = new RoundButton("", new Dimension(40,35));

    }
}
