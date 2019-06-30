package com.a4sc11production.krlassist.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.*;
import com.a4sc11production.krlassist.R;
import com.a4sc11production.krlassist.adapter.StasiunSpinnerAdapter;
import com.a4sc11production.krlassist.adapter.TimetableAdapter;
import com.a4sc11production.krlassist.model.Stasiun.Datum;
import com.a4sc11production.krlassist.pojo.Timetable;
import com.a4sc11production.krlassist.model.TimetableAP.Timetable_;
import com.a4sc11production.krlassist.pojo.Krl;
import com.a4sc11production.krlassist.util.ChangeActionBarAndStatusBarColor;
import com.codetroopers.betterpickers.radialtimepicker.RadialTimePickerDialogFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import es.dmoral.toasty.Toasty;

import java.text.SimpleDateFormat;
import java.util.*;

import static android.support.constraint.Constraints.TAG;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link jadwal.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link jadwal#newInstance} factory method to
 * create an instance of this fragment.
 */
public class jadwal extends Fragment implements RadialTimePickerDialogFragment.OnTimeSetListener{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    private String time_1,time_2,stasiun_id;
    private int time_1_int, time_2_int;
    private static final String FRAG_TAG_TIME_PICKER = "timePickerDialogFragment";
    private Date date_now = new Date();
    private boolean isStartTimePicked = true;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private String train_no, relasi, line_name, dep_time, stasiun;

    private ArrayList<Datum> DatumList;
    private ArrayList<com.a4sc11production.krlassist.pojo.Stasiun> stList, backupStList;

    private ArrayList<Timetable_> timetable_resp_list;

    private StasiunSpinnerAdapter stasiunAdapter;

    private ArrayList<Timetable> timetableList;
    private TimetableAdapter timetableAdapter;

    private AutoCompleteTextView stasiunChooser;
    private EditText time_picker_1, time_picker_2;
    private ListView lv;

    private OnFragmentInteractionListener mListener;

    public jadwal() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment jadwal.
     */
    // TODO: Rename and change types and number of parameters
    public static jadwal newInstance(String param1, String param2) {
        jadwal fragment = new jadwal();
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
        date_now = new Date();

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


        return inflater.inflate(R.layout.fragment_jadwal, container, false);

    }

    public void onViewCreated(View view,@Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        date_now = new Date();

        Window window = getActivity().getWindow();
        ActionBar abar = (ActionBar) ((AppCompatActivity) getActivity()).getSupportActionBar();


        ChangeActionBarAndStatusBarColor cbar = new ChangeActionBarAndStatusBarColor(getContext());
        cbar.changeStatusActionBarColorFromFragment(window, abar, R.color.colorPrimary, R.color.colorPrimaryDark);

        stasiunChooser = (AutoCompleteTextView) view.findViewById(R.id.timetable_krl_stasiun_autotextview);

        stasiunChooser.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                com.a4sc11production.krlassist.pojo.Stasiun stasiunSpinner = (com.a4sc11production.krlassist.pojo.Stasiun) parent.getItemAtPosition(position);
                stasiun_id = stasiunSpinner.getNama();

                if(evalForm()){
                    doCallApiRequests(stasiun_id,convertTimeToInt(time_1),convertTimeToInt(time_2));
                }
            }
        });

        lv = (ListView) view.findViewById(R.id.timetable_list);

        time_picker_1 = (EditText) view.findViewById(R.id.time_picker_1);
        time_picker_2 = (EditText) view.findViewById(R.id.time_picker_2);

        Calendar cal = Calendar.getInstance();

//        cal.setTime(new Date());

        SimpleDateFormat hours_default = new SimpleDateFormat("HH");
        SimpleDateFormat minute_default = new SimpleDateFormat("mm");

        cal.setTime(date_now);

        String hour_def = hours_default.format(cal.getTime());
        String minute_def = minute_default.format(cal.getTime());

        time_1 = hour_def + ":" + minute_def;
        time_picker_1.setText(time_1);

        cal.add(Calendar.HOUR_OF_DAY, 3);

        String hour_last_def = hours_default.format(cal.getTime());
        String minute_last_def = minute_default.format(cal.getTime());

        time_2 = hour_last_def + ":" + minute_last_def;
        time_picker_2.setText(time_2);

        time_picker_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isStartTimePicked = true;

                RadialTimePickerDialogFragment rtpd = new RadialTimePickerDialogFragment()
                        .setStartTime(Integer.parseInt(hour_def),Integer.parseInt(minute_def))
                        .setDoneText("Selesai")
                        .setThemeCustom(R.style.MyCustomBetterPickersDialogs)
                        .setForced24hFormat()
                        .setOnTimeSetListener(jadwal.this)
                        .setCancelText("Batal");

                rtpd.show(getFragmentManager(), FRAG_TAG_TIME_PICKER);
            }
        });

        time_picker_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isStartTimePicked = false;

                RadialTimePickerDialogFragment rtpd = new RadialTimePickerDialogFragment()
                        .setStartTime(Integer.parseInt(hour_last_def),Integer.parseInt(minute_last_def))
                        .setDoneText("Selesai")
                        .setCancelText("Batal")
                        .setOnTimeSetListener(jadwal.this)
                        .setForced24hFormat()
                        .setThemeCustom(R.style.MyCustomBetterPickersDialogs);
                rtpd.show(getFragmentManager(), FRAG_TAG_TIME_PICKER);
            }
        });


    }

    @Override
    public void onTimeSet(RadialTimePickerDialogFragment dialog, int hourOfDay, int minute){
        if(isStartTimePicked){
            time_1 = hourOfDay + ":" + minute;
            time_picker_1.setText(hourOfDay + ":" + minute);
        }else{
            time_2 = hourOfDay + ":" + minute;
            time_picker_2.setText(hourOfDay + ":" + minute);
            if(evalForm()){
                doCallApiRequests(stasiun_id,convertTimeToInt(time_1),convertTimeToInt(time_2));
            }
        }
    }

    public int convertTimeToInt(String time){
        List<String> timeList = Arrays.asList(time.split(":"));
        int hour;

        int minute;

        if(timeList.get(1).startsWith("0")){
            hour = Integer.valueOf(timeList.get(0));
            return (hour * 100) + Integer.valueOf(timeList.get(1));
        }else if(timeList.get(0).contentEquals("0")){
            hour = 25;
            minute = Integer.parseInt(timeList.get(1));
            return Integer.valueOf(String.valueOf(hour) + String.valueOf(minute));
        }else{
            hour = Integer.valueOf(timeList.get(0));
            minute = Integer.parseInt(timeList.get(1));
            return Integer.valueOf(String.valueOf(hour) + String.valueOf(minute));
        }
    }


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        date_now = new Date();
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

        date_now = new Date();
    }

    public boolean evalForm(){
        if(!stasiun_id.equals("") && !stasiun_id.equals(null) && !time_1.equals("") && !time_1.equals(null) && !time_2.equals("") && !time_2.equals(null)){
            return true;
        }else {
            return false;
        }
    }

    public void doCallApiRequests(String st_id, int time_range1, int time_range2){

        ArrayList<Krl> krlList = new ArrayList<>();
        ArrayList<String> krlNo = new ArrayList<>();

        CollectionReference krlRef = db.collection("krl");
        krlRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document : task.getResult()){
                        Krl krlObject = document.toObject(Krl.class);

                        try{
                            Map<String,Integer> schedule = krlObject.getSchedule();
                            Integer value = schedule.containsKey(st_id) ? schedule.get(st_id) : 0;
                            Log.d(TAG, "onComplete: int value = " + value);
                            if(value != null && value > time_range1 && value < time_range2){
                                krlList.add(krlObject);
                                krlNo.add(document.getId());

                                Log.d(TAG, "on time evaluation: " + krlNo + " : " + st_id + " " + value);
                            }else{
                                Log.d(TAG, "no schedule found");
                            }
                        }catch (NullPointerException e){
                            e.printStackTrace();
                            Log.e(TAG, e.getMessage());
                        }
                    }
                    renderJadwalListView(krlList, backupStList, krlNo, st_id);
                }else{
                    Log.e(TAG, "onComplete: Error getting document", task.getException());
                }
            }
        });

//        ArrayList<com.a4sc11production.krlassist.model.TimetableAP.Timetable> stasiunList = new ArrayList<>();
//
//        KeretaAPICall krlapi = new KeretaAPICall();
//        TimetableInterface timetableInterface = krlapi.getClient().create(TimetableInterface.class);
//        Call<com.a4sc11production.krlassist.model.TimetableAP.Timetable> calls = timetableInterface.getJadwal("https://api.clude.xyz/timetable/getrange/"+ st_id +"/from/"+ time_range1 +"/to/" + time_range2);
//        calls.enqueue(new Callback<com.a4sc11production.krlassist.model.TimetableAP.Timetable>() {
//            @Override
//            public void onResponse(Call<com.a4sc11production.krlassist.model.TimetableAP.Timetable> call, Response<com.a4sc11production.krlassist.model.TimetableAP.Timetable> response) {
//                String display_response = "";
//                try{
//                    com.a4sc11production.krlassist.model.TimetableAP.Timetable timetable = response.body();
//                    timetableList = new ArrayList<>();
//                    timetableList.clear();
//                    Data data = timetable.getData();
//                    timetable_resp_list = data.getTimetable();
//                    for (Timetable_ timetables : timetable_resp_list) {
//                        train_no = timetables.getTrainNo();
//                        relasi = timetables.getRelasi();
//                        stasiun = timetables.getStasiun();
//                        line_name = timetables.getLineName();
//                        dep_time = timetables.getDepTime().substring(0,5);
//
//                        timetableList.add(new Timetable(train_no,relasi,stasiun,line_name,dep_time));
//                    }
//
//                    timetableAdapter = new TimetableAdapter(timetableList,getContext());
//                    lv.setAdapter(timetableAdapter);
//
//                }catch (Exception E){
//                    Log.e("On Timetable Call", "can"t get Timetable, reason:" + E.toString());
//                    E.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<com.a4sc11production.krlassist.model.TimetableAP.Timetable> call, Throwable t) {
//                Log.e("On Timetable Call", "can"t get Timetable, reason:" + t.toString());
//                t.printStackTrace();
//            }
//        });
    }

    public void renderJadwalListView(ArrayList<Krl> krl, ArrayList<com.a4sc11production.krlassist.pojo.Stasiun> statList, ArrayList<String> noKrl, String stasiun_id){
        ArrayList<Timetable> jadwalList = new ArrayList<>();
        for (int i = 0; i < krl.size(); i++) {
            Krl krlObj = krl.get(i);
            String noKa = noKrl.get(i);
            String strDepartureTime = "";

            Map<String,Integer> jadwal = krlObj.getSchedule();

            Integer departureTime = jadwal.get(stasiun_id);
            if(departureTime != null){
                strDepartureTime = departureTime.toString();
                if(strDepartureTime.length() < 4){
                    strDepartureTime = "0" + strDepartureTime;

                    StringBuilder builder = new StringBuilder(strDepartureTime);
                    builder.insert(2, ":");
                    strDepartureTime = builder.toString();
                }else{
                    StringBuilder builder = new StringBuilder(strDepartureTime);
                    builder.insert(2, ":");
                    strDepartureTime = builder.toString();
                }
            }
            String relasi = krlObj.getLine();
            String[] relasiSplit = relasi.split("-");
            String terminal = "", terminus = "";
            for(com.a4sc11production.krlassist.pojo.Stasiun stat : statList){
                Log.d(TAG, "renderJadwalListView in iteration : "+ i + ": " + stat.getKode());
                if(stat.getKode().equals(relasiSplit[0])){
                    terminal = stat.getNama();
                }else if(stat.getKode().equals(relasiSplit[1])){
                    terminus = stat.getNama();
                }

                if(terminal != null && !terminal.isEmpty() && terminus != null && !terminus.isEmpty()){
                    break;
                }
            }

            relasi = terminal + "-" + terminus;
            String line = determineLine(relasi);

            Timetable finalSchedule = new Timetable(noKa, relasi, stasiun_id, line, strDepartureTime);
            jadwalList.add(finalSchedule);
        }

        timetableAdapter = new TimetableAdapter(jadwalList, getContext());
        lv.setAdapter(timetableAdapter);

    }

    public String determineLine(String line){
        switch (line) {
            case "DP-AK":
            case "AK-DP":
            case "DP-JNG":
            case "JNG-DP":
            case "BOO-AK":
            case "AK-BOO":
            case "JNG-BOO":
            case "BOO-JNG":
            case "NMO-AK":
            case "AK-NMO":
                return "Loop Line";
            case "BOO-JAKK":
            case "JAKK-BOO":
            case "DP-MRI":
            case "MRI-DP":
            case "BOO-MRI":
            case "MRI-BOO":
            case "JAKK-DP":
            case "DP-JAKK":
                return "Central Line";
            case "JAKK-CKR":
            case "CKR-JAKK":
            case "MRI-CKR":
            case "CKR-MRI":
            case "MRI-BKS":
            case "BKS-MRI":
            case "BKS-JAKK":
            case "JAKK-BKS":
                return "Bekasi Line";
            case "Tangerang Line":
                return "Tangerang Line";
            case "Rangkasbitung Line":
                return "Rangkasbitung Line";
            default:
                return "Central Line";
        }
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
