package com.example.myapplication.daoes;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplication.entities.Movie;

import java.util.List;

@Dao
public interface MovieDao {
    @Query("SELECT * FROM Movie")
    List<Movie> index();

    @Query("SELECT * FROM movie WHERE id = :id")
    Movie getMovie(String id);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Movie... Movies);

    @Update
    void update(Movie... Movies);

    @Delete
    void delete(Movie... Movies);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovies(Movie... Movies);
    @Query("DELETE FROM MOVIE")
    void clearTable();

}


