package com.amaita.thenewsapp.ui.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.amaita.thenewsapp.data.TheNewsRepository;

public class MainActivityViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final int mTab;
    private final TheNewsRepository mRepository;

    public MainActivityViewModelFactory (TheNewsRepository repository, int tab) {
        this.mRepository = repository;
        this.mTab = tab;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        return (T) new MainActivityViewModel(mRepository,mTab);
    }
}
