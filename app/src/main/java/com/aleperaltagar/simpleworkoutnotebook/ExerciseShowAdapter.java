package com.aleperaltagar.simpleworkoutnotebook;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
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

import java.text.DateFormat;
import java.util.ArrayList;

public class ExerciseShowAdapter extends RecyclerView.Adapter<ExerciseShowAdapter.ViewHolder>{

    private ArrayList<Exercise> items = new ArrayList<>();
    private ArrayList<Exercise> preloadedItems = new ArrayList<>();
    private Context context;
    private int nextPositionLoaded;

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
        holder.date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment mainFragment = new MainFragment(items.get(position).getCalendar());
                FragmentTransaction transaction = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.container , mainFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        // Setting the sets recyclerview
        SetAdapter setsAdapter = new SetAdapter(context, items.get(position).getId(), true);
        setsAdapter.setItems(items.get(position).getSets());
        holder.setsRecView.setAdapter(setsAdapter);
        holder.setsRecView.setLayoutManager((new LinearLayoutManager(context, RecyclerView.VERTICAL, false)));
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public int setItems(ArrayList<Exercise> preloadedItems) {
        this.preloadedItems = preloadedItems;
        if (preloadedItems.size() > 5) {
            this.items = new ArrayList<>(preloadedItems.subList(0,5));
            this.nextPositionLoaded = 5;
        } else {
            this.items = preloadedItems;
            this.nextPositionLoaded = -1;
        }
        notifyDataSetChanged();
        return nextPositionLoaded;
    }

    public int loadMore() {
        ArrayList<Exercise> nextBatch;
        int newNextPositionLoaded;
        if (preloadedItems.size() - nextPositionLoaded > 5) {
            newNextPositionLoaded = nextPositionLoaded + 5;
        } else {
            newNextPositionLoaded = preloadedItems.size();
        }
        nextBatch = new ArrayList<>(preloadedItems.subList(nextPositionLoaded, newNextPositionLoaded));
        items.addAll(nextBatch);
        notifyItemRangeInserted(nextPositionLoaded, newNextPositionLoaded);
        nextPositionLoaded = newNextPositionLoaded;
//        Toast.makeText(context, String.valueOf(nextPositionLoaded), Toast.LENGTH_SHORT).show();
        return nextPositionLoaded;
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
