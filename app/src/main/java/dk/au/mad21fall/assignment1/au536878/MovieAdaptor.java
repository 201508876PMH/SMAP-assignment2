package dk.au.mad21fall.assignment1.au536878;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import dk.au.mad21fall.assignment1.au536878.database.MovieEntity;

public class MovieAdaptor extends RecyclerView.Adapter<MovieAdaptor.MovieViewHolder> {

    //interface for handling when a Movie item is clicked in various ways
    public interface IMovieItemClickedListener{
        void onMovieClicked(int index);
    }

    //callback interface for movie actions on each item
    private IMovieItemClickedListener listener;
    
    //data in the adaptor
    private List<MovieEntity> movieList;


    //constructor
    public MovieAdaptor(IMovieItemClickedListener listener){
        this.listener = listener;
    }

    //a method for updating the list - causes the adaptor/recycleview to update
    public void updateMovieList(List<MovieEntity> lists){
        movieList = lists;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount(){
        return movieList.size();
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        MovieViewHolder vh = new MovieViewHolder(v, listener);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        holder.txtName.setText(movieList.get(position).getName());
        holder.txtYear.setText(movieList.get(position).getYear());
        holder.txtIMBD.setText(movieList.get(position).getUserRating());
        holder.imgIcon.setImageResource(movieList.get(position).getResourceIdFromGenre());
    }


    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        //viewholder ui widgets references
        ImageView imgIcon;
        TextView txtName;
        TextView txtYear;
        TextView txtIMBD;

        //custom callback interface for user actions done to the view holder item
        IMovieItemClickedListener listener;

        //contructor
        public MovieViewHolder(@NonNull View itemView,
                               IMovieItemClickedListener movieItemClickedListener) {
            super(itemView);
            imgIcon = itemView.findViewById(R.id.imageMovieIcon);
            txtName = itemView.findViewById(R.id.textMovieTitle);
            txtYear = itemView.findViewById(R.id.textMovieYear);
            txtIMBD = itemView.findViewById(R.id.list_item_MovieRating);
            listener = movieItemClickedListener;

            //set click listener for whole list item
            itemView.setOnClickListener(this);


        }

        //react to user clicking the listitem (implements OnClickListener)
        @Override
        public void onClick(View view) {
            listener.onMovieClicked(getAdapterPosition());
        }
    }


}
