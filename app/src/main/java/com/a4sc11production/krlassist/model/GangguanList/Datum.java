
package com.a4sc11production.krlassist.model.GangguanList;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("gangguan")
    @Expose
    private Gangguan gangguan;
    @SerializedName("affected")
    @Expose
    private ArrayList<Affected> affected = null;

    public Gangguan getGangguan() {
        return gangguan;
    }

    public void setGangguan(Gangguan gangguan) {
        this.gangguan = gangguan;
    }

    public ArrayList<Affected> getAffected() {
        return affected;
    }

    public void setAffected(ArrayList<Affected> affected) {
        this.affected = affected;
    }

}
