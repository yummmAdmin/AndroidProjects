package com.digitaldestino.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.Toast;

import com.digitaldestino.R;
import com.digitaldestino.listener.onAddCartListener;
import com.digitaldestino.listener.onArticleItemListener;
import com.digitaldestino.modelClass.dishes_detail.Price;

import java.util.ArrayList;

public class ChoiceAdapter extends RecyclerView.Adapter<ChoiceAdapter.ViewHolder> {
    private Context context;
    private onAddCartListener onAddCartListener;
    private ArrayList<Price> priceArrayList;
    private int lastSelectedPosition = 0;

    public ChoiceAdapter(Context context, onAddCartListener onAddCartListener, ArrayList<Price> priceArrayList) {
        this.context = context;
        this.onAddCartListener = onAddCartListener;
        this.priceArrayList = priceArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ChoiceAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.choice_adapter, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.radio_select.setChecked(lastSelectedPosition == position);
        holder.radio_select.setText(" " + priceArrayList.get(position).getName());
        if (lastSelectedPosition == position) {
            holder.radio_select.setChecked(true);
        }
    }

    @Override
    public int getItemCount() {
        return priceArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private RadioButton radio_select;

        public ViewHolder(View itemView) {
            super(itemView);
            radio_select = itemView.findViewById(R.id.radio_select);
            radio_select.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lastSelectedPosition = getAdapterPosition();
                    notifyDataSetChanged();
                    onAddCartListener.onChoiceItemClick(lastSelectedPosition);
                }
            });

        }
    }
}
