package com.aleperaltagar.simpleworkoutnotebook;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MainFragment extends Fragment {

    private RecyclerView exercisesRecView;
    private ExerciseAdapter exercisesAdapter;
    private ImageView btnAddExercise;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate layout
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        // Set today's date
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.set(Calendar.MILLISECOND, 0);

        // Initialize views for today
        initViews(view);
        initRecViews(today);

        // Change the text of the toolbar to the date and set a click listener
        String dateString = DateFormat.getDateInstance().format(today.getTime());
        TextView textView = getActivity().findViewById(R.id.textToolbar);
        textView.setText(dateString);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        // Add new exercise button
        btnAddExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Exercise newExercise = new Exercise("");
                exercisesAdapter.addNewExercise(newExercise);
            }
        });

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
    }
}
