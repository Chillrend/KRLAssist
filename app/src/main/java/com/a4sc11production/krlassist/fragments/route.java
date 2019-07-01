package com.a4sc11production.krlassist.fragments;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.view.Window;
import android.widget.*;
import com.a4sc11production.krlassist.R;
import com.a4sc11production.krlassist.adapter.MapInfoWindowAdapter;
import com.a4sc11production.krlassist.adapter.StasiunSpinnerAdapter;
import com.a4sc11production.krlassist.model.Line.Result;
import com.a4sc11production.krlassist.model.Stasiun.Datum;
import com.a4sc11production.krlassist.model.Stasiun.Stasiun_;
import com.a4sc11production.krlassist.model.StasiunTemp;
import com.a4sc11production.krlassist.pojo.Line;
import com.a4sc11production.krlassist.pojo.Stasiun;
import com.a4sc11production.krlassist.util.APIInterface.RouteInterface;
import com.a4sc11production.krlassist.util.APIInterface.StasiunInterface;
import com.a4sc11production.krlassist.util.ChangeActionBarAndStatusBarColor;
import com.a4sc11production.krlassist.util.DirectionsJSONParser;
import com.a4sc11production.krlassist.util.KeretaAPICall;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import es.dmoral.toasty.Toasty;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

import static android.support.constraint.Constraints.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link route.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link route#newInstance} factory method to
 * create an instance of this fragment.
 */
public class route extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private ArrayList<StasiunTemp> stasiunTemps;
    private ArrayList<Result> result;
    private ArrayList<String> linusNamus;
    private Map<String, com.a4sc11production.krlassist.pojo.Line> lineMap;
    private ArrayList<com.a4sc11production.krlassist.pojo.Stasiun> stasiunList;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    MapView mMapView;
    private GoogleMap googleMap;
    private Spinner spinner;

    private OnFragmentInteractionListener mListener;

    public route() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment route.
     */

    public static route newInstance(String param1, String param2) {
        route fragment = new route();
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

        stasiunList = new ArrayList<>();


        CollectionReference colRef = db.collection("stasiun");

        colRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document : task.getResult()){

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


                        stasiunList.add(new com.a4sc11production.krlassist.pojo.Stasiun(st_name,kode,latitude,line_served,longitude,neighbors,transit));
                    }

                }else{
                    Toasty.error(getContext(), "Tidak dapat mengambil data stasiun, silahkan cek koneksi internet Anda.", Toast.LENGTH_SHORT, true).show();
                }
            }
        });

        lineMap = new HashMap<>();
        linusNamus = new ArrayList<>();

        CollectionReference lineRef = db.collection("line");
        lineRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document : task.getResult()){
                        com.a4sc11production.krlassist.pojo.Line lineObject = document.toObject(com.a4sc11production.krlassist.pojo.Line.class);
                        String line_name = document.getId();

                        lineMap.put(line_name, lineObject);
                        linusNamus.add(line_name);

                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_selected_item, linusNamus);
                    adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
                    spinner.setAdapter(adapter);
                }
            }
        });

        return inflater.inflate(R.layout.fragment_route, container, false);
    }

    public void onViewCreated(View view,@Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ActionBar abar = (ActionBar) ((AppCompatActivity) getActivity()).getSupportActionBar();
        Window window = getActivity().getWindow();

        spinner = view.findViewById(R.id.route_spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int white = Color.parseColor("#FFFFFF");
                ((TextView) parent.getChildAt(0)).setTextColor(white);

                String res = linusNamus.get(position);
                Line line = lineMap.get(res);
                ArrayList<String> stasiunServed = line.getStasiun_served();

                ArrayList<Stasiun> finalStasiun = new ArrayList<>();

                googleMap.clear();

                for (int i = 0; i < stasiunServed.size(); i++) {
                    for (Stasiun stat : stasiunList) {
                        if (stat.getNama().equals(stasiunServed.get(i))) {
                            finalStasiun.add(stat);
                        }
                    }
                }

                ArrayList<StasiunTemp> statTemp = new ArrayList<>();
                statTemp.clear();

                for (Stasiun stat : finalStasiun) {
                    ArrayList<String> linenNamen = new ArrayList<>();

                    for(Map.Entry<String, Line> entry : lineMap.entrySet()){
                        for(String line_served : stat.getLine_served()){
                            Log.d(TAG, "onLineServed loop: " + line_served);
                            if(entry.getKey().equals(line_served)){
                                Log.d(TAG, "on lineServed eval: " + line_served);
                                Line linus = entry.getValue();
                                if(!linenNamen.contains(linus.getLine_name())){
                                    Log.d(TAG, "onItemSelected:" + linus.getLine_name());
                                    linenNamen.add(linus.getLine_name());
                                }
                            }
                        }

                        if(linenNamen.size() == stat.getLine_served().size()){
                            break;
                        }
                    }
                    Log.d(TAG, "LinenNamen value: " + linenNamen);
                    StasiunTemp stats = new StasiunTemp(stat.getKode(), stat.getNama(), stat.getLatitude(), stat.getLongitude(), stat.isTransit(), linenNamen);
                    statTemp.add(stats);
                }

                for (StasiunTemp temp : statTemp) {
                    LatLng pos = new LatLng(temp.getLat(), temp.getLng());

                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(pos);

                    Marker m = googleMap.addMarker(markerOptions);
                    m.setTag(temp);
                }

                MapInfoWindowAdapter miwa = new MapInfoWindowAdapter(getContext());
                googleMap.setInfoWindowAdapter(miwa);

                String start_station = line.getStart_station();
                String end_station = line.getEnd_station();

                ArrayList<LatLng> latlng = new ArrayList<>();

                for(Stasiun stat : stasiunList){
                    if(latlng.size() < 2){
                       if(stat.getNama().equals(start_station) || stat.getNama().equals(end_station)){
                            LatLng pos = new LatLng(stat.getLatitude(),stat.getLongitude());
                            latlng.add(pos);
                       }
                    }
                }

                LatLng start_pos = latlng.get(0);
                LatLng end_pos = latlng.get(1);

                String uri = buildDirectionURL(start_pos,end_pos);

                DownloadDirection downloadDirection = new DownloadDirection();
                downloadDirection.execute(uri);

                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(start_pos,10));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                int white = Color.parseColor("#ffffff");
//                ((TextView) parent.getChildAt(0)).setTextColor(white);
//                Result res = result.get(position);
//
//                String line_name = res.getLineName();
//
//                KeretaAPICall krlapi = new KeretaAPICall();
//                StasiunInterface stasiunInterface = krlapi.getClient().create(StasiunInterface.class);
//                Call<Stasiun> calls = stasiunInterface.getStasiun("https://api.clude.xyz/stasiun");
//                calls.enqueue(new Callback<Stasiun>() {
//                    @Override
//                    public void onResponse(Call<Stasiun> call, Response<Stasiun> response) {
//                        String display_response = "";
//                        googleMap.clear();
//                        Stasiun stat = response.body();
//
//                        ArrayList<Datum> data = stat.getData();
//                        for (Datum dat : data) {
//                            Stasiun_ stasiun_ = dat.getStasiun();
//                            ArrayList<String> lineArr = dat.getLinenamearr();
//                            if(lineArr.contains(line_name)){
//                                stasiunTemps = new ArrayList<>();
//                                stasiunTemps.clear();
//                                Boolean isTransit;
//                                if(stasiun_.getIsTransit() == 0){
//                                    isTransit = false;
//                                }else{
//                                    isTransit = true;
//                                }
//                                stasiunTemps.add(new StasiunTemp(stasiun_.getStasiunId(), stasiun_.getNama(),
//                                        Double.parseDouble(stasiun_.getLat()), Double.parseDouble(stasiun_.getLng()),isTransit, lineArr));
//
//                                for(StasiunTemp temp : stasiunTemps){
//                                    LatLng pos = new LatLng(temp.getLat(), temp.getLng());
//
//                                    MarkerOptions markerOptions = new MarkerOptions();
//                                    markerOptions.position(pos);
//
//                                    Marker m = googleMap.addMarker(markerOptions);
//                                    m.setTag(temp);
//                                }
//                                MapInfoWindowAdapter miwa = new MapInfoWindowAdapter(getContext());
//                                googleMap.setInfoWindowAdapter(miwa);
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<Stasiun> call, Throwable t) {
//                        Log.e("On Stasiun Call", "can't get stasiun, reason:" + t.toString());
//                        t.printStackTrace();
//                    }
//                });
//
//                String lat1,lng1,lat2,lng2;
//                lat1 = res.getLat1();
//                lng1 = res.getLng1();
//                lat2 = res.getLat2();
//                lng2 = res.getLng2();
//
//                LatLng term1 = new LatLng(Double.parseDouble(lat1),Double.parseDouble(lng1));
//                LatLng term2 = new LatLng(Double.parseDouble(lat2),Double.parseDouble(lng2));
//
//                String uri = buildDirectionURL(term1,term2);
//                DownloadDirection downloadDirection = new DownloadDirection();
//                downloadDirection.execute(uri);
//
//                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(term1, 10));
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

        ChangeActionBarAndStatusBarColor cbar = new ChangeActionBarAndStatusBarColor(getContext());
        cbar.changeStatusActionBarColorFromFragment(window,abar,R.color.colorPrimary,R.color.colorPrimaryDark);

        mMapView = (MapView) view.findViewById(R.id.mapView);

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;

                // For dropping a marker at a point on the Map
                LatLng terminus1 = new LatLng(-6.595684,106.790417);
                LatLng terminus2 = new LatLng(-6.13757,106.814633);


            }
        });

        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String buildDirectionURL(LatLng origin, LatLng destination){
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + destination.latitude + "," + destination.longitude;
        // Departure time today at 12pm
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY,12);
        cal.set(Calendar.MINUTE,0);
        cal.set(Calendar.SECOND,0);
        cal.set(Calendar.MILLISECOND,0);

        Date d = cal.getTime();
        long epoch = d.getTime()/1000;
        String date = String.valueOf(epoch);

        // Sensor enabled
        String sensor = "sensor=false";
        String mode = "mode=transit";
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor + "&" + mode + "&departure_time=" + date + "&key=AIzaSyDOLOpJqaK_qRis79Ys1pX2aWjlPfJrgcU";

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;


        return url;
    }

    private String downloadURL(String strUrl) throws IOException {
        String data = "";
        InputStream is = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.connect();

            is = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        } catch (Exception e) {
            Log.d("Exception", e.toString());
        } finally {
            is.close();
            urlConnection.disconnect();
        }
        return data;
    }

    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String,String>>>>{
        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();

                routes = parser.parse(jObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList points = null;
            PolylineOptions lineOptions = null;
            MarkerOptions markerOptions = new MarkerOptions();

            for (int i = 0; i < result.size(); i++) {
                points = new ArrayList();
                lineOptions = new PolylineOptions();

                List<HashMap<String, String>> path = result.get(i);

                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                lineOptions.addAll(points);
                lineOptions.width(12);
                lineOptions.color(Color.RED);
                lineOptions.geodesic(true);

            }

            // Drawing polyline in the Google Map for the i-th route
            googleMap.addPolyline(lineOptions);
        }
    }

    private class DownloadDirection extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... url){
            String data = "";
            try{
                data = downloadURL(url[0]);
            }catch (Exception e){
                e.printStackTrace();
                Log.d("From Direction Async", e.toString());
            }
            return data;
        }
        @Override
        protected void onPostExecute(String result){
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();
            parserTask.execute(result);
        }
    }

    public void getTransitDirection(LatLng first, LatLng second){

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

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
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
