package com.example.assignment_5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Random;

public class knn extends AppCompatActivity {
    MyDatabase database;
    TextView text;
    WifiManager mWifiManager;
    List<ScanResult> scanResultList;
    int[] arr = new int[4];
    List<Model> modelList;

    double diff = Integer.MAX_VALUE;
    int room = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_knn);
        text = findViewById(R.id.text_knn);
        database = MyDatabase.getInstance(this);
        try{
            mWifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            mWifiManager.startScan();
            scanResultList = mWifiManager.getScanResults();
            for(int i = 0 ; i<Math.min(scanResultList.size(), 4); i++){
                arr[i] = scanResultList.get(i).level;
            }
            modelList = database.dao().getList();

            Log.i("Size", "Size pf model"+String.valueOf(modelList.size()));
            for(int i = 0;i<modelList.size();i++) {
                int res = modelList.get(i).getRoom();
                int val1 = modelList.get(i).getValue1();
                int val2 = modelList.get(i).getValue2();
                int val3 = modelList.get(i).getValue3();
                int val4 = modelList.get(i).getValue4();

                double calc = Math.sqrt((arr[0] - val1) * (arr[0] - val1) + (arr[1] - val2) * (arr[1] - val2) + (arr[2] - val3) * (arr[2] - val3) + (arr[3] - val4) * (arr[3] - val4));
                if (calc < diff) {
                    room = res;
                    diff = calc;
                }


            }
            Random random = new Random();
            int i = random.nextInt(2);
            room = room - i;
            text.setText("You are in room "+ room);
        }
        catch (Exception e){
            Toast.makeText(getApplicationContext(), "Somethning went wrong", Toast.LENGTH_SHORT).show();
        }
    }
}