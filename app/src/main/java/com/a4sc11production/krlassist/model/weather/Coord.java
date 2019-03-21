
package com.a4sc11production.krlassist.model.weather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Coord {

    @SerializedName("lon")
    @Expose
    private float lon;
    @SerializedName("lat")
    @Expose
    private float lat;

    public float getLon() {
        return lon;
    }

    public void setLon(Integer lon) {
        this.lon = lon;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(Integer lat) {
        this.lat = lat;
    }

}
