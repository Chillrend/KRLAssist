package com.a4sc11production.krlassist.pojo;

import java.util.ArrayList;

public class Gangguan {

    private ArrayList<String> line_affected;
    private String long_desc;
    private String nearest_stat;
    private ArrayList<String> other_line_affected;
    private String severity;
    private String short_desc;

    public Gangguan(){

    }

    public Gangguan(ArrayList<String> line_affected, String long_desc, String nearest_stat, ArrayList<String> other_line_affected, String severity, String short_desc) {
        this.line_affected = line_affected;
        this.long_desc = long_desc;
        this.nearest_stat = nearest_stat;
        this.other_line_affected = other_line_affected;
        this.severity = severity;
        this.short_desc = short_desc;
    }

    public ArrayList<String> getLine_affected() {
        return line_affected;
    }

    public void setLine_affected(ArrayList<String> line_affected) {
        this.line_affected = line_affected;
    }

    public String getLong_desc() {
        return long_desc;
    }

    public void setLong_desc(String long_desc) {
        this.long_desc = long_desc;
    }

    public String getNearest_stat() {
        return nearest_stat;
    }

    public void setNearest_stat(String nearest_stat) {
        this.nearest_stat = nearest_stat;
    }

    public ArrayList<String> getOther_line_affected() {
        return other_line_affected;
    }

    public void setOther_line_affected(ArrayList<String> other_line_affected) {
        this.other_line_affected = other_line_affected;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getShort_desc() {
        return short_desc;
    }

    public void setShort_desc(String short_desc) {
        this.short_desc = short_desc;
    }
}
