package com.digitaldestino.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.digitaldestino.R;
import com.digitaldestino.listener.onOrderSelectListener;
import com.digitaldestino.modelClass.search_dishes.SearchDish;

import java.util.ArrayList;

public class SearchListAdapter extends RecyclerView.Adapter<SearchListAdapter.ViewHolder> {
    private Context context;
    private ArrayList<String> searchDisharrayList;
    private onOrderSelectListener onOrderSelectListener;
    int num = 1;

    public SearchListAdapter(Context context, ArrayList<String> searchDisharrayList,onOrderSelectListener onOrderSelectListener) {
        this.context = context;
        this.searchDisharrayList = searchDisharrayList;
        this.onOrderSelectListener=onOrderSelectListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SearchListAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.searchdish_adapter, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.text_search_dish.setText(searchDisharrayList.get(position));
        holder.linear_dish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onOrderSelectListener.onOrderSelectItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (num * 10 > searchDisharrayList.size()) {
            return searchDisharrayList.size();
        } else {
            return num * 10;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView text_search_dish;
        private LinearLayout linear_dish;

        public ViewHolder(View itemView) {
            super(itemView);
            text_search_dish = itemView.findViewById(R.id.text_search_dish);
            linear_dish=itemView.findViewById(R.id.linear_dish);
        }
    }
}
