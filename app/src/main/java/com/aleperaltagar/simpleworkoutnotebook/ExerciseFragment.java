package com.aleperaltagar.simpleworkoutnotebook;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;

public class ExerciseFragment extends Fragment {

    private String exerciseName;
    private RecyclerView exercisesRecView;
    private ExerciseShowAdapter exercisesAdapter;
    private TextView textToolbar;
    private ImageView editButtonToolbar;

    public ExerciseFragment(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate layout
        View view = inflater.inflate(R.layout.fragment_exercise, container, false);

        initViews(view);
        initRecViews(exerciseName);

        // Change toolbar text and disable click listener
        textToolbar.setOnClickListener(null);
        textToolbar.setText(exerciseName);
        editButtonToolbar.setVisibility(View.GONE);

        return view;
    }

    private void initViews(View view) {
        exercisesRecView  = view.findViewById(R.id.exerciseFragmentRecview);
        textToolbar = getActivity().findViewById(R.id.textToolbar);
        editButtonToolbar = getActivity().findViewById(R.id.editButtonToolbar);
    }

    private void initRecViews(String name) {
        exercisesAdapter = new ExerciseShowAdapter(getActivity());
        exercisesRecView.setAdapter(exercisesAdapter);
        exercisesRecView.setLayoutManager((new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false)));

        ArrayList<Exercise> exercises = Utils.getItemsByName(getActivity(), name);
        if (null != exercises) {
            exercisesAdapter.setItems(exercises);
        }
    }

}
