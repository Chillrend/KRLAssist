package com.a4sc11production.krlassist.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.a4sc11production.krlassist.  R;
import com.a4sc11production.krlassist.pojo.Gangguan;

import java.util.ArrayList;

public class GangguanAdapter extends ArrayAdapter<Gangguan> {
    private ArrayList<Gangguan> items;
    Context ctx;
    private ArrayList<String> line;

    private static class ViewHolder {
        TextView line_name;
        TextView status_short_desc;
        TextView stasiun_affected;
        LinearLayout parent_line_status;
    }

    public GangguanAdapter(ArrayList<Gangguan> items, Context mCtx, ArrayList<String> line) {
        super(mCtx, R.layout.line_status_list_item, items);
        this.line = line;
        this.items = items;
        this.ctx = mCtx;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Gangguan gangguan = getItem(position);
        String line_name = line.get(position);

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

        viewHolder.line_name.setText(line.get(position));
        viewHolder.status_short_desc.setText(gangguan.getShort_desc());

        String severity = gangguan.getSeverity();

        if (!severity.equals("NORMAL")) {
            viewHolder.stasiun_affected.setVisibility(View.VISIBLE);
        } else {
            viewHolder.stasiun_affected.setVisibility(View.GONE);
        }

        switch (severity) {
            case "NORMAL":
                viewHolder.parent_line_status.setBackgroundColor(ctx.getResources().getColor(R.color.colorNormal));

                break;
            case "MEDIUM":
                viewHolder.parent_line_status.setBackgroundColor(ctx.getResources().getColor(R.color.colorWarning));
                break;
            case "SEVERE":
                viewHolder.parent_line_status.setBackgroundColor(ctx.getResources().getColor(R.color.colorDanger));
                break;
        }

        switch (line.get(position)) {
            case "Central Line":
                viewHolder.line_name.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_red_line_18dp, 0, 0, 0);
                break;
            case "Loop Line":
                viewHolder.line_name.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_loop_line_18dp, 0, 0, 0);
                break;
            case "Rangkasbitung Line":
                viewHolder.line_name.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_rangkasbitung_line_18dp, 0, 0, 0);
                break;
            case "Bekasi Line":
                viewHolder.line_name.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_bekasi_line_18dp, 0, 0, 0);
                break;
            case "Tangerang Line":
                viewHolder.line_name.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_tangerang_line_18dp, 0, 0, 0);
                break;
            case "Tanjung Priok Line":
                viewHolder.line_name.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_tanjung_priok_18dp, 0, 0, 0);
                break;
        }

        return convertView;

    }
}
