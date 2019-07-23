package com.projeto.academicplanner.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.projeto.academicplanner.Interface.IFirebaseLoadDoneDiscipline;
import com.projeto.academicplanner.R;
import com.projeto.academicplanner.helper.ConfigFirebase;
import com.projeto.academicplanner.helper.SendMail;
import com.projeto.academicplanner.model.AdminPeople;
import com.projeto.academicplanner.model.Classes;
import com.projeto.academicplanner.model.Student;


public class ClassUpdateActivity extends AppCompatActivity {

    private TextView classHour, classDate, classSubject, classTopicsAndContents, classRoom,
            classUniversity, classCourse, classDuration, classDiscipline, classSemester, classYear;
    private Button buttonClassAdd;

    private EditText editTextDate, editTextHour, subjectEditText, editTextContent;

    private String semester1, yearD, classroom, university, course, discipline, duration, subject,
            topicsAndContents, idDiscipline, idClass, idUniversity, idCourse, hour, date, newClassDate,
            newClassTime, newSubject;

    private String userIdLogged;
    private DatabaseReference databaseDisciplineReference, firebaseRefDisciplines;
    private DatabaseReference adminPeopleRef;
    private IFirebaseLoadDoneDiscipline iFirebaseLoadDoneDiscipline;
    private String[] numbers = new String[]{"1 hour", "2 hours", "3 hours", "4 hours", "5 hours", "6 hours", "7 hours", "8 hours"};

    private FirebaseAuth auth;
    private DatabaseReference firebaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_update);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Edit Class");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        auth = ConfigFirebase.getReferenciaAutenticacao();
        firebaseRef = ConfigFirebase.getReferenciaFirebase();
        userIdLogged = ConfigFirebase.getUserId();

        initializingComponentes();

        Intent intentGetParameters = getIntent();
        Classes classToShow = intentGetParameters.getParcelableExtra("ClassToUpdate");

        hour = classToShow.getClassTime();
        date = classToShow.getClassDate();
        semester1 = classToShow.getSemester();
        yearD = classToShow.getNameYear();
        classroom = classToShow.getClassroom();
        duration = classToShow.getTimeDuration();
        university = classToShow.getNameUniversity();
        course = classToShow.getNameCourse();
        discipline = classToShow.getNameDiscipline();
        subject = classToShow.getSubject();
        topicsAndContents = classToShow.getTopicsAndContents();
        idDiscipline = classToShow.getIdDiscipline();
        idClass = classToShow.getIdClass();
        idUniversity = classToShow.getIdUniversity();
        idCourse = classToShow.getIdCourse();

        //classType.setText("Special Class");
        editTextHour.setText(hour);
        editTextDate.setText(date);
        classDuration.setText(duration);
        classSemester.setText(semester1);
        classRoom.setText(classroom);
        classUniversity.setText(university);
        classCourse.setText(course);
        classDiscipline.setText(discipline);
        subjectEditText.setText(subject);
        editTextContent.setText(topicsAndContents);

        buttonClassAdd.setOnClickListener(v -> {
            newClassDate = editTextDate.getText().toString();
            newClassTime = editTextHour.getText().toString();
            newSubject = subjectEditText.getText().toString();

            Classes classes = new Classes();
            classes.setIdUser(userIdLogged);
            classes.setClassDate(newClassDate);
            classes.setClassTime(newClassTime);
            classes.setSubject(newSubject);
            classes.setTopicsAndContents(editTextContent.getText().toString());
            classes.setIdUniversity(idUniversity);
            classes.setIdDiscipline(idDiscipline);
            classes.setIdCourse(idCourse);
            classes.setIdClass(idClass);
            classes.setSubject(subject);
            classes.setTimeDuration(duration);
            classes.setNameUniversity(university);
            classes.setNameCourse(course);
            classes.setNameDiscipline(discipline);
            classes.setNameYear(yearD);
            classes.setClassroom(classroom);

            classes.save();

            if (!date.equals(newClassDate) || !hour.equals(newClassTime)) {
                allStudentsEmail(idDiscipline);
                adminPeopleCourseEmail(idCourse);
            }

            toastMessageShort("Class has been updated");

            finish();
        });

    }

    public void adminPeopleCourseEmail (String idCourse) {

        DatabaseReference adminPeople = adminPeopleRef
                .child("courses")
                .child(userIdLogged)
                .child(idCourse)
                .child("adminPeople");

        adminPeople.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snap: dataSnapshot.getChildren()){
                    AdminPeople adminPeople = snap.getValue(AdminPeople.class);

                    String adminPeopleEmail = adminPeople.getAdminPeopleEmail();
                    System.out.println("Emails -> " + adminPeople);

                    sendEmail(adminPeopleEmail);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void allStudentsEmail(String idDiscipline) {

        DatabaseReference students = databaseDisciplineReference
                .child(idDiscipline)
                .child("students");

        students.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snap: dataSnapshot.getChildren()){
                    Student student = snap.getValue(Student.class);

                    String studentEmail = student.getStudentEmail();

                    Log.i("Emails: ", studentEmail);
                    sendEmail(studentEmail);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void sendEmail (String sendTo) {
        //Getting content for email

        String email = sendTo;
        String subject = "Info :: Alteração na aula de " + classDate + " às " + classHour;
        String message = "A aula foi alterada de " + classDate + " às " + classHour + " para " + newClassDate + " às " + newClassTime + ".\n" +
                "Cumprimentos.";


        //Creating SendMail object
        SendMail sm = new SendMail(getApplicationContext(), email, subject, message);

        //Executing sendmail to send email
        sm.execute();
    }

    private void toastMessageShort(String msg) {
        Toast.makeText(this, msg,
                Toast.LENGTH_SHORT).show();
    }

    private void toastMessageLong(String msg) {
        Toast.makeText( this, msg,
                Toast.LENGTH_LONG).show();
    }

    private void initializingComponentes() {

        editTextHour = findViewById(R.id.editTextHour);
        editTextDate = findViewById(R.id.editTextDate);
        classDuration = findViewById(R.id.txtTImeDuration);
        classSemester = findViewById(R.id.txtSemester);
        classRoom = findViewById(R.id.txtClassroom);
        classUniversity = findViewById(R.id.textUniversity);
        classCourse = findViewById(R.id.txtCourse);
        classDiscipline = findViewById(R.id.txtDiscipline);
        subjectEditText = findViewById(R.id.subjectEditText);
        editTextContent = findViewById(R.id.editTextContent);

        buttonClassAdd = findViewById(R.id.buttonClassAdd);

    }
}


