package com.projeto.academicplanner.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.projeto.academicplanner.R;
import com.projeto.academicplanner.activity.NavMainActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {

    private TextView homeActivity, yearCrudMain, universityCrudMain, coursesCrudMain, disciplinesCrudMain, eventsTypeCrudMain, studentsCrudMain, adminPeopleCrudMain;

    private NavMainActivity navMain;
    private YearMainFragment yearMain;
    private UniversityMainFragment universityMain;
    private CourseMainFragment courseMain;
    private DisciplineMainFragment disciplineMain;
    private StudentMainFragment studentMain;
    private AdminPeopleMainFragment adminPeopleMain;


    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_settings, container, false);

        initializingComponents(v);


        universityCrudMain.setOnClickListener( view -> {
            goToUniversityMain(view);
        });

        universityCrudMain.setOnClickListener( view -> {
            goToUniversityMain(view);
        });

        coursesCrudMain.setOnClickListener( view -> {
                goToCourseMain(view);
        });

        disciplinesCrudMain.setOnClickListener( view -> {
                goToDisciplineMain(view);
        });

        studentsCrudMain.setOnClickListener( view -> {
            goToStudentMain(view);
        });

        adminPeopleCrudMain.setOnClickListener( view -> {
            goToAdminPeopleMain(view);
        });

        yearCrudMain.setOnClickListener( view -> {
                goToYearMain(view);
        });

        return v;
    }


    public void goToYearMain(View view) {

        yearMain = new YearMainFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameSettingsMain, yearMain);
        transaction.commit();

    }

    public void goToUniversityMain(View view) {

        universityMain = new UniversityMainFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameSettingsMain, universityMain);
        transaction.commit();

    }

    public void goToCourseMain(View view) {

        courseMain = new CourseMainFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameSettingsMain, courseMain);
        transaction.commit();

    }

    public void goToDisciplineMain(View view) {

        disciplineMain = new DisciplineMainFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameSettingsMain, disciplineMain);
        transaction.commit();

    }

    public void goToStudentMain(View view) {

        studentMain = new StudentMainFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameSettingsMain, studentMain);
        transaction.commit();

    }


    public void goToAdminPeopleMain(View view) {

        adminPeopleMain = new AdminPeopleMainFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameSettingsMain, adminPeopleMain);
        transaction.commit();

    }

    private void initializingComponents(View view){
        yearCrudMain = view.findViewById(R.id.yearCrudMain);
        universityCrudMain = view.findViewById(R.id.universityCrudMain);
        coursesCrudMain = view.findViewById(R.id.coursesCrudMain);
        disciplinesCrudMain = view.findViewById(R.id.disciplinesCrudMain);
        studentsCrudMain = view.findViewById(R.id.studentsCrudMain);
        adminPeopleCrudMain = view.findViewById(R.id.adminPeopleCrudMain);
    }

}
