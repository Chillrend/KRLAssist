
package com.a4sc11production.krlassist.model.GangguanList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Gangguan {

    @SerializedName("gangguan_id")
    @Expose
    private Integer gangguanId;
    @SerializedName("stasiun_nearest")
    @Expose
    private String stasiunNearest;
    @SerializedName("short_desc")
    @Expose
    private String shortDesc;
    @SerializedName("severity")
    @Expose
    private String severity;
    @SerializedName("long_desc")
    @Expose
    private String longDesc;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public Integer getGangguanId() {
        return gangguanId;
    }

    public void setGangguanId(Integer gangguanId) {
        this.gangguanId = gangguanId;
    }

    public String getStasiunNearest() {
        return stasiunNearest;
    }

    public void setStasiunNearest(String stasiunNearest) {
        this.stasiunNearest = stasiunNearest;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getLongDesc() {
        return longDesc;
    }

    public void setLongDesc(String longDesc) {
        this.longDesc = longDesc;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

}
