package com.aleperaltagar.simpleworkoutnotebook.DatabaseFiles;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import com.aleperaltagar.simpleworkoutnotebook.Exercise;
import com.aleperaltagar.simpleworkoutnotebook.Set;
import com.aleperaltagar.simpleworkoutnotebook.Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class IODatabaseManager {

    private static final String TAG = "IODatabaseManager";
    private Context context;

    public IODatabaseManager(Context context) {
        this.context = context;
    }

    public void exportDatabase() {
        try {
            // Get the current date for the file name
            String dateString = new SimpleDateFormat("yyyy_MM_dd").format(Calendar.getInstance().getTime());

            // Create the file in the local files directory
            File path = context.getFilesDir();
            File file = new File(path, dateString + "_db.txt");
            FileOutputStream stream = new FileOutputStream(file);

            // Set the converted to convert the sets to json
            SetsConverter setsConverter = new SetsConverter();

            // Load the whole database in a variable
            ArrayList<Exercise> wholeDatabase = Utils.getAllItems(context);

            // Save every exercise in a string variable
            String textToFile = "";
            for (Exercise e : wholeDatabase) {
                textToFile += e.getId() + "\t" + e.getName() + "\t" + e.getCalendar().getTimeInMillis() + "\t" + setsConverter.setsToJson(e.getSets()) + "\n";
            }
            stream.write(textToFile.getBytes());
            stream.close();

            // Prepare the file provider with the file URI
            Uri uri = FileProvider.getUriForFile(context, "com.aleperaltagar.simpleworkoutnotebook.provider", file);

            // Create and launch the intent to share the file
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_STREAM, uri);
            sendIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            sendIntent.setType("text/plain");
            Intent shareIntent = Intent.createChooser(sendIntent, "Share database");
            shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(shareIntent);
        } catch (IOException error) {
            Log.d(TAG, "exportDatabase: " + error);
        }
    }

    public void importDatabase(Context context, ArrayList<String> imported) {
        ArrayList<Exercise> newExercises = new ArrayList<>();
        SetsConverter setsConverter = new SetsConverter();
        try {
            for (String stringExercise : imported) {
                String[] split = stringExercise.split("\t");
                String name = split[1];
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(Long.parseLong(split[2]));
                ArrayList<Set> sets = setsConverter.jsonToSets(split[3]);
                ArrayList<Set> setsNewId = changeSetsId(sets);
                Exercise newExercise = new Exercise(name, calendar, setsNewId);
                newExercises.add(newExercise);
            }
            for (Exercise exercise : newExercises) {
                Utils.addExercise(context, exercise);
            }

            Toast.makeText(context, "Database imported successfully", Toast.LENGTH_SHORT).show();
            
        } catch (Exception e) {
            Toast.makeText(context, "This file is not supported", Toast.LENGTH_SHORT).show();
        }

    }

    private ArrayList<Set> changeSetsId(ArrayList<Set> sets) {
        ArrayList<Set> newSets = new ArrayList<>();
        for (Set set : sets) {
            set.setId(Utils.getSetID(context));
            newSets.add(set);
        }
        return newSets;
    }

}
