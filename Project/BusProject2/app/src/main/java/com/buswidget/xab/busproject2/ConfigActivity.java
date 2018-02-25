package com.buswidget.xab.busproject2;

import android.Manifest;
import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.ArrayList;
import java.util.regex.Pattern;


public class ConfigActivity extends Activity {

    int widgetID = AppWidgetManager.INVALID_APPWIDGET_ID;
    Intent resultValue;

    public final static String WIDGET_TEXT = "widget_text_";
    public final static String WIDGET_PREF = "widget_pref";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                != PackageManager.PERMISSION_GRANTED){
//            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1001);
//        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1001);
        }

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            widgetID = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        if (widgetID == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
        }

        resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetID);

        setResult(RESULT_CANCELED, resultValue);

        setContentView(R.layout.activity_config);
    }


    public void onClick(View v) {
//        SharedPreferences sp = getSharedPreferences(WIDGET_PREF, MODE_PRIVATE);
//        SharedPreferences.Editor editor = sp.edit();
//
//        //
//        InputStream inputStream = getResources().openRawResource(R.raw.test4);
//        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
//        //
        new MaterialFilePicker()
                .withActivity(ConfigActivity.this)
                .withRequestCode(1000)
//                .withFilter(Pattern.compile(".*\\.csv$")) // Filtering files and directories by file name using regexp
//                .withFilterDirectories(true) // Set directories filterable (false by default)
                .withHiddenFiles(true) // Show hidden files and folders
                .start();

//        String cavLine;
//        try {
//            StringBuilder stringBuilder = new StringBuilder();
//            while((cavLine = reader.readLine()) != null){
//                stringBuilder.append(cavLine);
//                stringBuilder.append(",");
//            }
//            editor.putString(WIDGET_TEXT + widgetID, stringBuilder.toString());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        editor.commit();
//
//        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
//        MyWidget.updateAppWidget(this, appWidgetManager, sp, widgetID);
//
//        setResult(RESULT_OK, resultValue);
//
//        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1000 && resultCode == RESULT_OK) {
            String filePath = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
            Log.d("filePath", filePath);
            // Do anything with file
            SharedPreferences sp = getSharedPreferences(WIDGET_PREF, MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();

            //
            FileInputStream inputStream = null;
            try {
                inputStream = new FileInputStream(filePath);
            } catch (FileNotFoundException e) {
                //e.printStackTrace();
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String cavLine;
            try {
                StringBuilder stringBuilder = new StringBuilder();
                while((cavLine = reader.readLine()) != null){
                    stringBuilder.append(cavLine);
                    stringBuilder.append(",");
                }
                editor.putString(WIDGET_TEXT + widgetID, stringBuilder.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            editor.commit();

            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
            MyWidget.updateAppWidget(this, appWidgetManager, sp, widgetID);

            setResult(RESULT_OK, resultValue);

            finish();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

    }
}
