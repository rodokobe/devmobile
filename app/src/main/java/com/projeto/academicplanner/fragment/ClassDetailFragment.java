package com.projeto.academicplanner.fragment;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.projeto.academicplanner.Interface.IFirebaseLoadDoneDiscipline;
import com.projeto.academicplanner.R;
import com.projeto.academicplanner.helper.ConfigFirebase;
import com.projeto.academicplanner.model.Classes;
import com.projeto.academicplanner.model.Discipline;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClassDetailFragment extends Fragment {


    private Text textSubject, textDate, textHour, textTimeDuration,
            textClassroom, textDiscipline, textTopicsContents;


    private String idUserLogged;

    private FirebaseAuth auth;
    private DatabaseReference firebaseRef;
    private Classes classToUpdate;

    private DatabaseReference databaseClassesReference, databaseDisciplineReference;
    private IFirebaseLoadDoneDiscipline iFirebaseLoadDoneDiscipline;

    public ClassDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        classToUpdate = (Classes) getArguments().getSerializable("ClassToUpdate");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View classDetailView = inflater.inflate(R.layout.fragment_class_detail, container, false);

        initializingComponentes(classDetailView);

        auth = ConfigFirebase.getReferenciaAutenticacao();
        firebaseRef = ConfigFirebase.getReferenciaFirebase();
        idUserLogged = ConfigFirebase.getUserId();

        Discipline discipline = new Discipline();
        Classes classes = new Classes();

        databaseClassesReference = FirebaseDatabase.getInstance()
                .getReference("disciplines").child(idUserLogged)
                .child(discipline.getIdDiscipline())
                .child("classes")
                .child(classes.getIdClass());

        textSubject.setTextContent(classToUpdate.getSubject());
        textDate.setTextContent(classToUpdate.getClassDate());
        textHour.setTextContent(classToUpdate.getClassTime());
        textTimeDuration.setTextContent(classToUpdate.getTimeDuration());
        textClassroom.setTextContent(classToUpdate.getClassroom());
        textDiscipline.setTextContent(classToUpdate.getNameDiscipline());
        textTopicsContents.setTextContent(classToUpdate.getTimeDuration());

        return classDetailView;
    }

    private void initializingComponentes(View view){

        textDiscipline = view.findViewById(R.id.textDiscipline);
        textSubject = view.findViewById(R.id.textSubject);
        textHour = view.findViewById(R.id.textHour);
        textDate = view.findViewById(R.id.textDate);
        textTimeDuration = view.findViewById(R.id.textTimeDuration);
        textClassroom = view.findViewById(R.id.textClassroom);
        textTopicsContents = view.findViewById(R.id.textTopicsContents);

    };

}
