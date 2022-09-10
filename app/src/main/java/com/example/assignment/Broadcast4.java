package com.example.assignment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Broadcast4 extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        int counter =0;
        Toast.makeText(context, "Power disconnected", Toast.LENGTH_SHORT).show();
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
            intent1.putExtra("counter", counter);
            context.startService(intent1);

        return;
    }
}