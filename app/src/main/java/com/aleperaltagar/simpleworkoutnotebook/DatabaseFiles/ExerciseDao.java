package com.aleperaltagar.simpleworkoutnotebook.DatabaseFiles;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.aleperaltagar.simpleworkoutnotebook.Exercise;

import java.util.Calendar;
import java.util.List;

@Dao
public interface ExerciseDao {

    @Query("DELETE FROM exercises")
    public void nukeTable();

    @Insert
    void insert(Exercise exercise);

    @Query("SELECT * FROM exercises")
    List<Exercise> getAllItems();

    @Query("SELECT * FROM exercises WHERE id=:id")
    Exercise getItemById(int id);

    @Query("UPDATE exercises SET sets=:sets WHERE id=:id")
    void updateSets(int id, String sets);

    @Query("UPDATE exercises SET name=:name WHERE id=:id")
    void updateExerciseName(int id, String name);

    @Query("DELETE FROM exercises WHERE id=:id")
    void deleteById(int id);

    @Query("SELECT * FROM exercises WHERE calendar=:calendarInLong")
    List<Exercise> getItemsByDate(long calendarInLong);

    @Query("SELECT * FROM exercises WHERE name=:name ORDER BY calendar DESC")
    List<Exercise> getItemsByName(String name);

    @Query("SELECT * FROM exercises ORDER BY id DESC LIMIT 1")
    Exercise getLastItem();
}
