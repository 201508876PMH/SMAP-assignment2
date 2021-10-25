package dk.au.mad21fall.assignment1.au536878.repository;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import dk.au.mad21fall.assignment1.au536878.database.MovieDatabase;
import dk.au.mad21fall.assignment1.au536878.database.MovieEntity;

public class Repository {
    private MovieDatabase db;                   //database
    private LiveData<List<MovieEntity>> movies; //livedata
    private MovieRequester mr;
    private ExecutorService executorService;    //allows for methods to be off-loaded Mainthread
    private static Repository singletonRepository;

    //constructor - takes application object for context
    private Repository(Application app){
        db = MovieDatabase.getDatabase(app.getApplicationContext());
        mr = new MovieRequester(this);
        movies = db.movieDao().getAll();
        executorService = Executors.newSingleThreadExecutor();
    }
    public static Repository getRepositoryInstance(Application app){
        if(singletonRepository == null){
            singletonRepository = new Repository(app);
        }
        return singletonRepository;
    }

    public void requestMovie(String movieName, Context context){
        mr.requestMovie(movieName, context);
    }

    public LiveData<List<MovieEntity>> getMovies() {
        return movies;
    }

    public LiveData<MovieEntity> findMovie(String name){
        return db.movieDao().findMovie(name);
    }

    public List<MovieEntity> getMoviesFromGenre(String genre){
        return db.movieDao().getMoviesFromGenre(genre);
    }

    public void addMovie(MovieEntity movie){
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                db.movieDao().addMovie(movie);
            }
        });

    }

    public void updateMovie(MovieEntity movie){
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                db.movieDao().updateMovie(movie);
            }
        });
    }

    public void delete(MovieEntity movie){
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                db.movieDao().delete(movie);
            }
        });
    }
}
