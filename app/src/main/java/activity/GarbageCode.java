package activity;

public class GarbageCode {

    /*

altera layout dos objetos

    private void changeLayout() {

        //setting new parameter values to layouts
        heightR = LinearLayout.LayoutParams.WRAP_CONTENT;
        heightRecycler = 840;
        LinearLayout.LayoutParams retractR = new LinearLayout.LayoutParams(width, heightR);
        LinearLayout.LayoutParams retractRecycle = new LinearLayout.LayoutParams(width, heightRecycler);

        //setting new parameters to linear layouts and recycler view
        linerLayoutAddGroup.setVisibility(View.VISIBLE);
        recylcerUniversities.setLayoutParams(retractRecycle);
        linearLayoutRecycler.setLayoutParams(retractR);

    }




    Classe com o FAB original

    public class AddEditParametersActivityBackup extends AppCompatActivity implements View.OnClickListener {

    //create objects as 'x'Fragment classes for display fragments on main Fragment
    private FloatingActionButton addMainFab, addAdmPeopleFab, addStudentFab, addEventTypeFab, addDisciplineFab, addCourseFab, addUniversityFab, backSysPrefHomeFab;
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
    private AddAdmPeopleFragment addAdmPeopleF;
    private LinearLayout textLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addedit_parameters);

        montaFab();

    }

    private void montaFab() {

        addMainFab = findViewById(R.id.addMainFab);
        addAdmPeopleFab = findViewById(R.id.addAdmPeoplenFab);
        addStudentFab = findViewById(R.id.addStudentFab);
        addEventTypeFab = findViewById(R.id.addEventTypeFab);
        addDisciplineFab = findViewById(R.id.addDisciplineFab);
        addCourseFab = findViewById(R.id.addCourseFab);
        addUniversityFab = findViewById(R.id.addUniversityFab);
        backSysPrefHomeFab = findViewById(R.id.backSysPrefHomeFab);
        textLayout = findViewById(R.id.textLayout);

        addAdmPeopleFab.setAlpha(0f);
        addStudentFab.setAlpha(0f);
        addEventTypeFab.setAlpha(0f);
        addDisciplineFab.setAlpha(0f);
        addCourseFab.setAlpha(0f);
        addUniversityFab.setAlpha(0f);
        backSysPrefHomeFab.setAlpha(0f);
        textLayout.setVisibility(View.GONE);

        addAdmPeopleFab.setTranslationY(translationY);
        addStudentFab.setTranslationY(translationY);
        addEventTypeFab.setTranslationY(translationY);
        addDisciplineFab.setTranslationY(translationY);
        addCourseFab.setTranslationY(translationY);
        addUniversityFab.setTranslationY(translationY);
        backSysPrefHomeFab.setTranslationY(translationY);

        addMainFab.setOnClickListener(this);
        addAdmPeopleFab.setOnClickListener(this);
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

        addAdmPeropleFab.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
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

        addAdmPeopleFab.animate().translationY(translationY).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
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
            case R.id.addAdmPeopleFab:
                addAdmPeople(v);
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

    public void addAdmPeople(View view) {

        addAdmPeopleF = new AddAdmPeopleFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameAddEditParameters, addAdmPeopleF);
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





    * */

}
