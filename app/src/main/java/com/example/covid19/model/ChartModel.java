package com.example.covid19.model;

public class ChartModel {

   private int vaka;
   private int iyilesen;
   private int olen;
   private String date;

    public ChartModel(int vaka, int iyilesen, int olen, String date) {
        this.vaka = vaka;
        this.iyilesen = iyilesen;
        this.olen = olen;
        this.date = date;
    }

    public int getVaka() {
        return vaka;
    }

    public void setVaka(int vaka) {
        this.vaka = vaka;
    }

    public int getIyilesen() {
        return iyilesen;
    }

    public void setIyilesen(int iyilesen) {
        this.iyilesen = iyilesen;
    }

    public int getOlen() {
        return olen;
    }

    public void setOlen(int olen) {
        this.olen = olen;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
