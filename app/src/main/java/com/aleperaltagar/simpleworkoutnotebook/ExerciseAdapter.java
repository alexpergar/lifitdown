package com.aleperaltagar.simpleworkoutnotebook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ViewHolder>{

    private ArrayList<Exercise> items = new ArrayList<>();
    private Context context;
    private boolean editable;

    public ExerciseAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercise_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Name of the exercise
        holder.exerciseName.setText(items.get(position).getName());
        holder.exerciseName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment exerciseFragment = new ExerciseFragment(items.get(position).getName());
                FragmentTransaction transaction = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.container , exerciseFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        // Setting the sets recyclerview
        SetAdapter setsAdapter = new SetAdapter(context, items.get(position).getId());
        setsAdapter.setItems(items.get(position).getSets());
        holder.setsRecView.setAdapter(setsAdapter);
        holder.setsRecView.setLayoutManager((new LinearLayoutManager(context, RecyclerView.VERTICAL, false)));

        // Button to add a new set
        holder.btnAddSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setsAdapter.addEmptySet();
            }
        });

        // Update exercise name when focus lost
        holder.exerciseName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    Utils.updateExerciseName(context, items.get(position).getId(), holder.exerciseName.getText().toString());
                    items.get(position).setName(holder.exerciseName.getText().toString());
                }
            }
        });

        holder.btnDeleteExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.deleteExercise(context, items.get(position).getId());
                items.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, getItemCount());
            }
        });

        // If edit mode is enabled (through button click on menu) show the button and inform the sets adapter
        if (editable) {
            holder.btnDeleteExercise.setVisibility(View.VISIBLE);
        } else {
            holder.btnDeleteExercise.setVisibility(View.GONE);
        }
        setsAdapter.switchEditMode(editable);
    }

    public void switchEditMode(boolean editMode) {
        editable = editMode;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(ArrayList<Exercise> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    // Used to add an empty exercise in the recycler view
    public void addNewExercise(Exercise exercise) {
        items.add(items.size(), exercise);
        notifyItemInserted(items.size());
        if (Utils.addExercise(context, exercise)) {
            Toast.makeText(context, "Exercise created", Toast.LENGTH_SHORT).show();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private EditText exerciseName;
        private MaterialCardView parent;
        private RecyclerView setsRecView;
        private ImageView btnDeleteExercise;
        private ImageView btnAddSet;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            exerciseName = itemView.findViewById(R.id.exerciseName);
            parent = itemView.findViewById(R.id.parent);
            setsRecView = itemView.findViewById(R.id.setsRecView);
            btnDeleteExercise = itemView.findViewById(R.id.btnDeleteExercise);
            btnAddSet = itemView.findViewById(R.id.btnAddSet);

        }
    }
}
