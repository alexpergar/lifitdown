package com.aleperaltagar.simpleworkoutnotebook.DatabaseFiles;

import androidx.room.TypeConverter;

import com.aleperaltagar.simpleworkoutnotebook.Set;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class SetsConverter {
    @TypeConverter
    public String setsToJson (ArrayList<Set> sets) {
        Gson gson = new Gson();
        return gson.toJson(sets);
    }

    @TypeConverter
    public ArrayList<Set> jsonToSets (String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Set>>() {}.getType();
        return gson.fromJson(json, type);
    }
}
