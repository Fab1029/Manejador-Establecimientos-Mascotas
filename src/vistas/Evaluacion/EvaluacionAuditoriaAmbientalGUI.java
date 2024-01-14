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

public class EvaluacionAuditoriaAmbientalGUI extends JFrame{
    public JButton atrasBoton;
    public JTextField NroPoliza;
    public JButton aceptarBoton;
    public JButton cancelarButon;

    public JPanel panelPrincipal;
    public JTextField montoPoliza;
    public JTextField codigoProyecto;
    public JPanel periodoAuditadoPanel;
    public JDateChooser periodoAuditado;
    public JCheckBox oficioAprobacionTDR;
    public JPanel fechaAprobacionTDRPanel;
    public JPanel fechaEmisionPolizaPanel;
    public JDateChooser fechaAprobacionTDR;
    public JTextField numLicenciaAmbiental;
    public JDateChooser fechaEmisionPoliza;
    public JCheckBox oficioUltimaAprobacion;
    public JPanel fechaEmisionLicenciaPanel;
    public JPanel fechaUltimaAceptacionPanel;
    public JDateChooser fechaEmisionLicencia;
    public JTextField nombreConsultorUltimaAA;
    public JPanel fechaVencimientoPolizaPanel;
    public JDateChooser fechaUltimaAceptacion;
    public JDateChooser fechaVencimientoPoliza;

    public EvaluacionAuditoriaAmbientalGUI(){

        setTheme();
        setIcono();

        setPeriodoAuditado();
        setFechaAprobacionTDR();
        setFechaEmisionPoliza();
        setFechaEmisionLicencia();
        setFechaUltimaAceptacion();
        setFechaVencimientoPoliza();

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
    public void setDefaultValues(){
        NroPoliza.setText("");
        montoPoliza.setText("");
        codigoProyecto.setText("");
        numLicenciaAmbiental.setText("");
        nombreConsultorUltimaAA.setText("");
        oficioAprobacionTDR.setSelected(false);
        oficioUltimaAprobacion.setSelected(false);


        fechaAprobacionTDR.cleanup();
        fechaAprobacionTDRPanel.add(fechaAprobacionTDR);

        fechaEmisionPoliza.cleanup();
        fechaEmisionPolizaPanel.add(fechaEmisionPoliza);

        fechaEmisionLicencia.cleanup();
        fechaEmisionLicenciaPanel.add(fechaEmisionLicencia);

        fechaUltimaAceptacion.cleanup();
        fechaUltimaAceptacionPanel.add(fechaUltimaAceptacion);

        fechaVencimientoPoliza.cleanup();
        fechaVencimientoPolizaPanel.add(fechaVencimientoPoliza);

    }
    public void setPanelPrincipal(){

        setResizable(false);
        setSize(800, 500);

        setLocationRelativeTo(null);
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }
    public void setFechaEmisionPoliza(){
        fechaEmisionPoliza = new JDateChooser();
        fechaEmisionPolizaPanel.add(fechaEmisionPoliza);

    }
    public void setPeriodoAuditado(){
        periodoAuditado = new JDateChooser();
        periodoAuditadoPanel.add(periodoAuditado);
    }

    public void setFechaAprobacionTDR(){
        fechaAprobacionTDR = new JDateChooser();
        fechaAprobacionTDRPanel.add(fechaAprobacionTDR);
    }
    public void setFechaEmisionLicencia(){
        fechaEmisionLicencia = new JDateChooser();
        fechaEmisionLicenciaPanel.add(fechaEmisionLicencia);
    }
    public void cerrarVentana(){
        dispose();
    }
    public void setFechaUltimaAceptacion(){
        fechaUltimaAceptacion = new JDateChooser();
        fechaUltimaAceptacionPanel.add(fechaUltimaAceptacion);
    }
    public void setFechaVencimientoPoliza(){
        fechaVencimientoPoliza = new JDateChooser();
        fechaVencimientoPolizaPanel.add(fechaVencimientoPoliza);

    }
    public void setPanelPrincipalVisible(boolean status){
        setVisible(status);
    }

    private void createUIComponents() {

        atrasBoton = new RoundButton("", new Dimension(40,35));
        aceptarBoton = new RoundButton("Aceptar", new Dimension(160, 35));
        cancelarButon = new RoundButton("Cancelar", new Dimension(160, 35));

        NroPoliza = new RoundJTextField(21);
        montoPoliza = new RoundJTextField(21);
        codigoProyecto = new RoundJTextField(21);
        numLicenciaAmbiental = new RoundJTextField(21);
        nombreConsultorUltimaAA = new RoundJTextField(21);

    }
}
