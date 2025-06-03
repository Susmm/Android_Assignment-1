package com.example.bookreviewapplication.repository;

import androidx.lifecycle.LiveData;

import com.example.bookreviewapplication.data.local.entity.FavoriteBook;
import com.example.bookreviewapplication.data.model.Book;

import java.util.List;

public interface BookRepository {
    // Fetch list of all books (from assets JSON)
    LiveData<List<Book>> getAllBooks();

    // Get a single Book’s details (search from loaded list)
    LiveData<Book> getBookDetails(int bookId);

    // Favorites management (Room)
    LiveData<List<FavoriteBook>> getFavoriteBooks();
    LiveData<FavoriteBook> getFavoriteById(int bookId);
    void addBookToFavorites(Book book);
    void removeBookFromFavorites(FavoriteBook favoriteBook);
}
