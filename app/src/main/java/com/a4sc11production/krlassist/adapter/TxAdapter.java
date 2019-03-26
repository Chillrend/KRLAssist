package com.a4sc11production.krlassist.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.a4sc11production.krlassist.R;
import com.a4sc11production.krlassist.model.TxTemporary;

import java.util.ArrayList;

public class TxAdapter extends ArrayAdapter<TxTemporary> {

    private ArrayList<TxTemporary> items;
    Context ctx;

    private static class ViewHolder {
        TextView tx_type;
        TextView deduction_or_addition;
        TextView amount;
        TextView stasiun;
        ImageView icon;
        RelativeLayout parent_container;
    }

    public TxAdapter(ArrayList<TxTemporary> data, Context mCtx){
        super(mCtx, R.layout.realtime_list_item, data);

        this.items = data;
        this.ctx = mCtx;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TxTemporary txTemporary = getItem(position);

        ViewHolder viewHolder;
        final View result;

        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater li = LayoutInflater.from(getContext());
            convertView = li.inflate(R.layout.sejarah_tx_list_item, parent, false);
            viewHolder.tx_type = (TextView) convertView.findViewById(R.id.tx_type);
            viewHolder.stasiun = (TextView) convertView.findViewById(R.id.tx_at_stasiun);
            viewHolder.deduction_or_addition = (TextView) convertView.findViewById(R.id.tx_substract_or_addition);
            viewHolder.amount = (TextView) convertView.findViewById(R.id.tx_amount);
            viewHolder.icon = (ImageView) convertView.findViewById(R.id.tx_type_icon);
            viewHolder.parent_container = (RelativeLayout) convertView.findViewById(R.id.parent_tx_list_item);

            result = convertView;

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        String txType = txTemporary.getTx_type();
        if(txType.equals("DEDUCTION_TRAVEL")){
            viewHolder.tx_type.setText("Perjalanan");
            viewHolder.stasiun.setText("Stasiun " + txTemporary.getStasiun());
            viewHolder.deduction_or_addition.setText("Pengurangan");
            viewHolder.parent_container.setBackgroundColor(ctx.getResources().getColor(R.color.colorDanger));
            viewHolder.icon.setImageResource(R.drawable.ic_remove_24dp);
            String amount = String.valueOf(txTemporary.getAmount());
            viewHolder.amount.setText(amount);
        }else if(txType.equals("ADDITION_STAT")){
            viewHolder.tx_type.setText("Isi Ulang");
            viewHolder.deduction_or_addition.setText("Penambahan");
            viewHolder.stasiun.setText("Stasiun " + txTemporary.getStasiun());
            viewHolder.icon.setImageResource(R.drawable.ic_plus);
            viewHolder.parent_container.setBackgroundColor(ctx.getResources().getColor(R.color.colorNormal));
            String amount = String.valueOf(txTemporary.getAmount());
            viewHolder.amount.setText(amount);
        }else if(txType.equals("ADDITION_PARTNER")){
            viewHolder.tx_type.setText("Isi Ulang");
            viewHolder.deduction_or_addition.setText("Penambahan");
            viewHolder.stasiun.setText("Partner");
            viewHolder.icon.setImageResource(R.drawable.ic_plus);
            viewHolder.parent_container.setBackgroundColor(ctx.getResources().getColor(R.color.colorNormal));
            String amount = String.valueOf(txTemporary.getAmount());
            viewHolder.amount.setText(amount);
        }else if(txType.equals("DEDUCTION_OTHER")) {
            viewHolder.tx_type.setText("Lain-Lain");
            viewHolder.deduction_or_addition.setText("Pengurangan");
            viewHolder.stasiun.setText("Partner");
            viewHolder.parent_container.setBackgroundColor(ctx.getResources().getColor(R.color.colorDanger));
            viewHolder.icon.setImageResource(R.drawable.ic_remove_24dp);
            String amount = String.valueOf(txTemporary.getAmount());
            viewHolder.amount.setText(amount);
        }
        return convertView;
    }


}
