package bazar.labs.pwyf.service;

import static com.facebook.HttpMethod.GET;

/**
 * Created by hiyoon on 2017-04-05.
 */

public interface RegionService {

    @GET("users/{user}/repos")
    Call<List<Repo>> listRepos(@Path("user") String user);

}
