package dk.au.mad21fall.assignment1.au536878;

import android.content.Context;
import android.content.Intent;

import java.lang.reflect.Type;

import dk.au.mad21fall.assignment1.au536878.database.MovieEntity;

public class IntentTransferHelper {
    public static Intent prepareIntentFromMovieData(MovieEntity movieObject, Context owner, Class classType){
        Intent i =  new Intent(owner, classType);

        i.putExtra("movieName", movieObject.getName());
        i.putExtra("movieYear", movieObject.getYear());
        i.putExtra("movieRating", movieObject.getMovieRating());
        i.putExtra("moviePlot", movieObject.getPlot());
        i.putExtra("movieGenre", movieObject.getGenre());
        i.putExtra("userNotes", movieObject.getUserNotes());
        i.putExtra("userRating", movieObject.getUserRating());
        i.putExtra("index", movieObject.getIndex());

        return i;
    }
    public static Intent prepareIntentFromMovieData(MovieEntity movieObject){
        Intent i =  new Intent();

        i.putExtra("movieName", movieObject.getName());
        i.putExtra("movieYear", movieObject.getYear());
        i.putExtra("movieRating", movieObject.getMovieRating());
        i.putExtra("moviePlot", movieObject.getPlot());
        i.putExtra("movieGenre", movieObject.getGenre());
        i.putExtra("userNotes", movieObject.getUserNotes());
        i.putExtra("userRating", movieObject.getUserRating());
        i.putExtra("index", movieObject.getIndex());

        return i;
    }

    public static MovieEntity constructMovieDataFromIntent(Intent i){
        MovieEntity m = new MovieEntity();

        m.setName(i.getStringExtra("movieName"));
        m.setYear(i.getStringExtra("movieYear"));
        m.setMovieRating(i.getStringExtra("movieRating"));
        m.setPlot(i.getStringExtra("moviePlot"));
        m.setGenre(i.getStringExtra("movieGenre"));
        m.setUserNotes(i.getStringExtra("userNotes"));
        m.setUserRating(i.getStringExtra("userRating"));
        m.setIndex(i.getStringExtra("index"));

        return m;
    }
}
