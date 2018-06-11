package com.amaita.thenewsapp.data;

import android.arch.lifecycle.LiveData;

import com.amaita.thenewsapp.data.database.Article;
import com.amaita.thenewsapp.data.database.TheNewsDao;
import com.amaita.thenewsapp.data.database.TheNewsDatabase;
import com.amaita.thenewsapp.data.rest.TheNewsRetrofitDataSource;
import com.amaita.thenewsapp.data.rest.TheNewsWebClient;
import com.amaita.thenewsapp.utils.AppExecutors;
import com.amaita.thenewsapp.utils.GlobalCustom;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.logging.Handler;

public class TheNewsRepository {

    private LiveData<ArrayList<Article>> articles;

    private static final Object LOCK = new Object();

    private static TheNewsRepository sInstance;

    private TheNewsRetrofitDataSource webClient;

    private TheNewsDao newsDao;

    private TheNewsRepository (TheNewsDao newsDao, TheNewsRetrofitDataSource webClient) {
        this.newsDao = newsDao;
        this.webClient = webClient;
    }


    public synchronized static TheNewsRepository getInstance (TheNewsDao newsDao, TheNewsRetrofitDataSource webClient) {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new TheNewsRepository(newsDao,webClient);
            }
        }
    return sInstance;
    }

    public LiveData<List<Article>> getArticles (int tab) {
        if (tab == GlobalCustom.ALL_NEWS)
            return webClient.getDowloadedArticles();
        else
            return newsDao.getFavoriteArticles();
    }

    public void saveArticle (final Article article) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                newsDao.bulkInsert(article);

            }
        });
    }

}
