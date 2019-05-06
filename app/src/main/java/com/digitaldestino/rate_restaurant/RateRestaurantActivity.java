package com.digitaldestino.rate_restaurant;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.digitaldestino.R;
import com.digitaldestino.articleUI.ArticleActivity;
import com.digitaldestino.utils.BMSPrefs;
import com.digitaldestino.utils.CommonMethod;
import com.digitaldestino.utils.ConnectionDetector;
import com.digitaldestino.utils.Constants;
import com.digitaldestino.utils.CustomRatingBar;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class RateRestaurantActivity extends AppCompatActivity implements View.OnClickListener, RateRestaurantContract.View {
    private ImageView img_back;
    private EditText edt_review;
    private String Food_rating = "", Service_rating = "", Delivery_rating = "", Enviroment_rating = "", session_token, _id, order_id, seekerId, restaurant_id,
            current_time;
    private Button btn_submit;
    private CustomRatingBar rating_food, rating_service, rating_delivery, rating_environment;
    private ProgressDialog progressDialog;
    private ConnectionDetector connectionDetector;
    private boolean isInternetPresent = false;
    private static final String TAG = "RateRestaurantActivity";
    private RateRestaurantPresenter rateRestaurantPresenter;
    private Date date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_restaurant);
        findView();
    }

    // initialize object here...
    private void findView() {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        current_time = df.format(Calendar.getInstance().getTime());
        try {
            date = df.parse(current_time);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        connectionDetector = new ConnectionDetector(getApplicationContext());
        isInternetPresent = connectionDetector.isConnectingToInternet();
        _id = BMSPrefs.getString(RateRestaurantActivity.this, Constants._ID);
        session_token = BMSPrefs.getString(RateRestaurantActivity.this, Constants.SESSION_TOKEN);
        order_id = BMSPrefs.getString(RateRestaurantActivity.this, Constants.ORDER_ID);
        seekerId = BMSPrefs.getString(RateRestaurantActivity.this, Constants.Seeker_id);
        restaurant_id = BMSPrefs.getString(RateRestaurantActivity.this, Constants.RESTAURANT_ID);
        img_back = findViewById(R.id.img_back);
        edt_review = findViewById(R.id.edt_review);
        btn_submit = findViewById(R.id.btn_submit);
        rating_food = findViewById(R.id.rating_food);
        rating_service = findViewById(R.id.rating_service);
        rating_delivery = findViewById(R.id.rating_delivery);
        rating_environment = findViewById(R.id.rating_environment);
        img_back.setOnClickListener(this);
        btn_submit.setOnClickListener(this);

        rating_food.setOnRatingChangeListener(new CustomRatingBar.OnRatingChangeListener() {
            @Override
            public void onChange(CustomRatingBar customRatingBar, float f, float f2) {
                Food_rating = String.valueOf(f2);
            }
        });

        rating_service.setOnRatingChangeListener(new CustomRatingBar.OnRatingChangeListener() {
            @Override
            public void onChange(CustomRatingBar customRatingBar, float f, float f2) {
                Service_rating = String.valueOf(f2);

            }
        });

        rating_delivery.setOnRatingChangeListener(new CustomRatingBar.OnRatingChangeListener() {
            @Override
            public void onChange(CustomRatingBar customRatingBar, float f, float f2) {
                Delivery_rating = String.valueOf(f2);
            }
        });

        rating_environment.setOnRatingChangeListener(new CustomRatingBar.OnRatingChangeListener() {
            @Override
            public void onChange(CustomRatingBar customRatingBar, float f, float f2) {
                Enviroment_rating = String.valueOf(f2);
            }
        });
    }

    // manage listener
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back: {
                onBackPressed();
                break;
            }
            case R.id.btn_submit: {
                if (isInternetPresent) {
                    if (Food_rating.equals("") || Service_rating.equals("")) {
                        CommonMethod.showToastShort(getString(R.string.give_rating), getApplicationContext());
                    } else {
                        String review = edt_review.getText().toString().trim();
                        requestParam(review);
                    }

                } else {
                    CommonMethod.showAlertDialog(RateRestaurantActivity.this, getString(R.string.internet_toast));
                }
                break;
            }
        }
    }

    // backpress
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left);
    }


    // call activity
    private void callActivityFinish(Intent intent) {
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left);
        finish();
    }


    // request param for rating restaurant
    private void requestParam(String review) {
        HashMap<String, String> params = new HashMap<>();
        params.put("seeker_id", _id);
        params.put("seekerId", seekerId);
        params.put("session_token", session_token);
        params.put("restaurant_id", restaurant_id);
        params.put("order_id", order_id);
        params.put("review", review);
        params.put("food_rating", Food_rating);
        params.put("service_rating", Service_rating);
        params.put("env_rating", Enviroment_rating);
        params.put("delivery_rating", Delivery_rating);
        params.put("feedback_date_time", "" + date.getTime());
        Log.d("user_param", params.toString());
        rateRestaurantPresenter = new RateRestaurantPresenter(this);
        rateRestaurantPresenter.requestGiveRating(params);
    }

    @Override
    public void showProgress() {
        progressDialog = CommonMethod.showProgressDialog(progressDialog, RateRestaurantActivity.this, getString(R.string.please_wait));
    }

    @Override
    public void hideProgress() {
        CommonMethod.hideProgressDialog(progressDialog);
    }

    @Override
    public void setDataToViews(String status, String msg, String key) {
        if (status.equalsIgnoreCase("1") && key.equalsIgnoreCase("sendFeedback")) {
            CommonMethod.showToastShort(msg, getApplicationContext());
            callActivityFinish(new Intent(getApplicationContext(), ArticleActivity.class));
        } else if (status.equalsIgnoreCase("0") && key.equalsIgnoreCase("invalid_session")) {
            BMSPrefs.putString(getApplicationContext(), Constants._ID, "");
            BMSPrefs.putString(getApplicationContext(), Constants.Seeker_id, "");
            CommonMethod.showToastShort(msg, getApplicationContext());
            callActivityFinish(new Intent(getApplicationContext(), ArticleActivity.class));
        } else {
            CommonMethod.showToastShort(msg, getApplicationContext());
        }
    }

    @Override
    public void onResponseFailure(Throwable throwable) {
        Log.d(TAG, throwable.getMessage());
    }
}
