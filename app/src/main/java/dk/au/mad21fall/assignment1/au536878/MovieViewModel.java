package dk.au.mad21fall.assignment1.au536878;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MovieViewModel extends ViewModel {

    private MutableLiveData<Movie> movie;

    public void instantiateMovieModel(Movie movieObject){
        movie = new MutableLiveData<Movie>(movieObject);
    }

    public LiveData<Movie> getMovieData(){
        return movie;
    }

    public void setMovieData(Movie movieObject){
        movie.setValue(movieObject);
    }
}
