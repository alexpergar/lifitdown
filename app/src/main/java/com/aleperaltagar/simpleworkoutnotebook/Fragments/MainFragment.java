package com.aleperaltagar.simpleworkoutnotebook.Fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aleperaltagar.simpleworkoutnotebook.Exercise;
import com.aleperaltagar.simpleworkoutnotebook.Adapters.ExerciseAdapter;
import com.aleperaltagar.simpleworkoutnotebook.R;
import com.aleperaltagar.simpleworkoutnotebook.Utils;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    private static final String TAG = "MainFragment";

    private RecyclerView exercisesRecView;
    private ExerciseAdapter exercisesAdapter;
    private ImageView btnAddExercise;
    private TextView textToolbar, txtNoExercises;
    private ImageView editButtonToolbar;
    private Fragment context = this;
    private Calendar currentDay;
    private ProgressBar loadingSpinner;
    private boolean editMode = false;
    private ArrayList<Exercise> exercises;
    private View thisView;

    public MainFragment(Calendar currentDay) {
        this.currentDay = currentDay;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Initialize the toolbar
        textToolbar = getActivity().findViewById(R.id.textToolbar);
        editButtonToolbar = getActivity().findViewById(R.id.editButtonToolbar);
        ProgressBar toolbarLoadingSpinner = getActivity().findViewById(R.id.toolbarLoadingSpinner);
        String dateString = DateFormat.getDateInstance().format(currentDay.getTime());
        textToolbar.setText(dateString);
        editButtonToolbar.setImageResource(R.drawable.ic_edit);

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
                editButtonToolbar.setVisibility(View.GONE);
                toolbarLoadingSpinner.setVisibility(View.VISIBLE);
                if (editMode) {
                    editButtonToolbar.setImageResource(R.drawable.ic_check);
                } else {
                    editButtonToolbar.setImageResource(R.drawable.ic_edit);
                }
                // Do the action after setting the loading spinner rolling
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        exercisesAdapter.switchEditMode(editMode);
                        editButtonToolbar.setVisibility(View.VISIBLE);
                        toolbarLoadingSpinner.setVisibility(View.GONE);
                    }
                }, 0);
            }
        });

        // If this view exists (because it was backstacked), load it
        if (thisView == null) {
            // Inflate layout
            View view = inflater.inflate(R.layout.fragment_main, container, false);

            // Initialize views for today
            initViews(view);

            // Add new exercise button
            btnAddExercise.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Exercise newExercise = new Exercise(currentDay);
                    exercisesAdapter.addNewExercise(newExercise);
                    txtNoExercises.setVisibility(View.GONE);
                }
            });

            // Set everything for the current date
            setWorkoutDay(currentDay);

            // Save the view to recover it
            thisView = view;
        }
        return thisView;
    }

    private void initRecViews(Calendar day) {
        exercisesAdapter = new ExerciseAdapter(getActivity());
        exercisesRecView.setAdapter(exercisesAdapter);
        exercisesRecView.setLayoutManager((new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false)));

        // Show loading spinner meanwhile the content loads
        loadingSpinner.setVisibility(View.VISIBLE);
        btnAddExercise.setVisibility(View.GONE);
        txtNoExercises.setVisibility(View.GONE);

        // Get the data from the database from a service thread
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                exercises = Utils.getItemsByDate(getActivity(), day);
                SystemClock.sleep(250); // sleep 0.25s to let the drawer close (not a very good solution)
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        loadingSpinner.setVisibility(View.GONE);
                        btnAddExercise.setVisibility(View.VISIBLE);
                        if (null != exercises) {
                            exercisesAdapter.setItems(exercises);
                        }
                        if (exercises.isEmpty()){
                            txtNoExercises.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }
        });
    }

    private void initViews(View view) {
        exercisesRecView = view.findViewById(R.id.exercisesRecView);
        btnAddExercise = view.findViewById(R.id.btnAddExercise);
        loadingSpinner = view.findViewById(R.id.loading_spinner);
        txtNoExercises = view.findViewById(R.id.txtNoExercises);
    }

    // Function to reset all data related to day (toolbar, recyclerview)
    private void setWorkoutDay(Calendar day) {
        currentDay = day;
        initRecViews(day);
        // Change the text of the toolbar to the date
        String dateString = DateFormat.getDateInstance().format(day.getTime());
        textToolbar.setText(dateString);
        editButtonToolbar.setImageResource(R.drawable.ic_edit);
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
