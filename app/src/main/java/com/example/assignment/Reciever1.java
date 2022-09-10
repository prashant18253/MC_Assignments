package com.example.assignment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Reciever1 extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        int counter =0;
        Toast.makeText(context, "Battery okay", Toast.LENGTH_SHORT).show();
        if(MainActivity.destroy){
           Intent intent1 = new Intent(context, MyService.class);
            try {
                FileInputStream fin = context.openFileInput("counter");
                counter = fin.read();
                fin.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            intent.putExtra("counter", counter);
            context.startService(intent);
        }

        return;
    }
}