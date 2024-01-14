package modulos.Usuario;

import modulos.Usuario.Usuario;

import java.io.Serializable;

public class PersonalCGA extends Usuario implements Serializable {
    public PersonalCGA() {
        super();
    }

    public PersonalCGA(String cedula, String nombre, String direccion, String telefono, String correo, String contrasena, String pathFoto) {
        super(cedula, nombre, direccion, telefono, correo, contrasena, pathFoto);
    }
}
