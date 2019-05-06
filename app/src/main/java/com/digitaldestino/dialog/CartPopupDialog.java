package com.digitaldestino.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.digitaldestino.R;
import com.digitaldestino.adapter.ChoiceAdapter;
import com.digitaldestino.articleUI.ArticleActivity;
import com.digitaldestino.cart.DeleteCartPresenter;
import com.digitaldestino.dishes_detail.DishesDetailActivity;
import com.digitaldestino.dishes_detail.DishesDetailPresenter;
import com.digitaldestino.dishes_restaurentUI.DishesRestaurentActivity;
import com.digitaldestino.edit_profile.EditProfileActivity;
import com.digitaldestino.listener.onAddCartListener;
import com.digitaldestino.modelClass.dishes_detail.DishDetial;
import com.digitaldestino.modelClass.dishes_detail.Price;
import com.digitaldestino.restaurant_info.RestaurentInfoActivity;
import com.digitaldestino.utils.BMSPrefs;
import com.digitaldestino.utils.CommonMethod;
import com.digitaldestino.utils.ConnectionDetector;
import com.digitaldestino.utils.Constants;
import com.facebook.login.LoginManager;

import java.util.ArrayList;
import java.util.HashMap;

public class CartPopupDialog extends DialogFragment implements View.OnClickListener, onAddCartListener, AddCartContract.View, DeleteAllItemContract.View {
    // TODO: Rename parameter arguments, choose names that match
    private View view;
    private ImageView img_add, img_minus;
    private TextView text_qty, text_price, text_choice, text_name;
    private EditText edt_instruction;
    private Button btn_confirm;
    private String dish_price, session_token, _id, food_item_id, food_item_name, qty, final_price, restaurant_id, choice = "", instruction = "",food_image;
    private RecyclerView recycler_choice;
    private ChoiceAdapter choiceAdapter;
    private LinearLayoutManager linearLayoutManager;
    private double price = 0.0;
    private int count = 1;
    private ArrayList<Price> priceArrayList = new ArrayList<>();
    private ConnectionDetector connectionDetector;
    private boolean isInternetPresent = false;
    private ProgressDialog progressDialog;
    private AddCartPresenter addCartPresenter;
    private final String TAG = "CartPopupDialog";
    private DeleteAllItemPresenter deleteAllItemPresenter;
    HashMap<String, String> params = new HashMap<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.dishes_popup, container, false);
        findView();
        setAdapter();
        return view;
    }

    // initialize object here...
    private void findView() {
        deleteAllItemPresenter = new DeleteAllItemPresenter(this);
        connectionDetector = new ConnectionDetector(getContext());
        isInternetPresent = connectionDetector.isConnectingToInternet();
        _id = BMSPrefs.getString(getContext(), Constants._ID);
        session_token = BMSPrefs.getString(getContext(), Constants.SESSION_TOKEN);
        food_item_id = BMSPrefs.getString(getContext(), Constants.FOOD_ITEM_ID);
        food_image = BMSPrefs.getString(getContext(),"food_image");
        recycler_choice = view.findViewById(R.id.recycler_choice);
        dish_price = getArguments().getString("dish_price");
        priceArrayList = getArguments().getParcelableArrayList("priceArrayList");
        food_item_name = getArguments().getString("food_item_name");
        restaurant_id = getArguments().getString("restaurant_id");
        img_add = view.findViewById(R.id.img_add);
        text_choice = view.findViewById(R.id.choice);
        img_minus = view.findViewById(R.id.img_minus);
        text_qty = view.findViewById(R.id.text_qty);
        text_price = view.findViewById(R.id.text_price);
        text_name = view.findViewById(R.id.text_name);
        btn_confirm = view.findViewById(R.id.btn_confirm);
        edt_instruction = view.findViewById(R.id.edt_instruction);
        if (priceArrayList.isEmpty()) {
            text_price.setText(dish_price);
            text_choice.setVisibility(View.GONE);
            choice = "";
        } else {
            text_price.setText(priceArrayList.get(0).getValue());
            dish_price = priceArrayList.get(0).getValue();
            text_choice.setVisibility(View.VISIBLE);
            choice = priceArrayList.get(0).getName();
        }
        price = Double.parseDouble((String) text_price.getText());
        text_name.setText(food_item_name);
        img_add.setOnClickListener(this);
        img_minus.setOnClickListener(this);
        btn_confirm.setOnClickListener(this);

    }

    // adapter
    private void setAdapter() {
        choiceAdapter = new ChoiceAdapter(getContext(), this, priceArrayList);
        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recycler_choice.setLayoutManager(linearLayoutManager);
        recycler_choice.setAdapter(choiceAdapter);

    }


    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow()
                .getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.setCancelable(true);
        return dialog;
    }

    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);

            dialog.getWindow().setBackgroundDrawable(
                    new ColorDrawable(Color.TRANSPARENT));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_add: {
                increaseItemSize();
                break;
            }

            case R.id.img_minus: {
                decreaseItemSize();
                break;
            }

            case R.id.btn_confirm: {
                if (isInternetPresent) {
                    requestParam();
                    addCartPresenter = new AddCartPresenter(this);
                    addCartPresenter.requestAddCart(params);
                } else {
                    CommonMethod.showAlertDialog(getContext(), getString(R.string.internet_toast));
                }
                break;
            }
        }
    }

    // listener method
    @Override
    public void onChoiceItemClick(int position) {
        count = 1;
        text_qty.setText("" + count);
        text_price.setText(priceArrayList.get(position).getValue());
        price = Double.parseDouble((String) text_price.getText());
        dish_price = priceArrayList.get(position).getValue();
        choice = priceArrayList.get(position).getName();
    }

    @Override
    public void onChoiceItemDecreaseClick(int position) {

    }

    @Override
    public void onDeleteItemClick(int position) {

    }

    // increase item value
    private void increaseItemSize() {
        count++;
        text_qty.setText("" + count);
        text_price.setText("" + (price * count));
    }

    // decrease item value
    private void decreaseItemSize() {
        if (count > 1) {
            count--;
            text_qty.setText("" + count);
            text_price.setText("" + (price * count));
        }
    }

    // request param
    private void requestParam() {
        qty = text_qty.getText().toString().trim();
        final_price = text_price.getText().toString().trim();
        instruction = edt_instruction.getText().toString().trim();
        params.put("session_token", session_token);
        params.put("seeker_id", _id);
        params.put("food_item_id", food_item_id);
        params.put("food_item_name", food_item_name);
        params.put("qty", qty);
        params.put("price", dish_price);
        params.put("discount", "0");
        params.put("total_price", final_price);
        params.put("restaurant_id", restaurant_id);
        params.put("choice", choice);
        params.put("instruction", instruction);
        params.put("food_image", food_image);
        Log.d("params", params.toString());
    }

    @Override
    public void showProgress() {
        progressDialog = CommonMethod.showProgressDialog(progressDialog, getContext(), getString(R.string.please_wait));
    }

    @Override
    public void hideProgress() {
        CommonMethod.hideProgressDialog(progressDialog);
    }

    // diffferent restaurant item added
    @Override
    public void deleteAllCart(String status, String msg, String key) {
        if (status.equalsIgnoreCase("1") && key.equalsIgnoreCase("addtocart")) {
            CommonMethod.showToastShort(msg, getContext());
            callActivityFinish(new Intent(getContext(), DishesRestaurentActivity.class));
        } else if (status.equalsIgnoreCase("0") && key.equalsIgnoreCase("invalid_session")) {
            BMSPrefs.putString(getContext(), Constants._ID, "");
            CommonMethod.showToastShort(msg, getContext());
            callActivityFinish(new Intent(getContext(), ArticleActivity.class));
        } else {
            CommonMethod.showToastShort(msg, getContext());
        }
    }

    // item add in cart
    @Override
    public void setDataToCart(String status, String msg, String key) {
        if (status.equalsIgnoreCase("1") && key.equalsIgnoreCase("addtocart")) {
            CommonMethod.showToastShort(msg, getContext());
            callActivityFinish(new Intent(getContext(), ArticleActivity.class));
        } else if (status.equalsIgnoreCase("0") && key.equalsIgnoreCase("invalid_session")) {
            BMSPrefs.putString(getContext(), Constants._ID, "");
            LoginManager.getInstance().logOut();
            CommonMethod.showToastShort(msg, getContext());
            callActivityFinish(new Intent(getContext(), ArticleActivity.class));
        } else if (status.equalsIgnoreCase("0") && key.equalsIgnoreCase("differentitem")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle(getString(R.string.app_name));
            builder.setMessage(msg);
            builder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    try {
                        requestParam();
                        deleteAllItemPresenter.requestDeleteCart(params);
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
            CommonMethod.showToastShort(msg, getContext());
        }

    }

    @Override
    public void onResponseFailure(Throwable throwable) {
        Log.d(TAG, throwable.getMessage());
    }

    // call activity
    private void callActivityFinish(Intent intent) {
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left);
        getActivity().finish();
    }
}
