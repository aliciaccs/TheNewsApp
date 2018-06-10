package com.amaita.thenewsapp.data;

import com.amaita.thenewsapp.data.database.Article;
import com.amaita.thenewsapp.utils.GlobalCustom;

import java.util.ArrayList;

public class TheNewsRepository {

    private static final Object LOCK = new Object();

    private static TheNewsRepository sInstance;

    private int tabNumber;

    private TheNewsRepository (int tabNumber) {
        this.tabNumber = tabNumber;
    }


    public synchronized static TheNewsRepository getInstance (int tabNumber) {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new TheNewsRepository(tabNumber);
            }
        }
    return sInstance;
    }


}
