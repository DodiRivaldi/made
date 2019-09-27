package tech.march.submission1.alarm;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import java.util.Calendar;

import tech.march.submission1.R;
import tech.march.submission1.activity.main.MainActivity;

import static tech.march.submission1.utils.Helper.NOTIF_ID;
import static tech.march.submission1.utils.Helper.NOTIF_NAME;
import static tech.march.submission1.utils.Helper.REQ_DAILY;

public class DailyAlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        notif(context, context.getString(R.string.app_name));
    }

    private void notif(Context context, String title) {
        Intent i = new Intent(context, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        PendingIntent pendingIntent = TaskStackBuilder.create(context).
                addNextIntent(i).getPendingIntent(REQ_DAILY, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationManager manager = (NotificationManager) context.
                getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context).setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_search)
                .setContentText(context.getString(R.string.alarm_daily))
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setContentTitle(title)
                .setContentIntent(pendingIntent)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setSound(uri)
                .setAutoCancel(true);

        if (manager != null) {
            manager.notify(REQ_DAILY, builder.build());
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(
                    NOTIF_ID, NOTIF_NAME,
                    NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setVibrationPattern(new long[]{1000, 1000, 1000, 1000, 1000});
            notificationChannel.enableVibration(true);
            notificationChannel.setLightColor(Color.BLUE);
            builder.setChannelId(NOTIF_ID);
            if (manager != null) {
                manager.createNotificationChannel(notificationChannel);
            }
        }

    }

    public void stopAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, DailyAlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, REQ_DAILY, intent, 0);
        alarmManager.cancel(pendingIntent);
    }

    public void setupAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, DailyAlarmReceiver.class);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 15);
        calendar.set(Calendar.MINUTE, 10);
        calendar.set(Calendar.SECOND, 0);

        PendingIntent pendingIntent = PendingIntent.
                getBroadcast(context, REQ_DAILY, intent, 0);
        if (alarmManager != null) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }
    }
}
