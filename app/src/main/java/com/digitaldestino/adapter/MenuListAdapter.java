package com.digitaldestino.adapter;

import android.content.Context;
import android.graphics.Color;
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
import com.digitaldestino.listener.onArticleItemListener;
import com.digitaldestino.modelClass.menu.MenuItem;

import java.util.ArrayList;


public class  MenuListAdapter extends RecyclerView.Adapter<MenuListAdapter.ViewHolder> {
    private Context context;
    private ArrayList<MenuItem> menuItemArrayList;
    private onArticleItemListener onArticleItemListener;

    public MenuListAdapter(Context context, ArrayList<MenuItem> menuItemArrayList, onArticleItemListener onArticleItemListener) {
        this.context = context;
        this.menuItemArrayList = menuItemArrayList;
        this.onArticleItemListener = onArticleItemListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MenuListAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.menulist_adapter, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.text_menulist.setText(menuItemArrayList.get(position).getMenu_name());

        Glide.with(context)
                .load(menuItemArrayList.get(position).getImage())
                .skipMemoryCache(true)
                .into(holder.img_menu);


        holder.linear_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onArticleItemListener.onMenuItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return menuItemArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView img_menu;

        private TextView text_menulist;

        private LinearLayout linear_item;

        public ViewHolder(View itemView) {
            super(itemView);
            img_menu = itemView.findViewById(R.id.img_menu);
            text_menulist = itemView.findViewById(R.id.text_menulist);
            linear_item = itemView.findViewById(R.id.linear_item);
        }

    }
}
