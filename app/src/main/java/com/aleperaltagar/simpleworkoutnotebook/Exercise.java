package com.aleperaltagar.simpleworkoutnotebook;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.aleperaltagar.simpleworkoutnotebook.DatabaseFiles.SetsConverter;

import java.util.ArrayList;

@Entity(tableName = "exercises")
public class Exercise {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    @TypeConverters(SetsConverter.class)
    private ArrayList<Set> sets;

    public Exercise(String name, ArrayList<Set> sets) {
        this.name = name;
        this.sets = sets;
    }

    @Ignore
    public Exercise(String name) {
        this.name = name;
        this.sets = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Set> getSets() {
        return sets;
    }

    public void setSets(ArrayList<Set> sets) {
        this.sets = sets;
    }

    @Ignore
    @Override
    public String toString() {
        return "Exercise{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sets=" + sets +
                '}';
    }
}
