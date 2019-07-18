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
import com.projeto.academicplanner.model.AdminPeople;

public class AdminPeopleUpdateFragment extends Fragment {

    private TextView backToPrevious;
    private EditText adminPeopleFirstName, adminPeopleLastName, adminPeopleEmail;
    private Button buttonAdminPeople;
    private String idUserLogged;

    private AdminPeopleMainFragment adminPeopleMainFragmentF;
    private AdminPeople adminPeopleToUpdate;


    public AdminPeopleUpdateFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adminPeopleToUpdate = (AdminPeople) getArguments().getSerializable("AdminPeopleToUpdate");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View updateStudent = inflater.inflate(R.layout.fragment_admin_people_add, container, false);


        initializingComponents(updateStudent);

        adminPeopleFirstName.setText(adminPeopleToUpdate.getAdminPeopleFirstName());
        adminPeopleLastName.setText(adminPeopleToUpdate.getAdminPeopleLastName());
        adminPeopleEmail.setText(adminPeopleToUpdate.getAdminPeopleEmail());

        buttonAdminPeople.setText("UPDATE");


        //recovery logged user ID
        idUserLogged = ConfigFirebase.getUserId();

        backToPrevious.setOnClickListener( v -> {
                backToMain();
        });

        buttonAdminPeople.setOnClickListener( v -> {
                adminPeopleUpdate();
        });

        return updateStudent;

    }

    private void adminPeopleUpdate() {

        AdminPeople admPeopleUpdate = new AdminPeople();

        admPeopleUpdate.setIdUser(idUserLogged);
        admPeopleUpdate.setIdAdminPeople(adminPeopleToUpdate.getIdAdminPeople());
        admPeopleUpdate.setAdminPeopleFirstName(adminPeopleFirstName.getText().toString());
        admPeopleUpdate.setAdminPeopleLastName(adminPeopleLastName.getText().toString());
        admPeopleUpdate.setAdminPeopleEmail(adminPeopleEmail.getText().toString());
        admPeopleUpdate.update(admPeopleUpdate);

        toastMsg("Admin " + admPeopleUpdate.getAdminPeopleFirstName() + " successfully update");
        backToMain();

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

    private void initializingComponents(View v){
        backToPrevious = v.findViewById(R.id.backToPrevious);
        adminPeopleFirstName = v.findViewById(R.id.adminPeopleFirstName);
        adminPeopleLastName = v.findViewById(R.id.adminPeopleLastName);
        adminPeopleEmail = v.findViewById(R.id.adminPeopleEmail);
        buttonAdminPeople = v.findViewById(R.id.buttonAdminPeople);
    }

}
