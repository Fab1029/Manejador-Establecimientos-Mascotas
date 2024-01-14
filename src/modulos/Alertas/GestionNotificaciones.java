package modulos.Alertas;

import java.io.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class GestionNotificaciones implements Serializable{
    private static GestionNotificaciones instance;
    private TreeMap<String, ArrayList<String>> notificaciones;
    public GestionNotificaciones(){
        notificaciones = new TreeMap<>();
    }

    //Comienzo funciones y metodos
    public static synchronized GestionNotificaciones getInstance() {
        if (instance == null) {
            instance = new GestionNotificaciones();
        }
        return instance;
    }

    public void agregarNotificacion(String email, String content){

        if(notificaciones.get(email) == null)
            notificaciones.put(email, new ArrayList<>());

        notificaciones.get(email).add(content);

    }

    public boolean isNotificacionExistente(String email, String content){
        if(notificaciones.get(email) != null){
            for(String contents : notificaciones.get(email)){
                if(contents.equals(content))
                    return true;

            }
        }

        return false;
    }

    public TreeMap<String, ArrayList<String>> getNotificaciones(){
        return notificaciones;
    }

    public void guardarDatos(){

        try{
            ObjectOutputStream archivo = new ObjectOutputStream(new FileOutputStream("Notificaciones"));

            archivo.writeObject(notificaciones);
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
            File path = new File("Notificaciones");
            if(path.exists()){
                ObjectInputStream archivo = new ObjectInputStream(new FileInputStream("Notificaciones"));
                notificaciones = (TreeMap<String, ArrayList<String>>) archivo.readObject();
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



}
