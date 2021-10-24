package dk.au.mad21fall.assignment1.au536878.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import dk.au.mad21fall.assignment1.au536878.database.MovieDatabase;
import dk.au.mad21fall.assignment1.au536878.database.MovieEntity;

public class Repository {
    private MovieDatabase db;                   //database
    private LiveData<List<MovieEntity>> movies; //livedata

    //constructor - takes application object for context
    public Repository(Application app){
        db = MovieDatabase.getDatabase(app.getApplicationContext());
        movies = db.movieDao().getAll();
    }

    public LiveData<List<MovieEntity>> getMovies() {
        return movies;
    }

    public MovieEntity findMovie(String name){
        return db.movieDao().findMovie(name);
    }

    public List<MovieEntity> getMoviesFromGenre(String genre){
        return db.movieDao().getMoviesFromGenre(genre);
    }

    public void addMovie(MovieEntity movie){
        db.movieDao().addMovie(movie);
    }

    public void updateMovie(MovieEntity movie){
        db.movieDao().updateMovie(movie);
    }

    public void delete(MovieEntity movie){
        db.movieDao().delete(movie);
    }
}
