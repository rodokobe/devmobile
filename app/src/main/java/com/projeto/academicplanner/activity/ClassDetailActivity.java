package com.projeto.academicplanner.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.projeto.academicplanner.R;
import com.projeto.academicplanner.helper.ConfigFirebase;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.w3c.dom.Text;

public class ClassDetailActivity extends AppCompatActivity {

    private Text subjectEditText, editTextClassroom, editTextContent, editTextDate, editTextHour;
    private Text textDiscipline;
    private Button buttonClassAdd;

    private String userIdLogged;

    private FirebaseAuth auth;
    private DatabaseReference firebaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Class Detail");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        auth = ConfigFirebase.getReferenciaAutenticacao();
        firebaseRef = ConfigFirebase.getReferenciaFirebase();
        userIdLogged = ConfigFirebase.getUserId();

        initializingComponentes();
    }

    private void initializingComponentes(){



    };
}
