
package com.a4sc11production.krlassist.model.TimetableAP;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Timetable {

    @SerializedName("data")
    @Expose
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

}
