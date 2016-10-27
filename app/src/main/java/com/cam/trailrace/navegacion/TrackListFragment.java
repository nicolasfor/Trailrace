package com.cam.trailrace.navegacion;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.cam.trailrace.R;
import com.cam.trailrace.actividades.NavigationMainActivity;
import com.cam.trailrace.adapter.TrackListAdapter;
import com.cam.trailrace.lib.FireBaseHelper;
import com.cam.trailrace.modelo.Pista;
import com.cam.trailrace.modelo.PistaItem;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by nicolasfor on 16/09/2016.
 */
public class TrackListFragment extends Fragment {

    private ListView lista;
    private FragmentActivity fa;
    private ArrayList<Pista> pistasCreadas;
    private ArrayList<PistaItem> pistasItem;
    private TextView agregar;
    private DataCommunication mCallback;
    private  View rootView;
    private FireBaseHelper helper;
    public TrackListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fa = super.getActivity();
        rootView = inflater.inflate(R.layout.fragment_track_list, container, false);
        helper = FireBaseHelper.getInstance();
        try {
            mCallback = (DataCommunication) getActivity();
        } catch (Exception e) {

            e.printStackTrace();
        }
        lista= (ListView)rootView.findViewById(R.id.track_list);

        Intent intent = fa.getIntent();
        //pistasCreadas= ((NavigationMainActivity)super.getActivity()).darPistas();
        pistasCreadas=new ArrayList<>();
        pistasItem= new ArrayList<>();
        DatabaseReference nueva=helper.darPistasReferencia();
        nueva.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.getValue() == null) {
                        ArrayList<PistaItem> pistas= new ArrayList<>();
                        pistas.add(new PistaItem());
                        TrackListAdapter m_adapter = new TrackListAdapter(getActivity().getBaseContext(), R.layout.list_item, pistas);
                        lista.setAdapter(m_adapter);
                }
            }
            @Override
            public void onCancelled(DatabaseError firebaseError) {
            }
        });
        nueva.addChildEventListener(
                new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        Pista pis = dataSnapshot.getValue(Pista.class);
                        pistasCreadas.add(pis);
                        PistaItem pI= new PistaItem(pis);
                        pistasItem.add(pI);

                        if(pistasItem.size()>0) {

                            mCallback.agregarPista(pistasCreadas);

                            TrackListAdapter m_adapter = new TrackListAdapter(rootView.getContext(), R.layout.list_item, pistasItem);
                            lista.setAdapter(m_adapter);

                            lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView adapter, View view, int position, long arg) {
                                    FragmentManager fm = getFragmentManager();
                                    mCallback.cambiarPosicion(position);
                                    fm.beginTransaction().replace(R.id.frame, new StartFragment()).commit();
                                    ((NavigationMainActivity) getActivity()).getSupportActionBar().setTitle("Start");
                                }
                            });

                        }
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError firebaseError) {

                    }
                }
        );

        agregar = (TextView) rootView.findViewById(R.id.buttonAgregarLista);

        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                fm.beginTransaction().replace(R.id.frame, new TrackFragment()).commit();
            }
        });
        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}