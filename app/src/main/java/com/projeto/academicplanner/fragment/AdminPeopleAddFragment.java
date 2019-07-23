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

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.projeto.academicplanner.R;
import com.projeto.academicplanner.helper.ConfigFirebase;
import com.projeto.academicplanner.model.AdminPeople;

public class AdminPeopleAddFragment extends Fragment {

    private TextView backToPrevious;
    private EditText adminPeopleFirstName, adminPeopleLastName, adminPeopleEmail;
    private Button buttonAdminPeople;
    private AdminPeopleMainFragment adminPeopleMainFragmentF;
    private String idUserLogged;

    public AdminPeopleAddFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View addAdminPeople = inflater.inflate(R.layout.fragment_admin_people_add, container, false);

        initializingComponents(addAdminPeople);

        //recovery logged user ID
        idUserLogged = ConfigFirebase.getUserId();


        backToPrevious.setOnClickListener( view -> {
                backToMain();
        });

        buttonAdminPeople.setOnClickListener( view -> {
                String adminPeopleSaveFirstName = adminPeopleFirstName.getText().toString();
                String adminPeopleSaveLastName = adminPeopleLastName.getText().toString();
                String adminPeopleSaveEmail = adminPeopleEmail.getText().toString();

                adminPeopleAddNew(adminPeopleSaveFirstName, adminPeopleSaveLastName, adminPeopleSaveEmail);

        });

        return addAdminPeople;

    }

    private void adminPeopleAddNew(String adminPeopleSaveFirstName, String adminPeopleSaveLastName, String adminPeopleSaveEmail) {

        if (!adminPeopleSaveFirstName.isEmpty()) {
            if (!adminPeopleSaveLastName.isEmpty()) {
                if (!adminPeopleSaveEmail.isEmpty()) {

                    AdminPeople adminPeople = new AdminPeople();
                    adminPeople.setIdUser(idUserLogged);
                    adminPeople.setAdminPeopleFirstName(adminPeopleSaveFirstName);
                    adminPeople.setAdminPeopleLastName(adminPeopleSaveLastName);
                    adminPeople.setAdminPeopleEmail(adminPeopleSaveEmail);

                    adminPeople.save();
                    toastMsg("Admin " + adminPeople.getAdminPeopleFirstName() + " successfully added ");
                    backToMain();

                } else {
                    toastMsg("Enter an e-mail to Admin");
                }
            } else {
                toastMsg("Enter a last name to Admin");
            }
        } else {
            toastMsg("Enter a first name to Admin");
        }

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
        adminPeopleFirstName = view.findViewById(R.id.adminPeopleName);
        adminPeopleLastName = view.findViewById(R.id.adminPeopleLastName);
        adminPeopleEmail = view.findViewById(R.id.adminPeopleEmail);
        buttonAdminPeople = view.findViewById(R.id.buttonAdminPeople);
    }

}
