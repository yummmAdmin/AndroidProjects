package com.digitaldestino.dishes_detail;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.digitaldestino.R;
import com.digitaldestino.adapter.SlidingImage_Adapter;
import com.digitaldestino.articleUI.ArticleActivity;
import com.digitaldestino.cart.Cart_Activity;
import com.digitaldestino.dialog.CartPopupDialog;
import com.digitaldestino.loginUI.LoginActivity;
import com.digitaldestino.modelClass.dishes_detail.DishDetial;
import com.digitaldestino.modelClass.dishes_detail.Price;
import com.digitaldestino.notification.NotificationActivity;
import com.digitaldestino.utils.BMSPrefs;
import com.digitaldestino.utils.CommonMethod;
import com.digitaldestino.utils.ConnectionDetector;
import com.digitaldestino.utils.Constants;
import com.digitaldestino.utils.SmartTextView;
import com.digitaldestino.wishlist.WishlistActivity;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.HashMap;

public class DishesDetailActivity extends AppCompatActivity implements View.OnClickListener, DishesDetailContract.View {
    private TextView text_go, text_dishes_name, text_delivered, text_sales, text_qty, text_price;
    private SmartTextView text_description;
    private Button btn_addCart;
    private EditText edt_coupon;
    private ImageView img_dish, img_bookmark, img_notification;
    private String food_item_id, dish_price, food_item_name, restaurant_id, _id;
    private ProgressDialog progressDialog;
    private DishesDetailPresenter dishesDetailPresenter;
    private ConnectionDetector connectionDetector;
    private boolean isInternetPresent = false;
    private static ViewPager mPager;
    private ArrayList<Price> priceArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dishes_detail);
        findView();
        if (isInternetPresent) {
            requestParam();
        } else {
            CommonMethod.showAlertDialog(DishesDetailActivity.this, getString(R.string.internet_toast));
        }
    }

    // initialize object
    private void findView() {
        mPager = (ViewPager) findViewById(R.id.pager);

        mPager.setAdapter(new SlidingImage_Adapter(getApplicationContext()));

        CirclePageIndicator indicator = (CirclePageIndicator)
                findViewById(R.id.indicator);

        indicator.setViewPager(mPager);

        final float density = getResources().getDisplayMetrics().density;

        //Set circle indicator radius
        indicator.setRadius(3 * density);
        _id = BMSPrefs.getString(getApplicationContext(), Constants._ID);
        food_item_id = BMSPrefs.getString(getApplicationContext(), Constants.FOOD_ITEM_ID);

        connectionDetector = new ConnectionDetector(getApplicationContext());
        isInternetPresent = connectionDetector.isConnectingToInternet();
        //   text_go = findViewById(R.id.text_go);
        text_description = findViewById(R.id.text_description);
        img_dish = findViewById(R.id.img_dish);
        text_dishes_name = findViewById(R.id.text_dishes_name);
        text_delivered = findViewById(R.id.text_delivered);
        text_sales = findViewById(R.id.text_sales);
        //edt_coupon = findViewById(R.id.edt_coupon);
        btn_addCart = findViewById(R.id.btn_addCart);
        img_bookmark = findViewById(R.id.img_bookmark);
        img_notification = findViewById(R.id.img_notification);
        //  text_go.setOnClickListener(this);
        btn_addCart.setOnClickListener(this);
        img_bookmark.setOnClickListener(this);
        img_notification.setOnClickListener(this);
    }

    // request param
    private void requestParam() {
        HashMap<String, String> params = new HashMap<>();
        params.put("food_item_id", food_item_id);
        dishesDetailPresenter = new DishesDetailPresenter(this);
        dishesDetailPresenter.requestDishDetail(params);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_addCart: {
                if (!TextUtils.isEmpty(_id)) {
                    openDialouge();
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
            case R.id.img_notification: {
                if (!TextUtils.isEmpty(_id)) {
                    callActivity(new Intent(getApplicationContext(), NotificationActivity.class));
                } else {
                    callActivity(new Intent(getApplicationContext(), LoginActivity.class));
                }
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


    //dialog open on button click
    private void openDialouge() {
        CartPopupDialog cancelBookingFragment = new CartPopupDialog();
        Bundle args = new Bundle();
        args.putString("dish_price", dish_price);
        args.putString("food_item_name", food_item_name);
        args.putString("restaurant_id", restaurant_id);
        args.putParcelableArrayList("priceArrayList", priceArrayList);

        cancelBookingFragment.setArguments(args);
        // cancelBookingFragment.setOnCancelBookingListener(this);
        cancelBookingFragment.show(getSupportFragmentManager(), "xfh");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void showProgress() {
        progressDialog = CommonMethod.showProgressDialog(progressDialog, DishesDetailActivity.this, getString(R.string.please_wait));

    }

    @Override
    public void hideProgress() {
        CommonMethod.hideProgressDialog(progressDialog);
    }

    @Override
    public void setDataToViews(String status, String msg, String key, DishDetial dishDetial) {
        if (status.equalsIgnoreCase("1") && key.equalsIgnoreCase("getdishdeatils")) {
            dish_price = dishDetial.getBase_price();
            text_dishes_name.setText(dishDetial.getFood_item_name() + " $" + dish_price);
            text_description.setText(dishDetial.getFood_desc());
            text_delivered.setText("Delivered: " + dishDetial.getSalecount() + " Times");

            try {
                Glide.with(getApplicationContext())
                        .load(Constants.ARTICLE_IMAGE_PATH + "dish/" + dishDetial.getFood_image())
                        .error(R.drawable.placeholder)
                        .skipMemoryCache(true)
                        .into(img_dish);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            priceArrayList = dishDetial.getPrice();
            food_item_name = dishDetial.getFood_item_name();
            restaurant_id = dishDetial.getRestaurant_id();
        } else {
            CommonMethod.showToastShort(msg, getApplicationContext());
        }
    }

    @Override
    public void onResponseFailure(Throwable throwable) {
    }

}
