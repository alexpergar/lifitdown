package com.aleperaltagar.simpleworkoutnotebook;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class SetAdapter extends RecyclerView.Adapter<SetAdapter.ViewHolder>{

    private ArrayList<Set> items = new ArrayList<>();
    private Context context;
    private boolean editable = false;
    private int exerciseId;

    public SetAdapter(Context context, int exerciseId) {
        this.context = context;
        this.exerciseId = exerciseId;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.set_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Set the data for the sets
        holder.txtWeight.setText(items.get(position).getWeight());
        holder.txtReps.setText(items.get(position).getReps());

        // If focus lost, save the data that was written on the EditText
        holder.txtWeight.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    items.get(position).setWeight(holder.txtWeight.getText().toString());
                    Utils.updateSets(context, exerciseId, items);
                }
            }
        });

        holder.txtReps.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    items.get(position).setReps(holder.txtReps.getText().toString());
                    Utils.updateSets(context, exerciseId, items);
                }
            }
        });

        // If parent ExerciseAdapter is in editmode, put yourself in edit mode as well
        if (editable) {
            holder.parent.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    items.remove(position);
                    Utils.updateSets(context, exerciseId, items);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, getItemCount());
                    return true;
                }
            });
        } else {
            holder.parent.setOnLongClickListener(null);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(ArrayList<Set> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public void addEmptySet() {
        items.add(new Set(exerciseId));
        Utils.updateSets(context, exerciseId, items);
        notifyItemInserted(items.size());
        notifyItemRangeChanged(items.size(), getItemCount());
    }

    // Used from ExerciseAdapter
    public void switchEditMode(boolean editMode) {
        this.editable = editMode;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private EditText txtWeight;
        private EditText txtReps;
        private MaterialCardView parent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtWeight = itemView.findViewById(R.id.txtWeight);
            txtReps = itemView.findViewById(R.id.txtReps);
            parent = itemView.findViewById(R.id.parent);
        }
    }

}
