package com.cam.trailrace.navegacion;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.cam.trailrace.R;
import com.cam.trailrace.actividades.NavigationMainActivity;
import com.cam.trailrace.lib.FireBaseHelper;
import com.cam.trailrace.modelo.Piloto;
import com.cam.trailrace.modelo.PilotoItem;
import com.cam.trailrace.modelo.Sesion;
import com.cam.trailrace.modelo.Vehiculo;
import com.google.firebase.database.DatabaseReference;


import org.codehaus.jackson.map.ObjectMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by NicolasForero on 12/09/16.
 */
public class PilotFragment extends Fragment {

    private Spinner spinner1;

    private DataCommunication mCallback;

    private EditText pilotName;
    private EditText pilotCountry;
    private EditText pilotEmail;
    private EditText pilotAge;
    private EditText pilotPhone;
    private EditText carName;
    private EditText carBrand;
    private EditText carModel;
    private EditText carMotor;
    private EditText carMaxTorque;
    private EditText carMaxPower;
    private EditText carMaxRPM;
    private EditText carMaxSpeed;

    private TextView buttonSalvar;
    private Spinner dropDownTypeCar;

    private FireBaseHelper helper;

    public PilotFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_pilot, container, false);
        helper = FireBaseHelper.getInstance();
        pilotName = (EditText) rootView.findViewById(R.id.pilotName);
        pilotCountry = (EditText) rootView.findViewById(R.id.pilotCountry);
        pilotEmail = (EditText) rootView.findViewById(R.id.pilotEmail);
        pilotAge = (EditText) rootView.findViewById(R.id.pilotAge);
        pilotPhone = (EditText) rootView.findViewById(R.id.pilotPhone);
        carName = (EditText) rootView.findViewById(R.id.carName);
        carBrand = (EditText) rootView.findViewById(R.id.carBrand);
        carModel = (EditText) rootView.findViewById(R.id.carModel);
        carMotor = (EditText) rootView.findViewById(R.id.carMotor);
        carMaxTorque = (EditText) rootView.findViewById(R.id.carTorque);
        carMaxPower = (EditText) rootView.findViewById(R.id.carMaxPower);
        carMaxRPM = (EditText) rootView.findViewById(R.id.carMaxRPM);
        carMaxSpeed = (EditText) rootView.findViewById(R.id.carMaxSpeed);

        buttonSalvar = (TextView) rootView.findViewById(R.id.buttonSavePilot);
        dropDownTypeCar = (Spinner) rootView.findViewById(R.id.spinnerTypeCar);
        try {
            mCallback = (DataCommunication) getActivity();
        } catch (Exception e) {

            e.printStackTrace();
        }
        getActivity().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        buttonSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardar();
            }
        });
        PilotoItem pilotoActual = mCallback.darPilotoAEditar();
        if (pilotoActual != null) {
            pilotName.setText(pilotoActual.getPiloto().getNombre());
            pilotCountry.setText(pilotoActual.getPiloto().getPais());
            pilotEmail.setText(pilotoActual.getPiloto().getCorreo());
            pilotAge.setText(pilotoActual.getPiloto().getEdad());
            pilotPhone.setText(pilotoActual.getPiloto().getTelefono());
            SpinnerAdapter sp=dropDownTypeCar.getAdapter();
            Vehiculo v = pilotoActual.getPiloto().getVehiculos().get(0);
            String [] items= new String[10];
            for (int i = 0; i < 10; i++) {
                items[i]=(String)sp.getItem(i);
            }
            for (int i = 0; i < items.length; i++) {
                if(v.getTipo().equals(items[i])){
                    dropDownTypeCar.setSelection(i);
                }
            }
            carName.setText(v.getNombre());
            carBrand.setText(v.getMarca());
            carModel.setText(v.getModelo());
            carMotor.setText(v.getMotor());
            carMaxTorque.setText(v.getTorqueMaximo());
            carMaxPower.setText(v.getPotenciaMaxima());
            carMaxRPM.setText(v.getRpmMaxima());
            carMaxSpeed.setText(v.getVelocidadMaxima());
        }

        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    public void guardar() {


        String pilotNameStr = pilotName.getText().toString();
        String pilotCountryStr = pilotCountry.getText().toString();
        String pilotEmailStr = pilotEmail.getText().toString();
        String pilotAgeStr = pilotAge.getText().toString();
        String pilotPhoneStr = pilotPhone.getText().toString();
        String carNameStr = carName.getText().toString();
        String carBrandStr = carBrand.getText().toString();
        String carModelStr = carModel.getText().toString();
        String carMotorStr = carMotor.getText().toString();
        String carMaxTorqueStr = carMaxTorque.getText().toString();
        String carMaxPowerStr = carMaxPower.getText().toString();
        String carMaxRPMStr = carMaxRPM.getText().toString();
        String carMaxSpeedStr = carMaxSpeed.getText().toString();
        int posicion = dropDownTypeCar.getSelectedItemPosition();
        String[] some_array = getResources().getStringArray(R.array.type_cars_arrays);
        String type = some_array[posicion];
        Vehiculo vehiculo = new Vehiculo(carBrandStr, carModelStr, carMotorStr, carNameStr, type, carMaxPowerStr, carMaxRPMStr, carMaxTorqueStr, carMaxSpeedStr);
        List<Vehiculo> vehiculoList = new ArrayList<>();
        Map<String,Sesion> sesiones= null;
        vehiculoList.add(vehiculo);
        Piloto p = new Piloto(pilotNameStr, pilotEmailStr, pilotCountryStr, pilotAgeStr, pilotPhoneStr, vehiculoList,sesiones);
        String currentUser = mCallback.darCurrentUser();

        if (mCallback.darPilotoAEditar() == null) {
            DatabaseReference nueva = helper.darPilotosReferencia().push();
            nueva.setValue(p);
        }
        else {
            String idPiloto=mCallback.darPilotoAEditar().getPilotoId();
            DatabaseReference nueva =helper.darPilotosReferencia().child(idPiloto);
            Map<String, Object> nickname = new ObjectMapper().convertValue(p,Map.class);
            nueva.updateChildren(nickname);
        }
        FragmentManager fm = getFragmentManager();
        fm.beginTransaction().replace(R.id.frame, new PilotListFragment()).commit();
    }

}
