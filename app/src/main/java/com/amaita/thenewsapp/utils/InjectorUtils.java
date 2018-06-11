package com.amaita.thenewsapp.utils;

import android.content.Context;

import com.amaita.thenewsapp.data.TheNewsRepository;
import com.amaita.thenewsapp.data.database.TheNewsDatabase;
import com.amaita.thenewsapp.data.rest.TheNewsRetrofitDataSource;
import com.amaita.thenewsapp.data.rest.TheNewsWebClient;
import com.amaita.thenewsapp.ui.viewmodel.MainActivityViewModelFactory;

public class InjectorUtils {

    public static TheNewsRepository provideRepository(Context context) {
        TheNewsDatabase database = TheNewsDatabase.getInstance(context.getApplicationContext());
        TheNewsWebClient theNewsWebClient = new TheNewsWebClient();
        TheNewsRetrofitDataSource networkDataSource =
                TheNewsRetrofitDataSource.getInstance(context.getApplicationContext(), theNewsWebClient);
        return TheNewsRepository.getInstance(database.theNewsDao(), networkDataSource);
    }


    public static MainActivityViewModelFactory provideMainViewModelFactory(Context context, int tab) {
        TheNewsRepository repository = provideRepository(context.getApplicationContext());
        return new MainActivityViewModelFactory(repository, tab);
    }
}
