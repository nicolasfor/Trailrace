package com.cam.trailrace.modelo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by nsanc on 13/09/2016.
 */
public class Sesion {

    private List<Vuelta> vueltas;

    private Vuelta mejorVuelta;

    private Pista pista;

    public Sesion() {
        vueltas= new ArrayList<>();
    }



    public Vuelta getMejorVuelta() {
        return mejorVuelta;
    }

    public void setMejorVuelta(Vuelta mejorVuelta) {
        this.mejorVuelta = mejorVuelta;
    }

    public List<Vuelta> getVueltas() {
        return vueltas;
    }

    public void setVueltas(List<Vuelta> vueltas) {
        this.vueltas = vueltas;
    }

    public Pista getPista() {
        return pista;
    }

    public void setPista(Pista pista) {
        this.pista = pista;
    }


}
