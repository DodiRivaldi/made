package tech.march.submission1.alarm;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.View;

import androidx.core.app.NotificationCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import tech.march.submission1.R;
import tech.march.submission1.activity.detail.DetailActivity;
import tech.march.submission1.activity.main.MainActivity;
import tech.march.submission1.activity.search.SearchActivity;
import tech.march.submission1.api.ApiRequest;
import tech.march.submission1.api.model.Movie;

import static tech.march.submission1.BuildConfig.API_KEY;
import static tech.march.submission1.utils.Helper.NOTIF_ID;

public class DailyReleaseAlarmReceiver extends BroadcastReceiver {
    private final int REQUEST_CODE_RELEASE = 13;
    private ApiRequest request;

    @Override
    public void onReceive(Context context, Intent intent) {

    }

    public void setAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        // TIME SET HERE
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        Intent intent = new Intent(context, DailyReleaseAlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, REQUEST_CODE_RELEASE, intent, 0);

        if (alarmManager != null) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }
    }

    public void cancelAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, DailyReleaseAlarmReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, REQUEST_CODE_RELEASE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        pendingIntent.cancel();

        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
    }

    private void showNotification(Context context, String title, String message, int id) {
        try {

            String CHANNEL_ID = "channel_2";
            String CHANNEL_NAME = "Release";

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            Intent intent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_search)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                    .setSound(sound)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true);


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
                builder.setChannelId(CHANNEL_ID);
                if (notificationManager != null) {
                    notificationManager.createNotificationChannel(channel);
                }
            }

            Notification notification = builder.build();
            notificationManager.notify(id, notification);
        } catch (Exception e) {
            Log.d("showNotif ", e.getMessage());
        }
    }

    private void getRelease(final Context context  ,String message) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        Date date = new Date();
        String today = dateFormat.format(date);
        request = ViewModelProviders.of((FragmentActivity) context).get(ApiRequest.class);
        request.getMovies().observe((FragmentActivity) context, getMovie);
        request.setMoviesSearch("EXTRA_MOVIE",today);

    }

    private Observer<ArrayList<Movie>> getMovie = new Observer<ArrayList<Movie>>() {
        @Override
        public void onChanged(ArrayList<Movie> movies) {
            if (movies != null) {
                //adapterMovie.setupData(movies);
                showNotification(getApplicationContext, movieTitle, message, ID_RELEASE);

            }

        }
    };
}
