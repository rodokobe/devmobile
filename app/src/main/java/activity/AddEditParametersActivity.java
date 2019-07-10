package activity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.projeto.academicplanner.R;

import fragment.AddAdmPersonFragment;
import fragment.AddEditMainFragment;
import fragment.AddEventTypeFragment;
import fragment.AddStudentFragment;
import fragment.CourseMainFragment;
import fragment.DisciplineMainFragment;
import fragment.UniversityMainFragment;

public class AddEditParametersActivity extends AppCompatActivity implements View.OnClickListener {

    //create objects as 'x'Fragment classes for display fragments on main Fragment
    private FloatingActionButton addMainFab, addAdmPersonFab, addStudentFab, addEventTypeFab, addDisciplineFab, addCourseFab, addUniversityFab, backSysPrefHomeFab;
    private float translationY = 100f;
    private Boolean isMenuOpen = false;

    OvershootInterpolator interpolator = new OvershootInterpolator();

    private static final String TAG = "AddEditParametersActivity";

    private AddEditMainFragment addEditMainF;
    private AddEditMainFragment backSysPrefHomeF;
    private UniversityMainFragment addUniversityF;
    private CourseMainFragment addCourseF;
    private DisciplineMainFragment addDisciplineF;
    private AddEventTypeFragment addEventTypeF;
    private AddStudentFragment addStudentF;
    private AddAdmPersonFragment addAdmPersonF;
    private LinearLayout textLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addedit_parameters);

        montaFab();

    }

    private void montaFab() {

        addMainFab = findViewById(R.id.addMainFab);
        addAdmPersonFab = findViewById(R.id.addAdmPersonFab);
        addStudentFab = findViewById(R.id.addStudentFab);
        addEventTypeFab = findViewById(R.id.addEventTypeFab);
        addDisciplineFab = findViewById(R.id.addDisciplineFab);
        addCourseFab = findViewById(R.id.addCourseFab);
        addUniversityFab = findViewById(R.id.addUniversityFab);
        backSysPrefHomeFab = findViewById(R.id.backSysPrefHomeFab);
        textLayout = findViewById(R.id.textLayout);

        addAdmPersonFab.setAlpha(0f);
        addStudentFab.setAlpha(0f);
        addEventTypeFab.setAlpha(0f);
        addDisciplineFab.setAlpha(0f);
        addCourseFab.setAlpha(0f);
        addUniversityFab.setAlpha(0f);
        backSysPrefHomeFab.setAlpha(0f);
        textLayout.setVisibility(View.GONE);

        addAdmPersonFab.setTranslationY(translationY);
        addStudentFab.setTranslationY(translationY);
        addEventTypeFab.setTranslationY(translationY);
        addDisciplineFab.setTranslationY(translationY);
        addCourseFab.setTranslationY(translationY);
        addUniversityFab.setTranslationY(translationY);
        backSysPrefHomeFab.setTranslationY(translationY);

        addMainFab.setOnClickListener(this);
        addAdmPersonFab.setOnClickListener(this);
        addStudentFab.setOnClickListener(this);
        addEventTypeFab.setOnClickListener(this);
        addDisciplineFab.setOnClickListener(this);
        addCourseFab.setOnClickListener(this);
        addUniversityFab.setOnClickListener(this);
        backSysPrefHomeFab.setOnClickListener(this);

    }

    private void openMenu() {

        isMenuOpen = !isMenuOpen;

        addMainFab.animate().setInterpolator(interpolator).rotation(45f).setDuration(300).start();

        addAdmPersonFab.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
        addStudentFab.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
        addEventTypeFab.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
        addDisciplineFab.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
        addCourseFab.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
        addUniversityFab.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
        backSysPrefHomeFab.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();

        textLayout.setVisibility(View.VISIBLE);

    }

    private void closeMenu() {

        isMenuOpen = !isMenuOpen;

        addMainFab.animate().setInterpolator(interpolator).rotation(0f).setDuration(300).start();

        addAdmPersonFab.animate().translationY(translationY).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
        addStudentFab.animate().translationY(translationY).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
        addEventTypeFab.animate().translationY(translationY).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
        addDisciplineFab.animate().translationY(translationY).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
        addCourseFab.animate().translationY(translationY).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
        addUniversityFab.animate().translationY(translationY).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
        backSysPrefHomeFab.animate().translationY(translationY).alpha(0f).setInterpolator(interpolator).setDuration(300).start();

        textLayout.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.addMainFab:
                if (isMenuOpen) {
                    closeMenu();
                } else {
                    openMenu();
                }
                break;
            case R.id.addAdmPersonFab:
                addAdmPerson(v);
                closeMenu();
                break;
            case R.id.addStudentFab:
                addStudent(v);
                closeMenu();
                break;
            case R.id.addEventTypeFab:
                addEventType(v);
                closeMenu();
                break;
            case R.id.addDisciplineFab:
                addDiscipline(v);
                closeMenu();
                break;
            case R.id.addCourseFab:
                addCourse(v);
                closeMenu();
                break;
            case R.id.addUniversityFab:
                addUniversity(v);
                closeMenu();
                break;
            case R.id.backSysPrefHomeFab:
                backSysPrefHome(v);
                closeMenu();
                break;

        }

    }

    public void addAdmPerson(View view) {

        addAdmPersonF = new AddAdmPersonFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameAddEditParameters, addAdmPersonF);
        transaction.commit();

    }

    public void addStudent(View view) {

        addStudentF = new AddStudentFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameAddEditParameters, addStudentF);
        transaction.commit();

    }

    public void addEventType(View view) {

        addEventTypeF = new AddEventTypeFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameAddEditParameters, addEventTypeF);
        transaction.commit();

    }

    public void addDiscipline(View view) {

        addDisciplineF = new DisciplineMainFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameAddEditParameters, addDisciplineF);
        transaction.commit();

    }

    public void addCourse(View view) {

        addCourseF = new CourseMainFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameAddEditParameters, addCourseF);
        transaction.commit();

    }

    public void addUniversity(View view) {

        addUniversityF = new UniversityMainFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameAddEditParameters, addUniversityF);
        transaction.commit();

    }

    public void backSysPrefHome(View view) {

        backSysPrefHomeF = new AddEditMainFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameAddEditParameters, backSysPrefHomeF);
        transaction.commit();

    }

}
