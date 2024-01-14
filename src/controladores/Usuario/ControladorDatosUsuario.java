package controladores.Usuario;

import controladores.Sesion.ControladorInicioSesion;
import modulos.Usuario.*;
import vistas.Usuario.DatosUsuarioGUI;
import vistas.Usuario.UsuarioGUI;

import javax.swing.*;
import javax.swing.plaf.PanelUI;
import java.awt.*;
import java.awt.event.*;

public class ControladorDatosUsuario implements ActionListener, KeyListener {
    private int opcion;
    private Usuario usuario;
    private JFrame vistaAnterior;
    private DatosUsuarioGUI datosUsuarioGUI;
    private GestionUsuarios gestionUsuarios;

    public ControladorDatosUsuario(int opcion, Usuario usuario, JFrame vistaAnterior){
        this.opcion = opcion;
        this.usuario = usuario;
        this.vistaAnterior = vistaAnterior;


        setVentana();
        setListeners();
        gestionUsuarios = GestionUsuarios.getInstance();

    }

    //Comienzo de metodos y funciones
    public void setListeners(){

        datosUsuarioGUI.atrasBoton.setToolTipText("Volver");

        datosUsuarioGUI.fotoBoton.addActionListener(this);
        datosUsuarioGUI.atrasBoton.addActionListener(this);
        datosUsuarioGUI.cancelarBoton.addActionListener(this);
        datosUsuarioGUI.ingresarBoton.addActionListener(this);

        datosUsuarioGUI.nombre.addKeyListener(this);
        datosUsuarioGUI.cedula.addKeyListener(this);
        datosUsuarioGUI.telefono.addKeyListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == datosUsuarioGUI.fotoBoton) datosUsuarioGUI.setPathFotoUsuario();

        else if(e.getSource() == datosUsuarioGUI.atrasBoton) cerrarControlador();

        else if(e.getSource() == datosUsuarioGUI.cancelarBoton){
            if(usuario == null) datosUsuarioGUI.setDefaultValues("", "");
            else datosUsuarioGUI.setDefaultValues(usuario.getCedula(), usuario.getCorreo());
        }

        else if(e.getSource() == datosUsuarioGUI.ingresarBoton) {

            if(camposEstanLLenos() && validaciones())
                ingresarDatosUsuario();
            else
                JOptionPane.showMessageDialog(null, "Todos los campos deben estar llenos.", "ERROR", JOptionPane.ERROR_MESSAGE);

        }

    }

    //Se setea informacion acerca de atributos cambiantes
    public void setVentana(){

        if(usuario == null)
            datosUsuarioGUI = new DatosUsuarioGUI(true, true, true);

        else if(usuario.getCedula() == null){
            datosUsuarioGUI = new DatosUsuarioGUI(false, true, true);
            setTipoUsuarioGUI();
        }

        else{

            datosUsuarioGUI = new DatosUsuarioGUI(false, false, true);
            setTipoUsuarioGUI();
            setDatosUsuarioGUI();

        }
    }

    //Se setea informacion si el usuario ya existe en la vista
    public void setDatosUsuarioGUI(){
        datosUsuarioGUI.cedula.setText(usuario.getCedula());
        datosUsuarioGUI.correo.setText(usuario.getCorreo());
        datosUsuarioGUI.nombre.setText(usuario.getNombre());
        datosUsuarioGUI.correo.setText(usuario.getCorreo());
        datosUsuarioGUI.setFotoUsuario(usuario.getPathFoto());
        datosUsuarioGUI.telefono.setText(usuario.getTelefono());
        datosUsuarioGUI.pathFotoUsuario = usuario.getPathFoto();
        datosUsuarioGUI.direccion.setText(usuario.getDireccion());
        datosUsuarioGUI.contrasena.setText(usuario.getContrasena());
    }

    public void setTipoUsuarioGUI(){
        if(usuario instanceof PersonalCGA) datosUsuarioGUI.tipoUsuario.setSelectedIndex(0);
        else if(usuario instanceof DuenoMascota) datosUsuarioGUI.tipoUsuario.setSelectedIndex(2);
        else if(usuario instanceof Administrador) datosUsuarioGUI.tipoUsuario.setSelectedIndex(1);
        else if(usuario instanceof PropietarioEstablecimiento) datosUsuarioGUI.tipoUsuario.setSelectedIndex(3);
    }


    public boolean validaciones(){

            // Validaciones para nuevo usuario
            if (!new Usuario().isNombreValid(datosUsuarioGUI.getNombre())) {
                JOptionPane.showMessageDialog(null, "Nombre no válido. Ingrese su nombre y apellido", "ERROR", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            if (!new Usuario().isValidCedula(datosUsuarioGUI.getCedula())) {
                JOptionPane.showMessageDialog(null, "Cédula no válida.","ERROR",JOptionPane.ERROR_MESSAGE);
                return false;
            }

            if (!new Usuario().isTelefonoValid(datosUsuarioGUI.getTelefono())) {
                JOptionPane.showMessageDialog(null, "Teléfono no válido.", "ERROR", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            if (!new Usuario().isCorreoValid(datosUsuarioGUI.getCorreoElectronico())) {
                JOptionPane.showMessageDialog(null, "Correo no válido. Por favor, ingrese un correo válido.", "ERROR", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            return true;

    }

    public void ingresoNuevoUsuario(){

        if (datosUsuarioGUI.getTipoUsuario().equals("CGA"))
            gestionUsuarios.agregarUsuario(new PersonalCGA(datosUsuarioGUI.getCedula(), datosUsuarioGUI.getNombre(), datosUsuarioGUI.getDireccion(), datosUsuarioGUI.getTelefono(), datosUsuarioGUI.getCorreoElectronico(), new String(datosUsuarioGUI.contrasena.getPassword()), datosUsuarioGUI.pathFotoUsuario));

        else if (datosUsuarioGUI.getTipoUsuario().equals("Administrador"))
            gestionUsuarios.agregarUsuario(new Administrador(datosUsuarioGUI.getCedula(), datosUsuarioGUI.getNombre(), datosUsuarioGUI.getDireccion(), datosUsuarioGUI.getTelefono(), datosUsuarioGUI.getCorreoElectronico(), new String(datosUsuarioGUI.contrasena.getPassword()), datosUsuarioGUI.pathFotoUsuario));

        else if (datosUsuarioGUI.getTipoUsuario().equals("Dueño de mascota"))
            gestionUsuarios.agregarUsuario(new DuenoMascota(datosUsuarioGUI.getCedula(), datosUsuarioGUI.getNombre(), datosUsuarioGUI.getDireccion(), datosUsuarioGUI.getTelefono(), datosUsuarioGUI.getCorreoElectronico(), new String(datosUsuarioGUI.contrasena.getPassword()), datosUsuarioGUI.pathFotoUsuario));

        else if (datosUsuarioGUI.getTipoUsuario().equals("Dueño de establecimiento"))
            gestionUsuarios.agregarUsuario(new PropietarioEstablecimiento(datosUsuarioGUI.getCedula(), datosUsuarioGUI.getNombre(), datosUsuarioGUI.getDireccion(), datosUsuarioGUI.getTelefono(), datosUsuarioGUI.getCorreoElectronico(), new String(datosUsuarioGUI.contrasena.getPassword()), datosUsuarioGUI.pathFotoUsuario));

    }
    public void modificarUsuario(){
        usuario.setNombre(datosUsuarioGUI.getNombre());
        usuario.setTelefono(datosUsuarioGUI.getTelefono());
        usuario.setDireccion(datosUsuarioGUI.getDireccion());
        usuario.setPathFoto(datosUsuarioGUI.pathFotoUsuario);
        usuario.setCorreo(datosUsuarioGUI.getCorreoElectronico());
        usuario.setContrasena(new String(datosUsuarioGUI.contrasena.getPassword()));
    }

    public void ingresarDatosUsuario() {

        // Registro primera vez usuario
        if (usuario == null || usuario.getCedula() == null)
            ingresoNuevoUsuario();

        // Usuario ya existe, modificación de datos
        else
            modificarUsuario();

        cerrarControlador();

    }

    public void cerrarControlador(){
        datosUsuarioGUI.cerrarVentana();
        //Uso del patron observer
        if(opcion != 0)
            gestionUsuarios.notificarObservadores();

        vistaAnterior.setVisible(true);
    }


    public boolean camposEstanLLenos(){

        if(datosUsuarioGUI.direccion.getText().isEmpty() &&
                datosUsuarioGUI.nombre.getText().isEmpty() &&
                datosUsuarioGUI.cedula.getText().isEmpty() &&
                datosUsuarioGUI.telefono.getText().isEmpty() &&
                datosUsuarioGUI.correo.getText().isEmpty() &&
                datosUsuarioGUI.contrasena.getPassword().toString().isEmpty()) {

            JOptionPane.showMessageDialog(null, "Todos los campos deben estar llenos.", "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        char keyPressed = e.getKeyChar();

        if (e.getSource() == datosUsuarioGUI.nombre) {
            // Permitir letras y espacios
            if (!(Character.isLetter(keyPressed) || keyPressed == KeyEvent.VK_SPACE) || datosUsuarioGUI.nombre.getText().length() > 40)
                e.consume();

        }

        else if(e.getSource() == datosUsuarioGUI.cedula){
            if(!Character.isDigit(keyPressed) || datosUsuarioGUI.cedula.getText().length() > 9)
                e.consume();
        }

        else if (e.getSource() == datosUsuarioGUI.telefono) {
            if (!Character.isDigit(keyPressed) || datosUsuarioGUI.telefono.getText().length() > 9)
                e.consume();

        }
    }

    @Override
    public void keyPressed(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}

}
