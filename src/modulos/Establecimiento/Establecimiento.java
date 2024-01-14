package modulos.Establecimiento;

import modulos.Evaluacion.Evaluacion;

import javax.swing.*;
import java.io.Serializable;

public class Establecimiento <tipoEvaluacion> implements Serializable, Comparable<Establecimiento> {
    private String ruc;
    private int telefono;
    private String correo;
    private Permisos permisos;
    private String coordenadas;
    private tipoEvaluacion evaluacion;
    private String tipoEstablecimiento;
    private String nombreEstablecimiento;

    public Establecimiento() {

    }

    public Establecimiento(String ruc, int telefono, String correo, String coordenadas, String tipoEstablecimiento, String nombreEstablecimiento, tipoEvaluacion evaluacion) {
        this.ruc = ruc;
        this.correo = correo;
        this.telefono = telefono;
        this.evaluacion = evaluacion;
        this.coordenadas = coordenadas;
        this.tipoEstablecimiento = tipoEstablecimiento;
        this.nombreEstablecimiento = nombreEstablecimiento;
    }

    //Comienzo funciones y metodos


    public Permisos getPermisos() {
        return permisos;
    }

    public void setPermisos(Permisos permisos) {
        this.permisos = permisos;
    }

    public String getRuc() {
        return ruc;
    }

    public String getCorreo() {
        return correo;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getCoordenadas() {
        return coordenadas;
    }

    public tipoEvaluacion getEvaluacion() {
        return evaluacion;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public String getTipoEstablecimiento() {
        return tipoEstablecimiento;
    }

    public void setNombreEstablecimiento(String nombreEstablecimiento) {
        this.nombreEstablecimiento = nombreEstablecimiento;
    }

    public String getNombreEstablecimiento() {
        return nombreEstablecimiento;
    }

    public void setCoordenadas(String coordenadas) {
        this.coordenadas = coordenadas;
    }

    public void setEvaluacion(tipoEvaluacion evaluacion) {
        this.evaluacion = evaluacion;
    }

    public void setTipoEstablecimiento(String tipoEstablecimiento) {
        this.tipoEstablecimiento = tipoEstablecimiento;
    }

    @Override
    public String toString() {
        return "Establecimiento{" +
                "ruc=" + ruc +
                ", telefono=" + telefono +
                ", correo='" + correo + '\'' +
                ", coordenadas='" + coordenadas + '\'' +
                ", evaluacion=" + evaluacion +
                ", tipoEstablecimiento='" + tipoEstablecimiento + '\'' +
                ", nombreEstablecimiento='" + nombreEstablecimiento + '\'' +
                '}';
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

    public boolean isCorreoValid(String correo){

        String[] correoSplit = correo.split("[@.]");

        if(correoSplit.length >= 3){

            if(correoSplit[correoSplit.length - 1].equals("@") || correoSplit[correoSplit.length - 1].length() > 3) return false;

            return true;
        }

        return false;

    }

    public boolean isRucValid(String ruc){

        if(ruc.length() == 13 && (validarRucSociedadesPrivadas(ruc) || validarRucSociedadesPublicas(ruc) || validarRucPersonasNaturales(ruc)))
            return true;
        else
            return false;


    }

    public boolean validarRucPersonasNaturales(String ruc){

        if(ruc.charAt(10) != '0' || ruc.charAt(11) != '0' || ruc.charAt(12) != '1')
            return false;

        String cedula = ruc.substring(0, 10);

        System.out.println(cedula);

        int validador = 0;
        int[] mascara = {2, 1, 2, 1, 2, 1, 2, 1, 2};

        for(int i = 0; i < cedula.length() - 1; i++){
            if(mascara[i] * Integer.parseInt("" + cedula.charAt(i)) > 9)
                validador += (mascara[i] * Integer.parseInt("" + cedula.charAt(i))) - 9;

            else
                validador += mascara[i] * Integer.parseInt("" + cedula.charAt(i));

        }

        if(validador % 10 == 0 && cedula.charAt(9) == '0')
            return true;

        validador = 10 - (validador % 10);

        if(validador == Integer.parseInt("" + cedula.charAt(9)))
            return  true;

        return false;
    }

    public boolean validarRucSociedadesPrivadas(String ruc){
        if(Integer.parseInt(ruc.substring(0, 2)) < 0 || Integer.parseInt(ruc.substring(0, 2)) > 24)
            return false;

        int validador = 0;
        int[] mascara = {4, 3, 2, 7, 6, 5, 4, 3, 2};

        for(int i = 0; i < 9; i++){
            validador += Integer.parseInt("" + ruc.charAt(i)) * mascara[i];
        }

        if((validador % 11) == 0 && Integer.parseInt("" + ruc.charAt(9)) == 0)
            return true;

        validador = 11 - (validador % 11);

        if(validador == Integer.parseInt("" + ruc.charAt(9)))
            return true;

        return false;

    }

    public boolean validarRucSociedadesPublicas(String ruc){
        if(Integer.parseInt(ruc.substring(0, 2)) < 0 || Integer.parseInt(ruc.substring(0, 2)) > 24)
            return false;

        int validador = 0;
        int[] mascara = {3, 2, 7, 6, 5, 4, 3, 2};

        for(int i = 0; i < 8; i++){
            validador += Integer.parseInt("" + ruc.charAt(i)) * mascara[i];
        }

        if((validador % 11) == 0 && Integer.parseInt("" + ruc.charAt(8)) == 0)
            return true;

        validador = 11 - (validador % 11);

        if(validador == Integer.parseInt("" + ruc.charAt(8)))
            return true;

        return false;

    }


    @Override
    public int compareTo(Establecimiento o) {
        return  ruc.compareTo(o.getRuc());

    }
}
