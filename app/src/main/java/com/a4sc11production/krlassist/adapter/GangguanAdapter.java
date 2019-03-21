package com.a4sc11production.krlassist.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.a4sc11production.krlassist.R;
import com.a4sc11production.krlassist.model.Gangguan;

import java.util.ArrayList;

public class GangguanAdapter extends ArrayAdapter<Gangguan> {
    private ArrayList<Gangguan> items;
    Context ctx;

    private static class ViewHolder {
        TextView line_name;
        TextView status_short_desc;
        TextView stasiun_affected;
        LinearLayout parent_line_status;
    }

    public GangguanAdapter(ArrayList<Gangguan> items, Context mCtx) {
        super(mCtx, R.layout.line_status_list_item, items);

        this.items = items;
        this.ctx = mCtx;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Gangguan gangguan = getItem(position);

        ViewHolder viewHolder;

        final View result;

        if (convertView == null) {
            viewHolder = new ViewHolder();

            LayoutInflater li = LayoutInflater.from(getContext());
            convertView = li.inflate(R.layout.line_status_list_item, parent, false);
            viewHolder.line_name = convertView.findViewById(R.id.line_name);
            viewHolder.status_short_desc = convertView.findViewById(R.id.status_short_desc);
            viewHolder.stasiun_affected = convertView.findViewById(R.id.status_stasiun_affected);
            viewHolder.parent_line_status = convertView.findViewById(R.id.parent_line_status_item);

            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }
        String severity = gangguan.getSeverity();

        viewHolder.line_name.setText(gangguan.getLine_name());
        viewHolder.status_short_desc.setText(gangguan.getShort_desc());
        if (!severity.equals("Normal")) {
            viewHolder.stasiun_affected.setVisibility(View.VISIBLE);
        } else {
            viewHolder.stasiun_affected.setVisibility(View.GONE);
        }

        if (severity.equals("Normal")) {
            viewHolder.parent_line_status.setBackgroundColor(ctx.getResources().getColor(R.color.colorNormal));
        } else if (severity.equals("Medium")) {
            viewHolder.parent_line_status.setBackgroundColor(ctx.getResources().getColor(R.color.colorWarning));
        } else if (severity.equals("Severe")) {
            viewHolder.parent_line_status.setBackgroundColor(ctx.getResources().getColor(R.color.colorDanger));
        }

        String line = gangguan.getLine_name();


        if (line.equals("Central Line")) {
            viewHolder.line_name.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_red_line_18dp, 0, 0, 0);
        } else if (line.equals("Loop Line")) {
            viewHolder.line_name.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_loop_line_18dp, 0, 0, 0);
        } else if (line.equals("Rangkasbitung Line")) {
            viewHolder.line_name.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_rangkasbitung_line_18dp, 0, 0, 0);
        } else if (line.equals("Bekasi Line")) {
            viewHolder.line_name.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_bekasi_line_18dp, 0, 0, 0);
        } else if (line.equals("Tangerang Line")) {
            viewHolder.line_name.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_tangerang_line_18dp, 0, 0, 0);
        } else if (line.equals("Tanjung Priok Line")) {
            viewHolder.line_name.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_tanjung_priok_18dp, 0, 0, 0);
        }

        return convertView;

    }
}
