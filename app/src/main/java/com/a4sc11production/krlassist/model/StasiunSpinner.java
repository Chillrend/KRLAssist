package com.a4sc11production.krlassist.model;

public class StasiunSpinner {
    private String stasiun_id;
    private String stasiun_name;
    private boolean is_transit;
    private int image_res_id;

    public StasiunSpinner(String stasiun_id, String stasiun_name, boolean is_transit, int image_res_id){
        this.stasiun_id = stasiun_id;
        this.stasiun_name = stasiun_name;
        this.is_transit = is_transit;
        this.image_res_id = image_res_id;
    }

    public String getStasiun_id() {
        return stasiun_id;
    }

    public void setStasiun_id(String stasiun_id) {
        this.stasiun_id = stasiun_id;
    }

    public String getStasiun_name() {
        return stasiun_name;
    }

    public void setStasiun_name(String stasiun_name) {
        this.stasiun_name = stasiun_name;
    }

    public boolean isIs_transit() {
        return is_transit;
    }

    public void setIs_transit(boolean is_transit) {
        this.is_transit = is_transit;
    }

    public int getImage_res_id() {
        return image_res_id;
    }

    public void setImage_res_id(int image_res_id) {
        this.image_res_id = image_res_id;
    }
}
