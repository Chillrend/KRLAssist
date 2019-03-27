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
import android.widget.*;
import com.a4sc11production.krlassist.R;
import com.a4sc11production.krlassist.adapter.StasiunSpinnerAdapter;
import com.a4sc11production.krlassist.adapter.TimetableAdapter;
import com.a4sc11production.krlassist.model.Stasiun.Datum;
import com.a4sc11production.krlassist.model.Stasiun.Stasiun;
import com.a4sc11production.krlassist.model.Stasiun.Stasiun_;
import com.a4sc11production.krlassist.model.StasiunSpinner;
import com.a4sc11production.krlassist.model.Timetable;
import com.a4sc11production.krlassist.model.TimetableAP.Data;
import com.a4sc11production.krlassist.model.TimetableAP.Timetable_;
import com.a4sc11production.krlassist.util.APIInterface.StasiunInterface;
import com.a4sc11production.krlassist.util.APIInterface.TimetableInterface;
import com.a4sc11production.krlassist.util.ChangeActionBarAndStatusBarColor;
import com.a4sc11production.krlassist.util.KeretaAPICall;
import com.codetroopers.betterpickers.radialtimepicker.RadialTimePickerDialogFragment;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


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

    private ArrayList<Stasiun> stasiunList;
    private String time_1,time_2,stasiun_id;
    private static final String FRAG_TAG_TIME_PICKER = "timePickerDialogFragment";
    private Date date_now = new Date();
    private boolean isStartTimePicked = true;

    private String train_no, relasi, line_name, dep_time, stasiun;

    private ArrayList<Datum> DatumList;

    private ArrayList<Timetable_> timetable_resp_list;

    private Stasiun_ StasiunObj;
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

        ArrayList<Stasiun_> stasiunList = new ArrayList<>();

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

                    stasiunChooser.setThreshold(1);
                    stasiunChooser.setAdapter(stasiunAdapter);
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
                Stasiun_ stasiunSpinner = (Stasiun_) parent.getItemAtPosition(position);
                stasiun_id = stasiunSpinner.getStasiunId();

                if(evalForm()){
                    doCallApiRequests(stasiun_id,time_1,time_2);
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
                doCallApiRequests(stasiun_id,time_1,time_2);
            }
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

    public void doCallApiRequests(String st_id, String time_range1, String time_range2){
        ArrayList<com.a4sc11production.krlassist.model.TimetableAP.Timetable> stasiunList = new ArrayList<>();


        KeretaAPICall krlapi = new KeretaAPICall();
        TimetableInterface timetableInterface = krlapi.getClient().create(TimetableInterface.class);
        Call<com.a4sc11production.krlassist.model.TimetableAP.Timetable> calls = timetableInterface.getJadwal("https://api.clude.xyz/timetable/getrange/"+ st_id +"/from/"+ time_range1 +"/to/" + time_range2);
        calls.enqueue(new Callback<com.a4sc11production.krlassist.model.TimetableAP.Timetable>() {
            @Override
            public void onResponse(Call<com.a4sc11production.krlassist.model.TimetableAP.Timetable> call, Response<com.a4sc11production.krlassist.model.TimetableAP.Timetable> response) {
                String display_response = "";
                try{
                    com.a4sc11production.krlassist.model.TimetableAP.Timetable timetable = response.body();
                    timetableList = new ArrayList<>();
                    timetableList.clear();
                    Data data = timetable.getData();
                    timetable_resp_list = data.getTimetable();
                    for (Timetable_ timetables : timetable_resp_list) {
                        train_no = timetables.getTrainNo();
                        relasi = timetables.getRelasi();
                        stasiun = timetables.getStasiun();
                        line_name = timetables.getLineName();
                        dep_time = timetables.getDepTime().substring(0,5);

                        timetableList.add(new Timetable(train_no,relasi,stasiun,line_name,dep_time));
                    }

                    timetableAdapter = new TimetableAdapter(timetableList,getContext());
                    lv.setAdapter(timetableAdapter);

                }catch (Exception E){
                    Log.e("On Timetable Call", "can't get Timetable, reason:" + E.toString());
                    E.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<com.a4sc11production.krlassist.model.TimetableAP.Timetable> call, Throwable t) {
                Log.e("On Timetable Call", "can't get Timetable, reason:" + t.toString());
                t.printStackTrace();
            }
        });
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
