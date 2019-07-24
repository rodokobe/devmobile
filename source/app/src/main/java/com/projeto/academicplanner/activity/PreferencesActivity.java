package com.projeto.academicplanner.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.projeto.academicplanner.R;
import com.projeto.academicplanner.fragment.PreferencesFragment;
import com.projeto.academicplanner.fragment.UserProfileFragment;

public class PreferencesActivity extends AppCompatActivity {

    private PreferencesFragment preferencesFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        /**
         * Calling Toolbar
         */
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /**
         * Setting Title
         */
        getSupportActionBar().setTitle("Preferences");

        /**
         * Enabling go to back on toolbar
         */
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        /**
         * Charging a fragment
         */
        showPreferencesFragment();

    }

    private void showPreferencesFragment(){

        preferencesFragment = new PreferencesFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.framePreferencesMain, preferencesFragment);
        transaction.commit();

    }

}
