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
    private String idUserLogged;
    private List<Years> yearsList = new ArrayList<>();
    private YearAddFragment addYearFragmentF;
    private TextView backToPrevious;
    private SettingsFragment settingsFragment;

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

        backToPrevious.setOnClickListener( view -> {
            backToMainSettings();
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

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        String name = selectedToRemove.getYearName();
        String msg = "Are you sure, you want to delete the year " + name + "?";

        builder.setTitle(msg);
        builder.setPositiveButton(android.R.string.yes, (dialog, id) -> {

            selectedToRemove.delete();
            toastMsgLong("Year " + name + " has been removed!");
            adapter.notifyDataSetChanged();
            dialog.dismiss();

            //call methods
            adapterConstructor();

            //create object and fill recyclerViewCourses
            Years yearss = new Years();
            yearss.recovery(idUserLogged, yearsList, adapter);

        });

        builder.setNegativeButton(android.R.string.no, (dialog, id) -> {
            //method to cancel the delete operation
            toastMsgLong("Request CANCELED");
            dialog.dismiss();
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void goToNewFragment() {
        addYearFragmentF = new YearAddFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameSettingsMain, addYearFragmentF);
        transaction.commit();
    }

    /*public void goToUpdateFragment(University objectToAction) {
        updateYearFragmentF = new UniversityUpdateFragment();
        Bundle bundle = new Bundle();
        bundle.putString("universityIdBundle", objectToAction.getIdUniversity());
        bundle.putString("universityNameBundle", objectToAction.getUniversityName());
        bundle.putString("universityAcronymBundle", objectToAction.getUniversityAcronym());

        updateYearFragmentF.setArguments(bundle);

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameAddEditUserProfile, updateYearFragmentF);
        transaction.commit();
    }*/

    /*public void goBackToMain() {

        fragmentMain = new AddEditMainFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameSettingsMain, fragmentMain);
        transaction.commit();

    }*/

    private void toastMsgLong(String text) {

        Toast.makeText(getContext(), text, Toast.LENGTH_LONG).show();

    }

    private void backToMainSettings(){

        settingsFragment = new SettingsFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameSettingsMain, settingsFragment);
        transaction.commit();

    }

    private void initializingComponents(View v) {

        buttonYears = v.findViewById(R.id.buttonYears);
        recyclerYears = v.findViewById(R.id.recyclerYears);
        backToPrevious = v.findViewById(R.id.backToPrevious);

    }

}
