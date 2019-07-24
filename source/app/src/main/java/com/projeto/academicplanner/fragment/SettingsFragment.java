package com.projeto.academicplanner.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.projeto.academicplanner.R;
import com.projeto.academicplanner.activity.NavMainActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {

    private ImageView imageViewYears, imageViewUniversities, imageViewCourses, imageViewDisciplines, imageViewStudents, imageViewAdminPeople;
    private TextView homeActivity;

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


        imageViewYears.setOnClickListener( view -> {
            goToYearMain(view);
        });

        imageViewUniversities.setOnClickListener( view -> {
            goToUniversityMain(view);
        });

        imageViewCourses.setOnClickListener( view -> {
                goToCourseMain(view);
        });

        imageViewDisciplines.setOnClickListener( view -> {
                goToDisciplineMain(view);
        });

        imageViewStudents.setOnClickListener( view -> {
            goToStudentMain(view);
        });

        imageViewAdminPeople.setOnClickListener( view -> {
            goToAdminPeopleMain(view);
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
        imageViewYears = view.findViewById(R.id.imageViewYears);
        imageViewUniversities = view.findViewById(R.id.imageViewUniversities);
        imageViewCourses = view.findViewById(R.id.imageViewCourses);
        imageViewDisciplines = view.findViewById(R.id.imageViewDisciplines);
        imageViewStudents = view.findViewById(R.id.imageViewStudents);
        imageViewAdminPeople = view.findViewById(R.id.imageViewAdminPeople);
    }

}
