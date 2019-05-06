package com.digitaldestino.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.digitaldestino.R;
import com.digitaldestino.listener.onOrderSelectListener;
import com.digitaldestino.modelClass.get_restaurant_menu.ResDetial;
import com.digitaldestino.utils.Constants;

import java.util.ArrayList;

public class CategoryBaseDishesAdapter extends RecyclerView.Adapter<CategoryBaseDishesAdapter.ViewHolder> {
    private Context context;
    private ArrayList<ResDetial> resDetialArrayList;
    private onOrderSelectListener onOrderSelectListener;
    private String status;

    public CategoryBaseDishesAdapter(Context context, ArrayList<ResDetial> resDetialArrayList, onOrderSelectListener onOrderSelectListener, String status) {
        this.context = context;
        this.resDetialArrayList = resDetialArrayList;
        this.onOrderSelectListener = onOrderSelectListener;
        this.status = status;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryBaseDishesAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.category_base_adapter, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        if (status.equalsIgnoreCase("popular")) {
            holder.text_cat_name.setText("Top 10");
        } else {
            holder.text_cat_name.setText(resDetialArrayList.get(position).getFood_section_name());
        }

        //    holder.text_cat_name.setText(resDetialArrayList.get(position).getFood_section_name());
        holder.text_dishes_name.setText(resDetialArrayList.get(position).getFood_item_name());
        holder.text_description.setText(resDetialArrayList.get(position).getFood_desc());
        holder.text_price.setText("$" + resDetialArrayList.get(position).getBase_price());
        holder.text_sale.setText(resDetialArrayList.get(position).getSalecount() + " sale");


        if (resDetialArrayList.get(position).getOrganic() != null) {
            holder.linear_organic.setVisibility(View.VISIBLE);
            if (resDetialArrayList.get(position).getOrganic().equalsIgnoreCase("1")) {
                holder.img_organic.setBackgroundResource(R.drawable.nut_free_green);
            } else {
                holder.img_organic.setBackgroundResource(R.drawable.nut_free_gre_dark);
            }
        } else {
            holder.linear_organic.setVisibility(View.GONE);
        }
        if (resDetialArrayList.get(position).getVegan() != null) {
            holder.linear_vegan.setVisibility(View.GONE);
            if (resDetialArrayList.get(position).getVegan().equalsIgnoreCase("1")) {
                holder.img_vegan.setBackgroundResource(R.drawable.nut_free_green);
            } else {
                holder.img_vegan.setBackgroundResource(R.drawable.nut_free_gre_dark);
            }
        } else {
            holder.linear_vegan.setVisibility(View.GONE);
        }

        try {
            String cat_name = resDetialArrayList.get(position).getFood_section_name();
            String cat_name_copy = resDetialArrayList.get(position - 1).getFood_section_name();
            if (cat_name_copy.equalsIgnoreCase(cat_name)) {
                holder.text_cat_name.setVisibility(View.GONE);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try {
            Glide.with(context)
                    .load(Constants.ARTICLE_IMAGE_PATH + "dish/" + resDetialArrayList.get(position).getFood_image())
                    .error(R.drawable.placeholder)
                    .skipMemoryCache(true)
                    .into(holder.res_image);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        holder.menu_relative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOrderSelectListener.onOrderSelectItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return resDetialArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView text_cat_name, text_dishes_name, text_description, text_price, text_sale;
        private ImageView res_image, img_organic, img_vegan;
        private RelativeLayout menu_relative;
        private LinearLayout linear_organic, linear_vegan;

        public ViewHolder(View itemView) {
            super(itemView);
            text_dishes_name = itemView.findViewById(R.id.text_dishes_name);
            text_cat_name = itemView.findViewById(R.id.text_cat_name);
            text_description = itemView.findViewById(R.id.text_description);
            text_price = itemView.findViewById(R.id.text_price);
            text_sale = itemView.findViewById(R.id.text_sale);
            menu_relative = itemView.findViewById(R.id.menu_relative);
            res_image = itemView.findViewById(R.id.res_image);
            img_organic = itemView.findViewById(R.id.img_organic);
            img_vegan = itemView.findViewById(R.id.img_vegan);
            linear_organic = itemView.findViewById(R.id.linear_organic);
            linear_vegan = itemView.findViewById(R.id.linear_vegan);
        }
    }
}
