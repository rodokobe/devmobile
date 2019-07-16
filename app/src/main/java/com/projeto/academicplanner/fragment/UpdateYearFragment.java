package com.projeto.academicplanner.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.projeto.academicplanner.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpdateYearFragment extends Fragment {


    private EditText year;
    private Button buttonUniversity;
    private TextView backToAddEditMain, addEdit;
    private String idUserLoged, universityUpdateId, universityUpdateName, universityUpdateAcronym;

    private UniversityMainFragment universityMainFragment;

    public UpdateYearFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_update_year, container, false);
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
        transaction.replace(R.id.frameAddEditUserProfile, universityMainFragment);
        transaction.commit();
    }

}
