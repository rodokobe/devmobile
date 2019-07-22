package com.projeto.academicplanner.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import com.projeto.academicplanner.R;
import com.projeto.academicplanner.fragment.ClassAddFragment;
import com.projeto.academicplanner.fragment.ClassUpdateFragment;

public class ClassMainActivity extends AppCompatActivity {

    private ClassAddFragment classAddFragment;
    private ClassUpdateFragment classUpdateFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add New Class");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        addClassFragment();

    }

    private void addClassFragment() {
        classAddFragment = new ClassAddFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameClassMain, classAddFragment);
        transaction.commit();
    }

    private void updateClassFragment() {
        classUpdateFragment = new ClassUpdateFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameClassMain, classUpdateFragment);
        transaction.commit();
    }
}
