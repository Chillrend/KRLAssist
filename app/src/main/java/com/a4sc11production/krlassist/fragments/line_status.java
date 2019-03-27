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
import android.widget.ListView;
import com.a4sc11production.krlassist.R;
import com.a4sc11production.krlassist.adapter.GangguanAdapter;
import com.a4sc11production.krlassist.model.Gangguan;
import com.a4sc11production.krlassist.model.GangguanHome.GangguanHome;
import com.a4sc11production.krlassist.model.GangguanList.Affected;
import com.a4sc11production.krlassist.model.GangguanList.Datum;
import com.a4sc11production.krlassist.model.GangguanList.GangguanList;
import com.a4sc11production.krlassist.util.APIInterface.GangguanInterface;
import com.a4sc11production.krlassist.util.ChangeActionBarAndStatusBarColor;
import com.a4sc11production.krlassist.util.DialogShow;
import com.a4sc11production.krlassist.util.KeretaAPICall;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link line_status.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link line_status#newInstance} factory method to
 * create an instance of this fragment.
 */
public class line_status extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private ArrayList<Gangguan> gangguanList;
    private GangguanAdapter gangguanAdapter;

    private OnFragmentInteractionListener mListener;

    public line_status() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment line_status.
     */
    // TODO: Rename and change types and number of parameters
    public static line_status newInstance(String param1, String param2) {
        line_status fragment = new line_status();
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
        return inflater.inflate(R.layout.fragment_line_status, container, false);
    }

    public void onViewCreated(View view,@Nullable Bundle savedInstanceState) {
        Window window = getActivity().getWindow();
        ActionBar abar = (ActionBar) ((AppCompatActivity) getActivity()).getSupportActionBar();


        ChangeActionBarAndStatusBarColor cbar = new ChangeActionBarAndStatusBarColor(getContext());
        cbar.changeStatusActionBarColorFromFragment(window, abar, R.color.colorPrimary, R.color.colorPrimaryDark);

        ListView lv = view.findViewById(R.id.status_list);

        gangguanList = new ArrayList<>();

        KeretaAPICall krlapi = new KeretaAPICall();
        GangguanInterface gangguanInterface = krlapi.getClient().create(GangguanInterface.class);
        Call<GangguanList> getList = gangguanInterface.getGangguanList("https://api.clude.xyz/gangguan/");
        getList.enqueue(new Callback<GangguanList>() {
            @Override
            public void onResponse(Call<GangguanList> call, Response<GangguanList> response) {
                GangguanList gl = response.body();

                ArrayList<Datum> datumList = gl.getData();
                for (Datum datum : datumList) {
                    com.a4sc11production.krlassist.model.GangguanList.Gangguan gangguan = datum.getGangguan();
                    ArrayList<Affected> affecteds = datum.getAffected();
                    String line_name, stasiun_nearest, short_desc, long_desc, severity;
                    int size = affecteds.size();
                    for (int i = 0; i<size; i++) {
                        short_desc = gangguan.getShortDesc();
                        long_desc = gangguan.getLongDesc();
                        severity = gangguan.getSeverity();
                        stasiun_nearest = gangguan.getStasiunNearest();
                        line_name = affecteds.get(i).getLineName();
                        gangguanList.add(new Gangguan(line_name,short_desc,long_desc,stasiun_nearest,severity));
                    }
                }

                gangguanAdapter = new GangguanAdapter(gangguanList,getContext());
                lv.setAdapter(gangguanAdapter);

            }

            @Override
            public void onFailure(Call<GangguanList> call, Throwable t) {

            }
        });


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Gangguan g = gangguanList.get(position);

                String line = g.getLine_name();
                String short_desc = g.getShort_desc();
                String long_desc = g.getLong_desc();
                String severity = g.getSeverity();

                DialogShow alert = new DialogShow();
                alert.showDialogBox(getActivity(), line, long_desc, severity);

            }
        });
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
        void onFragmentInteraction(Uri uri);
    }
}
