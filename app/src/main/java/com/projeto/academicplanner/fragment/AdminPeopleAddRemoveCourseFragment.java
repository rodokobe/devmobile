package com.projeto.academicplanner.fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
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
import com.projeto.academicplanner.R;
import com.projeto.academicplanner.adapter.Adapter_AdminPeople_Courses;
import com.projeto.academicplanner.helper.ConfigFirebase;
import com.projeto.academicplanner.model.AdminPeople;
import com.projeto.academicplanner.model.Course;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.List;

public class AdminPeopleAddRemoveCourseFragment extends Fragment implements IFirebaseLoadDoneCourse {

    private TextView adminPeopleName, adminPeopleEmail, backToPrevious;
    private Button buttonAddIntoCourse;
    private AdminPeopleMainFragment adminPeopleMainFragmentF;
    private String idUserLogged, idUniversitySelected, nameUniversitySelected, idCourseSelected, nameCourseSelected;
    private SearchableSpinner spinnerCourse;
    private List<Course> courseRecycler = new ArrayList<>();

    private AdminPeople adminPeopleToAddCourse;
    private Course courseSelected;
    private DatabaseReference firebaseRefCourse;
    private IFirebaseLoadDoneCourse iFirebaseLoadDoneCourse;

    //recycler view variables
    private RecyclerView recyclerAdminCourses;
    private RecyclerView.LayoutManager layout;
    private Adapter_AdminPeople_Courses adapter;

    public AdminPeopleAddRemoveCourseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adminPeopleToAddCourse = (AdminPeople) getArguments().getSerializable("AdminPeopleToAddCourse");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View addRemoveCourses = inflater.inflate(R.layout.fragment_admin_people_add_remove_course, container, false);

        //recovery logged user ID
        idUserLogged = ConfigFirebase.getUserId();

        initializingComponents(addRemoveCourses);

        String fullName = adminPeopleToAddCourse.getAdminPeopleFirstName() + " " + adminPeopleToAddCourse.getAdminPeopleLastName();

        adminPeopleName.setText(fullName);
        adminPeopleEmail.setText(adminPeopleToAddCourse.getAdminPeopleEmail());


        /**
         * instances to load data and send to spinners
         */
        iFirebaseLoadDoneCourse = this;
        firebaseRefCourse = FirebaseDatabase.getInstance().getReference("courses").child(idUserLogged);


        /**
         * Setting action on buttons
         */

        backToPrevious.setOnClickListener( view -> {
                backToMain();
        });

        buttonAddIntoCourse.setOnClickListener( view -> {

            AdminPeople adminPeopleOnCourse = new AdminPeople();
            adminPeopleOnCourse.saveAdminPeopleInCourse(courseSelected, adminPeopleToAddCourse);
            adminPeopleOnCourse.saveCourseInAdminPeople(courseSelected, adminPeopleToAddCourse);

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

        DatabaseReference adminPeopleRef = FirebaseDatabase.getInstance().getReference("adminpeople").child(idUserLogged);

        adminPeopleRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                courseRecycler.clear();

                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    AdminPeople adminpeople = snap.getValue(AdminPeople.class);
                    String adminPeopleIdToShow = adminpeople.getIdAdminPeople();

                    DatabaseReference courseRef = adminPeopleRef.child(adminPeopleIdToShow).child("courses");

                    courseRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            for (DataSnapshot courseSnapshot : dataSnapshot.getChildren()) {

                                Course course = courseSnapshot.getValue(Course.class);

                                try {

                                    if (adminPeopleIdToShow.equals(adminPeopleToAddCourse.getIdAdminPeople())) {
                                        courseRecycler.add(course);

                                    } else {

                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }

                            //recycler view configuration
                            layout = new LinearLayoutManager(getContext());
                            adapter = new Adapter_AdminPeople_Courses(courseRecycler, getContext());
                            recyclerAdminCourses.setAdapter(adapter);
                            recyclerAdminCourses.setLayoutManager(layout);
                            recyclerAdminCourses.setHasFixedSize(true);

                            adapter.setOnItemClickListener( (adapter_adminPeople_courses, v, position) ->  {

                                final ImageView imageDelete = v.findViewById(R.id.imageDelete);

                                final Course objectToAction = courseRecycler.get(position);

                                imageDelete.setOnClickListener( view -> {
                                    courseDelete(objectToAction);
                                });

                            });

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return addRemoveCourses;

    }

    //charge the spinner Course values
    @Override
    public void onFireBaseLoadCourseSuccess(final List<Course> coursesList) {

        final List<String> university_name = new ArrayList<>();
        for (Course course : coursesList)

            university_name.add(course.getUniversityName() + "\n" + course.getCourseName());

        //Create adapter
        ArrayAdapter<String> adapterUniversity = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, university_name);
        spinnerCourse.setAdapter(adapterUniversity);

        spinnerCourse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                idUniversitySelected = coursesList.get(position).getIdUniversity();
                nameUniversitySelected = coursesList.get(position).getUniversityName();
                idCourseSelected = coursesList.get(position).getIdCourse();
                nameCourseSelected = coursesList.get(position).getCourseName();

                courseSelected = coursesList.get(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    @Override
    public void onFireBaseLoadCourseFailed(String message) {
    }


    private void courseDelete(final Course selectedToRemove) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        String name = selectedToRemove.getCourseName();
        String msg = "Are you sure, you want to delete the course " + name + "?";

        builder.setTitle(msg);
        builder.setPositiveButton(android.R.string.yes, (dialog, id) -> {

            selectedToRemove.deleteCourseIntoAdminPeople(selectedToRemove, adminPeopleToAddCourse);
            adminPeopleToAddCourse.deleteAdminPeopleIntoOneCourse(adminPeopleToAddCourse, selectedToRemove);
            toastMsg("Discipline " + name + " has been removed!");
            adapter.notifyDataSetChanged();
            dialog.dismiss();
        });

        builder.setNegativeButton(android.R.string.no, (dialog, id) -> {
            //method to cancel the delete operation
            toastMsg("Request CANCELED");
            dialog.dismiss();
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }




    public void backToMain() {

        adminPeopleMainFragmentF = new AdminPeopleMainFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameSettingsMain, adminPeopleMainFragmentF);
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
        adminPeopleName = view.findViewById(R.id.adminPeopleName);
        adminPeopleEmail = view.findViewById(R.id.adminPeopleEmail);
        spinnerCourse = view.findViewById(R.id.spinnerCourse);
        buttonAddIntoCourse = view.findViewById(R.id.buttonAddIntoCourse);
        recyclerAdminCourses = view.findViewById(R.id.recyclerAdminCourses);
    }

}
