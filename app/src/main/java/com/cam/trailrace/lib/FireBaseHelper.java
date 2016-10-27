package com.cam.trailrace.lib;

/**
 * Created by NicolasForero on 22/10/16.
 */


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;


public class FireBaseHelper{

    private DatabaseReference dataReference;

    private static FirebaseDatabase mDatabase;

    private FirebaseAuth mAuth;

    private final static String USERS_PATH = "users";

    private static class SingletonHolder {
        private static final FireBaseHelper INSTANCE = new FireBaseHelper();
    }

    public static FireBaseHelper getInstance() {
        if (mDatabase == null) {
            mDatabase = FirebaseDatabase.getInstance();
            mDatabase.setPersistenceEnabled(true);
        }
        return SingletonHolder.INSTANCE;
    }

    public FireBaseHelper(){
        dataReference = FirebaseDatabase.getInstance().getReference();
        mAuth= FirebaseAuth.getInstance();
    }

    public DatabaseReference getDataReference() {
        return dataReference;
    }

    public FirebaseAuth getAuthentication() {
        return mAuth;
    }

    public DatabaseReference darUsuariosReferencia(){
        return  getDataReference().child(USERS_PATH);
    }

    public DatabaseReference darUsuarioLogueadoReferencia(){
        return darUsuariosReferencia().child(mAuth.getCurrentUser().getUid());
    }

    public DatabaseReference darPilotosReferencia(){
        return  darUsuarioLogueadoReferencia().child("pilotos");
    }

    public DatabaseReference darTeamReferencia(){
        return darUsuarioLogueadoReferencia().child("team");
    }

    public DatabaseReference darPistasReferencia(){
        return  getDataReference().child("pistas");
    }

    public String getAuthUserEmail() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String email = null;
        if (user != null) {
            email = user.getEmail();
        }
        return email;
    }

  public DatabaseReference getVueltaActualReference(){
      DatabaseReference fire=getDataReference().child("Carrera").child("PilotoActual").child("VueltaActual");
      return fire;
  }

  public DatabaseReference getMejorVueltaReference(){
      DatabaseReference fire=getDataReference().child("Carrera").child("PilotoActual").child("MejorVuelta");
        return fire;
  }

  public DatabaseReference getPistaActualReference(){
      DatabaseReference fire=getDataReference().child("Carrera").child("PilotoActual").child("PistaActual");
        return fire;
  }


}
