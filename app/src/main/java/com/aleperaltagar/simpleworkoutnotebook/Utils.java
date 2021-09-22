package com.aleperaltagar.simpleworkoutnotebook;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.aleperaltagar.simpleworkoutnotebook.DatabaseFiles.ExercisesDatabase;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;

import static android.content.ContentValues.TAG;

public class Utils {
    private static int ID_SET = 0;

    public static ArrayList<Exercise> getAllItems(Context context) {
        return (ArrayList<Exercise>) ExercisesDatabase.getInstance(context).exerciseDao().getAllItems();
    }

    public static ArrayList<String> getUniqueItemsString(Context context)  {
        ArrayList<String> uniqueExercises = new ArrayList<>();
        ArrayList<Exercise> exercises =  (ArrayList<Exercise>) ExercisesDatabase.getInstance(context).exerciseDao().getAllItems();

        // If there is no exercises, return an empty list of strings
        if (exercises.equals(new ArrayList<Exercise>())) {
            return new ArrayList<String>();
        }

        // Look if it has been already added an exercise with the same name
        for (Exercise e : exercises) {
            boolean present = false;
            for (String ue : uniqueExercises) {
                if (e.getName().equals(ue)) {
                    present = true;
                    break;
                }
            }
            if (!present) {
                uniqueExercises.add(e.getName());
            }
        }

        // Sort the exercises alphabetically
        uniqueExercises.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareToIgnoreCase(o2);
            }
        });

        if (uniqueExercises.get(0).equals("")) {
            uniqueExercises.remove(0);
        }

        return uniqueExercises;
    }

    public static boolean addExercise(Context context, Exercise exercise) {
        ExercisesDatabase.getInstance(context).exerciseDao().insert(exercise);
        return true;
    }

    public static void updateSets(Context context, int exerciseId, ArrayList<Set> newSets) {
        Gson gson = new Gson();
        String newSetsJson = gson.toJson(newSets);
        ExercisesDatabase.getInstance(context).exerciseDao().updateSets(exerciseId, newSetsJson);
    }

    public static void updateExerciseName(Context context, int exerciseId, String name) {
        ExercisesDatabase.getInstance(context).exerciseDao().updateExerciseName(exerciseId, name);
    }

    public static void updateEveryExerciseName(Context context, String newName, String oldName) {
        ExercisesDatabase.getInstance(context).exerciseDao().updateEveryExerciseName(newName, oldName);
    }

    public static void deleteExercise(Context context, int exerciseId) {
        ExercisesDatabase.getInstance(context).exerciseDao().deleteById(exerciseId);
    }

    public static void deleteExerciseByName(Context context, String exerciseName) {
        ExercisesDatabase.getInstance(context).exerciseDao().deleteByName(exerciseName);
    }

    public static Exercise getItemById(Context context, int id) {
        return ExercisesDatabase.getInstance(context).exerciseDao().getItemById(id);
    }

    public static ArrayList<Exercise> getItemsByDate(Context context, Calendar calendar) {
        return (ArrayList<Exercise>) ExercisesDatabase.getInstance(context).exerciseDao().getItemsByDate(calendar.getTimeInMillis());
    }

    public static ArrayList<Exercise> getItemsByName(Context context, String name) {
        return (ArrayList<Exercise>) ExercisesDatabase.getInstance(context).exerciseDao().getItemsByName(name);
    }

    public static Exercise getLastItem(Context context) {
        return ExercisesDatabase.getInstance(context).exerciseDao().getLastItem();
    }

    public static void updateSetNoteById(Context context, int exerciseId, int setId, String noteText) {
        Exercise exercise = ExercisesDatabase.getInstance(context).exerciseDao().getItemById(exerciseId);
        ArrayList<Set> newSets = new ArrayList<>();
        for (Set s : exercise.getSets()) {
            if (s.getId() == setId) {
                s.setNote(noteText);
            }
            newSets.add(s);
        }
        Utils.updateSets(context, exerciseId, newSets);
    }

    public static void deleteDatabase(Context context) {
        ExercisesDatabase.getInstance(context).exerciseDao().nukeTable();

        // Put sets ID back to -1
        SharedPreferences sharedPreferences = context.getSharedPreferences("prefs",0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("setId", -1);
        editor.commit();
    }

    public static void initiateSharedPreferences(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("prefs",0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (sharedPreferences.getBoolean("initiated", false) == false ) {
            editor.putBoolean("checkWeight", true);
            editor.putBoolean("checkReps", true);
            editor.putString("weightUnit", "kg");
            editor.putBoolean("initiated", true);
            editor.commit();
        }
    }


    public static int getSetID(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("prefs",0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        int value = sharedPreferences.getInt("setId", -1);
        value++;
        editor.putInt("setId", value);
        editor.commit();
        return value;
    }

    public static String getPositionInDay(Context context, Exercise exercise) {
        ArrayList<Exercise> thatDayExercises = getItemsByDate(context, exercise.getCalendar());
        int order = 0;
        for (Exercise e : thatDayExercises) {
            order++;
            if (e.getId() == exercise.getId()) {
                return String.valueOf(order);
            }
        }
        return "?";
    }
}
