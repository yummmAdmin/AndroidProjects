package com.digitaldestino.articledetailUI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.digitaldestino.R;
import com.digitaldestino.dishes_restaurentUI.DishesRestaurentActivity;
import com.digitaldestino.loginUI.LoginActivity;
import com.digitaldestino.articleUI.ArticleActivity;
import com.digitaldestino.modelClass.article.Article;
import com.digitaldestino.modelClass.articleDetail.User;
import com.digitaldestino.notification.NotificationActivity;
import com.digitaldestino.restaurant_info.RestaurentInfoActivity;
import com.digitaldestino.utils.BMSPrefs;
import com.digitaldestino.utils.CommonMethod;
import com.digitaldestino.utils.ConnectionDetector;
import com.digitaldestino.utils.Constants;
import com.digitaldestino.utils.SmartTextView;
import com.digitaldestino.wishlist.WishlistActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class ArticleDetailActivity extends AppCompatActivity implements View.OnClickListener, ArticleDetailContract.View {
    private TextView txt_article_name, txt_go_restaurant;
    private SmartTextView text_article_desp;
    private String identifier, _id;
    private ImageView img_article, img_share, img_notification, img_bookmark;
    private ConnectionDetector connectionDetector;
    private boolean isInternetPresent = false;
    private String description, subject;
    private ProgressBar dialog_progress_bar;
    private final String TAG = "ArticleDetail";
    private ArticleDetailPresenter articleDetailPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);
        findId();
        if (isInternetPresent) {
            HashMap<String, String> params = new HashMap<>();
            params.put("identifier", identifier);
            articleDetailPresenter = new ArticleDetailPresenter(this);
            articleDetailPresenter.requestArticleData(params);
        } else {
            CommonMethod.showAlertDialog(ArticleDetailActivity.this, getString(R.string.internet_toast));
        }
    }

    // to initialize object here...
    private void findId() {
        _id = BMSPrefs.getString(getApplicationContext(), Constants._ID);
        identifier = BMSPrefs.getString(getApplicationContext(), Constants.IDENTIFIER);
        connectionDetector = new ConnectionDetector(getApplicationContext());
        isInternetPresent = connectionDetector.isConnectingToInternet();
        txt_article_name = findViewById(R.id.txt_article_name);
        img_article = findViewById(R.id.img_article_img);
        text_article_desp = findViewById(R.id.text_article_desp);
        dialog_progress_bar = findViewById(R.id.dialog_progress_bar);
        txt_go_restaurant = findViewById(R.id.txt_go_restaurant);
        img_share = findViewById(R.id.img_share);
        img_notification = findViewById(R.id.img_notification);
        img_bookmark = findViewById(R.id.img_bookmark);

        img_share.setOnClickListener(this);
        txt_go_restaurant.setOnClickListener(this);
        img_notification.setOnClickListener(this);
        img_bookmark.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_go_restaurant: {
                callActivity(new Intent(getApplicationContext(), RestaurentInfoActivity.class));
                break;
            }

            case R.id.img_share: {
                shareArticleDetail();
                break;
            }
            case R.id.img_notification: {
                if (!TextUtils.isEmpty(_id)) {
                    callActivity(new Intent(getApplicationContext(), NotificationActivity.class));
                } else {
                    callActivity(new Intent(getApplicationContext(), LoginActivity.class));
                }
                break;
            }

            case R.id.img_bookmark: {
                if (!TextUtils.isEmpty(_id)) {
                    callActivity(new Intent(getApplicationContext(), WishlistActivity.class));
                } else {
                    callActivity(new Intent(getApplicationContext(), LoginActivity.class));
                }
                break;
            }
        }
    }


    // backpress
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        callActivityFinish(new Intent(getApplicationContext(), ArticleActivity.class));
    }

    // share article detail
    private void shareArticleDetail() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, subject + ": " + description);
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, ""));
    }

    // call activity method
    private void callActivity(Intent intent) {
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left);
    }

    // call activity method
    private void callActivityFinish(Intent intent) {
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left);
    }


    @Override
    public void showProgress() {
        dialog_progress_bar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        dialog_progress_bar.setVisibility(View.GONE);
    }

    @Override
    public void setDataToViews(User user) {
        subject = user.getSubject();
        description = user.getDescription();
        text_article_desp.setText(description);
        txt_article_name.setText(subject);
        BMSPrefs.putString(getApplicationContext(), Constants.RESTAURANT_ID, user.getRestaurant_id());
        try {
            Glide.with(getApplicationContext())
                    .load(Constants.ARTICLE_IMAGE_PATH+"article/"+user.getImage())
                    .error(R.drawable.placeholder)
                    .skipMemoryCache(true)
                    .into(img_article);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onResponseFailure(Throwable throwable) {
        Log.e(TAG, throwable.getMessage());
    }
}
