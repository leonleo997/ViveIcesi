package co.highusoft.viveicesi.model;

import java.util.ArrayList;

public class Actividad {
    private String nombre;
    private String descripcion;
    private String img;

    private ArrayList<Horario> horarios;
//private ArrayList<Encuesta> encuestas;
    public Actividad() {

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public ArrayList<Horario> getHorarios() {
        return horarios;
    }

    public void setHorarios(ArrayList<Horario> horarios) {
        this.horarios = horarios;
    }
}
