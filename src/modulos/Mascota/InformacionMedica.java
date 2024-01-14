package modulos.Mascota;

import java.io.Serializable;
import java.util.Date;

public class InformacionMedica implements Comparable<InformacionMedica>, Serializable {
    private Date fecha;
    private String tipo;
    private String descripcion;

    public InformacionMedica(){

    }
    public InformacionMedica(Date fecha, String tipo, String descripcion) {
        this.tipo = tipo;
        this.fecha = fecha;
        this.descripcion = descripcion;
    }

    //Comienzo funciones y metodos


    public Date getFecha() {
        return fecha;
    }

    public String getTipo() {
        return tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public int compareTo(InformacionMedica o) {
        return this.fecha.compareTo(o.fecha);
    }
}
