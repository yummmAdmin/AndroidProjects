package com.digitaldestino.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.digitaldestino.R;
import com.digitaldestino.modelClass.get_feedback.FeedbackData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ShowReviewAdapter extends RecyclerView.Adapter<ShowReviewAdapter.ViewHolder> {
    private Context context;
    private ArrayList<FeedbackData> feedbackDataArrayList;
    private SimpleDateFormat sdf;

    public ShowReviewAdapter(Context context, ArrayList<FeedbackData> feedbackDataArrayList) {
        this.context = context;
        this.feedbackDataArrayList = feedbackDataArrayList;
        sdf = new SimpleDateFormat("dd/MM/yyyy");
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ShowReviewAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.show_review_adapter, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FeedbackData feedbackData = feedbackDataArrayList.get(position);
        holder.text_name.setText(feedbackData.getFirst_name() + " " + feedbackData.getLast_name());
        holder.text_description.setText(feedbackData.getReview());
        if(feedbackData.getFeedback_date_time()==null)
        {

        }
        else {
            long timestamp = Long.parseLong(feedbackData.getFeedback_date_time());
            Date d = new Date(timestamp);
            String date = sdf.format(d);
            holder.text_date.setText(date);
        }

    }

    @Override
    public int getItemCount() {
        return feedbackDataArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView text_name, text_description, text_date;
        private RelativeLayout main_layout;

        public ViewHolder(View itemView) {
            super(itemView);
            text_name = itemView.findViewById(R.id.text_name);
            text_description = itemView.findViewById(R.id.text_description);
            text_date = itemView.findViewById(R.id.text_date);
            main_layout=itemView.findViewById(R.id.main_layout);
        }
    }

}
