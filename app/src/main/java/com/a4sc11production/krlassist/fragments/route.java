package com.a4sc11production.krlassist.fragments;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
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
import com.a4sc11production.krlassist.R;
import com.a4sc11production.krlassist.adapter.MapInfoWindowAdapter;
import com.a4sc11production.krlassist.model.StasiunTemp;
import com.a4sc11production.krlassist.util.ChangeActionBarAndStatusBarColor;
import com.a4sc11production.krlassist.util.DirectionsJSONParser;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

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

    MapView mMapView;
    private GoogleMap googleMap;

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


        return inflater.inflate(R.layout.fragment_route, container, false);
    }

    public void onViewCreated(View view,@Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ActionBar abar = (ActionBar) ((AppCompatActivity) getActivity()).getSupportActionBar();
        Window window = getActivity().getWindow();

        ChangeActionBarAndStatusBarColor cbar = new ChangeActionBarAndStatusBarColor(getContext());
        cbar.changeStatusActionBarColorFromFragment(window,abar,R.color.colorPrimary,R.color.colorPrimaryDark);

        stasiunTemps = new ArrayList<>();
        List<String> line = new ArrayList<>();
        line.add("Loop Line");
        line.add("Central Line");
        stasiunTemps.add(new StasiunTemp("BOO", "Bogor", -6.595684, 106.790417, false, line));

        line.clear();
        line.add("Loop Line");
        line.add("Central Line");
        stasiunTemps.add(new StasiunTemp("CLT", "Cilebut", -6.530543, 106.800566, false, line));

        line.clear();
        line.add("Loop Line");
        line.add("Central Line");
        stasiunTemps.add(new StasiunTemp("BJD", "Bojong Gede", -6.493245, 106.794896, false, line));

        line.clear();
        line.add("Loop Line");
        line.add("Central Line");
        stasiunTemps.add(new StasiunTemp("CTA", "Citayam", -6.448799, 106.802486, true, line));

        line.clear();
        line.add("Loop Line");
        line.add("Central Line");
        stasiunTemps.add(new StasiunTemp("DP", "Depok", -6.404935, 106.817253, true, line));

        mMapView = (MapView) view.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately


        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;

                // For dropping a marker at a point on the Map
                LatLng terminus1 = new LatLng(-6.595684,106.790417);
                LatLng terminus2 = new LatLng(-6.13757,106.814633);

                for(StasiunTemp temp : stasiunTemps){
                    LatLng pos = new LatLng(temp.getLat(), temp.getLng());

                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(pos);

                    Marker m = mMap.addMarker(markerOptions);
                    m.setTag(temp);
                }
                MapInfoWindowAdapter miwa = new MapInfoWindowAdapter(getContext());
                mMap.setInfoWindowAdapter(miwa);

                String uri = buildDirectionURL(terminus1,terminus2);

                DownloadDirection downloadDirection = new DownloadDirection();
                downloadDirection.execute(uri);



                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(terminus1, 10));
            }
        });
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
