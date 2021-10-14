package dk.au.mad21fall.assignment1.au536878;

import static dk.au.mad21fall.assignment1.au536878.IntentTransferHelper.constructMovieDataFromIntent;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class DetailedActivity extends AppCompatActivity {

    Button bttnBack, bttnRate;
    TextView movieTitle, movieYear, movieRating,
            movieGenre, moviePlot, userRating, userNotes;
    ImageView movieIcon;

    Bundle ratingActivityExtra;
    private MovieViewModel m;

    ActivityResultLauncher<Intent> resultFromRatingActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_activity);

        instantiateUIFields();

        Movie movie = IntentTransferHelper.constructMovieDataFromIntent(getIntent());

        m = new ViewModelProvider(this).get(MovieViewModel.class);
        m.instantiateMovieModel(movie);
        m.getMovieData().observe(this, new Observer<Movie>() {
            @Override
            public void onChanged(Movie movie) {
                updateUIFields();
            }
        });

        resultFromRatingActivity = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),new ActivityResultRatingHandler());
    }

    protected void updateUIFields(){
        movieTitle.setText(m.getMovieData().getValue().name);
        movieYear.setText(m.getMovieData().getValue().year);
        movieGenre.setText(m.getMovieData().getValue().genre);
        movieRating.setText(m.getMovieData().getValue().movieRating);
        moviePlot.setText(m.getMovieData().getValue().plot);
        userRating.setText(m.getMovieData().getValue().userRating);
        userNotes.setText(m.getMovieData().getValue().userNotes);
        movieIcon.setImageResource(m.getMovieData().getValue().getResourceIdFromGenre());
    }

    protected void instantiateUIFields(){
        movieTitle = findViewById(R.id.textMovieTitleDetailedView);
        movieYear = findViewById(R.id.textMovieYearDetailedView);
        movieGenre = findViewById(R.id.textMovieGenreDetailedView);
        movieRating = findViewById(R.id.textMovieRatingDetailedView );
        moviePlot = findViewById(R.id.textMoviePlotDetailedView);
        movieIcon = findViewById(R.id.MovieIconDetailedView);
        userRating = findViewById(R.id.details_activity_userRating);
        userNotes = findViewById(R.id.txtUsernotes);

        bttnBack = findViewById(R.id.bttnBack);
        bttnBack.setOnClickListener((view -> onBackClick()));

        bttnRate = findViewById(R.id.bttnRate);
        bttnRate.setOnClickListener((view -> onRateClick()));
    }

    protected void onRateClick(){
        Intent intentResult = IntentTransferHelper.prepareIntentFromMovieData(m.getMovieData().getValue(), this, RatingActivity.class);
        resultFromRatingActivity.launch(intentResult);
    }

    protected void onBackClick(){
        Intent i = IntentTransferHelper.prepareIntentFromMovieData(m.getMovieData().getValue());
        setResult(RESULT_OK,i);
        finish();
    }

    private class ActivityResultRatingHandler implements ActivityResultCallback<ActivityResult>{

        @Override
        public void onActivityResult(ActivityResult result) {
            if(result.getResultCode() == RESULT_OK) {
                Intent data = result.getData();
                Movie movieObject;
                movieObject = constructMovieDataFromIntent(data);

                m.setMovieData(movieObject);
                ratingActivityExtra = data.getExtras();
            }
        }
    }
}
