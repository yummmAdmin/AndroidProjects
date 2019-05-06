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
import com.digitaldestino.utils.Constants;

import java.util.ArrayList;

public class RestaurentListAdapter extends RecyclerView.Adapter<RestaurentListAdapter.ViewHolder>
{
    private Context context;
    private ArrayList<com.digitaldestino.modelClass.restaurent.HomeData> userRestaurentList;
    private onArticleItemListener onArticleItemListener;


    public RestaurentListAdapter(Context context, ArrayList<com.digitaldestino.modelClass.restaurent.HomeData> userRestaurentList,onArticleItemListener onArticleItemListener) {
        this.context = context;
        this.userRestaurentList = userRestaurentList;
        this.onArticleItemListener = onArticleItemListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RestaurentListAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurent_adapter, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.text_restaurent_name.setText(userRestaurentList.get(position).getName());
        holder.text_address.setText(userRestaurentList.get(position).getAddress()+" $$ "+Math.round(Double.valueOf(userRestaurentList.get(position).getDistance())) +"mi");
        holder.text_slogen.setText(userRestaurentList.get(position).getSlogan());

        try {
            Glide.with(context)
                    .load(Constants.ARTICLE_IMAGE_PATH+"restaurants/"+userRestaurentList.get(position).getRes_image())
                    .error(R.drawable.placeholder)
                    .skipMemoryCache(true)
                    .into(holder.img_restaurent);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        holder.relative_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onArticleItemListener.onRestaurantItemClick(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return userRestaurentList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView text_restaurent_name,text_address,text_slogen;
        private ImageView img_restaurent;
        private RelativeLayout relative_item;
        public ViewHolder(View itemView) {
            super(itemView);
            text_restaurent_name=itemView.findViewById(R.id.text_restaurent_name);
            text_address=itemView.findViewById(R.id.text_address);
            img_restaurent=itemView.findViewById(R.id.img_restaurent);
            text_slogen=itemView.findViewById(R.id.text_slogen);
            relative_item=itemView.findViewById(R.id.relative_item);
        }
    }
}
