package bazar.labs.pwyf.core.utils;

import android.content.res.Resources;

import bazar.labs.pwyf.R;
import lombok.Getter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by hiyoon on 2017-04-06.
 */
@Getter
public class CommonUtils {

    /**
     * set retrofit
     */
    private static Retrofit retrofit = null;

    private static final String baseUrl = "http://ec2-52-79-123-100.ap-northeast-2.compute.amazonaws.com:3000";

    public static Retrofit getRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }
}
