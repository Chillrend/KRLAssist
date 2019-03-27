package com.a4sc11production.krlassist.util.APIInterface;

import com.a4sc11production.krlassist.model.Line.Line;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface RouteInterface {
    @GET
    Call<Line> getLine(@Url String url);
}
