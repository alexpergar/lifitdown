package com.aleperaltagar.simpleworkoutnotebook.DatabaseFiles;

import androidx.room.TypeConverter;

import java.util.Calendar;

public class CalendarConverter {
    @TypeConverter
    public long calendarToLong (Calendar calendar) {
        return calendar.getTimeInMillis();
    }

    @TypeConverter
    public Calendar longToCalendar (long calendarInLong) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(calendarInLong);
        return calendar;
    }
}
