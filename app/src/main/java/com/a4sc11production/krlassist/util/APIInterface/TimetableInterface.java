package com.a4sc11production.krlassist.util.APIInterface;

import com.a4sc11production.krlassist.model.TimetableAP.Timetable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface TimetableInterface {
    @GET
    Call<Timetable> getJadwal(@Url String url);
}
