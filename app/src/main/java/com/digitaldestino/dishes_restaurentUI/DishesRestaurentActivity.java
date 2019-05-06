package com.digitaldestino.dishes_restaurentUI;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.digitaldestino.R;
import com.digitaldestino.avoid_queue.AvoidQueueActivity;
import com.digitaldestino.cart.Cart_Activity;
import com.digitaldestino.dishes_detail.DishesDetailActivity;
import com.digitaldestino.listener.onBookmarkListener;
import com.digitaldestino.modelClass.near_restaurent.Dishes;
import com.digitaldestino.notification.NotificationActivity;
import com.digitaldestino.restaurant_info.RestaurentInfoActivity;
import com.digitaldestino.adapter.RestaurentListAdapter;
import com.digitaldestino.adapter.DishesAdapter;
import com.digitaldestino.articleUI.ArticleActivity;
import com.digitaldestino.interfaces.ApiInterface;
import com.digitaldestino.listener.onArticleItemListener;
import com.digitaldestino.loginUI.LoginActivity;
import com.digitaldestino.menu_list.MenuActivity;
import com.digitaldestino.modelClass.near_restaurent.HomeData;
import com.digitaldestino.modelClass.restaurent.RestaurentResponses;
import com.digitaldestino.networkClass.RetrofitClient;
import com.digitaldestino.search_activity.SearchActivity;
import com.digitaldestino.utils.BMSPrefs;
import com.digitaldestino.utils.CommonMethod;
import com.digitaldestino.utils.ConnectionDetector;
import com.digitaldestino.utils.Constants;
import com.digitaldestino.utils.GPSTracker;
import com.digitaldestino.wishlist.WishlistActivity;
import com.facebook.login.LoginManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class DishesRestaurentActivity extends AppCompatActivity implements onArticleItemListener, View.OnClickListener,
        DishesRestaurentContract.View, onBookmarkListener, FavDishContract.View {
    private String TAG = "DishesRestaurentActivity";
    private TextView text_nearby, text_bysale, text_restaurent, text_filter;
    private EditText edt_search;
    private LinearLayout linear_nearby, linear_sale, linear_restaurent, linear_filter;
    private ImageView img_nearby, img_sale, img_restaurent, img_filter, img_home,
            img_menu, img_cart, img_list, img_bookmark, img_scan, img_notification;
    private String Latitude, Longitude, status, msg, _id, food_item_id, seekerId,
            session_token, search_dishes = "", filter_id = "", current_time;
    private RecyclerView recycler_nearRestaurent, recycler_restaurent;
    private DishesAdapter dishesAdapter;
    private RestaurentListAdapter restaurentListAdapter;
    private LinearLayoutManager linearLayoutManager;
    private ProgressDialog progressDialog;
    private GPSTracker gpsTracker;
    private ConnectionDetector connectionDetector;
    private boolean isInternetPresent = false;
    private Intent intent;
    private ArrayList<HomeData> dishesArrayList = new ArrayList<>();
    private ArrayList<com.digitaldestino.modelClass.restaurent.HomeData> userRestaurentList = new ArrayList<>();
    private DishesListPresenter dishesListPresenter;
    private FavDishPresenter favDishPresenter;
    public static final int REQUEST_CODE = 1;
    private Date date;
    String listString = "";
    private ArrayList<String> sendDataArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_restaurent);
        findView();
        tabSelect();
        if (isInternetPresent) {
            setParamDishes("1", "0");
        } else {
            CommonMethod.showAlertDialog(DishesRestaurentActivity.this, getString(R.string.internet_toast));
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isInternetPresent) {
            tabNearBySelect();
            setParamDishes("1", "0");
        } else {
            CommonMethod.showAlertDialog(DishesRestaurentActivity.this, getString(R.string.internet_toast));
        }

    }

    private void setParamDishes(String nearby, String bysales) {
//        Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();
//        String prettyJson = prettyGson.toJson(sendDataArrayList);
        try {
            if (sendDataArrayList.size() > 0) {
                for (String s : sendDataArrayList) {
                    listString += s + ",";
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        HashMap<String, String> params = new HashMap<>();
        params.put("lat", Latitude);
        params.put("lng", Longitude);
        params.put("nearby", nearby);
        params.put("bysales", bysales);
        params.put("seekerId", seekerId);
        params.put("search", listString);
        Log.d("param", params.toString());
        dishesListPresenter.requestDataFromServer(params);

    }

    private void setParamFavDishes(String food_item_id) {
        HashMap<String, String> params = new HashMap<>();
        params.put("session_token", session_token);
        params.put("seekerId", seekerId);
        params.put("food_item_id", food_item_id);
        params.put("favourite_date_time", "" + date.getTime());
        Log.d("fav_param", params.toString());
        favDishPresenter = new FavDishPresenter(this);
        favDishPresenter.requestFavDishes(params);
    }

    private void findView() {
        try {
            sendDataArrayList = (ArrayList<String>) getIntent().getSerializableExtra("sendDataArrayList");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        current_time = df.format(Calendar.getInstance().getTime());
        try {
            date = df.parse(current_time);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        Intent intent = getIntent();
        search_dishes = intent.getStringExtra("search_dishes");

        session_token = BMSPrefs.getString(DishesRestaurentActivity.this, Constants.SESSION_TOKEN);
        seekerId = BMSPrefs.getString(DishesRestaurentActivity.this, Constants.Seeker_id);
        _id = BMSPrefs.getString(DishesRestaurentActivity.this, Constants._ID);

        gpsTracker = new GPSTracker(DishesRestaurentActivity.this);
        getCurrentLatLong();
        dishesListPresenter = new DishesListPresenter(this);
        connectionDetector = new ConnectionDetector(getApplicationContext());
        isInternetPresent = connectionDetector.isConnectingToInternet();
        text_nearby = findViewById(R.id.text_nearby);
        linear_nearby = findViewById(R.id.linear_nearby);
        img_nearby = findViewById(R.id.img_nearby);

        linear_sale = findViewById(R.id.linear_sale);
        img_sale = findViewById(R.id.img_sale);
        text_bysale = findViewById(R.id.text_bysale);
        edt_search = findViewById(R.id.edt_search);


        linear_restaurent = findViewById(R.id.linear_restaurent);
        img_restaurent = findViewById(R.id.img_restaurent);
        text_restaurent = findViewById(R.id.text_restaurent);

        linear_filter = findViewById(R.id.linear_filter);
        img_filter = findViewById(R.id.img_filter);
        text_filter = findViewById(R.id.text_filter);

        recycler_nearRestaurent = findViewById(R.id.recycler_nearRestaurent);
        recycler_restaurent = findViewById(R.id.recycler_restaurent);

        img_home = findViewById(R.id.img_home);
        img_menu = findViewById(R.id.img_menu);
        img_cart = findViewById(R.id.img_cart);
        img_list = findViewById(R.id.img_list);
        img_bookmark = findViewById(R.id.img_bookmark);
        img_scan = findViewById(R.id.img_scan);
        img_notification = findViewById(R.id.img_notification);

        img_list.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.dish));

        img_cart.setOnClickListener(this);
        img_menu.setOnClickListener(this);
        img_home.setOnClickListener(this);

        linear_nearby.setOnClickListener(this);
        linear_sale.setOnClickListener(this);
        linear_restaurent.setOnClickListener(this);
        linear_filter.setOnClickListener(this);
        edt_search.setOnClickListener(this);
        img_bookmark.setOnClickListener(this);
        img_scan.setOnClickListener(this);
        img_notification.setOnClickListener(this);
    }

    //get current latitude and longitude
    private void getCurrentLatLong() {
        Latitude = String.valueOf(gpsTracker.getLatitude());
        Longitude = String.valueOf(gpsTracker.getLongitude());
    }

    private void setAdapter() {
        recycler_nearRestaurent.setVisibility(View.VISIBLE);
        recycler_restaurent.setVisibility(View.GONE);
        dishesAdapter = new DishesAdapter(getApplicationContext(), this, dishesArrayList, this);
        recycler_nearRestaurent.setLayoutManager(new GridLayoutManager(this, 2));
        recycler_nearRestaurent.setAdapter(dishesAdapter);
    }

    private void setRestaurentAdapter() {
        recycler_nearRestaurent.setVisibility(View.GONE);
        recycler_restaurent.setVisibility(View.VISIBLE);
        restaurentListAdapter = new RestaurentListAdapter(getApplicationContext(), userRestaurentList, this);
        linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recycler_restaurent.setLayoutManager(linearLayoutManager);
        recycler_restaurent.setAdapter(restaurentListAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.linear_nearby: {
                edt_search.setText("");
                try {
                    sendDataArrayList.clear();
                    listString = "";
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                tabNearBySelect();
                if (isInternetPresent) {
                    BMSPrefs.putString(getApplicationContext(), Constants.SALE_ID, "");

                    setParamDishes("1", "0");
                } else {
                    CommonMethod.showAlertDialog(DishesRestaurentActivity.this, getString(R.string.internet_toast));
                }
                break;
            }

            case R.id.linear_sale: {
                edt_search.setText("");
                try {
                    sendDataArrayList.clear();
                    listString = "";
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                tabNearSaleSelect();
                if (isInternetPresent) {
                    BMSPrefs.putString(getApplicationContext(), Constants.SALE_ID, "1");
                    listString = "";
                    setParamDishes("0", "1");
                } else {
                    CommonMethod.showAlertDialog(DishesRestaurentActivity.this, getString(R.string.internet_toast));
                }
                break;
            }
            case R.id.linear_restaurent: {
                edt_search.setText("");
                try {
                    sendDataArrayList.clear();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                tabRestaurentSelect();
                if (isInternetPresent) {
                    getRestaurentDetail("0", "0");
                } else {
                    CommonMethod.showAlertDialog(DishesRestaurentActivity.this, getString(R.string.internet_toast));
                }
                break;
            }
            case R.id.linear_filter: {
                CommonMethod.showToastShort(getString(R.string.under_development), getApplicationContext());
                CommonMethod.callFilterSelect(getApplicationContext(), linear_filter, text_filter, img_filter);
                CommonMethod.callRestaurentUnselect(getApplicationContext(), linear_restaurent, text_restaurent, img_restaurent);
                CommonMethod.callSalesUnselect(getApplicationContext(), linear_sale, text_bysale, img_sale);
                CommonMethod.callNearByUnselect(getApplicationContext(), linear_nearby, text_nearby, img_nearby);
                break;
            }
            case R.id.img_home: {
                callActivityFinish(new Intent(getApplicationContext(), ArticleActivity.class));
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
            case R.id.img_scan: {
                if (!TextUtils.isEmpty(_id)) {
                    callActivity(new Intent(getApplicationContext(), AvoidQueueActivity.class));
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
            case R.id.img_cart: {
                if (!TextUtils.isEmpty(_id)) {
                    callActivity(new Intent(getApplicationContext(), Cart_Activity.class));
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
//                Intent intent = new Intent(getApplicationContext(),
//                        SearchActivity.class);
                //     startActivityForResult(intent, REQUEST_CODE);
                break;
            }
        }
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        try {
//            super.onActivityResult(requestCode, resultCode, data);
//
//            if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
//
//                search_dishes = data.getStringExtra("search_dishes");
//                edt_search.setText(search_dishes);
//            }
//        } catch (Exception ex) {
//            Toast.makeText(getApplicationContext(), ex.toString(),
//                    Toast.LENGTH_SHORT).show();
//        }
//    }

    private void tabSelect() {
        CommonMethod.callNearBySelect(getApplicationContext(), linear_nearby, text_nearby, img_nearby);
        CommonMethod.callSalesUnselect(getApplicationContext(), linear_sale, text_bysale, img_sale);
        CommonMethod.callRestaurentUnselect(getApplicationContext(), linear_restaurent, text_restaurent, img_restaurent);
        CommonMethod.callFilterUnselect(getApplicationContext(), linear_filter, text_filter, img_filter);
    }

    private void tabRestaurentSelect() {
        CommonMethod.callRestaurentSelect(getApplicationContext(), linear_restaurent, text_restaurent, img_restaurent);
        CommonMethod.callSalesUnselect(getApplicationContext(), linear_sale, text_bysale, img_sale);
        CommonMethod.callNearByUnselect(getApplicationContext(), linear_nearby, text_nearby, img_nearby);
        CommonMethod.callFilterUnselect(getApplicationContext(), linear_filter, text_filter, img_filter);

    }

    private void tabNearBySelect() {
//        edt_search.setText(search_dishes);
        CommonMethod.callNearBySelect(getApplicationContext(), linear_nearby, text_nearby, img_nearby);
        CommonMethod.callSalesUnselect(getApplicationContext(), linear_sale, text_bysale, img_sale);
        CommonMethod.callRestaurentUnselect(getApplicationContext(), linear_restaurent, text_restaurent, img_restaurent);
        CommonMethod.callFilterUnselect(getApplicationContext(), linear_filter, text_filter, img_filter);
    }

    private void tabNearSaleSelect() {
        CommonMethod.callSalesSelect(getApplicationContext(), linear_sale, text_bysale, img_sale);
        CommonMethod.callNearByUnselect(getApplicationContext(), linear_nearby, text_nearby, img_nearby);
        CommonMethod.callRestaurentUnselect(getApplicationContext(), linear_restaurent, text_restaurent, img_restaurent);
        CommonMethod.callFilterUnselect(getApplicationContext(), linear_filter, text_filter, img_filter);
    }

    // get login detail from api
    public void getRestaurentDetail(String nearby, String bysales) {
        final HashMap<String, String> params = new HashMap<>();
        params.put("lat", Latitude);
        params.put("lng", Longitude);
//        params.put("lat", "40.7831");
//        params.put("lng", "-73.9712");
        params.put("nearby", nearby);
        params.put("bysales", bysales);
        Log.d("param", params.toString());

        Retrofit retrofit = RetrofitClient.getRetrofitClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        apiInterface.getRestaurentsDetails(params).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RestaurentResponses>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        progressDialog = CommonMethod.showProgressDialog(progressDialog, DishesRestaurentActivity.this, getString(R.string.please_wait));
                    }

                    @Override
                    public void onNext(RestaurentResponses restaurentResponses) {
                        CommonMethod.hideProgressDialog(progressDialog);
                        if (restaurentResponses != null) {
                            status = restaurentResponses.getStatus();
                            msg = restaurentResponses.getMsg();
                            if (status.equalsIgnoreCase("1")) {
                                userRestaurentList = restaurentResponses.getResponse_data().getHomeData();
                            } else {
                                CommonMethod.showToastShort(msg, getApplicationContext());
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        CommonMethod.hideProgressDialog(progressDialog);
                        setRestaurentAdapter();
                    }
                });
    }


    // listener method
    @Override
    public void onArticleItemClick(int position, String identifier) {

    }

    @Override
    public void onMenuItemClick(int position) {
        food_item_id = dishesArrayList.get(position).getDishes().getFood_item_id();

        BMSPrefs.putString(getApplicationContext(), Constants.FOOD_ITEM_ID, food_item_id);
        BMSPrefs.putString(getApplicationContext(), "food_image", dishesArrayList.get(position).getDishes().getFood_image());
        callActivity(new Intent(getApplicationContext(), DishesDetailActivity.class));
    }


    @Override
    public void onRestaurantItemClick(int position) {
        BMSPrefs.putString(getApplicationContext(), Constants.BACK_ID, "3");
        BMSPrefs.putString(getApplicationContext(), Constants.RESTAURANT_ID, userRestaurentList.get(position).getRestaurant_id());
        intent = new Intent(getApplicationContext(), RestaurentInfoActivity.class);
        intent.putExtra("res_name", userRestaurentList.get(position).getName());
        intent.putExtra("res_address", userRestaurentList.get(position).getAddress());
        intent.putExtra("open_time", userRestaurentList.get(position).getOpentime());
        intent.putExtra("close_time", userRestaurentList.get(position).getClosetime());
        intent.putExtra("slogen", userRestaurentList.get(position).getSlogan());
        intent.putExtra("distance", userRestaurentList.get(position).getDistance());

        startActivity(intent);
        overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left);
    }

    // dishes bookmark listener
    @Override
    public void onBookmarkItemClick(int position) {
        if (!TextUtils.isEmpty(seekerId)) {
            if (isInternetPresent) {
                setParamFavDishes(dishesArrayList.get(position).getDishes().getFood_item_id());
                String feb = "";
                if (dishesArrayList.get(position).getDishes().getIs_favourite().equalsIgnoreCase("1")) {
                    feb = "0";
                }
                if (dishesArrayList.get(position).getDishes().getIs_favourite().equalsIgnoreCase("0")) {
                    feb = "1";
                }
                HomeData homeData = new HomeData();
                homeData.setAddress(dishesArrayList.get(position).getAddress());
                homeData.setDistance(dishesArrayList.get(position).getDistance());
                Dishes dishes = new Dishes();
                dishes.setFood_item_name(dishesArrayList.get(position).getDishes().getFood_item_name());
                dishes.setSalecount(dishesArrayList.get(position).getDishes().getSalecount());
                dishes.setFood_item_id(dishesArrayList.get(position).getDishes().getFood_item_id());

                dishes.setBase_price(dishesArrayList.get(position).getDishes().getBase_price());
                dishes.setFood_image(dishesArrayList.get(position).getDishes().getFood_image());
                dishes.setIs_favourite(feb);
                homeData.setDishes(dishes);
                dishesArrayList.set(position, homeData);
                dishesAdapter.notifyItemChanged(position);

            } else {
                CommonMethod.showAlertDialog(DishesRestaurentActivity.this, getString(R.string.internet_toast));
            }
        } else {
            callActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //  BMSPrefs.putString(getApplicationContext(), Constants.BACK_ID, "");
        callActivityFinish(new Intent(getApplicationContext(), ArticleActivity.class));
        BMSPrefs.putString(getApplicationContext(), Constants.SALE_ID, "");
    }

    // call activity method
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

    @Override
    public void showProgress() {
        progressDialog = CommonMethod.showProgressDialog(progressDialog, DishesRestaurentActivity.this, getString(R.string.please_wait));
    }

    @Override
    public void hideProgress() {
        CommonMethod.hideProgressDialog(progressDialog);
    }

    // favourite dishes
    @Override
    public void setDataFav(String status, String msg, String key) {
        try {
            if (status.equalsIgnoreCase("1") && key.equalsIgnoreCase("favourite")) {
                CommonMethod.showToastShort(msg, getApplicationContext());

            } else if (status.equalsIgnoreCase("1") && key.equalsIgnoreCase("unFavourite")) {
                CommonMethod.showToastShort(msg, getApplicationContext());
            } else if (key.equalsIgnoreCase("invalid_session")) {
                try {
                    LoginManager.getInstance().logOut();
                    BMSPrefs.putString(getApplicationContext(), Constants._ID, "");
                    BMSPrefs.putString(getApplicationContext(), Constants.Seeker_id, "");
                    CommonMethod.showToastShort(msg, getApplicationContext());
                    callActivityFinish(new Intent(getApplicationContext(), ArticleActivity.class));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                CommonMethod.showToastShort(msg, getApplicationContext());
            }
        } catch (Exception ex) {
        }


    }

    @Override
    public void setDataToRecyclerView(ArrayList<HomeData> dishessArrayList) {
        String listStrings = "";
        try {
            if (sendDataArrayList.size() > 0) {
                for (String s : sendDataArrayList) {
                    listStrings += s + ",";
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        edt_search.setText(listStrings);
        dishesArrayList.clear();
        dishesArrayList = dishessArrayList;
        setAdapter();
    }

    @Override
    public void onResponseFailure(Throwable throwable) {
        Log.d(TAG, throwable.getMessage());
    }
}
