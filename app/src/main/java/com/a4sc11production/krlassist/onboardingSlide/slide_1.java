package com.a4sc11production.krlassist.onboardingSlide;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.TransitionDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.a4sc11production.krlassist.R;
import com.github.paolorotolo.appintro.ISlideBackgroundColorHolder;
import com.github.paolorotolo.appintro.ISlidePolicy;
import es.dmoral.toasty.Toasty;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link slide_1.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link slide_1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class slide_1 extends Fragment implements ISlideBackgroundColorHolder, ISlidePolicy {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;

    TextView tifaw;

    LinearLayout red_line;
    LinearLayout loop_line, bekasi_line, tangerang_line, rangkasbitung_line;

    public slide_1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment slide_1.
     */
    // TODO: Rename and change types and number of parameters
    public static slide_1 newInstance(String param1, String param2) {
        slide_1 fragment = new slide_1();
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
        return inflater.inflate(R.layout.fragment_slide_1, container, false);
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

    @Override
    public void onViewCreated(View view,@Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loop_line = (LinearLayout) view.findViewById(R.id.loop_line);
        red_line = (LinearLayout) view.findViewById(R.id.red_line);
        bekasi_line = (LinearLayout) view.findViewById(R.id.bekasi_line);
        tangerang_line = (LinearLayout) view.findViewById(R.id.tangerang_line);
        rangkasbitung_line = (LinearLayout) view.findViewById(R.id.rangkasbitung_line);


        loop_line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loop_line.setSelected(true);
                red_line.setSelected(false);
                bekasi_line.setSelected(false);
                tangerang_line.setSelected(false);
                rangkasbitung_line.setSelected(false);

                SharedPreferences.Editor editor = getActivity().getSharedPreferences("DEFAULT_LINE", Context.MODE_PRIVATE).edit();
                editor.putString("def_line", "Loop Line");
                editor.commit();
            }
        });

        red_line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loop_line.setSelected(false);
                red_line.setSelected(true);
                bekasi_line.setSelected(false);
                tangerang_line.setSelected(false);
                rangkasbitung_line.setSelected(false);

                SharedPreferences.Editor editor = getActivity().getSharedPreferences("DEFAULT_LINE", Context.MODE_PRIVATE).edit();
                editor.putString("def_line", "Red Line");
                editor.commit();
            }
        });

        bekasi_line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loop_line.setSelected(false);
                red_line.setSelected(false);
                bekasi_line.setSelected(true);
                tangerang_line.setSelected(false);
                rangkasbitung_line.setSelected(false);

                SharedPreferences.Editor editor = getActivity().getSharedPreferences("DEFAULT_LINE", Context.MODE_PRIVATE).edit();
                editor.putString("def_line", "Bekasi Line");
                editor.commit();
            }
        });

        tangerang_line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loop_line.setSelected(false);
                red_line.setSelected(false);
                bekasi_line.setSelected(false);
                tangerang_line.setSelected(true);
                rangkasbitung_line.setSelected(false);

                SharedPreferences.Editor editor = getActivity().getSharedPreferences("DEFAULT_LINE", Context.MODE_PRIVATE).edit();
                editor.putString("def_line", "Tangerang Line");
                editor.commit();
            }
        });

        rangkasbitung_line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loop_line.setSelected(false);
                red_line.setSelected(false);
                bekasi_line.setSelected(false);
                tangerang_line.setSelected(false);
                rangkasbitung_line.setSelected(true);

                SharedPreferences.Editor editor = getActivity().getSharedPreferences("DEFAULT_LINE", Context.MODE_PRIVATE).edit();
                editor.putString("def_line", "Rangkasbitung Line");
                editor.commit();
            }
        });
    }

    @Override
    public int getDefaultBackgroundColor() {
        // Return the default background color of the slide.
        return Color.parseColor("#00BCD4");
    }

    @Override
    public void setBackgroundColor(@ColorInt int backgroundColor) {
        // Set the background color of the view within your slide to which the transition should be applied.
        FrameLayout fr = (FrameLayout) getView().findViewById(R.id.slide_1_container);

        if (fr != null){
            fr.setBackgroundColor(Color.parseColor("#5C6BCE"));

        }
    }

    @Override
    public boolean isPolicyRespected() {
        if (loop_line.isSelected() || red_line.isSelected() || bekasi_line.isSelected() || rangkasbitung_line.isSelected()
        || tangerang_line.isSelected()){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void onUserIllegallyRequestedNextPage() {
        Toasty.error(getContext(), "Silahkan pilih jalur telebih dahulu", Toast.LENGTH_SHORT, true).show();
        // User illegally requested next slide
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
