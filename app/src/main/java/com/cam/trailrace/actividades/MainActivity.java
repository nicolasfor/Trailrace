package com.cam.trailrace.actividades;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cam.trailrace.R;
import com.cam.trailrace.lib.FireBaseHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;


import customfonts.MyRegulerText;

public class MainActivity extends AppCompatActivity {


    private TextView signup;
    private TextView signin;
    private TextView fb;
    private TextView account;
    private EditText email;
    private EditText password;
    private MyRegulerText botonLoguear;

    private View rootView;

    private FireBaseHelper helper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        helper = FireBaseHelper.getInstance();
        Log.e("ENTRA MAIN","--------------");
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {

            helper.getDataReference().addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Intent it = new Intent(MainActivity.this, NavigationMainActivity.class);
                    String uid = user.getUid();
                    it.putExtra("name", ((String) dataSnapshot.child("users").child(uid).child("name").getValue()));
                    it.putExtra("ID", uid);
                    startActivity(it);
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w("TAG", "Failed to read value.", error.toException());
                }
            });

        } else {
            setContentView(R.layout.activity_main);
            rootView = findViewById(R.id.loguearLayout);
            signup = (TextView) findViewById(R.id.signup);
            signin = (TextView) findViewById(R.id.signin);
            botonLoguear = (MyRegulerText) findViewById(R.id.buttonsignin);
            fb = (TextView) findViewById(R.id.fb);
            account = (TextView) findViewById(R.id.account);
            email = (EditText) findViewById(R.id.email);
            password = (EditText) findViewById(R.id.password);


            botonLoguear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String user = email.getText().toString();
                    String pass = password.getText().toString();
                    autenticarUsuario(user, pass);

                }
            });

            signup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent it = new Intent(MainActivity.this, SecondActivity.class);
                    startActivity(it);
                }
            });
        }
    }

    public void autenticarUsuario(String user, final String pass) {

        helper.getAuthentication().signInWithEmailAndPassword(user, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull final Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Snackbar snackbar = Snackbar
                                    .make(rootView, "Usuario o contraseña incorrectos", Snackbar.LENGTH_LONG);
                            snackbar.show();

                        } else {

                            helper.getDataReference().addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    Intent it = new Intent(MainActivity.this, NavigationMainActivity.class);
                                    String uid = task.getResult().getUser().getUid();
                                    it.putExtra("name", ((String) dataSnapshot.child("users").child(uid).child("name").getValue()));
                                    it.putExtra("ID", uid);
                                    startActivity(it);
                                }

                                @Override
                                public void onCancelled(DatabaseError error) {
                                    // Failed to read value
                                    Log.w("TAG", "Failed to read value.", error.toException());
                                }
                            });


                        }
                    }
                });


    }
}

