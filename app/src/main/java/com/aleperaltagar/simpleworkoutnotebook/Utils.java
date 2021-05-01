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
    
    private static int ID_EXERCISE = 0;
    private static int ID_SET = 0;

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
        Set set1bench = new Set(1);
        Set set2bench = new Set(1);
        Set set3bench = new Set(1);
        set1bench.setReps("10");
        set1bench.setWeight("20");
        set2bench.setReps("20");
        set2bench.setWeight("30");
        set3bench.setReps("30");
        set3bench.setWeight("40");
        ArrayList<Set> benchSets = new ArrayList<>();
        benchSets.add(set1bench);
        benchSets.add(set2bench);
        benchSets.add(set3bench);
        benchPress.setSets(benchSets);
        allItems.add(benchPress);


        Exercise pullUp = new Exercise("Pull up");
        Set set1pullUp = new Set(2);
        Set set2pullUp = new Set(2);
        Set set3pullUp = new Set(2);
        set1pullUp.setReps("100");
        set1pullUp.setWeight("200");
        set2pullUp.setReps("200");
        set2pullUp.setWeight("300");
        set3pullUp.setReps("300");
        set3pullUp.setWeight("400");
        ArrayList<Set> pullUpSets = new ArrayList<>();
        pullUpSets.add(set1pullUp);
        pullUpSets.add(set2pullUp);
        pullUpSets.add(set3pullUp);
        pullUp.setSets(pullUpSets);
        allItems.add(pullUp);


        Exercise squat = new Exercise("Squat");
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

    public static boolean addSet(Context context, int exerciseId) {
        ArrayList<Exercise> allItems = getAllItems(context);
        if (null != allItems) {
            for (Exercise e : allItems) {
                if (e.getId() == exerciseId) {
                    int exerciseIndex = allItems.indexOf(e);
                    ArrayList<Set> newSets = allItems.get(exerciseIndex).getSets();
                    if (null != newSets) {
                        if (newSets.add(new Set(exerciseId))) {
                            SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            allItems.get(exerciseIndex).setSets(newSets);
                            editor.remove(DB_NAME);
                            editor.putString(ALL_ITEMS_KEY, gson.toJson(allItems));
                            editor.commit();
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public static boolean deleteSet(Context context, int exerciseId, int setId) {
        ArrayList<Exercise> allItems = getAllItems(context);
        if (null != allItems) {
            for (Exercise e : allItems) {
                if (e.getId() == exerciseId) {
                    int exerciseIndex = allItems.indexOf(e);
                    ArrayList<Set> newSets = allItems.get(exerciseIndex).getSets();
                    if (null != newSets) {
                        for (Set set : newSets) {
                            if (set.getId() == setId) {
                                SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                newSets.remove(set);
                                allItems.get(exerciseIndex).setSets(newSets);
                                editor.remove(DB_NAME);
                                editor.putString(ALL_ITEMS_KEY, gson.toJson(allItems));
                                editor.commit();
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public static boolean updateDatabase(Context context, int exerciseId, ArrayList<Set> newSets) {
        ArrayList<Exercise> allItems = getAllItems(context);
        if (null != allItems) {
            for (Exercise e : allItems) {
                if (e.getId() == exerciseId) {
                    int exerciseIndex = allItems.indexOf(e);
                    SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    allItems.get(exerciseIndex).setSets(newSets);
                    editor.remove(DB_NAME);
                    editor.putString(ALL_ITEMS_KEY, gson.toJson(allItems));
                    editor.commit();
                    return true;
                }
            }
        }
            return false;
    }

    public static int getExerciseID() {
        ID_EXERCISE++;
        return ID_EXERCISE;
    }

    public static int getSetID() {
        ID_SET++;
        return ID_SET;
    }
}
