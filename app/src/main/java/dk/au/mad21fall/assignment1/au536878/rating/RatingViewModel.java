package dk.au.mad21fall.assignment1.au536878.rating;

import android.app.Application;
import android.graphics.Movie;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.room.Entity;

import java.util.List;

import dk.au.mad21fall.assignment1.au536878.database.MovieEntity;
import dk.au.mad21fall.assignment1.au536878.repository.Repository;

public class RatingViewModel extends ViewModel {

    private LiveData<MovieEntity> movie;
    private Repository repository;

    public void instantiateMovieModel(Application app, String name){
        repository = Repository.getRepositoryInstance(app);
        movie = repository.findMovie(name);
    }

    public MovieEntity getMovieObject(){
        return movie.getValue();
    }
    public LiveData<MovieEntity> getMovieObjectAsLiveData(){
        return movie;
    }

    public void setMovieData(MovieEntity movieObject){
        repository.updateMovie(movieObject);
    }
}
