package com.aleperaltagar.simpleworkoutnotebook;

import android.util.Log;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.aleperaltagar.simpleworkoutnotebook.DatabaseFiles.CalendarConverter;
import com.aleperaltagar.simpleworkoutnotebook.DatabaseFiles.SetsConverter;

import java.util.ArrayList;
import java.util.Calendar;

@Entity(tableName = "exercises")
public class Exercise {

    private static final String TAG = "Exercise";

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    @TypeConverters(CalendarConverter.class)
    private Calendar calendar;
    @TypeConverters(SetsConverter.class)
    private ArrayList<Set> sets;

    public Exercise(String name, Calendar calendar, ArrayList<Set> sets) {
        this.name = name;
        this.calendar = calendar;
        this.sets = sets;
    }

    // Constructor for new exercises
    @Ignore
    public Exercise(String name) {
        this.name = name;
        this.sets = new ArrayList<>();
        // Taking today's date and truncating to zero
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        this.calendar = calendar;
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

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
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
