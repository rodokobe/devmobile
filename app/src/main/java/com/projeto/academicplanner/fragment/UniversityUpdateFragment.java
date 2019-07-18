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

public class UniversityUpdateFragment extends Fragment {

    //general variables
    private EditText universityName, universityAcronym;
    private Button buttonUniversity;
    private TextView backToPrevious;
    private String idUserLogged, universityUpdateId, universityUpdateName, universityUpdateAcronym;

    private UniversityMainFragment universityMainFragment;


    public UniversityUpdateFragment() {
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
        View editUniversity = inflater.inflate(R.layout.fragment_university_add, container, false);

        //start configurations
        initializingComponents(editUniversity);

        universityName.setText(universityUpdateName);
        universityAcronym.setText(universityUpdateAcronym);
        buttonUniversity.setText("UPDATE");


        //recovery logged user ID
        idUserLogged = ConfigFirebase.getUserId();

        buttonUniversity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                universityUpdate();
            }

        });

        backToPrevious.setOnClickListener(new View.OnClickListener() {
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

        universityUpdate.setIdUser(idUserLogged);
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
        transaction.replace(R.id.frameSettingsMain, universityMainFragment);
        transaction.commit();
    }

    private void initializingComponents(View view){
        universityName = view.findViewById(R.id.universityName);
        universityAcronym = view.findViewById(R.id.universityAcronym);
        buttonUniversity = view.findViewById(R.id.buttonUniversity);
        backToPrevious = view.findViewById(R.id.backToPrevious);
    }

}
