package bazar.labs.pwyf.service;

import java.util.List;

import bazar.labs.pwyf.core.model.PlatformData;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

/**
 * Created by hiyoon on 2017-04-05.
 */

public interface PlatformService {

    @Headers({"Accept: application/json"})
    @GET("/platform/list")
    Call<List<PlatformData>> getList();
}
