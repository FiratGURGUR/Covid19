package com.example.covid19.api;

import android.app.role.RoleManager;
import android.os.Handler;

import com.example.covid19.model.ChartModel;
import com.example.covid19.model.Country;
import com.example.covid19.model.ResponseAllData;
import com.example.covid19.model.ResponseDetay;
import com.example.covid19.model.ResponseGeneral;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.covid19.api.URLList.baseUrl;


public class APIClass {

    public void getWorldCounts(final Handler hnd) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String sonuc = HttpPostJson.GetHttp(baseUrl);
                if (sonuc != null && !sonuc.isEmpty()) {
                    try {

                        ObjectMapper mapper = new ObjectMapper();
                        ResponseGeneral model = mapper.readValue(sonuc, ResponseGeneral.class);
                        hnd.obtainMessage(1, model).sendToTarget();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    hnd.obtainMessage(0, "Server Hatası").sendToTarget();
                }
            }
        }).start();
    }

    public void getCountries(final Handler hnd) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String sonuc = HttpPostJson.GetHttp(URLList.countries);

                if (sonuc != null && !sonuc.isEmpty()) {
                    try {

                        JSONObject ja = new JSONObject(sonuc);
                        String ss = ja.getString("countries");

                        ObjectMapper mapper = new ObjectMapper();
                        List<Country> model = mapper.readValue(ss, new TypeReference<List<Country>>() {
                        });
                        hnd.obtainMessage(10, model).sendToTarget();

                    } catch (JsonParseException e) {
                        hnd.obtainMessage(0, "Json Parse Hatası").sendToTarget();
                    } catch (JsonMappingException e) {
                        hnd.obtainMessage(0, "Json Parse Hatası").sendToTarget();
                    } catch (IOException e) {
                        hnd.obtainMessage(0, "Json Parse Hatası").sendToTarget();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    hnd.obtainMessage(0, "Server Hatası").sendToTarget();
                }
            }
        }).start();
    }

    public void getCountriesDetail(final Handler hnd, final String code) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String sonuc = HttpPostJson.GetHttp(URLList.detay + code);

                if (sonuc != null && !sonuc.isEmpty()) {
                    try {

                        ObjectMapper mapper = new ObjectMapper();
                        ResponseDetay model = mapper.readValue(sonuc, ResponseDetay.class);
                        hnd.obtainMessage(1, model).sendToTarget();

                    } catch (JsonParseException e) {
                        hnd.obtainMessage(0, "Json Parse Hatası").sendToTarget();
                    } catch (JsonMappingException e) {
                        hnd.obtainMessage(0, "Json Parse Hatası").sendToTarget();
                    } catch (IOException e) {
                        hnd.obtainMessage(0, "Json Parse Hatası").sendToTarget();
                    }
                } else {
                    hnd.obtainMessage(0, "Server Hatası").sendToTarget();
                }
            }
        }).start();
    }


    public void getAllData(final Handler hnd) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String sonuc = HttpPostJson.GetHttp(URLList.allData);

                if (sonuc != null && !sonuc.isEmpty()) {
                    List<ResponseAllData> liste = new ArrayList<>();
                    try {
                        JSONArray ja = new JSONArray(sonuc);
                        for (int i = 0; i < ja.length(); i++) {
                            JSONObject cont = ja.getJSONObject(i);
                            String name = cont.getString("name");
                            String code = cont.getString("alpha2Code");
                            double lat = 0;
                            double lon = 0;
                            JSONArray jj = cont.getJSONArray("latlng");
                            Double[] dbl = new Double[jj.length()];
                            if (jj.length() > 0) {

                                dbl[0] = jj.getDouble(0);
                                dbl[1] = jj.getDouble(1);
                                lat = dbl[0];
                                lon = dbl[1];
                            } else {

                            }


                            liste.add(new ResponseAllData(name, code, lat, lon));

                        }
                        hnd.obtainMessage(10, liste).sendToTarget();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                } else {
                    hnd.obtainMessage(0, "Server Hatası").sendToTarget();
                }
            }
        }).start();
    }




    public void getChartData(final Handler hnd , final String country) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String sonuc = HttpPostJson.GetHttp(URLList.chartData + country);

                if (sonuc != null && !sonuc.isEmpty()) {
                    List<ChartModel> liste = new ArrayList<>();
                    try {
                        JSONArray ja = new JSONArray(sonuc);
                        for (int i = 0; i < ja.length(); i++) {
                            JSONObject cont = ja.getJSONObject(i);
                            int vaka = cont.getInt("Confirmed");
                            int olum = cont.getInt("Deaths");
                            int iyilesen = cont.getInt("Recovered");
                            String date = cont.getString("Date");

                            liste.add(new ChartModel(vaka,iyilesen, olum,date));

                        }
                        hnd.obtainMessage(10, liste).sendToTarget();

                    } catch (JSONException e) {
                        hnd.obtainMessage(100, "Server Hatası").sendToTarget();
                    }


                } else {
                    hnd.obtainMessage(100, "Server Hatası").sendToTarget();
                }
            }
        }).start();
    }



}
