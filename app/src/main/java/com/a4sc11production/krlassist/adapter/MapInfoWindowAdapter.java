package com.a4sc11production.krlassist.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.a4sc11production.krlassist.R;
import com.a4sc11production.krlassist.model.StasiunTemp;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import java.util.List;

public class MapInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
    private Context ctx;
    private TextView st_name, st_id, isTransit;
    private ImageView line_1, line_2, line_3;

    public MapInfoWindowAdapter(Context context){
        this.ctx = context;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View view = ((Activity)ctx).getLayoutInflater()
                .inflate(R.layout.map_info_window, null);


        st_name = view.findViewById(R.id.map_stasiun_name);
        st_id = view.findViewById(R.id.map_kode_stasiun);
        isTransit = view.findViewById(R.id.isTransit);

        line_1 = view.findViewById(R.id.marker_line_1);
        line_2 = view.findViewById(R.id.marker_line_2);
        line_3 = view.findViewById(R.id.marker_line_3);

        StasiunTemp stTemp = (StasiunTemp) marker.getTag();

        st_name.setText(stTemp.getNama_stat());
        st_id.setText(stTemp.getKode_st());

        if(stTemp.isTransit()){
            isTransit.setVisibility(View.VISIBLE);
        }else{
            isTransit.setVisibility(View.GONE);
        }

        List<String> iterable = stTemp.getLine_served();
        int line_count = stTemp.getLine_served().size();
        int iterator = 0;
        if(line_count == 1){
            line_1.setVisibility(View.VISIBLE);
            line_2.setVisibility(View.GONE);
            line_3.setVisibility(View.GONE);
            String line = iterable.get(0);
            line_1.setImageResource(getDrawable(line));
        }else if(line_count == 2){
            line_1.setVisibility(View.VISIBLE);
            line_2.setVisibility(View.VISIBLE);
            line_3.setVisibility(View.GONE);

            String line = iterable.get(0);
            line_1.setImageResource(getDrawable(line));
            line = iterable.get(1);
            line_2.setImageResource(getDrawable(line));
        }else if(line_count == 3){
            line_1.setVisibility(View.VISIBLE);
            line_2.setVisibility(View.VISIBLE);
            line_3.setVisibility(View.VISIBLE);

            String line = iterable.get(0);
            line_1.setImageResource(getDrawable(line));
            line = iterable.get(1);
            line_2.setImageResource(getDrawable(line));
            line = iterable.get(2);
            line_3.setImageResource(getDrawable(line));
        }else{
            line_1.setVisibility(View.GONE);
            line_2.setVisibility(View.GONE);
            line_3.setVisibility(View.GONE);
        }

        return view;
    }

    private int getDrawable(String line){
        if(line.equals("Central Line")){
            return R.drawable.ic_red_line_18dp;
        }else if(line.equals("Loop Line")){
            return R.drawable.ic_loop_line_18dp;
        }else if(line.equals("Rangkasbitung Line")){
            return R.drawable.ic_rangkasbitung_line_18dp;
        }else if(line.equals("Bekasi Line")){
            return R.drawable.ic_bekasi_line;
        }else if(line.equals("Tangerang Line")){
            return R.drawable.ic_tangerang_line_18dp;
        }else if(line.equals("Tanjung Priok Line")){
            return R.drawable.ic_tanjung_priok_18dp;
        }
        return R.drawable.ic_forbidden;
    }
}
