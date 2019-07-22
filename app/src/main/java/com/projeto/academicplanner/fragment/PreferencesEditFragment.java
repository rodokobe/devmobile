package com.projeto.academicplanner.fragment;

import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.projeto.academicplanner.R;
import com.projeto.academicplanner.helper.ConfigFirebase;
import com.projeto.academicplanner.model.UserProfile;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PreferencesEditFragment extends Fragment {


    private Button buttonPreferencesAdd;
    private EditText editTextHour;
    private TextView backToPrevious;
    private SearchableSpinner spinnerDuration;
    private String userIdLogged;

    private FirebaseAuth auth;
    private DatabaseReference firebaseRef;

    private PreferencesFragment preferencesFragment;

    private String[] numbers = new String[]{"1 hour", "2 hours", "3 hours", "4 hours", "5 hours", "6 hours", "7 hours", "8 hours"};


    public PreferencesEditFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_preferences_edit, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Edit Preferences");

        /**
         * Initializing Firebase
         */
        auth = ConfigFirebase.getReferenciaAutenticacao();
        firebaseRef = ConfigFirebase.getReferenciaFirebase();
        userIdLogged = ConfigFirebase.getUserId();

        initializingComponents(v);

        buttonPreferencesAdd.setText("update");

        /**
         * Recovering preferences on profile
         */

        preferencesRecovery();

        /**
         * Time Picker
         */

        editTextHour.setOnClickListener(view -> {

            TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                    editTextHour.setText(String.format("%02d:%02d", hourOfDay, minutes));
                }
            }, 0, 0, false);

            timePickerDialog.show();
        });

        /**
         * Saving new class data
         */
        buttonPreferencesAdd.setOnClickListener(view -> {
            updateData();
        });


        backToPrevious.setOnClickListener(view -> {
            backToPrevious();
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

                    editTextHour.setText(hour);

                    for (int i =0; i < numbers.length; i++){
                        if (numbers[i].equals(timeDuration)){
                            spinnerDuration.setSelection(i);
                        }
                    }

                } else {
                    editTextHour.setText("19:00");
                    spinnerDuration.setTitle("4 hours");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void updateData(){

        String hourToUpdate = editTextHour.getText().toString();
        String timeDurationToUpdate = spinnerDuration.getSelectedItem().toString();

        UserProfile userProfilePref = new UserProfile();
        userProfilePref.setIdUser(userIdLogged);
        userProfilePref.setDefaultHour(hourToUpdate);
        userProfilePref.setDefaultTimeDuration(timeDurationToUpdate);

        userProfilePref.savePreferences();
        toastMessageShort("Preferences updated");

        backToPrevious();

    }

    private void toastMessageShort(String msg) {
        Toast.makeText(getActivity(), msg,
                Toast.LENGTH_SHORT).show();
    }


    public void backToPrevious() {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        preferencesFragment = new PreferencesFragment();
        transaction.replace(R.id.framePreferencesMain, preferencesFragment);
        transaction.commit();
    }

    private void initializingComponents(View view) {

        backToPrevious = view.findViewById(R.id.backToPrevious);
        buttonPreferencesAdd = view.findViewById(R.id.buttonPreferencesAdd);
        editTextHour = view.findViewById(R.id.editTextHour);
        spinnerDuration = view.findViewById(R.id.spinnerDuration);

        /**
         * Setting 8 hours to initializes Spinner Duration Time
         */
        List<String> numbersList = new ArrayList<String>(Arrays.asList(numbers));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, numbersList);
        //specify the layout to appear list items
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //data bind adapter with both spinners
        spinnerDuration.setAdapter(adapter);
        spinnerDuration.setSelection(3);

    }

}
