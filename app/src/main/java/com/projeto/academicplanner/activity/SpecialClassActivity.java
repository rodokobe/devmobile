package com.projeto.academicplanner.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
import com.projeto.academicplanner.fragment.ClassAddFragment;
import com.projeto.academicplanner.fragment.SpecialClassAddFragment;
import com.projeto.academicplanner.helper.ConfigFirebase;
import com.projeto.academicplanner.model.Classes;
import com.projeto.academicplanner.model.Discipline;
import com.projeto.academicplanner.model.UserProfile;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class SpecialClassActivity extends AppCompatActivity implements IFirebaseLoadDoneDiscipline {

    private SearchableSpinner spinnerDiscipline, spinnerSpecial;
    private EditText editTextClassroom, editTextContent, editTextDate, editTextHour;
    private SearchableSpinner spinnerDuration;
    private Button buttonSpecialClassAdd;

    private String idUserLogged, isSpecial, idUniversitySelected, nameUniversitySelected, idCourseSelected, nameCourseSelected,
            idDisciplineSelected, nameDisciplineSelected, idYearSelected, nameYearSelected, semester;

    private String[] numbers = new String[]{"1 hour", "2 hours", "3 hours", "4 hours", "5 hours", "6 hours", "7 hours", "8 hours"};
    private String[] special = new String[]{"---", "Exame", "Exame Recurso", "Acompanhamento Trabalho", "Defesa Trabalho"};

    private DatabaseReference databaseDisciplineReference;
    private IFirebaseLoadDoneDiscipline iFirebaseLoadDoneDiscipline;
    private DatePickerDialog.OnDateSetListener setListener;

    private FirebaseAuth auth;
    private DatabaseReference firebaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special_class);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add New Special Class");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        auth = ConfigFirebase.getReferenciaAutenticacao();
        firebaseRef = ConfigFirebase.getReferenciaFirebase();
        idUserLogged = ConfigFirebase.getUserId();

        initializingComponents();

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);


        /**
         * Date Picker
         */
        editTextDate.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog( this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, setListener, year, month, day);
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

            TimePickerDialog timePickerDialog = new TimePickerDialog( this, new TimePickerDialog.OnTimeSetListener() {
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
        buttonSpecialClassAdd.setOnClickListener(v -> {

            String subjectEditTextToSave = spinnerSpecial.getSelectedItem().toString();
            String isSpecial = "Sim";
            String editTextDateToSave = editTextDate.getText().toString();
            String editTextHourToSave = editTextHour.getText().toString();
            String editTextTimeDurationToSave = spinnerDuration.getSelectedItem().toString();
            toastMsgShort(editTextTimeDurationToSave);
            String editTextClassroomToSave = editTextClassroom.getText().toString();

            classAddNew(subjectEditTextToSave, isSpecial, editTextDateToSave, editTextHourToSave,
                    editTextTimeDurationToSave, editTextClassroomToSave, idUniversitySelected,
                    nameUniversitySelected, idCourseSelected, nameCourseSelected, idDisciplineSelected,
                    nameDisciplineSelected, idYearSelected, nameYearSelected, semester);
        });

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

        preferencesRecovery();

    }


    //charge the spinner Discipline values
    @Override
    public void onFireBaseLoadDisciplineSuccess(final List<Discipline> disciplinesList) {

        final List<String> discipline_info = new ArrayList<>();
        for (Discipline discipline : disciplinesList)

            discipline_info.add(discipline.getDisciplineName() + "\n"
                    + discipline.getDisciplineYearName() + "   Semester: " + discipline.getDisciplineSemester() + "\n"
                    + discipline.getCourseName() + "\n"
                    + discipline.getUniversityName());

        /**
         * Creates adapter
         */
        ArrayAdapter<String> adapterDiscipline = new ArrayAdapter<>( this, android.R.layout.simple_list_item_1, discipline_info);
        spinnerDiscipline.setAdapter(adapterDiscipline);

        spinnerDiscipline.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
    public void onFireBaseLoadDisciplineFailed(String message) {
    }

    public void preferencesRecovery(){
        DatabaseReference userProfileRef = firebaseRef
                .child("users")
                .child(idUserLogged)
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
                    editTextHour.setText("-");
                    spinnerDuration.setTitle("-");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void classAddNew(String subjectEditTextToSave, String isSpecial, String editTextDateToSave,
                             String editTextHourToSave, String spinnerDurationToSave, String editTextClassroomToSave,
                             String idUniversitySelected, String nameUniversitySelected, String idCourseSelected,
                             String nameCourseSelected, String idDisciplineSelected, String nameDisciplineSelected,
                             String idYearSelected, String nameYearSelected, String semester) {

        if (!subjectEditTextToSave.isEmpty()) {
            if (!editTextDateToSave.isEmpty()) {
                if (!editTextHourToSave.isEmpty()) {
                    if (!spinnerDurationToSave.isEmpty()) {
                        if (!editTextClassroomToSave.isEmpty()) {

                            Classes classToSave = new Classes();
                            classToSave.setIdUser(idUserLogged);
                            classToSave.setIsSpecial(isSpecial);
                            classToSave.setSubject(subjectEditTextToSave);
                            classToSave.setClassDate(editTextDateToSave);
                            classToSave.setClassTime(editTextHourToSave);
                            classToSave.setTimeDuration(spinnerDurationToSave);
                            classToSave.setClassroom(editTextClassroomToSave);
                            classToSave.setIdUniversity(idUniversitySelected);
                            classToSave.setNameUniversity(nameUniversitySelected);
                            classToSave.setIdCourse(idCourseSelected);
                            classToSave.setNameCourse(nameCourseSelected);
                            classToSave.setIdDiscipline(idDisciplineSelected);
                            classToSave.setNameDiscipline(nameDisciplineSelected);
                            classToSave.setIdYear(idYearSelected);
                            classToSave.setNameYear(nameYearSelected);
                            classToSave.setSemester(semester);

                            classToSave.save();

                            toastMsgShort("Class " + classToSave.getTopicsAndContents() + " added");
                            backToMain();

                        } else {
                            toastMsgShort("Enter a Class room");
                        }
                    } else {
                        toastMsgShort("Enter a Class duration");
                    }
                } else {
                    toastMsgShort("Enter a Class hour");
                }
            } else {
                toastMsgShort("Enter a Class date");
            }
        } else {
            toastMsgShort("Enter a Class subject");
        }

    }

    public void toastMsgShort(String errorMsg) {
        Toast toastErrorMsg = Toast.makeText( this, errorMsg, Toast.LENGTH_SHORT);
        toastErrorMsg.setGravity(Gravity.CENTER, 0, 600);
        toastErrorMsg.show();
    }

    public void backToMain() {
        startActivity(new Intent(this, NavMainActivity.class));
    }

    private void initializingComponents(){
        spinnerDiscipline = findViewById(R.id.spinnerDiscipline);
        spinnerSpecial = findViewById(R.id.spinnerSpecial);

        editTextDate = findViewById(R.id.editTextDate);
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat dateOnly = new SimpleDateFormat("dd/MM/yyyy");
        editTextDate.setText(dateOnly.format(cal.getTime()));

        editTextHour = findViewById(R.id.editTextHour);

        editTextClassroom = findViewById(R.id.editTextClassroom);
        editTextContent = findViewById(R.id.editTextContent);
        buttonSpecialClassAdd = findViewById(R.id.buttonSpecialClassAdd);

        spinnerDuration = findViewById(R.id.spinnerDuration);

        /**
         * Setting 8 hours to initializes Spinner Duration Time
         */
        String[] numbers = new String[]{"1 hour", "2 hours", "3 hours", "4 hours", "5 hours", "6 hours", "7 hours", "8 hours"};
        List<String> numbersList = new ArrayList<String>(Arrays.asList(numbers));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>( this, android.R.layout.simple_spinner_item, numbersList);
        //specify the layout to appear list items
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //data bind adapter with both spinners
        spinnerDuration.setAdapter(adapter);
        spinnerDuration.setSelection(3);

        /**
         * Setting Special Classes
         */
        String[] special = new String[]{"---", "Exame", "Exame Recurso", "Acompanhamento Trabalho", "Defesa Trabalho"};
        List<String> specialList = new ArrayList<String>(Arrays.asList(special));
        ArrayAdapter<String> adapterSpecial= new ArrayAdapter<String>( this, android.R.layout.simple_spinner_item, specialList);
        //specify the layout to appear list items
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //data bind adapter with both spinners
        spinnerSpecial.setAdapter(adapterSpecial);
        spinnerSpecial.setSelection(0);


        /**
         * instances to load data and send to spinners
         */
        databaseDisciplineReference = FirebaseDatabase.getInstance().getReference("disciplines").child(idUserLogged);

        iFirebaseLoadDoneDiscipline = this;
    }

}
