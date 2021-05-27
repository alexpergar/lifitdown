package com.aleperaltagar.simpleworkoutnotebook;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ListOfExercisesFragment extends Fragment {

    private static final String TAG = "ListOfExercisesFragment";

    private RecyclerView listOfExercisesRecView;
    private ListOfExercisesAdapter listOfExercisesAdapter;
    private TextView textToolbar;
    private ImageView editButtonToolbar;
    private AutoCompleteTextView txtSearchExercise;
    ArrayList<String> exercises = new ArrayList<>();

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
        editButtonToolbar.setVisibility(View.GONE);

        txtSearchExercise.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                searchExercise(txtSearchExercise.getText().toString());
            }
        });

        return view;
    }

    private void searchExercise(String txtExercise){
        txtExercise = txtExercise.toLowerCase();
        ArrayList<String> foundItems = new ArrayList<>();
        for (String exercise : exercises) {
            String exerciseLower = exercise.toLowerCase();
            if (exerciseLower.contains(txtExercise)) {
                foundItems.add(exercise);
            }
        }
        listOfExercisesAdapter.setItems(foundItems);
    }

    private void initViews(View view) {
        listOfExercisesRecView  = view.findViewById(R.id.listOfExercisesFragmentRecview);
        txtSearchExercise = view.findViewById(R.id.txtSearchExercise);
        textToolbar = getActivity().findViewById(R.id.textToolbar);
        editButtonToolbar = getActivity().findViewById(R.id.editButtonToolbar);
    }

    private void initRecViews() {
        listOfExercisesAdapter = new ListOfExercisesAdapter(getActivity());
        listOfExercisesRecView.setAdapter(listOfExercisesAdapter);
        listOfExercisesRecView.setLayoutManager((new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false)));

        exercises = Utils.getUniqueItemsString(getActivity());
        listOfExercisesAdapter.setItems(exercises);
    }
}
