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
    private boolean editable;
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
        holder.txtWeight.setText(items.get(position).getWeight());
        holder.txtReps.setText(items.get(position).getReps());

        // Change if edit mode is enabled
        if (editable) {
            holder.txtWeight.setFocusableInTouchMode(true);
            holder.txtWeight.setClickable(true);
            holder.txtWeight.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    try {
                        items.get(position).setWeight(holder.txtWeight.getText().toString());
                    } catch (IndexOutOfBoundsException e){
                        // Do nothing, this is a pseudobug when it tries to change the text of a deleted set
                    }
                }
                @Override
                public void afterTextChanged(Editable s) {
                }
            });
            holder.txtReps.setFocusableInTouchMode(true);
            holder.txtReps.setClickable(true);
            holder.txtReps.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    try {
                        items.get(position).setReps(holder.txtReps.getText().toString());
                    } catch (IndexOutOfBoundsException e){
                        // Do nothing
                    }
                }
                @Override
                public void afterTextChanged(Editable s) {
                }
            });
            holder.parent.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    items.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, getItemCount());
                    return true;
                }
            });
        } else {
            holder.txtWeight.setFocusable(false);
            holder.txtWeight.setClickable(false);
            holder.txtReps.setFocusableInTouchMode(false);
            holder.txtReps.setClickable(false);
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
        notifyItemInserted(items.size());
        notifyItemRangeChanged(items.size(), getItemCount());
    }

    public void enableEdition(boolean enable) {
        this.editable = enable;
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
