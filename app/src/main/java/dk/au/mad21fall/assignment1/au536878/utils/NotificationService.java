package dk.au.mad21fall.assignment1.au536878.utils;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.view.ContentInfoCompat;

public class NotificationService extends Service {

    private static final String channel = "546";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId ){
        Notification notification = new NotificationCompat.Builder(this, channel)
                .setContentTitle("Movie suggester")
    }
}
