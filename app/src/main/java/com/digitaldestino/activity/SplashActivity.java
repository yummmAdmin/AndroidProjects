package com.digitaldestino.activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.digitaldestino.R;
import com.digitaldestino.articleUI.ArticleActivity;
import com.digitaldestino.utils.BMSPrefs;
import com.digitaldestino.utils.Constants;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import io.fabric.sdk.android.Fabric;

public class SplashActivity extends AppCompatActivity {
    private final int SPLASH_TIME_OUT = 3000;
    private String device_id = "";
    private Intent startIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_splash);
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    getPackageName(),
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        }
        catch (PackageManager.NameNotFoundException e) {

        }
        catch (NoSuchAlgorithmException e) {

        }
        device_id = getDeviceId();
        BMSPrefs.putString(SplashActivity.this, Constants.DEVICE_ID, device_id);
        //    CommonMethod.showToastShort(BMSPrefs.getString(getApplicationContext(), Constants.DEVICE_TOKEN),getApplicationContext());
        Log.d("fcm_token",BMSPrefs.getString(getApplicationContext(), Constants.DEVICE_TOKEN));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                startIntent = new Intent(SplashActivity.this, ArticleActivity.class);
                startActivity(startIntent);
                SplashActivity.this.overridePendingTransition(R.anim.anim_slide_in_left,
                        R.anim.anim_slide_out_left);
                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

    // to get device_id here...
    private String getDeviceId() {
        String androidId = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);
        return androidId;
    }

}
