package com.aleperaltagar.simpleworkoutnotebook.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.aleperaltagar.simpleworkoutnotebook.R;
import com.aleperaltagar.simpleworkoutnotebook.Utils;

import java.util.Calendar;

public class NoteFragment extends Fragment {

    private int exerciseId;
    private int setId;
    private String noteText;
    private EditText txtOnNote;
    private Button btnDone;
    private Calendar calendar;
    private TextView textToolbar;
    private ImageView editButtonToolbar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Pick up the arguments from previous fragment
        pickUpArguments();

        // Initialize the view
        View view = inflater.inflate(R.layout.fragment_note, container, false);

        // Initialize each view inside the fragment
        initViews(view);

        // Disable toolbar click listener and edit button visibility
        textToolbar.setOnClickListener(null);
        editButtonToolbar.setVisibility(View.GONE);

        // Set the note and a click listener to commit changes
        txtOnNote.setText(noteText);
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Saves all the sets in this exercise changing this set note
                Utils.updateSetNoteById(getContext(), exerciseId, setId, txtOnNote.getText().toString());
                Fragment notebookFragment = new MainFragment(calendar);
                // Transaction to the notebook's corresponding day
                FragmentTransaction notebookTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                notebookTransaction.replace(R.id.container , notebookFragment);
                int backStackEntryCount = getActivity().getSupportFragmentManager().getBackStackEntryCount();
                // Delete all the fragment backstack
                for (int i = 0; i < backStackEntryCount; i++) {
                    getActivity().getSupportFragmentManager().popBackStack();
                }
                notebookTransaction.commit();
            }
        });

        return view;
    }

    // Pick arguments from previous fragment
    private void pickUpArguments() {
        exerciseId = getArguments().getInt("exerciseId");
        setId = getArguments().getInt("setId");
        noteText = getArguments().getString("noteText");
        calendar = Utils.getItemById(getContext(), exerciseId).getCalendar();
    }

    private void initViews(View view) {
        txtOnNote = view.findViewById(R.id.txtOnNote);
        btnDone = view.findViewById(R.id.btnDone);
        textToolbar = (TextView) getActivity() .findViewById(R.id.textToolbar);
        editButtonToolbar = (ImageView) getActivity().findViewById(R.id.editButtonToolbar);
    }
}
