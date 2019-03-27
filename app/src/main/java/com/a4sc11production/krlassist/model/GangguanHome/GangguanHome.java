
package com.a4sc11production.krlassist.model.GangguanHome;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GangguanHome {

    @SerializedName("data")
    @Expose
    private ArrayList<Datum> data = new ArrayList<>();

    public ArrayList<Datum> getData() {
        return data;
    }

    public void setData(ArrayList<Datum> data) {
        this.data = data;
    }

}
