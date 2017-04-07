package bazar.labs.pwyf;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.Spinner;

import java.util.List;
import java.util.stream.Collectors;

import bazar.labs.pwyf.core.model.PlatformData;
import bazar.labs.pwyf.core.model.RegionData;
import bazar.labs.pwyf.core.utils.CommonUtils;
import bazar.labs.pwyf.service.PlatformService;
import bazar.labs.pwyf.service.RegionService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BattleTagActivity extends AppCompatActivity {

    /**
     * platformService
     */
    private PlatformService platformService = CommonUtils.getRetrofit().create(PlatformService.class);

    /**
     * regionService
     */
    private RegionService regionService = CommonUtils.getRetrofit().create(RegionService.class);

    /**
     * onCreate
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle_tag);

        // get Data
        this.getPlatformList();
        this.getRegionList();
    }

    private void getPlatformList() {
        this.platformService.getList().enqueue(new Callback<List<PlatformData>>() {
            @Override
            public void onResponse(Call<List<PlatformData>> call, Response<List<PlatformData>> response) {
                Spinner spinner = (Spinner) findViewById(R.id.spPlatform);
                String[] arrPlatform = new String[response.body().size()];
                for (int i = 0, cnt = response.body().size(); i < cnt; i++) {
                    arrPlatform[i] = response.body().get(i).getName();
                }
                ArrayAdapter<String> adapter = new ArrayAdapter(BattleTagActivity.this, android.R.layout.simple_dropdown_item_1line, arrPlatform);
                spinner.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<PlatformData>> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }

    private void getRegionList() {
        this.regionService.getList().enqueue(new Callback<List<RegionData>>() {
            @Override
            public void onResponse(Call<List<RegionData>> call, Response<List<RegionData>> response) {
                Spinner spinner = (Spinner) findViewById(R.id.spRegion);
                String[] arrRegion = new String[response.body().size()];
                for (int i = 0, cnt = response.body().size(); i < cnt; i++) {
                    arrRegion[i] = response.body().get(i).getName();
                }
                ArrayAdapter<String> adapter = new ArrayAdapter(BattleTagActivity.this, android.R.layout.simple_dropdown_item_1line, arrRegion);
                spinner.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<RegionData>> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }
}
