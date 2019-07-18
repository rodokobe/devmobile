package com.projeto.academicplanner.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.projeto.academicplanner.R;
import com.projeto.academicplanner.activity.NavMainActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClassUpdateFragment extends Fragment {

    private Spinner spinnerDiscipline;
    private EditText subjectEditText, editTextDate, editTextHour, editTextTimeDuration, editTextClassroom, editTextContent;
    private TextView backToAddEditMain;


    public ClassUpdateFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_class_update, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Edit Class");

        initializingComponents(v);

        backToAddEditMain.setOnClickListener( view -> {
            backToMain();
        });

        return v;
    }

    private void initializingComponents(View view) {

        spinnerDiscipline = view.findViewById(R.id.spinnerDiscipline);
        subjectEditText = view.findViewById(R.id.subjectEditText);
        editTextDate = view.findViewById(R.id.editTextDate);
        editTextHour = view.findViewById(R.id.editTextHour);
        editTextTimeDuration = view.findViewById(R.id.editTextTimeDuration);
        editTextClassroom = view.findViewById(R.id.editTextClassroom);
        editTextContent = view.findViewById(R.id.editTextContent);
        backToAddEditMain = view.findViewById(R.id.backToAddEditMain);

    }

    private void backToMain(){
        startActivity( new Intent( getActivity(), NavMainActivity.class) );
    }

}
