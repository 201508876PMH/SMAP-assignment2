package dk.au.mad21fall.assignment1.au536878.repository;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dk.au.mad21fall.assignment1.au536878.database.MovieEntity;

public class MovieRequester {
    private static final String URL_PREFIX = "https://www.omdbapi.com/?t=";
    private static final String URL_POSTFIX = "&apikey=abf5ebe0";

    private Repository repository;
    RequestQueue queue;

    public MovieRequester(Repository repository) {
        this.repository = repository;
    }



    public void requestMovie(String movieName, Context context){
        String movieURL = URL_PREFIX + movieName.replace(" ","%20") + URL_POSTFIX;
        if(queue == null){
            queue = Volley.newRequestQueue(context);
        }

        StringRequest stringRequest = new StringRequest(Request.Method.GET, movieURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                parseJSON(response);
                Log.d("MovieRequester","onResponse: " + response);
                Toast.makeText(context, "Movie " + movieName + " added!", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("MovieRequester","That sadly didn't work");
                Toast.makeText(context, "That sadly didn't work", Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(stringRequest);
    }

    private void parseJSON(String json){
        Gson gson = new GsonBuilder().create();
        MovieEntity movie = gson.fromJson(json, MovieEntity.class);
        repository.addMovie(movie);
    }
}
