package com.cam.trailrace.modelo;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Date;
import java.util.List;

/**
 * Created by nsanc on 13/09/2016.
 */
@IgnoreExtraProperties
public class Equipo {

    private String nombre;

    private String pais;

    private String categoria;

    private List<Piloto> pilotos;

    public Equipo() {
    }

    public Equipo(String nombre, String pais, String categoria) {
        this.nombre = nombre;
        this.pais = pais;
        this.categoria = categoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public List<Piloto> getPilotos() {
        return pilotos;
    }

    public void setPilotos(List<Piloto> pilotos) {
        this.pilotos = pilotos;
    }


}
