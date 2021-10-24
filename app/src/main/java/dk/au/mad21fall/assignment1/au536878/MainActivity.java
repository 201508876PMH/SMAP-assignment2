package dk.au.mad21fall.assignment1.au536878;

import static dk.au.mad21fall.assignment1.au536878.IntentTransferHelper.constructMovieDataFromIntent;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import dk.au.mad21fall.assignment1.au536878.repository.Repository;

public class MainActivity extends AppCompatActivity implements MovieAdaptor.IMovieItemClickedListener {


    //widgets
    private RecyclerView rcvList;
    private MovieAdaptor adaptor;
    private Button bttnExit;
    private TextView userRating;

    ActivityResultLauncher<Intent> resultFromDetailedActivity;

    //state
    private MainViewModel m;
    private Repository repository;

    //data
    private ArrayList<Movie> movies = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        instantiate();

        m = new ViewModelProvider(this).get(MainViewModel.class);

        if(m.getMovieData() == null){
            loadCSV();
            m.instantiateMovieModel(movies);
        }

        m.getMovieData().observe(this, new Observer<ArrayList<Movie>>() {
            @Override
            public void onChanged(ArrayList<Movie> movies) {
               adaptor.updateMovieList(movies);
            }
        });

    }

    protected void instantiate(){
        setContentView(R.layout.activity_main);
        userRating = findViewById(R.id.list_item_MovieRating);

        //set up recyclerview with adaptor and layout manager
        adaptor = new MovieAdaptor(this);
        rcvList = findViewById(R.id.rcvMovies);
        rcvList.setLayoutManager(new LinearLayoutManager(this));
        rcvList.setAdapter(adaptor);

        resultFromDetailedActivity = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),new ActivityResultDetailHandler());
        adaptor.updateMovieList(movies);

        bttnExit = findViewById(R.id.bttnExit);
        bttnExit.setOnClickListener((view -> finish()));
    }

    public void loadCSV(){
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

                Movie movie = new Movie(
                        input[0],
                        input[1],
                        input[2],
                        input[3],
                        plot,
                        "",
                        "X"
                );
                movies.add(movie);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //callback when a person item is clicked in the list
    @Override
    public void onMovieClicked(int index) {

        Movie fetchedMovie = m.getMovieData().getValue().get(index);
        fetchedMovie.index = String.valueOf(index);

        Intent intentResult = IntentTransferHelper.prepareIntentFromMovieData(fetchedMovie, this, DetailedActivity.class);
        resultFromDetailedActivity.launch(intentResult);
    }


    private class ActivityResultDetailHandler implements ActivityResultCallback<ActivityResult> {

        @Override
        public void onActivityResult(ActivityResult result) {

            if(result.getResultCode() == RESULT_OK) {
                Intent data = result.getData();
                Movie movieObject;
                movieObject = constructMovieDataFromIntent(data);

                m.setMovieData(movieObject);
            }
        }
    }
}