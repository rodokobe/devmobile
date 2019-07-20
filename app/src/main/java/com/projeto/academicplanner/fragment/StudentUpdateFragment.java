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
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.projeto.academicplanner.Interface.IFirebaseLoadDoneDiscipline;
import com.projeto.academicplanner.R;
import com.projeto.academicplanner.helper.ConfigFirebase;
import com.projeto.academicplanner.model.Discipline;
import com.projeto.academicplanner.model.Student;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class StudentUpdateFragment extends Fragment implements IFirebaseLoadDoneDiscipline{

    private SearchableSpinner spinnerDisciplines;

    private TextView backToPrevious;
    private EditText studentFirstName, studentLastName, studentEmail;
    private ToggleButton isDelegateButton;
    private Button buttonStudents, buttonAddIntoDiscipline;
    private String idUserLogged;
    private RecyclerView recyclerViewSonD;

    private String idUniversitySelected, nameUniversitySelected, idCourseSelected, nameCourseSelected,
            idDisciplineSelected, nameDisciplineSelected, idYearSelected, nameYearSelected, semester;

    private StudentMainFragment studentMainFragmentF;
    private Discipline disciplineToUpdate;
    private Student studentToUpdate;

    private DatabaseReference firebaseRefDiscipline;
    private IFirebaseLoadDoneDiscipline iFirebaseLoadDoneDiscipline;


    public StudentUpdateFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        studentToUpdate = (Student) getArguments().getSerializable("StudentToUpdate");
        disciplineToUpdate = (Discipline) getArguments().getSerializable("DisciplineToUpdate");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View updateStudent = inflater.inflate(R.layout.fragment_student_update, container, false);


        //recovery loged user ID
        idUserLogged = ConfigFirebase.getUserId();

        initializingComponents(updateStudent);

        studentFirstName.setText(studentToUpdate.getStudentFirstName());
        studentLastName.setText(studentToUpdate.getStudentLastName());
        studentEmail.setText(studentToUpdate.getStudentEmail());
        isDelegateButton.setText(studentToUpdate.getStudentDelegate());
        spinnerDisciplines.setTitle(disciplineToUpdate.getCourseName());

        /**
         * instances to load data and send to spinners
         */
        firebaseRefDiscipline = FirebaseDatabase.getInstance().getReference("disciplines").child(idUserLogged);

        iFirebaseLoadDoneDiscipline = this;

        /**
         * Setting action on buttons
         */

        backToPrevious.setOnClickListener(v -> {
            backToMain();
        });

        buttonStudents.setOnClickListener(v -> {
            studentUpdate();
        });

        buttonAddIntoDiscipline.setOnClickListener( v-> {

            Student studentOnDiscipline = new Student();

            String disciplineToSave = spinnerDisciplines.getSelectedItem().toString();

            studentOnDiscipline.saveOnDiscipline(disciplineToSave);


        });

        /**
         * load fields to the Discipline spinner
         */
        firebaseRefDiscipline
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

        return updateStudent;

    }

    private void studentUpdate() {

        String studentSaveDelegate = "NO";

        if (isDelegateButton.isChecked()) {
            studentSaveDelegate = "YES";
        }

        Student studentUpdate = new Student();

        studentUpdate.setIdUser(idUserLogged);
        studentUpdate.setIdStudent(studentToUpdate.getIdStudent());
        studentUpdate.setStudentFirstName(studentFirstName.getText().toString());
        studentUpdate.setStudentLastName(studentLastName.getText().toString());
        studentUpdate.setStudentEmail(studentEmail.getText().toString());
        studentUpdate.setStudentDelegate(studentSaveDelegate);
        studentUpdate.update(studentUpdate);

        toastMsg("Student " + studentUpdate.getStudentFirstName() + " successfully update");
        backToMain();

    }

    //charge the spinner Discipline values
    @Override
    public void onFireBaseLoadDisciplineSuccess(final List<Discipline> disciplinesList) {


        /**
         * disciplineSpinner = disciplineList
         */
        final List<String> discipline_info = new ArrayList<>();
        for (Discipline discipline : disciplinesList)

            discipline_info.add(discipline.getUniversityAcronym()
                    + " - " + discipline.getAcronymDiscipline()
                    + " - " + discipline.getDisciplineName()
                    + " - " + discipline.getDisciplineYearName()
                    + " / " + discipline.getDisciplineSemester());

        /**
         * Creates adapter
         */
        ArrayAdapter<String> adapterDiscipline = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, discipline_info);
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
    public void onFireBaseLoadDisciplineFailed(String message) {
    }

    public void backToMain() {

        studentMainFragmentF = new StudentMainFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameSettingsMain, studentMainFragmentF);
        transaction.commit();
    }

    public void toastMsg(String text) {

        //show toast parameters
        Toast toastError = Toast.makeText(getContext(), text, Toast.LENGTH_LONG);
        toastError.setGravity(Gravity.CENTER, 0, 800);
        toastError.show();
    }

    private void initializingComponents(View view) {

        spinnerDisciplines = view.findViewById(R.id.spinnerDisciplines);
        backToPrevious = view.findViewById(R.id.backToPrevious);
        studentFirstName = view.findViewById(R.id.studentFirstName);
        studentLastName = view.findViewById(R.id.studentLastName);
        studentEmail = view.findViewById(R.id.studentEmail);
        isDelegateButton = view.findViewById(R.id.isDelegateButton);
        buttonStudents = view.findViewById(R.id.buttonStudents);
        buttonAddIntoDiscipline = view.findViewById(R.id.buttonAddIntoDiscipline);
        recyclerViewSonD = view.findViewById(R.id.recyclerViewSonD);

    }

}
