package com.aleperaltagar.simpleworkoutnotebook;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class SetAdapter extends RecyclerView.Adapter<SetAdapter.ViewHolder>{

    private ArrayList<Set> items = new ArrayList<>();
    private Context context;
    private boolean editable = false;
    private int exerciseId;
    private boolean onlyShow;
    private SharedPreferences sharedPreferences;

    public SetAdapter(Context context, int exerciseId, boolean onlyShow) {
        this.context = context;
        this.exerciseId = exerciseId;
        this.onlyShow = onlyShow;
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


        // Show only desired fields
        hideNotUsedFields(holder, position, sharedPreferences);
        
        // Set the data for the sets

        // Text changed listeners for fields in the sets

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

    private void hideNotUsedFields(ViewHolder holder, int position, SharedPreferences sharedPreferences) {
        // if weight is not checked in preferences, hide it
        if (!sharedPreferences.getBoolean("checkWeight", false)) {
            holder.txtWeight.setVisibility(View.GONE);
            holder.txtIconWeight.setVisibility(View.GONE);
        } else {
            // check which weight unit is checked on settings
            if (sharedPreferences.getString("weightUnit", "kg").equals("kg")) {
                holder.txtIconWeight.setText("kg");
            } else {
                holder.txtIconWeight.setText("lb");
            }
            holder.txtWeight.setText(items.get(position).getWeight());
            // if onlyShow, don't let the user change the data in the fields
            if (onlyShow) {
                holder.txtWeight.setFocusable(false);
                holder.txtWeight.setFocusableInTouchMode(false);
            } else {
                holder.txtWeight.removeTextChangedListener(holder.textWatcherWeight);
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
            }
        }

        if (!sharedPreferences.getBoolean("checkReps", false)) {
            holder.txtReps.setVisibility(View.GONE);
            holder.txtIconReps.setVisibility(View.GONE);
        } else {
            holder.txtReps.setText(items.get(position).getReps());
            if (onlyShow) {
                holder.txtReps.setFocusable(false);
                holder.txtReps.setFocusableInTouchMode(false);
            } else {
                holder.txtReps.removeTextChangedListener(holder.textWatcherReps);
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
            }
        }

        if (!sharedPreferences.getBoolean("checkRestingTime", false)) {
            holder.txtRestingTime.setVisibility(View.GONE);
            holder.imageRestingTime.setVisibility(View.GONE);
        } else {
            holder.txtRestingTime.setText(items.get(position).getRestingTime());
            if (onlyShow) {
                holder.txtRestingTime.setFocusableInTouchMode(false);
                holder.txtRestingTime.setFocusable(false);
            } else {
                holder.txtRestingTime.removeTextChangedListener(holder.textWatcherRestingTime);
                holder.txtRestingTime.addTextChangedListener(holder.textWatcherRestingTime = new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }
                    @Override
                    public void afterTextChanged(Editable s) {
                        items.get(position).setRestingTime(holder.txtRestingTime.getText().toString());
                        Utils.updateSets(context, exerciseId, items);
                    }
                });
            }
        }

        if (!sharedPreferences.getBoolean("checkExecutionTime", false)) {
            holder.txtWorkingTime.setVisibility(View.GONE);
            holder.imageWorkingTime.setVisibility(View.GONE);
        } else {
            holder.txtWorkingTime.setText(items.get(position).getExecutionTime());
            if (onlyShow) {
                holder.txtWorkingTime.setFocusable(false);
                holder.txtWorkingTime.setFocusableInTouchMode(false);
            } else {
                holder.txtWorkingTime.removeTextChangedListener(holder.textWatcherWorkingTime);
                holder.txtWorkingTime.addTextChangedListener(holder.textWatcherWorkingTime = new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }
                    @Override
                    public void afterTextChanged(Editable s) {
                        items.get(position).setExecutionTime(holder.txtWorkingTime.getText().toString());
                        Utils.updateSets(context, exerciseId, items);
                    }
                });
            }
        }

        if (!sharedPreferences.getBoolean("checkFailure", false)) {
            holder.checkboxFailure.setVisibility(View.GONE);
            holder.imageFailure.setVisibility(View.GONE);
        } else {
            holder.checkboxFailure.setChecked(items.get(position).isFailure());
            if (onlyShow) {
                holder.checkboxFailure.setClickable(false);
            } else {
                holder.checkboxFailure.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        items.get(position).setFailure(isChecked);
                        Utils.updateSets(context, exerciseId,items);
                    }
                });
            }
        }

        if (!sharedPreferences.getBoolean("checkRIR", false)) {
            holder.txtRIR.setVisibility(View.GONE);
            holder.txtIconRIR.setVisibility(View.GONE);
        } else {
            holder.txtRIR.setText(items.get(position).getRIR());
            if (onlyShow) {
                holder.txtRIR.setFocusable(false);
                holder.txtRIR.setFocusableInTouchMode(false);
            } else {
                holder.txtRIR.removeTextChangedListener(holder.textWatcherRIR);
                holder.txtRIR.addTextChangedListener(holder.textWatcherRIR = new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }
                    @Override
                    public void afterTextChanged(Editable s) {
                        items.get(position).setRIR(holder.txtRIR.getText().toString());
                        Utils.updateSets(context, exerciseId, items);
                    }
                });
            }
        }

        if (!sharedPreferences.getBoolean("checkTargetReps", false)) {
            holder.txtTargetReps.setVisibility(View.GONE);
            holder.imageTargetReps.setVisibility(View.GONE);
        } else {
            holder.txtTargetReps.setText(items.get(position).getTargetReps());
            if (onlyShow) {
                holder.txtTargetReps.setFocusable(false);
                holder.txtTargetReps.setFocusableInTouchMode(false);
            } else {
                holder.txtTargetReps.removeTextChangedListener(holder.textWatcherTargetReps);
                holder.txtTargetReps.addTextChangedListener(holder.textWatcherTargetReps = new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }
                    @Override
                    public void afterTextChanged(Editable s) {
                        items.get(position).setTargetReps(holder.txtTargetReps.getText().toString());
                        Utils.updateSets(context, exerciseId, items);
                    }
                });
            }
        }

        if (!sharedPreferences.getBoolean("checkMood", false)) {
            holder.txtMood.setVisibility(View.GONE);
            holder.imageMood.setVisibility(View.GONE);
        } else {
            holder.txtMood.setText(items.get(position).getMood());
            if (onlyShow) {
                holder.txtMood.setFocusable(false);
                holder.txtMood.setFocusableInTouchMode(false);
            } else {
                holder.txtMood.removeTextChangedListener(holder.textWatcherMood);
                holder.txtMood.addTextChangedListener(holder.textWatcherMood = new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }
                    @Override
                    public void afterTextChanged(Editable s) {
                        items.get(position).setMood(holder.txtMood.getText().toString());
                        Utils.updateSets(context, exerciseId, items);
                    }
                });
            }
        }

        if (!sharedPreferences.getBoolean("checkComment", false)) {
            holder.txtComment.setVisibility(View.GONE);
            holder.imageComment.setVisibility(View.GONE);
        } else {
            holder.txtComment.setText(items.get(position).getComment());
            if (onlyShow) {
                holder.txtComment.setFocusable(false);
                holder.txtComment.setFocusableInTouchMode(false);
            } else {
                holder.txtComment.removeTextChangedListener(holder.textWatcherComment);
                holder.txtComment.addTextChangedListener(holder.textWatcherComment = new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }
                    @Override
                    public void afterTextChanged(Editable s) {
                        items.get(position).setComment(holder.txtComment.getText().toString());
                        Utils.updateSets(context, exerciseId, items);
                    }
                });
            }
        }

        // TODO: 5/14/21 click to open an editor fragment
        if (!sharedPreferences.getBoolean("checkNote", false)) {
            holder.imageNote.setVisibility(View.GONE);
        } else {
            if (!items.get(position).getNote().equals("")) {
                holder.imageNote.setImageResource(R.drawable.ic_note_full);
                holder.imageNote.setColorFilter(Color.BLACK);
            }
            holder.imageNote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("exerciseId", exerciseId);
                    bundle.putInt("setId", items.get(position).getId());
                    bundle.putString("noteText", items.get(position).getNote());
                    Fragment noteFragment = new NoteFragment();
                    noteFragment.setArguments(bundle);
                    FragmentTransaction noteTransaction = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
                    noteTransaction.replace(R.id.container , noteFragment);
                    noteTransaction.addToBackStack(null);
                    noteTransaction.commit();
                }
            });
        }

        if (!sharedPreferences.getBoolean("checkPersonal1", false)) {
            holder.txtPersonal1.setVisibility(View.GONE);
            holder.txtIconPersonal1.setVisibility(View.GONE);
        } else {
            holder.txtPersonal1.setText(items.get(position).getPersonal1());
            if (onlyShow) {
                holder.txtPersonal1.setFocusable(false);
                holder.txtPersonal1.setFocusableInTouchMode(false);
            } else {
                holder.txtPersonal1.removeTextChangedListener(holder.textWatcherPersonal1);
                holder.txtPersonal1.addTextChangedListener(holder.textWatcherPersonal1 = new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }
                    @Override
                    public void afterTextChanged(Editable s) {
                        items.get(position).setPersonal1(holder.txtPersonal1.getText().toString());
                        Utils.updateSets(context, exerciseId, items);
                    }
                });
            }
        }

        if (!sharedPreferences.getBoolean("checkPersonal2", false)) {
            holder.txtPersonal2.setVisibility(View.GONE);
            holder.txtIconPersonal2.setVisibility(View.GONE);
        } else {
            holder.txtPersonal2.setText(items.get(position).getPersonal2());
            if (onlyShow) {
                holder.txtPersonal2.setFocusable(false);
                holder.txtPersonal2.setFocusableInTouchMode(false);
            } else {
                holder.txtPersonal2.removeTextChangedListener(holder.textWatcherPersonal2);
                holder.txtPersonal2.addTextChangedListener(holder.textWatcherPersonal2 = new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }
                    @Override
                    public void afterTextChanged(Editable s) {
                        items.get(position).setPersonal2(holder.txtPersonal2.getText().toString());
                        Utils.updateSets(context, exerciseId, items);
                    }
                });
            }
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
        items.add(new Set(context, exerciseId));
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
            txtComment, txtPersonal1, txtPersonal2, txtMood;
        private TextView txtIconWeight, txtIconReps, txtIconRIR, txtIconPersonal1, txtIconPersonal2;
        private TextWatcher textWatcherWeight, textWatcherReps, textWatcherWorkingTime, textWatcherRestingTime,
            textWatcherRIR, textWatcherTargetReps, textWatcherComment, textWatcherPersonal1, textWatcherPersonal2,
            textWatcherMood;
        private ImageView btnDeleteSet, imageWorkingTime, imageRestingTime, imageFailure, imageTargetReps,
            imageComment, imageNote, imageMood;
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
            txtMood = itemView.findViewById(R.id.txtMood);
            imageMood = itemView.findViewById(R.id.imageMood);
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
