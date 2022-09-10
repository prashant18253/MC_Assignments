package com.example.assignment_3;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpHandler {

    private static final String TAG = HttpHandler.class.getSimpleName();

    public String makeServiceCall(String reqUrl) {
        String response = null;
        try {
            URL url = new URL(reqUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            InputStream in = new BufferedInputStream(conn.getInputStream());
            response = convertStreamToString(in);
        }  catch (Exception e) {
        }
        return response;
    }

    private String convertStreamToString(InputStream is) {
        InputStreamReader r = new InputStreamReader(is);
        BufferedReader reader = new BufferedReader(r);
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (Exception e) {
        } finally {
            try {
                is.close();
            } catch (IOException e) {
            }
        }
        String s = sb.toString();
        return s;
    }
}
