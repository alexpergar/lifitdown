package com.aleperaltagar.simpleworkoutnotebook;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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

        // Enter edit mode
        holder.parent.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                holder.parent.setCardBackgroundColor(Color.parseColor("#f2e8da"));
                holder.btnConfirmEdit.setVisibility(View.VISIBLE);
                holder.btnAddSet.setVisibility(View.VISIBLE);
                holder.btnAddSet.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setsAdapter.addEmptySet();
                    }
                });
                holder.exerciseName.setFocusableInTouchMode(true);
                holder.exerciseName.setClickable(true);
                setsAdapter.enableEdition(true);
                return true;
            }
        });

        // Exit edit mode
        holder.btnConfirmEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.parent.setCardBackgroundColor(Color.WHITE);
                holder.btnConfirmEdit.setVisibility(View.GONE);
                holder.btnAddSet.setVisibility(View.GONE);
                items.get(position).setName(holder.exerciseName.getText().toString());
                holder.exerciseName.setFocusable(false);
                holder.exerciseName.setClickable(false);
                setsAdapter.enableEdition(false);
                Utils.updateDatabase(context, items.get(position).getId(), holder.exerciseName.getText().toString(), items.get(position).getSets());
                notifyDataSetChanged(); // is this necessary?
            }
        });
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
        private ImageView btnConfirmEdit;
        private ImageView btnAddSet;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            exerciseName = itemView.findViewById(R.id.exerciseName);
            parent = itemView.findViewById(R.id.parent);
            setsRecView = itemView.findViewById(R.id.setsRecView);
            btnConfirmEdit = itemView.findViewById(R.id.btnConfirmEdit);
            btnAddSet = itemView.findViewById(R.id.btnAddSet);

        }
    }
}
