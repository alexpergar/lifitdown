package com.aleperaltagar.simpleworkoutnotebook.DatabaseFiles;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.aleperaltagar.simpleworkoutnotebook.Exercise;

import java.util.List;

@Dao
public interface ExerciseDao {

    @Insert
    void insert(Exercise exercise);

    @Query("SELECT * FROM exercises")
    List<Exercise> getAllItems();

    @Query("SELECT * FROM exercises WHERE id=:id")
    Exercise getItemById(int id);

    @Query("UPDATE exercises SET sets=:sets WHERE id=:id")
    void updateSets(int id, String sets);
}
