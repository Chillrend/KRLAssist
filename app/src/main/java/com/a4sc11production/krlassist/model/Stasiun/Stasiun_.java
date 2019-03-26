
package com.a4sc11production.krlassist.model.Stasiun;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Stasiun_ {

    @SerializedName("stasiun_id")
    @Expose
    private String stasiunId;
    @SerializedName("nama")
    @Expose
    private String nama;
    @SerializedName("is_transit")
    @Expose
    private Integer isTransit;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("lng")
    @Expose
    private String lng;
    @SerializedName("lineid")
    @Expose
    private String lineid;
    @SerializedName("linenamestr")
    @Expose
    private String linenamestr;

    public String getStasiunId() {
        return stasiunId;
    }

    public void setStasiunId(String stasiunId) {
        this.stasiunId = stasiunId;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public Integer getIsTransit() {
        return isTransit;
    }

    public void setIsTransit(Integer isTransit) {
        this.isTransit = isTransit;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLineid() {
        return lineid;
    }

    public void setLineid(String lineid) {
        this.lineid = lineid;
    }

    public String getLinenamestr() {
        return linenamestr;
    }

    public void setLinenamestr(String linenamestr) {
        this.linenamestr = linenamestr;
    }

}
