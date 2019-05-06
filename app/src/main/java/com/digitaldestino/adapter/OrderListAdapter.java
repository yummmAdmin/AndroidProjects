package com.digitaldestino.adapter;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.digitaldestino.R;
import com.digitaldestino.listener.onAddCartListener;
import com.digitaldestino.modelClass.get_cart.CartData;
import com.digitaldestino.utils.CircleImageView;
import com.digitaldestino.utils.CommonMethod;
import com.digitaldestino.utils.Constants;

import java.util.ArrayList;
public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.ViewHolder> {
    private Context context;
    private ArrayList<CartData> cartDataArrayList;
    private onAddCartListener onAddCartListener;

    public OrderListAdapter(Context context, ArrayList<CartData> cartDataArrayList, onAddCartListener onAddCartListener) {
        this.context = context;
        this.cartDataArrayList = cartDataArrayList;
        this.onAddCartListener = onAddCartListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrderListAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.orderlist_adapter, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final CartData cartData = cartDataArrayList.get(position);
        holder.text_name.setText(cartData.getFood_item_name());
        holder.text_price.setText("$" + cartData.getTotal_price());
        holder.text_qty.setText(cartData.getQty());

        holder.img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddCartListener.onDeleteItemClick(position);
                cartDataArrayList.remove(position);
                notifyDataSetChanged();
            }
        });

        holder.text_increaseItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddCartListener.onChoiceItemClick(position);
            }
        });

        holder.text_decreaseItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddCartListener.onChoiceItemDecreaseClick(position);
            }
        });


        try {
            Glide.with(context)
                    .load(Constants.ARTICLE_IMAGE_PATH+"dish/"+cartData.getFood_image())
                    .error(R.drawable.placeholder)
                    .skipMemoryCache(true)
                    .into(holder.img_dishes);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return cartDataArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView img_dishes;
        private ImageView img_delete;
        private TextView text_name, text_price, text_qty, text_increaseItem, text_decreaseItem;

        public ViewHolder(View itemView) {
            super(itemView);
            img_dishes = itemView.findViewById(R.id.img_dishes);
            text_name = itemView.findViewById(R.id.text_name);
            text_price = itemView.findViewById(R.id.text_price);
            text_qty = itemView.findViewById(R.id.text_qty);
            img_delete = itemView.findViewById(R.id.img_delete);
            text_increaseItem = itemView.findViewById(R.id.text_increaseItem);
            text_decreaseItem = itemView.findViewById(R.id.text_decreaseItem);
        }
    }
}
