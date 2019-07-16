package com.projeto.academicplanner.fragment;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.projeto.academicplanner.R;
import com.projeto.academicplanner.helper.ConfigFirebase;
import com.projeto.academicplanner.model.University;

public class UpdateUniversityFragment extends Fragment {

    //general variables
    private EditText universityName, universityAcronym;
    private Button buttonUniversity;
    private TextView backToAddEditMain, addEdit;
    private String idUserLoged, universityUpdateId, universityUpdateName, universityUpdateAcronym;

    private UniversityMainFragment universityMainFragment;


    public UpdateUniversityFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        universityUpdateId = getArguments().getString("universityIdBundle");
        universityUpdateName = getArguments().getString("universityNameBundle");
        universityUpdateAcronym = getArguments().getString("universityAcronymBundle");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View editUniversity = inflater.inflate(R.layout.fragment_add_university, container, false);

        //start configurations
        universityName = editUniversity.findViewById(R.id.universityName);
        universityAcronym = editUniversity.findViewById(R.id.universityAcronym);
        buttonUniversity = editUniversity.findViewById(R.id.buttonUniversity);
        backToAddEditMain = editUniversity.findViewById(R.id.backToAddEditMain);
        addEdit = editUniversity.findViewById(R.id.addEdit);

        universityName.setText(universityUpdateName);
        universityAcronym.setText(universityUpdateAcronym);
        buttonUniversity.setText("UPDATE");
        addEdit.setText("update");


        //recovery loged user ID
        idUserLoged = ConfigFirebase.getUserId();

        buttonUniversity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                universityUpdate();
            }

        });

        backToAddEditMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToMain();
            }
        });

        return editUniversity;
    }

    private void universityUpdate() {

        //method to update
        University universityUpdate = new University();

        universityUpdate.setIdUser(idUserLoged);
        universityUpdate.setIdUniversity(universityUpdateId);
        universityUpdate.setUniversityName(universityName.getText().toString());
        universityUpdate.setUniversityAcronym(universityAcronym.getText().toString());
        universityUpdate.update(universityUpdate);
        toastMsg("University " + universityUpdate.getUniversityName() + " successfully update");
        backToMain();
    }

    public void toastMsg(String text) {

        //show toast parameters
        Toast toastError = Toast.makeText(getContext(), text, Toast.LENGTH_LONG);
        toastError.setGravity(Gravity.CENTER, 0, 800);
        toastError.show();

    }

    public void backToMain() {
        universityMainFragment = new UniversityMainFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameAddEditUserProfile, universityMainFragment);
        transaction.commit();
    }

}
