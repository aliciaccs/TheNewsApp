package com.amaita.thenewsapp.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amaita.thenewsapp.R;
import com.amaita.thenewsapp.data.database.Article;
import com.amaita.thenewsapp.utils.GlobalCustom;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder> {

    private static final String TAG = ArticleAdapter.class.getSimpleName();

    final private ListItemClickListener mOnClickListener;

    final private FavoriteItemClickListener fOnClickListener;

    private List<Article> articles;

    private int tab;

    public interface ListItemClickListener  {
        void onListItemClick (int itemPosition);
    }

    public interface FavoriteItemClickListener  {
        void onFavoriteItemClick (int itemPosition);
    }

    public ArticleAdapter (List<Article> articles, ListItemClickListener mOnClickListener, FavoriteItemClickListener fOnClickListener, int tab) {
        this.mOnClickListener = mOnClickListener;
        this.articles = articles;
        this.fOnClickListener = fOnClickListener;
        this.tab = tab;
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

    public Article getItem (int position) {
        return articles.get(position);
    }

    class ArticleViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener {


        TextView title;
        TextView description;
        ImageView thumbnail;
        ImageView favorite;

        public ArticleViewHolder(View itemView) {
            super(itemView);
            thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
            favorite = (ImageView) itemView.findViewById(R.id.favorite);
            title = (TextView) itemView.findViewById(R.id.title);
            description = (TextView) itemView.findViewById(R.id.description);
            itemView.setOnClickListener(this);
            if (tab == GlobalCustom.FAVORITE_NEWS)
                favorite.setVisibility(View.GONE);
            else
                favorite.setVisibility(View.VISIBLE);

        }

        void bind(Article article) {

            title.setText(article.getTitle());
            description.setText(article.getDescription());
            favorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    favorite.setSelected(!favorite.isSelected());
                    if (favorite.isSelected()) {
                        favorite.setImageResource(android.R.drawable.star_big_on);
                    } else {
                        favorite.setImageResource(android.R.drawable.star_big_off);
                    }
                    int itemPosition = getAdapterPosition();
                    fOnClickListener.onFavoriteItemClick(itemPosition);
                }
            });

            Picasso.get().load(article.getUrlToImage()).placeholder(R.drawable.img_noimg).into(thumbnail);
        }

        @Override
        public void onClick(View view) {
            int itemPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(itemPosition);

        }
    }
}
