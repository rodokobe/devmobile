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

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.projeto.academicplanner.R;
import com.projeto.academicplanner.helper.ConfigFirebase;
import com.projeto.academicplanner.model.Student;

public class UpdateStudentFragment extends Fragment {

    private TextView backToAddEditMain, addEdit;
    private EditText studentFirstName, studentLastName, studentEmail;
    private ToggleButton isDelegateButton;
    private Button buttonStudents;
    private String idUserLoged;

    private StudentMainFragment studentMainFragmentF;
    private Student studentToUpdate;


    public UpdateStudentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        studentToUpdate = (Student) getArguments().getSerializable("StudentToUpdate");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View updateStudent = inflater.inflate(R.layout.fragment_add_student, container, false);


        backToAddEditMain = updateStudent.findViewById(R.id.backToAddEditMain);
        studentFirstName = updateStudent.findViewById(R.id.studentFirstName);
        studentLastName = updateStudent.findViewById(R.id.studentLastName);
        studentEmail = updateStudent.findViewById(R.id.studentEmail);
        isDelegateButton = updateStudent.findViewById(R.id.isDelegateButton);
        buttonStudents = updateStudent.findViewById(R.id.buttonStudents);
        addEdit = updateStudent.findViewById(R.id.addEdit);

        studentFirstName.setText(studentToUpdate.getStudentFirstName());
        studentLastName.setText(studentToUpdate.getStudentLastName());
        studentEmail.setText(studentToUpdate.getStudentEmail());

        buttonStudents.setText("UPDATE");
        addEdit.setText("update");


        //recovery loged user ID
        idUserLoged = ConfigFirebase.getUserId();

        backToAddEditMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToMain();
            }
        });

        buttonStudents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                studentUpdate();
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

        studentUpdate.setIdUser(idUserLoged);
        studentUpdate.setIdStudent(studentToUpdate.getIdStudent());
        studentUpdate.setStudentFirstName(studentFirstName.getText().toString());
        studentUpdate.setStudentLastName(studentLastName.getText().toString());
        studentUpdate.setStudentEmail(studentEmail.getText().toString());
        studentUpdate.setStudentDelegate(studentSaveDelegate);
        studentUpdate.update(studentUpdate);

        toastMsg("Student " + studentUpdate.getStudentFirstName() + " successfully update");
        backToMain();

    }

    public void backToMain() {

        studentMainFragmentF = new StudentMainFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameAddEditUserProfile, studentMainFragmentF);
        transaction.commit();
    }

    public void toastMsg(String text) {

        //show toast parameters
        Toast toastError = Toast.makeText(getContext(), text, Toast.LENGTH_LONG);
        toastError.setGravity(Gravity.CENTER, 0, 800);
        toastError.show();
    }

}
