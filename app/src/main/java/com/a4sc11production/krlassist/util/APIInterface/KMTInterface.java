package com.a4sc11production.krlassist.util.APIInterface;

import com.a4sc11production.krlassist.model.KMT.MultiTrip;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface KMTInterface {
    @GET
    Call<MultiTrip> getBalance(@Url String url);
}
