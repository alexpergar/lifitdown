package com.aleperaltagar.simpleworkoutnotebook;

import android.content.Context;
import com.aleperaltagar.simpleworkoutnotebook.DatabaseFiles.ExercisesDatabase;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;

public class Utils {
    private static int ID_SET = 0;

    public static ArrayList<Exercise> getAllItems(Context context) {
        return (ArrayList<Exercise>) ExercisesDatabase.getInstance(context).exerciseDao().getAllItems();
    }

    public static ArrayList<Exercise> getUniqueItems(Context context)  {
        ArrayList<Exercise> uniqueExercises = new ArrayList<>();
        ArrayList<Exercise> exercises =  (ArrayList<Exercise>) ExercisesDatabase.getInstance(context).exerciseDao().getAllItems();

        // Look if it has been already added an exercise with the same name
        for (Exercise e : exercises) {
            boolean present = false;
            for (Exercise ue : uniqueExercises) {
                if (e.getName().equals(ue.getName())) {
                    present = true;
                    break;
                }
            }
            if (!present) {
                uniqueExercises.add(e);
            }
        }

        // Sort the exercises alphabetically
        uniqueExercises.sort(new Comparator<Exercise>() {
            @Override
            public int compare(Exercise o1, Exercise o2) {
                return o1.getName().compareToIgnoreCase(o2.getName());
            }
        });

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

    public static void deleteExercise(Context context, int exerciseId) {
        ExercisesDatabase.getInstance(context).exerciseDao().deleteById(exerciseId);
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

    public static int getSetID() {
        ID_SET++;
        return ID_SET;
    }
}
