package vistas.Usuario;

import com.formdev.flatlaf.intellijthemes.FlatArcOrangeIJTheme;
import modulos.Usuario.Administrador;
import modulos.Usuario.PersonalCGA;
import modulos.Usuario.Usuario;
import vistas.ComponentsModified.RoundButton;
import vistas.ComponentsModified.RoundJTextField;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class GestionUsuarioGUI extends JFrame{
    public JButton atrasBoton;
    public JButton borraBoton;
    public JButton editarBoton;
    public JTextField busqueda;
    public JButton buscarBoton;
    public JTable gestionTabla;
    public JButton agregarBoton;
    public JButton eliminarBoton;
    public JPanel panelPrincipal;
    public JComboBox tipoBusqueda;
    public JButton opcionAdicionalBoton;
    public JButton permisosBoton;
    public DefaultTableModel gestionModelTabla;
    public DefaultComboBoxModel<String> tipoBusquedaModel;

    public GestionUsuarioGUI(Usuario usuario, String tipoGestion, boolean opcionAdicional){
        setIcono();
        setTheme();
        setGestionTabla();
        setTipoAgregar(tipoGestion);
        setTipoBusqueda(usuario, tipoGestion);
        setPanelPrincipal(opcionAdicional);

    }


    //Comienza funciones y metodos
    public void setTipoAgregar(String tipoGestion){
        ImageIcon agregar = new ImageIcon();

        switch (tipoGestion){
            case "GestionPersonalCGA", "GestionDuenosMascotas", "GestionDuenoEstablecimiento":
                agregar = new ImageIcon("src/vistas/complements/agregarUsuarioIcono.png");

                break;
            case "GestionEstablecimientos":
                agregar = new ImageIcon("src/vistas/complements/agregarEstablecimientoIcono.png");
                break;
            case "GestionMascotas":
                agregar = new ImageIcon("src/vistas/complements/agregarMascota.png");

                break;

        }

        agregarBoton.setIcon(agregar);

    }

    public void setGestionTabla(){
        gestionTabla.setDefaultEditor(Object.class, null);
    }

    public void setPanelPrincipal(boolean opcionAdicional){

        setVisible(true);
        setResizable(false);
        opcionAdicionalBoton.setVisible(opcionAdicional);

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

    public void cerrarVentana(){
        dispose();
    }
    public void setTipoBusqueda(Usuario usuario, String tipoGestion){
        tipoBusquedaModel = new DefaultComboBoxModel<>();

        switch (tipoGestion){
            case "GestionPersonalCGA", "GestionDuenosMascotas", "GestionDuenoEstablecimiento":
                tipoBusquedaModel.addElement("Cedula");
                tipoBusquedaModel.addElement("Nombre apellido");
                break;
            case "GestionEstablecimientos":

                tipoBusquedaModel.addElement("Ruc");

                if(usuario instanceof Administrador || usuario instanceof PersonalCGA)
                    tipoBusquedaModel.addElement("Cedula propietario");

                break;
            case "GestionMascotas":

                tipoBusquedaModel.addElement("Numero chip");

                if(usuario instanceof Administrador || usuario instanceof PersonalCGA)
                    tipoBusquedaModel.addElement("Cedula dueño");

                break;

        }

        tipoBusqueda.setModel(tipoBusquedaModel);
    }

    public void setDefaultValues(){
        busqueda.setText("");
    }

    public void setPanelPrincipalVisible(boolean status){
        setVisible(status);
    }


    private void createUIComponents() {
        busqueda = new RoundJTextField(21);

        atrasBoton = new RoundButton("", new Dimension(40,35));
        borraBoton = new RoundButton("", new Dimension(40,35));
        buscarBoton = new RoundButton("", new Dimension(40,35));
        editarBoton = new RoundButton("", new Dimension(40,35));
        agregarBoton = new RoundButton("", new Dimension(40,35));
        eliminarBoton = new RoundButton("", new Dimension(40,35));
        permisosBoton = new RoundButton("", new Dimension(40, 35));
        opcionAdicionalBoton = new RoundButton("", new Dimension(40,35));
    }
}
