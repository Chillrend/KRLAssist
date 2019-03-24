package com.a4sc11production.krlassist.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.a4sc11production.krlassist.R;
import com.a4sc11production.krlassist.model.Timetable;


import java.sql.Time;
import java.util.ArrayList;

public class TimetableAdapter extends ArrayAdapter<Timetable> {

    private ArrayList<Timetable> items;
    Context ctx;

    private static class ViewHolder {
        TextView nomor_ka;
        TextView relasi;
        TextView stasiun_choosed;
        TextView time_departed;
        RelativeLayout parent_timetable_layout;
    }

    public TimetableAdapter(ArrayList<Timetable> items, Context mCtx){
        super(mCtx, R.layout.timetable_list_item ,items);

        this.items = items;
        this.ctx = mCtx;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Timetable timetable = getItem(position);

        ViewHolder viewHolder;

        final View result;

        if (convertView == null){
            viewHolder = new ViewHolder();

            LayoutInflater li = LayoutInflater.from(getContext());
            convertView = li.inflate(R.layout.timetable_list_item, parent,false);
            viewHolder.nomor_ka = convertView.findViewById(R.id.timetable_nomor_ka);
            viewHolder.relasi = convertView.findViewById(R.id.timetable_relasi);
            viewHolder.stasiun_choosed = convertView.findViewById(R.id.timetable_stasiun_keberangkatan);
            viewHolder.time_departed = convertView.findViewById(R.id.timetable_departed);
            viewHolder.parent_timetable_layout = convertView.findViewById(R.id.parent_timetable_list_item);

            result = convertView;

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        viewHolder.nomor_ka.setText(timetable.getNomor_ka());
        viewHolder.time_departed.setText(timetable.getTime_departed());
        viewHolder.stasiun_choosed.setText(timetable.getChoosed_stasiun());
        viewHolder.relasi.setText(timetable.getRelasi());

        String line = timetable.getLine_served();
        if(line.equals("Central Line")){
            viewHolder.parent_timetable_layout.setBackgroundColor(ctx.getResources().getColor(R.color.RedLine));
        }else if(line.equals("Loop Line")){
            viewHolder.parent_timetable_layout.setBackgroundColor(ctx.getResources().getColor(R.color.LoopLine));
        }else if(line.equals("Rangkasbitung Line")){
            viewHolder.parent_timetable_layout.setBackgroundColor(ctx.getResources().getColor(R.color.RangkasbitungLine));
        }else if(line.equals("Bekasi Line")){
            viewHolder.parent_timetable_layout.setBackgroundColor(ctx.getResources().getColor(R.color.BekasiLine));
        }else if(line.equals("Tangerang Line")){
            viewHolder.parent_timetable_layout.setBackgroundColor(ctx.getResources().getColor(R.color.TanggerangLine));
        }else if(line.equals("Tanjung Priok Line")){
            viewHolder.parent_timetable_layout.setBackgroundColor(ctx.getResources().getColor(R.color.TanjungPriokLine));
        }

        return convertView;
    }


}
