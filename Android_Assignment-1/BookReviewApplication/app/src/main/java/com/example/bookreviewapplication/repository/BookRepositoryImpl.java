package com.example.bookreviewapplication.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.bookreviewapplication.data.local.AppDatabase;
import com.example.bookreviewapplication.data.local.dao.FavoriteBookDao;
import com.example.bookreviewapplication.data.local.entity.FavoriteBook;
import com.example.bookreviewapplication.data.model.Book;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BookRepositoryImpl implements BookRepository {

    private final MutableLiveData<List<Book>> booksLiveData = new MutableLiveData<>();
    private final FavoriteBookDao favoriteBookDao;
    private final LiveData<List<FavoriteBook>> allFavoritesLiveData;
    private final Application application;

    public BookRepositoryImpl(Application app) {
        this.application = app;

        // Initialize Room DAO
        AppDatabase db = AppDatabase.getInstance(app);
        favoriteBookDao = db.favoriteBookDao();
        allFavoritesLiveData = favoriteBookDao.getAllFavorites();

        // load JSON manually:
        loadBooksFromAssets();
    }

    private void loadBooksFromAssets() {
        try (InputStream is = application.getAssets().open("books.json");
             InputStreamReader reader = new InputStreamReader(is)) {

            Type listType = new TypeToken<List<Book>>() {}.getType();
            List<Book> bookList = new Gson().fromJson(reader, listType);

            booksLiveData.postValue(bookList);

        } catch (IOException e) {
            e.printStackTrace();
            booksLiveData.postValue(null);
        }
    }

    @Override
    public LiveData<List<Book>> getAllBooks() {
        return booksLiveData;
    }

    @Override
    public LiveData<Book> getBookDetails(int bookId) {
        MutableLiveData<Book> detailLiveData = new MutableLiveData<>();
        List<Book> currentList = booksLiveData.getValue();
        if (currentList != null) {
            for (Book b : currentList) {
                if (b.getId() == bookId) {
                    detailLiveData.setValue(b);
                    return detailLiveData;
                }
            }
        }
        booksLiveData.observeForever(books -> {
            if (books != null) {
                for (Book b : books) {
                    if (b.getId() == bookId) {
                        detailLiveData.postValue(b);
                        break;
                    }
                }
            }
        });
        return detailLiveData;
    }

    @Override
    public LiveData<List<FavoriteBook>> getFavoriteBooks() {
        return allFavoritesLiveData;
    }

    @Override
    public LiveData<FavoriteBook> getFavoriteById(int bookId) {
        return favoriteBookDao.getFavoriteById(bookId);
    }

    @Override
    public void addBookToFavorites(Book book) {
        FavoriteBook fav = new FavoriteBook(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getDescription(),
                book.getRating(),
                book.getThumbnailUrl(),
                book.getImageUrl()
        );
        new InsertFavoriteAsyncTask(favoriteBookDao).execute(fav);
    }

    @Override
    public void removeBookFromFavorites(FavoriteBook favoriteBook) {
        new DeleteFavoriteAsyncTask(favoriteBookDao).execute(favoriteBook);
    }

    // AsyncTasks for Room operations:
    private static class InsertFavoriteAsyncTask extends android.os.AsyncTask<FavoriteBook, Void, Void> {
        private final FavoriteBookDao dao;
        InsertFavoriteAsyncTask(FavoriteBookDao dao) { this.dao = dao; }
        @Override
        protected Void doInBackground(FavoriteBook... favorites) {
            dao.insert(favorites[0]);
            return null;
        }
    }

    private static class DeleteFavoriteAsyncTask extends android.os.AsyncTask<FavoriteBook, Void, Void> {
        private final FavoriteBookDao dao;
        DeleteFavoriteAsyncTask(FavoriteBookDao dao) { this.dao = dao; }
        @Override
        protected Void doInBackground(FavoriteBook... favorites) {
            dao.delete(favorites[0]);
            return null;
        }
    }
}
