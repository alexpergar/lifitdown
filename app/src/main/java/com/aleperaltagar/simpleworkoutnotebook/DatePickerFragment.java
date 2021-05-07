package com.aleperaltagar.simpleworkoutnotebook;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment {

    Calendar currentDay;

    public DatePickerFragment(Calendar calendar) {
        this.currentDay = calendar;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        int year = currentDay.get(Calendar.YEAR);
        int month = currentDay.get(Calendar.MONTH);
        int day = currentDay.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), (DatePickerDialog.OnDateSetListener) getTargetFragment(), year, month, day);
    }
}
