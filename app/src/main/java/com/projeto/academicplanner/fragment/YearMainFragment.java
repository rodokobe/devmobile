package com.projeto.academicplanner.fragment;


import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.projeto.academicplanner.R;
import com.projeto.academicplanner.adapter.Adapter_Years;
import com.projeto.academicplanner.helper.ConfigFirebase;
import com.projeto.academicplanner.model.University;
import com.projeto.academicplanner.model.Years;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class YearMainFragment extends Fragment {

    /**
     * General variables
     */
    private Button buttonYears;
    private TextView backToAddEditMain;
    private String idUserLogged;
    private List<Years> yearsList = new ArrayList<>();
    private AddYearFragment addYearFragmentF;
    //private UpdateYearFragment updateYearFragmentF;
    private AddEditMainFragment fragmentMain;

    /**
     * RecyclerView
     */
    private RecyclerView recyclerYears;
    private RecyclerView.LayoutManager layout;
    private Adapter_Years adapter;

    private static final String TAG = "AddEditParametersActivity";


    public YearMainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_year_main, container, false);

        initializingComponents(v);

        /**
         * recovery id from logged user
         */
        idUserLogged = ConfigFirebase.getUserId();

        /**
         * Call adapter
         */
        adapterConstructor();

        /**
         * Create object and fill recyclerViewYears
         */
        Years years = new Years();
        years.recovery(idUserLogged, yearsList, adapter);

        buttonYears.setOnClickListener(view -> {
            goToNewFragment();
        });

        backToAddEditMain.setOnClickListener(view -> {
            goBackToMain();
        });

        return v;
    }

    private void adapterConstructor() {

        //recycler view configuration
        layout = new LinearLayoutManager(getContext());
        adapter = new Adapter_Years(yearsList, getContext());
        recyclerYears.setAdapter(adapter);
        recyclerYears.setLayoutManager(layout);
        recyclerYears.setHasFixedSize(true);

        adapter.setOnItemClickListener(new Adapter_Years.ClickListener() {
            @Override
            public void onItemClick(Adapter_Years adapter_years, View v, final int position) {

                //final ImageView imageEdit = v.findViewById(R.id.imageEdit);
                final ImageView imageDelete = v.findViewById(R.id.imageDelete);

                final Years objectToAction = yearsList.get(position);

                imageDelete.setOnClickListener(view -> {

                    yearsDelete(objectToAction);

                });

                /*imageEdit.setOnClickListener(view -> {

                    goToUpdateFragment(objectToAction);

                });*/
            }
        });

    }

    private void yearsDelete(final Years selectedToRemove) {

        final AlertDialog.Builder deleteDialog = new AlertDialog.Builder(getContext());
        final View deleteDialogView = getLayoutInflater().inflate(R.layout.dialog_model_delete_request, null);

        final Button buttonNoDelete = deleteDialogView.findViewById(R.id.buttonNoDelete);
        final Button buttonDelete = deleteDialogView.findViewById(R.id.buttonDelete);

        //method to create and show AlertDialog to DELETE
        deleteDialog.setView(deleteDialogView);
        final AlertDialog deleteDialogAlert = deleteDialog.create();
        deleteDialogAlert.show();

        buttonDelete.setOnClickListener(v -> {

            //method to remove the selected object
            selectedToRemove.delete();
            toastMsgLong("Year " + selectedToRemove.getYearName() + " has been removed!");
            adapter.notifyDataSetChanged();
            deleteDialogAlert.cancel();

            /**
             * Call adapter
             */
            adapterConstructor();

            /**
             * Create object and fill recyclerViewYears
             */
            Years years = new Years();
            years.recovery(idUserLogged, yearsList, adapter);

        });

        buttonNoDelete.setOnClickListener(v -> {

            //method to cancel the delete operation
            toastMsgLong("Request CANCELED");
            deleteDialogAlert.cancel();
        });
    }

    public void goToNewFragment() {
        addYearFragmentF = new AddYearFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameAddEditUserProfile, addYearFragmentF);
        transaction.commit();
    }

    public void goToUpdateFragment(University objectToAction) {
        /*updateYearFragmentF = new UpdateUniversityFragment();
        Bundle bundle = new Bundle();
        bundle.putString("universityIdBundle", objectToAction.getIdUniversity());
        bundle.putString("universityNameBundle", objectToAction.getUniversityName());
        bundle.putString("universityAcronymBundle", objectToAction.getUniversityAcronym());

        updateYearFragmentF.setArguments(bundle);

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameAddEditUserProfile, updateYearFragmentF);
        transaction.commit();*/
    }

    public void goBackToMain() {

        fragmentMain = new AddEditMainFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameAddEditUserProfile, fragmentMain);
        transaction.commit();

    }

    public void toastMsgLong(String text) {

        Toast.makeText(getContext(), text, Toast.LENGTH_LONG).show();

    }

    private void initializingComponents(View v) {

        buttonYears = v.findViewById(R.id.buttonYears);
        backToAddEditMain = v.findViewById(R.id.backToAddEditMain);
        recyclerYears = v.findViewById(R.id.recyclerYears);

    }

}
