package com.example.assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity2 extends AppCompatActivity {
    Button next;
    TextView title, body;
    ImageView image;
    int i =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        next = findViewById(R.id.buttons);
        image = findViewById(R.id.image1);
        title = findViewById(R.id.title1);
        body = findViewById(R.id.body1);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File f = new File("/data/data/com.example.assignment/files/imageFile" + i);
                if (!f.exists()) {
                    next.setEnabled(false);
                    return;
                }

                try {
                    String string = "";
                    StringBuilder stringBuilder = new StringBuilder();
                    InputStream is = openFileInput("titleFile" + i);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                    while (true) {
                        try {
                            if ((string = reader.readLine()) == null) break;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        stringBuilder.append(string).append("\n");
                    }
                    title.setText(stringBuilder);
                } catch (Exception e) {

                }
                try {
                    String string = "";
                    StringBuilder stringBuilder = new StringBuilder();
                    InputStream is = openFileInput("bodyFile" + i);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                    while (true) {
                        try {
                            if ((string = reader.readLine()) == null) break;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        stringBuilder.append(string).append("\n");
                    }
                    body.setText(stringBuilder);
                } catch (Exception e) {

                }

                Bitmap bitmap = null;
                try {
                    FileInputStream buf = new FileInputStream(f);
                    byte[] arr = new byte[(int) f.length()];
                    buf.read(arr);
                    bitmap = BitmapFactory.decodeByteArray(arr, 0, arr.length);
                    buf.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                image.setImageBitmap(bitmap);
                i++;
            }
        });
    }
}