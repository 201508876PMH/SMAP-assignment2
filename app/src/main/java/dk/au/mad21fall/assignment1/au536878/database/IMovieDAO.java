package dk.au.mad21fall.assignment1.au536878.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface IMovieDAO {
    @Query("SELECT * FROM MovieEntity")
    LiveData<List<MovieEntity>> getAll();

    @Query("SELECT * FROM MovieEntity WHERE name LIKE :name")
    LiveData<MovieEntity> findMovie(String name);

    @Query("SELECT * FROM MovieEntity WHERE genre LIKE :genre")
    List<MovieEntity> getMoviesFromGenre(String genre);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addMovie(MovieEntity movie);

    @Update
    void updateMovie(MovieEntity movie);

    @Delete
    void delete(MovieEntity movie);

    @Query("DELETE FROM MovieEntity")
    public void deleteAllMovies();
}
