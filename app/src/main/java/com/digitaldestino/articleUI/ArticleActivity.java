package com.digitaldestino.articleUI;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.digitaldestino.R;
import com.digitaldestino.articledetailUI.ArticleDetailActivity;
import com.digitaldestino.avoid_queue.AvoidQueueActivity;
import com.digitaldestino.cart.Cart_Activity;
import com.digitaldestino.loginUI.LoginActivity;
import com.digitaldestino.menu_list.MenuActivity;
import com.digitaldestino.dishes_restaurentUI.DishesRestaurentActivity;
import com.digitaldestino.adapter.ArticleAdapter;
import com.digitaldestino.listener.onArticleItemListener;
import com.digitaldestino.modelClass.article.Article;
import com.digitaldestino.notification.NotificationActivity;
import com.digitaldestino.search_activity.SearchActivity;
import com.digitaldestino.utils.BMSPrefs;
import com.digitaldestino.utils.CommonMethod;
import com.digitaldestino.utils.ConnectionDetector;
import com.digitaldestino.utils.Constants;
import com.digitaldestino.wishlist.WishlistActivity;

import java.util.ArrayList;

public class ArticleActivity extends AppCompatActivity implements onArticleItemListener, View.OnClickListener, ArticleListContract.View {
    private RecyclerView recycler_article;
    private ArticleAdapter articleAdapter;
    private LinearLayoutManager linearLayoutManager;
    private ConnectionDetector connectionDetector;
    private RelativeLayout hide_keyboard;
    private EditText edt_search;
    private ImageView img_menu, img_home, img_list, img_cart, img_scan, img_bookmark, img_notification;
    private boolean isInternetPresent = false;
    private ArrayList<Article> articleArrayList = new ArrayList<>();
    private ProgressBar dialog_progress_bar;
    private String _id, session_token;
    private final String TAG = "ArticleActivity";
    private ArticleListPresenter articleListPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        findView();
        if (isInternetPresent) {
            articleListPresenter = new ArticleListPresenter(this);
            articleListPresenter.requestDataFromServer();
        } else {
            CommonMethod.showAlertDialog(ArticleActivity.this, getString(R.string.internet_toast));
        }
    }

    // to initialize object here...
    private void findView() {
        _id = BMSPrefs.getString(ArticleActivity.this, Constants._ID);
        session_token = BMSPrefs.getString(ArticleActivity.this, Constants.SESSION_TOKEN);
        dialog_progress_bar = findViewById(R.id.dialog_progress_bar);
        connectionDetector = new ConnectionDetector(getApplicationContext());
        isInternetPresent = connectionDetector.isConnectingToInternet();
        recycler_article = findViewById(R.id.recycler_article);
        hide_keyboard = findViewById(R.id.main_layout);
        edt_search = findViewById(R.id.edt_search);
        img_menu = findViewById(R.id.img_menu);
        img_home = findViewById(R.id.img_home);
        img_list = findViewById(R.id.img_list);
        img_cart = findViewById(R.id.img_cart);
        img_scan = findViewById(R.id.img_scan);
        img_bookmark = findViewById(R.id.img_bookmark);
        img_notification = findViewById(R.id.img_notification);
        img_home.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.home));

//        edt_search.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after)
//            {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                try {
//                    ArticleActivity.this.articleAdapter.getFilter().filter(s);
//                } catch (Exception ex) {
//                    ex.printStackTrace();
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });

        hide_keyboard.setOnClickListener(this);
        img_menu.setOnClickListener(this);
        img_home.setOnClickListener(this);
        img_list.setOnClickListener(this);
        img_cart.setOnClickListener(this);
        img_scan.setOnClickListener(this);
        img_bookmark.setOnClickListener(this);
        img_notification.setOnClickListener(this);
        edt_search.setOnClickListener(this);
    }

    // set adapter
    private void setAdapter() {
        articleAdapter = new ArticleAdapter(getApplicationContext(), articleArrayList, this);
        linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recycler_article.setLayoutManager(linearLayoutManager);
        recycler_article.setAdapter(articleAdapter);
    }
    // manage listener
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_layout: {
                CommonMethod.hideKeyBoard(ArticleActivity.this);
                break;
            }

            case R.id.img_menu: {
                if (!TextUtils.isEmpty(_id)) {
                    callActivity(new Intent(getApplicationContext(), MenuActivity.class));
                 } else {
                    callActivity(new Intent(getApplicationContext(), LoginActivity.class));
                }
                break;
            }

            case R.id.img_home: {
                //   callActivity(new Intent(getApplicationContext(), DishesRestaurentActivity.class));
                break;
            }

            case R.id.img_list: {
                callActivity(new Intent(getApplicationContext(), DishesRestaurentActivity.class));
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

            case R.id.img_cart: {
                if (!TextUtils.isEmpty(_id)) {
                    callActivity(new Intent(getApplicationContext(), Cart_Activity.class));
                } else {
                    callActivity(new Intent(getApplicationContext(), LoginActivity.class));
                }
                break;
            }

            case R.id.img_scan: {
                if (!TextUtils.isEmpty(_id)) {
                    callActivity(new Intent(getApplicationContext(), AvoidQueueActivity.class));
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

            case R.id.edt_search: {
                callActivity(new Intent(getApplicationContext(), SearchActivity.class));
                break;
            }
        }
    }

    // listener
    @Override
    public void onArticleItemClick(int position, String identifier) {
        edt_search.setText("");
        BMSPrefs.putString(getApplicationContext(), Constants.IDENTIFIER, identifier);
        callActivity(new Intent(getApplicationContext(), ArticleDetailActivity.class));
    }

    @Override
    public void onMenuItemClick(int position)
    {

    }

    @Override
    public void onRestaurantItemClick(int position)
    {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    // call activity method
    private void callActivity(Intent intent) {
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left);
    }

    @Override
    public void showProgress()
    {
        dialog_progress_bar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        dialog_progress_bar.setVisibility(View.GONE);
    }

    @Override
    public void setDataToRecyclerView(ArrayList<Article> articleArrayListData) {
        articleArrayList = articleArrayListData;
        setAdapter();
    }

    @Override
    public void onResponseFailure(Throwable throwable) {
        Log.e(TAG, throwable.getMessage());
    }

}
