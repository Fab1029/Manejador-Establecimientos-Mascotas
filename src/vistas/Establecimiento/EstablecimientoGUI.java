package vistas.Establecimiento;

import com.formdev.flatlaf.intellijthemes.FlatArcOrangeIJTheme;
import vistas.ComponentsModified.RoundButton;
import vistas.ComponentsModified.RoundJTextField;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class EstablecimientoGUI extends JFrame{
    public JTextField ruc;
    public JTextField correo;
    public JButton atrasBoton;
    public JTextField telefono;
    public JButton aceptarBoton;
    public JButton cancelarBoton;

    public JPanel panelPrincipal;
    public JTextField coordenadas;
    public ButtonGroup tiposEvaluacion;
    public JComboBox tipoEstablecimiento;
    public JCheckBox otroEstablecimiento;
    public JTextField nombreEstablecimiento;
    public JCheckBox evaluacionInformeMonitoreo;
    public JTextField tipoEstablecimientoIngreso;
    public JCheckBox evaluacionAuditoriaAmbiental;
    public JCheckBox evaluacionInformeAmbientalCumplimiento;

    public EstablecimientoGUI(){

        setIcono();
        setTheme();
        setTiposEvaluacion();
        setPanelPrincipalVisible(true);
        setTipoEstablecimientoIngreso(false);

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

    //Comienzo de metodos y funciones
    public void setDefaultValues(){
        ruc.setText("");
        correo.setText("");
        telefono.setText("");
        coordenadas.setText("");
        nombreEstablecimiento.setText("");
        tipoEstablecimientoIngreso.setText("");

        tiposEvaluacion.clearSelection();
        otroEstablecimiento.setSelected(false);

    }
    public void setPanelPrincipal(){

        setVisible(true);
        setResizable(false);
        setSize(800, 500);

        setLocationRelativeTo(null);
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

    }
    public void setTiposEvaluacion(){
        tiposEvaluacion = new ButtonGroup();

        tiposEvaluacion.add(evaluacionInformeMonitoreo);
        tiposEvaluacion.add(evaluacionAuditoriaAmbiental);
        tiposEvaluacion.add(evaluacionInformeAmbientalCumplimiento);
    }
    public void cerrarVentana(){
        dispose();
    }
    public void setPanelPrincipalVisible(boolean status){
        setVisible(status);
    }
    public void setTipoEstablecimientoIngreso(boolean status){
        tipoEstablecimientoIngreso.setEnabled(status);
    }

    private void createUIComponents() {

        atrasBoton = new RoundButton("", new Dimension(40,35));
        aceptarBoton = new RoundButton("Aceptar", new Dimension(160, 35));
        cancelarBoton = new RoundButton("Cancelar", new Dimension(160, 35));

        ruc = new RoundJTextField(21);
        correo = new RoundJTextField(21);
        nombreEstablecimiento = new RoundJTextField(21);
        telefono = new RoundJTextField(21);
        coordenadas = new RoundJTextField(21);
        tipoEstablecimientoIngreso = new RoundJTextField(21);

    }
}
