package com.example.assignment;
import androidx.appcompat.app.AppCompatActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
public class MainActivity extends AppCompatActivity {
    Button btnStart, btnStop, btn_5;
    int counter = 0;
    static boolean destroy = false;
    int destroyed = 0;
    static Reciever1 b1 = new Reciever1();
    int uricounter =0;
    Reciever2 b2 = new Reciever2();
    Broadcast3 b3 = new Broadcast3();
    Broadcast4 b4 = new Broadcast4();
    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String s1 = intent.getStringExtra("title");
            String s2 = intent.getStringExtra("body");
            String s3 = intent.getStringExtra("counter");
            Bitmap bitmap= null;
            File f  = new File("/data/data/com.example.assignment/files/imageFile"+((Integer.parseInt(s3))%5));
            if (!f.exists())
                Toast.makeText(MainActivity.this, "There is no such file to read data. ", Toast.LENGTH_SHORT).show();
            try{
                FileInputStream buf = new FileInputStream(f);
                byte[] arr = new byte[(int) f.length()];
                buf.read(arr);
                bitmap = BitmapFactory.decodeByteArray(arr, 0, arr.length);
                buf.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(destroyed != 1){
                fragmentActivity homeFragment = (fragmentActivity) getSupportFragmentManager().findFragmentById(R.id.fragment);
                if(homeFragment != null) {
                    homeFragment.text.setText("News "+s3+": "+s1);
                    homeFragment.body.setText(s2);
                    homeFragment.imageView.setImageBitmap(bitmap);
                }
                Toast.makeText(MainActivity.this, "File "+s3+" downloaded", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.example.assignment");
        registerReceiver(broadcastReceiver, intentFilter);

        IntentFilter filter1 = new IntentFilter(Intent.ACTION_BATTERY_LOW);
        registerReceiver(b1, filter1);

        IntentFilter filter2 = new IntentFilter(Intent.ACTION_BATTERY_LOW);
        registerReceiver(b2, filter2);

        IntentFilter filter3 = new IntentFilter(Intent.ACTION_POWER_CONNECTED);
        registerReceiver(b3, filter3);

        IntentFilter filter4 = new IntentFilter(Intent.ACTION_POWER_DISCONNECTED);
        registerReceiver(b4, filter4);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        destroyed = 0;
        btnStart = findViewById(R.id.btnStart);
        btnStop = findViewById(R.id.btnStop);
        downloadService.context1 = this;
        btn_5 = findViewById(R.id.btn_5);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), MyService.class);
                try {
                    FileInputStream fin = openFileInput("counter");
                    counter = fin.read();
                    fin.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                intent.putExtra("counter", counter);
                startService(intent);
                btnStart.setEnabled(false);
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopService(new Intent(getBaseContext(), MyService.class));
                btnStart.setEnabled(true);
            }
        });

        btn_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(broadcastReceiver);
        unregisterReceiver(b1);
        unregisterReceiver(b2);
        unregisterReceiver(b3);
        unregisterReceiver(b4);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        destroyed =1;
        Toast.makeText(MainActivity.this, "Activity destroyed", Toast.LENGTH_SHORT).show();
        stopService(new Intent(getBaseContext(), MyService.class));
        Intent intent = new Intent(this, MyService.class);
        try {
            FileInputStream fin = openFileInput("counter");
            counter = fin.read();
            fin.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        intent.putExtra("counter", counter);
        startService(intent);

    }
}