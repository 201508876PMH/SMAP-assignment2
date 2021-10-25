package dk.au.mad21fall.assignment1.au536878.detailed;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import dk.au.mad21fall.assignment1.au536878.database.MovieEntity;
import dk.au.mad21fall.assignment1.au536878.repository.Repository;

public class DetailedViewModel extends ViewModel {
    private LiveData<MovieEntity> movie;
    private Repository repository;

    public void instantiateMovieModel(Application app, String name){
        repository = new Repository(app);
        movie = repository.findMovie(name);
    }

    public LiveData<MovieEntity> getSpecificMovie(String movieName){
        return movie;
    }

    public MovieEntity getMovieObject(){
        return movie.getValue();
    }

    public void deleteMovie(MovieEntity movieObject){
        repository.delete(movieObject);
    }

    public void setMovieData(MovieEntity movieObject){
        repository.updateMovie(movieObject);
    }
}
