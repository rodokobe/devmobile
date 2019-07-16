package com.projeto.academicplanner.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.projeto.academicplanner.R;
import com.projeto.academicplanner.activity.NavMainActivity;

public class AddEditMainFragment extends Fragment {

    private TextView homeActivity, yearCrudMain, universityCrudMain, coursesCrudMain, disciplinesCrudMain, eventsTypeCrudMain, studentsCrudMain, adminPeopleCrudMain;

    private NavMainActivity navMain;
    private YearMainFragment yearMain;
    private UniversityMainFragment universityMain;
    private CourseMainFragment courseMain;
    private DisciplineMainFragment disciplineMain;
    private EventTypeMainFragment eventTypeMain;
    private StudentMainFragment studentMain;
    private AdminPeopleMainFragment adminPeopleMain;

    private static final String TAG = "AddEditParametersActivity";

    public AddEditMainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        final View addEditMain = inflater.inflate(R.layout.fragment_add_edit_main, container, false);

        homeActivity = addEditMain.findViewById(R.id.homeActivity);
        yearCrudMain = addEditMain.findViewById(R.id.yearCrudMain);
        universityCrudMain = addEditMain.findViewById(R.id.universityCrudMain);
        coursesCrudMain = addEditMain.findViewById(R.id.coursesCrudMain);
        disciplinesCrudMain = addEditMain.findViewById(R.id.disciplinesCrudMain);
        eventsTypeCrudMain = addEditMain.findViewById(R.id.eventsTypeCrudMain);
        studentsCrudMain = addEditMain.findViewById(R.id.studentsCrudMain);
        adminPeopleCrudMain = addEditMain.findViewById(R.id.adminPeopleCrudMain);

        homeActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { goToNavMain(v); }
        });

        universityCrudMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { goToUniversityMain(v); }
        });

        universityCrudMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { goToUniversityMain(v); }
        });

        coursesCrudMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToCourseMain(v);
            }
        });

        disciplinesCrudMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToDisciplineMain(v);
            }
        });

        eventsTypeCrudMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToEventTypeMain(v);
            }
        });

        studentsCrudMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { goToStudentMain(v);}
        });

        adminPeopleCrudMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { goToAdminPeopleMain(v);}
        });

        yearCrudMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToYearMain(v);
            }
        });

        return addEditMain;
    }

    public void goToNavMain(View view) {

        Intent navMain = new Intent(getContext(), NavMainActivity.class);
        startActivity(navMain);

    }

    public void goToYearMain(View view) {

        yearMain = new YearMainFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameAddEditUserProfile, yearMain);
        transaction.commit();

    }

    public void goToUniversityMain(View view) {

        universityMain = new UniversityMainFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameAddEditUserProfile, universityMain);
        transaction.commit();

    }

    public void goToCourseMain(View view) {

        courseMain = new CourseMainFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameAddEditUserProfile, courseMain);
        transaction.commit();

    }

    public void goToDisciplineMain(View view) {

        disciplineMain = new DisciplineMainFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameAddEditUserProfile, disciplineMain);
        transaction.commit();

    }

    public void goToEventTypeMain(View view) {

        eventTypeMain = new EventTypeMainFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameAddEditUserProfile, eventTypeMain);
        transaction.commit();

    }

    public void goToStudentMain(View view) {

        studentMain = new StudentMainFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameAddEditUserProfile, studentMain);
        transaction.commit();

    }


    public void goToAdminPeopleMain(View view) {

        adminPeopleMain = new AdminPeopleMainFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameAddEditUserProfile, adminPeopleMain);
        transaction.commit();

    }


}
