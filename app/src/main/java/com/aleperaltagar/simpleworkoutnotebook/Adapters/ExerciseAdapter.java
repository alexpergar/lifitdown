package com.aleperaltagar.simpleworkoutnotebook.Adapters;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aleperaltagar.simpleworkoutnotebook.Exercise;
import com.aleperaltagar.simpleworkoutnotebook.Fragments.ExerciseFragment;
import com.aleperaltagar.simpleworkoutnotebook.R;
import com.aleperaltagar.simpleworkoutnotebook.Utils;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ViewHolder>{

    private ArrayList<Exercise> items = new ArrayList<>();
    private Context context;
    private boolean editable;
    private ArrayList<String> everyUniqueExercise;

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
        // Eliminate the textWatcher, as the placeholder changes item when recycled
        holder.exerciseName.removeTextChangedListener(holder.textWatcher);

        // Load every unique exercise name in the database into an ArrayList and put them in an adapter for the exerciseName
        everyUniqueExercise = Utils.getUniqueItemsString(context);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, everyUniqueExercise);
        holder.exerciseName.setAdapter(adapter);

        // Name of the exercise
        holder.exerciseName.setText(items.get(position).getName());

        // Transition to exercise's previous marks fragment when clicked
        holder.btnPreviousMarks.setOnClickListener(new View.OnClickListener() {
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
        SetAdapter setsAdapter = new SetAdapter(context, items.get(position).getId(), false);
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

        // Save what is being written as each character is introduced
        holder.exerciseName.addTextChangedListener(holder.textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                Utils.updateExerciseName(context, items.get(position).getId(), holder.exerciseName.getText().toString());
                items.get(position).setName(holder.exerciseName.getText().toString());
            }
        });

        // Button to delete the exercise
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

    // Switch the edit mode (on or off)
    public void switchEditMode(boolean editMode) {
        this.editable = editMode;
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
        Utils.addExercise(context, exercise);
        items.add(Utils.getLastItem(context)); // you have to take back the last record, because if you just insert "exercise" its id is 0
        notifyItemInserted(items.size() - 1);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private AutoCompleteTextView exerciseName;
        private MaterialCardView parent;
        private RecyclerView setsRecView;
        private ImageView btnDeleteExercise;
        private ImageView btnAddSet;
        private ImageView btnPreviousMarks;
        private TextWatcher textWatcher = null;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            exerciseName = itemView.findViewById(R.id.exerciseName);
            parent = itemView.findViewById(R.id.parent);
            setsRecView = itemView.findViewById(R.id.setsRecView);
            btnDeleteExercise = itemView.findViewById(R.id.btnDeleteExercise);
            btnAddSet = itemView.findViewById(R.id.btnAddSet);
            btnPreviousMarks = itemView.findViewById(R.id.buttonPreviousMarks);
        }
    }
}
