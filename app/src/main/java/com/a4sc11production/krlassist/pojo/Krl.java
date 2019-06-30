package com.a4sc11production.krlassist.pojo;

import java.util.Map;

public class Krl {
    private String line;
    private Map<String,String> realtime_pos;
    private Map<String,Integer> schedule;

    public Krl() {

    }

    public Krl(String line, Map<String, String> realtime_pos, Map<String, Integer> schedule) {
        this.line = line;
        this.realtime_pos = realtime_pos;
        this.schedule = schedule;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public Map<String, String> getRealtime_pos() {
        return realtime_pos;
    }

    public void setRealtime_pos(Map<String, String> realtime_pos) {
        this.realtime_pos = realtime_pos;
    }

    public Map<String, Integer> getSchedule() {
        return schedule;
    }

    public void setSchedule(Map<String, Integer> schedule) {
        this.schedule = schedule;
    }
}
