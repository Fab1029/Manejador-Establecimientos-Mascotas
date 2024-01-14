package modulos.Mascota;

import modulos.ObserverPattern.Observer;
import modulos.ObserverPattern.Subject;

import javax.swing.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Mascota implements Comparable<Mascota>, Serializable, Subject {
    private int edad;
    private String sexo;
    private String raza;
    private String color;
    private String nombre;
    private int numeroChip;
    private String tipoMascota;
    private String pathFotoCarnet;
    private transient Observer controladorInformacionMedica;
    private LinkedList<InformacionMedica> informacionMedicas;

    public Mascota() {
    }

    public Mascota(int edad, String sexo, String raza, String color, String nombre, int numeroChip, String pathFotoCarnet, String tipoMascota, LinkedList<InformacionMedica> informacionMedicas) {
        this.edad = edad;
        this.sexo = sexo;
        this.raza = raza;
        this.color = color;
        this.nombre = nombre;
        this.numeroChip = numeroChip;
        this.tipoMascota = tipoMascota;
        this.pathFotoCarnet = pathFotoCarnet;
        this.informacionMedicas = informacionMedicas;
    }

    //Comienzo funciones y metodos

    public String getTipoMascota() {
        return tipoMascota;
    }

    public void setTipoMascota(String tipoMascota) {
        this.tipoMascota = tipoMascota;
    }

    public int getEdad() {
        return edad;
    }

    public String getRaza() {
        return raza;
    }

    public String getSexo() {
        return sexo;
    }

    public String getColor() {
        return color;
    }

    public String getNombre() {
        return nombre;
    }

    public int getNumeroChip() {
        return numeroChip;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getPathFotoCarnet() {
        return pathFotoCarnet;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setNumeroChip(int numeroChip) {
        this.numeroChip = numeroChip;
    }

    public List<InformacionMedica> getInformacionMedicas() {
        return informacionMedicas;
    }

    public InformacionMedica getInformacionMedica(String date, String descripcion){

        for(InformacionMedica informacionMedica : informacionMedicas){
            if(informacionMedica.getFecha().toString().equals(date) && informacionMedica.getDescripcion().equals(descripcion))
                return informacionMedica;
        }
        return null;
    }

    public void removeInformacionMedica(InformacionMedica informacionMedica){
        informacionMedicas.remove(informacionMedica);
    }


    public void setPathFotoCarnet(String pathFotoCarnet) {
        this.pathFotoCarnet = pathFotoCarnet;
    }

    public void addInformacionMedica(InformacionMedica informacionMedica) {
        informacionMedicas.add(informacionMedica);
    }

    @Override
    public int compareTo(Mascota o) {
        return Integer.compare(edad, o.edad);
    }

    @Override
    public void registrarObservador(Observer observador) {
        controladorInformacionMedica = observador;
    }

    @Override
    public void eliminarObservador(Observer observador) {

    }

    @Override
    public void notificarObservadores() {
        controladorInformacionMedica.actualizar(this);
    }
}
