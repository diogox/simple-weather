package com.diogox.simpleweather;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import com.evernote.android.job.JobManager;

public class App extends Application {
    public static final String CHANNEL_ID = "weatherServiceChannel";

    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationChannel();
        JobManager.create(this).addJobCreator(new AlertJobCreator());
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Example Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }
}
