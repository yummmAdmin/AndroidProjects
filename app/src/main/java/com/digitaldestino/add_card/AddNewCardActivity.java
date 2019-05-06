package com.digitaldestino.add_card;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.digitaldestino.R;
import com.digitaldestino.articleUI.ArticleActivity;
import com.digitaldestino.book_table.BookTableActivity;
import com.digitaldestino.book_table.BookTablePresenter;
import com.digitaldestino.bookingtable_list.BookingTableList;
import com.digitaldestino.dishes_restaurentUI.DishesRestaurentActivity;
import com.digitaldestino.notification.NotificationActivity;
import com.digitaldestino.payment.PaymentActivity;
import com.digitaldestino.utils.BMSPrefs;
import com.digitaldestino.utils.CommonMethod;
import com.digitaldestino.utils.ConnectionDetector;
import com.digitaldestino.utils.Constants;
import com.digitaldestino.wishlist.WishlistActivity;
import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;
import com.stripe.android.view.CardMultilineWidget;

import java.util.HashMap;

import static java.security.AccessController.getContext;

public class AddNewCardActivity extends AppCompatActivity implements View.OnClickListener, AddCardContract.View {
    private ImageView img_back,img_bookmark,img_notification;
    private Button btn_addcard;
    private String session_token, user_name, _id, user_email;
    private CardMultilineWidget cardMultilineWidget;
    private ConnectionDetector connectionDetector;
    private boolean isInternetPresent = false;
    private ProgressDialog progressDialog;
    private AddCardPresenter addCardPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);
        findView();
    }

    // intialize object here...
    private void findView() {
        _id = BMSPrefs.getString(AddNewCardActivity.this, Constants._ID);
        session_token = BMSPrefs.getString(AddNewCardActivity.this, Constants.SESSION_TOKEN);
        user_name = BMSPrefs.getString(AddNewCardActivity.this, Constants.USER_NAME);
        user_email = BMSPrefs.getString(AddNewCardActivity.this, Constants.USER_EMAIL);
        connectionDetector = new ConnectionDetector(getApplicationContext());
        isInternetPresent = connectionDetector.isConnectingToInternet();
        img_back = findViewById(R.id.img_back);
        btn_addcard = findViewById(R.id.btn_addcard);
        cardMultilineWidget = findViewById(R.id.card);
        img_bookmark=findViewById(R.id.img_bookmark);
        img_notification=findViewById(R.id.img_notification);
        img_back.setOnClickListener(this);
        btn_addcard.setOnClickListener(this);
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
                Card card = cardMultilineWidget.getCard();
                if (card == null) {
                    CommonMethod.showToastShort(getString(R.string.valid_card_detail), getApplicationContext());
                } else {
                    if (isInternetPresent) {
                        CommonMethod.hideKeyBoard(AddNewCardActivity.this);
                        progressDialog = CommonMethod.showProgressDialog(progressDialog, AddNewCardActivity.this, getString(R.string.please_wait));
                        Stripe stripe = new Stripe(getApplicationContext(), getString(R.string.stripe_publishable_key));
                        stripe.createToken(card, new TokenCallback() {
                                    public void onSuccess(Token token) {
                                        CommonMethod.hideProgressDialog(progressDialog);
                                        //  CommonMethod.showToastShort("token is:" + token.getId(), getApplicationContext());
                                        Log.d("token:", "" + token.getId());
                                        requestParam(token.getId());
                                    }

                                    public void onError(Exception placeholder) {
                                        // Show localized placeholder message
                                        CommonMethod.showToastShort("" + placeholder.getLocalizedMessage(), getApplicationContext());
                                    }
                                }
                        );
                    } else {
                        CommonMethod.showAlertDialog(AddNewCardActivity.this, getString(R.string.internet_toast));
                    }
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

    // call activity method
    private void callActivity(Intent intent) {
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left);
    }

    // request param
    private void requestParam(String stripe_token) {
        HashMap<String, String> params = new HashMap<>();
        params.put("session_token", session_token);
        params.put("seeker_id", _id);
        params.put("email", user_email);
        params.put("name", user_name);
        params.put("stripe_token", stripe_token);
        Log.d("param", params.toString());
        addCardPresenter = new AddCardPresenter(this);
        addCardPresenter.requestAddCard(params);
    }

    @Override
    public void showProgress() {
        //      progressDialog = CommonMethod.showProgressDialog(progressDialog, AddNewCardActivity.this, getString(R.string.please_wait));
    }

    @Override
    public void hideProgress() {
        CommonMethod.hideKeyBoard(AddNewCardActivity.this);
    }

    @Override
    public void setDataToViews(String status, String msg, String key) {
        if (status.equalsIgnoreCase("1") && key.equalsIgnoreCase("addcard")) {
            CommonMethod.showToastShort(msg, getApplicationContext());
            callActivityFinish(new Intent(getApplicationContext(), PaymentActivity.class));
        } else if (status.equalsIgnoreCase("0") && key.equalsIgnoreCase("invalid_session")) {
            BMSPrefs.putString(getApplicationContext(), Constants._ID, "");
            CommonMethod.showToastShort(msg, getApplicationContext());
            callActivityFinish(new Intent(getApplicationContext(), ArticleActivity.class));
        } else {
            CommonMethod.showToastShort(msg, getApplicationContext());
        }
    }

    @Override
    public void onResponseFailure(Throwable throwable) {

    }
}
