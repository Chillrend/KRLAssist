package com.a4sc11production.krlassist;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.media.Image;
import android.net.Uri;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.*;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;
import com.a4sc11production.krlassist.fragments.home;
import com.a4sc11production.krlassist.fragments.jadwal;
import com.a4sc11production.krlassist.fragments.krl_pos;
import com.a4sc11production.krlassist.fragments.nfc_kmt;
import com.a4sc11production.krlassist.model.weather.Main;
import com.a4sc11production.krlassist.model.weather.Weather;
import com.a4sc11production.krlassist.model.weather.WeatherModel;
import com.a4sc11production.krlassist.model.weather.Wind;
import com.a4sc11production.krlassist.util.APIInterface.WeatherAPIInterface;
import com.a4sc11production.krlassist.util.SingleShotLocationProvider;
import com.a4sc11production.krlassist.util.WeatherAPICall;
import com.kennyc.view.MultiStateView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, home.OnFragmentInteractionListener,
        nfc_kmt.OnFragmentInteractionListener, krl_pos.OnFragmentInteractionListener,
        jadwal.OnFragmentInteractionListener{

    MultiStateView weather_state;
    TextView weather_city_name,weather_temp,weather_humidity,weather_wind_speed;
    ImageView icon_weather;

    private Handler mHandler = new Handler(Looper.getMainLooper());

    WeatherAPIInterface apiInterface;
    String lat, lng;
    Boolean hasLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new getLoc().execute("");
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        weather_city_name = (TextView) headerView.findViewById(R.id.weather_city_name);
        weather_temp = (TextView) headerView.findViewById(R.id.weather_temperature);
        weather_humidity = (TextView) headerView.findViewById(R.id.weather_humidity);
        weather_wind_speed = (TextView) headerView.findViewById(R.id.weather_wind_speed);
        icon_weather = (ImageView) headerView.findViewById(R.id.drawable_weather);
        weather_state = (MultiStateView) headerView.findViewById(R.id.multi_state_header);
        weather_state.setViewState(MultiStateView.VIEW_STATE_EMPTY);
        hasLocation = false;

        Fragment defaultFragment = new home();

        displaySpecificFragment(defaultFragment, "HOME_FRAGMENT");

        changeStatusBarAndToolbar(R.color.colorWarning,R.color.colorWarningDark, toolbar);

    }

    private class getLoc extends AsyncTask<String, Void, String>{

        @Override
        protected void onPreExecute(){
            SingleShotLocationProvider.requestSingleUpdate(getApplicationContext(), new SingleShotLocationProvider.LocationCallback() {
                @Override
                public void onNewLocationAvailable(SingleShotLocationProvider.GPSCoordinates location) {

                    Log.i("Location", "Location is :" + location.getLatitude() + " - " + location.getLongitude());
                    lat = Float.toString(location.getLatitude());
                    lng = Float.toString(location.getLongitude());
                    hasLocation = true;

                }
            });
        }

        @Override
        protected String doInBackground(String... strings) {
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
        }
    }

    public void callWeatherApi(View view){
        weather_state.setViewState(MultiStateView.VIEW_STATE_LOADING);
        WeatherAPICall apiCall = new WeatherAPICall();

        apiInterface = apiCall.getClient().create(WeatherAPIInterface.class);
        String urls = "http://api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon=" + lng +"&units=metric&appid=b0b1585868743006b048c5261e30ea84";

        Call<WeatherModel> call = apiInterface.getWeather(urls);
        call.enqueue(new Callback<WeatherModel>() {
            @Override
            public void onResponse(Call<WeatherModel> call, Response<WeatherModel> response) {

                String displayResponses = "";

                try{
                    WeatherModel weatherNow = response.body();
                    String weather_name = "";
                    String icon = "";

                    List<Weather> weatherDesc = weatherNow.getWeather();
                    for (Weather weather : weatherDesc){
                        weather_name = weather.getMain();
                        icon = weather.getIcon();
                    }
                    Main TempAndHumid = weatherNow.getMain();
                    String Temperature = Double.toString(TempAndHumid.getTemp()).substring(0,2);
                    String Humidity = String.valueOf(TempAndHumid.getHumidity());

                    String city = weatherNow.getName();

                    Wind wind = weatherNow.getWind();
                    String wind_speed = String.valueOf(wind.getSpeed());

                    weather_city_name.setText(city);
                    weather_humidity.setText(Humidity + "%");
                    weather_temp.setText(Temperature);
                    weather_wind_speed.setText(wind_speed + " m/s");

                    if(weather_name.equals("Thunderstorm")){
                        icon_weather.setImageResource(R.drawable.ic_weather_thunderstorm);
                    }else if(weather_name.equals("Drizzle")){
                        icon_weather.setImageResource(R.drawable.ic_weather_drizzle);
                    }else if(weather_name.equals("Rain")){
                        icon_weather.setImageResource(R.drawable.ic_weather_rain);
                    }else if(weather_name.equals("Clear")){
                        icon_weather.setImageResource(R.drawable.ic_weather_clear);
                    }else if(weather_name.equals("Clouds")){
                        icon_weather.setImageResource(R.drawable.ic_weather_cloudy);
                    }else if(icon.equals("50d") || icon.equals("50n")){
                        icon_weather.setImageResource(R.drawable.ic_weather_drizzle);
                    }
                    weather_state.setViewState(MultiStateView.VIEW_STATE_CONTENT);


                }catch (Exception e){
                    e.printStackTrace();
                    weather_state.setViewState(MultiStateView.VIEW_STATE_ERROR);
                }
            }
            @Override
            public void onFailure(Call<WeatherModel> call, Throwable t) {
                weather_state.setViewState(MultiStateView.VIEW_STATE_ERROR);
                t.printStackTrace();
            }
        });

    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        nfc_kmt nfcFragment = (nfc_kmt) getSupportFragmentManager().findFragmentByTag("NFC_FRAGMENT");
        Log.i("awe", "onNewIntent: INTENT_TRIGGERED");
       if(nfcFragment != null && nfcFragment.isAdded() && nfcFragment.isVisible() && nfcFragment.getUserVisibleHint()){
           Log.i("awe", "onNewIntent: FRAGMENT_VISIBLE");
           if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(intent.getAction())) {
               Log.i("awe", "onNewIntent: TECH_DISCOVERED");
               Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
               String KMT_UID = bin2hex(tag.getId());;

               nfcFragment.KmtDiscovered(KMT_UID);
           }
       }

    }

    static String bin2hex(byte[] data) {
        return String.format("%0" + (data.length * 2) + "X", new BigInteger(1,data));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.pos_krl) {
            Fragment fragment = new krl_pos();
            displaySpecificFragment(fragment,"REALTIME_POS_FRAGMENT");
        } else if (id == R.id.timetable) {
            Fragment fragment = new jadwal();
            displaySpecificFragment(fragment,"TIMETABLE_FRAGMENT");
        } else if (id == R.id.tariff) {

        } else if (id == R.id.check_card) {
            Fragment fragment = new nfc_kmt();
            displaySpecificFragment(fragment,"NFC_FRAGMENT");
        } else if (id == R.id.settings) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void displaySpecificFragment(Fragment fragment, String Tags){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment, Tags);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    public void changeStatusBarAndToolbar(int color, int colorDark,Toolbar toolbar){
        Window window = getWindow();

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        window.setStatusBarColor(getResources().getColor(colorDark));

        toolbar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(color)));
    }
    @Override
    public void onFragmentInteraction(Uri uri){

    }
}
