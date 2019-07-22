package com.projeto.academicplanner.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.projeto.academicplanner.Interface.IFirebaseLoadDoneDiscipline;
import com.projeto.academicplanner.R;
import com.projeto.academicplanner.helper.ConfigFirebase;
import com.projeto.academicplanner.helper.SendMail;
import com.projeto.academicplanner.model.Discipline;
import com.projeto.academicplanner.model.Student;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.List;

import static java.security.AccessController.getContext;

public class SendEmailActivity extends AppCompatActivity implements IFirebaseLoadDoneDiscipline {

    //Declaring EditText

    private EditText editTextSubject;
    private EditText editTextMessage;
    private SearchableSpinner spinnerDisciplines;
    private Switch switchStdOrDlgt;

    private String idUserLogged, idDiscipline;

    private String idUniversitySelected, nameUniversitySelected, idCourseSelected, nameCourseSelected,
            idDisciplineSelected, nameDisciplineSelected, idYearSelected, nameYearSelected, semester;

    private DatabaseReference databaseDisciplineReference;
    private IFirebaseLoadDoneDiscipline iFirebaseLoadDoneDiscipline;

    private List<String> allStudentsEmailList = new ArrayList<>();
    private final List<String> allDelegatesEmailList = new ArrayList<>();

    //Send button
    private Button buttonSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_email);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Send Mail");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initializingComponents();

        /**
         * load fields to the Discipline spinner
         */
        databaseDisciplineReference
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        List<Discipline> disciplines = new ArrayList<>();

                        for (DataSnapshot disciplinesSnapShot : dataSnapshot.getChildren()) {

                            disciplines.add(disciplinesSnapShot.getValue(Discipline.class));
                            iFirebaseLoadDoneDiscipline.onFireBaseLoadDisciplineSuccess(disciplines);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                        iFirebaseLoadDoneDiscipline.onFireBaseLoadDisciplineFailed(databaseError.getMessage());
                    }
                });


        buttonSend.setOnClickListener(view -> {
            if (switchStdOrDlgt.isChecked()) {
                delegatesEmail(idDisciplineSelected);
            } else {
                allStudentsEmail(idDisciplineSelected);
            }
        });
    }

    public void allStudentsEmail(String idDisciplineSelected) {

        DatabaseReference students = databaseDisciplineReference
                .child(idDisciplineSelected)
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

    public void delegatesEmail(String idDisciplineSelected) {

        DatabaseReference students = databaseDisciplineReference
                .child(idDisciplineSelected)
                .child("students");

        students.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snap: dataSnapshot.getChildren()){
                    Student student = snap.getValue(Student.class);

                    String studentEmail = student.getStudentEmail();

                    if (student.getStudentDelegate().equals("YES")) {
                        sendEmail(studentEmail);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


        //charge the spinner Discipline values
        @Override
        public void onFireBaseLoadDisciplineSuccess ( final List<Discipline> disciplinesList){

            final List<String> discipline_info = new ArrayList<>();
            for (Discipline discipline : disciplinesList)

                discipline_info.add(discipline.getDisciplineName() + "\n"
                        + discipline.getDisciplineYearName() + "   Semester: " + discipline.getDisciplineSemester() + "\n"
                        + discipline.getCourseName() + "\n"
                        + discipline.getUniversityName());

            /**
             * Creates adapter
             */
            ArrayAdapter<String> adapterDiscipline = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, discipline_info);
            spinnerDisciplines.setAdapter(adapterDiscipline);

            spinnerDisciplines.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    idUniversitySelected = disciplinesList.get(position).getIdUniversity();
                    nameUniversitySelected = disciplinesList.get(position).getUniversityName();
                    idCourseSelected = disciplinesList.get(position).getIdCourse();
                    nameCourseSelected = disciplinesList.get(position).getCourseName();
                    idDisciplineSelected = disciplinesList.get(position).getIdDiscipline();
                    nameDisciplineSelected = disciplinesList.get(position).getDisciplineName();
                    idYearSelected = disciplinesList.get(position).getDisciplineYearId();
                    nameYearSelected = disciplinesList.get(position).getDisciplineYearName();
                    semester = disciplinesList.get(position).getDisciplineSemester();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

        }

        @Override
        public void onFireBaseLoadDisciplineFailed (String message){
        }


        private void sendEmail (String sendTo) {
            //Getting content for email

            String email = sendTo;
            String subject = editTextSubject.getText().toString().trim();
            String message = editTextMessage.getText().toString().trim();

            //Creating SendMail object
            SendMail sm = new SendMail(this, email, subject, message);

            //Executing sendmail to send email
            sm.execute();
        }

        private void initializingComponents () {

            editTextSubject = findViewById(R.id.editTextSubject);
            editTextMessage = findViewById(R.id.editTextMessage);
            switchStdOrDlgt = findViewById(R.id.switchStdOrDlgt);
            buttonSend = findViewById(R.id.buttonSend);
            spinnerDisciplines = findViewById(R.id.spinnerDisciplines);

            idUserLogged = ConfigFirebase.getUserId();
            databaseDisciplineReference = FirebaseDatabase.getInstance().getReference("disciplines").child(idUserLogged);
            iFirebaseLoadDoneDiscipline = this;

        }

    }
