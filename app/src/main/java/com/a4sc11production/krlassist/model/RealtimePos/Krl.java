
package com.a4sc11production.krlassist.model.RealtimePos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Krl {

    @SerializedName("train_no")
    @Expose
    private String trainNo;
    @SerializedName("line_name")
    @Expose
    private String lineName;
    @SerializedName("relasi")
    @Expose
    private String relasi;
    @SerializedName("setatus")
    @Expose
    private String setatus;
    @SerializedName("stamformasi")
    @Expose
    private Integer stamformasi;

    public String getTrainNo() {
        return trainNo;
    }

    public void setTrainNo(String trainNo) {
        this.trainNo = trainNo;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public String getRelasi() {
        return relasi;
    }

    public void setRelasi(String relasi) {
        this.relasi = relasi;
    }

    public String getSetatus() {
        return setatus;
    }

    public void setSetatus(String setatus) {
        this.setatus = setatus;
    }

    public Integer getStamformasi() {
        return stamformasi;
    }

    public void setStamformasi(Integer stamformasi) {
        this.stamformasi = stamformasi;
    }

}
