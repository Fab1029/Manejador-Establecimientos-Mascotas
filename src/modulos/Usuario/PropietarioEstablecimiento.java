package modulos.Usuario;

import modulos.Establecimiento.Establecimiento;

import java.io.Serializable;
import java.util.*;

public class PropietarioEstablecimiento extends Usuario implements Serializable {

    private TreeMap<String, Establecimiento> establecimientos;

    public PropietarioEstablecimiento() {
        super();
        establecimientos = new TreeMap<>();
    }

    public PropietarioEstablecimiento(String cedula, String nombre, String direccion, String telefono, String correo, String contrasena, String pathFoto) {
        super(cedula, nombre, direccion, telefono, correo, contrasena, pathFoto);
        establecimientos = new TreeMap<>();
    }

    //Comienza funciones y metodos


    public TreeMap<String, Establecimiento> getEstablecimientos() {
        return establecimientos;
    }

    public Establecimiento getEstablecimiento(String ruc){

        return establecimientos.get(ruc);

    }
    public void addEstablecimiento(String ruc, Establecimiento establecimiento) {
        establecimientos.put(ruc, establecimiento);
    }

    public void removeEstablecimiento(String ruc){
        establecimientos.remove(ruc);
    }

    public boolean isEstablecimiento(String ruc){
        for(String rucEstablecimiento : establecimientos.keySet()){
            if(rucEstablecimiento.equals(ruc))
                return true;
        }

        return false;
    }


}
