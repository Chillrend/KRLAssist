
package com.a4sc11production.krlassist.model.RealtimePos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Line {

    @SerializedName("line_id")
    @Expose
    private Integer lineId;
    @SerializedName("line_name")
    @Expose
    private String lineName;

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

}
