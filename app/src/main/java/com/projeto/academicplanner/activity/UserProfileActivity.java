package com.projeto.academicplanner.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;
import com.projeto.academicplanner.R;
import com.projeto.academicplanner.fragment.UserProfileEditFragment;
import com.projeto.academicplanner.fragment.UserProfileFragment;
import com.projeto.academicplanner.helper.ConfigFirebase;

public class UserProfileActivity extends AppCompatActivity {

    private FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

    private UserProfileFragment userProfileFragment;
    private UserProfileEditFragment userProfileEditFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        showUserProfileFragment();

    }

    private void showUserProfileFragment(){
        userProfileFragment = new UserProfileFragment();
        transaction.replace(R.id.frameAddEditUserProfile, userProfileFragment);
        transaction.commit();
    }

}
