package com.aleperaltagar.simpleworkoutnotebook;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Utils {

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
        }
    }

    private static void initAllItems(Context context) {
        ArrayList<Exercise> allItems = new ArrayList<>();

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

    public static int getID() {
        ID++;
        return ID;
    }
}
