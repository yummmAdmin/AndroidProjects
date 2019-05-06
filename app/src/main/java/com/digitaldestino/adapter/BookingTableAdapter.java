package com.digitaldestino.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.digitaldestino.R;
import com.digitaldestino.modelClass.book_table.TableData;
import com.digitaldestino.utils.CommonMethod;
import com.digitaldestino.utils.Constants;

import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class BookingTableAdapter extends RecyclerView.Adapter<BookingTableAdapter.ViewHolder> {
    private Context context;
    private ArrayList<TableData> tableDataArrayList;
    private SimpleDateFormat sdf;

    public BookingTableAdapter(Context context, ArrayList<TableData> tableDataArrayList) {
        this.context = context;
        this.tableDataArrayList = tableDataArrayList;
        sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BookingTableAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.bookingtable_adapter, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            TableData tableData = tableDataArrayList.get(position);


            long timestamp = Long.parseLong(tableData.getOrder_date_time());
            Date d = new Date(timestamp);
            String date = sdf.format(d);

            holder.text_person.setText("Person: " + tableData.getNo_of_person());
            holder.text_date.setText(context.getString(R.string.date_time) + " " + date);
            holder.text_res_name.setText(tableData.getRestaurant_name());
            holder.text_res_address.setText("Restaurant Address: " + tableData.getRestaurant_address());
            holder.text_person_detail.setText("Personal Detail: " + tableData.getName() + ", " + tableData.getAddress());
            holder.text_status.setText(tableData.getStatus());
            if(tableData.getStatus().equalsIgnoreCase("Pending"))
            {
                holder.text_status.setBackgroundResource(R.color.yellow);
            }
            else
            {
                holder.text_status.setBackgroundResource(R.color.green );
            }
                Glide.with(context)
                        .load(Constants.ARTICLE_IMAGE_PATH+"restaurants/"+tableData.getRestaurant_image())
                        .error(R.drawable.placeholder)
                        .skipMemoryCache(true)
                        .into(holder.img_restaurant);

        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return tableDataArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView text_res_name, text_date, text_person, text_res_address, text_person_detail,text_status;
        private ImageView img_restaurant;

        public ViewHolder(View itemView) {
            super(itemView);
            text_res_name = itemView.findViewById(R.id.text_res_name);
            text_date = itemView.findViewById(R.id.text_date);
            text_person = itemView.findViewById(R.id.text_person);
            text_res_address = itemView.findViewById(R.id.text_res_address);
            text_person_detail = itemView.findViewById(R.id.text_person_detail);
            text_status=itemView.findViewById(R.id.text_status);
            img_restaurant=itemView.findViewById(R.id.img_restaurant);
        }
    }
}
