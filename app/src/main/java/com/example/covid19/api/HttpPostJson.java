package com.example.covid19.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpPostJson {
    public static String GetHttp(String URL) {
        String don="";
        try {
            java.net.URL connectURL = new URL(URL);
            HttpURLConnection conn = (HttpURLConnection) connectURL.openConnection();
            String conco = String.valueOf(conn.getResponseCode());
            InputStream is = conn.getInputStream();
            don=convertStreamToString(is);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return don;
    }

    private static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

}
