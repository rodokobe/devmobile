package com.projeto.academicplanner.activity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.projeto.academicplanner.R;
import com.projeto.academicplanner.adapter.Adapter_Classes_Calendar;
import com.projeto.academicplanner.helper.ConfigFirebase;
import com.projeto.academicplanner.model.Classes;

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

        adapter.setOnItemClickListener(new Adapter_Classes_Calendar.ClickListener() {

            @Override
            public void onItemClick(Adapter_Classes_Calendar adapter_disciplines, View v, int position) {

            }

            @Override
            public void onItemLongClick(Adapter_Classes_Calendar adapter_disciplines, View v, int position) {

            }
        });

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

        adapter.setOnItemClickListener(new Adapter_Classes_Calendar.ClickListener() {

            @Override
            public void onItemClick(Adapter_Classes_Calendar adapter_disciplines, View v, int position) {

            }

            @Override
            public void onItemLongClick(Adapter_Classes_Calendar adapter_disciplines, View v, int position) {

            }
        });

    }


}
