package bazar.labs.pwyf.service;

import java.util.List;

import bazar.labs.pwyf.core.model.RegionData;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

/**
 * Created by hiyoon on 2017-04-05.
 */
public interface RegionService {

    @Headers({"Accept: application/json"})
    @GET("/region/list")
    Call<List<RegionData>> getList();

}
