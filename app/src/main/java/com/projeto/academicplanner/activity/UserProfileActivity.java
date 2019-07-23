package com.projeto.academicplanner.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.projeto.academicplanner.R;
import com.projeto.academicplanner.fragment.UserProfileFragment;

public class UserProfileActivity extends AppCompatActivity {

    private UserProfileFragment userProfileFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("User Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        showUserProfileFragment();

    }

    private void showUserProfileFragment(){
        userProfileFragment = new UserProfileFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameAddEditUserProfile, userProfileFragment);
        transaction.commit();
    }

}
