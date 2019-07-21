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

public class StudentAddFragment extends Fragment implements IFirebaseLoadDoneCourse {

    private TextView backToPrevious;
    private EditText studentFirstName, studentLastName, studentEmail;
    private ToggleButton isDelegateButton;
    private Button buttonStudents;
    private SearchableSpinner spinnerCourses;
    private String idUserLogged, studentSaveFirstName, studentSaveLastName, studentSaveEmail, studentSaveDelegate,
                   idUniversitySelected, nameUniversitySelected, idCourseSelected, nameCourseSelected;

    private StudentMainFragment studentMainFragmentF;
    private DatabaseReference firebaseRefCourse;
    private IFirebaseLoadDoneCourse iFirebaseLoadDoneCourse;


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

        firebaseRefCourse = FirebaseDatabase.getInstance().getReference("courses").child(idUserLogged);

        iFirebaseLoadDoneCourse = this;

        backToPrevious.setOnClickListener( v -> {
                backToMain();
        });

        buttonStudents.setOnClickListener( v -> {

                studentSaveFirstName = studentFirstName.getText().toString();
                studentSaveLastName = studentLastName.getText().toString();
                studentSaveEmail = studentEmail.getText().toString();
                studentSaveDelegate = "NO";

                if (isDelegateButton.isChecked()) {
                    studentSaveDelegate = "YES";
                }

                studentAddNew();
        });

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

        return addStudent;

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

    private void studentAddNew() {

        if (!studentSaveFirstName.isEmpty()) {
            if (!studentSaveLastName.isEmpty()) {
                if (!studentSaveEmail.isEmpty()) {

                Student student = new Student();
                student.setIdUser(idUserLogged);
                student.setStudentFirstName(studentSaveFirstName);
                student.setStudentLastName(studentSaveLastName);
                student.setStudentEmail(studentSaveEmail);
                student.setStudentDelegate(studentSaveDelegate);
                student.setIdUniversity(idUniversitySelected);
                student.setUniversityName(nameUniversitySelected);
                student.setIdCourse(idCourseSelected);
                student.setCourseName(nameCourseSelected);
                student.setIdDiscipline("sem chave");
                student.setDisciplineName("Don't have disciplines associated");

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
        spinnerCourses = view.findViewById(R.id.spinnerCourses);
    }

}
