package com.a4sc11production.krlassist.fragments;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.NfcA;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.Toolbar;
import android.view.*;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.a4sc11production.krlassist.R;
import com.a4sc11production.krlassist.model.KMT.Kmt;
import com.a4sc11production.krlassist.model.KMT.MultiTrip;
import com.a4sc11production.krlassist.model.RealtimePos.Line;
import com.a4sc11production.krlassist.util.APIInterface.KMTInterface;
import com.a4sc11production.krlassist.util.KeretaAPICall;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.kennyc.view.MultiStateView;
import com.a4sc11production.krlassist.HomeActivity;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.math.BigInteger;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link nfc_kmt.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link nfc_kmt#newInstance} factory method to
 * create an instance of this fragment.
 */
public class nfc_kmt extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private String UID;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private OnFragmentInteractionListener mListener;

    private NfcAdapter mNfcAdapter;
    private String KMT_UID;

    private KMTInterface kmtInterface;

    private MultiStateView mMultiStateView;
    private TextView kmt_uid_text, kmt_bal;


    public nfc_kmt() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment nfc_kmt.
     */
    public static nfc_kmt newInstance(String param1, String param2) {
        nfc_kmt fragment = new nfc_kmt();
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

        return inflater.inflate(R.layout.fragment_nfc_kmt, container, false);


    }

    public void onViewCreated(View view,@Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        changeStatusActionBarColorFromFragment(R.color.colorNormal,R.color.colorNormalDark);

        mMultiStateView = (MultiStateView) view.findViewById(R.id.nfc_status_container);
        kmt_uid_text = (TextView) view.findViewById(R.id.kmt_uid);
        kmt_bal = (TextView) view.findViewById(R.id.kmt_bal_amount);


        mNfcAdapter = NfcAdapter.getDefaultAdapter(getContext());

        if(mNfcAdapter == null || !mNfcAdapter.isEnabled()){
            mMultiStateView.setViewState(MultiStateView.VIEW_STATE_ERROR);
        }else{
            mMultiStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
        }

        TextView tx_history_btn = (TextView) view.findViewById(R.id.nfc_tx_history);
        tx_history_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("UID", UID);
                Fragment fragment = new sejarah_tx();
                fragment.setArguments(bundle);
                ((HomeActivity) getActivity()).displaySpecificFragment(fragment, "SEJARAH_TX_FRAGMENT");
            }
        });


    }

    public void KmtDiscovered(String UIDS){
        View v = getView();
        TextView kmtBals = v.findViewById(R.id.kmt_bal_amount);
        mMultiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
        UID = UIDS;
        kmt_uid_text.setText(UIDS);

        CollectionReference kmtRef = db.collection("kmt");
        DocumentReference kmt = kmtRef.document(UIDS);
        kmt.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if(document.exists()){
                        com.a4sc11production.krlassist.pojo.Kmt kmtResult = document.toObject(com.a4sc11production.krlassist.pojo.Kmt.class);

                        kmtBals.setText(Integer.toString(kmtResult.getBalance()));

                        mMultiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
                    }else{
                        Toasty.error(getContext(),"Tidak dapat menemukan KMT", Toast.LENGTH_SHORT).show();
                        mMultiStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
                    }
                }else{
                    Toasty.error(getContext(),"Tidak dapat mengambil data, Silahkan cek koneksi internet Anda", Toast.LENGTH_SHORT).show();
                    mMultiStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
                }
            }
        });
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

    public void changeStatusActionBarColorFromFragment(int ColorNormal, int ColorDark){
        Window window = getActivity().getWindow();

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        window.setStatusBarColor(getResources().getColor(ColorDark));

        ActionBar abar = (ActionBar) ((AppCompatActivity) getActivity()).getSupportActionBar();

        abar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(ColorNormal)));
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
