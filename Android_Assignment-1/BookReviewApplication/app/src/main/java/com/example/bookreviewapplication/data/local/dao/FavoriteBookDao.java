package com.example.bookreviewapplication.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.bookreviewapplication.data.local.entity.FavoriteBook;

import java.util.List;

@Dao
public interface FavoriteBookDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(FavoriteBook book);

    @Delete
    void delete(FavoriteBook book);

    @Query("SELECT * FROM favorite_books")
    LiveData<List<FavoriteBook>> getAllFavorites();

    @Query("SELECT * FROM favorite_books WHERE id = :bookId LIMIT 1")
    LiveData<FavoriteBook> getFavoriteById(int bookId);
}
