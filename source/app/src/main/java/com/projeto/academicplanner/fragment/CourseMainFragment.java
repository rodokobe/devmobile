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
import com.projeto.academicplanner.adapter.Adapter_Courses;
import com.projeto.academicplanner.helper.ConfigFirebase;
import com.projeto.academicplanner.model.AdminPeople;
import com.projeto.academicplanner.model.Course;

import java.util.ArrayList;
import java.util.List;

public class CourseMainFragment extends Fragment {

    //general variables
    private Button buttonCourses;
    private TextView backToPrevious;
    private String idUserLogged;
    private List<Course> courses = new ArrayList<>();
    private CourseAddFragment addCourseFragmentF;
    private CourseUpdateFragment updateCourseFragmentF;
    private SettingsFragment settingsFragment;

    //recycler view variables
    private RecyclerView recyclerCourses;
    private RecyclerView.LayoutManager layout;
    private Adapter_Courses adapter;


    public CourseMainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View mainCourses = inflater.inflate(R.layout.fragment_course_main, container, false);

        //start configurations
        initializingComponents(mainCourses);

        //recovery loged user ID
        idUserLogged = ConfigFirebase.getUserId();

        //call methods
        adapterConstructor();

        //create object and fill recyclerViewCourses
        Course course = new Course();
        course.recovery(idUserLogged, courses, adapter);

        buttonCourses.setOnClickListener( v -> {
                goToNewFragment();
        });

        backToPrevious.setOnClickListener( v -> {
                backToMainSettings();
        });

        return mainCourses;

    }

    private void adapterConstructor() {

        //recycler view configuration
        layout = new LinearLayoutManager(getContext());
        adapter = new Adapter_Courses(courses, getContext());
        recyclerCourses.setAdapter(adapter);
        recyclerCourses.setLayoutManager(layout);
        recyclerCourses.setHasFixedSize(true);
        //recyclerCourses.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayout.VERTICAL));

        adapter.setOnItemClickListener( (adapter_courses, v, position) -> {

                final ImageView imageEdit = v.findViewById(R.id.imageEdit);
                final ImageView imageDelete = v.findViewById(R.id.imageDelete);

                final Course objectToAction = courses.get(position);

                imageDelete.setOnClickListener( view -> {

                        courseDelete(objectToAction);

                });

                imageEdit.setOnClickListener( view -> {

                        goToUpdateFragment(objectToAction);
                });
        });

    }

    private void courseDelete(final Course selectedToRemove) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        String name = selectedToRemove.getCourseName();
        String msg = "Are you sure, you want to delete the course " + name + "?";

        builder.setTitle(msg);
        builder.setPositiveButton(android.R.string.yes, (dialog, id) -> {

            selectedToRemove.delete();
            toastMsgLong("Course " + name + " has been removed!");
            adapter.notifyDataSetChanged();
            dialog.dismiss();

            //call methods
            adapterConstructor();

            //create object and fill recyclerViewCourses
            Course course = new Course();
            course.recovery(idUserLogged, courses, adapter);

        });

        builder.setNegativeButton(android.R.string.no, (dialog, id) -> {
            //method to cancel the delete operation
            toastMsgLong("Request CANCELED");
            dialog.dismiss();
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void goToNewFragment() {
        addCourseFragmentF = new CourseAddFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameSettingsMain, addCourseFragmentF);
        transaction.commit();
    }

    public void goToUpdateFragment(Course objectToAction) {
        updateCourseFragmentF = new CourseUpdateFragment();
        Bundle dataToUpdate = new Bundle();
        dataToUpdate.putSerializable("CourseToUpdate", objectToAction);

        updateCourseFragmentF.setArguments(dataToUpdate);

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameSettingsMain, updateCourseFragmentF);
        transaction.commit();
    }

    public void toastMsgLong(String text) {

        //show toast parameters
        Toast toastError = Toast.makeText(getContext(), text, Toast.LENGTH_LONG);
        toastError.setGravity(Gravity.CENTER, 0, 800);
        toastError.show();

    }

    private void backToMainSettings(){

        settingsFragment = new SettingsFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameSettingsMain, settingsFragment);
        transaction.commit();

    }

    public void initializingComponents(View view){
        buttonCourses = view.findViewById(R.id.buttonCourses);
        backToPrevious = view.findViewById(R.id.backToPrevious);
        recyclerCourses = view.findViewById(R.id.recyclerCourses);
    }
}
