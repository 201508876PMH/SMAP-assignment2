package dk.au.mad21fall.assignment1.au536878.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {MovieEntity.class}, version = 4)
public abstract class MovieDatabase extends RoomDatabase {
    public abstract IMovieDAO movieDao();    //mandatory DAO getter
    private static MovieDatabase instance;  //database instance for singleton

    //singleton pattern used, for lazy loading + single instance since db object is expensive
    public static MovieDatabase getDatabase(final Context context){
        if(instance == null){
            synchronized (MovieDatabase.class){
                if(instance == null){
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            MovieDatabase.class, "movie_database")
                            .fallbackToDestructiveMigration()
                            //.allowMainThreadQueries()
                            .build();
                }
            }
        }
        return instance;
    }
}
