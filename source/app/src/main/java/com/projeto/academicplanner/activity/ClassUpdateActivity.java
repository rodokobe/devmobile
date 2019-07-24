package com.projeto.academicplanner.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.projeto.academicplanner.Interface.IFirebaseLoadDoneDiscipline;
import com.projeto.academicplanner.R;
import com.projeto.academicplanner.helper.ConfigFirebase;
import com.projeto.academicplanner.helper.SendMail;
import com.projeto.academicplanner.model.AdminPeople;
import com.projeto.academicplanner.model.Classes;
import com.projeto.academicplanner.model.Student;

import java.util.Calendar;


public class ClassUpdateActivity extends AppCompatActivity {

    private TextView classHour, classDate, classSubject, classTopicsAndContents, classRoom,
            classUniversity, classCourse, classDuration, classDiscipline, classSemester, classYear, textViewConteudo;
    private Button buttonClassAdd;

    private EditText editTextDate, editTextHour, subjectEditText, editTextContent;

    private String semester1, yearD, classroom, university, course, discipline, duration, subject,
            topicsAndContents, idDiscipline, idClass, idUniversity, idCourse, hour, date, newClassDate,
            newClassTime, newSubject, isSpecialEvent;

    private String userIdLogged;
    private DatabaseReference databaseDisciplineReference, firebaseRefDisciplines;
    private DatabaseReference adminPeopleRef;
    private IFirebaseLoadDoneDiscipline iFirebaseLoadDoneDiscipline;
    private String[] numbers = new String[]{"1 hour", "2 hours", "3 hours", "4 hours", "5 hours", "6 hours", "7 hours", "8 hours"};
    private DatePickerDialog.OnDateSetListener setListener;

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
        isSpecialEvent = classToShow.getIsSpecialEvent();

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

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        /**
         * Date Picker
         */
        editTextDate.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, setListener, year, month, day);
            datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            datePickerDialog.show();
        });

        setListener = (view, thisYear, thisMonth, dayOfMonth) -> {
            thisMonth = thisMonth + 1;
            String date = dayOfMonth + "/" + thisMonth + "/" + thisYear;
            editTextDate.setText(date);
        };

        editTextDate.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    this, (view, thisYear, thisMonth, dayOfMonth) -> {
                thisMonth = thisMonth + 1;
                editTextDate.setText(String.format("%02d/%02d/%04d", dayOfMonth, thisMonth, thisYear));
            }, year, month, day);
            datePickerDialog.show();
        });

        /**
         * Time Picker
         */

        editTextHour.setOnClickListener(view -> {

            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                    editTextHour.setText(String.format("%02d:%02d", hourOfDay, minutes));
                }
            }, 0, 0, false);

            timePickerDialog.show();
        });


        /**
         * Setting title do activity among Regular Class, Special Class and Special Event when Update
         */

        if (topicsAndContents == null) {
            editTextContent.setVisibility(View.GONE);
            textViewConteudo.setVisibility(View.GONE);
            getSupportActionBar().setTitle("Edit Special Class");
        } else if (isSpecialEvent == null) {
            getSupportActionBar().setTitle("Edit Class");
        } else if (isSpecialEvent.equals("Yes")) {
            getSupportActionBar().setTitle("Edit Special Event");
        }


        /**
         * Save new data
         */
        buttonClassAdd.setOnClickListener(v -> {
            newClassDate = editTextDate.getText().toString();
            newClassTime = editTextHour.getText().toString();
            newSubject = subjectEditText.getText().toString();


            Classes classes = new Classes();

            if (topicsAndContents == null) {
                classes.setIdUser(userIdLogged);
                classes.setClassDate(newClassDate);
                classes.setClassTime(newClassTime);
                classes.setSubject(newSubject);
                classes.setIdUniversity(idUniversity);
                classes.setIdDiscipline(idDiscipline);
                classes.setIdCourse(idCourse);
                classes.setIdClass(idClass);
                classes.setTimeDuration(duration);
                classes.setNameUniversity(university);
                classes.setNameCourse(course);
                classes.setNameDiscipline(discipline);
                classes.setNameYear(yearD);
                classes.setClassroom(classroom);
                classes.setSemester(semester1);

                if (!date.equals(newClassDate) || !hour.equals(newClassTime)) {
                    if (idDiscipline != null) {
                        allStudentsEmail(idDiscipline);
                    }
                    if (idCourse != null) {
                        adminPeopleCourseEmail(idCourse);
                    }
                }

            } else if (isSpecialEvent == null) {
                classes.setIdUser(userIdLogged);
                classes.setClassDate(newClassDate);
                classes.setClassTime(newClassTime);
                classes.setSubject(newSubject);
                classes.setTopicsAndContents(editTextContent.getText().toString());
                classes.setIdUniversity(idUniversity);
                classes.setIdDiscipline(idDiscipline);
                classes.setIdCourse(idCourse);
                classes.setIdClass(idClass);
                classes.setTimeDuration(duration);
                classes.setNameUniversity(university);
                classes.setNameCourse(course);
                classes.setNameDiscipline(discipline);
                classes.setNameYear(yearD);
                classes.setClassroom(classroom);
                classes.setSemester(semester1);

                if (!date.equals(newClassDate) || !hour.equals(newClassTime)) {
                    if (idDiscipline != null) {
                        allStudentsEmail(idDiscipline);
                    }
                    if (idCourse != null) {
                        adminPeopleCourseEmail(idCourse);
                    }
                }

            } else if (isSpecialEvent.equals("Yes")) {
                classes.setIdUser(userIdLogged);
                classes.setClassDate(newClassDate);
                classes.setClassTime(newClassTime);
                classes.setSubject(newSubject);
                classes.setTopicsAndContents(editTextContent.getText().toString());
                classes.setIdUniversity(idUniversity);
                classes.setIdDiscipline(idDiscipline);
                classes.setIdCourse(idCourse);
                classes.setIdClass(idClass);
                classes.setTimeDuration(duration);
                classes.setNameUniversity(university);
                classes.setNameCourse(course);
                classes.setNameDiscipline(discipline);
                classes.setNameYear(yearD);
                classes.setClassroom(classroom);
                classes.setSemester(semester1);
                classes.setIsSpecialEvent(isSpecialEvent);
            }

            classes.save();

            toastMessageShort("Class has been updated");

            startActivity(new Intent(this, NavMainActivity.class));
        });

    }

    /**
     * Receives idCourse to send email only for administrative people assigned on course
     *
     * @param idCourse
     */

    public void adminPeopleCourseEmail(String idCourse) {

        if (idCourse != null) {

            DatabaseReference adminPeople = FirebaseDatabase.getInstance().getReference("courses")
                    .child(userIdLogged)
                    .child(idCourse)
                    .child("adminPeople");

            System.out.println("Emails -> " + adminPeople);

            adminPeople.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot snap : dataSnapshot.getChildren()) {
                        AdminPeople adminPeople2 = snap.getValue(AdminPeople.class);
                        String adminPeopleEmail = adminPeople2.getAdminPeopleEmail();
                        sendEmail(adminPeopleEmail);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    /**
     * Receives idDiscipline to send email only for students subscribed on discipline
     *
     * @param idDiscipline
     */
    public void allStudentsEmail(String idDiscipline) {

        if (idDiscipline != null) {

            DatabaseReference students = databaseDisciplineReference
                    .child(idDiscipline)
                    .child("students");

            students.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot snap : dataSnapshot.getChildren()) {
                        Student student = snap.getValue(Student.class);

                        String studentEmail = student.getStudentEmail();
                        sendEmail(studentEmail);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

    }

    /**
     * Email sender. Receives Student email or Administrative People email
     *
     * @param sendTo
     */
    private void sendEmail(String sendTo) {
        //Getting content for email and Executing sendmail to send email
        try {
            String email = sendTo;
            String subject = "Info :: Alteração na aula de " + classDate + " às " + classHour;
            String message = "A aula foi alterada de " + classDate + " às " + classHour + " para " + newClassDate + " às " + newClassTime + ".\n" +
                    "Cumprimentos.";
            //Creating SendMail object
            SendMail sm = new SendMail(getApplicationContext(), email, subject, message);
            sm.execute();
        } catch (Exception e) {
            Log.e("SendMail", e.getMessage(), e);
        }
    }

    private void toastMessageShort(String msg) {
        Toast.makeText(this, msg,
                Toast.LENGTH_SHORT).show();
    }

    /**
     * to initialize components
     */

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
        textViewConteudo = findViewById(R.id.textViewConteudo);
        buttonClassAdd = findViewById(R.id.buttonClassAdd);
        databaseDisciplineReference = FirebaseDatabase.getInstance().getReference("disciplines").child(userIdLogged);
    }
}


