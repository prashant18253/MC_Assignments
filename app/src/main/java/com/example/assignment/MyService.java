package com.example.assignment;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MyService extends Service {
    downloadService task = null;
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int counter = intent.getIntExtra("counter", 0);
        Toast.makeText(this, "Service Started "+counter, Toast.LENGTH_SHORT).show();
        task = new downloadService(this);
        task.execute(counter);
        return START_STICKY;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        int counter = task.counter;
        Toast.makeText(this, "Service stopped", Toast.LENGTH_SHORT).show();
        try {
            FileOutputStream fOut = openFileOutput("counter",MODE_PRIVATE);
            fOut.write(counter);
            fOut.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(task!=null)
            task.cancel(true);
    }
}