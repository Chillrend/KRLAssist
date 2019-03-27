
package com.a4sc11production.krlassist.model.TimetableAP;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("timetable")
    @Expose
    private ArrayList<Timetable_> timetable = null;

    public ArrayList<Timetable_> getTimetable() {
        return timetable;
    }

    public void setTimetable(ArrayList<Timetable_> timetable) {
        this.timetable = timetable;
    }

}
