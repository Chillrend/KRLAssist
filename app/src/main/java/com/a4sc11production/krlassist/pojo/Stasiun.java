package com.a4sc11production.krlassist.pojo;

import java.util.ArrayList;

public class Stasiun {
    private String nama;
    private String kode;
    private Double latitude;
    private ArrayList<String> line_served;
    private Double longitude;
    private ArrayList<String> neighbors;
    private boolean transit;

    public Stasiun(){

    }

    public Stasiun(String nama, String kode, Double latitude, ArrayList<String> line_served, Double longitude, ArrayList<String> neighbors, boolean transit) {
        this.nama = nama;
        this.kode = kode;
        this.latitude = latitude;
        this.line_served = line_served;
        this.longitude = longitude;
        this.neighbors = neighbors;
        this.transit = transit;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public ArrayList<String> getLine_served() {
        return line_served;
    }

    public void setLine_served(ArrayList<String> line_served) {
        this.line_served = line_served;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public ArrayList<String> getNeighbors() {
        return neighbors;
    }

    public void setNeighbors(ArrayList<String> neighbors) {
        this.neighbors = neighbors;
    }

    public boolean isTransit() {
        return transit;
    }

    public void setTransit(boolean transit) {
        this.transit = transit;
    }
}
