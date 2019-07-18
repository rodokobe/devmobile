package com.projeto.academicplanner.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.projeto.academicplanner.R;
import com.projeto.academicplanner.helper.ConfigFirebase;
import com.projeto.academicplanner.model.Years;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class YearAddFragment extends Fragment implements AdapterView.OnItemSelectedListener {


    /**
     * Creating view components
     */
    private Spinner spinnerYears;
    private Button buttonAdd;
    private TextView backToAddEditMain;

    /**
     * Creating variables
     */
    private String spinnerValue;
    private String idUserLogged;

    /**
     * Setting Firebase items
     */
    private FirebaseAuth auth;
    private DatabaseReference firebaseRef;

    private YearMainFragment yearMainFragment;

    public YearAddFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_year_add, container, false);

        auth = ConfigFirebase.getReferenciaAutenticacao();
        firebaseRef = ConfigFirebase.getReferenciaFirebase();

        InitializingComponents(view);

        spinnerYears.setEnabled(true);
        idUserLogged = ConfigFirebase.getUserId();

        backToAddEditMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToMain();
            }

        });

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Years year = new Years();
                year.setYearName(spinnerValue);
                year.setIdUser(idUserLogged);
                year.save();

                toastMsgShort("Year " + spinnerValue + " was added");

                backToMain();
            }

        });

        return view;
    }

    public void toastMsgLong(String errorMsg) {
        Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_SHORT).show();
    }

    public void toastMsgShort(String errorMsg) {
        Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_SHORT).show();
    }

    private void InitializingComponents(View v) {

        spinnerYears = v.findViewById(R.id.spinnerYears);

        ArrayList<String> years = new ArrayList<String>();
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        int fromYear = thisYear - 2;

        for (int i = fromYear; i <= (thisYear + 3); i++) {
            String yearOne = Integer.toString(i);
            String yearTwo = Integer.toString(i + 1);
            String AnoLetivo = yearOne + "-" + yearTwo;
            years.add(AnoLetivo);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getContext(),
                android.R.layout.simple_spinner_item,
                years);

        spinnerYears.setAdapter(adapter);
        spinnerYears.setOnItemSelectedListener(this);

        buttonAdd = v.findViewById(R.id.buttonAdd);
        backToAddEditMain = v.findViewById(R.id.backToAddEditMain);


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        spinnerValue = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void backToMain() {
        yearMainFragment = new YearMainFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameAddEditUserProfile, yearMainFragment);
        transaction.commit();
    }

}
