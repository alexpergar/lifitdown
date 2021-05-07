package com.aleperaltagar.simpleworkoutnotebook;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.aleperaltagar.simpleworkoutnotebook.DatabaseFiles.ExercisesDatabase;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;

public class Utils {
    private static int ID_SET = 0;

    public static ArrayList<Exercise> getAllItems(Context context) {
        return (ArrayList<Exercise>) ExercisesDatabase.getInstance(context).exerciseDao().getAllItems();
    }

    public static boolean addExercise(Context context, Exercise exercise) {
        ExercisesDatabase.getInstance(context).exerciseDao().insert(exercise);
        return true;
    }


    public static boolean updateDatabase(Context context, int exerciseId, String name, ArrayList<Set> newSets) {
        Gson gson = new Gson();
        String newSetsJson = gson.toJson(newSets);
        ExercisesDatabase.getInstance(context).exerciseDao().updateSets(exerciseId, name, newSetsJson);
        return true;
    }

    public static ArrayList<Exercise> getItemsByDate(Context context, Calendar calendar) {
        return (ArrayList<Exercise>) ExercisesDatabase.getInstance(context).exerciseDao().getItemsByDate(calendar.getTimeInMillis());
    }

    public static int getSetID() {
        ID_SET++;
        return ID_SET;
    }
}
