package com.projeto.academicplanner.fragment;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.projeto.academicplanner.Interface.IFirebaseLoadDoneDiscipline;
import com.projeto.academicplanner.R;
import com.projeto.academicplanner.activity.NavMainActivity;
import com.projeto.academicplanner.helper.ConfigFirebase;
import com.projeto.academicplanner.model.Classes;
import com.projeto.academicplanner.model.Course;
import com.projeto.academicplanner.model.Discipline;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClassUpdateFragment extends Fragment implements IFirebaseLoadDoneDiscipline {

    private SearchableSpinner spinnerDiscipline;
    private EditText subjectEditText, editTextClassroom, editTextContent, editTextDate, editTextHour;
    private SearchableSpinner spinnerDuration;
    private Button buttonClassAdd;
    private Classes classToUpdate;

    private String idUserLogged, idUniversitySelected, nameUniversitySelected, idCourseSelected, nameCourseSelected,
            idDisciplineSelected, nameDisciplineSelected, idYearSelected, nameYearSelected, semester;

    private DatabaseReference databaseClassesReference, databaseDisciplineReference;
    private IFirebaseLoadDoneDiscipline iFirebaseLoadDoneDiscipline;
    private DatePickerDialog.OnDateSetListener setListener;


    public ClassUpdateFragment() {
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
        View v = inflater.inflate(R.layout.fragment_class_add, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Edit Class");

        initializingComponents(v);

        View updateClass = inflater.inflate(R.layout.fragment_class_add, container, false);

        idUserLogged = ConfigFirebase.getUserId();

        buttonClassAdd.setText("update");

        Discipline discipline = new Discipline();
        Classes classes = new Classes();

        databaseClassesReference = FirebaseDatabase.getInstance()
                .getReference("disciplines").child(idUserLogged)
                .child(discipline.getIdDiscipline())
                .child("classes")
                .child(classes.getIdClass());



        initializingComponents(updateClass);

        spinnerDiscipline.setTitle(classToUpdate.getNameDiscipline());
        subjectEditText.setText(classToUpdate.getSubject());
        editTextDate.setText(classToUpdate.getClassDate());
        editTextHour.setText(classToUpdate.getClassTime());
        editTextClassroom.setText(classToUpdate.getClassroom());
        editTextContent.setText(classToUpdate.getTopicsAndContents());
        spinnerDuration.setTitle(classToUpdate.getTimeDuration());

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);


        /**
         * Date Picker
         */
        editTextDate.setOnClickListener( view -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), android.R.style.Theme_Holo_Light_Dialog_MinWidth, setListener, year, month, day);
            datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            datePickerDialog.show();
        });

        setListener = (view, thisYear, thisMonth, dayOfMonth) -> {
            thisMonth = thisMonth + 1;
            String date = dayOfMonth + "/" + thisMonth + "/" + thisYear;
            editTextDate.setText(date);
        };

        editTextDate.setOnClickListener( view -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    getActivity(), (myView, thisYear, thisMonth, dayOfMonth) -> {
                thisMonth = thisMonth + 1;
                editTextDate.setText(String.format("%02d/%02d/%04d", dayOfMonth, thisMonth, thisYear));
            }, year, month, day);
            datePickerDialog.show();
        });

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
        buttonClassAdd.setOnClickListener( view -> {

            String subjectEditTextToSave = subjectEditText.getText().toString();
            String editTextDateToSave = editTextDate.getText().toString();
            String editTextHourToSave = editTextHour.getText().toString();
            String editTextTimeDurationToSave = spinnerDuration.getSelectedItem().toString();
            toastMsgShort(editTextTimeDurationToSave);
            String editTextClassroomToSave = editTextClassroom.getText().toString();
            String editTextContentToSave = editTextContent.getText().toString();

            classAddNew(subjectEditTextToSave, editTextDateToSave, editTextHourToSave, editTextTimeDurationToSave, editTextClassroomToSave,
                    editTextContentToSave, idUniversitySelected, nameUniversitySelected, idCourseSelected, nameCourseSelected, idDisciplineSelected,
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

        return updateClass;
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
        ArrayAdapter<String> adapterDiscipline = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, discipline_info);
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

    private void classAddNew(String subjectEditTextToSave, String editTextDateToSave, String
            editTextHourToSave, String spinnerDurationToSave, String editTextClassroomToSave,
                             String editTextContentToSave, String idUniversitySelected, String
                                     nameUniversitySelected, String idCourseSelected, String nameCourseSelected,
                             String idDisciplineSelected, String nameDisciplineSelected, String
                                     idYearSelected, String nameYearSelected, String semester) {

        if (!subjectEditTextToSave.isEmpty()) {
            if (!editTextDateToSave.isEmpty()) {
                if (!editTextHourToSave.isEmpty()) {
                    if (!spinnerDurationToSave.isEmpty()) {
                        if (!editTextClassroomToSave.isEmpty()) {
                            if (!editTextContentToSave.isEmpty()) {

                                Classes classToSave = new Classes();
                                classToSave.setIdUser(idUserLogged);
                                classToSave.setSubject(subjectEditTextToSave);
                                classToSave.setClassDate(editTextDateToSave);
                                classToSave.setClassTime(editTextHourToSave);
                                classToSave.setTimeDuration(spinnerDurationToSave);
                                classToSave.setClassroom(editTextClassroomToSave);
                                classToSave.setTopicsAndContents(editTextContentToSave);
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
                                toastMsgShort("Enter a Class content");
                            }
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

    public void backToMain() {
        startActivity(new Intent(getActivity(), NavMainActivity.class));
    }

    public void toastMsgShort(String errorMsg) {
        Toast toastErrorMsg = Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_SHORT);
        toastErrorMsg.setGravity(Gravity.CENTER, 0, 600);
        toastErrorMsg.show();
    }
    private void initializingComponents(View view) {

        spinnerDiscipline = view.findViewById(R.id.spinnerDiscipline);
        subjectEditText = view.findViewById(R.id.subjectEditText);
        editTextDate = view.findViewById(R.id.editTextDate);
        editTextHour = view.findViewById(R.id.editTextHour);
        editTextClassroom = view.findViewById(R.id.editTextClassroom);
        editTextContent = view.findViewById(R.id.editTextContent);
        spinnerDuration = view.findViewById(R.id.spinnerDuration);
        buttonClassAdd = view.findViewById(R.id.buttonClassAdd);


    }

}
