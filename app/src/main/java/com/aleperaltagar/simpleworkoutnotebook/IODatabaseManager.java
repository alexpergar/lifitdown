package com.aleperaltagar.simpleworkoutnotebook;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import androidx.core.content.FileProvider;

import com.aleperaltagar.simpleworkoutnotebook.DatabaseFiles.SetsConverter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class IODatabaseManager {

    private Context context;

    private static final String TAG = "IODatabaseManager";

    public IODatabaseManager(Context context) {
        this.context = context;
    }

    public void exportDatabase() {
        try {
            String dateString = new SimpleDateFormat("yyyy_MM_dd").format(Calendar.getInstance().getTime());
            File path = context.getFilesDir();
            File file = new File(path, dateString + "_db.csv");
            FileOutputStream stream = new FileOutputStream(file);
            SetsConverter setsConverter = new SetsConverter();
            String textToFile = "";

            ArrayList<Exercise> wholeDatabase = Utils.getAllItems(context);
            for (Exercise e : wholeDatabase) {
                textToFile += e.getId() + ";" + e.getName() + ";" + e.getCalendar().getTimeInMillis() + ";" + setsConverter.setsToJson(e.getSets()) + ";\n";
            }
            stream.write(textToFile.getBytes());
            stream.close();

            Uri uri = FileProvider.getUriForFile(context, "com.aleperaltagar.simpleworkoutnotebook.provider", file);

            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_STREAM, uri);
            sendIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            sendIntent.setType("text/plain");

            Intent shareIntent = Intent.createChooser(sendIntent, "Share database");
            shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(shareIntent);

        } catch (IOException error) {

        }

    }

}
