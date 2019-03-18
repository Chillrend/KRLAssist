package com.a4sc11production.krlassist.fragments;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.*;

import android.widget.*;
import com.a4sc11production.krlassist.R;
import com.a4sc11production.krlassist.adapter.RealtimePositionAdapter;
import com.a4sc11production.krlassist.adapter.StasiunSpinnerAdapter;
import com.a4sc11production.krlassist.model.RealtimePosition;
import com.a4sc11production.krlassist.model.StasiunSpinner;
import com.a4sc11production.krlassist.util.ChangeActionBarAndStatusBarColor;
import es.dmoral.toasty.Toasty;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link krl_pos.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link krl_pos#newInstance} factory method to
 * create an instance of this fragment.
 */
public class krl_pos extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private String[] testingAutoComplete = {"Bleh", "Blah", "Bloh", "Blih", "Bluh"};
    private AutoCompleteTextView stasiunChooser;
    private ListView realtime_pos_listview;

    private ArrayList<StasiunSpinner> StasiunList;
    private StasiunSpinnerAdapter stasiunAdapter;

    private ArrayList<RealtimePosition> realtimeList;
    private RealtimePositionAdapter realtimeAdapter;

    private OnFragmentInteractionListener mListener;

    public krl_pos() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment krl_pos.
     */
    // TODO: Rename and change types and number of parameters
    public static krl_pos newInstance(String param1, String param2) {
        krl_pos fragment = new krl_pos();
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_krl_pos, container, false);
    }
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public void onViewCreated(View view,@Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Window window = getActivity().getWindow();
        ActionBar abar = (ActionBar) ((AppCompatActivity) getActivity()).getSupportActionBar();

        ChangeActionBarAndStatusBarColor cbar = new ChangeActionBarAndStatusBarColor(getContext());
        cbar.changeStatusActionBarColorFromFragment(window, abar, R.color.colorPrimary, R.color.colorPrimaryDark);

        stasiunChooser = (AutoCompleteTextView) view.findViewById(R.id.realtime_pos_choose_st_autotextview);

        StasiunList = new ArrayList<>();

        StasiunList.add(new StasiunSpinner("CUK", "Cakung", false, R.drawable.ic_letter_s));
        StasiunList.add(new StasiunSpinner("CTA", "Citayam", true, R.drawable.ic_letter_t));
        StasiunList.add(new StasiunSpinner("MRI", "Manggarai", true, R.drawable.ic_letter_t));
        StasiunList.add(new StasiunSpinner("DP", "Depok", false, R.drawable.ic_letter_s));
        StasiunList.add(new StasiunSpinner("BOO", "Bogor", false, R.drawable.ic_letter_s));
        StasiunList.add(new StasiunSpinner("DU", "Duri", true, R.drawable.ic_letter_t));

        stasiunAdapter = new StasiunSpinnerAdapter(getContext(), R.layout.custom_autotext_row, StasiunList);

        stasiunChooser.setThreshold(1);
        stasiunChooser.setAdapter(stasiunAdapter);

        stasiunChooser.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                StasiunSpinner stasiunSpinner = (StasiunSpinner) parent.getItemAtPosition(position);
                String stasiun_id = stasiunSpinner.getStasiun_id();
                Toasty.info(getContext(), "Anda Memilih " + stasiun_id, Toast.LENGTH_SHORT, true).show();
            }
        });

        realtime_pos_listview = (ListView) view.findViewById(R.id.realtime_pos_listview);

        realtimeList = new ArrayList<>();
        realtimeList.add(new RealtimePosition("D1/1270", "Nambo - Angke", "Berangkat Cibinong", "Loop Line", 8));
        realtimeList.add(new RealtimePosition("1571", "Bogor - Jakarta Kota", "Di Citayam", "Central Line", 12));
        realtimeList.add(new RealtimePosition("1080-1081", "Bogor - Jatinegara", "Di Bojong Gede", "Loop Line", 8));
        realtimeList.add(new RealtimePosition("1921", "Bogor - Angke", "Berangkat Bogor", "Loop Line", 10));
        realtimeList.add(new RealtimePosition("D1/1511", "Angke - Nambo", "Di Pondok Cina", "Loop Line", 10));
        realtimeList.add(new RealtimePosition("1611", "Jakarta Kota - Bogor", "Berangkat Univ. Indonesia", "Central Line", 12));
        realtimeList.add(new RealtimePosition("1271", "Jakarta Kota - Bogor", "Berangkat Lenteng Agung", "Central Line", 8));

        realtimeAdapter = new RealtimePositionAdapter(realtimeList, getContext());

        realtime_pos_listview.setAdapter(realtimeAdapter);
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
