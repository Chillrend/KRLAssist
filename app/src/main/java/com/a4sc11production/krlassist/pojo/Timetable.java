package com.a4sc11production.krlassist.pojo;

public class Timetable {
    private String nomor_ka;
    private String relasi;
    private String choosed_stasiun;
    private String line_served;
    private String time_departed;

    public Timetable(String nomor_ka, String relasi, String choosed_stasiun, String line_served, String time_departed) {
        this.nomor_ka = nomor_ka;
        this.relasi = relasi;
        this.choosed_stasiun = choosed_stasiun;
        this.line_served = line_served;
        this.time_departed = time_departed;
    }

    public String getLine_served() {
        return line_served;
    }

    public void setLine_served(String line_served) {
        this.line_served = line_served;
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

    public String getChoosed_stasiun() {
        return choosed_stasiun;
    }

    public void setChoosed_stasiun(String choosed_stasiun) {
        this.choosed_stasiun = choosed_stasiun;
    }

    public String getTime_departed() {
        return time_departed;
    }

    public void setTime_departed(String time_departed) {
        this.time_departed = time_departed;
    }
}
