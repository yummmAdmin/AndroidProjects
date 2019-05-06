package com.digitaldestino.bookingtable_list;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.digitaldestino.R;
import com.digitaldestino.adapter.ArticleAdapter;
import com.digitaldestino.adapter.BookingTableAdapter;
import com.digitaldestino.articleUI.ArticleActivity;
import com.digitaldestino.change_pwd.ChangePasswordActivity;
import com.digitaldestino.change_pwd.ChangePwdContract;
import com.digitaldestino.edit_profile.EditProfileActivity;
import com.digitaldestino.menu_list.MenuActivity;
import com.digitaldestino.modelClass.book_table.TableData;
import com.digitaldestino.notification.NotificationActivity;
import com.digitaldestino.utils.BMSPrefs;
import com.digitaldestino.utils.CommonMethod;
import com.digitaldestino.utils.ConnectionDetector;
import com.digitaldestino.utils.Constants;
import com.digitaldestino.wishlist.WishlistActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class BookingTableList extends AppCompatActivity implements View.OnClickListener, BookingTableContract.View {
    private ImageView img_back,img_bookmark,img_notification;
    private RecyclerView recycler_bookingtable;
    private TextView text_no_booking;
    private BookingTableAdapter bookingTableAdapter;
    private LinearLayoutManager linearLayoutManager;
    private String _id, session_token;
    private ArrayList<TableData> tableDataArrayList = new ArrayList<>();
    private ProgressDialog progressDialog;
    private ConnectionDetector connectionDetector;
    private boolean isInternetPresent = false;
    private BookingTablePresenter bookingTablePresenter;
    private static final String TAG = "BookingTableList";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_table_list);
        findView();
        if (isInternetPresent) {
            requestParam();
        } else {
            CommonMethod.showAlertDialog(BookingTableList.this, getString(R.string.internet_toast));
        }
    }

    // initialize object here...
    private void findView() {
        connectionDetector = new ConnectionDetector(getApplicationContext());
        isInternetPresent = connectionDetector.isConnectingToInternet();
        _id = BMSPrefs.getString(BookingTableList.this, Constants._ID);
        session_token = BMSPrefs.getString(BookingTableList.this, Constants.SESSION_TOKEN);
        img_back = findViewById(R.id.img_back);
        recycler_bookingtable = findViewById(R.id.recycler_bookingtable);
        text_no_booking = findViewById(R.id.text_no_booking);
        img_bookmark=findViewById(R.id.img_bookmark);
        img_notification=findViewById(R.id.img_notification);
        img_back.setOnClickListener(this);
        img_bookmark.setOnClickListener(this);
        img_notification.setOnClickListener(this);
    }

    // set adapter
    private void setAdapter() {
        bookingTableAdapter = new BookingTableAdapter(getApplicationContext(), tableDataArrayList);
        linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recycler_bookingtable.setLayoutManager(linearLayoutManager);
        recycler_bookingtable.setAdapter(bookingTableAdapter);
    }


    // request param
    private void requestParam() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("session_token", session_token);
        hashMap.put("seeker_id", _id);
        bookingTablePresenter = new BookingTablePresenter(this);
        bookingTablePresenter.requestgetBookTable(hashMap);
    }

    // manage listener here...
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back: {
                onBackPressed();
                break;
            }
            case R.id.img_bookmark:
            {
                callActivity(new Intent(getApplicationContext(), WishlistActivity.class));
                break;
            }
            case R.id.img_notification:
            {
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

    private void callActivityFinish(Intent intent) {
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left);
        finish();
    }

    // handle backpress
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        callActivity(new Intent(getApplicationContext(), MenuActivity.class));
        finish();
    }

    @Override
    public void showProgress() {
        progressDialog = CommonMethod.showProgressDialog(progressDialog, BookingTableList.this, getString(R.string.please_wait));
    }

    @Override
    public void hideProgress() {
        CommonMethod.hideProgressDialog(progressDialog);
    }

    @Override
    public void setDataToRecyclerView(String status, String msg, String key, ArrayList<TableData> tableData) {
        if (status.equalsIgnoreCase("1") && key.equalsIgnoreCase("gettable")) {
            tableDataArrayList = tableData;
            if (tableDataArrayList.size() > 0) {
                text_no_booking.setVisibility(View.GONE);
                recycler_bookingtable.setVisibility(View.VISIBLE);
                setAdapter();
            } else {
                text_no_booking.setVisibility(View.VISIBLE);
                recycler_bookingtable.setVisibility(View.GONE);
            }
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
