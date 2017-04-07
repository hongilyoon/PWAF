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
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

public class FacebookActivity extends AppCompatActivity {

    private CallbackManager callbackManager;

    private AccessToken mToken = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook);

        // facebook login 설정
        callbackManager = CallbackManager.Factory.create();
        mToken = AccessToken.getCurrentAccessToken();
        if (mToken == null || TextUtils.isEmpty(mToken.getUserId())) {
            LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
            loginButton.setReadPermissions("email");
            loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    moveToBattleTagActivity();
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
        }
        else {
            moveToBattleTagActivity();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void moveToBattleTagActivity() {
        try {
            Intent intent = new Intent(getApplicationContext(), BattleTagActivity.class);
            startActivity(intent);
            finish();
        }
        catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
