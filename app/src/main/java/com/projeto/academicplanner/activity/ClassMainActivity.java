package com.projeto.academicplanner.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.projeto.academicplanner.Interface.IFirebaseLoadDoneClasses;
import com.projeto.academicplanner.Interface.IFirebaseLoadDoneCourse;
import com.projeto.academicplanner.Interface.IFirebaseLoadDoneDiscipline;
import com.projeto.academicplanner.Interface.IFirebaseLoadDoneUniversity;
import com.projeto.academicplanner.Interface.IFirebaseLoadDoneYears;
import com.projeto.academicplanner.R;
import com.projeto.academicplanner.fragment.ClassAddFragment;
import com.projeto.academicplanner.fragment.ClassUpdateFragment;
import com.projeto.academicplanner.helper.ConfigFirebase;
import com.projeto.academicplanner.model.Course;
import com.projeto.academicplanner.model.University;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.List;

public class ClassMainActivity extends AppCompatActivity
        /*implements IFirebaseLoadDoneYears, IFirebaseLoadDoneUniversity,
        IFirebaseLoadDoneCourse, IFirebaseLoadDoneDiscipline, IFirebaseLoadDoneClasses*/ {

    private ClassAddFragment classAddFragment;
    private ClassUpdateFragment classUpdateFragment;

    String idUniversitySelectedU, nameUniversitySelectedU;
    String idCourseSelectedC, nameCourseSelectedC;


    private String idUserLogged;
    private SearchableSpinner spinnerYears, spinnerUniversities, spinnerCourses, spinnerDisciplines;

    private IFirebaseLoadDoneYears iFirebaseLoadDoneYears;
    private IFirebaseLoadDoneUniversity iFirebaseLoadDoneUniversity;
    private IFirebaseLoadDoneCourse iFirebaseLoadDoneCourse;
    private IFirebaseLoadDoneDiscipline iFirebaseLoadDoneDiscipline;
    private IFirebaseLoadDoneClasses iFirebaseLoadDoneClasses;

    private DatabaseReference yearsRef, universitiesRef, coursesRef, disciplinesRef, classesRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add New Class");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initializingComponents();

        addClassFragment();

    }

    private void addClassFragment() {
        classAddFragment = new ClassAddFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameClassMain, classAddFragment);
        transaction.commit();
    }

    private void updateClassFragment() {
        classUpdateFragment = new ClassUpdateFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameClassMain, classUpdateFragment);
        transaction.commit();
    }

   /* @Override
    public void onFireBaseLoadUniversitySuccess(final List<University> universitiesList) {

        //universitySpinner = universitiesList;
        final List<String> university_name = new ArrayList<>();
        for (University university : universitiesList)

            university_name.add(university.getUniversityName());

        //Create adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, university_name);
        spinnerUniversities.setAdapter(adapter);

        spinnerUniversities.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                idUniversitySelectedU = universitiesList.get(position).getIdUniversity();
                nameUniversitySelectedU = universitiesList.get(position).getUniversityName();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    public void onFireBaseLoadUniversityFailed(String message) {
    }

    //charge the spinner Course values
    @Override
    public void onFireBaseLoadCourseSuccess(final List<Course> coursesList) {

        //universitySpinner = universitiesList;
        final List<String> university_name = new ArrayList<>();
        for (Course course : coursesList)

            university_name.add(course.getUniversityName() + "\n" + course.getCourseName());

        //Create adapter
        ArrayAdapter<String> adapterUniversity = new ArrayAdapter<>( this, android.R.layout.simple_list_item_1, university_name);
        spinnerCourses.setAdapter(adapterUniversity);

        spinnerCourses.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                idCourseSelectedC = coursesList.get(position).getIdCourse();
                nameCourseSelectedC = coursesList.get(position).getCourseName();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    @Override
    public void onFireBaseLoadCourseFailed(String message) {
    }
*/

    private void initializingComponents(){

        spinnerYears = findViewById(R.id.spinnerYears);
        spinnerUniversities = findViewById(R.id.spinnerUniversities);
        spinnerCourses = findViewById(R.id.spinnerCourses);
        spinnerDisciplines = findViewById(R.id.spinnerDisciplines);

        idUserLogged = ConfigFirebase.getUserId();

        yearsRef = FirebaseDatabase.getInstance().getReference("years").child(idUserLogged);
        universitiesRef = FirebaseDatabase.getInstance().getReference("universities").child(idUserLogged);
        coursesRef = FirebaseDatabase.getInstance().getReference("courses").child(idUserLogged);
        disciplinesRef = FirebaseDatabase.getInstance().getReference("disciplines").child(idUserLogged);
        classesRef = FirebaseDatabase.getInstance().getReference("disciplines").child(idUserLogged);


       /* iFirebaseLoadDoneYears = this;
        iFirebaseLoadDoneUniversity = this;
        iFirebaseLoadDoneCourse = this;
        iFirebaseLoadDoneDiscipline = this;
        iFirebaseLoadDoneClasses = this;*/

    }

}
