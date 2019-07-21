package com.projeto.academicplanner.fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.projeto.academicplanner.Interface.IFirebaseLoadDoneDiscipline;
import com.projeto.academicplanner.R;
import com.projeto.academicplanner.adapter.Adapter_Students_Disciplines;
import com.projeto.academicplanner.helper.ConfigFirebase;
import com.projeto.academicplanner.model.Discipline;
import com.projeto.academicplanner.model.Student;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class StudentAddRemoveDisciplineFragment extends Fragment implements IFirebaseLoadDoneDiscipline {

    private SearchableSpinner spinnerDisciplines;

    private TextView backToPrevious;
    private EditText studentFirstName, studentLastName, studentEmail, studentUniversity, studentCourse;
    private ToggleButton isDelegateButton;
    private Button buttonAddIntoDiscipline;
    private String idUserLogged;
    private List<Student> students = new ArrayList<>();
    private String idUniversitySelected, nameUniversitySelected, idCourseSelected, nameCourseSelected,
            idDisciplineSelected, nameDisciplineSelected, idYearSelected, nameYearSelected, semester;

    private StudentMainFragment studentMainFragmentF;
    private Student studentToAddInDiscipline;
    private Discipline disciplineToAddInStudent;

    private DatabaseReference firebaseRefDiscipline;
    private IFirebaseLoadDoneDiscipline iFirebaseLoadDoneDiscipline;


    //recycler view variables
    private RecyclerView recyclerStudentDiscipline;
    private RecyclerView.LayoutManager layout;
    private Adapter_Students_Disciplines adapter;

    public StudentAddRemoveDisciplineFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        studentToAddInDiscipline = (Student) getArguments().getSerializable("StudentToUpdate");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View addRemoveStudentDisciplines = inflater.inflate(R.layout.fragment_student_add_remove_discipline, container, false);


        //recovery loged user ID
        idUserLogged = ConfigFirebase.getUserId();

        initializingComponents(addRemoveStudentDisciplines);
        adapterConstructor();

        studentFirstName.setText(studentToAddInDiscipline.getStudentFirstName());
        studentLastName.setText(studentToAddInDiscipline.getStudentLastName());
        studentEmail.setText(studentToAddInDiscipline.getStudentEmail());
        studentUniversity.setText(studentToAddInDiscipline.getUniversityName());
        studentCourse.setText(studentToAddInDiscipline.getCourseName());
        isDelegateButton.setText(studentToAddInDiscipline.getStudentDelegate());

        //spinnerDisciplines.setTitle(disciplineToUpdate.getCourseName());

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

        buttonAddIntoDiscipline.setOnClickListener( v-> {

            Student studentOnDiscipline = new Student();
            studentOnDiscipline.saveOnDiscipline(idDisciplineSelected, studentToAddInDiscipline);

            Discipline disciplineOnStudent = new Discipline();
            disciplineOnStudent.saveOnStudent(studentToAddInDiscipline  , disciplineToAddInStudent);

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

                            Discipline discipline = disciplinesSnapShot.getValue(Discipline.class);
                            String courseToCompare = discipline.getIdCourse();

                            try {

                                if (courseToCompare.equals(studentToAddInDiscipline.getIdCourse())) {
                                    disciplines.add(discipline);
                                } else {
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            iFirebaseLoadDoneDiscipline.onFireBaseLoadDisciplineSuccess(disciplines);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                        iFirebaseLoadDoneDiscipline.onFireBaseLoadDisciplineFailed(databaseError.getMessage());
                    }
                });

        return addRemoveStudentDisciplines;

    }

    //charge the spinner Discipline values
    @Override
    public void onFireBaseLoadDisciplineSuccess(final List<Discipline> disciplinesList) {


        /**
         * disciplineSpinner = disciplineList
         */
        final List<String> discipline_info = new ArrayList<>();
        for (Discipline discipline : disciplinesList)

            discipline_info.add(discipline.getAcronymDiscipline()
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

                idDisciplineSelected = disciplinesList.get(position).getIdDiscipline();
                disciplineToAddInStudent = disciplinesList.get(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    @Override
    public void onFireBaseLoadDisciplineFailed(String message) {
    }

    private void adapterConstructor() {

        //recycler view configuration
        layout = new LinearLayoutManager(getContext());
        adapter = new Adapter_Students_Disciplines(students, getContext());
        recyclerStudentDiscipline.setAdapter(adapter);
        recyclerStudentDiscipline.setLayoutManager(layout);
        recyclerStudentDiscipline.setHasFixedSize(true);

        adapter.setOnItemClickListener( (adapter_disciplines, v, position) ->  {

            final ImageView imageDelete = v.findViewById(R.id.imageDelete);

            final Student objectToAction = students.get(position);

            imageDelete.setOnClickListener( view -> {
                disciplineDelete(objectToAction);
            });

        });

    }

    private void disciplineDelete(final Student selectedToRemove) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        String name = selectedToRemove.getDisciplineName();
        String msg = "Are you sure, you want to delete the discipline " + name + "?";

        builder.setTitle(msg);
        builder.setPositiveButton(android.R.string.yes, (dialog, id) -> {

            selectedToRemove.deleteDiscipline(selectedToRemove);
            toastMsg("Discipline " + name + " has been removed!");
            adapter.notifyDataSetChanged();
            dialog.dismiss();

            //call methods
            adapterConstructor();

            //create object and fill recyclerViewCourses
            Student student = new Student();
            student.recoveryDisciplines(studentToAddInDiscipline, students, adapter);

        });

        builder.setNegativeButton(android.R.string.no, (dialog, id) -> {
            //method to cancel the delete operation
            toastMsg("Request CANCELED");
            dialog.dismiss();
        });

        AlertDialog dialog = builder.create();
        dialog.show();
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
        studentUniversity = view.findViewById(R.id.studentUniversity);
        studentCourse = view.findViewById(R.id.studentCourse);
        isDelegateButton = view.findViewById(R.id.isDelegateButton);
        buttonAddIntoDiscipline = view.findViewById(R.id.buttonAddIntoDiscipline);
        recyclerStudentDiscipline = view.findViewById(R.id.recyclerStudentDiscipline);

        //create object and fill recyclerViewCourses
        Student student = new Student();
        student.recoveryDisciplines(studentToAddInDiscipline, students, adapter);

    }

}
