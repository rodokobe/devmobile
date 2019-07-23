package com.projeto.academicplanner.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.projeto.academicplanner.R;
import com.projeto.academicplanner.helper.ConfigFirebase;
import com.projeto.academicplanner.model.Classes;

public class ClassDetailActivity extends AppCompatActivity {

    private TextView classType, classHour, classDate, classDuration, classUniversity, classCourse,
                     classDiscipline, classSubject, classTopicsAndContents;
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

        Intent intentGetParameters = getIntent();
        Classes classToShow = intentGetParameters.getParcelableExtra("ClassToDetail");

        String hour = classToShow.getClassTime();
        String date = classToShow.getClassDate();
        String duration = classToShow.getTimeDuration();
        String university = classToShow.getNameUniversity();
        String course = classToShow.getNameCourse();
        String discipline = classToShow.getNameDiscipline();
        String subject = classToShow.getSubject();
        String topicsAndContents = classToShow.getTopicsAndContents();

        classType.setText("CLASS TYPE");
        classHour.setText(hour);
        classDate.setText(date);
        classDuration.setText(duration);
        classUniversity.setText(university);
        classCourse.setText(course);
        classDiscipline.setText(discipline);
        classSubject.setText(subject);
        classTopicsAndContents.setText(topicsAndContents);
    }

    private void initializingComponentes() {

        classType = findViewById(R.id.classType);
        classHour = findViewById(R.id.classHour);
        classDate = findViewById(R.id.classDate);
        classDuration = findViewById(R.id.classDuration);
        classUniversity = findViewById(R.id.classUniversity);
        classCourse = findViewById(R.id.classCourse);
        classDiscipline = findViewById(R.id.classDiscipline);
        classSubject = findViewById(R.id.classSubject);
        classTopicsAndContents = findViewById(R.id.classTopicsAndContents);

    }
}
