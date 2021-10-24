package dk.au.mad21fall.assignment1.au536878.main;

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

import java.util.ArrayList;
import java.util.List;

import dk.au.mad21fall.assignment1.au536878.MovieAdaptor;
import dk.au.mad21fall.assignment1.au536878.R;
import dk.au.mad21fall.assignment1.au536878.database.MovieEntity;
import dk.au.mad21fall.assignment1.au536878.detailed.DetailedActivity;
import dk.au.mad21fall.assignment1.au536878.utils.LoadCSV;

public class MainActivity extends AppCompatActivity implements MovieAdaptor.IMovieItemClickedListener {


    //widgets
    private RecyclerView rcvList;
    private MovieAdaptor adaptor;
    private Button bttnExit;
    private TextView userRating;

    ActivityResultLauncher<Intent> resultFromDetailedActivity;

    //state
    private MainViewModel m;
    private LoadCSV utils = new LoadCSV();


    //data
    private ArrayList<MovieEntity> movies = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        instantiate();

        m = new ViewModelProvider(this).get(MainViewModel.class);
        m.instantiateMovieModel(movies, getApplication());

        m.getMovieData().observe(this, new Observer<List<MovieEntity>>() {
            @Override
            public void onChanged(List<MovieEntity> movieEntities) {
                if(movieEntities.size() == 0){
                    utils.populateDB(getApplication());
                }
            }
        });

        m.getMovieData().observe(this, new Observer<List<MovieEntity>>() {
            @Override
            public void onChanged(List<MovieEntity> movieEntities) {
                adaptor.updateMovieList(movieEntities);
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

        resultFromDetailedActivity = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),new ActivityResultDetailHandler(this));
        adaptor.updateMovieList(movies);

        bttnExit = findViewById(R.id.bttnExit);
        bttnExit.setOnClickListener((view -> finish()));
    }


    //callback when a person item is clicked in the list
    @Override
    public void onMovieClicked(int index) {
        try {
            MovieEntity fetchedMovie = m.getMovieData().getValue().get(index);
            fetchedMovie.setIndex(String.valueOf(index));
            Intent i = new Intent(this, DetailedActivity.class);
            i.putExtra("index", fetchedMovie.getName());

            resultFromDetailedActivity.launch(i);

        }catch (Exception e){
            Log.d("LORT", e.getMessage());
        }

    }


    static class ActivityResultDetailHandler implements ActivityResultCallback<ActivityResult> {

        private final MainActivity mainActivity;

        public ActivityResultDetailHandler(MainActivity mainActivity) {
            this.mainActivity = mainActivity;
        }

        @Override
        public void onActivityResult(ActivityResult result) {

            if (result.getResultCode() == RESULT_OK) {
                Intent data = result.getData();
                MovieEntity movieObject;
                movieObject = constructMovieDataFromIntent(data);

                mainActivity.m.setMovieData(movieObject);
            }
        }
    }
}