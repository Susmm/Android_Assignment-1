package com.example.bookreviewapplication.ui.booklist;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookreviewapplication.R;
import com.example.bookreviewapplication.data.local.entity.FavoriteBook;
import com.example.bookreviewapplication.data.model.Book;
import com.example.bookreviewapplication.ui.bookdetail.BookDetailActivity;
import com.example.bookreviewapplication.viewmodel.BookListViewModel;

import java.util.ArrayList;
import java.util.List;

public class BookListActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private BookListAdapter adapter;
    private View offlinePlaceholder;
    private Button btnGoToFavorites;
    private TextView txtEmptyFavorites;

    private BookListViewModel viewModel;

    private boolean showingFavorites = false;

    private boolean isBookmarkFilled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recycler_books);
        offlinePlaceholder = findViewById(R.id.offline_placeholder);
        btnGoToFavorites = findViewById(R.id.btn_go_to_favorites);
        txtEmptyFavorites = findViewById(R.id.txt_empty_favorites); // NEW

        adapter = new BookListAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(BookListViewModel.class);

        if (!isOnline()) {
            showOfflineView();
        } else {
            showAllBooks();
        }

        btnGoToFavorites.setOnClickListener(v -> {
            showingFavorites = true;
            isBookmarkFilled = true;
            invalidateOptionsMenu();
            showFavoriteBooks();
        });

        adapter.setOnItemClickListener(book -> {
            Intent intent = new Intent(BookListActivity.this, BookDetailActivity.class);
            intent.putExtra("BOOK_ID", book.getId());
            startActivity(intent);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_book_list, menu);
        MenuItem favItem = menu.findItem(R.id.action_favorites);
        if (isBookmarkFilled) {
            favItem.setIcon(R.drawable.ic_baseline_bookmark_24);
        } else {
            favItem.setIcon(R.drawable.ic_baseline_bookmark_border_24);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_favorites) {
            if (!isOnline()) {
                showingFavorites = false;
                isBookmarkFilled = false;
                invalidateOptionsMenu();
                showOfflineView();
                return true;
            }

            if (showingFavorites) {
                showingFavorites = false;
                isBookmarkFilled = false;
                invalidateOptionsMenu();
                showAllBooks();
            } else {
                showingFavorites = true;
                isBookmarkFilled = true;
                invalidateOptionsMenu();
                showFavoriteBooks();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showAllBooks() {
        offlinePlaceholder.setVisibility(View.GONE);
        txtEmptyFavorites.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);

        viewModel.getAllBooks().observe(this, books -> {
            if (books != null) {
                adapter.setBooks(books);
            } else {
                Toast.makeText(this, "No books available.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showFavoriteBooks() {
        offlinePlaceholder.setVisibility(View.GONE);

        viewModel.getFavoriteBooks().observe(this, favs -> {
            List<Book> favAsBooks = new ArrayList<>();
            if (favs != null) {
                for (FavoriteBook f : favs) {
                    Book b = new Book();
                    b.setId(f.getId());
                    b.setTitle(f.getTitle());
                    b.setAuthor(f.getAuthor());
                    b.setDescription(f.getDescription());
                    b.setRating(f.getRating());
                    b.setThumbnailUrl(f.getThumbnailUrl());
                    b.setImageUrl(f.getImageUrl());
                    favAsBooks.add(b);
                }
            }

            if (favAsBooks.isEmpty()) {
                recyclerView.setVisibility(View.GONE);
                txtEmptyFavorites.setVisibility(View.VISIBLE);
            } else {
                txtEmptyFavorites.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                adapter.setBooks(favAsBooks);
            }
        });
    }

    private void showOfflineView() {
        recyclerView.setVisibility(View.GONE);
        txtEmptyFavorites.setVisibility(View.GONE);
        offlinePlaceholder.setVisibility(View.VISIBLE);
        showingFavorites = false;
    }

    private boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            return (networkInfo != null && networkInfo.isConnected());
        }
        return false;
    }
}


//package com.example.bookreviewapplication.ui.booklist;
//
//import android.content.Context;
//import android.content.Intent;
//import android.net.ConnectivityManager;
//import android.net.NetworkInfo;
//import android.os.Bundle;
//import android.view.Menu;
//import android.view.MenuInflater;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.Button;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.Toolbar;
//import androidx.lifecycle.ViewModelProvider;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.bookreviewapplication.R;
//import com.example.bookreviewapplication.data.local.entity.FavoriteBook;
//import com.example.bookreviewapplication.data.model.Book;
//import com.example.bookreviewapplication.ui.bookdetail.BookDetailActivity;
//import com.example.bookreviewapplication.viewmodel.BookListViewModel;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class BookListActivity extends AppCompatActivity {
//
//    private Toolbar toolbar;
//    private RecyclerView recyclerView;
//    private BookListAdapter adapter;
//    private View offlinePlaceholder;
//    private Button btnGoToFavorites;
//
//    private BookListViewModel viewModel;
//
//    // Track state: are we currently showing favorites?
//    private boolean showingFavorites = false;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_book_list);
//
//        // 1) Hook up the Toolbar
//        toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        // 2) Find views
//        recyclerView = findViewById(R.id.recycler_books);
//        offlinePlaceholder = findViewById(R.id.offline_placeholder);
//        btnGoToFavorites = findViewById(R.id.btn_go_to_favorites);
//
//        // 3) Set up RecyclerView + Adapter
//        adapter = new BookListAdapter();
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setAdapter(adapter);
//
//        // 4) Obtain ViewModel
//        viewModel = new ViewModelProvider(this).get(BookListViewModel.class);
//
//        // 5) Detect if we are offline at startup
//        if (!isOnline()) {
//            // Show the offline placeholder, hide RecyclerView
//            showOfflineView();
//        } else {
//            // We are online → show full book list
//            showAllBooks();
//        }
//
//        // 6) “Go to Favorites” button inside the offline placeholder
//        btnGoToFavorites.setOnClickListener(v -> {
//            // If user tapped “Go to Favorites,” switch to favorites view
//            showingFavorites = true;
//            showFavoriteBooks();
//        });
//
//        // 7) Handle RecyclerView item clicks (regardless of mode)
//        adapter.setOnItemClickListener(book -> {
//            Intent intent = new Intent(BookListActivity.this, BookDetailActivity.class);
//            intent.putExtra("BOOK_ID", book.getId());
//            startActivity(intent);
//        });
//    }
//
//    // Inflate the app bar menu (bookmark icon)
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu_book_list, menu);
//        return true;
//    }
//
//    // Handle app bar icon clicks
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        int id = item.getItemId();
//        if (id == R.id.action_favorites) {
//            // Toggle between all books and favorites
//            if (showingFavorites) {
//                // If we are already showing favorites, switch back to “all books” if online
//                if (isOnline()) {
//                    showingFavorites = false;
//                    showAllBooks();
//                } else {
//                    Toast.makeText(this,
//                            "Cannot show all books while offline. Showing favorites instead.",
//                            Toast.LENGTH_SHORT).show();
//                }
//            } else {
//                // Show favorites
//                showingFavorites = true;
//                showFavoriteBooks();
//            }
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//    // Helper: display the RecyclerView with *all* books
//    private void showAllBooks() {
//        // Hide offline placeholder (if visible)
//        offlinePlaceholder.setVisibility(View.GONE);
//        recyclerView.setVisibility(View.VISIBLE);
//
//        // Observe the LiveData for all books
//        viewModel.getAllBooks().observe(this, books -> {
//            if (books != null) {
//                adapter.setBooks(books);
//            } else {
//                // If parsing error or empty, show a Toast
//                Toast.makeText(this, "No books available.", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    // Helper: display only favorite books (Room)
//    private void showFavoriteBooks() {
//        // Hide offline placeholder (if visible)
//        offlinePlaceholder.setVisibility(View.GONE);
//        recyclerView.setVisibility(View.VISIBLE);
//
//        // Observe the LiveData for favorites
//        viewModel.getFavoriteBooks().observe(this, favs -> {
//            // Convert List<FavoriteBook> → List<Book> so adapter can accept it
//            List<Book> favAsBooks = new ArrayList<>();
//            if (favs != null) {
//                for (FavoriteBook f : favs) {
//                    Book b = new Book();
//                    b.setId(f.getId());
//                    b.setTitle(f.getTitle());
//                    b.setAuthor(f.getAuthor());
//                    b.setDescription(f.getDescription());
//                    b.setRating(f.getRating());
//                    b.setThumbnailUrl(f.getThumbnailUrl());
//                    b.setImageUrl(f.getImageUrl());
//                    favAsBooks.add(b);
//                }
//            }
//            adapter.setBooks(favAsBooks);
//        });
//    }
//
//    // Helper: show offline UI
//    private void showOfflineView() {
//        recyclerView.setVisibility(View.GONE);
//        offlinePlaceholder.setVisibility(View.VISIBLE);
//        showingFavorites = false; // ensure the menu icon knows we are not in “favorites mode” yet
//    }
//
//    // Utility to check network connectivity
//    private boolean isOnline() {
//        ConnectivityManager cm =
//                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//        if (cm != null) {
//            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
//            return (networkInfo != null && networkInfo.isConnected());
//        }
//        return false;
//    }
//}
