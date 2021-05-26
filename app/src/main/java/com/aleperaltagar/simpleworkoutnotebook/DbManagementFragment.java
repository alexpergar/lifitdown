package com.aleperaltagar.simpleworkoutnotebook;

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
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

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

        initViews(view);

        // Change toolbar text and disable click listener
        textToolbar.setOnClickListener(null);
        textToolbar.setText("Data management");
        editButtonToolbar.setVisibility(View.GONE);

        // Set click listeners
        btnExport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IODatabaseManager ioDatabaseManager = new IODatabaseManager(getActivity());
                ioDatabaseManager.exportDatabase();
            }
        });

        btnImport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).pickFile();
            }
        });

        btnDeleteDb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Warning")
                        .setMessage("Are you sure you want to delete the database?\nData cannot be recovered.")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Utils.deleteDatabase(getActivity());
                                Toast.makeText(getActivity(), "Database deleted successfully", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .create()
                        .show();
            }
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
