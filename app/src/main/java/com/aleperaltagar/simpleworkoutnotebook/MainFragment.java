package com.aleperaltagar.simpleworkoutnotebook;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainFragment extends Fragment {

    private RecyclerView exercisesRecView;
    private ExerciseAdapter exercisesAdapter;
    private ImageView btnAddExercise;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        initViews(view);
        initRecViews();

        btnAddExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Exercise newExercise = new Exercise("");
                exercisesAdapter.addNewExercise(newExercise);
            }
        });

        return view;
    }

    private void initRecViews() {
        exercisesAdapter = new ExerciseAdapter(getActivity());
        exercisesRecView.setAdapter(exercisesAdapter);
        exercisesRecView.setLayoutManager((new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false)));

        ArrayList<Exercise> allItems = Utils.getAllItems(getActivity());
        if (null != allItems) {
            exercisesAdapter.setItems(allItems);
        }
    }

    private void initViews(View view) {
        exercisesRecView = view.findViewById(R.id.exercisesRecView);
        btnAddExercise = view.findViewById(R.id.btnAddExercise);
    }
}
