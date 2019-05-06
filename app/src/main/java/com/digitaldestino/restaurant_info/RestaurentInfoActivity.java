package com.digitaldestino.restaurant_info;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.digitaldestino.R;
import com.digitaldestino.articleUI.ArticleActivity;
import com.digitaldestino.avoid_queue.AvoidQueueContract;
import com.digitaldestino.avoid_queue.AvoidQueuePresenter;
import com.digitaldestino.book_table.BookTableActivity;
import com.digitaldestino.edit_profile.EditProfileActivity;
import com.digitaldestino.fragment.Info_Fragment;
import com.digitaldestino.fragment.Map_Fragment;
import com.digitaldestino.modelClass.follower.FollowerData;
import com.digitaldestino.modelClass.restaurant_info.Details;
import com.digitaldestino.notification.NotificationActivity;
import com.digitaldestino.points.PointsFragment;
import com.digitaldestino.restaurant_menu.Menu_Fragment;
import com.digitaldestino.get_restaurant_review.Review_Fragment;
import com.digitaldestino.loginUI.LoginActivity;
import com.digitaldestino.utils.BMSPrefs;
import com.digitaldestino.utils.CommonMethod;
import com.digitaldestino.utils.ConnectionDetector;
import com.digitaldestino.utils.Constants;
import com.digitaldestino.utils.GPSTracker;
import com.digitaldestino.wishlist.WishlistActivity;
import com.facebook.login.LoginManager;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

public class RestaurentInfoActivity extends AppCompatActivity implements View.OnClickListener, FollowerContract.View, RestaurantDetailContract.View, AvoidQueueContract.View {
    private LinearLayout linear_menu, linear_info, linear_review, linear_map, linear_points;
    private android.support.v4.app.FragmentTransaction fragmentTransaction;
    private ImageView img_menu, img_info, img_review, img_map, img_point, img_follower, img_bookmark, img_notification, img_res_image;
    private TextView text_res_name, text_address, text_opentime, text_closetime, text_slogan, text_distance, text_follower;
    private Intent intent;
    private String res_name, res_address, open_time, close_time, slogen, distance, _id, session_token, seekerId = "", table_id = "",
            restaurant_id, order_timezone, Latitude, Longitude, device_token, name;
    private LinearLayout linear_booktable, linear_avoid_queue;
    private ConnectionDetector connectionDetector;
    private boolean isInternetPresent = false;
    private ProgressDialog progressDialog;
    private static final String TAG = "RestaurentInfoActivity";
    private FollowerPresenter followerPresenter;
    private RestaurantDetailPresenter restaurantDetailPresenter;
    private ArrayList<Details> detailsArrayList = new ArrayList<>();
    private Date date;
    private AvoidQueuePresenter avoidQueuePresenter;
    private GPSTracker gpsTracker;
    private Dialog dialogMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurent_info);
        findView();
        if (isInternetPresent) {
            requestParamDetail();
        } else {
            CommonMethod.showAlertDialog(RestaurentInfoActivity.this, getString(R.string.internet_toast));

        }
        // getData();

    }

    private void findView() {
        getCurrentLatLong();
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String current_time = format.format(Calendar.getInstance().getTime());
        try {
            date = format.parse(current_time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        order_timezone = TimeZone.getDefault().getID();
        connectionDetector = new ConnectionDetector(getApplicationContext());
        isInternetPresent = connectionDetector.isConnectingToInternet();
        _id = BMSPrefs.getString(RestaurentInfoActivity.this, Constants._ID);
        session_token = BMSPrefs.getString(RestaurentInfoActivity.this, Constants.SESSION_TOKEN);
        device_token = BMSPrefs.getString(RestaurentInfoActivity.this, Constants.DEVICE_TOKEN);
        name = BMSPrefs.getString(RestaurentInfoActivity.this, Constants.USER_NAME);
        seekerId = BMSPrefs.getString(RestaurentInfoActivity.this, Constants.Seeker_id);
        restaurant_id = BMSPrefs.getString(RestaurentInfoActivity.this, Constants.RESTAURANT_ID);
        table_id = BMSPrefs.getString(RestaurentInfoActivity.this, Constants.TABLE_ID);
        linear_menu = findViewById(R.id.linear_menu);
        linear_info = findViewById(R.id.linear_info);
        linear_review = findViewById(R.id.linear_review);
        linear_map = findViewById(R.id.linear_map);
        linear_points = findViewById(R.id.linear_points);
        img_menu = findViewById(R.id.img_menu);
        img_info = findViewById(R.id.img_info);
        img_review = findViewById(R.id.img_review);
        img_map = findViewById(R.id.img_map);
        text_res_name = findViewById(R.id.text_res_name);
        text_address = findViewById(R.id.text_address);
        text_opentime = findViewById(R.id.text_opentime);
        text_closetime = findViewById(R.id.text_closetime);
        text_slogan = findViewById(R.id.text_slogan);
        text_distance = findViewById(R.id.text_distance);
        linear_booktable = findViewById(R.id.linear_booktable);
        linear_avoid_queue = findViewById(R.id.linear_avoid_queue);
        text_follower = findViewById(R.id.text_follower);
        img_point = findViewById(R.id.img_point);
        img_follower = findViewById(R.id.img_follower);
        img_bookmark = findViewById(R.id.img_bookmark);
        img_notification = findViewById(R.id.img_notification);
        img_res_image = findViewById(R.id.img_res_image);
        linear_menu.setOnClickListener(this);
        linear_info.setOnClickListener(this);
        linear_review.setOnClickListener(this);
        linear_map.setOnClickListener(this);
        linear_booktable.setOnClickListener(this);
        linear_points.setOnClickListener(this);
        img_follower.setOnClickListener(this);
        linear_avoid_queue.setOnClickListener(this);
        img_bookmark.setOnClickListener(this);
        img_notification.setOnClickListener(this);
        text_address.setOnClickListener(this);
    }

    private void getData() {
        intent = getIntent();
        res_name = intent.getStringExtra("res_name");
        res_address = intent.getStringExtra("res_address");
        open_time = intent.getStringExtra("open_time");
        close_time = intent.getStringExtra("close_time");
        slogen = intent.getStringExtra("slogen");
        distance = intent.getStringExtra("distance");
        text_res_name.setText(res_name);
        text_address.setText(res_address);
        text_opentime.setText("Open Time \n" + open_time);
        text_closetime.setText("Close Time \n" + close_time);
        text_slogan.setText(slogen);
        text_distance.setText("Distance: " + Math.round(Double.valueOf(distance)) + "mi");
    }


    //get current latitude and longitude
    private void getCurrentLatLong() {
        gpsTracker = new GPSTracker(RestaurentInfoActivity.this);
        Latitude = String.valueOf(gpsTracker.getLatitude());
        Longitude = String.valueOf(gpsTracker.getLongitude());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.linear_menu: {
                img_menu.setBackgroundResource(R.drawable.menu_red);
                img_info.setBackgroundResource(R.drawable.info);
                img_review.setBackgroundResource(R.drawable.review);
                img_map.setBackgroundResource(R.drawable.map);
                img_point.setBackgroundResource(R.drawable.points);
                callFragment(new Menu_Fragment());
                break;
            }

            case R.id.linear_info: {
                img_menu.setBackgroundResource(R.drawable.food_menu);
                img_info.setBackgroundResource(R.drawable.info_red);
                img_review.setBackgroundResource(R.drawable.review);
                img_map.setBackgroundResource(R.drawable.map);
                img_point.setBackgroundResource(R.drawable.points);
                callFragment(new Info_Fragment());

                break;
            }

            case R.id.linear_review: {
                img_menu.setBackgroundResource(R.drawable.food_menu);
                img_info.setBackgroundResource(R.drawable.info);
                img_review.setBackgroundResource(R.drawable.review_red);
                img_map.setBackgroundResource(R.drawable.map);
                img_point.setBackgroundResource(R.drawable.points);
                callFragment(new Review_Fragment());
                break;
            }

            case R.id.linear_map: {
                img_menu.setBackgroundResource(R.drawable.food_menu);
                img_info.setBackgroundResource(R.drawable.info);
                img_review.setBackgroundResource(R.drawable.review);
                img_map.setBackgroundResource(R.drawable.map_red);
                img_point.setBackgroundResource(R.drawable.points);
                callFragment(new Map_Fragment());
                break;
            }

            case R.id.linear_booktable: {
                callActivity(new Intent(getApplicationContext(), BookTableActivity.class));
//                if (!TextUtils.isEmpty(_id)) {
//                    callActivity(new Intent(getApplicationContext(), BookTableActivity.class));
//                } else {
//                    callActivity(new Intent(getApplicationContext(), LoginActivity.class));
//                }
                break;
            }

            case R.id.linear_avoid_queue: {
                if (!TextUtils.isEmpty(_id)) {
                    if (isInternetPresent) {
                        requestParamAvoidQueue(name);
                    } else {
                        CommonMethod.showAlertDialog(RestaurentInfoActivity.this, getString(R.string.internet_toast));
                    }
                } else {
                    openDialouge();
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

            case R.id.linear_points: {
                if (!TextUtils.isEmpty(_id)) {
                    img_menu.setBackgroundResource(R.drawable.food_menu);
                    img_info.setBackgroundResource(R.drawable.info);
                    img_review.setBackgroundResource(R.drawable.review);
                    img_map.setBackgroundResource(R.drawable.map);
                    img_point.setBackgroundResource(R.drawable.points_red);
                    callFragment(new PointsFragment());
                } else {
                    callActivity(new Intent(getApplicationContext(), LoginActivity.class));
                }

                break;
            }

            case R.id.img_follower: {
                if (isInternetPresent) {
                    if (!TextUtils.isEmpty(seekerId)) {
                        requestParam();
                    } else {
                        callActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    }
                } else {
                    CommonMethod.showAlertDialog(RestaurentInfoActivity.this, getString(R.string.internet_toast));
                }
                break;
            }

            case R.id.text_address: {
                img_menu.setBackgroundResource(R.drawable.food_menu);
                img_info.setBackgroundResource(R.drawable.info);
                img_review.setBackgroundResource(R.drawable.review);
                img_map.setBackgroundResource(R.drawable.map_red);
                img_point.setBackgroundResource(R.drawable.points);
                callFragment(new Map_Fragment());
                break;
            }

        }
    }

    // call fragment method
    private void callFragment(Fragment fragment) {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment);
        // fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.addToBackStack("aa");
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //     BMSPrefs.putString(getApplicationContext(), Constants.BACK_ID,"3");
//        Intent intent = new Intent(getApplicationContext(), DishesRestaurentActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        startActivity(intent);
        //  BMSPrefs.putString(getApplicationContext(), Constants.TABLE_ID, "");
        overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left);
        finish();
    }

    // request param
    private void requestParam() {
        final HashMap<String, String> params = new HashMap<>();
        params.put("session_token", session_token);
        params.put("seekerId", seekerId);
        params.put("restaurant_id", restaurant_id);
        params.put("follow_date_time", "" + date.getTime());
        Log.d("follow_param", params.toString());
        followerPresenter = new FollowerPresenter(this);
        followerPresenter.requestFollower(params);
    }

    // request param for restaurant detail
    private void requestParamDetail() {
        final HashMap<String, String> params = new HashMap<>();
        params.put("seekerId", seekerId);
        params.put("restaurant_id", restaurant_id);
        params.put("lat", Latitude);
        params.put("lng", Longitude);
//        params.put("lat", "40.7831");
//        params.put("lng", "-73.9712");

        Log.d("param", params.toString());
        restaurantDetailPresenter = new RestaurantDetailPresenter(this);
        restaurantDetailPresenter.requestRestaurantDetails(params);
    }

    // request payment  param
    private void requestParamAvoidQueue(String name) {
        HashMap<String, String> params = new HashMap<>();
        params.put("name", name);
        params.put("device_token", device_token);
        params.put("device_type", "1");
        params.put("restaurant_id", restaurant_id);
        params.put("queue_date_time", "" + date.getTime());
        params.put("queue_timezone", order_timezone);
        Log.d("diningParam", params.toString());
        avoidQueuePresenter = new AvoidQueuePresenter(this);
        avoidQueuePresenter.requestForAvoidQueue(params);
    }


    // call activity method
    private void callActivity(Intent intent) {
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left);
    }

    @Override
    public void showProgress() {
        progressDialog = CommonMethod.showProgressDialog(progressDialog, RestaurentInfoActivity.this, getString(R.string.please_wait));
    }

    @Override
    public void hideProgress() {
        CommonMethod.hideProgressDialog(progressDialog);
    }

    @Override
    public void orderForAvoidQueue(String status, String msg, String key) {
        if (status.equalsIgnoreCase("1") && key.equalsIgnoreCase("addinqueue")) {
            CommonMethod.showToastShort(msg, getApplicationContext());
            callActivityFinish(new Intent(getApplicationContext(), ArticleActivity.class));
        } else if (status.equalsIgnoreCase("0") && key.equalsIgnoreCase("invalid_session")) {
            BMSPrefs.putString(getApplicationContext(), Constants._ID, "");
            BMSPrefs.putString(getApplicationContext(), Constants.Seeker_id, "");
            CommonMethod.showToastShort(msg, getApplicationContext());
            LoginManager.getInstance().logOut();
            callActivityFinish(new Intent(getApplicationContext(), ArticleActivity.class));
        } else {
            CommonMethod.showToastShort(msg, getApplicationContext());
        }
    }

    // restaurant detail
    @Override
    public void setDataToViews(String status, String msg, String key, ArrayList<Details> detailsArrayLists, String user_following) {
        if (status.equalsIgnoreCase("1") && key.equalsIgnoreCase("resDatails")) {
            detailsArrayList = detailsArrayLists;
            text_res_name.setText(detailsArrayList.get(0).getName());
            text_address.setText(detailsArrayList.get(0).getAddress());
            text_opentime.setText("Open Time \n" + detailsArrayList.get(0).getOpentime());
            text_closetime.setText("Close Time \n" + detailsArrayList.get(0).getClosetime());
            text_slogan.setText(detailsArrayList.get(0).getSlogan());
            text_follower.setText(detailsArrayList.get(0).getFollowers() + " Followers");
            text_distance.setText("Distance: " + Math.round(Double.valueOf(detailsArrayList.get(0).getDistance())) + "mi");

            try {
                Glide.with(getApplicationContext())
                        .load(Constants.ARTICLE_IMAGE_PATH + "restaurants/" + detailsArrayList.get(0).getRes_image())
                        .error(R.drawable.placeholder)
                        .skipMemoryCache(true)
                        .into(img_res_image);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            BMSPrefs.putString(getApplicationContext(), Constants.LOYALTY, detailsArrayList.get(0).getLoyality());
            BMSPrefs.putString(getApplicationContext(), Constants.RESTAURANT_LONGITUDE, detailsArrayList.get(0).getLocation().getCoordinates()[0]);
            BMSPrefs.putString(getApplicationContext(), Constants.RESTAURANT_LATITUDE, detailsArrayList.get(0).getLocation().getCoordinates()[1]);
            BMSPrefs.putString(getApplicationContext(), "res_title_details", detailsArrayList.get(0).getRes_title_details());
            BMSPrefs.putString(getApplicationContext(), "res_food_specialities", detailsArrayList.get(0).getRes_food_specialities());
            BMSPrefs.putString(getApplicationContext(), "res_hours", detailsArrayList.get(0).getRes_hours());
            BMSPrefs.putString(getApplicationContext(), "res_additional_info", detailsArrayList.get(0).getRes_additional_info());
            BMSPrefs.putString(getApplicationContext(), "res_history_image", detailsArrayList.get(0).getRes_history_image());
            callFragment(new Info_Fragment());
            if (user_following.equalsIgnoreCase("1")) {
                img_follower.setBackgroundResource(R.drawable.follower);
            } else {

                img_follower.setBackgroundResource(R.drawable.follow_resturant);
            }
        } else {
            CommonMethod.showToastShort(msg, getApplicationContext());
        }
    }

    @Override
    public void setData(String status, String msg, String key, ArrayList<FollowerData> followerData, String restaurant_followers) {
        if (status.equalsIgnoreCase("1") && key.equalsIgnoreCase("follow")) {
            CommonMethod.showToastShort(msg, getApplicationContext());
            img_follower.setBackgroundResource(R.drawable.follower);
            text_follower.setText(restaurant_followers + " Followers");
        } else if (status.equalsIgnoreCase("1") && key.equalsIgnoreCase("unFollow")) {
            CommonMethod.showToastShort(msg, getApplicationContext());
            img_follower.setBackgroundResource(R.drawable.follow_resturant);
            text_follower.setText(restaurant_followers + " Followers");
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


    // call activity
    private void callActivityFinish(Intent intent) {
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left);
        finish();
    }


    //dialog open on button click
    private void openDialouge() {
        dialogMsg = new Dialog(RestaurentInfoActivity.this, R.style.AppTheme1);
        dialogMsg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogMsg.setCancelable(true);
        dialogMsg.setContentView(R.layout.avoidqueue_popup);
        dialogMsg.getWindow().setBackgroundDrawableResource(R.color.background_color);
        Window window = dialogMsg.getWindow();
        window.setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        window.setGravity(Gravity.CENTER);
        dialogMsg.getWindow()
                .getAttributes().windowAnimations = R.style.AppTheme1;
        EditText edt_name = dialogMsg.findViewById(R.id.edt_name);
        Button btn_submit = dialogMsg.findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonMethod.hideKeyBoard(RestaurentInfoActivity.this);
                String name = edt_name.getText().toString().trim();
                if (!TextUtils.isEmpty(name)) {
                    requestParamAvoidQueue(name);
                } else {
                    CommonMethod.showToastShort(getString(R.string.valid_name), getApplicationContext());
                }
            }
        });

        dialogMsg.show();
    }

}
