package com.amaita.thenewsapp.data.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface TheNewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void bulkInsert(Article... article);

    @Query("SELECT * FROM articles")
    LiveData<List<Article>> getFavoriteArticles();
}
