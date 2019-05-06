package com.digitaldestino.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.digitaldestino.R;
import com.digitaldestino.modelClass.get_favourite.FavData;
import com.digitaldestino.utils.Constants;

import java.util.ArrayList;

public class WishlistAdapter extends RecyclerView.Adapter<WishlistAdapter.ViewHolder> {
    private Context context;
    private ArrayList<FavData> favDataArrayList;

    public WishlistAdapter(Context context, ArrayList<FavData> favDataArrayList) {
        this.context = context;
        this.favDataArrayList = favDataArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new WishlistAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurent_search, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FavData favData = favDataArrayList.get(position);
        holder.text_restaurent_name.setText(favData.getDishes().getFood_item_name());
        holder.text_sale.setText(favData.getDishes().getSalecount());
        holder.text_distance.setText(Math.round(Double.valueOf(favData.getDistance())) + "mi");
        holder.text_desc.setText(favData.getAddress());
        holder.text_dollar.setText("$" + favData.getDishes().getBase_price());
        holder.img_bookmark.setBackgroundResource(R.drawable.rate);
        try {
            Glide.with(context)
                    .load(Constants.ARTICLE_IMAGE_PATH+"dish/"+favData.getDishes().getFood_image())
                    .error(R.drawable.placeholder)
                    .skipMemoryCache(true)
                    .into(holder.img_restaurent);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return favDataArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView text_restaurent_name, text_desc, text_dollar, text_sale, text_distance;
        private ImageView img_restaurent, img_bookmark;
        public ViewHolder(View itemView) {
            super(itemView);
            img_restaurent = itemView.findViewById(R.id.img_restaurent);
            text_restaurent_name = itemView.findViewById(R.id.text_restaurent_name);
            text_restaurent_name = itemView.findViewById(R.id.text_restaurent_name);
            text_desc = itemView.findViewById(R.id.text_desc);
            text_dollar = itemView.findViewById(R.id.text_dollar);
            text_sale = itemView.findViewById(R.id.text_sale);
            text_distance = itemView.findViewById(R.id.text_distance);
            img_bookmark = itemView.findViewById(R.id.img_bookmark);
        }
    }
}
