package com.digitaldestino.restaurant_menu;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.digitaldestino.R;
import com.digitaldestino.adapter.CategoryBaseDishesAdapter;
import com.digitaldestino.adapter.DishesNameAdapter;
import com.digitaldestino.dishes_detail.DishesDetailActivity;
import com.digitaldestino.get_restaurant_review.GetReviewPresenter;
import com.digitaldestino.listener.onOrderSelectListener;
import com.digitaldestino.modelClass.get_restaurant_menu.ResDetial;
import com.digitaldestino.utils.BMSPrefs;
import com.digitaldestino.utils.CommonMethod;
import com.digitaldestino.utils.ConnectionDetector;
import com.digitaldestino.utils.Constants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class Menu_Fragment extends Fragment implements View.OnClickListener, MenuContract.View, GetSpecialContract.View, GetNewContract.View,
        GetPopularContract.View, onOrderSelectListener {
    private View view;
    private TextView text_default, text_popular, text_special, text_new, text_no_dishes;
    private RecyclerView recycler_dishes_name;
    private String restaurant_id, statuss = "";
    private CategoryBaseDishesAdapter dishesNameAdapter;
    private LinearLayoutManager linearLayoutManager;
    private ProgressDialog progressDialog;
    private ConnectionDetector connectionDetector;
    private boolean isInternetPresent = false;
    private static final String TAG = "Menu_Fragment";
    private ArrayList<ResDetial> resDetialArrayList = new ArrayList<>();
    private MenuPresenter menuPresenter;
    private GetSpecialPresenter getSpecialPresenter;
    private GetNewPresenter getNewPresenter;
    private GetPopularPresenter getPopularPresenter;
    private Context context = getActivity();


    public Menu_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_menu_, container, false);
        findView();
        if (isInternetPresent) {
            requestParamDefault();
        } else {
            CommonMethod.showAlertDialog(getContext(), getString(R.string.internet_toast));
        }
        return view;
    }

    // initialize object...
    private void findView() {
        restaurant_id = BMSPrefs.getString(getContext(), Constants.RESTAURANT_ID);
        connectionDetector = new ConnectionDetector(getContext());
        isInternetPresent = connectionDetector.isConnectingToInternet();
        text_no_dishes = view.findViewById(R.id.text_no_dishes);
        text_default = view.findViewById(R.id.text_default);
        text_popular = view.findViewById(R.id.text_popular);
        text_special = view.findViewById(R.id.text_special);
        text_new = view.findViewById(R.id.text_new);
        recycler_dishes_name = view.findViewById(R.id.recycler_dishes_name);
        text_default.setOnClickListener(this);
        text_popular.setOnClickListener(this);
        text_special.setOnClickListener(this);
        text_new.setOnClickListener(this);
    }

    // adapter
    private void setAdapter() {
        dishesNameAdapter = new CategoryBaseDishesAdapter(getContext(), resDetialArrayList, this, statuss);
        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recycler_dishes_name.setLayoutManager(linearLayoutManager);
        recycler_dishes_name.setAdapter(dishesNameAdapter);
    }

    // request param
    private void requestParamDefault() {
        final HashMap<String, String> params = new HashMap<>();
        params.put("restaurant_id", restaurant_id);
        Log.d("params", params.toString());
        menuPresenter = new MenuPresenter(this);
        menuPresenter.requestGetMenu(params);
    }

    // request param
    private void requestParamSpecial() {
        final HashMap<String, String> params = new HashMap<>();
        params.put("restaurant_id", restaurant_id);
        Log.d("params", params.toString());
        getSpecialPresenter = new GetSpecialPresenter(this);
        getSpecialPresenter.requestGetSpecialMenu(params);
    }

    // request param
    private void requestParamNew() {
        final HashMap<String, String> params = new HashMap<>();
        params.put("restaurant_id", restaurant_id);
        Log.d("params", params.toString());
        getNewPresenter = new GetNewPresenter(this);
        getNewPresenter.requestGetNewMenu(params);
    }

    // request param
    private void requestParamPopular() {
        final HashMap<String, String> params = new HashMap<>();
        params.put("restaurant_id", restaurant_id);
        Log.d("params", params.toString());
        getPopularPresenter = new GetPopularPresenter(this);
        getPopularPresenter.requestGetPopularMenu(params);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_default: {
                text_default.setBackgroundResource(R.color.app_color);
                text_popular.setBackgroundResource(R.color.res_info_color);
                text_special.setBackgroundResource(R.color.res_info_color);
                text_new.setBackgroundResource(R.color.res_info_color);
                if (isInternetPresent) {
                    requestParamDefault();
                } else {
                    CommonMethod.showAlertDialog(getContext(), getString(R.string.internet_toast));
                }
                break;
            }
            case R.id.text_popular: {
                text_popular.setBackgroundResource(R.color.app_color);
                text_default.setBackgroundResource(R.color.res_info_color);
                text_special.setBackgroundResource(R.color.res_info_color);
                text_new.setBackgroundResource(R.color.res_info_color);
                if (isInternetPresent) {
                    requestParamPopular();
                } else {
                    CommonMethod.showAlertDialog(getContext(), getString(R.string.internet_toast));
                }
                break;
            }
            case R.id.text_special: {
                text_popular.setBackgroundResource(R.color.res_info_color);
                text_default.setBackgroundResource(R.color.res_info_color);
                text_special.setBackgroundResource(R.color.app_color);
                text_new.setBackgroundResource(R.color.res_info_color);
                if (isInternetPresent) {
                    requestParamSpecial();
                } else {
                    CommonMethod.showAlertDialog(getContext(), getString(R.string.internet_toast));
                }
                break;
            }
            case R.id.text_new: {
                text_popular.setBackgroundResource(R.color.res_info_color);
                text_default.setBackgroundResource(R.color.res_info_color);
                text_special.setBackgroundResource(R.color.res_info_color);
                text_new.setBackgroundResource(R.color.app_color);
                if (isInternetPresent) {
                    requestParamNew();
                } else {
                    CommonMethod.showAlertDialog(getContext(), getString(R.string.internet_toast));
                }
                break;
            }
        }
    }


    @Override
    public void showProgress() {
        progressDialog = CommonMethod.showProgressDialog(progressDialog, getContext(), getString(R.string.please_wait));
    }

    @Override
    public void hideProgress() {
        if ((progressDialog != null) && progressDialog.isShowing())
            progressDialog.dismiss();
        progressDialog = null;
    }

    @Override
    public void setDataToPopular(String status, String msg, String key, ArrayList<ResDetial> detialArrayList) {
        resDetialArrayList.clear();
        if (status.equalsIgnoreCase("1") && key.equalsIgnoreCase("getPopularDish")) {
        //    CommonMethod.showToastShort(msg, context);
            resDetialArrayList = detialArrayList;
            // sort array
            Collections.sort(resDetialArrayList, COMPARE_BY_CAT_NAME);
            statuss = "popular";
            if (resDetialArrayList.size() > 0) {
                text_no_dishes.setVisibility(View.GONE);
                setAdapter();
            } else {
                text_no_dishes.setVisibility(View.VISIBLE);
            }

        } else {
            CommonMethod.showToastShort(msg, context);
        }
    }


    // new menu
    @Override
    public void setDataToNew(String status, String msg, String key, ArrayList<ResDetial> detialArrayList) {
        resDetialArrayList.clear();
        if (status.equalsIgnoreCase("1") && key.equalsIgnoreCase("getNewDish")) {
        //    CommonMethod.showToastShort(msg, context);
            resDetialArrayList = detialArrayList;
            // sort array
            Collections.sort(resDetialArrayList, COMPARE_BY_CAT_NAME);
            statuss = "new";
            if (resDetialArrayList.size() > 0) {
                text_no_dishes.setVisibility(View.GONE);
                setAdapter();
            } else {
                text_no_dishes.setVisibility(View.VISIBLE);
            }

        } else {
            CommonMethod.showToastShort(msg, context);
        }
    }


    //special menu
    @Override
    public void setDataToSpecial(String status, String msg, String key, ArrayList<ResDetial> detialArrayList) {
        resDetialArrayList.clear();
        if (status.equalsIgnoreCase("1") && key.equalsIgnoreCase("getSpecialDish")) {
        //    CommonMethod.showToastShort(msg, context);
            resDetialArrayList = detialArrayList;
            // sort array
            Collections.sort(resDetialArrayList, COMPARE_BY_CAT_NAME);
            statuss = "special";

            if (resDetialArrayList.size() > 0) {
                text_no_dishes.setVisibility(View.GONE);
                setAdapter();
            } else {
                text_no_dishes.setVisibility(View.VISIBLE);
            }
        } else {
            CommonMethod.showToastShort(msg, context);
        }
    }

    // default
    @Override
    public void setDataToViews(String status, String msg, String key, ArrayList<ResDetial> detialArrayList) {
        resDetialArrayList.clear();
        if (status.equalsIgnoreCase("1") && key.equalsIgnoreCase("getMenuDetails")) {
        //    CommonMethod.showToastShort(msg, context);
            resDetialArrayList = detialArrayList;
            // sort array
            Collections.sort(resDetialArrayList, COMPARE_BY_CAT_NAME);
            statuss = "default";
            if (resDetialArrayList.size() > 0) {
                text_no_dishes.setVisibility(View.GONE);
                setAdapter();
            } else {
                text_no_dishes.setVisibility(View.VISIBLE);
            }
        } else {
            CommonMethod.showToastShort(msg, context);
        }
    }

    @Override
    public void onResponseFailure(Throwable throwable) {
        Log.d(TAG, throwable.getMessage());
    }

    // sort array
    public static Comparator<ResDetial> COMPARE_BY_CAT_NAME = new Comparator<ResDetial>() {
        @Override
        public int compare(ResDetial o1, ResDetial o2) {
            return o1.getFood_section_name().compareTo(o2.getFood_section_name());
        }
    };


    // click listener
    @Override
    public void onOrderSelectItemClick(int position) {
        BMSPrefs.putString(getContext(), Constants.FOOD_ITEM_ID, resDetialArrayList.get(position).getFood_item_id());
        callActivity(new Intent(getContext(), DishesDetailActivity.class));
    }

    // call activity method
    private void callActivity(Intent intent) {
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left);
    }

    @Override
    public void onPause() {
        super.onPause();
        if ((progressDialog != null) && progressDialog.isShowing())
            progressDialog.dismiss();
        progressDialog = null;
    }
}
