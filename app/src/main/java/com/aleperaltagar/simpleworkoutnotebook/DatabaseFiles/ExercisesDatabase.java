package com.aleperaltagar.simpleworkoutnotebook.DatabaseFiles;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.aleperaltagar.simpleworkoutnotebook.Exercise;
import com.aleperaltagar.simpleworkoutnotebook.Set;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = Exercise.class, version = 1)
public abstract class ExercisesDatabase extends RoomDatabase {

    public abstract ExerciseDao exerciseDao();
    private static ExercisesDatabase instance;

    // Singleton pattern
    public static synchronized ExercisesDatabase getInstance(Context context) {
        if (null == instance) {
            instance = Room.databaseBuilder(context, ExercisesDatabase.class, "exercises_database")
                    .fallbackToDestructiveMigration() // remember to delete this
                    .allowMainThreadQueries()
                    .addCallback(initialCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback initialCallback = new RoomDatabase.Callback() {

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            Executors.newSingleThreadExecutor().execute(new FirstInsert(instance));
        }
    };

    private static class FirstInsert implements Runnable {

        private ExerciseDao exerciseDao;

        public FirstInsert(ExercisesDatabase db) {
            this.exerciseDao = db.exerciseDao();
        }

        @Override
        public void run() {
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

            for (Exercise e : allItems) {
                exerciseDao.insert(e);
            }
        }
    }


}
