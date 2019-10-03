package tech.march.submission1.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import tech.march.submission1.R;

/**
 * Implementation of App Widget functionality.
 */
public class FavoriteWidget extends AppWidgetProvider {

    public static final String EXTRA_ITEM = "tech.march.submission1.EXTRA_ITEM";
    private static final String REFRESH_ACTION = "tech.march.submission1.appwidget.action.REFRESH";

    private static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                        int appWidgetId) {

        Intent intent = new Intent(context, StackWidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        context.startService(intent);

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.favorite_widget);
        views.setRemoteAdapter(R.id.stack_view, intent);
        views.setEmptyView(R.id.stack_view, R.id.empty_view);

        Intent toastIntent = new Intent(context, FavoriteWidget.class);
        toastIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);

        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        PendingIntent toastPendingIntent = PendingIntent.getBroadcast(context, 0, toastIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.stack_view, toastPendingIntent);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    public static void sendRefreshBroadcast(Context context) {
        Intent intent = new Intent(REFRESH_ACTION);
        intent.setComponent(new ComponentName(context, FavoriteWidget.class));
        context.sendBroadcast(intent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() != null) {
            if (intent.getAction().equals(REFRESH_ACTION)) {
                AppWidgetManager mgr = AppWidgetManager.getInstance(context);
                ComponentName cn = new ComponentName(context, FavoriteWidget.class);
                mgr.notifyAppWidgetViewDataChanged(mgr.getAppWidgetIds(cn), R.id.stack_view);
            }
        }
        super.onReceive(context, intent);
    }
}
