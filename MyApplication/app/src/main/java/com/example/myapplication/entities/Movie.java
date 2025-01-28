package com.example.myapplication.entities;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Movie implements Serializable {
    @PrimaryKey
    private String id;
    private Category category;
    private String videoPath;
    private String description;
    private String imagePath;

    public Movie(String id, Category category, String video, String description, String imagePath) {
        this.id = id;
        this.category = category;
        this.videoPath = video;
        this.description = description;
        this.imagePath = imagePath;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getVideo() {
        return videoPath;
    }

    public void setVideo(String video) {
        this.videoPath = video;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}

