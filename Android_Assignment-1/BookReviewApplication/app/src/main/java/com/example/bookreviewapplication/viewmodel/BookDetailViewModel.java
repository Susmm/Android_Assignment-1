package com.example.bookreviewapplication.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.bookreviewapplication.data.local.entity.FavoriteBook;
import com.example.bookreviewapplication.data.model.Book;
import com.example.bookreviewapplication.repository.BookRepository;
import com.example.bookreviewapplication.repository.BookRepositoryImpl;

public class BookDetailViewModel extends AndroidViewModel {

    private BookRepository repository;
    private LiveData<Book> bookDetail;
    private LiveData<FavoriteBook> favoriteDetail;

    public BookDetailViewModel(@NonNull Application application) {
        super(application);
        repository = new BookRepositoryImpl(application);
    }

    public void setBookId(int bookId) {
        bookDetail = repository.getBookDetails(bookId);
        favoriteDetail = repository.getFavoriteById(bookId);
    }

    public LiveData<Book> getBookDetail() {
        return bookDetail;
    }

    public LiveData<FavoriteBook> getFavoriteDetail() {
        return favoriteDetail;
    }

    public void addToFavorites(Book book) {
        repository.addBookToFavorites(book);
    }

    public void removeFromFavorites(FavoriteBook favoriteBook) {
        repository.removeBookFromFavorites(favoriteBook);
    }
}
