package dk.au.mad21fall.assignment1.au536878;

import android.content.Context;
import android.content.Intent;

import java.lang.reflect.Type;

public class IntentTransferHelper {
    public static Intent prepareIntentFromMovieData(Movie movieObject, Context owner, Class classType){
        Intent i =  new Intent(owner, classType);

        i.putExtra("movieName", movieObject.name);
        i.putExtra("movieYear", movieObject.year);
        i.putExtra("movieRating", movieObject.movieRating);
        i.putExtra("moviePlot", movieObject.plot);
        i.putExtra("movieGenre", movieObject.genre);
        i.putExtra("userNotes", movieObject.userNotes);
        i.putExtra("userRating", movieObject.userRating);
        i.putExtra("index", movieObject.index);

        return i;
    }
    public static Intent prepareIntentFromMovieData(Movie movieObject){
        Intent i =  new Intent();

        i.putExtra("movieName", movieObject.name);
        i.putExtra("movieYear", movieObject.year);
        i.putExtra("movieRating", movieObject.movieRating);
        i.putExtra("moviePlot", movieObject.plot);
        i.putExtra("movieGenre", movieObject.genre);
        i.putExtra("userNotes", movieObject.userNotes);
        i.putExtra("userRating", movieObject.userRating);
        i.putExtra("index", movieObject.index);

        return i;
    }

    public static Movie constructMovieDataFromIntent(Intent i){
        Movie m = new Movie();

        m.name = i.getStringExtra("movieName");
        m.year = i.getStringExtra("movieYear");
        m.movieRating = i.getStringExtra("movieRating");
        m.plot = i.getStringExtra("moviePlot");
        m.genre = i.getStringExtra("movieGenre");
        m.userNotes = i.getStringExtra("userNotes");
        m.userRating = i.getStringExtra("userRating");
        m.index = i.getStringExtra("index");

        return m;
    }
}
