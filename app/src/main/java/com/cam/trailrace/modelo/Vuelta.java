package com.cam.trailrace.modelo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by nsanc on 13/09/2016.
 */
public class Vuelta {

    private List<Medicion> mediciones;

    private Vehiculo vehiculo;

    private String tiempoRecorrido;

    public Vuelta() {
        mediciones=new ArrayList<>();
    }

    public String getTiempoRecorrido() {
        return tiempoRecorrido;
    }

    public void setTiempoRecorrido(String tiempoRecorrido) {
        this.tiempoRecorrido = tiempoRecorrido;
    }

    public List<Medicion> getMediciones() {
        return mediciones;
    }

    public void setMediciones(List<Medicion> mediciones) {
        this.mediciones = mediciones;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }


}
