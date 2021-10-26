package dk.au.mad21fall.assignment1.au536878.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.view.ContentInfoCompat;
import androidx.lifecycle.LiveData;
import androidx.test.annotation.UiThreadTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import dk.au.mad21fall.assignment1.au536878.R;
import dk.au.mad21fall.assignment1.au536878.database.MovieEntity;
import dk.au.mad21fall.assignment1.au536878.main.MainActivity;
import dk.au.mad21fall.assignment1.au536878.repository.Repository;

public class NotificationService extends Service {

    private static final String channel = "546";
    private ExecutorService executorService;    //allows for methods to be off-loaded Mainthread
    private Repository repository;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId ) {
        createNotificationChannel();
        executorService = Executors.newSingleThreadExecutor();
        repository = Repository.getRepositoryInstance(getApplication());

        Intent intent1 = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent1, 0);

        Notification notification = new NotificationCompat.Builder(this, channel)
                .setContentTitle("Movie suggester")
                .setContentText("This is a test")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent)
                .build();

        startForeground(1, notification);

        startRandomMovieSuggester();

       return START_NOT_STICKY;
    }

    private void createNotificationChannel() {
        //Check if OS is oreo or above
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(channel,"Foreground notification", NotificationManager.IMPORTANCE_DEFAULT);

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(notificationChannel);
        }
    }


    private void startRandomMovieSuggester(){
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                while(true){
                    try {
                        Thread.sleep(20000);
                        postToastOnMainThread();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void postToastOnMainThread(){
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                    try {
                        LiveData<List<MovieEntity>>  movieList = repository.getMovies();
                        Random rn = new Random();
                        int randomNum = rn.nextInt(movieList.getValue().size());

                        Toast.makeText(getApplicationContext(),
                                "Try the movie: " + movieList.getValue().get(randomNum).getName() + ", year: " + movieList.getValue().get(randomNum).getYear(),
                                Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            }
        });
    }

    @Override
    public void onDestroy(){
        stopForeground(true);
        stopSelf();
        super.onDestroy();
    }
}
