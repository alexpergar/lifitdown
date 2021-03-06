package com.aleperaltagar.simpleworkoutnotebook.Fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aleperaltagar.simpleworkoutnotebook.Exercise;
import com.aleperaltagar.simpleworkoutnotebook.Adapters.ExerciseShowAdapter;
import com.aleperaltagar.simpleworkoutnotebook.R;
import com.aleperaltagar.simpleworkoutnotebook.Utils;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExerciseFragment extends Fragment {

    private static final String TAG = "ExerciseFragment";
    private String exerciseName;
    private RecyclerView exercisesRecView;
    private ExerciseShowAdapter exercisesAdapter;
    private TextView textToolbar;
    private ImageView editButtonToolbar;
    private ProgressBar loadingSpinner;
    private Button btnLoadMore;
    private ArrayList<Exercise> exercises;

    public ExerciseFragment(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate layout
        View view = inflater.inflate(R.layout.fragment_exercise, container, false);

        // Initialize views and recycler view
        initViews(view);
        initRecViews(exerciseName);

        // Change toolbar text and disable click listener
        textToolbar.setOnClickListener(null);
        textToolbar.setText(exerciseName);
        editButtonToolbar.setVisibility(View.GONE);

        btnLoadMore.setOnClickListener(v -> {
            int lastPosition = exercisesAdapter.loadMore();
            Log.d(TAG, "onClick: " + exercises.size() + " " + lastPosition);
            if (lastPosition == exercises.size()) {
                btnLoadMore.setVisibility(View.GONE);
            }
        });

        return view;
    }

    private void initViews(View view) {
        exercisesRecView  = view.findViewById(R.id.exerciseFragmentRecview);
        textToolbar = getActivity().findViewById(R.id.textToolbar);
        editButtonToolbar = getActivity().findViewById(R.id.editButtonToolbar);
        loadingSpinner = view.findViewById(R.id.loading_spinner);
        btnLoadMore = view.findViewById(R.id.btnLoadMore);
    }

    private void initRecViews(String name) {
        exercisesAdapter = new ExerciseShowAdapter(getActivity());
        exercisesRecView.setAdapter(exercisesAdapter);
        exercisesRecView.setLayoutManager((new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false)));
        loadingSpinner.setVisibility(View.VISIBLE);

        // Load the items in the background and meanwhile show a loading spinner
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executorService.submit( () -> {
            exercises = Utils.getItemsByName(getActivity(), name);
            handler.post( () -> {
                loadingSpinner.setVisibility(View.GONE);
                if (null != exercises) {
                    // Set the first five items. If every item was displayed (-1 returned), set button visibility to GONE
                    int nextPositionLoaded = exercisesAdapter.setItems(exercises);
                    if (nextPositionLoaded == -1) {
                        btnLoadMore.setVisibility(View.GONE);
                    } else {
                        btnLoadMore.setVisibility(View.VISIBLE);
                    }
                }
            });
        });
    }

}
