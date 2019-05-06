package com.digitaldestino.setting;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.digitaldestino.R;
import com.digitaldestino.notification.NotificationActivity;
import com.digitaldestino.wishlist.WishlistActivity;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView img_back,img_bookmark,img_notification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        findView();
    }

    // initialize object
    private void findView() {
        img_back = findViewById(R.id.img_back);
        img_bookmark=findViewById(R.id.img_bookmark);
        img_notification=findViewById(R.id.img_notification);
        img_bookmark.setOnClickListener(this);
        img_back.setOnClickListener(this);
        img_notification.setOnClickListener(this);
    }

    // backpress
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back: {
                onBackPressed();
                break;
            }
            case R.id.img_bookmark: {
                callActivity(new Intent(getApplicationContext(), WishlistActivity.class));
                break;
            }
            case R.id.img_notification: {
                callActivity(new Intent(getApplicationContext(), NotificationActivity.class));
                break;
            }
        }
    }

    // call activity method
    private void callActivity(Intent intent) {
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left);
    }

}
