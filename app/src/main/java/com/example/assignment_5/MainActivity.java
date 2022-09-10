package com.example.assignment_5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    class WifiReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

        }
    }
    ListView listView;
    WifiManager mWifiManager;
    List<ScanResult> wifiLists;

    int Rssi;
    String Rssi_value;
    ArrayList<String> listrssi;
    WifiReceiver mWifiReceiver;
    ArrayList<String> SSID;
    ArrayList<Integer> level;
    WifiListAdapter wifiListAdapter;

    Button scanWifi, locate, wardrive, knn, imu;
    TextView txtStep, txtDirection, stride;

    private double acc_prev = 0;
    private float[] acc_val = new float[3];
    private float[] mag_val = new float[3];

    private float[] rotationMatrix = new float[9];
    private float[] orientationAngle = new float[3];
    private int steps = 0;

    @SuppressLint("WifiManagerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scanWifi = findViewById(R.id.scanWifi);
        locate = findViewById(R.id.locate);
        wardrive = findViewById(R.id.wardrive);
        txtStep = findViewById(R.id.steps);
        txtDirection = findViewById(R.id.direction);
        listView = findViewById(R.id.wifiList);
        stride = findViewById(R.id.stride);
        knn = findViewById(R.id.knn);
        imu = findViewById(R.id.imu);
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        Sensor sensor1 = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        stride.setText("Stride length is: "+(0.43*180));
        scanWifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listrssi = new ArrayList<>();
                SSID = new ArrayList<String>();
                level = new ArrayList<Integer>();
                mWifiManager = (WifiManager)getSystemService(Context.WIFI_SERVICE);
                mWifiReceiver = new WifiReceiver();

                IntentFilter intentFilter = new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
                registerReceiver(mWifiReceiver,  intentFilter);

                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_WIFI_STATE) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.ACCESS_WIFI_STATE},0);
                }
                else if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},0);
                }
                else{
                    mWifiManager.startScan();
                    wifiLists = mWifiManager.getScanResults();
                    for(int i = 0;i<wifiLists.size();i++){
                        SSID.add(wifiLists.get(i).SSID);
                        level.add(wifiLists.get(i).level);
                    }
                    wifiListAdapter  = new WifiListAdapter(SSID, level, getApplicationContext());
                    listView.setAdapter(wifiListAdapter);
                }

            }
        });

        wardrive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),Wardrive.class);
                intent.putExtra("SSID",SSID);
                intent.putExtra("level",level);
                startActivity(intent);

            }
        });
        locate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), locate.class);
                startActivity(intent);
            }
        });
        knn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), knn.class);
                startActivity(intent);
            }
        });
        imu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), imu.class);
                startActivity(intent);
            }
        });
        SensorEventListener sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                if(sensorEvent!=null){
                    acc_val = sensorEvent.values;
                    float x_acc = acc_val[0];
                    float y_acc = acc_val[1];
                    float z_acc = acc_val[2];

                    double acc_curr = Math.sqrt(x_acc*x_acc + y_acc*y_acc + z_acc*z_acc);
                    double change = acc_curr - acc_prev;
                    acc_prev = acc_curr;
                    if(change > 2){
                        steps++;
                        txtStep.setText(steps+" Steps");
                    }
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };
        sensorManager.registerListener(sensorEventListener, sensor, SensorManager.SENSOR_DELAY_NORMAL,SensorManager.SENSOR_DELAY_UI);

        SensorEventListener sensorEventListener1 = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                if(sensorEvent!=null){
                    mag_val = sensorEvent.values;
                    String s ="";
                    try{
                        SensorManager.getRotationMatrix(rotationMatrix, null,acc_val, mag_val);
                        SensorManager.getOrientation(rotationMatrix, orientationAngle);
                        double azimuth = 180 * orientationAngle[0] / Math.PI;
                        double pitch = 180 * orientationAngle[1] / Math.PI;
                        double roll = 180 * orientationAngle[2] / Math.PI;
                        if(Math.abs(azimuth)<= 45)
                            s = "North";
                        else if(azimuth>=45 && azimuth<=135)
                            s = "East";
                        else if(Math.abs(azimuth)>=135)
                            s ="South";
                        else if (Math.abs(azimuth)>=45 && Math.abs(azimuth)<=135)
                            s = "West";

                        txtDirection.setText(s);
                    }
                    catch(Exception e){

                    }


                    // "rotationMatrix" now has up-to-date information.


                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };
        sensorManager.registerListener(sensorEventListener1, sensor1, SensorManager.SENSOR_DELAY_NORMAL,SensorManager.SENSOR_DELAY_UI);
    }
}