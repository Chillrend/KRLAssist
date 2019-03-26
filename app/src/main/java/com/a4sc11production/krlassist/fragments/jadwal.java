package com.a4sc11production.krlassist.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.*;
import com.a4sc11production.krlassist.R;
import com.a4sc11production.krlassist.adapter.StasiunSpinnerAdapter;
import com.a4sc11production.krlassist.adapter.TimetableAdapter;
import com.a4sc11production.krlassist.model.StasiunSpinner;
import com.a4sc11production.krlassist.model.Timetable;
import com.a4sc11production.krlassist.util.ChangeActionBarAndStatusBarColor;
import com.codetroopers.betterpickers.radialtimepicker.RadialTimePickerDialogFragment;
import es.dmoral.toasty.Toasty;

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

    private ArrayList<StasiunSpinner> StasiunList;
    private String time_1,time_2,stasiun_id;
    private static final String FRAG_TAG_TIME_PICKER = "timePickerDialogFragment";
    private Date date_now = new Date();
    private boolean isStartTimePicked = true;

    private StasiunSpinnerAdapter stasiunAdapter;

    private ArrayList<Timetable> timetableList;
    private TimetableAdapter timetableAdapter;

    private AutoCompleteTextView stasiunChooser;
    private EditText time_picker_1, time_picker_2;

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
        // Inflate the layout for this fragment
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
        StasiunList = new ArrayList<>();

        StasiunList.add(new StasiunSpinner("CUK", "Cakung", false, R.drawable.ic_letter_s));
        StasiunList.add(new StasiunSpinner("CTA", "Citayam", true, R.drawable.ic_letter_t));
        StasiunList.add(new StasiunSpinner("MRI", "Manggarai", true, R.drawable.ic_letter_t));
        StasiunList.add(new StasiunSpinner("DP", "Depok", false, R.drawable.ic_letter_s));
        StasiunList.add(new StasiunSpinner("BOO", "Bogor", false, R.drawable.ic_letter_s));
        StasiunList.add(new StasiunSpinner("DU", "Duri", true, R.drawable.ic_letter_t));

//        stasiunAdapter = new StasiunSpinnerAdapter(getContext(), R.layout.custom_autotext_row, StasiunList);

//        stasiunChooser.setThreshold(1);
//        stasiunChooser.setAdapter(stasiunAdapter);

        stasiunChooser.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                StasiunSpinner stasiunSpinner = (StasiunSpinner) parent.getItemAtPosition(position);
                stasiun_id = stasiunSpinner.getStasiun_id();
                Toasty.info(getContext(), "Anda Memilih " + stasiun_id, Toast.LENGTH_SHORT, true).show();
            }
        });

        timetableList = new ArrayList<>();
        timetableList.add(new Timetable("1800-1801","Bogor - Jatinegara", "Manggarai", "Loop Line", "9:22"));
        timetableList.add(new Timetable("1920","Bogor - Jakarta Kota", "Manggarai", "Central Line", "9:22"));
        timetableList.add(new Timetable("D1/1270","Jakarta Kota - Bekasi", "Manggarai", "Bekasi Line", "9:22"));
        timetableList.add(new Timetable("1511","Bogor - Angke", "Manggarai", "Loop Line", "9:22"));
        timetableList.add(new Timetable("1628","Depok - Angke", "Manggarai", "Loop Line", "9:22"));
        timetableList.add(new Timetable("1788","Bogor - Jakarta Kota", "Manggarai", "Central Line", "9:22"));



        ListView lv = (ListView) view.findViewById(R.id.timetable_list);
        timetableAdapter = new TimetableAdapter(timetableList,getContext());
        lv.setAdapter(timetableAdapter);



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
            time_picker_1.setText(hourOfDay + ":" + minute);
        }else{
            time_picker_2.setText(hourOfDay + ":" + minute);
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
