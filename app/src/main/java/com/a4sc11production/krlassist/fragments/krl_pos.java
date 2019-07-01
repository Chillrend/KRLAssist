package com.a4sc11production.krlassist.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.*;

import android.widget.*;
import com.a4sc11production.krlassist.R;
import com.a4sc11production.krlassist.adapter.RealtimePositionAdapter;
import com.a4sc11production.krlassist.adapter.StasiunSpinnerAdapter;
import com.a4sc11production.krlassist.model.RealtimePos.Data;
import com.a4sc11production.krlassist.model.RealtimePos.RealtimePos;
import com.a4sc11production.krlassist.model.RealtimePosition;
import com.a4sc11production.krlassist.model.Stasiun.Datum;
import com.a4sc11production.krlassist.model.Stasiun.Stasiun_;
import com.a4sc11production.krlassist.model.StasiunSpinner;
import com.a4sc11production.krlassist.pojo.Krl;
import com.a4sc11production.krlassist.pojo.Line;
import com.a4sc11production.krlassist.pojo.Stasiun;
import com.a4sc11production.krlassist.pojo.Timetable;
import com.a4sc11production.krlassist.util.APIInterface.PosInterface;
import com.a4sc11production.krlassist.util.APIInterface.StasiunInterface;
import com.a4sc11production.krlassist.util.ChangeActionBarAndStatusBarColor;
import com.a4sc11production.krlassist.util.KeretaAPICall;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.support.constraint.Constraints.TAG;

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

    public final static double AVERAGE_RADIUS_OF_EARTH = 6371;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private ArrayList<com.a4sc11production.krlassist.pojo.Stasiun> stList, backupStList;

    private Map<String, Line> lineListing;

    private String[] testingAutoComplete = {"Bleh", "Blah", "Bloh", "Blih", "Bluh"};
    private AutoCompleteTextView stasiunChooser;
    private ListView realtime_pos_listview;

    private ArrayList<Datum> DatumList;
    private Stasiun_ StasiunObj;
    private StasiunSpinnerAdapter stasiunAdapter;
    private String st_id;
    private int distancesz = 99999;

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

    @Override @SuppressWarnings("unchecked")
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        lineListing = new HashMap<>();

        CollectionReference lineRef = db.collection("line");

        lineRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()){
                        Line line = document.toObject(Line.class);
                        Log.d(TAG, "onComplete: " + document.getId() + " : " + document.getData().get("line_name"));
                        lineListing.put(document.getId(), line);
                    }
                }
            }
        });


        stList = new ArrayList<>();
        backupStList = new ArrayList<>();

        CollectionReference colRef = db.collection("stasiun");

        colRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document : task.getResult()){
//                        Log.d("TEST DATA FROM FBASE", document.getId() + "=>" + document.getData());

                        Map<String,Object> data = document.getData();

                        String st_name, kode;
                        Double latitude, longitude;
                        ArrayList<String> line_served, neighbors;
                        boolean transit;

                        st_name = document.getId();
                        kode = data.get("kode").toString();
                        latitude = Double.valueOf(data.get("latitude").toString());
                        longitude = Double.valueOf(data.get("longitude").toString());

                        line_served = (ArrayList<String>)data.get("line-served");
                        neighbors = (ArrayList<String>)data.get("neighbors");


                        transit = (Boolean) data.get("transit");


                        stList.add(new com.a4sc11production.krlassist.pojo.Stasiun(st_name,kode,latitude,line_served,longitude,neighbors,transit));
                        backupStList.add(new com.a4sc11production.krlassist.pojo.Stasiun(st_name,kode,latitude,line_served,longitude,neighbors,transit));
                    }

                    stasiunAdapter = new StasiunSpinnerAdapter(getContext(), R.layout.custom_autotext_row, stList);

                    stasiunChooser.setThreshold(1);
                    stasiunChooser.setAdapter(stasiunAdapter);

                }else{
                    Toasty.error(getContext(), "Tidak dapat mengambil data stasiun, silahkan cek koneksi internet Anda.", Toast.LENGTH_SHORT, true).show();
                }
            }
        });

        return inflater.inflate(R.layout.fragment_krl_pos, container, false);
    }
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public int calculateDistance(double userLat, double userLng, double venueLat, double venueLng) {

        double latDistance = Math.toRadians(userLat - venueLat);
        double lngDistance = Math.toRadians(userLng - venueLng);

        double a = (Math.sin(latDistance / 2) * Math.sin(latDistance / 2)) +
                (Math.cos(Math.toRadians(userLat))) *
                        (Math.cos(Math.toRadians(venueLat))) *
                        (Math.sin(lngDistance / 2)) *
                        (Math.sin(lngDistance / 2));

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return (int) (Math.round(AVERAGE_RADIUS_OF_EARTH * c));

    }

    public void doCallApi(String stasiun){
        CollectionReference krlRef = db.collection("krl");
        krlRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                try {

                    Map<String, Krl> finalKrlMap = new HashMap<>();
                    Map<String, Krl> krlMap = new HashMap<>();

                    if (task.isSuccessful()) {
                        ArrayList<String> noKrl = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Map<String, Object> data = document.getData();

                            String line = data.get("line").toString();
                            Map<String, String> realtime_pos = (HashMap<String, String>) data.get("realtime-pos");
                            Map<String, Integer> schedule = (HashMap<String, Integer>) data.get("schedule");

                            com.a4sc11production.krlassist.pojo.Krl krlObject = new com.a4sc11production.krlassist.pojo.Krl(line, realtime_pos, schedule);

                            Log.d(TAG, "onComplete: " + document.getId() + " : " + line);

                            krlMap.put(document.getId(), krlObject);
                        }

                        Map<String, Krl> finalKrl = new HashMap<>();

                        ArrayList<String> line_served = new ArrayList<>();

                        for (Stasiun stat : backupStList) {
                            if (stat.getNama().equals(stasiun)) {
                                line_served = new ArrayList<>(stat.getLine_served());
                                break;
                            }
                        }

                        for (int i = 0; i < line_served.size(); i++) {
                            Line line = lineListing.get(line_served.get(i));

                            ArrayList<String> usedStasiun = new ArrayList<>();
                            ArrayList<String> loopStation = new ArrayList<>(line.getStasiun_served());
                            for (int j = 0; j < loopStation.size(); j++) {
                                if (loopStation.get(j).equals(stasiun)) {
                                    usedStasiun.add(loopStation.get(j));
                                    break;
                                } else {
                                    usedStasiun.add(loopStation.get(j));
                                }
                            }

                            for (int j = 0; j < usedStasiun.size(); j++) {
                                for (Map.Entry<String, Krl> entry : krlMap.entrySet()) {
                                    String noKrlLoop = entry.getKey();
                                    Krl krl = entry.getValue();

                                    Map<String, String> realtime_pos = krl.getRealtime_pos();

                                    if (realtime_pos.get("stasiun").equals(usedStasiun.get(j)) && krl.getLine().equals(line_served.get(i))) {
                                        Log.d(TAG, "onIteration usedStation: " + usedStasiun);
                                        Log.d(TAG, "matching Stasiun : " + usedStasiun.get(j) + ", KA " + noKrlLoop + ", Status " + realtime_pos.get("status") + " " + realtime_pos.get("stasiun"));
                                        finalKrlMap.put(noKrlLoop, krl);
                                    }
                                }
                            }
                        }

                        ArrayList<RealtimePosition> realtimePosList = new ArrayList<>();

                        for (Map.Entry<String, Krl> krlEntry : finalKrlMap.entrySet()) {
                            Krl krl = krlEntry.getValue();

                            String relasi = krl.getLine();
                            String noKa = krlEntry.getKey();

                            jadwal jdwl = new jadwal();

                            String determinedLine = jdwl.determineLine(relasi);

                            Map<String, String> realtime_pos = krl.getRealtime_pos();

                            String status = realtime_pos.get("status");
                            String stamformasi = realtime_pos.get("sf");
                            String status_stasion = realtime_pos.get("stasiun");

                            RealtimePosition rPos = new RealtimePosition(noKa, relasi, status, status_stasion, determinedLine, Integer.parseInt(stamformasi));
                            realtimePosList.add(rPos);
                        }


                        RealtimePositionAdapter rPosAdapter = new RealtimePositionAdapter(realtimePosList, getContext());
                        rPosAdapter.notifyDataSetChanged();
                        realtime_pos_listview.setAdapter(rPosAdapter);
                    }
                }catch (NullPointerException e){
                    e.printStackTrace();
                    Toasty.error(getContext(), "Tidak menemukan KRL menuju " + stasiun).show();
                }
            }
            });

    }


    public void onViewCreated(View view,@Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Window window = getActivity().getWindow();
        ActionBar abar = (ActionBar) ((AppCompatActivity) getActivity()).getSupportActionBar();

        ChangeActionBarAndStatusBarColor cbar = new ChangeActionBarAndStatusBarColor(getContext());
        cbar.changeStatusActionBarColorFromFragment(window, abar, R.color.colorPrimary, R.color.colorPrimaryDark);

        stasiunChooser = (AutoCompleteTextView) view.findViewById(R.id.realtime_pos_choose_st_autotextview);
        realtime_pos_listview = (ListView) view.findViewById(R.id.realtime_pos_listview);

        stasiunChooser.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Stasiun stasiunSpinner = (Stasiun) parent.getItemAtPosition(position);
                String stasiun_id = stasiunSpinner.getNama();

                doCallApi(stasiun_id);
            }
        });
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
