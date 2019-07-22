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

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Preferences");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        showPreferencesFragment();

    }

    private void showPreferencesFragment(){

        preferencesFragment = new PreferencesFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.framePreferencesMain, preferencesFragment);
        transaction.commit();

    }

}
