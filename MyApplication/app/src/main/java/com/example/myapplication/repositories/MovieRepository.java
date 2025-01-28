package com.example.myapplication.repositories;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.myapplication.api.MoviesApi;
import com.example.myapplication.daoes.MovieDao;
import com.example.myapplication.databases.MovieDB;
import com.example.myapplication.entities.Movie;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MovieRepository {
    private MoviesApi moviesApi;
    private MovieDao movieDao;
    private MutableLiveData<Movie> movie;
    private Context context;
    private MutableLiveData<List<Movie>> moviesListData;

    public MovieRepository(Context context) {
        moviesApi = new MoviesApi();
        movieDao = MovieDB.getInstance(context).movieDao();
        this.context = context;
        moviesListData =new MutableLiveData<>();
    }

    public MutableLiveData<List<Movie>> getMoviesByCategory(String token,LifecycleOwner context){
        MutableLiveData<List<Movie>> movies = new MutableLiveData<>();
        moviesApi.getMoviesByCategories(movies,token);
        movies.observe(context, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> videos) {
                if (videos != null && !videos.isEmpty()) {
                    moviesListData.setValue(videos);
                    Executors.newSingleThreadExecutor().execute(() -> {
                        try {
                            Movie[] tempMovies  = moviesListData.getValue().toArray(new Movie[0]);
                                    movieDao.insertMovies(tempMovies);
                        } catch (Exception e) {
                            Log.e("MovieRepository", "Error inserting movies: " + e.getMessage(), e);
                        }
                    });
                    moviesListData.removeObserver(this);
                }
            }
        });

        return movies;
    }
}

