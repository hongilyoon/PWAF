package bazar.labs.pwyf;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import bazar.labs.pwyf.core.model.PlatformData;
import bazar.labs.pwyf.core.model.RegionData;
import bazar.labs.pwyf.core.model.common.SignInData;
import bazar.labs.pwyf.core.utils.CommonUtils;
import bazar.labs.pwyf.service.PlatformService;
import bazar.labs.pwyf.service.RegionService;
import bazar.labs.pwyf.service.UserService;
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
     * userService
     */
    private UserService userService = CommonUtils.getRetrofit().create(UserService.class);

    /**
     * onCreate
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle_tag);

        // get Data
        this.getPlatformList();
        this.getRegionList();

        // set Event
        Button btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BattleTagActivity.this.getUserInfo();
            }
        });
    }

    /**
     * 플랫폼 목록을 조회합니다
     */
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
                Toast.makeText(BattleTagActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 지역 목록을 조회합니다
     */
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

    private void getUserInfo() {
        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                AccessToken.getCurrentAccessToken().getUserId(),
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        // get Data
                        EditText battleTag1 = (EditText) findViewById(R.id.battleTag1);
                        EditText battleTag2 = (EditText) findViewById(R.id.battleTag2);
                        Spinner spPlatform = (Spinner) findViewById(R.id.spPlatform);
                        Spinner spRegion = (Spinner) findViewById(R.id.spPlatform);
                        String battleTag = TextUtils.isEmpty(battleTag1.getText()) || TextUtils.isEmpty(battleTag2.getText()) ?
                                "" :
                                String.format("%s-%s", battleTag1.getText(), battleTag2.getText());
                        long platformSeq = spPlatform.getSelectedItemId();
                        long regionSeq = spRegion.getSelectedItemId();
                        AccessToken accessToken = AccessToken.getCurrentAccessToken();

                        // save userInfo
                        try {
                            BattleTagActivity.this.userService.saveUserInfo(response.getJSONObject().getString("name"), accessToken.getUserId(), platformSeq, regionSeq, battleTag).enqueue(new Callback() {
                                @Override
                                public void onResponse(Call call, Response response) {
                                    BattleTagActivity.this.getFriendList();
                                }

                                @Override
                                public void onFailure(Call call, Throwable t) {
                                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
        ).executeAsync();
    }

    private void getFriendList() {
        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                String.format("/%s/friendlists", AccessToken.getCurrentAccessToken().getUserId()),
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        System.out.println(response);
                    }
                }
        ).executeAsync();
    }
}
