package com.a4sc11production.krlassist.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.a4sc11production.krlassist.HomeActivity;
import com.a4sc11production.krlassist.R;
import com.a4sc11production.krlassist.model.GangguanHome.AfterGangguan.GangguanLine;
import com.a4sc11production.krlassist.model.GangguanHome.Datum;
import com.a4sc11production.krlassist.model.GangguanHome.Gangguan;
import com.a4sc11production.krlassist.model.GangguanHome.GangguanHome;
import com.a4sc11production.krlassist.model.RealtimePos.Line;
import com.a4sc11production.krlassist.util.APIInterface.GangguanInterface;
import com.a4sc11production.krlassist.util.ChangeActionBarAndStatusBarColor;
import com.a4sc11production.krlassist.util.KeretaAPICall;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.kennyc.view.MultiStateView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link home.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link home#newInstance} factory method to
 * create an instance of this fragment.
 */
public class home extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private String short_desc,stasiun_nearest,severity;
    private Integer gangguan_id;

    private TextView gangguan_short_desc, gangguan_affected_stasiun, nama_line_gangguan_1, nama_line_gangguan_2, nama_line_gangguan_3, jalurt_terganggu_static;
    private TextView gangguan_normal_text;
    private ImageView gangguan_icon, icon_gangguan_line_1, icon_gangguan_line_2, icon_gangguan_line_3;
    private MultiStateView gangguan_multistate;
    private LinearLayout line_container_1, line_container_2, line_container_3, btn_info_lanjut;
    private LinearLayout btn_1,btn_2,btn_3;

    Window window;
    ActionBar abar;

    private OnFragmentInteractionListener mListener;

    public home() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment home.
     */
    // TODO: Rename and change types and number of parameters
    public static home newInstance(String param1, String param2) {
        home fragment = new home();
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

        return inflater.inflate(R.layout.fragment_home, container, false);
    }
    public void onViewCreated(View view,@Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btn_1 = (LinearLayout) view.findViewById(R.id.btn_1);
        btn_2 = (LinearLayout) view.findViewById(R.id.btn_2);
        btn_3 = (LinearLayout) view.findViewById(R.id.btn_3);

        btn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new krl_pos();
                ((HomeActivity) getActivity()).displaySpecificFragment(fragment, "REALTIME_POS_FRAGMENT");
            }
        });

        btn_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new jadwal();
                ((HomeActivity) getActivity()).displaySpecificFragment(fragment, "TIMETABLE_FRAGMENT");
            }
        });

        btn_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new nfc_kmt();
                ((HomeActivity) getActivity()).displaySpecificFragment(fragment, "NFC_KMT_FRAGMENT");
            }
        });

        gangguan_short_desc = (TextView) view.findViewById(R.id.gangguan_short_desc);
        gangguan_affected_stasiun = (TextView) view.findViewById(R.id.gangguan_affected_station);
        nama_line_gangguan_1 = (TextView) view.findViewById(R.id.nama_line_gangguan_1);
        nama_line_gangguan_2 = (TextView) view.findViewById(R.id.nama_line_gangguan_2);
        nama_line_gangguan_3 = (TextView) view.findViewById(R.id.nama_line_gangguan_3);
        jalurt_terganggu_static = (TextView) view.findViewById(R.id.jalur_terganggu_text);
        gangguan_normal_text = (TextView) view.findViewById(R.id.gangguan_normal_text);
        gangguan_icon = (ImageView) view.findViewById(R.id.gangguan_icon);
        icon_gangguan_line_1 = (ImageView) view.findViewById(R.id.icon_gangguan_line_1);
        icon_gangguan_line_2 = (ImageView) view.findViewById(R.id.icon_gangguan_line_2);
        icon_gangguan_line_3 = (ImageView) view.findViewById(R.id.icon_gangguan_line_3);
        gangguan_multistate = (MultiStateView) view.findViewById(R.id.gangguan_multi_state);
        line_container_1 = (LinearLayout) view.findViewById(R.id.line_container_1);
        line_container_2 = (LinearLayout) view.findViewById(R.id.line_container_2);
        line_container_3 = (LinearLayout) view.findViewById(R.id.line_container_3);
        btn_info_lanjut = (LinearLayout) view.findViewById(R.id.btn_info_lanjut);

        gangguan_multistate.setViewState(MultiStateView.VIEW_STATE_LOADING);

        window = getActivity().getWindow();
        abar = (ActionBar) ((AppCompatActivity) getActivity()).getSupportActionBar();
        ChangeActionBarAndStatusBarColor cbar = new ChangeActionBarAndStatusBarColor(getContext());

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("DEFAULT_LINE", Context.MODE_PRIVATE);
        String default_line = sharedPreferences.getString("def_line", "Central Line");

        DocumentReference docRef = db.collection("gangguan").document(default_line);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                com.a4sc11production.krlassist.pojo.Gangguan gangguan = documentSnapshot.toObject(com.a4sc11production.krlassist.pojo.Gangguan.class);


            }
        });

        KeretaAPICall krlapi = new KeretaAPICall();
        GangguanInterface gangguanInterface = krlapi.getClient().create(GangguanInterface.class);
        Call<GangguanHome> callGangguanFirst = gangguanInterface.getGangguanForHomePage("https://api.clude.xyz/gangguan/line/" + default_line);
        callGangguanFirst.enqueue(new Callback<GangguanHome>() {
            @Override
            public void onResponse(Call<GangguanHome> call, Response<GangguanHome> response) {

                GangguanHome gh = response.body();
                ArrayList<Datum> datumArrayList = new ArrayList<>();
                datumArrayList = gh.getData();
                for (Datum data: datumArrayList) {
                    Gangguan gangguan = data.getGangguan();
                    short_desc = gangguan.getShortDesc();
                    severity = gangguan.getSeverity();
                    stasiun_nearest = gangguan.getStasiunNearest();
                    gangguan_id = gangguan.getGangguanId();
                }

                gangguan_short_desc.setText(short_desc);
                gangguan_affected_stasiun.setText("Stasiun " + stasiun_nearest);


                KeretaAPICall apis = new KeretaAPICall();
                GangguanInterface gangInt = krlapi.getClient().create(GangguanInterface.class);
                Call<GangguanLine> calling = gangInt.getGangguanAfterAbove("https://api.clude.xyz/gangguan/" + gangguan_id);
                calling.enqueue(new Callback<GangguanLine>() {
                    @Override
                    public void onResponse(Call<GangguanLine> call, Response<GangguanLine> response) {
                        GangguanLine gl = response.body();
                        try{
                            ArrayList<com.a4sc11production.krlassist.model.GangguanHome.AfterGangguan.Datum> afterGangguanList = new ArrayList<>();
                            afterGangguanList = gl.getData();
                            ArrayList<String> line_affected = new ArrayList<>();

                            for (com.a4sc11production.krlassist.model.GangguanHome.AfterGangguan.Datum datum : afterGangguanList) {
                                line_affected = datum.getLinename();
                            }

                            setLine(line_affected);

                            if(severity.equals("Normal")){
                                cbar.changeStatusActionBarColorFromFragment(window,abar,R.color.colorNormal, R.color.colorNormalDark);
                                gangguan_icon.setImageResource(R.drawable.ic_done_all_black_24dp);
                                gangguan_multistate.setBackgroundColor(getActivity().getResources().getColor(R.color.colorNormal));

                                gangguan_affected_stasiun.setText(line_affected.get(0));

                                line_container_1.setVisibility(View.GONE);
                                line_container_2.setVisibility(View.GONE);
                                line_container_3.setVisibility(View.GONE);
                                jalurt_terganggu_static.setVisibility(View.GONE);
                                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) btn_info_lanjut.getLayoutParams();
                                params.addRule(RelativeLayout.BELOW, R.id.gangguan_normal_text);
                                gangguan_normal_text.setVisibility(View.VISIBLE);
                            }else if(severity.equals("Medium")){
                                cbar.changeStatusActionBarColorFromFragment(window,abar,R.color.colorWarning, R.color.colorWarningDark);
                                gangguan_icon.setImageResource(R.drawable.ic_warning);
                                gangguan_multistate.setBackgroundColor(getActivity().getResources().getColor(R.color.colorWarning));
                            }else if(severity.equals("Severe")){
                                cbar.changeStatusActionBarColorFromFragment(window,abar,R.color.colorDanger, R.color.colorDangerDark);
                                gangguan_icon.setImageResource(R.drawable.ic_danger);
                                gangguan_multistate.setBackgroundColor(getActivity().getResources().getColor(R.color.colorDanger));
                            }
                        }catch(Exception e){
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(Call<GangguanLine> call, Throwable t) {
                        gangguan_multistate.setViewState(MultiStateView.VIEW_STATE_ERROR);
                        t.printStackTrace();
                    }
                });
                gangguan_multistate.setViewState(MultiStateView.VIEW_STATE_CONTENT);
            }

            @Override
            public void onFailure(Call<GangguanHome> call, Throwable t) {
                gangguan_multistate.setViewState(MultiStateView.VIEW_STATE_ERROR);
                t.printStackTrace();
            }
        });

        btn_info_lanjut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new line_status();
                ((HomeActivity) getActivity()).displaySpecificFragment(fragment, "LINE_STATUS_FRAGMENT");
            }
        });

    }

    public void setLine(ArrayList<String> line){
        if(line.size() == 1){
            line_container_1.setVisibility(View.VISIBLE);
            line_container_2.setVisibility(View.GONE);
            line_container_3.setVisibility(View.GONE);

            String nama_lines = line.get(0);
            nama_line_gangguan_1.setText(nama_lines);
            int icon = evalLine(nama_lines);
            icon_gangguan_line_1.setImageResource(icon);
        }else if(line.size() == 2){
            line_container_1.setVisibility(View.VISIBLE);
            line_container_2.setVisibility(View.VISIBLE);
            line_container_3.setVisibility(View.GONE);

            String nama_lines = line.get(0);
            nama_line_gangguan_1.setText(nama_lines);
            int icon = evalLine(nama_lines);
            icon_gangguan_line_1.setImageResource(icon);

            nama_lines = line.get(1);
            nama_line_gangguan_2.setText(nama_lines);
            icon = evalLine(nama_lines);
            icon_gangguan_line_2.setImageResource(icon);
        }else if(line.size() == 3){
            line_container_1.setVisibility(View.VISIBLE);
            line_container_2.setVisibility(View.VISIBLE);
            line_container_3.setVisibility(View.VISIBLE);

            String nama_lines = line.get(0);
            nama_line_gangguan_1.setText(nama_lines);
            int icon = evalLine(nama_lines);
            icon_gangguan_line_1.setImageResource(icon);

            nama_lines = line.get(1);
            nama_line_gangguan_2.setText(nama_lines);
            icon = evalLine(nama_lines);
            icon_gangguan_line_2.setImageResource(icon);

            nama_lines = line.get(2);
            nama_line_gangguan_3.setText(nama_lines);
            icon = evalLine(nama_lines);
            icon_gangguan_line_3.setImageResource(icon);
        }
    }

    public int evalLine(String line){
        if (line.equals("Central Line")) {
            return R.drawable.ic_red_line_18dp;
        } else if (line.equals("Loop Line")) {
            return R.drawable.ic_loop_line_18dp;
        } else if (line.equals("Rangkasbitung Line")) {
            return R.drawable.ic_rangkasbitung_line_18dp;
        } else if (line.equals("Bekasi Line")) {
            return R.drawable.ic_bekasi_line_18dp;
        } else if (line.equals("Tangerang Line")) {
            return R.drawable.ic_tangerang_line_18dp;
        } else if (line.equals("Tanjung Priok Line")) {
            return R.drawable.ic_tanjung_priok_18dp;
        }else{
            return 0;
        }
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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
