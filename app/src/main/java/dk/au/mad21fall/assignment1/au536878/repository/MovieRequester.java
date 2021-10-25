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
import com.google.gson.JsonObject;

import org.json.JSONObject;

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
                parseJSON(response, context);
                Log.d("MovieRequester","onResponse: " + response);
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

    private void parseJSON(String json, Context context){
        Gson gsonBuilder = new GsonBuilder().create();
        JsonObject jsonObject = gsonBuilder.fromJson(json, JsonObject.class);
        try{
            if(jsonObject.has("Error")){
                Toast.makeText(context, "Movie can't be found", Toast.LENGTH_SHORT).show();
            }else{
                Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
                MovieEntity movie = gson.fromJson(json, MovieEntity.class);

                if(movie.getGenre().split(",").length > 1){
                    movie.setGenre(movie.getGenre().split(",")[0]);
                }
                movie.setUserRating("X");
                movie.setUserNotes(" ");
                repository.addMovie(movie);
                Toast.makeText(context, "Movie added!", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            Toast.makeText(context, "An error occurred: " + e, Toast.LENGTH_SHORT).show();
        }

    }
}
