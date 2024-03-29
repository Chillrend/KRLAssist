package com.a4sc11production.krlassist.util.APIInterface;

import com.a4sc11production.krlassist.model.GangguanHome.AfterGangguan.GangguanLine;
import com.a4sc11production.krlassist.model.GangguanHome.GangguanHome;
import com.a4sc11production.krlassist.model.GangguanList.GangguanList;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface GangguanInterface {
    @GET
    Call<GangguanHome> getGangguanForHomePage(@Url String url);

    @GET
    Call<GangguanLine> getGangguanAfterAbove(@Url String url);

    @GET
    Call<GangguanList> getGangguanList(@Url String url);
}
