package fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.projeto.academicplanner.R;

public class AddEditMainFragment extends Fragment {

    private TextView universityCrudMain, coursesCrudMain, disciplinesCrudMain, eventsCrudMain, studentsCrudMain, adminPeopleCrudMain;

    private UniversityMainFragment universityMain;
    private CourseMainFragment courseMain;
    private DisciplineMainFragment disciplineMain;
    private EventMainFragment eventMain;
    private StudentMainFragment studentMain;
    private AdminPeopleMainFragment adminPeopleMain;

    private static final String TAG = "AddEditParametersActivity";

    public AddEditMainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        final View addEditMain = inflater.inflate(R.layout.fragment_add_edit_main, container, false);

        universityCrudMain = addEditMain.findViewById(R.id.universityCrudMain);
        coursesCrudMain = addEditMain.findViewById(R.id.coursesCrudMain);
        disciplinesCrudMain = addEditMain.findViewById(R.id.disciplinesCrudMain);
        eventsCrudMain = addEditMain.findViewById(R.id.eventsCrudMain);
        studentsCrudMain = addEditMain.findViewById(R.id.studentsCrudMain);
        adminPeopleCrudMain = addEditMain.findViewById(R.id.adminPeopleCrudMain);


        universityCrudMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { goToUniversityMain(v); }
        });

        coursesCrudMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToCourseMain(v);
            }
        });

        disciplinesCrudMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToDisciplineMain(v);
            }
        });

        eventsCrudMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToEventMain(v);
            }
        });

        studentsCrudMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { goToStudentMain(v);}
        });

        adminPeopleCrudMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { goToAdminPeopleMain(v);}
        });

        return addEditMain;
    }

    public void goToUniversityMain(View view) {

        universityMain = new UniversityMainFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameAddEditParameters, universityMain);
        transaction.commit();

    }

    public void goToCourseMain(View view) {

        courseMain = new CourseMainFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameAddEditParameters, courseMain);
        transaction.commit();

    }

    public void goToDisciplineMain(View view) {

        disciplineMain = new DisciplineMainFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameAddEditParameters, disciplineMain);
        transaction.commit();

    }

    public void goToEventMain(View view) {

        eventMain = new EventMainFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameAddEditParameters, eventMain);
        transaction.commit();

    }

    public void goToStudentMain(View view) {

        studentMain = new StudentMainFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameAddEditParameters, studentMain);
        transaction.commit();

    }


    public void goToAdminPeopleMain(View view) {

        adminPeopleMain = new AdminPeopleMainFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameAddEditParameters, adminPeopleMain);
        transaction.commit();

    }


}
