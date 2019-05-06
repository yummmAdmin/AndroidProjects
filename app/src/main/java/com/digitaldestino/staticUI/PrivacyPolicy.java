package com.digitaldestino.staticUI;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.digitaldestino.R;
import com.digitaldestino.articledetailUI.ArticleDetailContract;
import com.digitaldestino.articledetailUI.ArticleDetailPresenter;
import com.digitaldestino.interfaces.ApiInterface;
import com.digitaldestino.modelClass.static_pages.PrivacyResponse;
import com.digitaldestino.modelClass.static_pages.User;
import com.digitaldestino.networkClass.RetrofitClient;
import com.digitaldestino.notification.NotificationActivity;
import com.digitaldestino.utils.CommonMethod;
import com.digitaldestino.utils.ConnectionDetector;
import com.digitaldestino.utils.SmartTextView;
import com.digitaldestino.wishlist.WishlistActivity;

import java.util.HashMap;
import java.util.logging.StreamHandler;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class PrivacyPolicy extends AppCompatActivity implements View.OnClickListener, PrivacyContract.View {
    private WebView web_privacy_policy;
    private ImageView img_bookmark,img_notification;
    private SmartTextView text_privacy_policy;
    private ImageView img_back;
    private ProgressDialog progressDialog;
    private String page_description;
    private ConnectionDetector connectionDetector;
    private boolean isInternetPresent = false;
    private final String TAG = "Privacy policy";
    private PrivacyPresenter privacyPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);
        findView();
        if (isInternetPresent) {
            HashMap<String, String> params = new HashMap<>();
            params.put("identifier", "privacy_policy");
            privacyPresenter = new PrivacyPresenter(this);
            privacyPresenter.requestPrivacyData(params);
        } else {
            CommonMethod.showAlertDialog(PrivacyPolicy.this, getString(R.string.internet_toast));
        }
    }

    private void findView() {
        connectionDetector = new ConnectionDetector(getApplicationContext());
        isInternetPresent = connectionDetector.isConnectingToInternet();
        text_privacy_policy = findViewById(R.id.text_privacy_policy);
//        web_privacy_policy = findViewById(R.id.web_privacy_policy);
//        web_privacy_policy.getSettings().setJavaScriptEnabled(true);
        img_back = findViewById(R.id.img_back);
        img_bookmark=findViewById(R.id.img_bookmark);
        img_notification=findViewById(R.id.img_notification);
        img_back.setOnClickListener(this);
        img_bookmark.setOnClickListener(this);
        img_notification.setOnClickListener(this);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left);
        //finish();
    }

    @Override
    public void showProgress() {
        progressDialog = CommonMethod.showProgressDialog(progressDialog, PrivacyPolicy.this, getString(R.string.please_wait));

    }

    @Override
    public void hideProgress() {
        CommonMethod.hideProgressDialog(progressDialog);
    }

    @Override
    public void setDataToViews(User user) {
        page_description = user.getPage_description();
        text_privacy_policy.setText(Html.fromHtml(page_description));
        // web_privacy_policy.loadData(page_description, "text/html", "UTF-8");
    }

    @Override
    public void onResponseFailure(Throwable throwable) {
        Log.e(TAG, throwable.getMessage());
    }
}
