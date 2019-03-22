
package com.a4sc11production.krlassist.model.KMT;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MultiTrip {

    @SerializedName("Kmt")
    @Expose
    private List<Kmt> kmt = null;

    public List<Kmt> getKmt() {
        return kmt;
    }

    public void setKmt(List<Kmt> kmt) {
        this.kmt = kmt;
    }

}
