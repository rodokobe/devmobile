package com.projeto.academicplanner.fragment;


import android.app.AlertDialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.projeto.academicplanner.R;
import com.projeto.academicplanner.adapter.Adapter_Disciplines;
import com.projeto.academicplanner.helper.ConfigFirebase;
import com.projeto.academicplanner.model.Discipline;

import java.util.ArrayList;
import java.util.List;

public class DisciplineMainFragment extends Fragment {


    //general variables
    private Button buttonDisciplines;
    private TextView backToPrevious;
    private String idUserLogged;
    private List<Discipline> disciplines = new ArrayList<>();
    private DisciplineAddFragment addDisciplineFragment;
    private DisciplineUpdateFragment updateDisciplineFragment;
    private DisciplineDuplicateFragment duplicateDisciplineFragment;
    private SettingsFragment settingsFragment;

    //recycler view variables
    private RecyclerView recyclerDisciplines;
    private RecyclerView.LayoutManager layout;
    private Adapter_Disciplines adapter;

    public DisciplineMainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View mainDisciplines = inflater.inflate(R.layout.fragment_discipline_main, container, false);

        //start configurations
        initializingComponents(mainDisciplines);

        //recovery loged user ID
        idUserLogged = ConfigFirebase.getUserId();

        //call methods
        adapterConstructor();

        //create object and fill recyclerViewCourses
        Discipline discipline = new Discipline();
        discipline.recovery(idUserLogged, disciplines, adapter);

        buttonDisciplines.setOnClickListener( v->  {
                goToNewFragment();
        });

        backToPrevious.setOnClickListener( v -> {
                goBackToMain();
        });

        return mainDisciplines;

    }

    private void adapterConstructor() {

        //recycler view configuration
        layout = new LinearLayoutManager(getContext());
        adapter = new Adapter_Disciplines(disciplines, getContext());
        recyclerDisciplines.setAdapter(adapter);
        recyclerDisciplines.setLayoutManager(layout);
        recyclerDisciplines.setHasFixedSize(true);

        adapter.setOnItemClickListener( (adapter_disciplines, v, position) ->  {

                final TextView disciplineDuplicate = v.findViewById(R.id.disciplineDuplicate);
                final ImageView imageEdit = v.findViewById(R.id.imageEdit);
                final ImageView imageDelete = v.findViewById(R.id.imageDelete);

                final Discipline objectToAction = disciplines.get(position);

                disciplineDuplicate.setOnClickListener( view -> {
                        goToDuplicateFragment(objectToAction);
                });

                imageDelete.setOnClickListener( view -> {
                        disciplineDelete(objectToAction);
                });

                imageEdit.setOnClickListener( view -> {
                        goToUpdateFragment(objectToAction);
                });
        });

    }

    private void disciplineDelete(final Discipline selectedToRemove) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        String name = selectedToRemove.getDisciplineName();
        String msg = "Are you sure, you want to delete the discipline " + name + "?";

        builder.setTitle(msg);
        builder.setPositiveButton(android.R.string.yes, (dialog, id) -> {

            selectedToRemove.delete();
            toastMsgLong("Discipline " + name + " has been removed!");
            adapter.notifyDataSetChanged();
            dialog.dismiss();

            //call methods
            adapterConstructor();

            //create object and fill recyclerViewCourses
            Discipline discipline = new Discipline();
            discipline.recovery(idUserLogged, disciplines, adapter);

        });

        builder.setNegativeButton(android.R.string.no, (dialog, id) -> {
            //method to cancel the delete operation
            toastMsgLong("Request CANCELED");
            dialog.dismiss();
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void goToDuplicateFragment(Discipline objectToAction) {
        duplicateDisciplineFragment = new DisciplineDuplicateFragment();

        Bundle dataToDuplicate = new Bundle();
        dataToDuplicate.putSerializable("DisciplineToDuplicate", objectToAction);

        duplicateDisciplineFragment.setArguments(dataToDuplicate);

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameSettingsMain, duplicateDisciplineFragment);
        transaction.commit();
    }

    public void goToUpdateFragment(Discipline objectToAction) {

        updateDisciplineFragment = new DisciplineUpdateFragment();

        Bundle dataToUpdate = new Bundle();
        dataToUpdate.putSerializable("DisciplineToUpdate", objectToAction);

        updateDisciplineFragment.setArguments(dataToUpdate);

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameSettingsMain, updateDisciplineFragment);
        transaction.commit();
    }

    public void goToNewFragment() {
        addDisciplineFragment = new DisciplineAddFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameSettingsMain, addDisciplineFragment);
        transaction.commit();
    }

    public void goBackToMain() {

        settingsFragment = new SettingsFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameSettingsMain, settingsFragment);
        transaction.commit();

    }

    public void toastMsgLong(String text) {

        //show toast parameters
        Toast toastError = Toast.makeText(getContext(), text, Toast.LENGTH_LONG);
        toastError.setGravity(Gravity.CENTER, 0, 800);
        toastError.show();

    }

    private void initializingComponents(View view){
        buttonDisciplines = view.findViewById(R.id.buttonDisciplines);
        backToPrevious = view.findViewById(R.id.backToPrevious);
        recyclerDisciplines = view.findViewById(R.id.recylcerDisciplines);
    }
}
