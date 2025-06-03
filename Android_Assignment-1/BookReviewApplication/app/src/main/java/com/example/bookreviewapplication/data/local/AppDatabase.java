package com.example.bookreviewapplication.data.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.bookreviewapplication.data.local.dao.FavoriteBookDao;
import com.example.bookreviewapplication.data.local.entity.FavoriteBook;

@Database(entities = {FavoriteBook.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DB_NAME = "book_app_db";
    private static volatile AppDatabase INSTANCE;

    public abstract FavoriteBookDao favoriteBookDao();

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            DB_NAME
                    ).build();
                }
            }
        }
        return INSTANCE;
    }
}
