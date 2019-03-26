package com.a4sc11production.krlassist.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;
import com.a4sc11production.krlassist.R;
import com.a4sc11production.krlassist.model.Stasiun.Stasiun;
import com.a4sc11production.krlassist.model.Stasiun.Stasiun_;
import com.a4sc11production.krlassist.model.StasiunSpinner;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class StasiunSpinnerAdapter extends ArrayAdapter<Stasiun_> {

    private Context context;
    private int resource_id;
    private ArrayList<Stasiun_> items, suggestions, tempItems;

    public StasiunSpinnerAdapter (@NonNull Context context, int resource_id, ArrayList<Stasiun_> items){
        super(context, resource_id, items);

        this.items = items;
        this.context = context;
        this.resource_id = resource_id;

        tempItems = new ArrayList<>(items);
        suggestions = new ArrayList<>();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        View view = convertView;
        try{
            if(convertView == null){
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                view = inflater.inflate(resource_id, parent, false);
            }

            Stasiun_ stasiun =  getItem(position);
            TextView stasiun_name = (TextView) view.findViewById(R.id.stasiun_name_row);
            ImageView transit = (ImageView) view.findViewById(R.id.is_transit_image);

            if(stasiun.getIsTransit() == 1){
                transit.setImageResource(R.drawable.ic_letter_t);
            }else{
                transit.setImageResource(R.drawable.ic_letter_s);
            }
            stasiun_name.setText(stasiun.getNama());
        }catch (Exception e){
            e.printStackTrace();
        }
        return view;
    }

    @Nullable
    @Override
    public Stasiun_ getItem(int position){
        return items.get(position);
    }

    @Override
    public int getCount(){
        return items.size();
    }

    public String getStasiunIdAtPosition(int position){
        Stasiun_ stasiunSpinner = getItem(position);
        return stasiunSpinner.getStasiunId();
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @NonNull
    @Override
    public Filter getFilter(){
        return stasiunFilter;
    }

    private Filter stasiunFilter = new Filter() {

        @Override
        public CharSequence convertResultToString(Object resultValue){
            Stasiun_ stasiunSpinner = (Stasiun_) resultValue;
            return stasiunSpinner.getNama();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if(constraint != null){
                suggestions.clear();
                for (Stasiun_ stasiunSpinner: tempItems){
                    if(stasiunSpinner.getNama().toLowerCase().startsWith(constraint.toString().toLowerCase())){
                        suggestions.add(stasiunSpinner);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            }else{
               return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            ArrayList<Stasiun_> tempValues = (ArrayList<Stasiun_>) results.values;
            if(results != null && results.count > 0){
                clear();
                for (Stasiun_ stasiunObj : tempValues){
                    add(stasiunObj);
                    notifyDataSetChanged();
                }
            }else {
                clear();
                notifyDataSetChanged();
            }
        }
    };
}
