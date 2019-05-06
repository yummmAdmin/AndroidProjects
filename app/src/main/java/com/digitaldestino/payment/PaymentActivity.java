package com.digitaldestino.payment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.digitaldestino.R;
import com.digitaldestino.adapter.CardListAdapter;
import com.digitaldestino.add_card.AddCardPresenter;
import com.digitaldestino.add_card.AddNewCardActivity;
import com.digitaldestino.articleUI.ArticleActivity;
import com.digitaldestino.cart.Cart_Activity;
import com.digitaldestino.listener.onCardSelectListener;
import com.digitaldestino.modelClass.get_card.Card_list;
import com.digitaldestino.notification.NotificationActivity;
import com.digitaldestino.payment_address.PaymentAddress;
import com.digitaldestino.reset_pwd.ResetPasswordActivity;
import com.digitaldestino.scan_qr.ScanQrActivity;
import com.digitaldestino.utils.BMSPrefs;
import com.digitaldestino.utils.CommonMethod;
import com.digitaldestino.utils.ConnectionDetector;
import com.digitaldestino.utils.Constants;
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

public class PaymentActivity extends AppCompatActivity implements View.OnClickListener, onCardSelectListener, PaymentContract.View, DeleteCardContract.View, OrderPaymentContract.View {
    private ImageView img_back, img_bookmark, img_notification;
    private Button btn_addcard, btn_send;
    private TextView added_card,text_or;
    private RecyclerView recycler_card_list;
    private CardListAdapter cardListAdapter;
    private LinearLayoutManager linearLayoutManager;
    private ProgressDialog progressDialog;
    private String session_token, _id, delivery_address, card_id, stripe_customer_id, order_timezone, seekerId, discount, drop_latitude, drop_longitude, payment_status;
    private ConnectionDetector connectionDetector;
    private boolean isInternetPresent = false;
    private ArrayList<Card_list> card_listArrayList = new ArrayList<>();
    private PaymentPresenter paymentPresenter;
    private DeleteCardPresenter deleteCardPresenter;
    private OrderPaymentPresenter orderPaymentPresenter;
    private Date date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        findView();
        if (isInternetPresent) {
            requestParam();
        } else {
            CommonMethod.showAlertDialog(PaymentActivity.this, getString(R.string.internet_toast));
        }
    }

    // to initialize object here...
    private void findView() {
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String current_time = format.format(Calendar.getInstance().getTime());
        try {
            date = format.parse(current_time);
            //    CommonMethod.showToastShort("" + date.getTime(), getApplicationContext());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        drop_latitude = BMSPrefs.getString(PaymentActivity.this, Constants.LATI);
        drop_longitude = BMSPrefs.getString(PaymentActivity.this, Constants.LONGI);
        seekerId = BMSPrefs.getString(PaymentActivity.this, Constants.Seeker_id);
        payment_status = BMSPrefs.getString(PaymentActivity.this, "payment_status");
        connectionDetector = new ConnectionDetector(getApplicationContext());
        isInternetPresent = connectionDetector.isConnectingToInternet();
        order_timezone = TimeZone.getDefault().getID();
        _id = BMSPrefs.getString(PaymentActivity.this, Constants._ID);
        session_token = BMSPrefs.getString(PaymentActivity.this, Constants.SESSION_TOKEN);
        discount = BMSPrefs.getString(PaymentActivity.this, Constants.DISCOUNT);
        delivery_address = BMSPrefs.getString(PaymentActivity.this, Constants.DELIVERY_ADDRESS);
        img_back = findViewById(R.id.img_back);
        added_card = findViewById(R.id.added_card);
        btn_addcard = findViewById(R.id.btn_addcard);
        btn_send = findViewById(R.id.btn_send);
        text_or=findViewById(R.id.text_or);

        img_bookmark = findViewById(R.id.img_bookmark);
        img_notification = findViewById(R.id.img_notification);

        if(payment_status.equalsIgnoreCase("1"))
        {
            btn_send.setVisibility(View.VISIBLE);
            text_or.setVisibility(View.VISIBLE);
        }
        else
        {
            btn_send.setVisibility(View.GONE);
            text_or.setVisibility(View.GONE);
        }

        btn_addcard.setOnClickListener(this);
        img_back.setOnClickListener(this);
        btn_send.setOnClickListener(this);
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

            case R.id.btn_addcard: {
                callActivity(new Intent(getApplicationContext(), AddNewCardActivity.class));
                break;
            }
            case R.id.btn_send: {
                if (isInternetPresent) {
                    if (card_listArrayList.size() > 0) {
                        requestPaymentParam(card_id, stripe_customer_id);
                    } else {
                        CommonMethod.showToastShort(getString(R.string.valid_add_card), getApplicationContext());
                    }
                } else {
                    CommonMethod.showAlertDialog(PaymentActivity.this, getString(R.string.internet_toast));
                }
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

    //adapter
    private void setAdapter() {
        recycler_card_list = findViewById(R.id.recycler_card_list);
        cardListAdapter = new CardListAdapter(getApplicationContext(), this, card_listArrayList);
        linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recycler_card_list.setLayoutManager(linearLayoutManager);
        recycler_card_list.setAdapter(cardListAdapter);
    }

    // backpress
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left);
        finish();
        //  callActivityFinish(new Intent(getApplicationContext(), PaymentAddress.class));
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
        Log.d("param", params.toString());
        paymentPresenter = new PaymentPresenter(this);
        paymentPresenter.requestCardList(params);
    }

    // request delete card param
    private void requestDeleteParam(String card_id) {
        HashMap<String, String> params = new HashMap<>();
        params.put("session_token", session_token);
        params.put("seeker_id", _id);
        params.put("card_id", card_id);

        Log.d("param", params.toString());
        deleteCardPresenter = new DeleteCardPresenter(this);
        deleteCardPresenter.requestDeleteCard(params);
    }


    // request payment  param
    private void requestPaymentParam(String card_id, String stripe_customer_id) {
        HashMap<String, String> params = new HashMap<>();
        params.put("session_token", session_token);
        params.put("seeker_id", _id);
        params.put("seekerId", seekerId);
        params.put("card_id", card_id);
        params.put("stripe_customer_id", stripe_customer_id);
        params.put("order_timezone", order_timezone);
        params.put("order_date_time", "" + date.getTime());
        params.put("order_address", delivery_address);
        params.put("order_type", "delivery");
        params.put("table_id", "");
        params.put("discount", discount);
        params.put("drop_lat", drop_latitude);
        params.put("drop_lng", drop_longitude);
        Log.d("Paymentparam", params.toString());

        orderPaymentPresenter = new OrderPaymentPresenter(this);
        orderPaymentPresenter.requestOrderPyament(params);
    }

    // manage listener here...
    @Override
    public void onCardSelectItemClick(int position) {
        card_id = card_listArrayList.get(position).getId();
        stripe_customer_id = card_listArrayList.get(position).getCustomer();
    }

    @Override
    public void onCardDeleteItemClick(String card_id, int position) {
        if (isInternetPresent) {
            requestDeleteParam(card_id);
        } else {
            CommonMethod.showAlertDialog(PaymentActivity.this, getString(R.string.internet_toast));
        }
    }

    @Override
    public void showProgress() {
        progressDialog = CommonMethod.showProgressDialog(progressDialog, PaymentActivity.this, getString(R.string.please_wait));
    }

    @Override
    public void hideProgress() {
        CommonMethod.hideProgressDialog(progressDialog);
    }

    // payment method
    @Override
    public void orderPaymentResponse(String status, String msg, String key) {
        if (status.equalsIgnoreCase("1") && key.equalsIgnoreCase("booknow")) {
            CommonMethod.showToastShort(msg, getApplicationContext());
            callActivityFinish(new Intent(getApplicationContext(), ArticleActivity.class));
            BMSPrefs.putString(getApplicationContext(), Constants.DISCOUNT, "0");
        } else if (status.equalsIgnoreCase("0") && key.equalsIgnoreCase("invalid_session")) {
            BMSPrefs.putString(getApplicationContext(), Constants._ID, "");
            BMSPrefs.putString(getApplicationContext(), Constants.Seeker_id, "");
            CommonMethod.showToastShort(msg, getApplicationContext());
            LoginManager.getInstance().logOut();
            callActivityFinish(new Intent(getApplicationContext(), ArticleActivity.class));
            BMSPrefs.putString(getApplicationContext(), Constants.DISCOUNT, "0");
        } else {
            CommonMethod.showToastShort(msg, getApplicationContext());
        }
    }

    // delete card
    @Override
    public void deleteDataFromView(String status, String msg, String key) {
        if (status.equalsIgnoreCase("1") && key.equalsIgnoreCase("deletecard")) {
            CommonMethod.showToastShort(msg, getApplicationContext());
        } else if (status.equalsIgnoreCase("0") && key.equalsIgnoreCase("invalid_session")) {
            BMSPrefs.putString(getApplicationContext(), Constants._ID, "");
            CommonMethod.showToastShort(msg, getApplicationContext());
            LoginManager.getInstance().logOut();
            callActivityFinish(new Intent(getApplicationContext(), ArticleActivity.class));
        } else {
            CommonMethod.showToastShort(msg, getApplicationContext());
        }
    }

    // get card list
    @Override
    public void setDataToViews(String status, String msg, String key, ArrayList<Card_list> card_list) {
        if (status.equalsIgnoreCase("1") && key.equalsIgnoreCase("lestcard")) {
            card_listArrayList = card_list;
            if (card_listArrayList.size() > 0) {
                added_card.setVisibility(View.VISIBLE);
                card_id = card_listArrayList.get(0).getId();
                stripe_customer_id = card_listArrayList.get(0).getCustomer();
                setAdapter();
            } else {
                added_card.setVisibility(View.GONE);
            }

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

    @Override
    public void onResponseFailure(Throwable throwable) {
        Log.d("placeholder", throwable.getMessage());
        CommonMethod.hideProgressDialog(progressDialog);
    }
}
