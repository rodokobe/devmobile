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

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.projeto.academicplanner.Interface.IFirebaseLoadDoneUniversity;
import com.projeto.academicplanner.R;
import com.projeto.academicplanner.helper.ConfigFirebase;
import com.projeto.academicplanner.model.Course;
import com.projeto.academicplanner.model.University;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.List;

public class CourseAddFragment extends Fragment implements IFirebaseLoadDoneUniversity {

    private EditText courseName, courseAcronym;
    private TextView backToPrevious;
    private SearchableSpinner spinnerUniversity;
    private Button buttonCourse;
    private String idUserLogged, idUniversitySelected, nameUniversitySelected;

    private DatabaseReference firebaseRefUniversity;
    private IFirebaseLoadDoneUniversity iFirebaseLoadDoneUniversity;
    private CourseMainFragment courseMainFragmentF;

    public CourseAddFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View addCourse = inflater.inflate(R.layout.fragment_course_add, container, false);

        //configurações iniciais
        initializingComponents(addCourse);

        idUserLogged = ConfigFirebase.getUserId();

        firebaseRefUniversity = FirebaseDatabase.getInstance().getReference("universities").child(idUserLogged);

        iFirebaseLoadDoneUniversity = this;


        buttonCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        String courseSaveName = courseName.getText().toString();
                        String courseSaveAcronym = courseAcronym.getText().toString();

                        courseAddNew(courseSaveName, courseSaveAcronym, idUniversitySelected, nameUniversitySelected);
                }
        });

        backToPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToMain();
            }
        });

        firebaseRefUniversity.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                List<University> universities = new ArrayList<>();

                for (DataSnapshot universitiesSnapShot : dataSnapshot.getChildren()) {

                    universities.add(universitiesSnapShot.getValue(University.class));
                    iFirebaseLoadDoneUniversity.onFireBaseLoadUniversitySuccess(universities);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                iFirebaseLoadDoneUniversity.onFireBaseLoadUniversityFailed(databaseError.getMessage());
            }
        });
        return addCourse;
    }

    @Override
    public void onFireBaseLoadUniversitySuccess(final List<University> universitiesList) {

        //universitySpinner = universitiesList;
        final List<String> university_name = new ArrayList<>();
        for (University university : universitiesList)

            university_name.add(university.getUniversityName());

        //Create adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, university_name);
        spinnerUniversity.setAdapter(adapter);

        spinnerUniversity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                idUniversitySelected = universitiesList.get(position).getIdUniversity();
                nameUniversitySelected = universitiesList.get(position).getUniversityName();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    public void onFireBaseLoadUniversityFailed(String message) {
    }

    private void courseAddNew(String courseDialogName, String courseDialogAcronym, String idUniversitySelected, String nameUniversitySelected) {

        if (!courseDialogName.isEmpty()) {
            if (!courseDialogAcronym.isEmpty()) {

                Course course = new Course();
                course.setIdUser(idUserLogged);
                course.setCourseName(courseDialogName);
                course.setAcronymCourse(courseDialogAcronym);
                course.setIdUniversity(idUniversitySelected);
                course.setUniversityName(nameUniversitySelected);

                course.save();

                courseName.setText("");
                courseAcronym.setText("");
                toastMsg("Course " + courseDialogName + " added to " + nameUniversitySelected + " university!");
                backToMain();

            } else {
                toastMsg("Enter an acronym to Course");
            }
        } else {
            toastMsg("Enter an Course name");
        }

    }

    public void backToMain() {
        courseMainFragmentF = new CourseMainFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameSettingsMain, courseMainFragmentF);
        transaction.commit();
    }

    public void toastMsg(String text) {

        //show toast parameters
        Toast toastError = Toast.makeText(getContext(), text, Toast.LENGTH_LONG);
        toastError.setGravity(Gravity.CENTER, 0, 800);
        toastError.show();

    }

    private void initializingComponents(View v) {
        backToPrevious = v.findViewById(R.id.backToPrevious);
        courseName = v.findViewById(R.id.courseName);
        courseAcronym = v.findViewById(R.id.courseAcronym);
        spinnerUniversity = v.findViewById(R.id.spinnerUniversity);
        buttonCourse = v.findViewById(R.id.buttonCourse);
    }

}
