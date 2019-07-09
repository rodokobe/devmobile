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
import com.projeto.academicplanner.R;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.List;

import Interface.IFirebaseLoadDoneCourse;
import helper.ConfigFirebase;
import model.Course;
import model.Discipline;

public class AddDisciplineFragment extends Fragment implements IFirebaseLoadDoneCourse {

    private EditText disciplineName, acronymDiscipline, disciplineYear;
    private TextView backText;
    private SearchableSpinner spinnerUniversity;
    private Switch switchSemester;
    private Button buttonDisciplines;
    private String idUserLoged, idUniversitySelected, nameUniversitySelected, idCourseSelected, nameCourseSelected;

    private DatabaseReference firebaseRefCourse;
    private IFirebaseLoadDoneCourse iFirebaseLoadDoneCourse;
    private DisciplineMainFragment disciplineMainFragmentF;

    public AddDisciplineFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View addDiscipline = inflater.inflate(R.layout.fragment_add_discipline, container, false);

        //configurações iniciais
        disciplineName = addDiscipline.findViewById(R.id.disciplineName);
        acronymDiscipline = addDiscipline.findViewById(R.id.acronymDiscipline);
        disciplineYear = addDiscipline.findViewById(R.id.disciplineYear);
        backText = addDiscipline.findViewById(R.id.backText);
        spinnerUniversity = addDiscipline.findViewById(R.id.spinnerUniversity);
        switchSemester = addDiscipline.findViewById(R.id.switchSemester);
        buttonDisciplines = addDiscipline.findViewById(R.id.buttonDisciplines);

        idUserLoged = ConfigFirebase.getUserId();

        //instances to load data and send to spinners
        firebaseRefCourse = FirebaseDatabase.getInstance().getReference("courses").child(idUserLoged);

        iFirebaseLoadDoneCourse = this;

        buttonDisciplines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String disciplineSaveName = disciplineName.getText().toString();
                String disciplineSaveAcronym = acronymDiscipline.getText().toString();
                String disciplineSaveYear = disciplineYear.getText().toString();
                String disciplineSaveSemester = "1º Semester";

                if (switchSemester.isChecked()) {
                    disciplineSaveSemester = "2º Semester";
                }


                disciplineAddNew(disciplineSaveName, disciplineSaveAcronym, disciplineSaveYear, disciplineSaveSemester,
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
                    iFirebaseLoadDoneCourse.onFireBaseLoadSuccess(courses);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                iFirebaseLoadDoneCourse.onFireBaseLoadFailed(databaseError.getMessage());
            }
        });


        backText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToMain();
            }
        });

        return addDiscipline;

    }


    @Override
    public void onFireBaseLoadSuccess(final List<Course> coursesList) {

        //universitySpinner = universitiesList;
        final List<String> university_name = new ArrayList<>();
        for (Course course : coursesList)

            university_name.add("University: " + course.getUniversityName() + "\nCourse: " + course.getCourseName());

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
    public void onFireBaseLoadFailed(String message) {
    }


    private void disciplineAddNew(String disciplineSaveName, String disciplineSaveAcronym, String disciplineSaveYear, String disciplineSaveSemester,
                                  String idUniversitySelected, String nameUniversitySelected, String idCourseSelected, String nameCourseSelected) {

        if (!disciplineSaveName.isEmpty()) {
            if (!disciplineSaveAcronym.isEmpty()) {

                Discipline discipline = new Discipline();
                discipline.setIdUser(idUserLoged);
                discipline.setDisciplineName(disciplineSaveName);
                discipline.setAcronymDiscipline(disciplineSaveAcronym);
                discipline.setDisciplineYear(disciplineSaveYear);
                discipline.setDisciplineSemester(disciplineSaveSemester);
                discipline.setIdUniversity(idUniversitySelected);
                discipline.setUniversityName(nameUniversitySelected);
                discipline.setIdCourse(idCourseSelected);
                discipline.setCourseName(nameCourseSelected);

                discipline.saveDisciplineData();

                toastMsg("Discipline " + disciplineSaveName + " added to " + nameCourseSelected + " course!");
                backToMain();

            } else {
                toastMsg("Enter an acronym to Discipline");
            }
        } else {
            toastMsg("Enter an Discipline name");
        }

    }


    public void toastMsg(String text) {

        //show toast parameters
        Toast toastError = Toast.makeText(getContext(), text, Toast.LENGTH_LONG);
        toastError.setGravity(Gravity.CENTER, 0, 800);
        toastError.show();

    }

    public void backToMain() {
        disciplineMainFragmentF = new DisciplineMainFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameAddEditParameters, disciplineMainFragmentF);
        transaction.commit();
    }




}
