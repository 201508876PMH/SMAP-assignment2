package dk.au.mad21fall.assignment1.au536878;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class MainViewModel extends ViewModel {

    private MutableLiveData<ArrayList<Movie>> movieList;

    public void instantiateMovieModel(ArrayList<Movie> movieObjects){
        movieList = new MutableLiveData<ArrayList<Movie>>(movieObjects);
    }

    public LiveData<ArrayList<Movie>> getMovieData(){
        return movieList;
    }

    public void setMovieData(ArrayList<Movie> movieObjects){
        movieList.setValue(movieObjects);
    }

    public void setMovieData(Movie movieObject){
        ArrayList<Movie> currentData = movieList.getValue();
        currentData.set(Integer.parseInt(movieObject.index), movieObject);
        setMovieData(currentData);
    }

}
