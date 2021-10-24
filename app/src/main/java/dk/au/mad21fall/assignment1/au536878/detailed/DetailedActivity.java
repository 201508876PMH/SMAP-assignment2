package dk.au.mad21fall.assignment1.au536878.detailed;

import static dk.au.mad21fall.assignment1.au536878.IntentTransferHelper.constructMovieDataFromIntent;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import dk.au.mad21fall.assignment1.au536878.IntentTransferHelper;
import dk.au.mad21fall.assignment1.au536878.R;
import dk.au.mad21fall.assignment1.au536878.database.MovieEntity;
import dk.au.mad21fall.assignment1.au536878.rating.RatingActivity;
import dk.au.mad21fall.assignment1.au536878.detailed.DetailedViewModel;

public class DetailedActivity extends AppCompatActivity {

    Button bttnBack, bttnRate;
    TextView movieTitle, movieYear, movieRating,
            movieGenre, moviePlot, userRating, userNotes;
    ImageView movieIcon;

    Bundle ratingActivityExtra;
    private DetailedViewModel m;
    Intent intent;

    ActivityResultLauncher<Intent> resultFromRatingActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_activity);
        intent = getIntent();

        instantiateUIFields();

        m = new ViewModelProvider(this).get(DetailedViewModel.class);
        m.instantiateMovieModel(getApplication(), intent.getStringExtra("index"));
        LiveData<MovieEntity> movie = m.getSpecificMovie(intent.getStringExtra("index"));

        movie.observe(this, new Observer<MovieEntity>() {
            @Override
            public void onChanged(MovieEntity movieEntity) {
                updateUIFields();
            }
        });

        resultFromRatingActivity = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),new ActivityResultRatingHandler(this));
    }

    protected void updateUIFields(){
        movieTitle.setText(m.getMovieObject().getName());
        movieYear.setText(m.getMovieObject().getYear());
        movieGenre.setText(m.getMovieObject().getGenre());
        movieRating.setText(m.getMovieObject().getMovieRating());
        moviePlot.setText(m.getMovieObject().getPlot());
        userRating.setText(m.getMovieObject().getUserRating());
        userNotes.setText(m.getMovieObject().getUserNotes());
        movieIcon.setImageResource(m.getMovieObject().getResourceIdFromGenre());
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
        Intent intentResult = IntentTransferHelper.prepareIntentFromMovieData(m.getMovieObject(), this, RatingActivity.class);
        resultFromRatingActivity.launch(intentResult);
    }

    protected void onBackClick(){
        Intent i = IntentTransferHelper.prepareIntentFromMovieData(m.getMovieObject());
        setResult(RESULT_OK,i);
        finish();
    }

    static class ActivityResultRatingHandler implements ActivityResultCallback<ActivityResult> {

        private final DetailedActivity detailedActivity;

        public ActivityResultRatingHandler(DetailedActivity detailedActivity) {
            this.detailedActivity = detailedActivity;
        }

        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == RESULT_OK) {
                Intent data = result.getData();
                MovieEntity movieObject;
                movieObject = constructMovieDataFromIntent(data);

                detailedActivity.m.setMovieData(movieObject);
                detailedActivity.ratingActivityExtra = data.getExtras();
            }
        }
    }
}
