package com.aleperaltagar.simpleworkoutnotebook.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.text.InputType;
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
import androidx.recyclerview.widget.RecyclerView;

import com.aleperaltagar.simpleworkoutnotebook.Fragments.ExerciseFragment;
import com.aleperaltagar.simpleworkoutnotebook.R;
import com.aleperaltagar.simpleworkoutnotebook.Utils;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class ListOfExercisesAdapter extends RecyclerView.Adapter<ListOfExercisesAdapter.ViewHolder>{

    private ArrayList<String> items = new ArrayList<>();
    private final Context context;
    private String dialog_input = "";

    public ListOfExercisesAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_of_exercises_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        // Put exercise name
        holder.listOfExercisesName.setText(items.get(position));

        // When clicked, see that exercise previous entries
        holder.parent.setOnClickListener(v -> {
            Fragment exerciseFragment = new ExerciseFragment(items.get(position));
            FragmentTransaction transaction = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container , exerciseFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        });

        // Three dots button to rename or delete the exercise
        holder.moreButton.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Choose an option")
                    .setItems(R.array.options_exercise, (dialog, which) -> {
                        switch (which) {
                            case 0: // Rename
                                AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                                builder1.setTitle("New exercise name:");

                                // Input type specified. Current name as default input
                                final EditText input = new EditText(context);
                                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
                                input.setText(items.get(position));
                                builder1.setView(input);

                                // Buttons
                                builder1.setPositiveButton("Accept", (dialog12, which12) -> {
                                    dialog_input = input.getText().toString();
                                    Utils.updateEveryExerciseName(context, dialog_input, items.get(position));
                                    items.set(position, dialog_input);
                                    notifyItemChanged(position);
                                    Toast.makeText(context, "Exercise renamed", Toast.LENGTH_SHORT).show();
                                });
                                builder1.setNegativeButton("Cancel", (dialog13, which13) -> {
                                    dialog13.cancel();
                                    Toast.makeText(context, "Canceled", Toast.LENGTH_SHORT).show();
                                });

                                builder1.show();
                                break;
                            case 1: // Delete
                                new AlertDialog.Builder(context)
                                        .setTitle("Warning")
                                        .setMessage("Delete \"" + items.get(position) + "\"?")
                                        .setPositiveButton("Yes", (dialog1, which1) -> {
                                            Utils.deleteExerciseByName(context, items.get(position));
                                            Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
                                            items.remove(position);
                                            notifyItemRemoved(position);
                                            notifyItemRangeChanged(position, getItemCount());
                                        })
                                        .setNegativeButton("No", null)
                                        .show();
                            default:
                                break;
                        }
                    })
                    .create()
                    .show();
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(ArrayList<String> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private MaterialCardView parent;
        private TextView listOfExercisesName;
        private ImageView moreButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.listOfExerciseParent);
            listOfExercisesName = itemView.findViewById(R.id.listOfExercisesName);
            moreButton = itemView.findViewById(R.id.moreButton);
        }
    }
}