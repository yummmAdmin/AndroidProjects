package com.digitaldestino.adapter;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.digitaldestino.R;
import com.digitaldestino.listener.onArticleItemListener;
import com.digitaldestino.modelClass.article.Article;
import com.digitaldestino.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder> implements Filterable {
    private Context context;
    private ArrayList<Article> articleArrayList;
    private onArticleItemListener onArticleItemListener;
    private ArrayList<Article> articleFilterArrayList;

    public ArticleAdapter(Context context, ArrayList<Article> articleArrayList, onArticleItemListener onArticleItemListener) {
        this.context = context;
        this.articleArrayList = articleArrayList;
        this.onArticleItemListener = onArticleItemListener;
        this.articleFilterArrayList = articleArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.article_adapter, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final Article article = articleFilterArrayList.get(position);
        holder.txt_name.setText(article.getName());
        holder.txt_description.setText(article.getDescription());
        holder.txt_description.setLines(3);
        try {
            Glide.with(context)
                    .load(Constants.ARTICLE_IMAGE_PATH+"article/"+article.getImage())
                    .error(R.drawable.placeholder)
                    .skipMemoryCache(true)
                    .into(holder.article_image);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        holder.frame_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onArticleItemListener.onArticleItemClick(position, article.get_id());
            }
        });
    }


    @Override
    public int getItemCount() {
        return articleFilterArrayList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    articleFilterArrayList = articleArrayList;
                } else {
                    ArrayList<Article> filteredList = new ArrayList<>();
                    for (Article row : articleArrayList) {
                        // name match condition. this might differ depending on your requirement
                        // here we are looking for article name
                        if (row.getName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }
                    articleFilterArrayList = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = articleFilterArrayList;
                filterResults.count = articleFilterArrayList.size();
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                articleFilterArrayList = (ArrayList<Article>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_name, txt_description;
        private FrameLayout frame_layout;
        private ImageView article_image;

        public ViewHolder(View itemView) {
            super(itemView);
            txt_name = itemView.findViewById(R.id.txt_name);
            txt_description = itemView.findViewById(R.id.txt_description);
            frame_layout = itemView.findViewById(R.id.frame_layout);
            article_image = itemView.findViewById(R.id.article_image);
        }
    }
}
