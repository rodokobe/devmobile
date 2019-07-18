package com.projeto.academicplanner.fragment;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.projeto.academicplanner.R;
import com.projeto.academicplanner.helper.ConfigFirebase;
import com.projeto.academicplanner.model.Student;

public class StudentAddFragment extends Fragment {

    private TextView backToPrevious;
    private EditText studentFirstName, studentLastName, studentEmail;
    private ToggleButton isDelegateButton;
    private Button buttonStudents;
    private String idUserLogged;

    private StudentMainFragment studentMainFragmentF;
    private SettingsFragment settingsFragment;

    public StudentAddFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View addStudent = inflater.inflate(R.layout.fragment_student_add, container, false);

        initializingComponents(addStudent);

        //recovery logged user ID
        idUserLogged = ConfigFirebase.getUserId();

        backToPrevious.setOnClickListener( v -> {
                backToMain();
        });

        buttonStudents.setOnClickListener( v -> {

                String studentSaveFirstName = studentFirstName.getText().toString();
                String studentSaveLastName = studentLastName.getText().toString();
                String studentSaveEmail = studentEmail.getText().toString();
                String studentSabeDelegate = "NO";

                if (isDelegateButton.isChecked()) {
                    studentSabeDelegate = "YES";
                }

                studentAddNew(studentSaveFirstName, studentSaveLastName, studentSaveEmail, studentSabeDelegate);
        });

        return addStudent;

    }

    private void studentAddNew(String studentSaveFirstName, String studentSaveLastName, String studentSaveEmail, String studentSabeDelegate) {

        if (!studentSaveFirstName.isEmpty()) {
            if (!studentSaveLastName.isEmpty()) {
                if (!studentSaveEmail.isEmpty()) {

                Student student = new Student();
                student.setIdUser(idUserLogged);
                student.setStudentFirstName(studentSaveFirstName);
                student.setStudentLastName(studentSaveLastName);
                student.setStudentEmail(studentSaveEmail);
                student.setStudentDelegate(studentSabeDelegate);

                student.save();
                toastMsg("Student " + student.getStudentFirstName() + " successfully added ");
                backToMain();

                } else {
                    toastMsg("Enter an e-mail to Student");
                }
            } else {
                toastMsg("Enter a last name to Student");
            }
        } else {
            toastMsg("Enter a first name");
        }

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

    private void initializingComponents(View view){
        backToPrevious = view.findViewById(R.id.backToPrevious);
        studentFirstName = view.findViewById(R.id.studentFirstName);
        studentLastName = view.findViewById(R.id.studentLastName);
        studentEmail = view.findViewById(R.id.studentEmail);
        isDelegateButton = view.findViewById(R.id.isDelegateButton);
        buttonStudents = view.findViewById(R.id.buttonStudents);
    }

}
