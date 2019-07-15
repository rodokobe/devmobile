package com.projeto.academicplanner.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.projeto.academicplanner.R;

import com.projeto.academicplanner.fragment.AddEditMainFragment;

public class AddEditParametersActivity extends AppCompatActivity implements View.OnClickListener {

    private AddEditMainFragment fragmentMain;


    private static final String TAG = "AddEditParametersActivity";

    public AddEditParametersActivity() {
        // Required empty public constructor
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addedit_parameters);

        fragmentMain = new AddEditMainFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameAddEditUserProfile, fragmentMain);
        transaction.commit();

    }

    @Override
    public void onClick(View v) {

    }

}
