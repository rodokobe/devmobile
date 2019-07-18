package com.projeto.academicplanner.fragment;


import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.projeto.academicplanner.Interface.IFirebaseLoadDoneDiscipline;
import com.projeto.academicplanner.R;
import com.projeto.academicplanner.helper.ConfigFirebase;
import com.projeto.academicplanner.helper.DateTimeCustom;
import com.projeto.academicplanner.model.Classes;
import com.projeto.academicplanner.model.Discipline;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClassAddFragment extends Fragment implements IFirebaseLoadDoneDiscipline {

    private SearchableSpinner spinnerDiscipline;
    private EditText subjectEditText, editTextClassroom, editTextContent, editTextDate, editTextHour, editTextTimeDuration;
    private Button buttonClassAdd;

    private String idUserLogged, idUniversitySelected, nameUniversitySelected, idCourseSelected, nameCourseSelected,
            idDisciplineSelected, nameDisciplineSelected, idYearSelected, nameYearSelected, semester;

    private DatabaseReference firebaseRefDiscipline;
    private IFirebaseLoadDoneDiscipline iFirebaseLoadDoneDiscipline;

    private ClassAddFragment addClassFragment;


    public ClassAddFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View addClass = inflater.inflate(R.layout.fragment_class_add, container, false);

        initializingComponents(addClass);

        buttonClassAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String subjectEditTextToSave = subjectEditText.getText().toString();
                String editTextDateToSave = editTextDate.getText().toString();
                String editTextHourToSave = editTextHour.getText().toString();
                String editTextTimeDurationToSave = editTextTimeDuration.getText().toString();
                toastMsgShort(editTextTimeDurationToSave);
                String editTextClassroomToSave = editTextClassroom.getText().toString();
                String editTextContentToSave = editTextContent.getText().toString();

                classAddNew(subjectEditTextToSave, editTextDateToSave, editTextHourToSave, editTextTimeDurationToSave, editTextClassroomToSave,
                        editTextContentToSave, idUniversitySelected, nameUniversitySelected, idCourseSelected, nameCourseSelected, idDisciplineSelected,
                        nameDisciplineSelected, idYearSelected, nameYearSelected, semester);
            }
        });

        //load fields to the Discipline spinner
        firebaseRefDiscipline.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                List<Discipline> disciplines = new ArrayList<>();

                for (DataSnapshot coursesSnapShot : dataSnapshot.getChildren()) {

                    disciplines.add(coursesSnapShot.getValue(Discipline.class));
                    iFirebaseLoadDoneDiscipline.onFireBaseLoadDisciplineSuccess(disciplines);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                iFirebaseLoadDoneDiscipline.onFireBaseLoadDisciplineFailed(databaseError.getMessage());
            }
        });

        return addClass;
    }

    //charge the spinner Course values
    @Override
    public void onFireBaseLoadDisciplineSuccess(final List<Discipline> disciplinesList) {

        //universitySpinner = universitiesList;
        final List<String> discipline_info = new ArrayList<>();
        for (Discipline discipline : disciplinesList)

            discipline_info.add("University: " + discipline.getUniversityName() + "\nCourse: " + discipline.getCourseName()
                    + "\n Discipline: " + discipline.getDisciplineName() + "\n Year: " + discipline.getDisciplineYearName() +
                    "\n Semester: " + discipline.getDisciplineSemester());

        //Create adapter
        ArrayAdapter<String> adapterUniversity = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, discipline_info);
        spinnerDiscipline.setAdapter(adapterUniversity);

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

    private void classAddNew(String subjectEditTextToSave, String editTextDateToSave, String editTextHourToSave, String editTextTimeDurationToSave, String editTextClassroomToSave,
                             String editTextContentToSave, String idUniversitySelected, String nameUniversitySelected, String idCourseSelected, String nameCourseSelected,
                             String idDisciplineSelected, String nameDisciplineSelected, String idYearSelected, String nameYearSelected, String semester) {

        if (!subjectEditTextToSave.isEmpty()) {
            if (!editTextDateToSave.isEmpty()) {
                if (!editTextHourToSave.isEmpty()) {
                    if (!editTextTimeDurationToSave.isEmpty()) {
                        if (!editTextClassroomToSave.isEmpty()) {
                            if (!editTextContentToSave.isEmpty()) {

                                Classes classToSave = new Classes();
                                classToSave.setIdUser(idUserLogged);
                                classToSave.setSubject(subjectEditTextToSave);
                                classToSave.setClassDate(editTextDateToSave);
                                classToSave.setClassTime(editTextHourToSave);
                                classToSave.setTimeDuration(editTextTimeDurationToSave);
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

    public void toastMsgShort(String errorMsg){
        Toast toastErrorMsg = Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_SHORT);
        toastErrorMsg.setGravity(Gravity.CENTER, 0 , 600);
        toastErrorMsg.show();
    }

    public void backToMain() {
        addClassFragment = new ClassAddFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameClassMain, addClassFragment);
        transaction.commit();
    }

    private void initializingComponents(View view) {

        spinnerDiscipline = view.findViewById(R.id.spinnerDiscipline);
        subjectEditText = view.findViewById(R.id.subjectEditText);
        editTextDate = view.findViewById(R.id.editTextDate);
        editTextHour = view.findViewById(R.id.editTextHour);
        editTextTimeDuration = view.findViewById(R.id.editTextTimeDuration);
        editTextClassroom = view.findViewById(R.id.editTextClassroom);
        editTextContent = view.findViewById(R.id.editTextContent);
        buttonClassAdd = view.findViewById(R.id.buttonClassAdd);

        editTextDate.setText(DateTimeCustom.getNowDate());
        editTextHour.setText(DateTimeCustom.getNowTime());
        editTextTimeDuration.setText("00");

        idUserLogged = ConfigFirebase.getUserId();

        //instances to load data and send to spinners
        firebaseRefDiscipline = FirebaseDatabase.getInstance().getReference("disciplines").child(idUserLogged);

        iFirebaseLoadDoneDiscipline = this;

    }

}
