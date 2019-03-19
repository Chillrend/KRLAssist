package com.a4sc11production.krlassist.util.APIInterface;

import com.a4sc11production.krlassist.model.weather.WeatherModel;
import retrofit2.Call;
import retrofit2.http.*;

public interface WeatherAPIInterface {
    @GET("/weather?q={city},id&appid=b0b1585868743006b048c5261e30ea84&units=metric")
    Call<WeatherModel> getWeather(@Path("city") String city);
}
