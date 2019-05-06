package com.digitaldestino.referal_code;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.digitaldestino.R;
import com.digitaldestino.notification.NotificationActivity;
import com.digitaldestino.utils.BMSPrefs;
import com.digitaldestino.utils.Constants;
import com.digitaldestino.wishlist.WishlistActivity;

public class ReferalCodeActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView img_back, img_bookmark, img_notification;
    private TextView text_referal_code;
    private Button btn_invite_contacts;
    private String referal_code = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_referal_code);
        findView();
    }

    // initialize object
    private void findView() {
        referal_code = BMSPrefs.getString(getApplicationContext(), Constants.REFERAL_CODE);
        text_referal_code = findViewById(R.id.text_referal_code);
        img_back = findViewById(R.id.img_back);
        btn_invite_contacts = findViewById(R.id.btn_invite_contacts);
        img_bookmark = findViewById(R.id.img_bookmark);
        img_notification = findViewById(R.id.img_notification);
        img_back.setOnClickListener(this);
        btn_invite_contacts.setOnClickListener(this);
        img_bookmark.setOnClickListener(this);
        img_notification.setOnClickListener(this);
        text_referal_code.setText(referal_code);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back: {
                onBackPressed();
                break;
            }

            case R.id.btn_invite_contacts: {
                shareRefralCode();
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

    // share referal code
    private void shareRefralCode() {
        //   newUrl = "\""+oldurl+response+certificate+"\"";
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = "Use this Referral Code - " + " " + referal_code + " in Signup, you will get a free coupon code on successful registration.";
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Referral Code");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

    // backpress
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left);
    }

}
