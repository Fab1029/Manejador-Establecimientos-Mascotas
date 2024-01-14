package vistas.Mascota;

import com.formdev.flatlaf.intellijthemes.FlatArcOrangeIJTheme;
import com.toedter.calendar.JDateChooser;
import vistas.ComponentsModified.RoundButton;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class IngresoInformacionMedicaGUI extends JFrame{
    public JButton atrasBoton;
    public JButton cancelarBoton;
    public JPanel panelPrincipal;
    public JButton ingresarBoton;
    public JDateChooser fechaInformacionMedica;
    public JPanel fechaInformacionMedicaPanel;
    public JTextArea descripcionInformacionMedica;


    public IngresoInformacionMedicaGUI(){

        setIcono();
        setTheme();
        setFechaInformacionMedica();
        setPanelPrincipal();
    }

    //Comienzo funciones y metodos

    public void setPanelPrincipal(){


        setVisible(true);
        setResizable(false);
        setSize(500, 400);
        setLocationRelativeTo(null);

        setContentPane(panelPrincipal);
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

    public void cerrarVentana(){
        dispose();
    }
    public void setFechaInformacionMedica(){
        fechaInformacionMedica = new JDateChooser();
        fechaInformacionMedicaPanel.add(fechaInformacionMedica);
    }

    public void setDefaultValues(){
        descripcionInformacionMedica.setText("");
    }


    private void createUIComponents() {
        atrasBoton = new RoundButton("", new Dimension(40,35));
        ingresarBoton = new RoundButton("Seleccionar foto Carnet", new Dimension(160, 35));
        cancelarBoton = new RoundButton("Agregar mascota", new Dimension(160, 35));
    }
}
