package com.projeto.academicplanner.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.projeto.academicplanner.R;
import com.projeto.academicplanner.helper.ConfigFirebase;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClassDetailFragment extends Fragment {

    private SearchableSpinner spinnerDiscipline;
    private Text subjectEditText, editTextClassroom, editTextContent, editTextDate, editTextHour;
    private Text textDiscipline;
    private Button buttonClassAdd;

    private String userIdLogged;

    private FirebaseAuth auth;
    private DatabaseReference firebaseRef;

    public ClassDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View classDetailView = inflater.inflate(R.layout.fragment_class_detail, container, false);

        initializingComponentes(classDetailView);

        auth = ConfigFirebase.getReferenciaAutenticacao();
        firebaseRef = ConfigFirebase.getReferenciaFirebase();
        userIdLogged = ConfigFirebase.getUserId();



        return classDetailView;
    }

    private void initializingComponentes(View view){

        textDiscipline = view.findViewById(R.id.textDiscipline);
        subjectEditText = view.findViewById(R.id.subjectEditText);
        editTextDate = view.findViewById(R.id.editTextDate);
        editTextClassroom = view.findViewById(R.id.editTextClassroom);
        editTextContent = view.findViewById(R.id.editTextContent);
        buttonClassAdd = view.findViewById(R.id.buttonClassAdd);

    };

}
