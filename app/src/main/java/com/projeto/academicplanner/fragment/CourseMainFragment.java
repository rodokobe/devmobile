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
import com.projeto.academicplanner.model.Course;

import java.util.ArrayList;
import java.util.List;

public class CourseMainFragment extends Fragment {

    //general variables
    private Button buttonCourses;
    private TextView backToAddEditMain;
    private String idUserLoged;
    private List<Course> courses = new ArrayList<>();
    private CourseAddFragment addCourseFragmentF;
    private CourseUpdateFragment updateCourseFragmentF;
    private AddEditMainFragment fragmentMain;

    //recycler view variables
    private RecyclerView recylcerCourses;
    private RecyclerView.LayoutManager layout;
    private Adapter_Courses adapter;

    private static final String TAG = "AddEditParametersActivity";


    public CourseMainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View mainCourses = inflater.inflate(R.layout.fragment_course_main, container, false);

        //start configurations
        buttonCourses = mainCourses.findViewById(R.id.buttonCourses);
        backToAddEditMain = mainCourses.findViewById(R.id.backToAddEditMain);
        recylcerCourses = mainCourses.findViewById(R.id.recylcerCourses);

        //recovery loged user ID
        idUserLoged = ConfigFirebase.getUserId();

        //call methods
        adapterConstructor();

        //create object and fill recyclerViewCourses
        Course course = new Course();
        course.recovery(idUserLoged, courses, adapter);

        buttonCourses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToNewFragment();
            }
        });

        backToAddEditMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBackToMain();
            }
        });

        return mainCourses;

    }

    private void adapterConstructor() {

        //recycler view configuration
        layout = new LinearLayoutManager(getContext());
        adapter = new Adapter_Courses(courses, getContext());
        recylcerCourses.setAdapter(adapter);
        recylcerCourses.setLayoutManager(layout);
        recylcerCourses.setHasFixedSize(true);
        recylcerCourses.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayout.VERTICAL));

        adapter.setOnItemClickListener(new Adapter_Courses.ClickListener() {
            @Override
            public void onItemClick(Adapter_Courses adapter_courses, View v, final int position) {

                final ImageView imageEdit = v.findViewById(R.id.imageEdit);
                final ImageView imageDelete = v.findViewById(R.id.imageDelete);

                final Course objectToAction = courses.get(position);

                imageDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        courseDelete(objectToAction);

                    }
                });

                imageEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        goToUpdateFragment(objectToAction);

                    }
                });
            }
        });

    }

    private void courseDelete(final Course selectedToRemove) {

        final AlertDialog.Builder deleteDialog = new AlertDialog.Builder(getContext());
        final View deleteDialogView = getLayoutInflater().inflate(R.layout.dialog_model_delete_request, null);

        final Button buttonNoDelete = deleteDialogView.findViewById(R.id.buttonNoDelete);
        final Button buttonDelete = deleteDialogView.findViewById(R.id.buttonDelete);

        //method to create and show AlertDialog to DELETE
        deleteDialog.setView(deleteDialogView);
        final AlertDialog deleteDialogAlert = deleteDialog.create();
        deleteDialogAlert.show();

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //method to remove the selected object
                selectedToRemove.delete();
                toastMsg("Course " + selectedToRemove.getCourseName() + " has been removed!");
                adapter.notifyDataSetChanged();
                deleteDialogAlert.cancel();

                //call methods
                adapterConstructor();

                //create object and fill recyclerViewCourses
                Course course = new Course();
                course.recovery(idUserLoged, courses, adapter);
            }
        });

        buttonNoDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //method to cancel the delete operation
                toastMsg("Request CANCELED");
                deleteDialogAlert.cancel();
            }
        });
    }

    public void goToNewFragment() {
        addCourseFragmentF = new CourseAddFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameAddEditUserProfile, addCourseFragmentF);
        transaction.commit();
    }

    public void goToUpdateFragment(Course objectToAction) {
        updateCourseFragmentF = new CourseUpdateFragment();
        Bundle dataToUpdate = new Bundle();
        dataToUpdate.putSerializable("CourseToUpdate", objectToAction);

        updateCourseFragmentF.setArguments(dataToUpdate);

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameAddEditUserProfile, updateCourseFragmentF);
        transaction.commit();
    }

    public void goBackToMain() {

        fragmentMain = new AddEditMainFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameAddEditUserProfile, fragmentMain);
        transaction.commit();

    }

    public void toastMsg(String text) {

        //show toast parameters
        Toast toastError = Toast.makeText(getContext(), text, Toast.LENGTH_LONG);
        toastError.setGravity(Gravity.CENTER, 0, 800);
        toastError.show();

    }
}
