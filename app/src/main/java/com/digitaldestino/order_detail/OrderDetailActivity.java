package com.digitaldestino.order_detail;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.digitaldestino.R;
import com.digitaldestino.adapter.ArticleAdapter;
import com.digitaldestino.adapter.OrderDetailAdapter;
import com.digitaldestino.modelClass.my_order.BookingData;
import com.digitaldestino.modelClass.my_order.Items;
import com.digitaldestino.rate_restaurant.RateRestaurantActivity;
import com.digitaldestino.utils.CommonMethod;
import com.digitaldestino.utils.Constants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class OrderDetailActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView img_back,img_restaurant;
    private TextView text_res_name, text_address, text_date, text_subtotal, text_service_tax, text_totalPrice, text_discount;
    private Button btn_rate_restaurant;
    private RecyclerView recycler_item_detail;
    private OrderDetailAdapter orderDetailAdapter;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<BookingData> bookingDataArrayList = new ArrayList<>();
    private ArrayList<Items> itemsArrayList = new ArrayList<>();
    private SimpleDateFormat sdf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        findView();
        setAdapter();
        setData();
    }

    private void findView() {
        sdf = new SimpleDateFormat("dd/MM/yyyy ");
        Intent intent = getIntent();
        bookingDataArrayList = intent.getParcelableArrayListExtra("bookingDataArrayList");
        itemsArrayList = intent.getParcelableArrayListExtra("itemsArrayList");
        img_back = findViewById(R.id.img_back);
        text_res_name = findViewById(R.id.text_res_name);
        text_address = findViewById(R.id.text_address);
        text_date = findViewById(R.id.text_date);
        text_subtotal = findViewById(R.id.text_subtotal);
        text_service_tax = findViewById(R.id.text_service_tax);
        text_totalPrice = findViewById(R.id.text_totalPrice);
        btn_rate_restaurant = findViewById(R.id.btn_rate_restaurant);
        text_discount = findViewById(R.id.text_discount);
        img_restaurant=findViewById(R.id.img_restaurant);
        img_back.setOnClickListener(this);
        btn_rate_restaurant.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back: {
                onBackPressed();
                break;
            }
            case R.id.btn_rate_restaurant: {
                callActivity(new Intent(getApplicationContext(), RateRestaurantActivity.class));
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

    // adapter method
    private void setAdapter() {
        recycler_item_detail = findViewById(R.id.recycler_item_detail);
        orderDetailAdapter = new OrderDetailAdapter(getApplicationContext(), itemsArrayList);
        linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recycler_item_detail.setLayoutManager(linearLayoutManager);
        recycler_item_detail.setAdapter(orderDetailAdapter);
    }

    // set data to view
    private void setData() {
        text_subtotal.setText("$" + bookingDataArrayList.get(0).getTotal());
        text_service_tax.setText("$" + bookingDataArrayList.get(0).getService_tax());
        text_totalPrice.setText("$" + bookingDataArrayList.get(0).getTotal_price());
        text_res_name.setText(bookingDataArrayList.get(0).getName());
        text_discount.setText("$" + bookingDataArrayList.get(0).getDiscount());
        if (bookingDataArrayList.get(0).getOrder_address().equalsIgnoreCase("")) {
            text_address.setText("Not Available");
        } else {
            text_address.setText(bookingDataArrayList.get(0).getOrder_address());
        }
        long timestamp = Long.parseLong(bookingDataArrayList.get(0).getOrder_date_time());
        Date d = new Date(timestamp);
        String date = sdf.format(d);
        text_date.setText(date);

        try {
            Glide.with(getApplicationContext())
                    .load(Constants.ARTICLE_IMAGE_PATH+"restaurants/"+bookingDataArrayList.get(0).getRes_image())
                    .error(R.drawable.placeholder)
                    .skipMemoryCache(true)
                    .into(img_restaurant);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // call activity method
    private void callActivity(Intent intent) {
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left);
    }
}
