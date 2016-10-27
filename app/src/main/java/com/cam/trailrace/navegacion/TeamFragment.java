package com.cam.trailrace.navegacion;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.cam.trailrace.R;
import com.cam.trailrace.adapter.PilotListAdapter;
import com.cam.trailrace.lib.FireBaseHelper;
import com.cam.trailrace.modelo.Equipo;
import com.cam.trailrace.modelo.PilotoItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;

/**
 * Created by NicolasForero on 12/09/16.
 */
public class TeamFragment extends Fragment implements View.OnClickListener{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private DataCommunication mCallback;

    private EditText teamName;
    private EditText teamCountry;
    private Spinner dropDownTeamCategory;
    private FireBaseHelper helper;

    private TextView buttonSalvarTeam;

    private String mParam1;
    private String mParam2;
    private Handler mHandler;


    private OnFragmentInteractionListener mListener;

    public TeamFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    public static TeamFragment newInstance(String param1, String param2){
        TeamFragment fragment= new TeamFragment();
        Bundle args= new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_team, container, false);
        this.setRetainInstance(true);
        helper = FireBaseHelper.getInstance();
        mHandler = new Handler();
        teamName = (EditText) rootView.findViewById(R.id.teamName);
        teamCountry = (EditText) rootView.findViewById(R.id.teamCountry);
        dropDownTeamCategory = (Spinner) rootView.findViewById(R.id.spinnerCategory);
        buttonSalvarTeam = (TextView) rootView.findViewById(R.id.buttonSaveTeam);

        try {
            mCallback = (DataCommunication) getActivity();
        } catch (Exception e) {

            e.printStackTrace();
        }
        DatabaseReference fire=helper.darTeamReferencia();
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

       /* buttonSalvarTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarTeam();
            }
        });*/
        buttonSalvarTeam.setOnClickListener(this);
        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonSaveTeam:
            guardarTeam();
        }
    }
    public void guardarTeam(){
        String teamNameStr= teamName.getText().toString();
        String teamCountryStr= teamCountry.getText().toString();
        String category= (String)dropDownTeamCategory.getSelectedItem();
        final Equipo e= new Equipo(teamNameStr,teamCountryStr,category);
        Log.e("ESTADO:","guardar");

        helper.darTeamReferencia().setValue(e);

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
