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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.text.DateFormat;
import java.util.ArrayList;

public class ExerciseShowAdapter extends RecyclerView.Adapter<ExerciseShowAdapter.ViewHolder>{

    private ArrayList<Exercise> items = new ArrayList<>();
    private Context context;

    public ExerciseShowAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercise_show_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        // Setting the date
        String dateString = DateFormat.getDateInstance().format(items.get(position).getCalendar().getTime());
        holder.date.setText(dateString);

        // Setting the sets recyclerview
        SetAdapter setsAdapter = new SetAdapter(context, items.get(position).getId());
        setsAdapter.setItems(items.get(position).getSets());
        holder.setsRecView.setAdapter(setsAdapter);
        holder.setsRecView.setLayoutManager((new LinearLayoutManager(context, RecyclerView.VERTICAL, false)));
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(ArrayList<Exercise> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private MaterialCardView parent;
        private RecyclerView setsRecView;
        private TextView date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.showExerciseParent);
            setsRecView = itemView.findViewById(R.id.showSetsRecView);
            date = itemView.findViewById(R.id.showExerciseDate);
        }
    }
}
