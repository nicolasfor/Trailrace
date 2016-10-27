package com.cam.trailrace.actividades;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.cam.trailrace.R;
import com.cam.trailrace.lib.FireBaseHelper;
import com.cam.trailrace.modelo.Usuario;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by NicolasForero on 10/09/16.
 */
public class SecondActivity extends MainActivity {


    private TextView signup;
    private TextView signin;
    private TextView fb;
    private TextView account;
    private EditText email;
    private EditText password;
    private EditText rePassword;
    private EditText user;

    private View rootView;

    FireBaseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity2_main);
        Log.e("ENTRA SECOND","--------------");
        rootView = findViewById(R.id.crearLayout);
        helper = FireBaseHelper.getInstance();
        signup = (TextView) findViewById(R.id.signup);
        signin = (TextView) findViewById(R.id.signin);
        fb = (TextView) findViewById(R.id.fb);
        account = (TextView) findViewById(R.id.account);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        rePassword = (EditText) findViewById(R.id.passwordConf);
        user = (EditText) findViewById(R.id.user);


        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();

            }
        });
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent it = new Intent(SecondActivity.this, MainActivity.class);
                startActivity(it);

            }
        });

    }

    public void registerUser() {
        final String emailTxt = email.getText().toString();
        final String userTxt = user.getText().toString();
        final String passwordTxt = password.getText().toString();
        final String rePasswordTxt = rePassword.getText().toString();

        if (!passwordTxt.equals(rePasswordTxt)) {
            Snackbar snackbar = Snackbar
                    .make(rootView, "Las contraseñas deben coincidir", Snackbar.LENGTH_LONG);
            snackbar.show();
            rePassword.setText("");
        }
        else if(passwordTxt.length()<6){
            Snackbar snackbar = Snackbar
                    .make(rootView, "Las contraseñas debe tener al menos 6 digitos", Snackbar.LENGTH_LONG);
            snackbar.show();
            password.setText("");
            rePassword.setText("");
        }

        else {
            Log.e("ERROR:","Entra");
            helper.getAuthentication().createUserWithEmailAndPassword(emailTxt, passwordTxt)
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Snackbar snackbar = Snackbar
                                    .make(rootView, "Usuario agregado exitosamente, por favor loguee", Snackbar.LENGTH_LONG);
                            snackbar.show();
                            Log.e("ERROR:","Usuario agregado");
                            email.setText("");
                            user.setText("");
                            password.setText("");
                            rePassword.setText("");
                            String uid = authResult.getUser().getUid();
                            Log.e("USERNAME: ",uid);
                            Map<String, String> map = new HashMap<String, String>();
                            map.put("name", userTxt);
                            helper.darUsuariosReferencia().child(uid).setValue(map);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Snackbar snackbar = Snackbar
                                    .make(rootView, "No se pudo crear en la DB", Snackbar.LENGTH_LONG);
                            snackbar.show();
                            Log.e("ERROR:","Usuario no agregado");
                        }
                    });


        }
    }

}