package com.projeto.academicplanner.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.projeto.academicplanner.R;
import com.projeto.academicplanner.activity.NavMainActivity;
import com.projeto.academicplanner.helper.ConfigFirebase;
import com.projeto.academicplanner.model.UserProfile;

/**
 * A simple {@link Fragment} subclass.
 */
public class PreferencesFragment extends Fragment {

    private Button buttonPreferencesAdd;
    private TextView textDefaultHour, textDefaultTimeDuration;
    private String userIdLogged;

    private FirebaseAuth auth;
    private DatabaseReference firebaseRef;

    private PreferencesEditFragment preferencesEditFragment;


    public PreferencesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_preferences, container, false);

        /**
         * Initializing Firebase
         */
        auth = ConfigFirebase.getReferenciaAutenticacao();
        firebaseRef = ConfigFirebase.getReferenciaFirebase();
        userIdLogged = ConfigFirebase.getUserId();

        initializingComponents(v);

        /**
         * Recovering preferences on profile
         */

        preferencesRecovery();

        /**
         * Go to edit preferences
         */
        buttonPreferencesAdd.setOnClickListener(view -> {
            showPreferencesEditFragment();
        });

        return v;
    }

    public void preferencesRecovery(){
        DatabaseReference userProfileRef = firebaseRef
                .child("users")
                .child( userIdLogged )
                .child("preferences");

        userProfileRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null){
                    UserProfile userProfilePref = dataSnapshot.getValue(UserProfile.class);

                    String hour = userProfilePref.getDefaultHour();
                    String timeDuration = userProfilePref.getDefaultTimeDuration();

                    textDefaultHour.setText(hour);
                    textDefaultTimeDuration.setText(timeDuration);

                } else {
                    textDefaultHour.setText("-");
                    textDefaultTimeDuration.setText("-");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void backToMain() {
        startActivity(new Intent(getActivity(), NavMainActivity.class));
    }

    private void showPreferencesEditFragment(){

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        preferencesEditFragment = new PreferencesEditFragment();
        transaction.replace(R.id.framePreferencesMain, preferencesEditFragment);
        transaction.commit();
    }

    private void initializingComponents(View view) {

        buttonPreferencesAdd = view.findViewById(R.id.buttonPreferencesAdd);
        textDefaultHour = view.findViewById(R.id.textDefaultHour);
        textDefaultTimeDuration = view.findViewById(R.id.textDefaultTimeDuration);

    }

}
