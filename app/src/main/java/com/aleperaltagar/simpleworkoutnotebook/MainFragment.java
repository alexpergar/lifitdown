package com.aleperaltagar.simpleworkoutnotebook;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MainFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    private static final String TAG = "MainFragment";

    private RecyclerView exercisesRecView;
    private ExerciseAdapter exercisesAdapter;
    private ImageView btnAddExercise;
    private TextView textToolbar;
    private ImageView editButtonToolbar;
    private Fragment context = this;
    private Calendar currentDay;
    private boolean editMode = false;

    public MainFragment(Calendar currentDay) {
        this.currentDay = currentDay;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate layout
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        // Initialize views for today
        initViews(view);

        // Toolbar date picker on date click
        textToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment(currentDay);
                datePicker.setTargetFragment(context, 1);
                datePicker.show(getFragmentManager(), "date picker");
            }
        });

        // Toolbar button to edit mode
        editButtonToolbar.setVisibility(View.VISIBLE);
        editButtonToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editMode = !editMode;
                exercisesAdapter.switchEditMode(editMode);
            }
        });

        // Add new exercise button
        btnAddExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Exercise newExercise = new Exercise(currentDay);
                exercisesAdapter.addNewExercise(newExercise);
            }
        });

        // Set everything for the current date
        setWorkoutDay(currentDay);

        return view;
    }

    private void initRecViews(Calendar day) {
        exercisesAdapter = new ExerciseAdapter(getActivity());
        exercisesRecView.setAdapter(exercisesAdapter);
        exercisesRecView.setLayoutManager((new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false)));

        ArrayList<Exercise> exercises = Utils.getItemsByDate(getActivity(), day);
        if (null != exercises) {
            exercisesAdapter.setItems(exercises);
        }
    }

    private void initViews(View view) {
        exercisesRecView = view.findViewById(R.id.exercisesRecView);
        btnAddExercise = view.findViewById(R.id.btnAddExercise);
        textToolbar = getActivity().findViewById(R.id.textToolbar);
        editButtonToolbar = getActivity().findViewById(R.id.editButtonToolbar);
    }

    // Function to reset all data related to day (toolbar, recyclerview)
    private void setWorkoutDay(Calendar day) {
        currentDay = day;
        initRecViews(day);
        // Change the text of the toolbar to the date
        String dateString = DateFormat.getDateInstance().format(day.getTime());
        textToolbar.setText(dateString);
    }

    // This triggers when a day is chosen
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        setWorkoutDay(c);
    }
}
