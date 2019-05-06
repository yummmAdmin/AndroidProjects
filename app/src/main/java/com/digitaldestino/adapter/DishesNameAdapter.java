package com.digitaldestino.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.digitaldestino.R;

public class DishesNameAdapter extends RecyclerView.Adapter<DishesNameAdapter.ViewHolder> {
    private Context context;

    public DishesNameAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DishesNameAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.dishes_name_adapter, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        CategoryBaseDishesAdapter categoryBaseDishesAdapter = new CategoryBaseDishesAdapter(context);
//        holder.recycler_dish.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
//        holder.recycler_dish.setAdapter(categoryBaseDishesAdapter);
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private RecyclerView recycler_dish;

        public ViewHolder(View itemView) {
            super(itemView);
            recycler_dish = itemView.findViewById(R.id.recycler_dishes);
        }
    }
}
