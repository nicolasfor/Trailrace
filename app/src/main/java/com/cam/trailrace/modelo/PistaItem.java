package com.cam.trailrace.modelo;

import com.google.android.gms.maps.model.Polyline;

/**
 * Created by NicolasForero on 16/09/16.
 */
public class PistaItem {
    private boolean showNotify;
    private Pista pista;

    public PistaItem() {
        showNotify=false;
    }

    public PistaItem(Pista pista) {
        this.showNotify = true;
        this.pista = pista;
    }

    public boolean isShowNotify() {
        return showNotify;
    }

    public void setShowNotify(boolean showNotify) {
        this.showNotify = showNotify;
    }

    public Pista getPista() {
        return pista;
    }

    public void setPista(Pista pista) {
        this.pista = pista;
    }
}
