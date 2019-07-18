package com.projeto.academicplanner.fragment;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.projeto.academicplanner.R;

import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClassAddFragment extends Fragment {

    private Spinner spinnerDiscipline;
    private EditText subjectEditText, editTextDate, editTextHour, editTextTimeDuration, editTextClassroom, editTextContent;


    private ClassAddFragment addClassFragment;


    public ClassAddFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_class_add, container, false);

        initializingComponents(v);



        return v;
    }


    public void toastMsgShort(String errorMsg){
        Toast toastErrorMsg = Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_SHORT);
        toastErrorMsg.setGravity(Gravity.CENTER, 0 , 600);
        toastErrorMsg.show();
    }

    public void backToMain() {
        addClassFragment = new ClassAddFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameAddEditUserProfile, addClassFragment);
        transaction.commit();
    }

    private void initializingComponents(View view) {

        spinnerDiscipline = view.findViewById(R.id.spinnerDiscipline);
        subjectEditText = view.findViewById(R.id.subjectEditText);
        editTextDate = view.findViewById(R.id.editTextDate);
        editTextHour = view.findViewById(R.id.editTextHour);
        editTextTimeDuration = view.findViewById(R.id.editTextTimeDuration);
        editTextClassroom = view.findViewById(R.id.editTextClassroom);
        editTextContent = view.findViewById(R.id.editTextContent);

    }

}
