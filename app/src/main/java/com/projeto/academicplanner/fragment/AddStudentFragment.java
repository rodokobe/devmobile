package com.projeto.academicplanner.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.projeto.academicplanner.R;

public class AddStudentFragment extends Fragment {

    private TextView backToAddEditMain;
    private StudentMainFragment studentMainFragmentF;

    public AddStudentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View addStudent = inflater.inflate(R.layout.fragment_add_student, container, false);


        backToAddEditMain = addStudent.findViewById(R.id.backToAddEditMain);


        backToAddEditMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBackToMain();
            }
        });

        return addStudent;

    }

    public void goBackToMain() {

        studentMainFragmentF = new StudentMainFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameAddEditUserProfile, studentMainFragmentF);
        transaction.commit();

    }

}
