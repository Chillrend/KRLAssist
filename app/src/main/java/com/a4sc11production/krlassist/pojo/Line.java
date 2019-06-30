package com.a4sc11production.krlassist.pojo;

import java.util.ArrayList;

public class Line {
    private String start_station;
    private String line_name;
    private String end_station;
    private ArrayList<String> stasiun_served;

    public Line(){

    }

    public Line(String start_station, String line_name, String end_station, ArrayList<String> stasiun_served) {
        this.start_station = start_station;
        this.line_name = line_name;
        this.end_station = end_station;
        this.stasiun_served = stasiun_served;
    }

    public String getStart_station() {
        return start_station;
    }

    public void setStart_station(String start_station) {
        this.start_station = start_station;
    }

    public String getLine_name() {
        return line_name;
    }

    public void setLine_name(String line_name) {
        this.line_name = line_name;
    }

    public String getEnd_station() {
        return end_station;
    }

    public void setEnd_station(String end_station) {
        this.end_station = end_station;
    }

    public ArrayList<String> getStasiun_served() {
        return stasiun_served;
    }

    public void setStasiun_served(ArrayList<String> stasiun_served) {
        this.stasiun_served = stasiun_served;
    }
}
