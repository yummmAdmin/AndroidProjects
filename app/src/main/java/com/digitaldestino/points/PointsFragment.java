package com.digitaldestino.points;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.digitaldestino.R;
import com.digitaldestino.adapter.DishesAdapter;
import com.digitaldestino.adapter.PointAdapter;
import com.digitaldestino.articleUI.ArticleActivity;
import com.digitaldestino.reedem_point.ReedemPointActivity;
import com.digitaldestino.reedem_point.ReedemPresenter;
import com.digitaldestino.restaurant_menu.GetPopularPresenter;
import com.digitaldestino.utils.BMSPrefs;
import com.digitaldestino.utils.CommonMethod;
import com.digitaldestino.utils.ConnectionDetector;
import com.digitaldestino.utils.Constants;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class PointsFragment extends Fragment implements View.OnClickListener, GetLoyaltyContract.View {
    private View view;
    private Button btn_reedem_point;
    private RecyclerView recycler_point;
    private PointAdapter pointAdapter;
    private int loyalty;
    private String session_token, restaurant_id, seekerId;
    private GetLoyaltyPresenter getLoyaltyPresenter;
    private ProgressDialog progressDialog;
    private ConnectionDetector connectionDetector;
    private boolean isInternetPresent = false;
    private int red_count;

    public PointsFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_points, container, false);
        findView();
        if (isInternetPresent) {
            requestParam();
        } else {
            CommonMethod.showAlertDialog(getContext(), getString(R.string.internet_toast));
        }
        return view;
    }

    // initialize objext
    private void findView() {
        connectionDetector = new ConnectionDetector(getContext());
        isInternetPresent = connectionDetector.isConnectingToInternet();
        session_token = BMSPrefs.getString(getContext(), Constants.SESSION_TOKEN);
        restaurant_id = BMSPrefs.getString(getContext(), Constants.RESTAURANT_ID);
        seekerId = BMSPrefs.getString(getContext(), Constants.Seeker_id);
        recycler_point = view.findViewById(R.id.recycler_point);
        btn_reedem_point = view.findViewById(R.id.btn_reedem_point);
        String loyalty_local = BMSPrefs.getString(getContext(), Constants.LOYALTY);
        if (!TextUtils.isEmpty(loyalty_local)) {
            loyalty = Integer.valueOf(BMSPrefs.getString(getContext(), Constants.LOYALTY));
        } else {
            loyalty = 0;
        }
        btn_reedem_point.setOnClickListener(this);
    }

    // api call
    private void requestParam() {
        HashMap<String, String> params = new HashMap<>();
        params.put("session_token", session_token);
        params.put("restaurant_id", restaurant_id);
        params.put("seekerId", seekerId);
        Log.d("param", params.toString());
        getLoyaltyPresenter = new GetLoyaltyPresenter(this);
        getLoyaltyPresenter.requestGetLoyalty(params);
    }

    //    // adapter
    private void setAdapter() {
        pointAdapter = new PointAdapter(getContext(), loyalty, red_count);
        recycler_point.setLayoutManager(new GridLayoutManager(getContext(), 5));
        recycler_point.setAdapter(pointAdapter);
    }

    // call activity method
    private void callActivity(Intent intent) {
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
//        getContext().overridePendingTransition(R.anim.anim_slide_in_left,
//                R.anim.anim_slide_out_left);
    }

    // call activity
    private void callActivityFinish(Intent intent) {
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
//        overridePendingTransition(R.anim.anim_slide_in_left,
//                R.anim.anim_slide_out_left);
        //finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_reedem_point: {
                callActivity(new Intent(getContext(), ReedemPointActivity.class));
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
        CommonMethod.hideProgressDialog(progressDialog);
    }

    @Override
    public void setDataToView(String status, String msg, String key, String loyalityData) {
        if (status.equalsIgnoreCase("1") && key.equalsIgnoreCase("loyalityPoints")) {
            red_count = Integer.parseInt(loyalityData);
            setAdapter();
        } else if (status.equalsIgnoreCase("0") && key.equalsIgnoreCase("invalid_session")) {
            BMSPrefs.putString(getContext(), Constants._ID, "");
            BMSPrefs.putString(getContext(), Constants.Seeker_id, "");
            CommonMethod.showToastShort(msg, getContext());
            callActivityFinish(new Intent(getContext(), ArticleActivity.class));
        } else {
            CommonMethod.showToastShort(msg, getContext());
        }
    }

    @Override
    public void onResponseFailure(Throwable throwable) {

    }
}
