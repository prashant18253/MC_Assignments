package com.example.assignment_4;

import static com.example.assignment_4.MyDatabase.myDatabase;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.ColorSpace;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.transition.Visibility;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class GPSFragment extends Fragment{
    Button addPlace, findPlace, submit;
    TextView tv1, tv2, tv3;
    EditText name , addresses;
    LocationManager locationManager;
    LocationListener locationListener;
    LinearLayout layout1, layout2;
    MyDatabase database;
    FusedLocationProviderClient fusedLocationProviderClient;
    double latitue, longitude;
    String addr;

    public GPSFragment() {
        // Required empty public constructor
    }

    public static GPSFragment newInstance() {
        GPSFragment fragment = new GPSFragment();
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
        View view = inflater.inflate(R.layout.fragment_g_p_s, container, false);
        addPlace = view.findViewById(R.id.addPlace);
        findPlace = view.findViewById(R.id.findPalces);
        submit = view.findViewById(R.id.submit);

        tv1 = view.findViewById(R.id.tv1);
        tv2 = view.findViewById(R.id.tv2);
        tv3 = view.findViewById(R.id.tv3);

        name = view.findViewById(R.id.name);
        addresses = view.findViewById(R.id.address);

        layout1 = view.findViewById(R.id.layout1);
        layout2 = view.findViewById(R.id.layout2);

        layout1.setVisibility(View.INVISIBLE);
        layout2.setVisibility(View.INVISIBLE);

        database = MyDatabase.getInstance(view.getContext());
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());

//        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
//                && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 100);
//        }
//
//        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
//        boolean  isGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
//        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
//            @Override
//            public void onComplete(@NonNull Task<android.location.Location> task) {
//                android.location.Location location  = task.getResult();
//                if(location!=null){
//                    Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
//                    try{
//                        List<Address> address = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
//                        latitue = address.get(0).getLatitude();
//                        longitude = address.get(0).getLongitude();
//                        addr = address.get(0).getAddressLine(0);
//                        addresses.setText(addr);
//                        Toast.makeText(getContext(),""+latitue+" "+longitude, Toast.LENGTH_SHORT).show();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });

        addPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout1.setVisibility(View.VISIBLE);
                layout2.setVisibility(View.INVISIBLE);
                getCurrentLocation();
            }

        });

        findPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout1.setVisibility(View.INVISIBLE);
                layout2.setVisibility(View.VISIBLE);
                getCurrentLocation();
                List<ModelGPS> values = database.gps_dao().getListItems();
                Log.d("Actt", ""+values.size());
                for(int i = 0;i<values.size();i++){
                    String s = ""+values.get(i).getLatitude()+"\n"+values.get(i).getLongitude()+"\n"+values.get(i).getAddress()+"\n"+values.get(i).getName();
                    if(i==0){
                        tv1.setText(s);
                    }
                    if(i==1)
                        tv2.setText(s);
                    if(i==2)
                        tv3.setText(s);
                }
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String x = name.getText().toString();
                Toast.makeText(getActivity(), "Submitted", Toast.LENGTH_SHORT).show();
                ModelGPS model = new ModelGPS();
                model.setName(x);
                model.setLatitude(latitue);
                model.setLongitude(longitude);
                model.setAddress(addr);
                database.gps_dao().insert(model);
            }
        });

        return view;
    }
    public void getCurrentLocation(){
        try{
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
                boolean isGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                if (!isGPS) {
                    Toast.makeText(getContext(), "Enable GPS", Toast.LENGTH_SHORT).show();
                    //getCurrentLocation();
                }
                fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<android.location.Location> task) {
                        android.location.Location location = task.getResult();
                        if (location != null) {
                            Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
                            try {
                                List<Address> address = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                                latitue = address.get(0).getLatitude();
                                longitude = address.get(0).getLongitude();
                                addr = address.get(0).getAddressLine(0);
                                addresses.setText(addr);
                                //Toast.makeText(getContext(), "" + latitue + " " + longitude, Toast.LENGTH_SHORT).show();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
            else{
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 100);
            }
        }
        catch(Exception e ){

        }


    }


}