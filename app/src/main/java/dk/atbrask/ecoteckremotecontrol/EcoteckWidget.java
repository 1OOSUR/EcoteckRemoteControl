package dk.atbrask.ecoteckremotecontrol;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.RemoteViews;

/**
 * Ecoteck Remote Control
 */
public class EcoteckWidget extends AppWidgetProvider
{
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds)
    {
        refreshButtonListeners(context);
    }

    public static void refreshButtonListeners(Context context)
    {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.ecoteck_widget);
        remoteViews.setOnClickPendingIntent(R.id.btnTempUp, createIntent(context, "dk.atbrask.EcoteckWidget.intent.action.TEMPERATURE_UP"));
        remoteViews.setOnClickPendingIntent(R.id.btnTempDown, createIntent(context, "dk.atbrask.EcoteckWidget.intent.action.TEMPERATURE_DOWN"));
        remoteViews.setOnClickPendingIntent(R.id.btnFireUp, createIntent(context, "dk.atbrask.EcoteckWidget.intent.action.FIRE_UP"));
        remoteViews.setOnClickPendingIntent(R.id.btnFireDown, createIntent(context, "dk.atbrask.EcoteckWidget.intent.action.FIRE_DOWN"));
        remoteViews.setOnClickPendingIntent(R.id.btnOnOff, createIntent(context, "dk.atbrask.EcoteckWidget.intent.action.ON_OFF"));
        remoteViews.setOnClickPendingIntent(R.id.btnConfirmOnOff, createIntent(context, "dk.atbrask.EcoteckWidget.intent.action.CONFIRM_ON_OFF"));
        remoteViews.setOnClickPendingIntent(R.id.btnCancelOnOff, createIntent(context, "dk.atbrask.EcoteckWidget.intent.action.CANCEL_ON_OFF"));
        pushWidgetUpdate(context, remoteViews);
    }

    public static PendingIntent createIntent(Context context, String intentText)
    {
        Intent intent = new Intent();
        intent.setAction(intentText);
        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public static void pushWidgetUpdate(Context context, RemoteViews remoteViews)
    {
        ComponentName myWidget = new ComponentName(context, EcoteckWidget.class);
        AppWidgetManager manager = AppWidgetManager.getInstance(context);
        manager.updateAppWidget(myWidget, remoteViews);
    }

    public static void showConfirmScreen(Context context)
    {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.ecoteck_widget);
        remoteViews.setViewVisibility(R.id.colTemperature, View.INVISIBLE);
        remoteViews.setViewVisibility(R.id.colOnOff, View.GONE);
        remoteViews.setViewVisibility(R.id.colFire, View.INVISIBLE);
        remoteViews.setViewVisibility(R.id.colConfirmOnOff, View.VISIBLE);
        pushWidgetUpdate(context, remoteViews);
    }

    public static void showMainScreen(Context context)
    {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.ecoteck_widget);
        remoteViews.setViewVisibility(R.id.colTemperature, View.VISIBLE);
        remoteViews.setViewVisibility(R.id.colOnOff, View.VISIBLE);
        remoteViews.setViewVisibility(R.id.colFire, View.VISIBLE);
        remoteViews.setViewVisibility(R.id.colConfirmOnOff, View.GONE);
        pushWidgetUpdate(context, remoteViews);
    }

    @Override
    public void onEnabled(Context context)
    {
        // Enter relevant functionality for when the last widget is enabled
    }

    @Override
    public void onDisabled(Context context)
    {
        // Enter relevant functionality for when the last widget is disabled
    }
}


