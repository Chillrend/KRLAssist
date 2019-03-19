package com.a4sc11production.krlassist.util.APIInterface;

import com.a4sc11production.krlassist.model.weather.WeatherModel;
import retrofit2.Call;
import retrofit2.http.*;

public interface WeatherAPIInterface {
    @GET
    Call<WeatherModel> getWeather(@Url String urls);
}
