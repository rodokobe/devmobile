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

    private TextView backToAddEditMain;
    private EditText adminPeopleFirstName, adminPeopleLastName, adminPeopleEmail;
    private Button buttonAdminPeople;
    private AdminPeopleMainFragment adminPeopleMainFragmentF;
    private String idUserLoged;

    public AdminPeopleAddFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View addAdminPeople = inflater.inflate(R.layout.fragment_admin_people_add, container, false);


        backToAddEditMain = addAdminPeople.findViewById(R.id.backToAddEditMain);
        adminPeopleFirstName = addAdminPeople.findViewById(R.id.adminPeopleFirstName);
        adminPeopleLastName = addAdminPeople.findViewById(R.id.adminPeopleLastName);
        adminPeopleEmail = addAdminPeople.findViewById(R.id.adminPeopleEmail);
        buttonAdminPeople = addAdminPeople.findViewById(R.id.buttonAdminPeople);

        //recovery loged user ID
        idUserLoged = ConfigFirebase.getUserId();

        backToAddEditMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToMain();
            }
        });

        buttonAdminPeople.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String adminPeopleSaveFirstName = adminPeopleFirstName.getText().toString();
                String adminPeopleSaveLastName = adminPeopleLastName.getText().toString();
                String adminPeopleSaveEmail = adminPeopleEmail.getText().toString();

                adminPeopleAddNew(adminPeopleSaveFirstName, adminPeopleSaveLastName, adminPeopleSaveEmail);
            }
        });

        return addAdminPeople;

    }

    private void adminPeopleAddNew(String adminPeopleSaveFirstName, String adminPeopleSaveLastName, String adminPeopleSaveEmail) {

        if (!adminPeopleSaveFirstName.isEmpty()) {
            if (!adminPeopleSaveLastName.isEmpty()) {
                if (!adminPeopleSaveEmail.isEmpty()) {

                    AdminPeople adminPeople = new AdminPeople();
                    adminPeople.setIdUser(idUserLoged);
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
        transaction.replace(R.id.frameAddEditUserProfile, adminPeopleMainFragmentF);
        transaction.commit();

    }

    public void toastMsg(String text) {

        //show toast parameters
        Toast toastError = Toast.makeText(getContext(), text, Toast.LENGTH_LONG);
        toastError.setGravity(Gravity.CENTER, 0, 800);
        toastError.show();
    }

}
