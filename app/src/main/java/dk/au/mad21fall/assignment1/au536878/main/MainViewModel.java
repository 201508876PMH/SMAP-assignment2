package dk.au.mad21fall.assignment1.au536878.main;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import dk.au.mad21fall.assignment1.au536878.database.MovieEntity;
import dk.au.mad21fall.assignment1.au536878.repository.Repository;

public class MainViewModel extends ViewModel {

    private MutableLiveData<List<MovieEntity>> movieList;
    private Repository repository;

    public void instantiateMovieModel(List<MovieEntity> movieObjects, Application app){
        repository = new Repository(app);
    }

    public LiveData<List<MovieEntity>> getMovieData(){
        return repository.getMovies();
    }

    public void setMovieData(MovieEntity movieObject){
        repository.updateMovie(movieObject);
    }

    public void requestMovie(String movieName, Context context){
        repository.requestMovie(movieName, context);
    }
}
