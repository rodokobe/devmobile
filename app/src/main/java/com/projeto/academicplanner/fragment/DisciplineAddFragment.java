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
import android.widget.Switch;
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
import com.projeto.academicplanner.Interface.IFirebaseLoadDoneCourse;
import com.projeto.academicplanner.Interface.IFirebaseLoadDoneYears;
import com.projeto.academicplanner.R;
import com.projeto.academicplanner.helper.ConfigFirebase;
import com.projeto.academicplanner.model.Course;
import com.projeto.academicplanner.model.Discipline;
import com.projeto.academicplanner.model.Years;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.List;

public class DisciplineAddFragment extends Fragment implements IFirebaseLoadDoneCourse, IFirebaseLoadDoneYears {

    private EditText disciplineName, acronymDiscipline;
    private TextView backToPrevious;
    private SearchableSpinner spinnerUniversity, spinnerYear;
    private Switch switchSemester;
    private Button buttonDisciplines;
    private String idUserLogged, idUniversitySelected, nameUniversitySelected, idCourseSelected, nameCourseSelected, idYearSelected, nameYearSelected;

    private DatabaseReference firebaseRefCourse, firebaseRefYear;
    private IFirebaseLoadDoneCourse iFirebaseLoadDoneCourse;
    private IFirebaseLoadDoneYears iFirebaseLoadDoneYears;
    private DisciplineMainFragment disciplineMainFragmentF;

    public DisciplineAddFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View addDiscipline = inflater.inflate(R.layout.fragment_discipline_add, container, false);

        //configurações iniciais
        initializingComponents(addDiscipline);

        idUserLogged = ConfigFirebase.getUserId();

        //instances to load data and send to spinners
        firebaseRefCourse = FirebaseDatabase.getInstance().getReference("courses").child(idUserLogged);
        firebaseRefYear = FirebaseDatabase.getInstance().getReference("years").child(idUserLogged);


        iFirebaseLoadDoneCourse = this;
        iFirebaseLoadDoneYears = this;


        buttonDisciplines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String disciplineSaveName = disciplineName.getText().toString();
                String disciplineSaveAcronym = acronymDiscipline.getText().toString();
                String disciplineSaveSemester = "1";

                if (switchSemester.isChecked()) {
                    disciplineSaveSemester = "2";
                }


                disciplineAddNew(disciplineSaveName, disciplineSaveAcronym, idYearSelected, nameYearSelected, disciplineSaveSemester,
                        idUniversitySelected, nameUniversitySelected, idCourseSelected, nameCourseSelected);

            }
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

        //load fields to the Year spinner
        firebaseRefYear.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                List<Years> years = new ArrayList<>();

                for (DataSnapshot coursesSnapShot : dataSnapshot.getChildren()) {

                    years.add(coursesSnapShot.getValue(Years.class));
                    iFirebaseLoadDoneYears.onFireBaseLoadYearSuccess(years);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                iFirebaseLoadDoneYears.onFireBaseLoadYearFailed(databaseError.getMessage());
            }
        });


        backToPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToMain();
            }
        });

        return addDiscipline;

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
        spinnerUniversity.setAdapter(adapterUniversity);

        spinnerUniversity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

    //charge the spinner Year values
    @Override
    public void onFireBaseLoadYearSuccess(final List<Years> yearsList) {

        //universitySpinner = universitiesList;
        final List<String> year_name = new ArrayList<>();
        for (Years year : yearsList)

            year_name.add(year.getYearName());

        //Create adapter
        ArrayAdapter<String> adapterYear = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, year_name);
        spinnerYear.setAdapter(adapterYear);

        spinnerYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                idYearSelected = yearsList.get(position).getIdYear();
                nameYearSelected = yearsList.get(position).getYearName();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    @Override
    public void onFireBaseLoadYearFailed(String message) {
    }


    private void disciplineAddNew(String disciplineSaveName, String disciplineSaveAcronym, String disciplineSaveYearId, String disciplineSaveYearName, String disciplineSaveSemester,
                                  String idUniversitySelected, String nameUniversitySelected, String idCourseSelected, String nameCourseSelected) {

        if (!disciplineSaveName.isEmpty()) {
            if (!disciplineSaveAcronym.isEmpty()) {

                Discipline discipline = new Discipline();
                discipline.setIdUser(idUserLogged);
                discipline.setDisciplineName(disciplineSaveName);
                discipline.setAcronymDiscipline(disciplineSaveAcronym);
                discipline.setDisciplineYearId(disciplineSaveYearId);
                discipline.setDisciplineYearName(disciplineSaveYearName);
                discipline.setDisciplineSemester(disciplineSaveSemester);
                discipline.setIdUniversity(idUniversitySelected);
                discipline.setUniversityName(nameUniversitySelected);
                discipline.setIdCourse(idCourseSelected);
                discipline.setCourseName(nameCourseSelected);

                discipline.save();

                toastMsg("Discipline " + disciplineSaveName + " added to " + nameCourseSelected + " course!");
                backToMain();

            } else {
                toastMsg("Enter an acronym to Discipline");
            }
        } else {
            toastMsg("Enter an Discipline name");
        }

    }

    public void backToMain() {
        disciplineMainFragmentF = new DisciplineMainFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameSettingsMain, disciplineMainFragmentF);
        transaction.commit();
    }


    public void toastMsg(String text) {

        //show toast parameters
        Toast toastError = Toast.makeText(getContext(), text, Toast.LENGTH_LONG);
        toastError.setGravity(Gravity.CENTER, 0, 800);
        toastError.show();

    }

    private void initializingComponents(View view){
        disciplineName = view.findViewById(R.id.disciplineName);
        acronymDiscipline = view.findViewById(R.id.acronymDiscipline);
        backToPrevious = view.findViewById(R.id.backToPrevious);
        spinnerUniversity = view.findViewById(R.id.spinnerUniversity);
        spinnerYear = view.findViewById(R.id.spinnerYear);
        switchSemester = view.findViewById(R.id.switchSemester);
        buttonDisciplines = view.findViewById(R.id.buttonDisciplines);
    }

}
