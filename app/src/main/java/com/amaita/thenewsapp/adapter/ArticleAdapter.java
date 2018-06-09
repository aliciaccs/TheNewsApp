package com.amaita.thenewsapp.adapter;

import android.content.Context;
import android.content.ContextWrapper;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amaita.thenewsapp.R;
import com.amaita.thenewsapp.rest.response.Article;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder> {

    private static final String TAG = ArticleAdapter.class.getSimpleName();

    final private ListItemClickListener mOnClickListener;

    private ArrayList<Article> articles;

    public interface ListItemClickListener  {
        void onListItemClick (int itemPosition);
    }

    public ArticleAdapter (ArrayList<Article> articles, ListItemClickListener mOnClickListener) {
        this.mOnClickListener = mOnClickListener;
        this.articles = articles;
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.article_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        ArticleViewHolder viewHolder = new ArticleViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        Article articleItem = articles.get(position);
        holder.bind(articleItem);
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    class ArticleViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener {


        TextView title;
        TextView description;
        ImageView thumbnail;

        public ArticleViewHolder(View itemView) {
            super(itemView);
            thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
            title = (TextView) itemView.findViewById(R.id.title);
            description = (TextView) itemView.findViewById(R.id.description);
            itemView.setOnClickListener(this);
        }

        void bind(Article article) {

            title.setText(article.getTitle());
            description.setText(article.getDescription());

            Picasso.get().load(article.getUrlToImage()).placeholder(R.drawable.img_noimg).into(thumbnail);
        }

        @Override
        public void onClick(View view) {
            int itemPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(itemPosition);

        }
    }
}
