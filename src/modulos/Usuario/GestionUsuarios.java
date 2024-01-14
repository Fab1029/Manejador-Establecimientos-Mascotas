package modulos.Usuario;

import modulos.ObserverPattern.Observer;
import modulos.ObserverPattern.Subject;

import java.io.*;
import java.util.TreeMap;

public class GestionUsuarios implements Subject {
    private static GestionUsuarios instance;
    private TreeMap<String, Usuario> usuarios;
    private transient Observer controladorGestion;

    public GestionUsuarios(){
        usuarios = new TreeMap<>();
    }

    //Comienzo funciones y metodos
    public static synchronized GestionUsuarios getInstance() {
        if (instance == null) {
            instance = new GestionUsuarios();
        }
        return instance;
    }

    public TreeMap<String, Usuario> getUsuarios(){return usuarios;}

    public Usuario getUsuario(String cedula){
        return usuarios.get(cedula);
    }
    public boolean isUsuario(String correo){
        return usuarios.containsKey(correo);
    }

    public void agregarUsuario(Usuario usuario){
        usuarios.put(usuario.getCedula(), usuario);
    }

    public void guardarDatos(){

        try{
            ObjectOutputStream archivo = new ObjectOutputStream(new FileOutputStream("DatosPrograma"));

            archivo.writeObject(usuarios);
            archivo.flush();
            archivo.close();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void cargarDatos(){
        try {
            File path = new File("DatosPrograma");
            if(path.exists()){
                ObjectInputStream archivo = new ObjectInputStream(new FileInputStream("DatosPrograma"));
                usuarios = (TreeMap<String, Usuario>) archivo.readObject();
                archivo.close();
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void registrarObservador(Observer observador) {
        controladorGestion = observador;
    }

    @Override
    public void eliminarObservador(Observer observador) {

    }

    @Override
    public void notificarObservadores() {
        controladorGestion.actualizar(this);
    }
}
