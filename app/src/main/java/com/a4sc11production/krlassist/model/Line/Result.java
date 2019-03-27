
package com.a4sc11production.krlassist.model.Line;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("line_id")
    @Expose
    private Integer lineId;
    @SerializedName("line_name")
    @Expose
    private String lineName;
    @SerializedName("terminus1")
    @Expose
    private String terminus1;
    @SerializedName("lat1")
    @Expose
    private String lat1;
    @SerializedName("lng1")
    @Expose
    private String lng1;
    @SerializedName("terminus2")
    @Expose
    private String terminus2;
    @SerializedName("lat2")
    @Expose
    private String lat2;
    @SerializedName("lng2")
    @Expose
    private String lng2;

    public Integer getLineId() {
        return lineId;
    }

    public void setLineId(Integer lineId) {
        this.lineId = lineId;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public String getTerminus1() {
        return terminus1;
    }

    public void setTerminus1(String terminus1) {
        this.terminus1 = terminus1;
    }

    public String getLat1() {
        return lat1;
    }

    public void setLat1(String lat1) {
        this.lat1 = lat1;
    }

    public String getLng1() {
        return lng1;
    }

    public void setLng1(String lng1) {
        this.lng1 = lng1;
    }

    public String getTerminus2() {
        return terminus2;
    }

    public void setTerminus2(String terminus2) {
        this.terminus2 = terminus2;
    }

    public String getLat2() {
        return lat2;
    }

    public void setLat2(String lat2) {
        this.lat2 = lat2;
    }

    public String getLng2() {
        return lng2;
    }

    public void setLng2(String lng2) {
        this.lng2 = lng2;
    }

    //Overriding toString() Method to show the concat at the spinner


    @Override
    public String toString(){
        return lineName + " (" + terminus1 + " - " + terminus2 +")";
    }

}
