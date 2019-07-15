package com.projeto.academicplanner.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.projeto.academicplanner.R;

public class AddAdminPeopleFragment extends Fragment {

    private TextView backToAddEditMain;
    private AdminPeopleMainFragment adminPeopleMainFragmentF;

    public AddAdminPeopleFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View addAdminPeople = inflater.inflate(R.layout.fragment_add_admin_people, container, false);


        backToAddEditMain = addAdminPeople.findViewById(R.id.backToAddEditMain);


        backToAddEditMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBackToMain();
            }
        });

        return addAdminPeople;

    }

    public void goBackToMain() {

        adminPeopleMainFragmentF = new AdminPeopleMainFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameAddEditParameters, adminPeopleMainFragmentF);
        transaction.commit();

    }

}
