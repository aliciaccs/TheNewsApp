package com.amaita.thenewsapp.ui.tabs;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amaita.thenewsapp.R;
import com.amaita.thenewsapp.ui.ArticleDetailActivity;
import com.amaita.thenewsapp.ui.MainActivity;
import com.amaita.thenewsapp.ui.adapter.ArticleAdapter;
import com.amaita.thenewsapp.data.rest.TheNewsWebClient;
import com.amaita.thenewsapp.data.database.Article;
import com.amaita.thenewsapp.data.rest.response.TopHeadline;
import com.amaita.thenewsapp.ui.viewmodel.MainActivityViewModel;
import com.amaita.thenewsapp.ui.viewmodel.MainActivityViewModelFactory;
import com.amaita.thenewsapp.utils.GlobalCustom;
import com.amaita.thenewsapp.utils.InjectorUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsTabFragment extends Fragment  implements  ArticleAdapter.ListItemClickListener, ArticleAdapter.FavoriteItemClickListener {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String ARG_TITLE = "section_title";
    private TheNewsWebClient theNewsWebClient;
    private RecyclerView rv_articles;
    private ArticleAdapter mAdapter;

    //view Model
    private MainActivityViewModel mViewModel;

    public NewsTabFragment() {
        theNewsWebClient = new TheNewsWebClient();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        rv_articles = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        //get the viewModel from the factory
        MainActivityViewModelFactory factory = InjectorUtils.provideMainViewModelFactory(getContext(),getArguments().getInt(ARG_SECTION_NUMBER) );
        mViewModel = ViewModelProviders.of(this,factory).get(MainActivityViewModel.class);
        mViewModel.getArticles().observe(this, new Observer<List<Article>>() {
            @Override
            public void onChanged(@Nullable List<Article> articles) {
                if (articles != null)
                setArticleAdapter(articles);
            }
        });

        return rootView;
    }



    public void setArticleAdapter (List<Article> articles) {
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),1,GridLayoutManager.VERTICAL,false);
        rv_articles.setLayoutManager(layoutManager);
        mAdapter = new ArticleAdapter(articles,this, this, getArguments().getInt(ARG_SECTION_NUMBER));
        rv_articles.setAdapter(mAdapter);
    }

    public String getTitle () {
        return getArguments().getString(ARG_TITLE);
    }

    public static NewsTabFragment newInstance(int sectionNumber, String title) {
        NewsTabFragment fragment = new NewsTabFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        args.putString(ARG_TITLE, title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onListItemClick(int itemPosition) {
        Article article = mAdapter.getItem(itemPosition);
        Intent intent = new Intent (getContext(), ArticleDetailActivity.class);
        intent.putExtra("url",article.getUrl());
        startActivity(intent);

    }

    @Override
    public void onFavoriteItemClick(int itemPosition) {
        mViewModel.saveFavoriteArticle(itemPosition);
    }
}