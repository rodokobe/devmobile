package com.projeto.academicplanner.fragment;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.projeto.academicplanner.Interface.IFirebaseLoadDoneCourse;
import com.projeto.academicplanner.R;
import com.projeto.academicplanner.helper.ConfigFirebase;
import com.projeto.academicplanner.model.Course;
import com.projeto.academicplanner.model.Student;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class StudentUpdateFragment extends Fragment implements IFirebaseLoadDoneCourse {

    private TextView backToPrevious;
    private EditText studentFirstName, studentLastName, studentEmail;
    private ToggleButton isDelegateButton;
    private Button buttonStudents;
    private String idUserLogged;
    private SearchableSpinner spinnerCourses;

    private String idUniversitySelected, nameUniversitySelected, idCourseSelected, nameCourseSelected,
            idDisciplineSelected, nameDisciplineSelected, idYearSelected, nameYearSelected, semester;

    private StudentMainFragment studentMainFragmentF;
    private Student studentToUpdate;

    private DatabaseReference firebaseRefCourse;
    private IFirebaseLoadDoneCourse iFirebaseLoadDoneCourse;

    public StudentUpdateFragment() {
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

        View updateStudent = inflater.inflate(R.layout.fragment_student_update, container, false);


        //recovery loged user ID
        idUserLogged = ConfigFirebase.getUserId();

        initializingComponents(updateStudent);

        studentFirstName.setText(studentToUpdate.getStudentFirstName());
        studentLastName.setText(studentToUpdate.getStudentLastName());
        studentEmail.setText(studentToUpdate.getStudentEmail());
        isDelegateButton.setText(studentToUpdate.getStudentDelegate());
        spinnerCourses.setTitle(studentToUpdate.getCourseName());

        /**
         * instances to load data and send to spinners
         */
        firebaseRefCourse = FirebaseDatabase.getInstance().getReference("courses").child(idUserLogged);

        iFirebaseLoadDoneCourse = this;

        /**
         * Setting action on buttons
         */

        backToPrevious.setOnClickListener(v -> {
            backToMain();
        });

        buttonStudents.setOnClickListener(v -> {
            studentUpdate();
        });

        /**
         * load fields to the Discipline spinner
         */
        //load fields to the Course spinner
        firebaseRefCourse.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                List<Course> courses = new ArrayList<>();

                for (DataSnapshot coursesSnapShot : dataSnapshot.getChildren()) {

                    courses.add(coursesSnapShot.getValue(Course.class));
                    iFirebaseLoadDoneCourse.onFireBaseLoadCourseSuccess(courses);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                iFirebaseLoadDoneCourse.onFireBaseLoadCourseFailed(databaseError.getMessage());
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

        studentUpdate.setIdUser(idUserLogged);
        studentUpdate.setIdStudent(studentToUpdate.getIdStudent());
        studentUpdate.setStudentFirstName(studentFirstName.getText().toString());
        studentUpdate.setStudentLastName(studentLastName.getText().toString());
        studentUpdate.setStudentEmail(studentEmail.getText().toString());
        studentUpdate.setStudentDelegate(studentSaveDelegate);
        studentUpdate.setIdUniversity(idUniversitySelected);
        studentUpdate.setUniversityName(nameUniversitySelected);
        studentUpdate.setIdCourse(idCourseSelected);
        studentUpdate.setCourseName(nameCourseSelected);


        studentUpdate.update(studentUpdate);

        toastMsg("Student " + studentUpdate.getStudentFirstName() + " successfully update");
        backToMain();

    }

    //charge the spinner Course values
    @Override
    public void onFireBaseLoadCourseSuccess(final List<Course> coursesList) {

        //universitySpinner = universitiesList;
        final List<String> university_name = new ArrayList<>();
        for (Course course : coursesList)

            university_name.add(course.getUniversityName() + "\n" + course.getCourseName());

        //Create adapter
        ArrayAdapter<String> adapterUniversity = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, university_name);
        spinnerCourses.setAdapter(adapterUniversity);

        spinnerCourses.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                idUniversitySelected = coursesList.get(position).getIdUniversity();
                nameUniversitySelected = coursesList.get(position).getUniversityName();
                idCourseSelected = coursesList.get(position).getIdCourse();
                nameCourseSelected = coursesList.get(position).getCourseName();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    @Override
    public void onFireBaseLoadCourseFailed(String message) {
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

    private void initializingComponents(View view) {

        spinnerCourses = view.findViewById(R.id.spinnerCourses);
        backToPrevious = view.findViewById(R.id.backToPrevious);
        studentFirstName = view.findViewById(R.id.studentFirstName);
        studentLastName = view.findViewById(R.id.studentLastName);
        studentEmail = view.findViewById(R.id.studentEmail);
        isDelegateButton = view.findViewById(R.id.isDelegateButton);
        buttonStudents = view.findViewById(R.id.buttonStudents);

    }

}
