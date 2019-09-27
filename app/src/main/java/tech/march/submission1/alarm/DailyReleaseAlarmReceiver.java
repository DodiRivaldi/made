package tech.march.submission1.alarm;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
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
import java.util.List;
import java.util.Locale;

import tech.march.submission1.R;
import tech.march.submission1.activity.main.MainActivity;
import tech.march.submission1.api.ApiRequest;
import tech.march.submission1.api.model.Movie;

public class DailyReleaseAlarmReceiver extends BroadcastReceiver {

    private static final String NOTIFICATION_CHANNEL_ID = "channel_01";
    private static final String NOTIFICATION_CHANNEL_NAME = "notifikasi_film";
    private static final int NOTIFICATION_ID = 101;

    private ApiRequest request;
    private ArrayList<Movie> movies = new ArrayList<>();
    private Context context;

    public DailyReleaseAlarmReceiver(Context context) {
        this.context=context;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        loadCurrDateMovies(context);
    }

    private void showNotification(Context context, String title, String message, int notifId) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, notifId, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Uri uriRingtone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_search)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setSound(uriRingtone)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setAutoCancel(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{1000, 1000, 1000, 1000, 1000});

            builder.setChannelId(NOTIFICATION_CHANNEL_ID);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }
        Notification notification = builder.build();
        if (notificationManager != null) {
            notificationManager.notify(notifId, notification);
        }

    }

    public void setRepeatingUpcomingAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), getPendingIntent(context));
        } else if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, getPendingIntent(context));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), getPendingIntent(context));
        }
    }

    public void cancelAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.cancel(getPendingIntent(context));
        }
    }

    private static PendingIntent getPendingIntent(Context context) {
        Intent intent = new Intent(context, DailyReleaseAlarmReceiver.class);
        return PendingIntent.getBroadcast(context, NOTIFICATION_ID, intent, PendingIntent.FLAG_CANCEL_CURRENT);
    }

    private void loadCurrDateMovies(final Context context) {
        request = ViewModelProviders.of((FragmentActivity) context).get(ApiRequest.class);
        request.getMovies().observe((LifecycleOwner) context, getMovie);
        request.setReleaseToday("EXTRA_MOVIE", getCurrDate());
    }


    private Observer<ArrayList<Movie>> getMovie = new Observer<ArrayList<Movie>>() {
        @Override
        public void onChanged(ArrayList<Movie> movies) {
            if (movies != null) {
                setupData(movies);
            }

        }
    };


    private void setupData(ArrayList<Movie> items) {
        int notifId = 0;
        movies.clear();
        movies.addAll(items);
        for (Movie film : movies) {
            if (film.getRelease_date().equals(getCurrDate())) {
                showNotification(context, film.getTitle(), context.getString(R.string.rilis), notifId);
                notifId++;
            }
        }
    }



    public static String getCurrDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
}