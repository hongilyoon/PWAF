package bazar.labs.pwyf.service;

import bazar.labs.pwyf.core.model.UserData;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by hiyoon on 2017. 3. 30..
 */
public interface UserService {

    @Headers({"Accept: application/json"})
    @GET("/user/list/{id}")
    Call<UserData> getInfo(@Path("id") String id);

    @Headers({"Accept: application/json"})
    @POST("/user/save")
    @FormUrlEncoded
    Call<Void> saveUserInfo(@Field("name") String name,
                      @Field("id") String id,
                      @Field("platformSeq") long platformSeq,
                      @Field("regionSeq") long regionSeq,
                      @Field("tag") String tag);
}
