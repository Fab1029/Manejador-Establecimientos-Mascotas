package modulos.Mascota;

import modulos.Usuario.GestionUsuarios;
import modulos.Usuario.Usuario;

import java.io.*;
import java.util.Map;
import java.util.Stack;


public class GestionChips  implements Serializable{
    private int numeroChipActual;
    private static GestionChips instance;
    private Stack<Integer> chipsDisponibles;

    public GestionChips(){
        numeroChipActual = 0;
        chipsDisponibles = new Stack<>();

    }

    public static synchronized GestionChips getInstance() {
        if (instance == null) {
            instance = new GestionChips();
        }
        return instance;
    }

    public int obtenerChip(){

        if(chipsDisponibles.isEmpty()){
            numeroChipActual += 1;
            return numeroChipActual;
        }

        else return chipsDisponibles.pop();
    }


    public void removeChip(int chip){
        chipsDisponibles.push(chip);
    }


    public void guardarInstanciaChips(){

        try{
            ObjectOutputStream archivo = new ObjectOutputStream(new FileOutputStream("InstanciaChips"));

            archivo.writeObject(GestionChips.getInstance());
            archivo.flush();
            archivo.close();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void cargarInstanciaChips(){
        try {
            File path = new File("InstanciaChips");
            if(path.exists()){
                ObjectInputStream archivo = new ObjectInputStream(new FileInputStream("InstanciaChips"));
                instance = (GestionChips) archivo.readObject();
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

    public void guardarChipsDisponibles(){

        try{
            ObjectOutputStream archivo = new ObjectOutputStream(new FileOutputStream("ChipsDisponibles"));

            archivo.writeObject(chipsDisponibles);
            archivo.flush();
            archivo.close();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void cargarChipsDisponibles(){
        try {
            File path = new File("ChipsDisponibles");
            if(path.exists()){
                ObjectInputStream archivo = new ObjectInputStream(new FileInputStream("ChipsDisponibles"));
                chipsDisponibles = (Stack<Integer>) archivo.readObject();
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
