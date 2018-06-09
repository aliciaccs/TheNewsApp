package com.amaita.thenewsapp.tabs;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amaita.thenewsapp.MainActivity;
import com.amaita.thenewsapp.R;
import com.amaita.thenewsapp.adapter.ArticleAdapter;
import com.amaita.thenewsapp.rest.TheNewsWebClient;
import com.amaita.thenewsapp.rest.response.Article;
import com.amaita.thenewsapp.rest.response.TopHeadline;
import com.amaita.thenewsapp.utils.GlobalCustom;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsTabFragment extends Fragment  implements  ArticleAdapter.ListItemClickListener {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String ARG_TITLE = "section_title";
    private TheNewsWebClient theNewsWebClient;
    private RecyclerView rv_articles;
    private ArticleAdapter mAdapter;

    public NewsTabFragment() {
        theNewsWebClient = new TheNewsWebClient();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        rv_articles = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        getArticles(getArguments().getInt(ARG_SECTION_NUMBER));
        return rootView;
    }

    public void getArticles (int section) {
        if (section == GlobalCustom.ALL_NEWS) {
        //obtener data del servicio web
            theNewsWebClient.getApiService().getTopHeadline("ve").enqueue(new Callback<TopHeadline>() {
                @Override
                public void onResponse(Call<TopHeadline> call, Response<TopHeadline> response) {
                    //validar si hubo algun error
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            if (!response.body().status.equalsIgnoreCase("error")) {
                                setArticleAdapter(response.body().articles);
                            }
                        }
                    }

                }

                @Override
                public void onFailure(Call<TopHeadline> call, Throwable t) {

                }
            });

        } else {
        // obtener data de persistencia
        }

    }

    public void setArticleAdapter (ArrayList<Article> articles) {
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),1,GridLayoutManager.VERTICAL,false);
        rv_articles.setLayoutManager(layoutManager);
        mAdapter = new ArticleAdapter(articles,this);
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

    }
}