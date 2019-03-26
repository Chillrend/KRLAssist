package com.a4sc11production.krlassist.util.APIInterface;

import com.a4sc11production.krlassist.model.RealtimePos.RealtimePos;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface PosInterface {
    @GET
    Call<RealtimePos> getRealtimePosition(@Url String url);
}
