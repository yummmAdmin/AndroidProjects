package com.digitaldestino.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.digitaldestino.R;
import com.digitaldestino.listener.onOrderSelectListener;
import com.digitaldestino.modelClass.my_order.BookingData;
import com.digitaldestino.utils.Constants;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.ViewHolder> {
    private Context context;
    private ArrayList<BookingData> bookingDataArrayList;
    private onOrderSelectListener onOrderSelectListener;
    SimpleDateFormat sdf;

    public MyOrderAdapter(Context context, ArrayList<BookingData> bookingDataArrayList, onOrderSelectListener onOrderSelectListener) {
        this.context = context;
        this.bookingDataArrayList = bookingDataArrayList;
        this.onOrderSelectListener = onOrderSelectListener;
        sdf = new SimpleDateFormat("dd/MM/yyyy ");
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyOrderAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.myorder_adapter, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.text_res_name.setText(bookingDataArrayList.get(position).getName());
        holder.text_status.setText(bookingDataArrayList.get(position).getStatus());
        if (bookingDataArrayList.get(position).getStatus().equalsIgnoreCase("Processing")) {
            holder.text_status.setBackgroundResource(R.color.yellow);
        } else if (bookingDataArrayList.get(position).getStatus().equalsIgnoreCase("Delivered")) {
            holder.text_status.setBackgroundResource(R.color.green);
        } else {
            holder.text_status.setBackgroundResource(R.color.cancel_color);
        }
        long timestamp = Long.parseLong(bookingDataArrayList.get(position).getOrder_date_time());
        Date d = new Date(timestamp);
        String date = sdf.format(d);
        holder.text_date.setText(date);
        holder.relative_myorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOrderSelectListener.onOrderSelectItemClick(position);
            }
        });

        try {
            Glide.with(context)
                    .load(Constants.ARTICLE_IMAGE_PATH+"restaurants/"+bookingDataArrayList.get(position).getRes_image())
                    .error(R.drawable.placeholder)
                    .skipMemoryCache(true)
                    .into(holder.img_restaurant);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return bookingDataArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView img_restaurant;
        private TextView text_res_name, text_date, text_status;
        private RelativeLayout relative_myorder;

        public ViewHolder(View itemView) {
            super(itemView);
            img_restaurant = itemView.findViewById(R.id.img_restaurant);
            text_res_name = itemView.findViewById(R.id.text_res_name);
            text_date = itemView.findViewById(R.id.text_date);
            text_status = itemView.findViewById(R.id.text_status);
            relative_myorder = itemView.findViewById(R.id.relative_myorder);
        }
    }
}
