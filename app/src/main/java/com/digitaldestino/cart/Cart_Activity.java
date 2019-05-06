package com.digitaldestino.cart;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.digitaldestino.R;
import com.digitaldestino.adapter.OrderListAdapter;
import com.digitaldestino.articleUI.ArticleActivity;
import com.digitaldestino.listener.onAddCartListener;
import com.digitaldestino.modelClass.apply_coupan.Promocode;
import com.digitaldestino.modelClass.get_cart.CartData;
import com.digitaldestino.modelClass.update_cart.AdditionalCharges;
import com.digitaldestino.modelClass.update_cart.UpdatecartData;
import com.digitaldestino.notification.NotificationActivity;
import com.digitaldestino.payment_address.PaymentAddress;
import com.digitaldestino.scan_qr.ScanQrActivity;
import com.digitaldestino.scan_qr.ScanQrContract;
import com.digitaldestino.scan_qr.ScanQrPresenter;
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

public class Cart_Activity extends AppCompatActivity implements View.OnClickListener, GetCartContract.View, DeleteCartContract.View, CoupanContract.View,
        UpdateCartContract.View, onAddCartListener, ScanQrContract.View {
    private ImageView img_back, img_bookmark, img_notification;
    private String _id, res_id = "", session_token, coupan, seekerId, discount, type = "", coupanSubtotal, table_id = "", restaurant_id, order_timezone;
    private TextView text_subtotal, text_totalPrice, text_tax, text_go, text_discount;
    private Button btn_place_order;
    private EditText edt_coupon;
    private RecyclerView recycler_cartList;
    private OrderListAdapter orderListAdapter;
    private LinearLayoutManager linearLayoutManager;
    private ProgressDialog progressDialog;
    private final String TAG = "Cart_Activity";
    private ArrayList<CartData> cartDataArrayList = new ArrayList<>();
    private ConnectionDetector connectionDetector;
    private boolean isInternetPresent = false;
    private GetCartPresenter getCartPresenter;
    private DeleteCartPresenter deleteCartPresenter;
    private UpdateCartPresenter updateCartPresenter;
    private CoupanPresenter coupanPresenter;
    private ScanQrPresenter scanQrPresenter;
    private int item_position;
    private LinearLayout linear_item_detail;
    private RelativeLayout relative_empty;
    private Integer qty;
    private double final_price, minimum_amount;
    private double subtotal, tax, promo_value;
    private Date date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_);
        findView();
        if (isInternetPresent) {
            getCart();
        } else {
            CommonMethod.showAlertDialog(Cart_Activity.this, getString(R.string.internet_toast));
        }
    }

    // to initialize object here....
    private void findView() {
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String current_time = format.format(Calendar.getInstance().getTime());
        try {
            date = format.parse(current_time);
            //    CommonMethod.showToastShort("" + date.getTime(), getApplicationContext());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        connectionDetector = new ConnectionDetector(getApplicationContext());
        isInternetPresent = connectionDetector.isConnectingToInternet();
        order_timezone = TimeZone.getDefault().getID();
        _id = BMSPrefs.getString(Cart_Activity.this, Constants._ID);
        table_id = BMSPrefs.getString(Cart_Activity.this, Constants.TABLE_ID);
        session_token = BMSPrefs.getString(Cart_Activity.this, Constants.SESSION_TOKEN);
        seekerId = BMSPrefs.getString(Cart_Activity.this, Constants.Seeker_id);
        restaurant_id = BMSPrefs.getString(Cart_Activity.this, Constants.RESTAURANT_ID);
        recycler_cartList = findViewById(R.id.recycler_cartList);
        img_back = findViewById(R.id.img_back);
        linear_item_detail = findViewById(R.id.linear_item_detail);
        relative_empty = findViewById(R.id.relative_empty);
        btn_place_order = findViewById(R.id.btn_place_order);
        text_subtotal = findViewById(R.id.text_subtotal);
        text_totalPrice = findViewById(R.id.text_totalPrice);
        text_tax = findViewById(R.id.text_tax);
        text_discount = findViewById(R.id.text_discount);
        img_bookmark = findViewById(R.id.img_bookmark);
        text_go = findViewById(R.id.text_go);
        edt_coupon = findViewById(R.id.edt_coupon);
        img_notification = findViewById(R.id.img_notification);
        btn_place_order.setOnClickListener(this);
        img_back.setOnClickListener(this);
        img_bookmark.setOnClickListener(this);
        text_go.setOnClickListener(this);
        img_notification.setOnClickListener(this);
    }

    // get cart item...
    private void getCart() {
        HashMap<String, String> params = new HashMap<>();
        params.put("session_token", session_token);
        params.put("seeker_id", _id);

        Log.d("params", params.toString());
        getCartPresenter = new GetCartPresenter(this);
        getCartPresenter.requestgetCartList(params);
    }

    // Apply Coupan...
    private void applyCoupan() {
        HashMap<String, String> params = new HashMap<>();
        params.put("session_token", session_token);
        params.put("seekerId", seekerId);
        params.put("promocode", coupan);
        params.put("restaurant_id", res_id);
        params.put("total_price", coupanSubtotal);
        Log.d("coupanParams", params.toString());
        coupanPresenter = new CoupanPresenter(this);
        coupanPresenter.requestApplyCoupan(params);
    }

    // request payment  param
    private void requestParam() {
        HashMap<String, String> params = new HashMap<>();
        params.put("session_token", session_token);
        params.put("seeker_id", _id);
        params.put("seekerId", seekerId);
        params.put("card_id", "");
        params.put("stripe_customer_id", "");
        params.put("order_timezone", order_timezone);
        params.put("order_date_time", "" + date.getTime());
        params.put("order_address", "N/A");
        params.put("order_type", "dinein");
        params.put("table_id", table_id);
        params.put("discount", discount);
        params.put("drop_lat", "0");
        params.put("drop_lng", "0");
        Log.d("diningParam", params.toString());
        scanQrPresenter = new ScanQrPresenter(this);
        scanQrPresenter.requestForDininig(params);
    }

    // set adapter
    private void setCartListAdapter() {
        orderListAdapter = new OrderListAdapter(getApplicationContext(), cartDataArrayList, this);
        linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recycler_cartList.setLayoutManager(linearLayoutManager);
        recycler_cartList.setAdapter(orderListAdapter);
    }

    private String validateForm() {
        coupan = edt_coupon.getText().toString().trim();
        if (coupan.isEmpty() || coupan.equals("")) {
            edt_coupon.setFocusable(true);
            edt_coupon.requestFocus();
            return getString(R.string.enter_coupan);
        }
        return "success";
    }

    // manage listener
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

            case R.id.btn_place_order: {
                discount = text_discount.getText().toString().trim();
                BMSPrefs.putString(getApplicationContext(), Constants.DISCOUNT, discount);
                if (cartDataArrayList.size() > 0) {
                    openDialouge();

                } else {
                    CommonMethod.showToastShort(getString(R.string.add_item_cart), getApplicationContext());
                }
                break;
            }
            case R.id.text_go: {
                CommonMethod.hideKeyBoard(Cart_Activity.this);

                String result = validateForm();
                if (result.equalsIgnoreCase("success")) {
                    if (isInternetPresent) {
                        applyCoupan();
                    } else {
                        CommonMethod.showAlertDialog(Cart_Activity.this, getString(R.string.internet_toast));
                    }
                } else {
                    CommonMethod.showAlertDialog(Cart_Activity.this, result);
                }
                break;
            }
        }
    }

    //dialog open on button click
    private void openDialouge() {
        final Dialog mBottomSheetDialog = new Dialog(Cart_Activity.this, R.style.MaterialDialogSheet);
        mBottomSheetDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mBottomSheetDialog.setContentView(R.layout.bottom_sheet);
        mBottomSheetDialog.setCancelable(false);
        mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mBottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);

        TextView text_delivery = mBottomSheetDialog.findViewById(R.id.text_delivery);
        TextView text_dinning = mBottomSheetDialog.findViewById(R.id.text_dinning);
        TextView text_cancel = mBottomSheetDialog.findViewById(R.id.text_cancel);

        text_delivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callActivity(new Intent(getApplicationContext(), PaymentAddress.class));
            }
        });

        text_dinning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(table_id) && res_id.equalsIgnoreCase(restaurant_id)) {
                    requestParam();
                } else {
                    callActivity(new Intent(getApplicationContext(), ScanQrActivity.class));
                }

            }
        });

        text_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.dismiss();
            }
        });

        mBottomSheetDialog.show();

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

    // handle backpress here...
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        callActivityFinish(new Intent(getApplicationContext(), ArticleActivity.class));

    }

    @Override
    public void showProgress() {
        progressDialog = CommonMethod.showProgressDialog(progressDialog, Cart_Activity.this, getString(R.string.please_wait));
    }

    @Override
    public void hideProgress() {
        CommonMethod.hideProgressDialog(progressDialog);
    }

    @Override
    public void orderForDininig(String status, String msg, String key) {
        if (status.equalsIgnoreCase("1") && key.equalsIgnoreCase("booknow")) {
            CommonMethod.showToastShort(msg, getApplicationContext());
            BMSPrefs.putString(getApplicationContext(), Constants.TABLE_ID, "");
            callActivityFinish(new Intent(getApplicationContext(), ArticleActivity.class));
        } else if (status.equalsIgnoreCase("0") && key.equalsIgnoreCase("invalid_session")) {
            BMSPrefs.putString(getApplicationContext(), Constants._ID, "");
            BMSPrefs.putString(getApplicationContext(), Constants.Seeker_id, "");
            //    BMSPrefs.putString(getApplicationContext(), Constants.TABLE_ID, "");
            LoginManager.getInstance().logOut();
            CommonMethod.showToastShort(msg, getApplicationContext());
            callActivityFinish(new Intent(getApplicationContext(), ArticleActivity.class));
        } else {
            CommonMethod.showToastShort(msg, getApplicationContext());
        }
    }

    // coupan
    @Override
    public void setDataToCoupan(String status, String msg, String key, Promocode promocode) {
        if (status.equalsIgnoreCase("1") && key.equalsIgnoreCase("promocode")) {
            CommonMethod.showToastShort(msg, getApplicationContext());
            type = promocode.getType();
            promo_value = Double.valueOf(promocode.getValue());
            minimum_amount = Double.valueOf(promocode.getMinimum_amount());
            edt_coupon.setText("");
            if (type.equalsIgnoreCase("fixed")) {
                double final_amount = (subtotal - promo_value) + tax;
                text_discount.setText(promocode.getValue());
                text_totalPrice.setText("$" + (String.valueOf(final_amount)));
            }
            if (type.equalsIgnoreCase("percentage")) {
                double tax_amount = ((subtotal * promo_value) / 100);
                text_discount.setText("" + tax_amount);
                double final_amount = subtotal - tax_amount + tax;
                text_totalPrice.setText("$" + (String.valueOf(final_amount)));
            }
        } else if (status.equalsIgnoreCase("0") && key.equalsIgnoreCase("invalid_session")) {
            invalidSession(msg);
            edt_coupon.setText("");
        } else if (status.equalsIgnoreCase("0") && key.equalsIgnoreCase("promocode")) {
            double amount = subtotal + tax;
            text_discount.setText("0");
            text_totalPrice.setText("$" + amount);
            edt_coupon.setText("");
            CommonMethod.showToastShort(msg, getApplicationContext());
        } else {
            edt_coupon.setText("");
            CommonMethod.showToastShort(msg, getApplicationContext());
        }
    }

    // update cart item
    @Override
    public void setUpdateCartItem(String status, String msg, String key, UpdatecartData updatecartData, AdditionalCharges additionalCharges) {
        if (status.equalsIgnoreCase("1") && key.equalsIgnoreCase("updatecart")) {
            subtotal = Double.valueOf(additionalCharges.getCart_sum());
            tax = Double.valueOf(additionalCharges.getService_tax());
            text_subtotal.setText("$" + additionalCharges.getCart_sum());
            text_tax.setText("$" + additionalCharges.getService_tax());
            Double total = Double.parseDouble(additionalCharges.getCart_sum()) + Double.parseDouble(additionalCharges.getService_tax());
            text_totalPrice.setText("$" + (String.valueOf(total)));
            cartDataArrayList.remove(item_position);
            CartData cartData = new CartData();
            cartData.set_id(updatecartData.get_id());
            cartData.setFood_item_id(updatecartData.getFood_item_id());
            cartData.setSeeker_id(updatecartData.getSeeker_id());
            cartData.setFood_item_id(updatecartData.getFood_item_id());
            cartData.setFood_item_name(updatecartData.getFood_item_name());
            cartData.setQty(updatecartData.getQty());
            cartData.setPrice(updatecartData.getPrice());
            cartData.setDiscount(updatecartData.getDiscount());
            cartData.setTotal_price(updatecartData.getTotal_price());
            cartData.setChoice(updatecartData.getChoice());
            cartData.setInstruction(updatecartData.getInstruction());
            cartData.setRestaurant_id(updatecartData.getRestaurant_id());
            cartData.setCart_date_time(updatecartData.getCart_date_time());
            cartData.setFood_image(updatecartData.getFood_image());
            cartDataArrayList.add(item_position, cartData);
            coupanSubtotal = additionalCharges.getCart_sum();
            recycler_cartList.getAdapter().notifyItemChanged(item_position);
            discount = text_discount.getText().toString().trim();

//            if (type.equalsIgnoreCase("fixed")) {
//                if (!discount.equalsIgnoreCase("0")) {
//                    double amount = (subtotal - Double.valueOf(discount)) + tax;
//                    text_totalPrice.setText("$" + (String.valueOf(amount)));
//                } else {
//                    text_totalPrice.setText("$" + (String.valueOf(total)));
//                }
//            }
//            if (type.equalsIgnoreCase("percentage")) {
//                double tax_amount = ((subtotal * promo_value) / 100);
//                text_discount.setText(promo_value + "%");
//                double final_amount = subtotal - tax_amount + tax;
//                text_totalPrice.setText("$" + (String.valueOf(final_amount)));
//            }
            text_discount.setText("0");
        } else if (status.equalsIgnoreCase("0") && key.equalsIgnoreCase("invalid_session")) {
            invalidSession(msg);
        } else {
            CommonMethod.showToastShort(msg, getApplicationContext());
        }

    }


    // delete cart
    @Override
    public void setDeleteDataToView(String status, String msg, String key, String cart_sum, String service_tax) {
        if (status.equalsIgnoreCase("1") && key.equalsIgnoreCase("deletecart")) {
            CommonMethod.showToastShort(msg, getApplicationContext());
            if (cartDataArrayList.size() > 0) {
                linear_item_detail.setVisibility(View.VISIBLE);
                relative_empty.setVisibility(View.GONE);

                text_subtotal.setText("$" + cart_sum);
                text_tax.setText("$" + service_tax);
                Double total = Double.parseDouble(cart_sum) + Double.parseDouble(service_tax);
                text_totalPrice.setText("$" + (String.valueOf(total)));

                subtotal = Double.valueOf(cart_sum);
                tax = Double.valueOf(service_tax);
                coupanSubtotal = cart_sum;
                discount = text_discount.getText().toString().trim();
//                if (type.equalsIgnoreCase("fixed")) {
//                    double amount = (subtotal - Double.valueOf(discount)) + tax;
//                    text_totalPrice.setText("$" + (String.valueOf(amount)));
//                }
//                if (type.equalsIgnoreCase("percentage")) {
//                    double tax_amount = ((subtotal * promo_value) / 100);
//                    text_discount.setText(promo_value + "%");
//                    double final_amount = subtotal - tax_amount + tax;
//                    text_totalPrice.setText("$" + (String.valueOf(final_amount)));
//                }

            } else {
                linear_item_detail.setVisibility(View.GONE);
                relative_empty.setVisibility(View.VISIBLE);
            }
        } else if (status.equalsIgnoreCase("0") && key.equalsIgnoreCase("invalid_session")) {
            invalidSession(msg);
        } else {
            CommonMethod.showToastShort(msg, getApplicationContext());
        }
    }

    // get cart data
    @Override
    public void setDataToRecyclerView(ArrayList<CartData> getCartArrayList, String status, String msg, String key, String cart_sum, String service_tax) {
        if (status.equalsIgnoreCase("1") && key.equalsIgnoreCase("getcart")) {
            cartDataArrayList = getCartArrayList;
            if (cartDataArrayList.size() > 0) {
                linear_item_detail.setVisibility(View.VISIBLE);
                relative_empty.setVisibility(View.GONE);
                setCartListAdapter();
                res_id = getCartArrayList.get(0).getRestaurant_id();
                text_subtotal.setText("$" + cart_sum);
                text_tax.setText("$" + service_tax);
                Double total = Double.parseDouble(cart_sum) + Double.parseDouble(service_tax);
                text_totalPrice.setText("$" + (String.valueOf(total)));

                subtotal = Double.valueOf(cart_sum);
                tax = Double.valueOf(service_tax);
                coupanSubtotal = cart_sum;

            } else {
                linear_item_detail.setVisibility(View.GONE);
                relative_empty.setVisibility(View.VISIBLE);
            }
        } else if (status.equalsIgnoreCase("0") && key.equalsIgnoreCase("invalid_session")) {
            invalidSession(msg);
        } else {
            CommonMethod.showToastShort(msg, getApplicationContext());
        }
    }

    @Override
    public void onResponseFailure(Throwable throwable) {
        Log.d(TAG, throwable.getMessage());
        CommonMethod.hideProgressDialog(progressDialog);
    }


    // listener method
    @Override
    public void onChoiceItemClick(int position) {
        // CommonMethod.showToastShort(cartDataArrayList.get(position).getQty(),getApplicationContext());
        item_position = position;
        qty = Integer.valueOf(cartDataArrayList.get(position).getQty());
        String price = cartDataArrayList.get(position).getPrice();
        qty++;
        final_price = qty * (Double.valueOf(price));
        itemAction(position, qty, final_price);
    }

    @Override
    public void onChoiceItemDecreaseClick(int position) {
        item_position = position;
        qty = Integer.valueOf(cartDataArrayList.get(position).getQty());
        if (qty > 1) {
            String price = cartDataArrayList.get(position).getPrice();
            qty--;
            final_price = qty * (Double.valueOf(price));
            itemAction(position, qty, final_price);
        }
    }

    // delete item from cart
    @Override
    public void onDeleteItemClick(int position) {
        if (isInternetPresent) {
            item_position = position;
            HashMap<String, String> params = new HashMap<>();
            params.put("session_token", session_token);
            params.put("_id", cartDataArrayList.get(position).get_id());
            params.put("seeker_id", _id);
            Log.d("params", params.toString());
            deleteCartPresenter = new DeleteCartPresenter(this);
            deleteCartPresenter.requestDeleteCart(params);
        } else {
            CommonMethod.showAlertDialog(Cart_Activity.this, getString(R.string.internet_toast));
        }
    }

    // action on add and decrease method
    private void itemAction(int position, int qty, double final_price) {
        if (isInternetPresent) {
            HashMap<String, String> params = new HashMap<>();
            params.put("session_token", session_token);
            params.put("seeker_id", _id);
            params.put("food_item_id", cartDataArrayList.get(position).getFood_item_id());
            params.put("_id", cartDataArrayList.get(position).get_id());
            params.put("qty", "" + qty);
            params.put("price", cartDataArrayList.get(position).getPrice());
            params.put("discount", "0");
            params.put("total_price", "" + final_price);
            Log.d("params", params.toString());
            updateCartPresenter = new UpdateCartPresenter(this);
            updateCartPresenter.requestUpdateCart(params);
        } else {
            CommonMethod.showAlertDialog(Cart_Activity.this, getString(R.string.internet_toast));
        }
    }

    // invalid session token
    private void invalidSession(String msg) {
        LoginManager.getInstance().logOut();
        BMSPrefs.putString(getApplicationContext(), Constants._ID, "");
        BMSPrefs.putString(getApplicationContext(), Constants.Seeker_id, "");
        CommonMethod.showToastShort(msg, getApplicationContext());
        callActivityFinish(new Intent(getApplicationContext(), ArticleActivity.class));
    }
}
