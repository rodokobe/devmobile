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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
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

public class StudentAddRemoveDisciplineFragment extends Fragment implements IFirebaseLoadDoneDiscipline {

    private SearchableSpinner spinnerDisciplines;
    private TextView backToPrevious, studentName,  studentDelegate, studentEmail, studentUniversity, studentCourse;
    private Button buttonAddIntoDiscipline;
    private String idUserLogged;
    private List<Discipline> disciplinesRecycler = new ArrayList<>();

    private StudentMainFragment studentMainFragmentF;
    private Student studentToAddInDiscipline;
    private Discipline disciplineSelected;

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

        String fullName = studentToAddInDiscipline.getStudentFirstName() + " " + studentToAddInDiscipline.getStudentLastName();

        studentName.setText(fullName);
        studentEmail.setText(studentToAddInDiscipline.getStudentEmail());
        studentUniversity.setText(studentToAddInDiscipline.getUniversityName());
        studentCourse.setText(studentToAddInDiscipline.getCourseName());
        studentDelegate.setText(studentToAddInDiscipline.getStudentDelegate());


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
            studentOnDiscipline.saveStudentInDiscipline(disciplineSelected, studentToAddInDiscipline);
            studentOnDiscipline.saveDisciplineInStudent(disciplineSelected, studentToAddInDiscipline);

        });

        /**
         * load fields to the Discipline spinner
         */
        firebaseRefDiscipline
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        List<Discipline> disciplines1 = new ArrayList<>();

                        for (DataSnapshot disciplinesSnapShot : dataSnapshot.getChildren()) {

                            Discipline discipline = disciplinesSnapShot.getValue(Discipline.class);
                            String courseToCompare = discipline.getIdCourse();

                            try {

                                if (courseToCompare.equals(studentToAddInDiscipline.getIdCourse())) {
                                    disciplines1.add(discipline);
                                } else {
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            iFirebaseLoadDoneDiscipline.onFireBaseLoadDisciplineSuccess(disciplines1);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                        iFirebaseLoadDoneDiscipline.onFireBaseLoadDisciplineFailed(databaseError.getMessage());
                    }
                });


            DatabaseReference studentRef = FirebaseDatabase.getInstance().getReference("students").child(idUserLogged);

            studentRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    disciplinesRecycler.clear();

                    for (DataSnapshot snap : dataSnapshot.getChildren()) {
                        Student student = snap.getValue(Student.class);
                        String studentIdToShow = student.getIdStudent();

                        DatabaseReference disciplineRef = studentRef.child(studentIdToShow).child("disciplines");

                        disciplineRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                for (DataSnapshot disciplineSnapshot : dataSnapshot.getChildren()) {

                                    Discipline discipline = disciplineSnapshot.getValue(Discipline.class);

                                    try {

                                        if (studentIdToShow.equals(studentToAddInDiscipline.getIdStudent())) {
                                            disciplinesRecycler.add(discipline);

                                        } else {

                                        }

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                }

                                //recycler view configuration
                                layout = new LinearLayoutManager(getContext());
                                adapter = new Adapter_Students_Disciplines(disciplinesRecycler, getContext());
                                recyclerStudentDiscipline.setAdapter(adapter);
                                recyclerStudentDiscipline.setLayoutManager(layout);
                                recyclerStudentDiscipline.setHasFixedSize(true);

                                adapter.setOnItemClickListener( (adapter_students_disciplines, v, position) ->  {

                                    final ImageView imageDelete = v.findViewById(R.id.imageDelete);

                                    final Discipline objectToAction = disciplinesRecycler.get(position);

                                    imageDelete.setOnClickListener( view -> {
                                        disciplineDelete(objectToAction);
                                    });

                                });

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

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

                disciplineSelected = disciplinesList.get(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    @Override
    public void onFireBaseLoadDisciplineFailed(String message) {
    }

    private void disciplineDelete(final Discipline selectedToRemove) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        String name = selectedToRemove.getDisciplineName();
        String msg = "Are you sure, you want to delete the discipline " + name + "?";

        builder.setTitle(msg);
        builder.setPositiveButton(android.R.string.yes, (dialog, id) -> {

            selectedToRemove.deleteDisciplineIntoStudent(selectedToRemove, studentToAddInDiscipline);
            studentToAddInDiscipline.deleteStudentIntoOneDiscipline(studentToAddInDiscipline, selectedToRemove);
            toastMsg("Discipline " + name + " has been removed!");
            adapter.notifyDataSetChanged();
            dialog.dismiss();
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
        studentName = view.findViewById(R.id.studentName);
        studentEmail = view.findViewById(R.id.studentEmail);
        studentUniversity = view.findViewById(R.id.studentUniversity);
        studentCourse = view.findViewById(R.id.studentCourse);
        studentDelegate = view.findViewById(R.id.studentDelegate);
        buttonAddIntoDiscipline = view.findViewById(R.id.buttonAddIntoDiscipline);
        recyclerStudentDiscipline = view.findViewById(R.id.recyclerStudentDiscipline);

    }

}
