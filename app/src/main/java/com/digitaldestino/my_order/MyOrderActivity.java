package com.digitaldestino.my_order;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.digitaldestino.R;
import com.digitaldestino.adapter.MyOrderAdapter;
import com.digitaldestino.articleUI.ArticleActivity;
import com.digitaldestino.listener.onOrderSelectListener;
import com.digitaldestino.menu_list.MenuActivity;
import com.digitaldestino.modelClass.my_order.BookingData;
import com.digitaldestino.modelClass.my_order.Items;
import com.digitaldestino.notification.NotificationActivity;
import com.digitaldestino.order_detail.OrderDetailActivity;
import com.digitaldestino.payment.PaymentActivity;
import com.digitaldestino.payment.PaymentPresenter;
import com.digitaldestino.reset_pwd.ResetPasswordActivity;
import com.digitaldestino.reset_pwd.ResetPwdContract;
import com.digitaldestino.utils.BMSPrefs;
import com.digitaldestino.utils.CommonMethod;
import com.digitaldestino.utils.ConnectionDetector;
import com.digitaldestino.utils.Constants;
import com.digitaldestino.wishlist.WishlistActivity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class MyOrderActivity extends AppCompatActivity implements View.OnClickListener, MyOrderContract.View, onOrderSelectListener {
    private ImageView img_back, img_bookmark,img_notification;
    private TextView text_fromdate, text_todate, text_no_order;
    private RecyclerView recycler_orderlist;
    private MyOrderAdapter myOrderAdapter;
    private Date from_date, to_date;
    private String first_date, last_date, session_token, _id, From_date = "", To_date = "";
    private LinearLayoutManager linearLayoutManager;
    private ProgressDialog progressDialog;
    private ConnectionDetector connectionDetector;
    private boolean isInternetPresent = false;
    private Calendar calendar;
    private MyOrderPresenter myOrderPresenter;
    private ArrayList<BookingData> bookingDataArrayList = new ArrayList<>();
    private ArrayList<BookingData> bookingDataArrayListTemp = new ArrayList<>();
    private ArrayList<Items> itemsArrayList = new ArrayList<>();
    private static final String TAG = "MyOrderActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        findView();
        if (isInternetPresent) {
            requestParam();
        } else {
            CommonMethod.showAlertDialog(MyOrderActivity.this, getString(R.string.internet_toast));
        }
    }

    // initialize object here...
    private void findView() {
        _id = BMSPrefs.getString(MyOrderActivity.this, Constants._ID);
        session_token = BMSPrefs.getString(MyOrderActivity.this, Constants.SESSION_TOKEN);
        connectionDetector = new ConnectionDetector(getApplicationContext());
        isInternetPresent = connectionDetector.isConnectingToInternet();
        img_back = findViewById(R.id.img_back);
        text_fromdate = findViewById(R.id.text_fromdate);
        text_todate = findViewById(R.id.text_todate);
        recycler_orderlist = findViewById(R.id.recycler_orderlist);
        text_no_order = findViewById(R.id.text_no_order);
        img_bookmark = findViewById(R.id.img_bookmark);
        img_notification=findViewById(R.id.img_notification);
        img_back.setOnClickListener(this);
        text_fromdate.setOnClickListener(this);
        text_todate.setOnClickListener(this);
        img_bookmark.setOnClickListener(this);
        img_notification.setOnClickListener(this);
    }

    // adapter method
    private void setAdapter() {
        recycler_orderlist = findViewById(R.id.recycler_orderlist);
        myOrderAdapter = new MyOrderAdapter(getApplicationContext(), bookingDataArrayList, this);
        linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recycler_orderlist.setLayoutManager(linearLayoutManager);
        recycler_orderlist.setAdapter(myOrderAdapter);
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
            case R.id.text_fromdate: {
                calendar = Calendar.getInstance();
                int yy = calendar.get(Calendar.YEAR);
                int mm = calendar.get(Calendar.MONTH);
                int dd = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePicker = new DatePickerDialog(MyOrderActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        first_date = String.valueOf(year) + "-" + String.valueOf(monthOfYear + 1)
                                + "-" + String.valueOf(dayOfMonth);
                        text_fromdate.setText(first_date);
                    }
                }, yy, mm, dd);
                datePicker.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePicker.show();
                break;
            }
            case R.id.text_todate: {
                if (text_fromdate.getText().toString().equalsIgnoreCase(getString(R.string.from_date))) {
                    CommonMethod.showToastShort(getString(R.string.select_from_date), getApplicationContext());
                } else {
                    final Calendar calendar = Calendar.getInstance();
                    int yy = calendar.get(Calendar.YEAR);
                    int mm = calendar.get(Calendar.MONTH);
                    int dd = calendar.get(Calendar.DAY_OF_MONTH);
                    DatePickerDialog datePicker = new DatePickerDialog(MyOrderActivity.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            last_date = String.valueOf(year) + "-" + String.valueOf(monthOfYear + 1)
                                    + "-" + String.valueOf(dayOfMonth);
                            DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                            from_date = null;
                            to_date = null;
                            try {
                                from_date = format.parse(first_date);
                                to_date = format.parse(last_date);
                                if (to_date.after(from_date) || to_date.equals(from_date)) {
                                    text_todate.setText(last_date);
                                    From_date = text_fromdate.getText().toString().trim();
                                    To_date = text_todate.getText().toString().trim();
                                    requestParam();
                                } else {
                                    Toast.makeText(getApplicationContext(), "to date can not be less than from date", Toast.LENGTH_SHORT).show();
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                    }, yy, mm, dd);
                    datePicker.getDatePicker().setMaxDate(System.currentTimeMillis());
                    datePicker.show();
                }
                break;
            }
        }
    }

    // on back press method...
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        callActivity(new Intent(getApplicationContext(), MenuActivity.class));
    }

    // call activity
    private void callActivityFinish(Intent intent) {
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left);
        finish();
    }

    // call activity method
    private void callActivity(Intent intent) {
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left);
    }

    // request param
    private void requestParam() {
        HashMap<String, String> params = new HashMap<>();
        params.put("session_token", session_token);
        params.put("seeker_id", _id);
        params.put("from_date", From_date);
        params.put("to_date", To_date);
        Log.d("param", params.toString());
        Log.e("param", params.toString());
        myOrderPresenter = new MyOrderPresenter(this);
        myOrderPresenter.requestMyOrder(params);
    }

    @Override
    public void showProgress() {
        progressDialog = CommonMethod.showProgressDialog(progressDialog, MyOrderActivity.this, getString(R.string.please_wait));
    }

    @Override
    public void hideProgress() {
        CommonMethod.hideProgressDialog(progressDialog);
    }

    @Override
    public void setDataToViews(String status, String msg, String key, ArrayList<BookingData> bookingDataArrayLists) {
        bookingDataArrayList.clear();
        if (status.equalsIgnoreCase("1") && key.equalsIgnoreCase("getbooking")) {
            bookingDataArrayList = bookingDataArrayLists;
            if (bookingDataArrayList.size() > 0) {
                text_no_order.setVisibility(View.GONE);
                recycler_orderlist.setVisibility(View.VISIBLE);
                setAdapter();
            } else {
                text_no_order.setVisibility(View.VISIBLE);
                recycler_orderlist.setVisibility(View.GONE);
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

    //manage listener
    @Override
    public void onOrderSelectItemClick(int position) {
        bookingDataArrayListTemp.clear();
        itemsArrayList = bookingDataArrayList.get(position).getItems();
        BMSPrefs.putString(getApplicationContext(), Constants.ORDER_ID, bookingDataArrayList.get(position).get_id());
        BMSPrefs.putString(getApplicationContext(), Constants.RESTAURANT_ID, bookingDataArrayList.get(position).getRestaurant_id());
        BookingData aa = bookingDataArrayList.get(position);
        bookingDataArrayListTemp.add(aa);

        Intent intent = new Intent(getApplicationContext(), OrderDetailActivity.class);
        intent.putParcelableArrayListExtra("bookingDataArrayList", bookingDataArrayListTemp);
        intent.putParcelableArrayListExtra("itemsArrayList", itemsArrayList);
        startActivity(intent);
        overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left);
        //    callActivity(new Intent(getApplicationContext(), OrderDetailActivity.class));
    }
}
