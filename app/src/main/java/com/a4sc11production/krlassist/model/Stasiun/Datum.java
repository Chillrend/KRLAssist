
package com.a4sc11production.krlassist.model.Stasiun;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("stasiun")
    @Expose
    private Stasiun_ stasiun;
    @SerializedName("linenamearr")
    @Expose
    private ArrayList<String> linenamearr = null;

    public Stasiun_ getStasiun() {
        return stasiun;
    }

    public void setStasiun(Stasiun_ stasiun) {
        this.stasiun = stasiun;
    }

    public ArrayList<String> getLinenamearr() {
        return linenamearr;
    }

    public void setLinenamearr(ArrayList<String> linenamearr) {
        this.linenamearr = linenamearr;
    }

}
