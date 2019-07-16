package com.projeto.academicplanner.fragment;


import android.app.AlertDialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import com.projeto.academicplanner.adapter.Adapter_Disciplines;
import com.projeto.academicplanner.helper.ConfigFirebase;
import com.projeto.academicplanner.model.Discipline;

import java.util.ArrayList;
import java.util.List;

public class EventMainFragment extends Fragment {


    //general variables
    private Button buttonDisciplines;
    private TextView backToAddEditMain;
    private String idUserLoged;
    private List<Discipline> disciplines = new ArrayList<>();
    private AddDisciplineFragment addDisciplineFragment;
    private AddEditMainFragment fragmentMain;

    //recycler view variables
    private RecyclerView recylcerDisciplines;
    private RecyclerView.LayoutManager layout;
    private Adapter_Disciplines adapter;

    private static final String TAG = "AddEditParametersActivity";


    public EventMainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View mainDisciplines = inflater.inflate(R.layout.fragment_discipline_main, container, false);

        //start configurations
        buttonDisciplines = mainDisciplines.findViewById(R.id.buttonDisciplines);
        backToAddEditMain = mainDisciplines.findViewById(R.id.backToAddEditMain);
        recylcerDisciplines = mainDisciplines.findViewById(R.id.recylcerDisciplines);

        //recovery loged user ID
        idUserLoged = ConfigFirebase.getUserId();

        //call methods
        adapterConstructor();

        //create object and fill recyclerViewCourses
        Discipline discipline = new Discipline();
        discipline.recovery(idUserLoged, disciplines, adapter);

        buttonDisciplines.setOnClickListener(new View.OnClickListener() {
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

        return mainDisciplines;

    }

    private void adapterConstructor() {

        //recycler view configuration
        layout = new LinearLayoutManager(getContext());
        adapter = new Adapter_Disciplines(disciplines, getContext());
        recylcerDisciplines.setAdapter(adapter);
        recylcerDisciplines.setLayoutManager(layout);
        recylcerDisciplines.setHasFixedSize(true);
        recylcerDisciplines.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayout.VERTICAL));

        adapter.setOnItemClickListener(new Adapter_Disciplines.ClickListener() {
            @Override
            public void onItemClick(Adapter_Disciplines adapter_disciplines, View v, final int position) {

                final ImageView imageEdit = v.findViewById(R.id.imageEdit);
                final ImageView imageDelete = v.findViewById(R.id.imageDelete);

                final Discipline objectToAction = disciplines.get(position);

                imageDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        disciplineDelete(objectToAction);

                    }
                });

                imageEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        disciplineUpdate(objectToAction);

                    }
                });
            }
        });

    }

    private void disciplineUpdate(final Discipline selectedToUpdate) {

        final Discipline disciplineUpdate = new Discipline();

        final AlertDialog.Builder updateDialog = new AlertDialog.Builder(getContext());

        final View updateDialogView = getLayoutInflater().inflate(R.layout.dialog_model, null);

        final EditText dialogUname = updateDialogView.findViewById(R.id.dialogName);
        final EditText dialogUacron = updateDialogView.findViewById(R.id.dialogAcronym);
        final Button dialogUbutton = updateDialogView.findViewById(R.id.buttonDialog);
        dialogUbutton.setText("UPDATE");

        dialogUname.setText(selectedToUpdate.getDisciplineName());
        dialogUacron.setText(selectedToUpdate.getAcronymDiscipline());


        updateDialog.setView(updateDialogView);
        final AlertDialog updateDialogAlert = updateDialog.create();
        updateDialogAlert.show();

        dialogUbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String disciplineDialogName = dialogUname.getText().toString();
                String disciplineDialogAcronym = dialogUacron.getText().toString();

                disciplineUpdate.setIdUser(idUserLoged);
                disciplineUpdate.setIdDiscipline(selectedToUpdate.getIdDiscipline());
                disciplineUpdate.setDisciplineName(disciplineDialogName);
                disciplineUpdate.setAcronymDiscipline(disciplineDialogAcronym);
                disciplineUpdate.setDisciplineYear(selectedToUpdate.getDisciplineYear());
                disciplineUpdate.setDisciplineSemester(selectedToUpdate.getDisciplineSemester());
                disciplineUpdate.setIdUniversity(selectedToUpdate.getIdUniversity());
                disciplineUpdate.setUniversityName(selectedToUpdate.getUniversityName());
                disciplineUpdate.setIdCourse(selectedToUpdate.getIdCourse());
                disciplineUpdate.setCourseName(selectedToUpdate.getCourseName());

                disciplineUpdate.update(disciplineUpdate);
                toastMsg("Discipline " + disciplineUpdate.getDisciplineName() + " successfully update");
                adapter.notifyDataSetChanged();
                updateDialogAlert.cancel();

            }
        });
    }

    private void disciplineDelete(final Discipline selectedToRemove) {

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
                toastMsg("Discipline " + selectedToRemove.getDisciplineName() + " has been removed!");
                adapter.notifyDataSetChanged();
                deleteDialogAlert.cancel();
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

    public void goToNewFragment() {
        addDisciplineFragment = new AddDisciplineFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameAddEditUserProfile, addDisciplineFragment);
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
