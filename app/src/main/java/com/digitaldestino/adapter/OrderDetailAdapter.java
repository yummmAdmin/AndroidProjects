package com.digitaldestino.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.digitaldestino.R;
import com.digitaldestino.modelClass.my_order.BookingData;
import com.digitaldestino.modelClass.my_order.Items;

import java.util.ArrayList;

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Items> itemsArrayList;

    public OrderDetailAdapter(Context context, ArrayList<Items> itemsArrayList) {
        this.context = context;
        this.itemsArrayList = itemsArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrderDetailAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.order_detail_adapter, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.text_item_name.setText(itemsArrayList.get(position).getQty() + "x " + itemsArrayList.get(position).getFood_item_name());
        holder.text_item_price.setText("$" + itemsArrayList.get(position).getPrice());

    }

    @Override
    public int getItemCount() {
        return itemsArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView text_item_name, text_item_price;

        public ViewHolder(View itemView) {
            super(itemView);
            text_item_name = itemView.findViewById(R.id.text_item_name);
            text_item_price = itemView.findViewById(R.id.text_item_price);
        }
    }
}
