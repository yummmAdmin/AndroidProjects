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
import com.digitaldestino.listener.onArticleItemListener;
import com.digitaldestino.listener.onBookmarkListener;
import com.digitaldestino.modelClass.near_restaurent.HomeData;
import com.digitaldestino.utils.Constants;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class DishesAdapter extends RecyclerView.Adapter<DishesAdapter.ViewHolder> {
    private Context context;
    private onArticleItemListener onArticleItemListener;
    private ArrayList<HomeData> userArrayList;
    private onBookmarkListener onBookmarkListener;

    public DishesAdapter(Context context, onArticleItemListener onArticleItemListener, ArrayList<HomeData> userArrayList, onBookmarkListener onBookmarkListener) {
        this.context = context;
        this.onArticleItemListener = onArticleItemListener;
        this.userArrayList = userArrayList;
        this.onBookmarkListener = onBookmarkListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DishesAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurent_search, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        HomeData homeData = userArrayList.get(position);
        if (homeData.getDishes().getIs_favourite() != null)
        {
            if (homeData.getDishes().getIs_favourite().equalsIgnoreCase("1")) {
                holder.img_bookmark.setBackgroundResource(R.drawable.rate);

            } else {
                holder.img_bookmark.setBackgroundResource(R.drawable.rate_a);
            }
        } else {
            holder.img_bookmark.setBackgroundResource(R.drawable.rate_a);
        }

        holder.text_restaurent_name.setText(homeData.getDishes().getFood_item_name());
        holder.text_sale.setText(homeData.getDishes().getSalecount());
        double distance = Double.valueOf(homeData.getDistance());
        holder.text_distance.setText(new DecimalFormat("##.##").format(distance) + " mi");

//        holder.text_distance.setText(Math.round(Double.valueOf(homeData.getDistance())) + "mi");

        holder.text_desc.setText(homeData.getAddress());
        holder.text_dollar.setText("$" + homeData.getDishes().getBase_price());

        holder.img_restaurent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onArticleItemListener.onMenuItemClick(position);
            }
        });

        holder.img_bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBookmarkListener.onBookmarkItemClick(position);
            }
        });

        try {
            Glide.with(context)
                    .load(Constants.ARTICLE_IMAGE_PATH + "dish/" + homeData.getDishes().getFood_image())
                    .error(R.drawable.placeholder)
                    .skipMemoryCache(true)
                    .into(holder.img_restaurent);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView img_restaurent, img_bookmark;
        private TextView text_restaurent_name, text_desc, text_dollar, text_sale, text_distance;

        public ViewHolder(View itemView) {
            super(itemView);
            img_restaurent = itemView.findViewById(R.id.img_restaurent);
            text_restaurent_name = itemView.findViewById(R.id.text_restaurent_name);
            text_desc = itemView.findViewById(R.id.text_desc);
            text_dollar = itemView.findViewById(R.id.text_dollar);
            text_sale = itemView.findViewById(R.id.text_sale);
            text_distance = itemView.findViewById(R.id.text_distance);
            img_bookmark = itemView.findViewById(R.id.img_bookmark);
        }
    }
}
