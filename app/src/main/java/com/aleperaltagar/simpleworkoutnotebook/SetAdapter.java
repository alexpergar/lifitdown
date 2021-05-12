package com.aleperaltagar.simpleworkoutnotebook;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
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
        // Eliminate the textWatcher, as the placeholder changes item when recycled
        holder.txtWeight.removeTextChangedListener(holder.textWatcherWeight);
        holder.txtReps.removeTextChangedListener(holder.textWatcherReps);

        // Set the data for the sets
        holder.txtWeight.setText(items.get(position).getWeight());
        holder.txtReps.setText(items.get(position).getReps());

        // If focus lost, save the data that was written on the EditText
//        holder.txtWeight.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (!hasFocus) {
//                    items.get(position).setWeight(holder.txtWeight.getText().toString());
//                    Utils.updateSets(context, exerciseId, items);
//                }
//            }
//        });
//
//        holder.txtReps.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (!hasFocus) {
//                    items.get(position).setReps(holder.txtReps.getText().toString());
//                    Utils.updateSets(context, exerciseId, items);
//                }
//            }
//        });

        // Text changed listeners for fields in the sets
        holder.txtWeight.addTextChangedListener(holder.textWatcherWeight = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                items.get(position).setWeight(holder.txtWeight.getText().toString());
                Utils.updateSets(context, exerciseId, items);
            }
        });

        holder.txtReps.addTextChangedListener(holder.textWatcherReps = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                items.get(position).setReps(holder.txtReps.getText().toString());
                Utils.updateSets(context, exerciseId, items);
            }
        });

        // If parent ExerciseAdapter is in editmode, put yourself in edit mode as well
        if (editable) {
            holder.btnDeleteSet.setVisibility(View.VISIBLE);
            holder.btnDeleteSet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    View focusedView = ((Activity) context).getCurrentFocus();
                    if (null != focusedView) focusedView.clearFocus();
                    items.remove(position);
                    Utils.updateSets(context, exerciseId, items);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, getItemCount());
                }
            });
        } else {
            holder.btnDeleteSet.setVisibility(View.GONE);
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
        private TextWatcher textWatcherWeight = null;
        private TextWatcher textWatcherReps = null;
        private ImageView btnDeleteSet;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtWeight = itemView.findViewById(R.id.txtWeight);
            txtReps = itemView.findViewById(R.id.txtReps);
            parent = itemView.findViewById(R.id.parent);
            btnDeleteSet = itemView.findViewById(R.id.btnDeleteSet);
        }
    }

}
