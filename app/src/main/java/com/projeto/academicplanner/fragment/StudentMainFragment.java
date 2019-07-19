package com.projeto.academicplanner.fragment;


import android.app.AlertDialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.projeto.academicplanner.R;
import com.projeto.academicplanner.adapter.Adapter_Students;
import com.projeto.academicplanner.helper.ConfigFirebase;
import com.projeto.academicplanner.model.Discipline;
import com.projeto.academicplanner.model.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentMainFragment extends Fragment {


    //general variables
    private Button buttonStudents;
    private TextView backToPrevious;
    private String idUserLogged;
    private List<Student> students = new ArrayList<>();
    private StudentAddFragment addStudentFragmentF;
    private StudentUpdateFragment updateStudentFragmentF;
    private SettingsFragment settingsFragment;

    //recycler view variables
    private RecyclerView recyclerStudents;
    private RecyclerView.LayoutManager layout;
    private Adapter_Students adapter;


    public StudentMainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View mainStudents = inflater.inflate(R.layout.fragment_student_main, container, false);

        //start configurations
        initializingComponents(mainStudents);

        //recovery loged user ID
        idUserLogged = ConfigFirebase.getUserId();

        //call methods
        adapterConstructor();

        //create object and fill recyclerViewCourses
        Student student = new Student();
        student.recovery(idUserLogged, students, adapter);

        buttonStudents.setOnClickListener( v -> {
            goToNewFragment();
        });

        backToPrevious.setOnClickListener( v -> {
           goBackToMain();
        });

        return mainStudents;

    }

    private void adapterConstructor() {

        //recycler view configuration
        layout = new LinearLayoutManager(getContext());
        adapter = new Adapter_Students(students, getContext());
        recyclerStudents.setAdapter(adapter);
        recyclerStudents.setLayoutManager(layout);
        recyclerStudents.setHasFixedSize(true);

        adapter.setOnItemClickListener( (adapter_students, v, position) -> {

                final ImageView imageEdit = v.findViewById(R.id.imageEdit);
                final ImageView imageDelete = v.findViewById(R.id.imageDelete);

                final Student objectToAction = students.get(position);

                imageDelete.setOnClickListener( view -> {
                        studentDelete(objectToAction);
                });

                imageEdit.setOnClickListener( view -> {
                        goToUpdateFragment(objectToAction);
                });
        });

    }

    private void studentDelete(final Student selectedToRemove) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        String name = selectedToRemove.getStudentFirstName() + " " + selectedToRemove.getStudentLastName();
        String msg = "Are you sure, you want to delete the student " + name + "?";

        builder.setTitle(msg);
        builder.setPositiveButton(android.R.string.yes, (dialog, id) -> {

            selectedToRemove.delete();
            toastMsgLong("Student " + name + " has been removed!");
            adapter.notifyDataSetChanged();
            dialog.dismiss();

            //call methods
            adapterConstructor();

            //create object and fill recyclerViewCourses
            Student student = new Student();
            student.recovery(idUserLogged, students, adapter);

        });

        builder.setNegativeButton(android.R.string.no, (dialog, id) -> {
            //method to cancel the delete operation
            toastMsgLong("Request CANCELED");
            dialog.dismiss();
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void goToUpdateFragment(Student objectToAction) {
        updateStudentFragmentF = new StudentUpdateFragment();
        Bundle dataToUpdate = new Bundle();
        dataToUpdate.putSerializable("StudentToUpdate", objectToAction);

        updateStudentFragmentF.setArguments(dataToUpdate);

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameSettingsMain, updateStudentFragmentF);
        transaction.commit();
    }

    public void goToNewFragment() {
        addStudentFragmentF = new StudentAddFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameSettingsMain, addStudentFragmentF);
        transaction.commit();
    }

    public void goBackToMain() {

        settingsFragment = new SettingsFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameSettingsMain, settingsFragment);
        transaction.commit();

    }

    public void toastMsgLong(String text) {

        //show toast parameters
        Toast toastError = Toast.makeText(getContext(), text, Toast.LENGTH_LONG);
        toastError.setGravity(Gravity.CENTER, 0, 800);
        toastError.show();

    }

    private void initializingComponents(View view){
        buttonStudents = view.findViewById(R.id.buttonStudents);
        backToPrevious = view.findViewById(R.id.backToPrevious);
        recyclerStudents = view.findViewById(R.id.recyclerStudents);
    }
}
