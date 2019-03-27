
package com.a4sc11production.krlassist.model.GangguanList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Affected {

    @SerializedName("line_name")
    @Expose
    private String lineName;

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

}
