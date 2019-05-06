package com.digitaldestino.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.digitaldestino.R;
import com.digitaldestino.modelClass.notification.NotificationsData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
    private Context context;
    private ArrayList<NotificationsData> notificationsDataArrayList;
    private SimpleDateFormat sdf;

    public NotificationAdapter(Context context, ArrayList<NotificationsData> notificationsDataArrayList) {
        this.context = context;
        this.notificationsDataArrayList = notificationsDataArrayList;
        sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NotificationAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_adapter, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String timestamp = notificationsDataArrayList.get(position).getAddedon();
        String currentString = timestamp;
        String[] separated = currentString.split("T");
        String date = separated[0]; // this will contain "Date"
        String time = separated[1];
        String[] separated1 = time.split("\\.");
        String show_time = separated1[0]; // this will contain "time"

        holder.text_title.setText(notificationsDataArrayList.get(position).getRestaurant_name());
        holder.text_notification.setText(notificationsDataArrayList.get(position).getContent());
        holder.text_date.setText(date + " " + show_time);
    }

    @Override
    public int getItemCount() {
        return notificationsDataArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView text_title, text_notification, text_date;

        public ViewHolder(View itemView) {
            super(itemView);
            text_title = itemView.findViewById(R.id.text_title);
            text_notification = itemView.findViewById(R.id.text_notification);
            text_date = itemView.findViewById(R.id.text_date);
        }
    }
}
