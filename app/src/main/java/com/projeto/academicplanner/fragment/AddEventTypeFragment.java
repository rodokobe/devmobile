package com.projeto.academicplanner.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.projeto.academicplanner.R;

public class AddEventTypeFragment extends Fragment {

    private TextView backToAddEditMain;
    private EventTypeMainFragment eventTypeMainFragmentF;

    public AddEventTypeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View addEventType = inflater.inflate(R.layout.fragment_add_event_type, container, false);

        backToAddEditMain = addEventType.findViewById(R.id.backToAddEditMain);


        backToAddEditMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBackToMain();
            }
        });

        return addEventType;

    }

    public void goBackToMain() {

        eventTypeMainFragmentF = new EventTypeMainFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameAddEditUserProfile, eventTypeMainFragmentF);
        transaction.commit();

    }

}
