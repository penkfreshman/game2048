package com.example.game2048;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class NewAppWidget extends AppWidgetProvider {


    private static RemoteViews views;
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        Log.d("pengkun","test12");



    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.appwidget_text);

        CharSequence widgetText1;
        // Construct the RemoteViews object
         views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
        Intent intent=new Intent(context,ScoreListActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 200, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        views.setOnClickPendingIntent(R.id.data_click,pendingIntent);
      //  views.setTextViewText(R.id.appwidget_text, widgetText);
        views.setTextViewText(R.id.data_Score, getNum(context.getApplicationContext())+"");
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them

        views.setTextViewText(R.id.data_Score, getNum(context.getApplicationContext())+"");
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);

            appWidgetManager.updateAppWidget(appWidgetId, views);
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
    @SuppressLint("Range")
    public  static int  getNum(Context context){
        Log.d("pengkun","1test1");
        int return_num=0;
        DataForList dataForList=new DataForList(context,Tool.List_name,null,1);
        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = dataForList.getReadableDatabase();
            cursor = db.rawQuery("select * from ScoreList order by score desc", null);
            cursor.moveToFirst();
            return_num=cursor.getInt(cursor.getColumnIndex(Tool.Save_Score));
            Log.d("pengkun","test1");
        }catch (Exception e){

        }finally {
            cursor.close();
            db.close();

        }
        return  return_num;
    }
}