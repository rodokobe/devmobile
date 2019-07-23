package com.projeto.academicplanner.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.projeto.academicplanner.R;
import com.projeto.academicplanner.adapter.Adapter_Classes;
import com.projeto.academicplanner.adapter.Adapter_Classes_Calendar;
import com.projeto.academicplanner.adapter.Adapter_Classes_To_Filter;
import com.projeto.academicplanner.adapter.Adapter_Disciplines;
import com.projeto.academicplanner.fragment.DisciplineMainFragment;
import com.projeto.academicplanner.helper.ConfigFirebase;
import com.projeto.academicplanner.model.Classes;
import com.projeto.academicplanner.model.Discipline;

import java.util.ArrayList;
import java.util.List;

public class NavigationActivity extends AppCompatActivity {

    //recycler view variables
    private RecyclerView recyclerDisciplines;
    private RecyclerView.LayoutManager layout;
    private Adapter_Classes_Calendar adapter;

    //searchview
    private SearchView searchView;


    //arrayList recycler
    private List<Classes> classesList = new ArrayList<>();

    private String userIdLogged;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);


        //start configurations
        initializingComponents();

        //recovery loged user ID
        userIdLogged = ConfigFirebase.getUserId();

        //call methods
        adapterConstructor();

        //create object and fill recyclerView
        Classes classes = new Classes();
        classes.recovery(userIdLogged, classesList, adapter);

    }

    private void adapterConstructor() {

        //recycler view configuration
        layout = new LinearLayoutManager(this);
        adapter = new Adapter_Classes_Calendar(classesList, this);
        recyclerDisciplines.setAdapter(adapter);
        recyclerDisciplines.setLayoutManager(layout);
        recyclerDisciplines.setHasFixedSize(true);

        // adapter.setOnItemClickListener( (adapter, v, position) -> {

        //});

    }

    public void toastMsgLong(String text) {

        //show toast parameters
        Toast toastError = Toast.makeText(this, text, Toast.LENGTH_LONG);
        toastError.setGravity(Gravity.CENTER, 0, 800);
        toastError.show();

    }

    private void initializingComponents() {

        recyclerDisciplines = findViewById(R.id.recyclerDiscipline);
        searchView = findViewById(R.id.searchView);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (searchView != null) {
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    search(newText);
                    return true;
                }
            });
        }
    }

    private void search(String str) {
        classesList = new ArrayList<>();

        for (Classes object : classesList) {
            if (object.getNameDiscipline().toLowerCase().contains(str.toLowerCase()))
            {
                classesList.add(object);
            }

        }
        adapter = new Adapter_Classes_Calendar(classesList, this);
        recyclerDisciplines.setAdapter(adapter);
        recyclerDisciplines.setLayoutManager(layout);
        recyclerDisciplines.setHasFixedSize(true);

    }


}
