package com.aleperaltagar.simpleworkoutnotebook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.aleperaltagar.simpleworkoutnotebook.DatabaseFiles.ExercisesDatabase;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawer;
    private NavigationView navigationView;
    private MaterialToolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        ExercisesDatabase.getInstance(this).exerciseDao().nukeTable();

        Utils.initiateSharedPreferences(this);

        initViews();

        // Set today's date
        Calendar currentDay = Calendar.getInstance();
        currentDay.set(Calendar.HOUR_OF_DAY, 0);
        currentDay.set(Calendar.MINUTE, 0);
        currentDay.set(Calendar.SECOND, 0);
        currentDay.set(Calendar.MILLISECOND, 0);

        // Fragment manager
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new MainFragment(currentDay)).commit();

        // Navigation drawer
        setSupportActionBar(toolbar);
        if (null != getSupportActionBar()) {
            getSupportActionBar().setDisplayShowTitleEnabled(false); // disable default bar title
        }
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // Navigation drawer listener
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.notebook:
                        Fragment notebookFragment = new MainFragment(currentDay);
                        FragmentTransaction notebookTransaction = getSupportFragmentManager().beginTransaction();
                        notebookTransaction.replace(R.id.container , notebookFragment);
                        notebookTransaction.addToBackStack(null);
                        notebookTransaction.commit();
                        break;
                    case R.id.exercises:
                        Fragment listOfExercisesFragment = new ListOfExercisesFragment();
                        FragmentTransaction listOfExercisesTransaction = getSupportFragmentManager().beginTransaction();
                        listOfExercisesTransaction.replace(R.id.container , listOfExercisesFragment);
                        listOfExercisesTransaction.addToBackStack(null);
                        listOfExercisesTransaction.commit();
                        break;
                    case R.id.settings:
                        Fragment settingsFragment = new SettingsFragment();
                        FragmentTransaction settingsTransaction = getSupportFragmentManager().beginTransaction();
                        settingsTransaction.replace(R.id.container , settingsFragment);
                        settingsTransaction.addToBackStack(null);
                        settingsTransaction.commit();
                        break;
                    default:
                        break;
                }
                return false;
            }
        });

    }

    private void initViews() {
        drawer = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navigationView);
        toolbar = findViewById(R.id.toolbar);
    }
}