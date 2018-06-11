package com.amaita.thenewsapp.data.rest;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.util.Log;

import com.amaita.thenewsapp.data.database.Article;
import com.amaita.thenewsapp.data.rest.response.TopHeadline;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TheNewsRetrofitDataSource {


    private static final String LOG_TAG = TheNewsRetrofitDataSource.class.getSimpleName();
    // For Singleton instantiation
    private static final Object LOCK = new Object();
    private static TheNewsRetrofitDataSource sInstance;
    private final Context mContext;
    private TheNewsWebClient mExecutors;
    private final MutableLiveData<List<Article>> mDownloadedArticles;


    private TheNewsRetrofitDataSource(Context context, TheNewsWebClient executors) {
        mContext = context;
        mExecutors = executors;
        mDownloadedArticles = new MutableLiveData<List<Article>>();
    }


    /**
     * Get the singleton for this class
     */
    public static TheNewsRetrofitDataSource getInstance(Context context, TheNewsWebClient executors) {
        Log.d(LOG_TAG, "Getting the network data source");
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new TheNewsRetrofitDataSource(context.getApplicationContext(), executors);
                Log.d(LOG_TAG, "Made new network data source");
            }
        }
        return sInstance;
    }

    public void startFetchArticles () {
        mExecutors.getApiService().getTopHeadline("VE").enqueue(new Callback<TopHeadline>() {
            @Override
            public void onResponse(Call<TopHeadline> call, Response<TopHeadline> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (!response.body().status.equalsIgnoreCase("error")) {
                            mDownloadedArticles.postValue(response.body().articles);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<TopHeadline> call, Throwable t) {

            }
        });
    }


    public LiveData<List<Article>> getDowloadedArticles() {
        startFetchArticles ();
        return mDownloadedArticles;
    }
}
