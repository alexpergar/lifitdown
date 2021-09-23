package com.aleperaltagar.simpleworkoutnotebook;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

import java.util.Calendar;

public class AboutFragment extends Fragment {

    private TextView textToolbar;
    private ImageView editButtonToolbar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.about, container, false);

        // Initialize each view inside the fragment
        initViews(view);

        // Disable toolbar click listener and edit button visibility
        textToolbar.setText("About");
        textToolbar.setOnClickListener(null);
        editButtonToolbar.setVisibility(View.GONE);

        return view;
    }

    private void initViews(View view) {
        textToolbar = (TextView) getActivity() .findViewById(R.id.textToolbar);
        editButtonToolbar = (ImageView) getActivity().findViewById(R.id.editButtonToolbar);
    }

}
