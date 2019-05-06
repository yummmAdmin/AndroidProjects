package com.digitaldestino.wishlist;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.digitaldestino.R;
import com.digitaldestino.adapter.DishesAdapter;
import com.digitaldestino.adapter.WishlistAdapter;
import com.digitaldestino.articleUI.ArticleActivity;
import com.digitaldestino.book_table.BookTableActivity;
import com.digitaldestino.book_table.BookTablePresenter;
import com.digitaldestino.dishes_restaurentUI.DishesRestaurentActivity;
import com.digitaldestino.modelClass.get_favourite.FavData;
import com.digitaldestino.utils.BMSPrefs;
import com.digitaldestino.utils.CommonMethod;
import com.digitaldestino.utils.ConnectionDetector;
import com.digitaldestino.utils.Constants;
import com.digitaldestino.utils.GPSTracker;

import java.util.ArrayList;
import java.util.HashMap;

public class WishlistActivity extends AppCompatActivity implements View.OnClickListener, WishlistContract.View {
    private RecyclerView recycler_whislist;
    private ImageView img_back;
    private String session_token, seekerId,Latitude,Longitude;
    private ProgressDialog progressDialog;
    private boolean isInternetPresent = false;
    private ConnectionDetector connectionDetector;
    private WishlistPresenter wishlistPresenter;
    private ArrayList<FavData> favDataArrayList = new ArrayList<>();
    private WishlistAdapter wishlistAdapter;
    private final static String TAG = "WishlistActivity";
    private GPSTracker gpsTracker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);
        findView();
        if (isInternetPresent) {
            requestParam();
        } else {
            CommonMethod.showAlertDialog(WishlistActivity.this, getString(R.string.internet_toast));

        }
    }

    // initialize object...
    private void findView() {
        session_token = BMSPrefs.getString(WishlistActivity.this, Constants.SESSION_TOKEN);
        seekerId = BMSPrefs.getString(WishlistActivity.this, Constants.Seeker_id);
        connectionDetector = new ConnectionDetector(getApplicationContext());
        isInternetPresent = connectionDetector.isConnectingToInternet();
        recycler_whislist = findViewById(R.id.recycler_whislist);
        img_back = findViewById(R.id.img_back);
        getCurrentLatLong();
        img_back.setOnClickListener(this);

    }

    //get current latitude and longitude
    private void getCurrentLatLong() {
        gpsTracker = new GPSTracker(WishlistActivity.this);
        Latitude = String.valueOf(gpsTracker.getLatitude());
        Longitude = String.valueOf(gpsTracker.getLongitude());
    }


    // adapter
    private void setAdapter() {
        wishlistAdapter = new WishlistAdapter(getApplicationContext(), favDataArrayList);
        recycler_whislist.setLayoutManager(new GridLayoutManager(this, 2));
        recycler_whislist.setAdapter(wishlistAdapter);
    }

    // request param
    private void requestParam() {
        HashMap<String, String> params = new HashMap<>();
        params.put("session_token", session_token);
        params.put("seekerId", seekerId);
        params.put("lat", Latitude);
        params.put("lng",Longitude);
        Log.d("param", params.toString());
        wishlistPresenter = new WishlistPresenter(this);
        wishlistPresenter.requestGetFavourites(params);
    }

    // listener...
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back: {
                onBackPressed();
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

    // call activity method
    private void callActivityFinish(Intent intent) {
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left);
    }

    @Override
    public void showProgress() {
        progressDialog = CommonMethod.showProgressDialog(progressDialog, WishlistActivity.this, getString(R.string.please_wait));
    }

    @Override
    public void hideProgress() {
        CommonMethod.hideProgressDialog(progressDialog);
    }

    @Override
    public void setDataToRecycler(String status, String msg, String key, ArrayList<FavData> favData) {
        if (status.equalsIgnoreCase("1") && key.equalsIgnoreCase("getFavourites")) {
            //  CommonMethod.showToastShort(msg, getApplicationContext());
            favDataArrayList = favData;
            setAdapter();
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
