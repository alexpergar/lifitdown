package com.aleperaltagar.simpleworkoutnotebook;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ListOfExercisesFragment extends Fragment {

    private RecyclerView listOfExercisesRecView;
    private ListOfExercisesAdapter listOfExercisesAdapter;
    private TextView textToolbar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate layout
        View view = inflater.inflate(R.layout.fragment_list_of_exercises, container, false);

        initViews(view);
        initRecViews();

        // Change toolbar text and disable click listener
        textToolbar.setOnClickListener(null);
        textToolbar.setText("Exercises");

        return view;
    }

    private void initViews(View view) {
        listOfExercisesRecView  = view.findViewById(R.id.listOfExercisesFragmentRecview);
        textToolbar = getActivity().findViewById(R.id.textToolbar);
    }

    private void initRecViews() {
        listOfExercisesAdapter = new ListOfExercisesAdapter(getActivity());
        listOfExercisesRecView.setAdapter(listOfExercisesAdapter);
        listOfExercisesRecView.setLayoutManager((new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false)));

        ArrayList<String> exercises = Utils.getUniqueItemsString(getActivity());
        if (null != exercises) {
            listOfExercisesAdapter.setItems(exercises);
        }
    }

}
