package com.projeto.academicplanner.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.projeto.academicplanner.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClassDetailFragment extends Fragment {


    public ClassDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_class_detail, container, false);
    }

}
