package com.aleperaltagar.simpleworkoutnotebook;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.aleperaltagar.simpleworkoutnotebook.DatabaseFiles.ExercisesDatabase;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private MaterialToolbar toolbar;

    private static final int READ_REQUEST_CODE = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                        clearBackstack();
                        Fragment notebookFragment = new MainFragment(currentDay);
                        FragmentTransaction notebookTransaction = getSupportFragmentManager().beginTransaction();
                        notebookTransaction.replace(R.id.container , notebookFragment);
                        notebookTransaction.commit();
                        break;
                    case R.id.exercises:
                        clearBackstack();
                        Fragment listOfExercisesFragment = new ListOfExercisesFragment();
                        FragmentTransaction listOfExercisesTransaction = getSupportFragmentManager().beginTransaction();
                        listOfExercisesTransaction.replace(R.id.container , listOfExercisesFragment);
                        listOfExercisesTransaction.commit();
                        break;
                    case R.id.settings:
                        clearBackstack();
                        Fragment settingsFragment = new SettingsFragment();
                        FragmentTransaction settingsTransaction = getSupportFragmentManager().beginTransaction();
                        settingsTransaction.replace(R.id.container , settingsFragment);
                        settingsTransaction.commit();
                        break;
                    case R.id.manageDatabase:
                        clearBackstack();
                        Fragment dbManagementFragment = new DbManagementFragment();
                        FragmentTransaction dbManagementTransaction = getSupportFragmentManager().beginTransaction();
                        dbManagementTransaction.replace(R.id.container , dbManagementFragment);
                        dbManagementTransaction.commit();
                        break;
                    case R.id.about:
                        clearBackstack();
                        Fragment dbAboutFragment= new AboutFragment();
                        FragmentTransaction dbAboutFragmentTransaction = getSupportFragmentManager().beginTransaction();
                        dbAboutFragmentTransaction.replace(R.id.container , dbAboutFragment);
                        dbAboutFragmentTransaction.commit();
                        break;
                    default:
                        break;
                }
                drawer.closeDrawers();
                return false;
            }
        });

    }

    private void initViews() {
        drawer = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navigationView);
        toolbar = findViewById(R.id.toolbar);
    }

    public void pickFile() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("text/plain");
        startActivityForResult(intent, READ_REQUEST_CODE);
    }

    private void clearBackstack() {
        FragmentManager fm = getSupportFragmentManager();
        for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == READ_REQUEST_CODE && resultCode == RESULT_OK) {
            ArrayList<String> imported = new ArrayList<>();
            if (data != null) {
                Uri uri = data.getData();
                try {
                    InputStream inputStream = getContentResolver().openInputStream(uri);
                    BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
                    String mLine;
                    while ((mLine = r.readLine()) != null) {
                        imported.add(mLine);
                    }
                    IODatabaseManager ioDatabaseManager = new IODatabaseManager(getApplicationContext());
                    ioDatabaseManager.importDatabase(this, imported);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}