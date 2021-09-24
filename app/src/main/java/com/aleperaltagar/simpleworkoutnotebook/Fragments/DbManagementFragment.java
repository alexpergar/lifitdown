package com.aleperaltagar.simpleworkoutnotebook.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.aleperaltagar.simpleworkoutnotebook.DatabaseFiles.IODatabaseManager;
import com.aleperaltagar.simpleworkoutnotebook.MainActivity;
import com.aleperaltagar.simpleworkoutnotebook.R;
import com.aleperaltagar.simpleworkoutnotebook.Utils;

public class DbManagementFragment extends Fragment {

    private TextView textToolbar;
    private ImageView editButtonToolbar;
    private Button btnImport, btnExport, btnDeleteDb;

    public DbManagementFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_database_management, container, false);

        // Initialize views
        initViews(view);

        // Change toolbar text and disable click listener
        textToolbar.setOnClickListener(null);
        textToolbar.setText(R.string.data_management);
        editButtonToolbar.setVisibility(View.GONE);

        // Set click listeners
        btnExport.setOnClickListener(v -> {
            IODatabaseManager ioDatabaseManager = new IODatabaseManager(getActivity());
            ioDatabaseManager.exportDatabase();
        });

        btnImport.setOnClickListener(v -> ((MainActivity)getActivity()).pickFile());

        btnDeleteDb.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Warning")
                    .setMessage("Are you sure you want to delete the database?\nData cannot be recovered.")
                    .setPositiveButton("Delete", (dialog, which) -> {
                        Utils.deleteDatabase(getActivity());
                        Toast.makeText(getActivity(), "Database deleted successfully", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Cancel", (dialog, which) -> {

                    })
                    .create()
                    .show();
        });

        return view;
    }

    private void initViews(View view) {
        textToolbar = getActivity().findViewById(R.id.textToolbar);
        editButtonToolbar = getActivity().findViewById(R.id.editButtonToolbar);
        btnImport = view.findViewById(R.id.btnImport);
        btnExport = view.findViewById(R.id.btnExport);
        btnDeleteDb = view.findViewById(R.id.btnDeleteDb);
    }
}
