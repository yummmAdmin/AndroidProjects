package com.digitaldestino.get_restaurant_review;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.digitaldestino.R;
import com.digitaldestino.adapter.ArticleAdapter;
import com.digitaldestino.adapter.ShowReviewAdapter;
import com.digitaldestino.articleUI.ArticleActivity;
import com.digitaldestino.edit_profile.EditProfileActivity;
import com.digitaldestino.modelClass.get_feedback.FeedbackData;
import com.digitaldestino.modelClass.get_feedback.FeedbackDataCount;
import com.digitaldestino.utils.BMSPrefs;
import com.digitaldestino.utils.CommonMethod;
import com.digitaldestino.utils.ConnectionDetector;
import com.digitaldestino.utils.Constants;
import com.digitaldestino.utils.CustomRatingBar;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class Review_Fragment extends Fragment implements GetReviewContract.View {
    private View view, view_line;
    private TextView text_food, text_service, text_delivery, text_enviroment, text_total_rating, text_user_review,text_review_status;
    private RecyclerView recycler_review;
    private String session_token, restaurant_id;
    private ShowReviewAdapter showReviewAdapter;
    private CustomRatingBar rating_restaurant;
    private RelativeLayout r2;
    private LinearLayoutManager linearLayoutManager;
    private ProgressDialog progressDialog;
    private ConnectionDetector connectionDetector;
    private ArrayList<FeedbackDataCount> feedbackDataCountArrayList = new ArrayList<>();
    private ArrayList<FeedbackData> feedbackDataArrayList = new ArrayList<>();
    private boolean isInternetPresent = false;
    private static final String TAG = "Review_Fragment";
    private GetReviewPresenter getReviewPresenter;
    private Context context = getActivity();

    public Review_Fragment() {
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
        view = inflater.inflate(R.layout.fragment_review_, container, false);
        findView();
        if (isInternetPresent) {
            requestParam();
        } else {
            CommonMethod.showAlertDialog(getContext(), getString(R.string.internet_toast));
        }
        return view;
    }

    // to initiaize object here...
    private void findView() {
        connectionDetector = new ConnectionDetector(getContext());
        isInternetPresent = connectionDetector.isConnectingToInternet();
        session_token = BMSPrefs.getString(getContext(), Constants.SESSION_TOKEN);
        restaurant_id = BMSPrefs.getString(getContext(), Constants.RESTAURANT_ID);
        r2 = view.findViewById(R.id.r2);
        view_line = view.findViewById(R.id.view);
        rating_restaurant = view.findViewById(R.id.rating_restaurant);
        text_total_rating = view.findViewById(R.id.text_total_rating);
        text_food = view.findViewById(R.id.text_food);
        text_service = view.findViewById(R.id.text_service);
        text_delivery = view.findViewById(R.id.text_delivery);
        text_enviroment = view.findViewById(R.id.text_enviroment);
        text_user_review = view.findViewById(R.id.text_user_review);
        recycler_review = view.findViewById(R.id.recycler_review);
        text_review_status=view.findViewById(R.id.text_review_status);
    }

    // set adapter
    private void setAdapter() {
        showReviewAdapter = new ShowReviewAdapter(getContext(), feedbackDataArrayList);
        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recycler_review.setLayoutManager(linearLayoutManager);
        recycler_review.setAdapter(showReviewAdapter);
    }

    // request param
    private void requestParam() {
        final HashMap<String, String> params = new HashMap<>();
        params.put("restaurant_id", restaurant_id);
        params.put("session_token", session_token);
        Log.d("params", params.toString());
        getReviewPresenter = new GetReviewPresenter(this);
        getReviewPresenter.requestGetReview(params);
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

        //  progressDialog.dismiss();
    }

    @Override
    public void setDataToViews(String status, String msg, String key, ArrayList<FeedbackData> feedbackDataArrayLists, ArrayList<FeedbackDataCount> feedbackDataCountArrayLists) {
        if (status.equalsIgnoreCase("1") && key.equalsIgnoreCase("getFeedback")) {
            CommonMethod.showToastShort(msg, context);
            feedbackDataCountArrayList = feedbackDataCountArrayLists;
            if (feedbackDataCountArrayList.size() > 0) {
                r2.setVisibility(View.VISIBLE);
                text_review_status.setVisibility(View.GONE);
                rating_restaurant.setVisibility(View.VISIBLE);
                view_line.setVisibility(View.VISIBLE);
                text_food.setText(feedbackDataCountArrayList.get(0).getFood_rating());
                text_service.setText(feedbackDataCountArrayList.get(0).getService_rating());
                text_delivery.setText(feedbackDataCountArrayList.get(0).getDelivery_rating());
                text_enviroment.setText(feedbackDataCountArrayList.get(0).getEnv_rating());
                text_total_rating.setText(feedbackDataCountArrayList.get(0).getRatings() + " Ratings");
                text_user_review.setText(feedbackDataCountArrayList.get(0).getReviews() + " Review");
            } else {
                r2.setVisibility(View.GONE);
                rating_restaurant.setVisibility(View.GONE);
                view_line.setVisibility(View.GONE);
                text_review_status.setVisibility(View.VISIBLE);
            }
            feedbackDataArrayList = feedbackDataArrayLists;
            setAdapter();

        } else {
            CommonMethod.showToastShort(msg, context);
        }
    }

    @Override
    public void onResponseFailure(Throwable throwable)
    {
        Log.d(TAG, throwable.getMessage());
    }

    @Override
    public void onPause() {
        super.onPause();
        if ((progressDialog != null) && progressDialog.isShowing())
            progressDialog.dismiss();
           progressDialog = null;
    }
}
