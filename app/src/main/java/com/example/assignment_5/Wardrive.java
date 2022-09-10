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

public class Wardrive extends AppCompatActivity {

    TextView textView;
    Button button;
    EditText editText, xaxis, yaxis;
    String str;
    Integer number, x_ax, y_ax;
    MyDatabase database;
    WifiManager mWifiManager;
    List<ScanResult> scanResultList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wardrive);
        database = MyDatabase.getInstance(this);
        textView = findViewById(R.id.textView);
        button = findViewById(R.id.button);
        editText = findViewById(R.id.editText);
        xaxis = findViewById(R.id.xaxis);
        yaxis = findViewById(R.id.yaxis);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                str =editText.getText().toString();
                String x = xaxis.getText().toString();
                String y = yaxis.getText().toString();

                try {
                    number = Integer.parseInt(str.trim());
                    if(x.length()==0 && y.length()==0 || x==null || y==null)
                    {
                        x_ax=0;
                        y_ax=0;
                    }
                    else{
                        x_ax = Integer.parseInt(x.trim());
                        y_ax = Integer.parseInt(y.trim());
                    }

                    Log.i("blsjdnksf", String.valueOf(number));
                    mWifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                    mWifiManager.startScan();
                    scanResultList = mWifiManager.getScanResults();
                    Model data = new Model();
                    data.setRoom(number);
//                    data.setValue5(x_ax);
//                    data.setValue6(y_ax);
                    Log.i("blsjdnksf", "Scan result size"+String.valueOf(scanResultList.size()));
                    if(scanResultList.size()>1)
                        data.setValue1(scanResultList.get(0).level);
                    if(scanResultList.size()>2)
                        data.setValue2(scanResultList.get(1).level);
                    if(scanResultList.size()>3)
                        data.setValue3(scanResultList.get(2).level);
                    if(scanResultList.size()>4)
                        data.setValue4(scanResultList.get(3).level);
                    database.dao().insert(data);
                    Log.i("blsjdnksf", "Scan result size"+String.valueOf(scanResultList.size()));
                    Toast.makeText(getApplicationContext(), "Data Collected Successfully", Toast.LENGTH_SHORT).show();
                }
                catch(Exception e){
                    //
                    Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}