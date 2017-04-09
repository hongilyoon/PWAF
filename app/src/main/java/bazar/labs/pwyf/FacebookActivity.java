package bazar.labs.pwyf;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import bazar.labs.pwyf.core.model.UserData;
import bazar.labs.pwyf.core.model.common.SignInData;
import bazar.labs.pwyf.core.utils.CommonUtils;
import bazar.labs.pwyf.service.UserService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FacebookActivity extends AppCompatActivity {

    /**
     * callbackManager
     */
    private CallbackManager callbackManager;

    /**
     * accessToken
     */
    private AccessToken accessToken = null;

    /**
     * userService
     */
    private UserService userService = CommonUtils.getRetrofit().create(UserService.class);

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook);

        // facebook login 설정
        callbackManager = CallbackManager.Factory.create();
        accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken == null || TextUtils.isEmpty(accessToken.getUserId())) {
            LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
            loginButton.setReadPermissions("email", "user_birthday", "user_posts");
            loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    FacebookActivity.this.getUserInfo(accessToken);
                }

                @Override
                public void onCancel() {
                    Toast.makeText(getApplicationContext(), "로그인 실패", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onError(FacebookException error) {
                    Toast.makeText(getApplicationContext(), "로그인 실패" + error.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        } else {
            this.getUserInfo(accessToken);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void getUserInfo(AccessToken accessToken) {
        this.userService.getInfo(accessToken.getUserId()).enqueue(new Callback<UserData>() {
            @Override
            public void onResponse(Call<UserData> call, Response<UserData> response) {
                if (response.body() == null || TextUtils.isEmpty(response.body().getId())) {
                    // 가입되지 않은 사용자의 경우
                    Intent intent = new Intent(getApplicationContext(), BattleTagActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // 가입한 사용자의 경우
                    SignInData.userInfo = response.body();
                }
            }

            @Override
            public void onFailure(Call<UserData> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
