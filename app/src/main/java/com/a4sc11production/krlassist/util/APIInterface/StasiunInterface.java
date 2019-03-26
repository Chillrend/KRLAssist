package com.a4sc11production.krlassist.util.APIInterface;

import com.a4sc11production.krlassist.model.Stasiun.Stasiun;
import com.a4sc11production.krlassist.model.Stasiun.Stasiun_;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface StasiunInterface {
    @GET
    Call<Stasiun> getStasiun(@Url String url);
}
