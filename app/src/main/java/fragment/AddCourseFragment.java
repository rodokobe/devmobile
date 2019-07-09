package fragment;

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
import com.projeto.academicplanner.R;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.List;

import Interface.IFirebaseLoadDoneUniversity;
import helper.ConfigFirebase;
import model.Course;
import model.University;

public class AddCourseFragment extends Fragment implements IFirebaseLoadDoneUniversity {

    private EditText courseName, courseAcronym;
    private TextView backText;
    private SearchableSpinner spinnerUniversity;
    private Button buttonCourse;
    private String idUserLoged, idUniversitySelected, nameUniversitySelected;

    private DatabaseReference firebaseRefUniversity;
    private IFirebaseLoadDoneUniversity iFirebaseLoadDoneUniversity;
    private CourseMainFragment courseMainFragmentF;

    public AddCourseFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View addCourse = inflater.inflate(R.layout.fragment_add_course, container, false);

        //configurações iniciais
        backText = addCourse.findViewById(R.id.backText);
        courseName = addCourse.findViewById(R.id.courseName);
        courseAcronym = addCourse.findViewById(R.id.courseAcronym);
        spinnerUniversity = addCourse.findViewById(R.id.spinnerUniversity);
        buttonCourse = addCourse.findViewById(R.id.buttonCourse);

        idUserLoged = ConfigFirebase.getUserId();

        firebaseRefUniversity = FirebaseDatabase.getInstance().getReference("universities").child(idUserLoged);

        iFirebaseLoadDoneUniversity = this;

        buttonCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        String courseSaveName = courseName.getText().toString();
                        String courseSaveAcronym = courseAcronym.getText().toString();

                        courseAddNew(courseSaveName, courseSaveAcronym, idUniversitySelected, nameUniversitySelected);
                }
        });

        backText.setOnClickListener(new View.OnClickListener() {
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
                    iFirebaseLoadDoneUniversity.onFireBaseLoadSuccess(universities);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                iFirebaseLoadDoneUniversity.onFireBaseLoadFailed(databaseError.getMessage());
            }
        });
        return addCourse;
    }

    @Override
    public void onFireBaseLoadSuccess(final List<University> universitiesList) {

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
    public void onFireBaseLoadFailed(String message) {
    }

    private void courseAddNew(String courseDialogName, String courseDialogAcronym, String idUniversitySelected, String nameUniversitySelected) {

        if (!courseDialogName.isEmpty()) {
            if (!courseDialogAcronym.isEmpty()) {

                Course course = new Course();
                course.setIdUser(idUserLoged);
                course.setCourseName(courseDialogName);
                course.setAcronymCourse(courseDialogAcronym);
                course.setIdUniversity(idUniversitySelected);
                course.setUniversityName(nameUniversitySelected);

                course.saveCourseData();

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

    public void toastMsg(String text) {

        //show toast parameters
        Toast toastError = Toast.makeText(getContext(), text, Toast.LENGTH_LONG);
        toastError.setGravity(Gravity.CENTER, 0, 800);
        toastError.show();

    }

    public void backToMain() {
        courseMainFragmentF = new CourseMainFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameAddEditParameters, courseMainFragmentF);
        transaction.commit();
    }

}
