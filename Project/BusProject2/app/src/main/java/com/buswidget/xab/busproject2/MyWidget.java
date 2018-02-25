package com.buswidget.xab.busproject2;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.RemoteViews;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import com.buswidget.xab.busproject2.TimeFunction;

/**
 * Implementation of App Widget functionality.
 */
public class MyWidget extends AppWidgetProvider {


    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                SharedPreferences sp, int appWidgetId) {

        String widgetText = sp.getString(ConfigActivity.WIDGET_TEXT + appWidgetId, null);
        if (widgetText == null) return;

        String[] itemsWords = widgetText.split(",");
        ArrayList<Integer> items = new ArrayList<>();
        ArrayList<String> items2 = new ArrayList<>();
        String nameTable = new String(itemsWords[0]);
        for (int i=1; i < itemsWords.length; i++) {
            items.add(TimeFunction.ConvertTimeToInt(itemsWords[i]));
            items2.add(itemsWords[i]);
        }
        Collections.sort(items);
        Collections.sort(items2);

        String timeFormat = new String("HH:mm");
        SimpleDateFormat sdf = new SimpleDateFormat(timeFormat);
        String currentTime = sdf.format(new Date(System.currentTimeMillis()));
        Integer currentTimeInt = TimeFunction.ConvertTimeToInt(currentTime);

        int indexLast = TimeFunction.FoundBusTime(items, currentTimeInt, TimeFunction.LAST);
        int indexNext = TimeFunction.FoundBusTime(items, currentTimeInt, TimeFunction.NEXT);
        int indexAfterNext = TimeFunction.FoundBusTime(items, currentTimeInt, TimeFunction.AFTER_NEXT);

        Integer LastDiffTime = items.get(indexLast) - currentTimeInt;
        Integer NextDiffTime = TimeFunction.CalcDiffOfTime(items.get(indexNext), currentTimeInt);
        Integer AfterNextDiffTime = TimeFunction.CalcDiffOfTime(items.get(indexAfterNext), currentTimeInt);

        RemoteViews widgetView = new RemoteViews(context.getPackageName(),
                R.layout.my_widget);
        widgetView.setTextViewText(R.id.text_time, nameTable);
        widgetView.setTextViewText(R.id.appwidget_text1, items2.get(indexLast));
        widgetView.setTextViewText(R.id.appwidget_text2, items2.get(indexNext));
        widgetView.setTextViewText(R.id.appwidget_text3, items2.get(indexAfterNext));
        widgetView.setTextViewText(R.id.appwidget_text1_2, TimeFunction.ConvertIntToTime(LastDiffTime));
        widgetView.setTextViewText(R.id.appwidget_text2_2, TimeFunction.ConvertIntToTime(NextDiffTime));
        widgetView.setTextViewText(R.id.appwidget_text3_2, TimeFunction.ConvertIntToTime(AfterNextDiffTime));


        Intent updateIntent = new Intent(context, MyWidget.class);
        updateIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        updateIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,
                new int[] { appWidgetId });
        PendingIntent pIntent = PendingIntent.getBroadcast(context, appWidgetId, updateIntent, 0);
        widgetView.setOnClickPendingIntent(R.id.but_update, pIntent);

        appWidgetManager.updateAppWidget(appWidgetId, widgetView);
    }



    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // Удаляем Preferences
        SharedPreferences.Editor editor = context.getSharedPreferences(
                ConfigActivity.WIDGET_PREF, Context.MODE_PRIVATE).edit();
        for (int widgetID : appWidgetIds) {
            editor.remove(ConfigActivity.WIDGET_TEXT + widgetID);
        }
        editor.commit();
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        SharedPreferences sp = context.getSharedPreferences(
                ConfigActivity.WIDGET_PREF, Context.MODE_PRIVATE);
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, sp, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

