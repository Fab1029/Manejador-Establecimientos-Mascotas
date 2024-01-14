package vistas.Mascota;

import com.formdev.flatlaf.intellijthemes.FlatArcOrangeIJTheme;
import vistas.ComponentsModified.RoundButton;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class InformacionMedicaGUI extends JFrame{
    public JButton atrasBoton;
    public JTable TablaVacunas;
    public JTable TablaCirugias;
    public JPanel PanelPrincipal;
    public JButton borrarCirugiaBoton;
    public JButton agregarVacunaBoton;
    public JButton agregarCirugiaBoton;
    public JButton borrarVacunaBoton;
    public JButton editarVacunaBoton;
    public JButton editarCirugiaBoton;
    public DefaultTableModel TablaModeloVacunas;
    public DefaultTableModel TablaModeloCirugias;


    public InformacionMedicaGUI() {

        setIcono();
        setTheme();
        setTablas();
        setTablaVacunas();
        setTablaCirugias();

        setPanelPrincipal();

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


    //Comienzo funciones y metodos
    public void setTablas(){
        TablaVacunas.setDefaultEditor(Object.class, null);
        TablaCirugias.setDefaultEditor(Object.class, null);
    }

    public void setTablaVacunas(){
        TablaModeloVacunas = new DefaultTableModel(new String [][]{}, new String[]{"Fecha", "Vacunas"});
        TablaVacunas.setModel(TablaModeloVacunas);
    }
    public void setTablaCirugias(){
        TablaModeloCirugias = new DefaultTableModel(new String[][]{}, new String []{"Fecha", "Cirugias"});
        TablaCirugias.setModel(TablaModeloCirugias);

    }

    public void setPanelPrincipal(){

        setVisible(true);
        setResizable(false);
        setSize(800, 500);//tamanio de la ventana
        setLocationRelativeTo(null); // Para centrar la ventana en la pantalla

        setContentPane(PanelPrincipal);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

    }

    public void cerrarVentana(){
        dispose();
    }


    private void createUIComponents() {
        atrasBoton = new RoundButton("", new Dimension(40,35));
        borrarVacunaBoton = new RoundButton("", new Dimension(40,35));
        editarVacunaBoton = new RoundButton("", new Dimension(40,35));
        agregarVacunaBoton = new RoundButton("", new Dimension(40,35));
        editarCirugiaBoton = new RoundButton("", new Dimension(40,35));
        borrarCirugiaBoton = new RoundButton("", new Dimension(40,35));
        agregarCirugiaBoton = new RoundButton("", new Dimension(40,35));
    }
}
