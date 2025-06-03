package com.example.bookreviewapplication.ui.bookdetail;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.bookreviewapplication.R;
import com.example.bookreviewapplication.data.local.entity.FavoriteBook;
import com.example.bookreviewapplication.data.model.Book;
import com.example.bookreviewapplication.viewmodel.BookDetailViewModel;

public class BookDetailActivity extends AppCompatActivity {

    private BookDetailViewModel viewModel;
    private ImageView imgCover;
    private TextView txtTitle, txtAuthor, txtRating, txtDescription;
    private Button btnFavorite;
    private int bookId;
    private boolean isFavorited = false;
    private Book currentBook;
    private FavoriteBook currentFavorite;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        imgCover = findViewById(R.id.img_cover);
        txtTitle = findViewById(R.id.txt_detail_title);
        txtAuthor = findViewById(R.id.txt_detail_author);
        txtRating = findViewById(R.id.txt_rating);
        txtDescription = findViewById(R.id.txt_description);
        btnFavorite = findViewById(R.id.btn_favorite);

        bookId = getIntent().getIntExtra("BOOK_ID", -1);

        viewModel = new ViewModelProvider(this).get(BookDetailViewModel.class);
        viewModel.setBookId(bookId);

        // Observe details of the book
        viewModel.getBookDetail().observe(this, book -> {
            if (book != null) {
                currentBook = book;
                txtTitle.setText(book.getTitle());
                txtAuthor.setText(book.getAuthor());
                txtRating.setText("Rating: " + book.getRating());
                txtDescription.setText(book.getDescription());
                // Placeholder image
                imgCover.setImageResource(R.drawable.ic_book_placeholder);
            }
        });

        viewModel.getFavoriteDetail().observe(this, favoriteBook -> {
            currentFavorite = favoriteBook;
            if (favoriteBook != null) {
                isFavorited = true;
                btnFavorite.setText("Remove from Favorites");
            } else {
                isFavorited = false;
                btnFavorite.setText("Add to Favorites");
            }
        });

        btnFavorite.setOnClickListener(v -> {
            if (isFavorited && currentFavorite != null) {
                viewModel.removeFromFavorites(currentFavorite);
                Toast.makeText(this, "Removed from Favorites", Toast.LENGTH_SHORT).show();
            } else if (!isFavorited && currentBook != null) {
                viewModel.addToFavorites(currentBook);
                Toast.makeText(this, "Added to Favorites", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
