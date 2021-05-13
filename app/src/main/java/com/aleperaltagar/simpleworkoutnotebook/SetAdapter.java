package com.aleperaltagar.simpleworkoutnotebook;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.Image;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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
    private SharedPreferences sharedPreferences;

    public SetAdapter(Context context, int exerciseId) {
        this.context = context;
        this.exerciseId = exerciseId;
        sharedPreferences = context.getSharedPreferences("prefs",0);
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

        // Show only desired fields
        hideNotUsedFields(holder, sharedPreferences);
        
        // Set the data for the sets
        holder.txtWeight.setText(items.get(position).getWeight());
        holder.txtReps.setText(items.get(position).getReps());

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

    private void hideNotUsedFields(ViewHolder holder, SharedPreferences sharedPreferences) {
        if (!sharedPreferences.getBoolean("checkWeight", false)) {
            holder.txtWeight.setVisibility(View.GONE);
            holder.txtIconWeight.setVisibility(View.GONE);
        } else {
            if (sharedPreferences.getString("weightUnit", null).equals("kg")) {
                holder.txtIconWeight.setText("kg");
            } else {
                holder.txtIconWeight.setText("lb");
            }
        }
        if (!sharedPreferences.getBoolean("checkReps", false)) {
            holder.txtReps.setVisibility(View.GONE);
            holder.txtIconReps.setVisibility(View.GONE);
        }
        if (!sharedPreferences.getBoolean("checkRestingTime", false)) {
            holder.txtRestingTime.setVisibility(View.GONE);
            holder.imageRestingTime.setVisibility(View.GONE);
        }
        if (!sharedPreferences.getBoolean("checkExecutionTime", false)) {
            holder.txtWorkingTime.setVisibility(View.GONE);
            holder.imageWorkingTime.setVisibility(View.GONE);
        }
        if (!sharedPreferences.getBoolean("checkFailure", false)) {
            holder.checkboxFailure.setVisibility(View.GONE);
            holder.imageFailure.setVisibility(View.GONE);
        }
        if (!sharedPreferences.getBoolean("checkRIR", false)) {
            holder.txtRIR.setVisibility(View.GONE);
            holder.txtIconRIR.setVisibility(View.GONE);
        }
        if (!sharedPreferences.getBoolean("checkTargetReps", false)) {
            holder.txtTargetReps.setVisibility(View.GONE);
            holder.imageTargetReps.setVisibility(View.GONE);
        }
        if (!sharedPreferences.getBoolean("checkMood", false)) {
            holder.spinnerMood.setVisibility(View.GONE);
        }
        if (!sharedPreferences.getBoolean("checkComment", false)) {
            holder.txtComment.setVisibility(View.GONE);
            holder.imageComment.setVisibility(View.GONE);
        }
        if (!sharedPreferences.getBoolean("checkNote", false)) {
            holder.imageNote.setVisibility(View.GONE);
        }
        if (!sharedPreferences.getBoolean("checkPersonal1", false)) {
            holder.txtPersonal1.setVisibility(View.GONE);
            holder.txtIconPersonal1.setVisibility(View.GONE);
        }
        if (!sharedPreferences.getBoolean("checkPersonal2", false)) {
            holder.txtPersonal2.setVisibility(View.GONE);
            holder.txtIconPersonal2.setVisibility(View.GONE);
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

        private MaterialCardView parent;
        private EditText txtWeight, txtReps, txtWorkingTime, txtRestingTime, txtRIR, txtTargetReps,
            txtComment, txtPersonal1, txtPersonal2;
        private TextView txtIconWeight, txtIconReps, txtIconRIR, txtIconPersonal1, txtIconPersonal2;
        private TextWatcher textWatcherWeight = null, textWatcherReps = null;
        private ImageView btnDeleteSet, imageWorkingTime, imageRestingTime, imageFailure, imageTargetReps,
            imageComment, imageNote;
        private Spinner spinnerMood;
        private CheckBox checkboxFailure;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.parent);
            btnDeleteSet = itemView.findViewById(R.id.btnDeleteSet);
            txtWeight = itemView.findViewById(R.id.txtWeight);
            txtIconWeight = itemView.findViewById(R.id.txtIconWeight);
            txtReps = itemView.findViewById(R.id.txtReps);
            txtIconReps = itemView.findViewById(R.id.txtIconReps);
            txtWorkingTime = itemView.findViewById(R.id.txtWorkingTime);
            imageWorkingTime = itemView.findViewById(R.id.imageWorkingTime);
            txtRestingTime = itemView.findViewById(R.id.txtRestingTime);
            imageRestingTime = itemView.findViewById(R.id.imageRestingTime);
            checkboxFailure = itemView.findViewById(R.id.checkboxFailure);
            imageFailure = itemView.findViewById(R.id.imageFailure);
            txtRIR = itemView.findViewById(R.id.txtRIR);
            txtIconRIR = itemView.findViewById(R.id.txtIconRIR);
            txtTargetReps = itemView.findViewById(R.id.txtTargetReps);
            imageTargetReps = itemView.findViewById(R.id.imageTargetReps);
            spinnerMood = itemView.findViewById(R.id.spinnerMood);
            txtComment = itemView.findViewById(R.id.txtComment);
            imageComment = itemView.findViewById(R.id.imageComment);
            imageNote = itemView.findViewById(R.id.imageNote);
            txtPersonal1 = itemView.findViewById(R.id.txtPersonal1);
            txtIconPersonal1 = itemView.findViewById(R.id.txtIconPersonal1);
            txtPersonal2 = itemView.findViewById(R.id.txtPersonal2);
            txtIconPersonal2 = itemView.findViewById(R.id.txtIconPersonal2);
        }
    }

}
