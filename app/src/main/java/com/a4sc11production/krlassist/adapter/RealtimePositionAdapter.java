package com.a4sc11production.krlassist.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.a4sc11production.krlassist.R;
import com.a4sc11production.krlassist.model.RealtimePos.Krl;
import com.a4sc11production.krlassist.model.RealtimePos.RealtimePos;
import com.a4sc11production.krlassist.model.RealtimePosition;

import java.util.ArrayList;

public class RealtimePositionAdapter extends ArrayAdapter<RealtimePosition> {

    private ArrayList<RealtimePosition> items;
    Context ctx;

    private static class ViewHolder {
        TextView nomor_ka;
        TextView relasi;
        TextView realtime_status;
        TextView stamformasi;
        RelativeLayout parent_container;
    }

    public RealtimePositionAdapter(ArrayList<RealtimePosition> data,  Context mCtx){
        super(mCtx, R.layout.realtime_list_item, data);

        this.items = data;
        this.ctx = mCtx;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        RealtimePosition rPos = getItem(position);

        ViewHolder viewHolder;

        final View result;

        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater li = LayoutInflater.from(getContext());
            convertView = li.inflate(R.layout.realtime_list_item, parent, false);
            viewHolder.nomor_ka = (TextView) convertView.findViewById(R.id.nomor_ka);
            viewHolder.relasi = (TextView) convertView.findViewById(R.id.relasi);
            viewHolder.realtime_status = (TextView) convertView.findViewById(R.id.realtime_status);
            viewHolder.stamformasi = (TextView) convertView.findViewById(R.id.stamformasi);
            viewHolder.parent_container = (RelativeLayout) convertView.findViewById(R.id.parent_realtime_list_item);

            result = convertView;

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        viewHolder.nomor_ka.setText(rPos.getNomor_ka());
        viewHolder.relasi.setText(rPos.getRelasi());
        viewHolder.realtime_status.setText(rPos.getRealtime_status() + " " + rPos.getStasiun_at());
        viewHolder.stamformasi.setText(String.valueOf(rPos.getStamformasi()));

        String line = rPos.getLine();
        if(line.equals("Central Line")){
            viewHolder.parent_container.setBackgroundColor(ctx.getResources().getColor(R.color.RedLine));
        }else if(line.equals("Loop Line")){
            viewHolder.parent_container.setBackgroundColor(ctx.getResources().getColor(R.color.LoopLine));
        }else if(line.equals("Rangkasbitung Line")){
            viewHolder.parent_container.setBackgroundColor(ctx.getResources().getColor(R.color.RangkasbitungLine));
        }else if(line.equals("Bekasi Line")){
            viewHolder.parent_container.setBackgroundColor(ctx.getResources().getColor(R.color.BekasiLine));
        }else if(line.equals("Tangerang Line")){
            viewHolder.parent_container.setBackgroundColor(ctx.getResources().getColor(R.color.TanggerangLine));
        }else if(line.equals("Tanjung Priok Line")){
            viewHolder.parent_container.setBackgroundColor(ctx.getResources().getColor(R.color.TanjungPriokLine));
        }

        return convertView;
    }

}
