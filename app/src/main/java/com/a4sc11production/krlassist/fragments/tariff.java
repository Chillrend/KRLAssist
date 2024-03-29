package com.a4sc11production.krlassist.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.view.Window;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import com.a4sc11production.krlassist.R;
import com.a4sc11production.krlassist.adapter.StasiunSpinnerAdapter;
import com.a4sc11production.krlassist.model.Stasiun.Datum;
import com.a4sc11production.krlassist.model.Stasiun.Stasiun;
import com.a4sc11production.krlassist.model.Stasiun.Stasiun_;
import com.a4sc11production.krlassist.util.APIInterface.StasiunInterface;
import com.a4sc11production.krlassist.util.ChangeActionBarAndStatusBarColor;
import com.a4sc11production.krlassist.util.KeretaAPICall;
import com.kennyc.view.MultiStateView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link tariff.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link tariff#newInstance} factory method to
 * create an instance of this fragment.
 */
public class tariff extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    private ArrayList<Datum> DatumList;
    private Stasiun_ StasiunObj;
    ArrayList<Stasiun_> stasiunList;
    private Double lat1,lng1,lat2,lng2;

    private StasiunSpinnerAdapter stasiunAdapter;
    private OnFragmentInteractionListener mListener;


    private String st_1,st_2;

    private AutoCompleteTextView stasiunChooser1, stasiunChooser2;
    private TextView stasiun1,stasiun2,tarifs;

    public tariff() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment tariff.
     */
    public static tariff newInstance(String param1, String param2) {
        tariff fragment = new tariff();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        stasiunList = new ArrayList<>();

        KeretaAPICall krlapi = new KeretaAPICall();
        StasiunInterface stasiunInterface = krlapi.getClient().create(StasiunInterface.class);
        Call<Stasiun> calls = stasiunInterface.getStasiun("https://api.clude.xyz/stasiun");
        calls.enqueue(new Callback<Stasiun>() {
            @Override
            public void onResponse(Call<Stasiun> call, Response<Stasiun> response) {
                String display_response = "";
                try{
                    Stasiun stasiun = response.body();
                    stasiunList.clear();
                    DatumList = stasiun.getData();
                    for (Datum datum : DatumList) {
                        StasiunObj = datum.getStasiun();
                        stasiunList.add(StasiunObj);
                    }

                    stasiunAdapter = new StasiunSpinnerAdapter(getContext(), R.layout.custom_autotext_row, stasiunList);

                    stasiunChooser1.setThreshold(1);
                    stasiunChooser1.setAdapter(stasiunAdapter);

                    stasiunChooser2.setThreshold(1);
                    stasiunChooser2.setAdapter(stasiunAdapter);
                }catch (Exception E){
                    Log.e("On Stasiun Call", "can't get stasiun, reason:" + E.toString());
                    E.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Stasiun> call, Throwable t) {
                Log.e("On Stasiun Call", "can't get stasiun, reason:" + t.toString());
                t.printStackTrace();
            }
        });

        return inflater.inflate(R.layout.fragment_tariff, container, false);
    }

    public void onViewCreated(View view,@Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Window window = getActivity().getWindow();
        ActionBar abar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        ChangeActionBarAndStatusBarColor cbar = new ChangeActionBarAndStatusBarColor(getContext());
        cbar.changeStatusActionBarColorFromFragment(window,abar,R.color.colorPrimary,R.color.colorPrimaryDark);

        MultiStateView mvs = view.findViewById(R.id.multi_state_tariff);
        mvs.setViewState(MultiStateView.VIEW_STATE_EMPTY);

        stasiunChooser1 = (AutoCompleteTextView) view.findViewById(R.id.tariff_stasiun_box_1);
        stasiunChooser2 = (AutoCompleteTextView) view.findViewById(R.id.tariff_stasiun_box_2);

        stasiun1 = (TextView) view.findViewById(R.id.stasiun_1);
        stasiun2 = (TextView) view.findViewById(R.id.stasiun_2);
        tarifs = (TextView) view.findViewById(R.id.Tarifs);

        stasiunChooser1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Stasiun_ stat =  stasiunList.get(position);
                lat1 = Double.parseDouble(stat.getLat());
                lng1 = Double.parseDouble(stat.getLng());

                st_1 = stat.getNama();
            }
        });

        stasiunChooser2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Stasiun_ stat =  stasiunList.get(position);

                st_2 = stat.getNama();

                lat2 = Double.parseDouble(stat.getLat());
                lng2 = Double.parseDouble(stat.getLng());

                if(!lat1.isNaN() && !lng1.isNaN() && !lat2.isNaN() && !lng2.isNaN()){
                    double Dist = distance(lat1,lat2,lng1,lng2,10,10);

                    DecimalFormat df = new DecimalFormat("#");
                    String formattedDistance = df.format(Dist);
                    int formattedIntDistance = Integer.parseInt(formattedDistance) / 1000;
                    int finalPrice;
                    if(formattedIntDistance <= 25){
                        finalPrice = 3000;
                    }else{
                        formattedIntDistance = formattedIntDistance - 25;
                        finalPrice = ((formattedIntDistance/10) * 1000) + 3000;
                    }

                    stasiun1.setText(st_1);
                    stasiun2.setText(st_2);
                    tarifs.setText(String.valueOf(finalPrice));
                    mvs.setViewState(MultiStateView.VIEW_STATE_CONTENT);
                }
            }
        });



    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public static double distance(double lat1, double lat2, double lon1,
                                  double lon2, double el1, double el2) {

        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        double height = el1 - el2;

        distance = Math.pow(distance, 2) + Math.pow(height, 2);

        return Math.sqrt(distance);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
