
package com.a4sc11production.krlassist.model.RealtimePos;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("line")
    @Expose
    private ArrayList<Line> line = new ArrayList<>();
    @SerializedName("krl")
    @Expose
    private ArrayList<Krl> krl = new ArrayList<>();

    public ArrayList<Line> getLine() {
        return line;
    }

    public void setLine(ArrayList<Line> line) {
        this.line = line;
    }

    public ArrayList<Krl> getKrl() {
        return krl;
    }

    public void setKrl(ArrayList<Krl> krl) {
        this.krl = krl;
    }

}
