package com.amaita.thenewsapp.data.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Article.class}, version = 1)
public abstract class TheNewsDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "theNews";

    // For Singleton instantiation
    private static final Object LOCK = new Object();
    private static volatile TheNewsDatabase sInstance;

    public static TheNewsDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                if (sInstance == null) {
                    sInstance = Room.databaseBuilder(context.getApplicationContext(),
                            TheNewsDatabase.class, TheNewsDatabase.DATABASE_NAME).build();
                }
            }
        }
        return sInstance;
    }


    public abstract TheNewsDao theNewsDao();
}
