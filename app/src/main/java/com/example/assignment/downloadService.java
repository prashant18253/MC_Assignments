package com.example.assignment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import org.json.JSONObject;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
public class downloadService extends AsyncTask<Integer, Object, Object> {

    private Context context;
    static Context context1;
    static int counter=0;

    downloadService(Context context){
        this.context = context;
    }
    @Override
    protected Object doInBackground(Integer... integers) {
        String body;
        String title;
        Bitmap Image = null;
        String encodedImage = null;
        counter = integers[0];
        while(!isCancelled()){
            HttpHandler sh = new HttpHandler();
            String url = "https://petwear.in/mc2022/news/news_"+counter+".json";
            String jsonStr = sh.makeServiceCall(url);
            byte[] byteArray = null;
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    body = jsonObj.getString("body");
                    title = jsonObj.getString("title");
                    String imageURL = jsonObj.getString("image-url");
                    URL ImageUrl = new URL(imageURL);
                    try {
                        HttpURLConnection conn = (HttpURLConnection) ImageUrl.openConnection();
                        conn.setDoInput(true);
                        conn.connect();
                        InputStream is = conn.getInputStream();
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        Image = BitmapFactory.decodeStream(is);
                        Image.compress(Bitmap.CompressFormat.PNG, 100, baos);
                        byteArray = baos.toByteArray();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    publishProgress(body, title, ""+counter,byteArray);
                }
                catch(Exception e){
                    e.printStackTrace();
                }
                counter++;
            }
            else {
                publishProgress( "Network error");
            }
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return counter;
    }
    @Override
    protected void onProgressUpdate(Object... progress){
        if(progress.length==1){
            Toast.makeText(context1, "Network error", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent1 = new Intent();
        intent1.setAction("com.example.assignment");
        intent1.putExtra("body", (String)progress[0]);
        intent1.putExtra("title", (String)progress[1]);
        intent1.putExtra("counter", (String)progress[2]);
        byte[] image = (byte[]) progress[3];
        int count = (Integer.parseInt((String)progress[2]))%5;
        try {
            OutputStreamWriter outputStreamWriter1 = new OutputStreamWriter(context.openFileOutput("bodyFile"+count, Context.MODE_PRIVATE));
            OutputStreamWriter outputStreamWriter2 = new OutputStreamWriter(context.openFileOutput("titleFile"+count, Context.MODE_PRIVATE));
            FileOutputStream fOut = context.openFileOutput("imageFile"+count, context.MODE_PRIVATE);
            fOut.write(image);
            outputStreamWriter1.write((String)progress[0]);
            outputStreamWriter2.write("News "+(String)progress[2]+": "+(String)progress[1]);
            fOut.close();
            outputStreamWriter1.close();
            outputStreamWriter2.close();
        } catch (Exception e) {
            Log.i("Act", "File not found");
        }
        context.sendBroadcast(intent1);
    }

    @Override
    protected void onPostExecute(Object object){

    }
}