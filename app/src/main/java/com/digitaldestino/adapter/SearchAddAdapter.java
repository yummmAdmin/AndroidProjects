package com.digitaldestino.adapter;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.digitaldestino.R;
import com.digitaldestino.listener.onDeleteItemListener;
import com.digitaldestino.modelClass.auto_complete.AutoCompleteData;

import java.util.ArrayList;

//Adapter class
public class SearchAddAdapter extends RecyclerView.Adapter<SearchAddAdapter.ViewHolder> {
    private Context context;
    private ArrayList<AutoCompleteData> searchDataArrayList;
    private onDeleteItemListener onDeleteItemListener;

    public SearchAddAdapter(Context context, ArrayList<AutoCompleteData> searchDataArrayList, onDeleteItemListener onDeleteItemListener) {
        this.context = context;
        this.searchDataArrayList = searchDataArrayList;
        this.onDeleteItemListener = onDeleteItemListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SearchAddAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.searchadd_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.text_search_item.setText(searchDataArrayList.get(position).getFood_item_name());
        holder.img_delete_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDeleteItemListener.onItemClick(position,Integer.parseInt(searchDataArrayList.get(position).getFood_item_id()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return searchDataArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView text_search_item;
        private ImageView img_delete_item;

        public ViewHolder(View itemView) {
            super(itemView);
            text_search_item = itemView.findViewById(R.id.text_search_item);
            img_delete_item = itemView.findViewById(R.id.img_delete_item);
        }
    }
}