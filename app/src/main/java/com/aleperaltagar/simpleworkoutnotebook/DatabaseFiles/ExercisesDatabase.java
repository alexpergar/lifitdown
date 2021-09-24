package com.aleperaltagar.simpleworkoutnotebook.DatabaseFiles;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.aleperaltagar.simpleworkoutnotebook.Exercise;

@Database(entities = Exercise.class, version = 1)
public abstract class ExercisesDatabase extends RoomDatabase {

    private static ExercisesDatabase instance;
    private static RoomDatabase.Callback initialCallback = new RoomDatabase.Callback() {

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }
    };

    // Singleton pattern
    public static synchronized ExercisesDatabase getInstance(Context context) {
        if (null == instance) {
            instance = Room.databaseBuilder(context, ExercisesDatabase.class, "exercises_database")
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    public abstract ExerciseDao exerciseDao();
}
