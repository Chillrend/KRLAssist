package com.a4sc11production.krlassist.model;

import java.util.List;

public class StasiunTemp {
    String kode_st;
    String nama_stat;
    double lat;
    double lng;
    boolean isTransit;
    List<String> line_served;

    public StasiunTemp(String kode_st, String nama_stat, double lat, double lng, boolean isTransit, List<String> line_served) {
        this.kode_st = kode_st;
        this.nama_stat = nama_stat;
        this.lat = lat;
        this.lng = lng;
        this.isTransit = isTransit;
        this.line_served = line_served;
    }

    public String getKode_st() {
        return kode_st;
    }

    public void setKode_st(String kode_st) {
        this.kode_st = kode_st;
    }

    public String getNama_stat() {
        return nama_stat;
    }

    public void setNama_stat(String nama_stat) {
        this.nama_stat = nama_stat;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(Long lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(Long lng) {
        this.lng = lng;
    }

    public boolean isTransit() {
        return isTransit;
    }

    public void setTransit(boolean transit) {
        isTransit = transit;
    }

    public List<String> getLine_served() {
        return line_served;
    }

    public void setLine_served(List<String> line_served) {
        this.line_served = line_served;
    }
}
