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
import com.projeto.academicplanner.adapter.Adapter_Universities;
import com.projeto.academicplanner.helper.ConfigFirebase;
import com.projeto.academicplanner.model.University;

import java.util.ArrayList;
import java.util.List;

public class UniversityMainFragment extends Fragment {

    //general variables
    private Button buttonUniversity;
    private TextView backToAddEditMain;
    private String idUserLoged;
    private List<University> universities = new ArrayList<>();
    private AddUniversityFragment addUniversityFragmentF;
    private AddEditMainFragment fragmentMain;

    //recycler view variables
    private RecyclerView recylcerUniversities;
    private RecyclerView.LayoutManager layout;
    private Adapter_Universities adapter;

    private static final String TAG = "AddEditParametersActivity";


    public UniversityMainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View mainUniversity = inflater.inflate(R.layout.fragment_university_main, container, false);

        //start configurations
        buttonUniversity = mainUniversity.findViewById(R.id.buttonUniversity);
        backToAddEditMain = mainUniversity.findViewById(R.id.backToAddEditMain);
        recylcerUniversities = mainUniversity.findViewById(R.id.recylcerUniversities);

        //recovery loged user ID
        idUserLoged = ConfigFirebase.getUserId();

        //call methods
        adapterConstructor();

        //create object and fill recyclerViewUniversities
        University university = new University();
        university.recoveryUniversities(idUserLoged, universities, adapter);

        buttonUniversity.setOnClickListener(new View.OnClickListener() {
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


        return mainUniversity;

    }

    private void adapterConstructor() {

        //recycler view configuration
        layout = new LinearLayoutManager(getContext());
        adapter = new Adapter_Universities(universities, getContext());
        recylcerUniversities.setAdapter(adapter);
        recylcerUniversities.setLayoutManager(layout);
        recylcerUniversities.setHasFixedSize(true);
        recylcerUniversities.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayout.VERTICAL));

        adapter.setOnItemClickListener(new Adapter_Universities.ClickListener() {
            @Override
            public void onItemClick(Adapter_Universities adapter_universities, View v, final int position) {

                final ImageView imageEdit = v.findViewById(R.id.imageEdit);
                final ImageView imageDelete = v.findViewById(R.id.imageDelete);

                final University objectToAction = universities.get(position);

                imageDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        universityDelete(objectToAction);

                    }
                });

                imageEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        universityUpdate(objectToAction);

                    }
                });
            }
        });

    }

    private void universityUpdate(final University selectedToUpdate) {

        //method to update the selected object

        final University universityUpdate = new University();

        final AlertDialog.Builder updateDialog = new AlertDialog.Builder(getContext());
        final View updateDialogView = getLayoutInflater().inflate(R.layout.dialog_model, null);
        final EditText dialogUname = updateDialogView.findViewById(R.id.dialogName);
        final EditText dialogUacron = updateDialogView.findViewById(R.id.dialogAcronym);
        final Button dialogUbutton = updateDialogView.findViewById(R.id.buttonDialog);
        dialogUbutton.setText("UPDATE");

        dialogUname.setText(selectedToUpdate.getUniversityName());
        dialogUacron.setText(selectedToUpdate.getUniversityAcronym());

        updateDialog.setView(updateDialogView);
        final AlertDialog updateDialogAlert = updateDialog.create();
        updateDialogAlert.show();

        dialogUbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String universityDialogName = dialogUname.getText().toString();
                String universityDialogAcronym = dialogUacron.getText().toString();

                universityUpdate.setIdUser(idUserLoged);
                universityUpdate.setIdUniversity(selectedToUpdate.getIdUniversity());
                universityUpdate.setUniversityName(universityDialogName);
                universityUpdate.setUniversityAcronym(universityDialogAcronym);
                universityUpdate.updateUniversityData(universityUpdate);
                toastMsg("University " + universityUpdate.getUniversityName() + " successfully update");
                adapter.notifyDataSetChanged();
                updateDialogAlert.cancel();
            }
        });
    }

    private void universityDelete(final University selectedToRemove) {

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
                selectedToRemove.deleteUniversityData();
                toastMsg("University " + selectedToRemove.getUniversityName() + " has been removed!");
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
        addUniversityFragmentF = new AddUniversityFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameAddEditParameters, addUniversityFragmentF);
        transaction.commit();
    }

    public void goBackToMain() {

        fragmentMain = new AddEditMainFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameAddEditParameters, fragmentMain);
        transaction.commit();

    }

    public void toastMsg(String text) {

        //show toast parameters
        Toast toastError = Toast.makeText(getContext(), text, Toast.LENGTH_LONG);
        toastError.setGravity(Gravity.CENTER, 0, 800);
        toastError.show();

    }
}
