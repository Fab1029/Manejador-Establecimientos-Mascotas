import javax.swing.*;

import com.formdev.flatlaf.intellijthemes.*;
import controladores.Establecimiento.ControladorPermisos;
import controladores.Sesion.ControladorInicioSesion;


public class Main {
    public static void main(String[] args) {

        setTheme();
        new ControladorInicioSesion();


    }

    public static void setTheme(){
        try {
            UIManager.setLookAndFeel(new FlatArcOrangeIJTheme());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }


}