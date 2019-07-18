package com.projeto.academicplanner.fragment;


import android.app.AlertDialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.projeto.academicplanner.R;
import com.projeto.academicplanner.adapter.Adapter_Students;
import com.projeto.academicplanner.helper.ConfigFirebase;
import com.projeto.academicplanner.model.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentMainFragment extends Fragment {


    //general variables
    private Button buttonStudents;
    private TextView backToAddEditMain;
    private String idUserLoged;
    private List<Student> students = new ArrayList<>();
    private AddStudentFragment addStudentFragmentF;
    private UpdateStudentFragment updateStudentFragmentF;
    private AddEditMainFragment fragmentMain;

    //recycler view variables
    private RecyclerView recylcerStudents;
    private RecyclerView.LayoutManager layout;
    private Adapter_Students adapter;

    private static final String TAG = "AddEditParametersActivity";


    public StudentMainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View mainStudents = inflater.inflate(R.layout.fragment_student_main, container, false);

        //start configurations
        buttonStudents = mainStudents.findViewById(R.id.buttonStudents);
        backToAddEditMain = mainStudents.findViewById(R.id.backToAddEditMain);
        recylcerStudents = mainStudents.findViewById(R.id.recylcerStudents);

        //recovery loged user ID
        idUserLoged = ConfigFirebase.getUserId();

        //call methods
        adapterConstructor();

        //create object and fill recyclerViewCourses
        Student student = new Student();
        student.recovery(idUserLoged, students, adapter);

        buttonStudents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToNewFragment();
            }
        });

        backToAddEditMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBackToMain();
            }
        });

        return mainStudents;

    }

    private void adapterConstructor() {

        //recycler view configuration
        layout = new LinearLayoutManager(getContext());
        adapter = new Adapter_Students(students, getContext());
        recylcerStudents.setAdapter(adapter);
        recylcerStudents.setLayoutManager(layout);
        recylcerStudents.setHasFixedSize(true);
        recylcerStudents.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayout.VERTICAL));

        adapter.setOnItemClickListener(new Adapter_Students.ClickListener() {
            @Override
            public void onItemClick(Adapter_Students adapter_students, View v, final int position) {

                final ImageView imageEdit = v.findViewById(R.id.imageEdit);
                final ImageView imageDelete = v.findViewById(R.id.imageDelete);

                final Student objectToAction = students.get(position);

                imageDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        studentDelete(objectToAction);

                    }
                });

                imageEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        goToUpdateFragment(objectToAction);

                    }
                });
            }
        });

    }

    private void studentDelete(final Student selectedToRemove) {

        final AlertDialog.Builder deleteDialog = new AlertDialog.Builder(getContext());
        final View deleteDialogView = getLayoutInflater().inflate(R.layout.dialog_model_delete_request, null);

        final Button buttonNoDelete = deleteDialogView.findViewById(R.id.buttonNoDelete);
        final Button buttonDelete = deleteDialogView.findViewById(R.id.buttonDelete);

        //method to create and show AlertDialog to DELETE
        deleteDialog.setView(deleteDialogView);
        final AlertDialog deleteDialogAlert = deleteDialog.create();
        deleteDialogAlert.show();

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //method to remove the selected object
                selectedToRemove.delete();
                toastMsg("Student " + selectedToRemove.getStudentFirstName() + " has been removed!");
                adapter.notifyDataSetChanged();
                deleteDialogAlert.cancel();

                //call methods
                adapterConstructor();

                //create object and fill recyclerViewCourses
                Student student = new Student();
                student.recovery(idUserLoged, students, adapter);
            }
        });

        buttonNoDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //method to cancel the delete operation
                toastMsg("Request CANCELED");
                deleteDialogAlert.cancel();
            }
        });
    }

    public void goToUpdateFragment(Student objectToAction) {
        updateStudentFragmentF = new UpdateStudentFragment();
        Bundle dataToUpdate = new Bundle();
        dataToUpdate.putSerializable("StudentToUpdate", objectToAction);

        updateStudentFragmentF.setArguments(dataToUpdate);

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameAddEditUserProfile, updateStudentFragmentF);
        transaction.commit();
    }

    public void goToNewFragment() {
        addStudentFragmentF = new AddStudentFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameAddEditUserProfile, addStudentFragmentF);
        transaction.commit();
    }

    public void goBackToMain() {

        fragmentMain = new AddEditMainFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameAddEditUserProfile, fragmentMain);
        transaction.commit();

    }

    public void toastMsg(String text) {

        //show toast parameters
        Toast toastError = Toast.makeText(getContext(), text, Toast.LENGTH_LONG);
        toastError.setGravity(Gravity.CENTER, 0, 800);
        toastError.show();

    }
}
