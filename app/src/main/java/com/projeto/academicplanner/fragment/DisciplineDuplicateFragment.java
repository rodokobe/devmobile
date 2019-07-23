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
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.projeto.academicplanner.Interface.IFirebaseLoadDoneCourse;
import com.projeto.academicplanner.Interface.IFirebaseLoadDoneYears;
import com.projeto.academicplanner.R;
import com.projeto.academicplanner.adapter.Adapter_Classes_Calendar;
import com.projeto.academicplanner.helper.ConfigFirebase;
import com.projeto.academicplanner.model.Classes;
import com.projeto.academicplanner.model.Course;
import com.projeto.academicplanner.model.Discipline;
import com.projeto.academicplanner.model.Years;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.List;

public class DisciplineDuplicateFragment extends Fragment implements IFirebaseLoadDoneCourse, IFirebaseLoadDoneYears {

    private EditText disciplineName, acronymDiscipline;
    private TextView backToPrevious;
    private SearchableSpinner spinnerUniversity, spinnerYear;
    private Switch switchSemester;
    private Button buttonDisciplines;
    private String idUserLogged, idUniversitySelected, nameUniversitySelected, idCourseSelected, nameCourseSelected, idYearSelected, nameYearSelected;
    private Discipline disciplineToDuplicate;
    private List<Classes> classes = new ArrayList<>();

    private DatabaseReference firebaseRefCourse, firebaseRefYear;
    private IFirebaseLoadDoneCourse iFirebaseLoadDoneCourse;
    private IFirebaseLoadDoneYears iFirebaseLoadDoneYears;
    private DisciplineMainFragment disciplineMainFragmentF;

    //recycler view variables
    private RecyclerView recyclerClasses;
    private RecyclerView.LayoutManager layout;
    private Adapter_Classes_Calendar adapter;

    public DisciplineDuplicateFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        disciplineToDuplicate = (Discipline) getArguments().getSerializable("DisciplineToDuplicate");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View duplicateDiscipline = inflater.inflate(R.layout.fragment_discipline_duplicate, container, false);

        //configurações iniciais
        initializingComponents(duplicateDiscipline);

        idUserLogged = ConfigFirebase.getUserId();


        //call methods
        adapterConstructor();

        //create object and fill recyclerViewCourses
        Classes classe = new Classes();
        classe.recoveryClassesInDiscipline(idUserLogged, disciplineToDuplicate.getIdDiscipline(), classes, adapter);



        disciplineName.setText(disciplineToDuplicate.getDisciplineName());
        acronymDiscipline.setText(disciplineToDuplicate.getAcronymDiscipline());
        spinnerUniversity.setTitle(disciplineToDuplicate.getCourseName());
        spinnerYear.setTitle(disciplineToDuplicate.getDisciplineYearName());

        String switchSem = disciplineToDuplicate.getDisciplineSemester();

        if (switchSem.equals("1")){
            switchSemester.setChecked(false);
        } else {
            switchSemester.setChecked(true);
        }

        //instances to load data and send to spinners
        firebaseRefCourse = FirebaseDatabase.getInstance().getReference("courses").child(idUserLogged);
        firebaseRefYear = FirebaseDatabase.getInstance().getReference("years").child(idUserLogged);

        iFirebaseLoadDoneCourse = this;
        iFirebaseLoadDoneYears = this;

        buttonDisciplines.setOnClickListener( v-> {
                disciplineUpdate();
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


        backToPrevious.setOnClickListener( v ->  {
                backToMain();
        });

        return duplicateDiscipline;

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


    private void adapterConstructor() {

        layout = new LinearLayoutManager(getContext());
        adapter = new Adapter_Classes_Calendar(classes, getContext());
        recyclerClasses.setAdapter(adapter);
        recyclerClasses.setLayoutManager(layout);
        recyclerClasses.setHasFixedSize(true);

        adapter.setOnItemClickListener(new Adapter_Classes_Calendar.ClickListener() {

            @Override
            public void onItemClick(Adapter_Classes_Calendar adapter_disciplines, View v, int position) {

            }

            @Override
            public void onItemLongClick(Adapter_Classes_Calendar adapter_disciplines, View v, int position) {

            }
        });

    }


    private void disciplineUpdate() {

        String disciplineSaveSemester = "1";

        if (switchSemester.isChecked()) {
            disciplineSaveSemester = "2";
        }

        final Discipline disciplineDuplicate = new Discipline();

        disciplineDuplicate.setIdUser(idUserLogged);
        disciplineDuplicate.setDisciplineName(disciplineName.getText().toString());
        disciplineDuplicate.setAcronymDiscipline(acronymDiscipline.getText().toString());
        disciplineDuplicate.setDisciplineYearId(idYearSelected);
        disciplineDuplicate.setDisciplineYearName(nameYearSelected);
        disciplineDuplicate.setDisciplineSemester(disciplineSaveSemester);
        disciplineDuplicate.setIdUniversity(idUniversitySelected);
        disciplineDuplicate.setUniversityName(nameUniversitySelected);
        disciplineDuplicate.setIdCourse(idCourseSelected);
        disciplineDuplicate.setCourseName(nameCourseSelected);

        disciplineDuplicate.saveObject(disciplineToDuplicate);
        toastMsg("Discipline " + disciplineDuplicate.getDisciplineName() + " successfully saved");
        backToMain();

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
        recyclerClasses = view.findViewById(R.id.recyclerClasses);
    }

}
