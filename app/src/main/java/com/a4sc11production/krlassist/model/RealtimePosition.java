package com.a4sc11production.krlassist.model;

public class RealtimePosition {
    private String nomor_ka;
    private String relasi;
    private String realtime_status;
    private String line;
    private int stamformasi;

    public RealtimePosition(String nomor_ka, String relasi, String realtime_status, String line, int stamformasi) {
        this.nomor_ka = nomor_ka;
        this.relasi = relasi;
        this.realtime_status = realtime_status;
        this.line = line;
        this.stamformasi = stamformasi;
    }

    public String getNomor_ka() {
        return nomor_ka;
    }

    public void setNomor_ka(String nomor_ka) {
        this.nomor_ka = nomor_ka;
    }

    public String getRelasi() {
        return relasi;
    }

    public void setRelasi(String relasi) {
        this.relasi = relasi;
    }

    public String getRealtime_status() {
        return realtime_status;
    }

    public void setRealtime_status(String realtime_status) {
        this.realtime_status = realtime_status;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public int getStamformasi() {
        return stamformasi;
    }

    public void setStamformasi(int stamformasi) {
        this.stamformasi = stamformasi;
    }
}
