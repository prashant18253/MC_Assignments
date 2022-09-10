package com.example.assignment_5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class imu extends AppCompatActivity {

    Button button;
    TextView txtView;
    EditText editText1, editText2;

    MyDatabase database;
    WifiManager mWifiManager;
    List<ScanResult> scanResultList;
    int[] arr = new int[4];
    List<Model> modelList;
    double diff = Integer.MAX_VALUE;
    int room = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imu);

        button = findViewById(R.id.button2);
        txtView = findViewById(R.id.textView2);
        editText1 = findViewById(R.id.EnterX);
        editText2 = findViewById(R.id.EnterY);
        database = MyDatabase.getInstance(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    int x = Integer.parseInt(editText1.getText().toString().trim());
                    int y = Integer.parseInt(editText2.getText().toString().trim());
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
//                        int val5 = modelList.get(i).getX();
//                        int val6 = modelList.get(i).getY();
                        double calc = Math.sqrt((arr[0] - val1) * (arr[0] - val1) + (arr[1] - val2) * (arr[1] - val2) + (arr[2] - val3) * (arr[2] - val3) + (arr[3] - val4) * (arr[3] - val4)+(x)*(x)+(y)*(y));
                        //double calc = 0;
                        if (calc < diff) {
                            room = res;
                            diff = calc;
                        }

                    }
                    txtView.setText("You are in room "+ room);

                }
                catch(Exception e){
                    Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}