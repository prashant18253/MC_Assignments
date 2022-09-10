package com.example.assignment_4;

import android.app.UiModeManager;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.room.RoomDatabase;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class activityFragment extends Fragment{

    private SensorManager sensorManager;
    Sensor accelerometer, proximity, light, la, orientation, gyroscope, magnetic;
    Switch proximity_toggle, la_toggle, orientation_toggle, magnetic_toggle, gyroscope_toggle, light_toggle;
    TextView textView;
    MyDatabase database;
    Button gps, proxi, acce;
    View view;
    private UiModeManager uiModeManager;
    SensorEventListener proximity_listener, la_listener, orientation_listener, magnetic_listener, gyroscope_listener, light_listener, accelerometer_listener;
    Float Acurr, Alast, accele;
    float[] val, val1;
    boolean proximity_checked, la_checked, orientation_checked, gyroscope_checked, light_checked;
    public activityFragment() {
        // Required empty public constructor
    }
    public static activityFragment newInstance() {
        activityFragment fragment = new activityFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_activity, container, false);
        database = MyDatabase.getInstance(view.getContext());
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        uiModeManager = (UiModeManager) getActivity().getSystemService(Context.UI_MODE_SERVICE);
        Acurr =SensorManager.GRAVITY_EARTH;
        Alast = SensorManager.GRAVITY_EARTH;
        accele = 0.00f;
        la_toggle = (Switch) view.findViewById(R.id.toggleLA);
        light_toggle = (Switch) view.findViewById(R.id.toggleLight);
        orientation_toggle = (Switch) view.findViewById(R.id.toggleOrientation);
        proximity_toggle = (Switch) view.findViewById(R.id.toggleProximity);
        gyroscope_toggle = (Switch) view.findViewById(R.id.toggleGyroscope);
        magnetic_toggle = (Switch) view.findViewById(R.id.toggleMagnetic);
        textView = view.findViewById(R.id.textView);

        acce = view.findViewById(R.id.graph_acceleration);
        proxi = view.findViewById(R.id.graph_proximity);
        gps = view.findViewById(R.id.gps);

        acce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = ((FragmentActivity) view.getContext()).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.replace(R.id.la, new AcclerationFragment());
                fragmentTransaction.commit();
            }
        });
        proxi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = ((FragmentActivity) view.getContext()).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.replace(R.id.la, new ProximityFragment());
                fragmentTransaction.commit();
            }
        });
        gps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = ((FragmentActivity) view.getContext()).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.replace(R.id.la, new GPSFragment());
                fragmentTransaction.commit();
            }
        });


        la_toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(la_toggle.isChecked()){
                    la = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
                    if(la!=null) {
                        Toast.makeText(getActivity(), "La Sensor started", Toast.LENGTH_SHORT).show();
                        la_listener = new SensorEventListener() {
                            @Override
                            public void onSensorChanged(SensorEvent sensorEvent) {
                                Long timeInfo = System.currentTimeMillis();
                                ModelAcceleration data = new ModelAcceleration(timeInfo, sensorEvent.values[0], sensorEvent.values[1], sensorEvent.values[2]);
                                Log.d("Activty", ""+timeInfo);
                                database.acceleration_dao().insert(data);
                            }
                            @Override
                            public void onAccuracyChanged(Sensor sensor, int i) {

                            }
                        };
                        sensorManager.registerListener(la_listener, la, SensorManager.SENSOR_DELAY_NORMAL);
                    }
                    else{
                        Toast.makeText(getActivity(), "Sensor do not exist", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getActivity(), "Sensor stopped", Toast.LENGTH_SHORT).show();
                    sensorManager.unregisterListener(la_listener);
                }
            }
        });

        proximity_toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(proximity_toggle.isChecked()){
                    proximity = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
                    if(proximity!=null) {
                        Toast.makeText(getActivity(), "proximity Sensor started", Toast.LENGTH_SHORT).show();
                        proximity_listener = new SensorEventListener() {
                            @Override
                            public void onSensorChanged(SensorEvent sensorEvent) {
                                Long timeInfo = System.currentTimeMillis();
                                ModelProximity data = new ModelProximity(timeInfo, sensorEvent.values[0]);
                                database.proximity_dao().insert(data);
                            }
                            @Override
                            public void onAccuracyChanged(Sensor sensor, int i) {

                            }
                        };
                        sensorManager.registerListener(proximity_listener, proximity, SensorManager.SENSOR_DELAY_NORMAL);
                    }
                    else{
                        Toast.makeText(getActivity(), "Sensor do not exist", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getActivity(), "Sensor stopped", Toast.LENGTH_SHORT).show();
                    sensorManager.unregisterListener(proximity_listener);
                }
            }
        });

        orientation_toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(orientation_toggle.isChecked()){
                    orientation = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
                    if(orientation!=null) {
                        Toast.makeText(getActivity(), "orientation Sensor started", Toast.LENGTH_SHORT).show();
                        orientation_listener = new SensorEventListener() {
                            @Override
                            public void onSensorChanged(SensorEvent sensorEvent) {
                                Long timeInfo = System.currentTimeMillis();
                                ModelOrientation data = new ModelOrientation(timeInfo, sensorEvent.values[0], sensorEvent.values[1], sensorEvent.values[2]);
                                database.orientation_dao().insert(data);
                            }
                            @Override
                            public void onAccuracyChanged(Sensor sensor, int i) {

                            }
                        };
                        sensorManager.registerListener(orientation_listener, orientation, 5000000);
                    }
                    else{
                        Toast.makeText(getActivity(), "Sensor do not exist", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getActivity(), "Sensor stopped", Toast.LENGTH_SHORT).show();
                    sensorManager.unregisterListener(orientation_listener);
                }
            }
        });

        gyroscope_toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(gyroscope_toggle.isChecked()){
                    gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
                    if(gyroscope!=null) {
                        Toast.makeText(getActivity(), "gyroscope Sensor started", Toast.LENGTH_SHORT).show();
                        gyroscope_listener = new SensorEventListener() {
                            @Override
                            public void onSensorChanged(SensorEvent sensorEvent) {
                                Long timeInfo = System.currentTimeMillis();
                                ModelGryroscope data = new ModelGryroscope(timeInfo, sensorEvent.values[0], sensorEvent.values[1], sensorEvent.values[2]);
                                database.gyroscope_dao().insert(data);
                            }
                            @Override
                            public void onAccuracyChanged(Sensor sensor, int i) {

                            }
                        };
                        sensorManager.registerListener(gyroscope_listener, gyroscope,5000000);
                    }
                    else{
                        Toast.makeText(getActivity(), "Sensor do not exist", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getActivity(), "Sensor stopped", Toast.LENGTH_SHORT).show();
                    sensorManager.unregisterListener(gyroscope_listener);
                }
            }
        });

        light_toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(light_toggle.isChecked()){
                    light = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
                    if(light!=null) {
                        Toast.makeText(getActivity(), "light Sensor started", Toast.LENGTH_SHORT).show();
                        light_listener = new SensorEventListener() {
                            @Override
                            public void onSensorChanged(SensorEvent sensorEvent) {
                                float x = sensorEvent.values[0];
                                Long timeInfo = System.currentTimeMillis();
                                ModelLight data = new ModelLight(timeInfo, x);
                                database.light_dao().insert(data);
                                Log.d("Light", ""+x);
                                if(x<25){
                                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                                }
                                else{
                                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                                }
                            }
                            @Override
                            public void onAccuracyChanged(Sensor sensor, int i) {

                            }
                        };
                        sensorManager.registerListener(light_listener, light, SensorManager.SENSOR_DELAY_NORMAL);
                    }
                    else{
                        Toast.makeText(getActivity(), "light do not exist", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getActivity(), "Sensor stopped", Toast.LENGTH_SHORT).show();
                    sensorManager.unregisterListener(light_listener);
                }
            }
        });

        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        accelerometer_listener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                float x = sensorEvent.values[0];
                float y = sensorEvent.values[1];
                float z = sensorEvent.values[2];
                Alast = Acurr;
                Acurr = (float) Math.sqrt(x*x+y*y+z*z);
                float diff = Acurr - Alast;
                accele = accele * 0.9f + diff;
                if(accele > 1){
                    textView.setText("Motion detected");
                }
                else{
                    textView.setText("Stationary");
                }
            }
            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {
            }
        };
        magnetic = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        magnetic_listener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                 val = sensorEvent.values;

            }
            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {
            }
        };
        sensorManager.registerListener(magnetic_listener, magnetic, SensorManager.SENSOR_DELAY_NORMAL);
        magnetic_toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(magnetic_toggle.isChecked()){
                    Toast.makeText(getContext(), "Magnetic Sensor Started", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getContext(), "Magnetic Sensor Stopped", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        sensorManager.unregisterListener(accelerometer_listener);

    }

}