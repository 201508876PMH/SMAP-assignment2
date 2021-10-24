package dk.au.mad21fall.assignment1.au536878.utils;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import dk.au.mad21fall.assignment1.au536878.database.MovieEntity;
import dk.au.mad21fall.assignment1.au536878.repository.Repository;

public class LoadCSV extends AppCompatActivity {

    private Repository repository;

    public void populateDB(){

        repository = new Repository(getApplication());

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(getAssets().open("movie_data.csv")));
            // do reading, usually loop until end of file reading

            String mLine;

            //skips first line in *.csv file
            mLine = reader.readLine();

            while ((mLine = reader.readLine()) != null) {
                String[] input = mLine.split((","));
                String plot;

                //fetch plot
                if(input[4].contains("\"")){
                    plot = mLine.split("\"")[1];
                }else{
                    plot = input[4];
                }

                MovieEntity movie = new MovieEntity(
                        input[0],
                        input[1],
                        input[2],
                        input[3],
                        plot,
                        "",
                        "X"
                );
                repository.addMovie(movie);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
