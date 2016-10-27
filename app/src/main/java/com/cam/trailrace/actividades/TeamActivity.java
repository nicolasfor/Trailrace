package com.cam.trailrace.actividades;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.cam.trailrace.R;
import com.cam.trailrace.lib.FireBaseHelper;
import com.cam.trailrace.modelo.Equipo;
import com.cam.trailrace.navegacion.DataCommunication;
import com.cam.trailrace.navegacion.TeamFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.codehaus.jackson.map.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by nicolasfor on 26/10/2016.
 */
public class TeamActivity extends Activity {

    private EditText teamName;
    private EditText teamCountry;
    private Spinner dropDownTeamCategory;
    private static FirebaseDatabase database;
    private DatabaseReference helper;
    private TextView buttonSalvarTeam;

    private String mParam1;
    private String mParam2;
    private Handler mHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Log.e("ENTRA: ","TeamActivity");
        if(database==null){
            database = FirebaseDatabase.getInstance();
            }
        helper = database.getReference();
        mHandler = new Handler();
        teamName = (EditText) findViewById(R.id.teamName2);
        teamCountry = (EditText) findViewById(R.id.teamCountry2);
        dropDownTeamCategory = (Spinner) findViewById(R.id.spinnerCategory2);
        buttonSalvarTeam = (TextView) findViewById(R.id.buttonSaveTeam2);


        /*DatabaseReference fire=helper;
        fire.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.getValue() != null) {
                    Equipo equipo = snapshot.getValue(Equipo.class);
                    teamName.setText(equipo.getNombre());
                    teamCountry.setText(equipo.getPais());
                    SpinnerAdapter sp=dropDownTeamCategory.getAdapter();
                    String [] items= new String[7];
                    for (int i = 0; i < 7; i++) {
                        items[i]=(String)sp.getItem(i);
                    }
                    for (int i = 0; i < items.length; i++) {
                        if(equipo.getCategoria().equals(items[i])){
                            dropDownTeamCategory.setSelection(i);
                        }
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError firebaseError) {
            }
        });

        buttonSalvarTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarTeam();
            }
        });*/
        // Inflate the layout for this fragment
        buttonSalvarTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarTeam();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            // finish the activity
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void guardarTeam(){
        String teamNameStr= teamName.getText().toString();
        String teamCountryStr= teamCountry.getText().toString();
        String category= (String)dropDownTeamCategory.getSelectedItem();
        Equipo e= new Equipo(teamNameStr,teamCountryStr,category);
        Map<String, Object> map= new ObjectMapper().convertValue(e,Map.class);
        Log.e("ENTRA","Guarda mi perro");
        helper.updateChildren(map);

        helper.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("ENTRA:"," on restart");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("ENTRA:"," on start");
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.e("ENTRA:"," on resume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("ENTRA:"," on paused");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("ENTRA:"," on stop");
    }
}
