package com.example.bookreviewapplication.data.local.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favorite_books")
public class FavoriteBook {

    @PrimaryKey
    private int id;

    private String title;
    private String author;
    private String description;
    private double rating;
    private String thumbnailUrl;
    private String imageUrl;

    public FavoriteBook(int id,
                        String title,
                        String author,
                        String description,
                        double rating,
                        String thumbnailUrl,
                        String imageUrl) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.description = description;
        this.rating = rating;
        this.thumbnailUrl = thumbnailUrl;
        this.imageUrl = imageUrl;
    }

    // --- Getters & Setters ---
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }

    public String getThumbnailUrl() { return thumbnailUrl; }
    public void setThumbnailUrl(String thumbnailUrl) { this.thumbnailUrl = thumbnailUrl; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}
