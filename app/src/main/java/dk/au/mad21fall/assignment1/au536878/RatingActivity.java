package dk.au.mad21fall.assignment1.au536878;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class RatingActivity extends AppCompatActivity {

    TextView movieTitle, userRating, multilineText;
    ImageView movieIcon;
    Button cancelbttn, bttnOK;

    SeekBar seekbar;
    int progress = 0;

    Bundle extras;
    private MovieViewModel m;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);
        extras = getIntent().getExtras();

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
                m.getMovieData().getValue().userRating = String.valueOf(i);
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
                m.getMovieData().getValue().userNotes = editable.toString();
            }
        });
    }

    protected void updateUIFields(){
        movieTitle.setText(m.getMovieData().getValue().name);
        movieIcon.setImageResource(m.getMovieData().getValue().getResourceIdFromGenre());

        if(m.getMovieData().getValue().userRating.equals("X")){
            progress = 0;
        }else{
            progress = Integer.parseInt(m.getMovieData().getValue().userRating);
        }
        multilineText.setText(m.getMovieData().getValue().userNotes);
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateUI();
    }

    private void updateUI() {
        userRating.setText(String.valueOf(m.getMovieData().getValue().userRating));
    }

    protected void bttnOKClick(){
        Intent i = IntentTransferHelper.prepareIntentFromMovieData(m.getMovieData().getValue());
        setResult(RESULT_OK, i);
        finish();
    }
    protected void cancelbttn(){
        Intent i = new Intent();
        setResult(RESULT_CANCELED,i);
        finish();
    }
}
