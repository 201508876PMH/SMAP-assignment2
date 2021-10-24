package dk.au.mad21fall.assignment1.au536878.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Locale;

import dk.au.mad21fall.assignment1.au536878.R;

@Entity
public class MovieEntity {
    @NonNull
    @PrimaryKey(autoGenerate = false)
    private String name;

    private String genre;
    private String year;
    private String movieRating;
    private String plot;
    private String userNotes;
    private String userRating;
    private String index;

    public MovieEntity(){}

    public MovieEntity(String name, String genre, String year, String movieRating, String plot, String userNotes, String userRating) {
        this.name = name;
        this.genre = genre;
        this.year = year;
        this.movieRating = movieRating;
        this.plot = plot;
        this.userNotes = userNotes;
        this.userRating = userRating;
    }

    public int getResourceIdFromGenre(){
        switch(genre.toLowerCase(Locale.ROOT)) {
            case "action":
                return R.drawable.action;
            case "comedy":
                return R.drawable.comedy;
            case "drama":
                return R.drawable.drama;
            case "horror":
                return R.drawable.horror;
            case "romance":
                return R.drawable.romance;
            case "western":
                return R.drawable.western;
            default:
                return R.drawable.ic_launcher_background;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMovieRating() {
        return movieRating;
    }

    public void setMovieRating(String movieRating) {
        this.movieRating = movieRating;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getUserNotes() {
        return userNotes;
    }

    public void setUserNotes(String userNotes) {
        this.userNotes = userNotes;
    }

    public String getUserRating() {
        return userRating;
    }

    public void setUserRating(String userRating) {
        this.userRating = userRating;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }
}
