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
import com.projeto.academicplanner.adapter.Adapter_AdminPeople;
import com.projeto.academicplanner.helper.ConfigFirebase;
import com.projeto.academicplanner.model.AdminPeople;

import java.util.ArrayList;
import java.util.List;

public class AdminPeopleMainFragment extends Fragment {


    //general variables
    private Button buttonAdminPeople;
    private TextView backToPrevious;
    private String idUserLogged;
    private List<AdminPeople> adminPeopleS = new ArrayList<>();
    private AdminPeopleAddFragment addAdminPeopleFragmentF;
    private AdminPeopleUpdateFragment updateAdminPeopleFragmentF;
    private SettingsFragment settingsFragment;

    //private StudentMainFragment studentMainFragmentF;

    //recycler view variables
    private RecyclerView recyclerAdminPeople;
    private RecyclerView.LayoutManager layout;
    private Adapter_AdminPeople adapter;

    private static final String TAG = "AddEditParametersActivity";


    public AdminPeopleMainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View mainAdminPeople = inflater.inflate(R.layout.fragment_adminpeople_main, container, false);

        //start configurations
        initializingComponents(mainAdminPeople);

        //recovery loged user ID
        idUserLogged = ConfigFirebase.getUserId();

        //call methods
        adapterConstructor();

        //create object and fill recyclerViewCourses
        AdminPeople adminPeople = new AdminPeople();
        adminPeople.recovery(idUserLogged, adminPeopleS, adapter);

        buttonAdminPeople.setOnClickListener(v -> {
            goToNewFragment();
        });

        backToPrevious.setOnClickListener(v -> {
            goBackToMain();
        });

        return mainAdminPeople;

    }

    private void adapterConstructor() {

        //recycler view configuration
        layout = new LinearLayoutManager(getContext());
        adapter = new Adapter_AdminPeople(adminPeopleS, getContext());
        recyclerAdminPeople.setAdapter(adapter);
        recyclerAdminPeople.setLayoutManager(layout);
        recyclerAdminPeople.setHasFixedSize(true);

        adapter.setOnItemClickListener((adapter_courses, v, position) -> {

            final ImageView imageEdit = v.findViewById(R.id.imageEdit);
            final ImageView imageDelete = v.findViewById(R.id.imageDelete);

            final AdminPeople objectToAction = adminPeopleS.get(position);

            imageDelete.setOnClickListener(view -> {
                courseDelete(objectToAction);
            });

            imageEdit.setOnClickListener(view -> {
                goToUpdateFragment(objectToAction);
            });
        });
    }

    private void courseDelete(final AdminPeople selectedToRemove) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        String name = selectedToRemove.getAdminPeopleFirstName() + " " + selectedToRemove.getAdminPeopleLastName();
        String msg = "Are you sure, you want to delete the administrative people " + name + "?";
        builder.setTitle(msg);
        builder.setPositiveButton(android.R.string.yes, (dialog, id) -> {

            selectedToRemove.delete();
            toastMsg("Administrative People, " + name + ", has been removed!");
            adapter.notifyDataSetChanged();
            dialog.dismiss();

            //call methods
            adapterConstructor();

            //create object and fill recyclerViewCourses
            AdminPeople adminPeople = new AdminPeople();
            adminPeople.recovery(idUserLogged, adminPeopleS, adapter);

        });

        builder.setNegativeButton(android.R.string.no, (dialog, id) -> {
            //method to cancel the delete operation
            toastMsg("Request CANCELED");
            dialog.dismiss();
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void goToUpdateFragment(AdminPeople objectToAction) {
        updateAdminPeopleFragmentF = new AdminPeopleUpdateFragment();
        Bundle dataToUpdate = new Bundle();
        dataToUpdate.putSerializable("AdminPeopleToUpdate", objectToAction);

        updateAdminPeopleFragmentF.setArguments(dataToUpdate);

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameSettingsMain, updateAdminPeopleFragmentF);
        transaction.commit();
    }

    public void goToNewFragment() {
        addAdminPeopleFragmentF = new AdminPeopleAddFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameSettingsMain, addAdminPeopleFragmentF);
        transaction.commit();
    }

    public void goBackToMain() {

        settingsFragment = new SettingsFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameSettingsMain, settingsFragment);
        transaction.commit();

    }

    public void toastMsg(String text) {

        //show toast parameters
        Toast toastError = Toast.makeText(getContext(), text, Toast.LENGTH_LONG);
        toastError.setGravity(Gravity.CENTER, 0, 800);
        toastError.show();

    }

    private void initializingComponents(View view) {
        buttonAdminPeople = view.findViewById(R.id.buttonAdminPeople);
        backToPrevious = view.findViewById(R.id.backToPrevious);
        recyclerAdminPeople = view.findViewById(R.id.recylcerAdminPeople);
    }
}
