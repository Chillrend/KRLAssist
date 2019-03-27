
package com.a4sc11production.krlassist.model.TimetableAP;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Timetable_ {

    @SerializedName("timetable_id")
    @Expose
    private Integer timetableId;
    @SerializedName("train_no")
    @Expose
    private String trainNo;
    @SerializedName("relasi")
    @Expose
    private String relasi;
    @SerializedName("line_name")
    @Expose
    private String lineName;
    @SerializedName("dep_time")
    @Expose
    private String depTime;
    @SerializedName("stasiun")
    @Expose
    private String stasiun;

    public Integer getTimetableId() {
        return timetableId;
    }

    public void setTimetableId(Integer timetableId) {
        this.timetableId = timetableId;
    }

    public String getTrainNo() {
        return trainNo;
    }

    public void setTrainNo(String trainNo) {
        this.trainNo = trainNo;
    }

    public String getRelasi() {
        return relasi;
    }

    public void setRelasi(String relasi) {
        this.relasi = relasi;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public String getDepTime() {
        return depTime;
    }

    public void setDepTime(String depTime) {
        this.depTime = depTime;
    }

    public String getStasiun() {
        return stasiun;
    }

    public void setStasiun(String stasiun) {
        this.stasiun = stasiun;
    }

}
