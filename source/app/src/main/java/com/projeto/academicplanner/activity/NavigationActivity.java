package com.projeto.academicplanner.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.projeto.academicplanner.R;
import com.projeto.academicplanner.adapter.Adapter_Classes_Main;
import com.projeto.academicplanner.helper.ConfigFirebase;
import com.projeto.academicplanner.model.Classes;

import java.util.ArrayList;
import java.util.List;

public class NavigationActivity extends AppCompatActivity {

    //recycler view variables
    private RecyclerView recyclerClasses;
    private RecyclerView.LayoutManager layout;
    private Adapter_Classes_Main adapter;


    //arrayList recycler
    private List<Classes> classesList = new ArrayList<>();

    private String userIdLogged;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Navigation");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


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
        adapter = new Adapter_Classes_Main(classesList, this);
        recyclerClasses.setAdapter(adapter);
        recyclerClasses.setLayoutManager(layout);
        recyclerClasses.setHasFixedSize(true);

        adapter.setOnItemClickListener(new Adapter_Classes_Main.ClickListener() {

            @Override
            public void onItemClick(Adapter_Classes_Main adapter_disciplines, View v, int position) {

            }

            @Override
            public void onItemLongClick(Adapter_Classes_Main adapter_disciplines_main, View v, int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                View dialogView = getLayoutInflater().inflate(R.layout.dialog_rv_nav_main, null);

                builder.setView(dialogView);
                AlertDialog dialog = builder.create();
                dialog.show();

                TextView textDetails = dialogView.findViewById(R.id.textDetails);
                TextView textUpdate = dialogView.findViewById(R.id.textUpdate);
                TextView textRemove = dialogView.findViewById(R.id.textRemove);

                final Classes objectToAction = classesList.get(position);

                textDetails.setOnClickListener( view -> {
                    Intent classDetail = new Intent(getApplicationContext(), ClassDetailActivity.class);
                    classDetail.putExtra("ClassToDetail", objectToAction);
                    startActivity(classDetail);
                    dialog.dismiss();

                });

                textUpdate.setOnClickListener( view -> {
                    Intent classUpdate = new Intent(getApplicationContext(), ClassUpdateActivity.class);
                    classUpdate.putExtra("ClassToUpdate", objectToAction);
                    startActivity(classUpdate);
                    dialog.dismiss();
                });

                textRemove.setOnClickListener( view -> {
                    objectToAction.delete();
                    toastMsgLong("Class " + objectToAction.getSubject() + " has been removed!");
                    adapter.notifyDataSetChanged();
                    dialog.dismiss();

                });

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

        recyclerClasses = findViewById(R.id.recyclerClasses);
    }

    private void search(String str) {
        classesList = new ArrayList<>();

        for (Classes object : classesList) {
            if (object.getNameDiscipline().toLowerCase().contains(str.toLowerCase()))
            {
                classesList.add(object);
            }

        }
        adapter = new Adapter_Classes_Main(classesList, this);
        recyclerClasses.setAdapter(adapter);
        recyclerClasses.setLayoutManager(layout);
        recyclerClasses.setHasFixedSize(true);

        adapter.setOnItemClickListener(new Adapter_Classes_Main.ClickListener() {


            @Override
            public void onItemClick(Adapter_Classes_Main adapter_disciplines, View v, int position) {

            }

            @Override
            public void onItemLongClick(Adapter_Classes_Main adapter_disciplines_main, View v, int position) {

            }
        });

    }

}
