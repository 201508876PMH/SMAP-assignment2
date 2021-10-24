package dk.au.mad21fall.assignment1.au536878.rating;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import dk.au.mad21fall.assignment1.au536878.IntentTransferHelper;
import dk.au.mad21fall.assignment1.au536878.R;
import dk.au.mad21fall.assignment1.au536878.database.MovieEntity;

public class RatingActivity extends AppCompatActivity {

    TextView movieTitle, userRating, multilineText;
    ImageView movieIcon;
    Button cancelbttn, bttnOK;

    SeekBar seekbar;
    int progress = 0;

    private RatingViewModel m;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);
        intent = getIntent();

        instantiateUIFields();

        m = new ViewModelProvider(this).get(RatingViewModel.class);
        m.instantiateMovieModel(getApplication(), intent.getStringExtra("index"));
        LiveData<MovieEntity> movie = m.getMovieObjectAsLiveData();

        movie.observe(this, new Observer<MovieEntity>() {
            @Override
            public void onChanged(MovieEntity movieEntity) {
                updateUI();
            }
        });
    }

    protected void instantiateUIFields(){
        movieTitle = findViewById(R.id.activity_rating_title);
        userRating = findViewById(R.id.activity_rating_userRating);
        cancelbttn = findViewById(R.id.cancelBttn);
        bttnOK = findViewById(R.id.bttnOK);
        movieIcon = findViewById(R.id.activity_rating_icon);
        multilineText = findViewById(R.id.editTextTextMultiLine);
        seekbar = findViewById(R.id.activity_rating_seekbar);

        cancelbttn.setOnClickListener((view -> cancelbttn()));
        bttnOK.setOnClickListener((view -> bttnOKClick()));

        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                m.getMovieObject().setUserRating(String.valueOf(i));
                updateUI();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        multilineText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                m.getMovieObject().setUserNotes(editable.toString());
                //m.getSpecificMovie() = editable.toString();
            }
        });
    }

    private void updateUI() {
        userRating.setText(String.valueOf(m.getMovieObject().getUserRating()));
        movieTitle.setText(m.getMovieObject().getName());
        movieIcon.setImageResource(m.getMovieObject().getResourceIdFromGenre());

        if(m.getMovieObject().getUserRating().equals("X")){
            progress = 0;
        }else{
            progress = Integer.parseInt(m.getMovieObject().getUserRating());
        }
        multilineText.setText(m.getMovieObject().getUserNotes());
    }

    protected void bttnOKClick(){
        Intent i = IntentTransferHelper.prepareIntentFromMovieData(m.getMovieObject());
        setResult(RESULT_OK, i);
        finish();
    }
    protected void cancelbttn(){
        Intent i = new Intent();
        setResult(RESULT_CANCELED,i);
        finish();
    }
}
