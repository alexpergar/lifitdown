package com.aleperaltagar.simpleworkoutnotebook;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class Utils {

    private static final String TAG = "Utils";
    
    private static int ID = 0;

    private static final String DB_NAME = "fake_database";
    private static final String ALL_ITEMS_KEY = "all_items";
    private static Gson gson = new Gson();
    private static Type exercisesType = new TypeToken<ArrayList<Exercise>>() {}.getType();

    public static void initSharedPreferences(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
        ArrayList<Exercise> allItems = gson.fromJson(sharedPreferences.getString(ALL_ITEMS_KEY, null), exercisesType);
        if (null == allItems) {
            initAllItems(context);
            Log.i(TAG, "initSharedPreferences: Shared preferences initiated from zero");
        }
    }

    private static void initAllItems(Context context) {
        ArrayList<Exercise> allItems = new ArrayList<>();

        Exercise benchPress = new Exercise("Bench press");
        Set set1 = new Set(1);
        set1.setReps(10);
        set1.setWeight(20);
        ArrayList<Set> benchSets = new ArrayList<>();
        benchSets.add(set1);
        benchSets.add(set1);
        benchSets.add(set1);
        benchSets.add(set1);
        benchPress.setSets(benchSets);
        allItems.add(benchPress);


        Exercise pullUp = new Exercise("Pull up");
        pullUp.setSets(benchSets);
        allItems.add(pullUp);

        Exercise squat = new Exercise("Squat");
        squat.setSets(benchSets);
        allItems.add(squat);

        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ALL_ITEMS_KEY, gson.toJson(allItems));
        editor.commit();
    }

    public static ArrayList<Exercise> getAllItems(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
        ArrayList<Exercise> allItems = gson.fromJson(sharedPreferences.getString(ALL_ITEMS_KEY, null), exercisesType);
        return allItems;
    }

    public static boolean addExercise(Context context, Exercise exercise) {
        ArrayList<Exercise> allItems = getAllItems(context);
        if (null != allItems) {
            if (allItems.add(exercise)) {
                SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(DB_NAME);
                editor.putString(ALL_ITEMS_KEY, gson.toJson(allItems));
                editor.commit();
                return true;
            }
        }
        return false;
    }

    public static int getID() {
        ID++;
        return ID;
    }
}
