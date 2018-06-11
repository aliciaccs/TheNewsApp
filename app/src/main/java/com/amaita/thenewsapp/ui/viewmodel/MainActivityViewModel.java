package com.amaita.thenewsapp.ui.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.amaita.thenewsapp.data.TheNewsRepository;
import com.amaita.thenewsapp.data.database.Article;

import java.util.List;


public class MainActivityViewModel extends ViewModel {

    private LiveData<List<Article>> articles;
    private final int mTab;
    private final TheNewsRepository mRepository;

    public MainActivityViewModel (TheNewsRepository repository, int tab) {
        articles = new MutableLiveData<List<Article>>();
        this.mRepository = repository;
        this.mTab = tab;
        articles = repository.getArticles(tab);
    }

    public LiveData<List<Article>> getArticles () {
        return articles;
    }

    public void saveFavoriteArticle (int position) {
        mRepository.saveArticle(articles.getValue().get(position));
    }


}
