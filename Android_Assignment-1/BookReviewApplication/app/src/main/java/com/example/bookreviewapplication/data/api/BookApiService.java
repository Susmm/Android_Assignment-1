package com.example.bookreviewapplication.data.api;

import com.example.bookreviewapplication.data.model.Book;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface BookApiService {
    // This “GET books.json” will load /assets/books.json
    @GET("books.json")
    Call<List<Book>> fetchBooks();
}
