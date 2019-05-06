package com.digitaldestino.menu_list;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.digitaldestino.R;
import com.digitaldestino.adapter.MenuListAdapter;
import com.digitaldestino.articleUI.ArticleActivity;
import com.digitaldestino.avoid_queue.AvoidQueueActivity;
import com.digitaldestino.bookingtable_list.BookingTableList;
import com.digitaldestino.cart.Cart_Activity;
import com.digitaldestino.dishes_restaurentUI.DishesRestaurentActivity;
import com.digitaldestino.edit_profile.EditProfileActivity;
import com.digitaldestino.listener.onArticleItemListener;
import com.digitaldestino.modelClass.menu.MenuItem;
import com.digitaldestino.my_order.MyOrderActivity;
import com.digitaldestino.notification.NotificationActivity;
import com.digitaldestino.payment.PaymentActivity;
import com.digitaldestino.payment_address.PaymentAddress;
import com.digitaldestino.referal_code.ReferalCodeActivity;
import com.digitaldestino.send_feedback.SendFeedbackActivity;
import com.digitaldestino.setting.SettingActivity;
import com.digitaldestino.staticUI.PrivacyPolicy;
import com.digitaldestino.utils.BMSPrefs;
import com.digitaldestino.utils.CommonMethod;
import com.digitaldestino.utils.ConnectionDetector;
import com.digitaldestino.utils.Constants;
import com.digitaldestino.wishlist.WishlistActivity;
import com.facebook.login.LoginManager;

import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;

import java.util.ArrayList;
import java.util.HashMap;


public class MenuActivity extends AppCompatActivity implements onArticleItemListener, View.OnClickListener, MenuListContract.View {
    private RecyclerView recycler_menulist;
    private MenuListAdapter menuListAdapter;
    private ArrayList<MenuItem> menuItemArrayList = new ArrayList<>();
    private ImageView img_back, img_home, img_list, img_cart, img_menu, img_scan, img_notification;
    private String session_token;
    private ProgressDialog progressDialog;
    private ConnectionDetector connectionDetector;
    private boolean isInternetPresent = false;
    private LogoutPresenter logoutPresenter;
    private final String TAG = "MenuActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        findView();
        addData();
        setAdapter();
    }

    // initialize object here...
    private void findView() {
        logoutPresenter = new LogoutPresenter(this);
        session_token = BMSPrefs.getString(MenuActivity.this, Constants.SESSION_TOKEN);
        connectionDetector = new ConnectionDetector(getApplicationContext());
        isInternetPresent = connectionDetector.isConnectingToInternet();
        recycler_menulist = findViewById(R.id.recycler_menulist);
        img_back = findViewById(R.id.img_back);
        img_home = findViewById(R.id.img_home);
        img_list = findViewById(R.id.img_list);
        img_cart = findViewById(R.id.img_cart);
        img_menu = findViewById(R.id.img_menu);
        img_scan = findViewById(R.id.img_scan);
        img_notification = findViewById(R.id.img_notification);
        img_menu.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.menu));
        img_cart.setOnClickListener(this);
        img_back.setOnClickListener(this);
        img_home.setOnClickListener(this);
        img_list.setOnClickListener(this);
        img_scan.setOnClickListener(this);
        img_notification.setOnClickListener(this);
    }

    //adapter
    private void setAdapter() {
        menuListAdapter = new MenuListAdapter(getApplicationContext(), menuItemArrayList, this);
        recycler_menulist.setLayoutManager(new GridLayoutManager(this, 3));
        recycler_menulist.setAdapter(menuListAdapter);
    }

    // listener method
    @Override
    public void onArticleItemClick(int position, String identifier) {

    }

    // listener method
    @Override
    public void onMenuItemClick(int position) {
        switch (position) {
            case 0: {
                callActivityFinish(new Intent(getApplicationContext(), ArticleActivity.class));
                break;
            }
            case 1: {
                callActivity(new Intent(getApplicationContext(), Cart_Activity.class));
                break;
            }

            case 2: {
                callActivity(new Intent(getApplicationContext(), WishlistActivity.class));
                break;
            }

            case 3: {
                callActivity(new Intent(getApplicationContext(), BookingTableList.class));
                break;
            }

            case 4: {
                callActivity(new Intent(getApplicationContext(), MyOrderActivity.class));
                break;
            }

            case 5: {
                BMSPrefs.putString(getApplicationContext(), "payment_status", "2");
                callActivity(new Intent(getApplicationContext(), PaymentActivity.class));
                break;
            }

            case 7: {
                callActivity(new Intent(getApplicationContext(), AvoidQueueActivity.class));
                break;
            }

            case 8: {
                callActivity(new Intent(getApplicationContext(), SettingActivity.class));
                break;
            }

            case 9: {
                callActivity(new Intent(getApplicationContext(), ReferalCodeActivity.class));
                break;
            }
            case 10: {
                callActivity(new Intent(getApplicationContext(), SendFeedbackActivity.class));
                break;
            }
            case 11: {
                CommonMethod.showToastShort(getString(R.string.under_development),getApplicationContext());
                break;
            }
            case 12: {
                callActivity(new Intent(getApplicationContext(), PrivacyPolicy.class));
                break;
            }
            case 13: {
                callActivity(new Intent(getApplicationContext(), EditProfileActivity.class));
                break;
            }
            case 14: {
                if (isInternetPresent) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle(getString(R.string.app_name));
                    builder.setMessage(getString(R.string.logout_msg));
                    builder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            try {
                                HashMap<String, String> params = new HashMap<>();
                                params.put("session_token", session_token);
                                logoutPresenter.logoutFromServer(params);
                            } catch (Exception e) {

                            }
                        }
                    });

                    builder.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    builder.create().show();

                } else {
                    CommonMethod.showAlertDialog(MenuActivity.this, getString(R.string.internet_toast));
                }
                break;
            }
        }
    }

    @Override
    public void onRestaurantItemClick(int position) {

    }

    //backpress
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        callActivityFinish(new Intent(getApplicationContext(), ArticleActivity.class));
    }


    // listener
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back: {
                onBackPressed();
                break;
            }

            case R.id.img_home: {
                callActivityFinish(new Intent(getApplicationContext(), ArticleActivity.class));
                break;
            }

            case R.id.img_list: {
                callActivityFinish(new Intent(getApplicationContext(), DishesRestaurentActivity.class));
                break;
            }

            case R.id.img_cart: {
                callActivityFinish(new Intent(getApplicationContext(), Cart_Activity.class));
                break;
            }

            case R.id.img_scan: {
                callActivity(new Intent(getApplicationContext(), AvoidQueueActivity.class));
                break;
            }

            case R.id.img_notification: {
                callActivity(new Intent(getApplicationContext(), NotificationActivity.class));
                break;
            }
        }
    }

    // data add
    private void addData() {
        menuItemArrayList.add(new MenuItem(getString(R.string.home), R.drawable.home_grey));
        menuItemArrayList.add(new MenuItem(getString(R.string.my_order), R.drawable.cart_grey));
        menuItemArrayList.add(new MenuItem(getString(R.string.wishlist), R.drawable.wish_list));
        menuItemArrayList.add(new MenuItem(getString(R.string.book_table), R.drawable.food));
        menuItemArrayList.add(new MenuItem(getString(R.string.order_history), R.drawable.hist));
        menuItemArrayList.add(new MenuItem(getString(R.string.manage_card), R.drawable.manage_card));
        menuItemArrayList.add(new MenuItem(getString(R.string.loyalty), R.drawable.loyalty));
        menuItemArrayList.add(new MenuItem(getString(R.string.scan_qr), R.drawable.scan_qr));
        menuItemArrayList.add(new MenuItem(getString(R.string.setting), R.drawable.seting_grey));
        menuItemArrayList.add(new MenuItem(getString(R.string.referal_code), R.drawable.code));
        menuItemArrayList.add(new MenuItem(getString(R.string.send_feedback), R.drawable.send));
        menuItemArrayList.add(new MenuItem(getString(R.string.rate_us_play_store), R.drawable.play_store));
        menuItemArrayList.add(new MenuItem(getString(R.string.privacy_policy), R.drawable.privacy));
        menuItemArrayList.add(new MenuItem(getString(R.string.edit_profile), R.drawable.profile));
        menuItemArrayList.add(new MenuItem(getString(R.string.logout), R.drawable.logout));
        //ggg
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

    @Override
    public void showProgress() {
        progressDialog = CommonMethod.showProgressDialog(progressDialog, MenuActivity.this, getString(R.string.please_wait));
    }

    @Override
    public void hideProgress() {
        CommonMethod.hideProgressDialog(progressDialog);
    }

    @Override
    public void setDataToViews(String status, String msg)
    {
        if (status.equalsIgnoreCase("1")) {
            logout(msg);
        } else {
            logout(msg);
        }
    }


    @Override
    public void onResponseFailure(Throwable throwable) {
        Log.d(TAG, throwable.getMessage());
    }

    private void logout(String msg) {
//        TwitterCore.getInstance().getSessionManager().clearActiveSession();
        LoginManager.getInstance().logOut();
        twitterLogout();
        BMSPrefs.putString(MenuActivity.this, Constants._ID, "");
        BMSPrefs.putString(getApplicationContext(), Constants.Seeker_id, "");
        BMSPrefs.putString(getApplicationContext(), Constants.USER_NAME, "");
        BMSPrefs.putString(getApplicationContext(), Constants.USER_Address, "");
        BMSPrefs.putString(getApplicationContext(), Constants.USER_NUMBER, "");
        CommonMethod.showToastShort(msg, getApplicationContext());
        callActivityFinish(new Intent(getApplicationContext(), ArticleActivity.class));
    }

    // twitter logout
    private void twitterLogout() {
        try {
            TwitterSession twitterSession = TwitterCore.getInstance().getSessionManager().getActiveSession();
            if (twitterSession != null) {
                TwitterCore.getInstance().getSessionManager().clearActiveSession();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
