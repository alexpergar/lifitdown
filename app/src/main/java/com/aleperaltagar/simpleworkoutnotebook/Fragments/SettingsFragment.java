package com.aleperaltagar.simpleworkoutnotebook.Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.aleperaltagar.simpleworkoutnotebook.R;

public class SettingsFragment extends Fragment {

    private CheckBox checkWeight, checkReps, checkRestingTime, checkExecutionTime, checkFailure,
    checkRIR, checkTargetReps, checkMood, checkComment, checkNote, checkPersonal1, checkPersonal2;
    private TextView textToolbar;
    private RadioButton rbKg, rbLb;
    private ImageView editButtonToolbar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate layout
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        // Initialize views
        initViews(view);

        // Change toolbar text and disable click listener
        textToolbar.setOnClickListener(null);
        textToolbar.setText("Settings");
        editButtonToolbar.setVisibility(View.GONE);

        // Set preferences as they were saved
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("prefs",0);
        setPreferences(sharedPreferences);

        // Set check listeners
        SharedPreferences.Editor editor = sharedPreferences.edit();
        setClickListeners(editor);

        return view;
    }

    private void setPreferences(SharedPreferences sharedPreferences) {
        if (sharedPreferences.getBoolean("checkWeight", false)) {
            checkWeight.setChecked(true);
        }
        if (sharedPreferences.getBoolean("checkReps", false)) {
            checkReps.setChecked(true);
        }
        if (sharedPreferences.getBoolean("checkRestingTime", false)) {
            checkRestingTime.setChecked(true);
        }
        if (sharedPreferences.getBoolean("checkExecutionTime", false)) {
            checkExecutionTime.setChecked(true);
        }
        if (sharedPreferences.getBoolean("checkFailure", false)) {
            checkFailure.setChecked(true);
        }
        if (sharedPreferences.getBoolean("checkRIR", false)) {
            checkRIR.setChecked(true);
        }
        if (sharedPreferences.getBoolean("checkTargetReps", false)) {
            checkTargetReps.setChecked(true);
        }
        if (sharedPreferences.getBoolean("checkMood", false)) {
            checkMood.setChecked(true);
        }
        if (sharedPreferences.getBoolean("checkComment", false)) {
            checkComment.setChecked(true);
        }
        if (sharedPreferences.getBoolean("checkNote", false)) {
            checkNote.setChecked(true);
        }
        if (sharedPreferences.getBoolean("checkPersonal1", false)) {
            checkPersonal1.setChecked(true);
        }
        if (sharedPreferences.getBoolean("checkPersonal2", false)) {
            checkPersonal2.setChecked(true);
        }
        if (sharedPreferences.getString("weightUnit", null).equals("kg")) {
            rbKg.setChecked(true);
            rbLb.setChecked(false);
        } else {
            rbKg.setChecked(false);
            rbLb.setChecked(true);
        }
    }

    private void setClickListeners(SharedPreferences.Editor editor) {
        OnCheckedChangeListener checkListener = new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                switch (buttonView.getId()) {
                    case R.id.checkWeight:
                        editor.putBoolean("checkWeight", isChecked);
                        break;
                    case R.id.checkReps:
                        editor.putBoolean("checkReps", isChecked);
                        break;
                    case R.id.checkRestingTime:
                        editor.putBoolean("checkRestingTime", isChecked);
                        break;
                    case R.id.checkExecutionTime:
                        editor.putBoolean("checkExecutionTime", isChecked);
                        break;
                    case R.id.checkFailure:
                        editor.putBoolean("checkFailure", isChecked);
                        break;
                    case R.id.checkRIR:
                        editor.putBoolean("checkRIR", isChecked);
                        break;
                    case R.id.checkTargetReps:
                        editor.putBoolean("checkTargetReps", isChecked);
                        break;
                    case R.id.checkMood:
                        editor.putBoolean("checkMood", isChecked);
                        break;
                    case R.id.checkComment:
                        editor.putBoolean("checkComment", isChecked);
                        break;
                    case R.id.checkNote:
                        editor.putBoolean("checkNote", isChecked);
                        break;
                    case R.id.checkPersonal1:
                        editor.putBoolean("checkPersonal1", isChecked);
                        break;
                    case R.id.checkPersonal2:
                        editor.putBoolean("checkPersonal2", isChecked);
                        break;
                }
                editor.commit();
            }
        };

        // Every check listener
        checkWeight.setOnCheckedChangeListener(checkListener);
        checkReps.setOnCheckedChangeListener(checkListener);
        checkRestingTime.setOnCheckedChangeListener(checkListener);
        checkExecutionTime.setOnCheckedChangeListener(checkListener);
        checkFailure.setOnCheckedChangeListener(checkListener);
        checkRIR.setOnCheckedChangeListener(checkListener);
        checkTargetReps.setOnCheckedChangeListener(checkListener);
        checkMood.setOnCheckedChangeListener(checkListener);
        checkComment.setOnCheckedChangeListener(checkListener);
        checkNote.setOnCheckedChangeListener(checkListener);
        checkPersonal1.setOnCheckedChangeListener(checkListener);
        checkPersonal2.setOnCheckedChangeListener(checkListener);
        rbKg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("weightUnit", "kg");
                editor.commit();
            }
        });
        rbLb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("weightUnit", "lb");
                editor.commit();
            }
        });
    }

    private void initViews(View view) {
        editButtonToolbar = getActivity().findViewById(R.id.editButtonToolbar);
        textToolbar = getActivity().findViewById(R.id.textToolbar);
        checkWeight = view.findViewById(R.id.checkWeight);
        checkReps = view.findViewById(R.id.checkReps);
        checkRestingTime = view.findViewById(R.id.checkRestingTime);
        checkExecutionTime = view.findViewById(R.id.checkExecutionTime);
        checkFailure = view.findViewById(R.id.checkFailure);
        checkRIR = view.findViewById(R.id.checkRIR);
        checkTargetReps = view.findViewById(R.id.checkTargetReps);
        checkMood = view.findViewById(R.id.checkMood);
        checkComment = view.findViewById(R.id.checkComment);
        checkNote = view.findViewById(R.id.checkNote);
        checkPersonal1 = view.findViewById(R.id.checkPersonal1);
        checkPersonal2 = view.findViewById(R.id.checkPersonal2);
        rbKg = view.findViewById(R.id.rbKg);
        rbLb = view.findViewById(R.id.rbLb);
    }
}
