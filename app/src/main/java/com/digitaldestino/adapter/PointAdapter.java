package com.digitaldestino.adapter;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.digitaldestino.R;

public class PointAdapter extends RecyclerView.Adapter<PointAdapter.ViewHolder> {
    private Context context;
    private int loyalty;
    private int redCount=0;

    public PointAdapter(Context context, int loyalty, int redCount) {
        this.context = context;
        this.loyalty = loyalty;
        this.redCount = redCount;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PointAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.point_adapter, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (redCount>0) {
            if (position<redCount)
            {
                holder.img_red.setBackgroundResource(R.drawable.select_like);
            }
            else {
                holder.img_red.setBackgroundResource(R.drawable.deselect_like);
            }
        }
        else
        {
            holder.img_red.setBackgroundResource(R.drawable.deselect_like);
        }
    }

    @Override
    public int getItemCount() {
        return loyalty;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView img_red;
        public ViewHolder(View itemView) {
            super(itemView);
            img_red=itemView.findViewById(R.id.img_red);
        }
    }
}
