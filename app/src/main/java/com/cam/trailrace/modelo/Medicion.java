package com.cam.trailrace.modelo;

/**
 * Created by nsanc on 13/09/2016.
 */
public class Medicion {

    private String latitud;

    private String longitud;

    private String velocidad;

    private String distanciaRecorrida;

    private String tiempo;

    public Medicion() {
    }

    public Medicion(String latitud, String longitud, String distanciaRecorrida, String tiempo, String velocidad){
        this.latitud=latitud;
        this.longitud=longitud;
        this.distanciaRecorrida=distanciaRecorrida;
        this.tiempo=tiempo;
        this.velocidad=velocidad;
    }

    public String getTiempo() {
        return tiempo;
    }

    public void setTiempo(String tiempo) {
        this.tiempo = tiempo;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(String velocidad) {
        this.velocidad = velocidad;
    }

    public String getDistanciaRecorrida() {
        return distanciaRecorrida;
    }

    public void setDistanciaRecorrida(String distanciaRecorrida) {
        this.distanciaRecorrida = distanciaRecorrida;
    }

}
