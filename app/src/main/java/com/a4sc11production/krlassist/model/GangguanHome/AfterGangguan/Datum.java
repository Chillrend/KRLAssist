
package com.a4sc11production.krlassist.model.GangguanHome.AfterGangguan;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("gangguan")
    @Expose
    private List<Gangguan> gangguan = null;
    @SerializedName("linename")
    @Expose
    private ArrayList<String> linename = new ArrayList<>();

    public List<Gangguan> getGangguan() {
        return gangguan;
    }

    public void setGangguan(List<Gangguan> gangguan) {
        this.gangguan = gangguan;
    }

    public ArrayList<String> getLinename() {
        return linename;
    }

    public void setLinename(ArrayList<String> linename) {
        this.linename = linename;
    }

}
