package vistas.Permisos;

import com.formdev.flatlaf.intellijthemes.FlatArcOrangeIJTheme;
import vistas.ComponentsModified.RoundButton;
import vistas.ComponentsModified.RoundJTextField;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class PermisosGUI extends JFrame {
    public JPanel PanelPrincipal;
    public JButton btnGenerarReporte;
    public JButton btnRegresar;
    public JTextField txtRuc;
    public JCheckBox usoSuelo;
    public JCheckBox solicitudRegistro;
    public JCheckBox pagoTarifario;
    public JCheckBox inspeccionAprobadaUGA;
    public JCheckBox croquisEstablecimiento;
    public JCheckBox responsableTecnico;
    public JCheckBox certificadoEnergiaAtomica;
    public JCheckBox listadoPersonal;
    public JCheckBox rucValido;

    public PermisosGUI() {

        setIcono();
        setTheme();
        setPanelPrincipal();

        setVisible(true);


    }

    public void setPanelPrincipal(){

        setVisible(true);
        setResizable(false);
        setSize(800, 500);

        setLocationRelativeTo(null);
        setContentPane(PanelPrincipal);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

    }

    public void setDefaultValues(){
        usoSuelo.setSelected(false);
        solicitudRegistro.setSelected(false);
        pagoTarifario.setSelected(false);
        inspeccionAprobadaUGA.setSelected(false);
        croquisEstablecimiento.setSelected(false);
        responsableTecnico.setSelected(false);
        certificadoEnergiaAtomica.setSelected(false);
        listadoPersonal.setSelected(false);
        rucValido.setSelected(false);
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

    public void cerrarVentana(){
        dispose();
    }

    private void createUIComponents() {
        txtRuc = new RoundJTextField(21);

        btnRegresar = new RoundButton("", new Dimension(40,35));
        btnGenerarReporte = new RoundButton("Ingresar Informacion", new Dimension(160,35));

    }
}
