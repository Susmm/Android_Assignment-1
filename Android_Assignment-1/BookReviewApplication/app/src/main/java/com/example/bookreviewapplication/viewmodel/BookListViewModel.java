package com.example.bookreviewapplication.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.bookreviewapplication.data.local.entity.FavoriteBook;
import com.example.bookreviewapplication.data.model.Book;
import com.example.bookreviewapplication.repository.BookRepository;
import com.example.bookreviewapplication.repository.BookRepositoryImpl;

import java.util.List;

public class BookListViewModel extends AndroidViewModel {

    private BookRepository repository;
    private LiveData<List<Book>> allBooks;
    private LiveData<List<FavoriteBook>> favoriteBooks;

    public BookListViewModel(@NonNull Application application) {
        super(application);
        repository = new BookRepositoryImpl(application);
        allBooks = repository.getAllBooks();
        favoriteBooks = repository.getFavoriteBooks();
    }

    public LiveData<List<Book>> getAllBooks() {
        return allBooks;
    }

    public LiveData<List<FavoriteBook>> getFavoriteBooks() {
        return favoriteBooks;
    }

    public void toggleFavorite(Book book, boolean isAlreadyFavorite) {
        if (isAlreadyFavorite) {
            repository.getFavoriteById(book.getId()).observeForever(fav -> {
                if (fav != null) {
                    repository.removeBookFromFavorites(fav);
                }
            });
        } else {
            repository.addBookToFavorites(book);
        }
    }
}
