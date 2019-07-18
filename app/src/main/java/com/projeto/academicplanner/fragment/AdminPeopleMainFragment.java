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
    private TextView backToAddEditMain;
    private String idUserLoged;
    private List<AdminPeople> adminPeopleS = new ArrayList<>();
    private AdminPeopleAddFragment addAdminPeopleFragmentF;
    private AdminPeopleUpdateFragment updateAdminPeopleFragmentF;
    private AddEditMainFragment fragmentMain;

    //private StudentMainFragment studentMainFragmentF;

    //recycler view variables
    private RecyclerView recylcerAdminPeople;
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
        buttonAdminPeople = mainAdminPeople.findViewById(R.id.buttonAdminPeople);
        backToAddEditMain = mainAdminPeople.findViewById(R.id.backToAddEditMain);
        recylcerAdminPeople = mainAdminPeople.findViewById(R.id.recylcerAdminPeople);

        //recovery loged user ID
        idUserLoged = ConfigFirebase.getUserId();

        //call methods
        adapterConstructor();

        //create object and fill recyclerViewCourses
        AdminPeople adminPeople = new AdminPeople();
        adminPeople.recovery(idUserLoged, adminPeopleS, adapter);

        buttonAdminPeople.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { goToNewFragment(); }
        });

        backToAddEditMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBackToMain();
            }
        });

        return mainAdminPeople;

    }

    private void adapterConstructor() {

        //recycler view configuration
        layout = new LinearLayoutManager(getContext());
        adapter = new Adapter_AdminPeople(adminPeopleS, getContext());
        recylcerAdminPeople.setAdapter(adapter);
        recylcerAdminPeople.setLayoutManager(layout);
        recylcerAdminPeople.setHasFixedSize(true);
        recylcerAdminPeople.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayout.VERTICAL));

        adapter.setOnItemClickListener(new Adapter_AdminPeople.ClickListener() {
            @Override
            public void onItemClick(Adapter_AdminPeople adapter_courses, View v, final int position) {

                final ImageView imageEdit = v.findViewById(R.id.imageEdit);
                final ImageView imageDelete = v.findViewById(R.id.imageDelete);

                final AdminPeople objectToAction = adminPeopleS.get(position);

                imageDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        courseDelete(objectToAction);

                    }
                });

                imageEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        goToUpdateFragment(objectToAction);

                    }
                });
            }
        });

    }

    private void courseDelete(final AdminPeople selectedToRemove) {

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
                toastMsg("Admin " + selectedToRemove.getAdminPeopleFirstName() + " has been removed!");
                adapter.notifyDataSetChanged();
                deleteDialogAlert.cancel();

                //call methods
                adapterConstructor();

                //create object and fill recyclerViewCourses
                AdminPeople adminPeople = new AdminPeople();
                adminPeople.recovery(idUserLoged, adminPeopleS, adapter);
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

    public void goToUpdateFragment(AdminPeople objectToAction) {
        updateAdminPeopleFragmentF = new AdminPeopleUpdateFragment();
        Bundle dataToUpdate = new Bundle();
        dataToUpdate.putSerializable("AdminPeopleToUpdate", objectToAction);

        updateAdminPeopleFragmentF.setArguments(dataToUpdate);

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameAddEditUserProfile, updateAdminPeopleFragmentF);
        transaction.commit();
    }

    public void goToNewFragment() {
        addAdminPeopleFragmentF = new AdminPeopleAddFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameAddEditUserProfile, addAdminPeopleFragmentF);
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
