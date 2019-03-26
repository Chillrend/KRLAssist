package com.a4sc11production.krlassist.fragments;

import android.support.v7.app.ActionBar;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.view.Window;
import android.widget.ListView;
import com.a4sc11production.krlassist.R;
import com.a4sc11production.krlassist.adapter.TxAdapter;
import com.a4sc11production.krlassist.model.TxTemporary;
import com.a4sc11production.krlassist.util.ChangeActionBarAndStatusBarColor;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link sejarah_tx.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link sejarah_tx#newInstance} factory method to
 * create an instance of this fragment.
 */
public class sejarah_tx extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public sejarah_tx() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment sejarah_tx.
     */
    public static sejarah_tx newInstance(String param1, String param2) {
        sejarah_tx fragment = new sejarah_tx();
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

        return inflater.inflate(R.layout.fragment_sejarah_tx, container, false);
    }

    public void onViewCreated(View view,@Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArrayList<TxTemporary> temporarieslist = new ArrayList<>();
        temporarieslist.add(new TxTemporary("DEDUCTION_TRAVEL", "Tanahabang", 5000));
        temporarieslist.add(new TxTemporary("DEDUCTION_TRAVEL", "Cibinong", 3000));
        temporarieslist.add(new TxTemporary("ADDITION_STAT", "Cibinong", 50000));
        temporarieslist.add(new TxTemporary("DEDUCTION_TRAVEL", "Cikini", 5000));
        temporarieslist.add(new TxTemporary("DEDUCTION_TRAVEL", "Cibinong", 5000));
        temporarieslist.add(new TxTemporary("DEDUCTION_TRAVEL", "Tambun", 7000));
        temporarieslist.add(new TxTemporary("ADDITION_STAT", "Tambun", 10000));
        temporarieslist.add(new TxTemporary("DEDUCTION_TRAVEL", "Depok", 8000));

        TxAdapter txAdapter = new TxAdapter(temporarieslist, getContext());

        ListView lv = view.findViewById(R.id.sejarah_tx_listview);

        lv.setAdapter(txAdapter);

        Window window = getActivity().getWindow();
        ActionBar abar = (ActionBar) ((AppCompatActivity) getActivity()).getSupportActionBar();

        ChangeActionBarAndStatusBarColor cbar = new ChangeActionBarAndStatusBarColor(getContext());
        cbar.changeStatusActionBarColorFromFragment(window, abar, R.color.colorNormal, R.color.colorNormalDark);
    }

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
