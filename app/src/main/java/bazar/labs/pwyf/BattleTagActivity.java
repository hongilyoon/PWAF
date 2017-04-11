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

import java.util.List;

import bazar.labs.pwyf.core.model.PlatformData;
import bazar.labs.pwyf.core.model.RegionData;
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
                BattleTagActivity.this.saveUserInfo();
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

    private void saveUserInfo() {



















//        // set user data
//        EditText battleTag1 = (EditText) findViewById(R.id.battleTag1);
//        EditText battleTag2 = (EditText) findViewById(R.id.battleTag2);
//        String tag = TextUtils.isEmpty(battleTag1.getText()) || TextUtils.isEmpty(battleTag2.getText()) ?
//                "" :
//                String.format("%s-%s", battleTag1.getText(), battleTag2.getText());
//
//        // save userInfo
//        try {
//            BattleTagActivity.this.userService.saveUserInfo(
//                    "Hi Yoon",
//                    AccessToken.getCurrentAccessToken().getUserId(),
//                    1,
//                    1,
//                    "요미-3910").enqueue(new Callback<Void>() {
//                @Override
//                public void onResponse(Call<Void> call, Response<Void> response) {
//                    BattleTagActivity.this.getFriendList();
//                }
//
//                @Override
//                public void onFailure(Call call, Throwable t) {
//                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


        GraphRequest graphRequest = new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                AccessToken.getCurrentAccessToken().getUserId(),
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
                        // save userInfo
                        try {
                            // set user data
                            EditText battleTag1 = (EditText) findViewById(R.id.battleTag1);
                            EditText battleTag2 = (EditText) findViewById(R.id.battleTag2);
                            String tag = TextUtils.isEmpty(battleTag1.getText()) || TextUtils.isEmpty(battleTag2.getText()) ?
                                "" :
                                String.format("%s-%s", battleTag1.getText(), battleTag2.getText());
                            BattleTagActivity.this.userService.saveUserInfo(
                                    response.getJSONObject().getString("name"),
                                    AccessToken.getCurrentAccessToken().getUserId(),
                                    ((Spinner) findViewById(R.id.spPlatform)).getSelectedItemId() + 1,
                                    ((Spinner) findViewById(R.id.spRegion)).getSelectedItemId() + 1,
                                    "요미-3910").enqueue(new Callback() {
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
                });
        graphRequest.executeAsync();






        /*
        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                AccessToken.getCurrentAccessToken().getUserId(),
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
//                        // set user data
//                        EditText battleTag1 = (EditText) findViewById(R.id.battleTag1);
//                        EditText battleTag2 = (EditText) findViewById(R.id.battleTag2);
//                        String tag = TextUtils.isEmpty(battleTag1.getText()) || TextUtils.isEmpty(battleTag2.getText()) ?
//                                "" :
//                                String.format("%s-%s", battleTag1.getText(), battleTag2.getText());

                        // save userInfo
                        try {
                            BattleTagActivity.this.userService.saveUserInfo(
                                    response.getJSONObject().getString("name"),
                                    AccessToken.getCurrentAccessToken().getUserId(),
                                    1,
                                    1,
                                    "요미-3910").enqueue(new Callback() {
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
                    }});
                    */
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

//    private class SaveUserInfoAsyncTask extends AsyncTask<UserData, Void, Void>{
//
//        @Override
//        protected Void doInBackground(UserData... params) {
//            Call<List<RegionData>> call = regionService.getList();
//            try {
//                List<RegionData> result = call.execute().body();
//                System.out.println(result.size());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            super.onPostExecute(aVoid);
//        }
//
//        @Override
//        protected void onCancelled(Void aVoid) {
//            super.onCancelled(aVoid);
//        }
//    }
}
