package vistas.Evaluacion;

import com.formdev.flatlaf.intellijthemes.FlatArcOrangeIJTheme;
import com.toedter.calendar.JDateChooser;
import vistas.ComponentsModified.RoundButton;
import vistas.ComponentsModified.RoundJTextField;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class EvaluacionInformeMonitoreoGUI extends JFrame{
    public JButton atrasBoton;
    public JButton aceptarBoton;
    public JButton cancelarBoton;
    public JPanel panelPrincipal;
    public JTextField tipoMonitoreo;
    public JTextField codigoProyecto;
    public JTextField numeroResolucion;
    public JCheckBox oficioUltimaAprobacion;
    public JPanel fechaUltimaAceptacionPanel;
    public JDateChooser fechaUltimaAceptacion;


    public EvaluacionInformeMonitoreoGUI(){

        setIcono();
        setTheme();
        setFechaUltimaAceptacion();

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

    //Comienzo funciones y metodos
    public void setDefaultValues(){

        tipoMonitoreo.setText("");
        codigoProyecto.setText("");
        numeroResolucion.setText("");
        oficioUltimaAprobacion.setSelected(false);

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

    public void setPanelPrincipal(){

        setVisible(true);
        setResizable(false);
        setSize(800, 300);

        setLocationRelativeTo(null);
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }
    public void cerrarVentana(){
        dispose();
    }
    public void setFechaUltimaAceptacion(){
        fechaUltimaAceptacion = new JDateChooser();
        fechaUltimaAceptacionPanel.add(fechaUltimaAceptacion);
    }
    public void setPanelPrincipalVisible(boolean status){
        setVisible(status);
    }


    private void createUIComponents() {

        atrasBoton = new RoundButton("", new Dimension(40,35));
        aceptarBoton = new RoundButton("Aceptar", new Dimension(160, 35));
        cancelarBoton = new RoundButton("Cancelar", new Dimension(160, 35));

        codigoProyecto = new RoundJTextField(21);
        tipoMonitoreo = new RoundJTextField(21);
        numeroResolucion = new RoundJTextField(21);
    }
}
