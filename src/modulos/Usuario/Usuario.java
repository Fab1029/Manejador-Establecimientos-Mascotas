package modulos.Usuario;

import java.io.Serializable;
import java.util.Observable;


public class Usuario extends Observable implements Comparable <Usuario>, Serializable{

    private String correo;
    private String cedula;
    private String nombre;
    private String pathFoto;
    private String telefono;
    private String direccion;
    private String contrasena;

    public Usuario() {
    }

    public Usuario(String cedula, String nombre, String direccion, String telefono, String correo, String contrasena, String pathFoto) {
        this.correo = correo;
        this.nombre = nombre;
        this.cedula = cedula;
        this.pathFoto = pathFoto;
        this.telefono = telefono;
        this.direccion = direccion;
        this.contrasena = contrasena;

    }

    //Comienzo funciones y metodos

    public String getPathFoto() {
        return pathFoto;
    }

    public void setPathFoto(String pathFoto) {
        this.pathFoto = pathFoto;
    }

    public String getCorreo() {
        return correo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCedula() {
        return cedula;
    }

    public boolean isCorreoValid(String correo){

        String[] correoSplit = correo.split("[@.]");

        if(correoSplit.length >= 3){

            if(correoSplit[correoSplit.length - 1].equals("@") || correoSplit[correoSplit.length - 1].length() > 3) return false;

            return true;
        }

        return false;

    }

    public boolean isNombreValid(String nombre){
        if(nombre.contains(" ") && nombre.length() > 3) return true;
        return  false;
    }

    public boolean isValidCedula(String cedula){
        if(cedula.length() != 10) return false;

        int validador = 0;
        int[] mascara = {2, 1, 2, 1, 2, 1, 2, 1, 2};

        for(int i = 0; i < cedula.length() - 1; i++){
            if(mascara[i] * Integer.parseInt("" + cedula.charAt(i)) > 9){
                validador += (mascara[i] * Integer.parseInt("" + cedula.charAt(i))) - 9;
            }
            else{
                validador += mascara[i] * Integer.parseInt("" + cedula.charAt(i));
            }
        }

        if(validador % 10 == 0 && cedula.charAt(9) == '0') return true;

        validador = 10 - (validador % 10);

        if(validador == Integer.parseInt("" + cedula.charAt(9))) return  true;

        return false;

    }

    public String getTelefono() {
        return telefono;
    }
    public String getDireccion() {
        return direccion;
    }
    public boolean isTelefonoValid(String telefono){
        if(telefono.length() == 7 || telefono.length() == 10){

            if(telefono.length() == 10){
                if(telefono.charAt(0) != '0') return false;
            }

            return true;
        }

        return false;
    }
    public String getContrasena() {
        return contrasena;
    }


    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setCedula(String numeroCedula) {
        this.cedula = numeroCedula;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }


    @Override
    public int compareTo(Usuario o) {
        return Integer.compare(Integer.parseInt(cedula), Integer.parseInt(o.cedula));
    }

}