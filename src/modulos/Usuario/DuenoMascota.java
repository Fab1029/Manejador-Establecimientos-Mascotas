package modulos.Usuario;

import modulos.Mascota.Mascota;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

public class DuenoMascota extends Usuario implements Serializable {

    private TreeMap<Integer, Mascota> mascotas;

    public DuenoMascota() {
        super();
        mascotas = new TreeMap<>();
    }

    public DuenoMascota(String cedula, String nombre, String direccion, String telefono, String correo, String contrasena, String pathFoto) {
        super(cedula, nombre, direccion, telefono, correo, contrasena, pathFoto);
        mascotas = new TreeMap<>();
    }

    //Comienzo funciones y metodos


    public TreeMap<Integer, Mascota> getMascotas() {
        return mascotas;
    }

    public void addMascota(Integer numeroChip, Mascota mascota) {
       mascotas.put(numeroChip, mascota);
    }

    public Mascota getMascota(int numeroChip){
        return mascotas.get(numeroChip);
    }

    public void removeMascota(int numeroChip){
        mascotas.remove(numeroChip);
    }

    public boolean isMascota(int numeroChip){
        for(Integer chip : mascotas.keySet()){
            if(chip == numeroChip)
                return true;
        }

        return false;
    }

}
