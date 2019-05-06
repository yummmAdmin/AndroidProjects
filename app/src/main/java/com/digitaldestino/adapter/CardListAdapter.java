package com.digitaldestino.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.digitaldestino.R;
import com.digitaldestino.listener.onCardSelectListener;
import com.digitaldestino.modelClass.get_card.Card_list;

import java.util.ArrayList;

public class CardListAdapter extends RecyclerView.Adapter<CardListAdapter.ViewHolder> {
    private Context context;
    private int lastSelectedPosition = 0;
    private onCardSelectListener onCardSelectListener;
    private ArrayList<Card_list> card_listArrayList;

    public CardListAdapter(Context context, onCardSelectListener onCardSelectListener, ArrayList<Card_list> card_listArrayList) {
        this.context = context;
        this.onCardSelectListener = onCardSelectListener;
        this.card_listArrayList = card_listArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CardListAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cardlist_adapter, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final Card_list card_list = card_listArrayList.get(position);
        holder.text_card_number.setText("xxxx xxxx xxxx " + card_list.getLast4());
        holder.radio_select.setChecked(lastSelectedPosition == position);
        if (lastSelectedPosition == position) {
            holder.radio_select.setChecked(true);
            holder.linear_select.setBackgroundColor(Color.parseColor("#E83B33"));
            holder.text_card_number.setTextColor(ContextCompat.getColor(context, R.color.white));
            holder.text_delete_card.setTextColor(ContextCompat.getColor(context, R.color.white));
        } else {
            holder.linear_select.setBackgroundColor(Color.parseColor("#ECEBEC"));
            holder.text_card_number.setTextColor(ContextCompat.getColor(context, R.color.black));
            holder.text_delete_card.setTextColor(ContextCompat.getColor(context, R.color.black));
        }

        holder.text_delete_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCardSelectListener.onCardDeleteItemClick(card_list.getId(),position);
                card_listArrayList.remove(position);
                notifyDataSetChanged();
            }
        });


    }

    @Override
    public int getItemCount() {
        return card_listArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView text_card_number, text_delete_card;
        private LinearLayout linear_select;
        private RadioButton radio_select;

        public ViewHolder(View itemView) {
            super(itemView);
            text_card_number = itemView.findViewById(R.id.text_card_number);
            text_delete_card = itemView.findViewById(R.id.text_delete_card);
            linear_select = itemView.findViewById(R.id.linear_select);
            radio_select = itemView.findViewById(R.id.radio_select);
            linear_select.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lastSelectedPosition = getAdapterPosition();
                    notifyDataSetChanged();
                    onCardSelectListener.onCardSelectItemClick(lastSelectedPosition);
                }
            });
        }
    }
}
