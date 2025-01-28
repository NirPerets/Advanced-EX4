package com.example.myapplication.viewModel;
import android.content.Context;
import android.provider.MediaStore;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

//import com.example.myapplication.entities.Movie;

import com.example.myapplication.entities.Movie;
import com.example.myapplication.globals.GlobalToken;
import com.example.myapplication.repositories.MovieRepository;

import java.util.List;

public class MovieViewModel extends ViewModel {
    private final MovieRepository movieRepository;
    private final MutableLiveData<List<Movie>> movies = new MutableLiveData<>();
    private final MutableLiveData<Movie> movie = new MutableLiveData<>();

    public MovieViewModel(Context context) {
        movieRepository = new MovieRepository(context);
    }

    public LiveData<List<Movie>> getMoviesByCategory(LifecycleOwner context) {
//
        movieRepository.getMoviesByCategory(GlobalToken.token, context).observe(context, movies::setValue);
        return movies;
    }
}