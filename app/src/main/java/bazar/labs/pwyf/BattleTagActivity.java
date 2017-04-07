package bazar.labs.pwyf;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

import bazar.labs.pwyf.core.model.PlatformData;
import bazar.labs.pwyf.core.utils.CommonUtils;
import bazar.labs.pwyf.service.PlatformService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BattleTagActivity extends AppCompatActivity {

    /**
     * platformService
     */
    private PlatformService platformService = CommonUtils.getRetrofit().create(PlatformService.class);

    /**
     * onCreate
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle_tag);

        // 플랫폼 목록을 조회합니다.
        this.getPlatformList();
    }

    private void getPlatformList() {
        this.platformService.getList().enqueue(new Callback<List<PlatformData>>() {
            @Override
            public void onResponse(Call<List<PlatformData>> call, Response<List<PlatformData>> response) {
                List<PlatformData> list = response.body();
                list.stream().forEach(o -> {
                    System.out.println("");
                });
            }

            @Override
            public void onFailure(Call<List<PlatformData>> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }
}
