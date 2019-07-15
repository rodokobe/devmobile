package com.projeto.academicplanner.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.projeto.academicplanner.R;
import com.projeto.academicplanner.helper.ConfigFirebase;
import com.projeto.academicplanner.model.UserProfile;
import com.squareup.picasso.Picasso;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class NavMainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth auth;

    private String userIdLogged;
    private TextView nameText;
    private String urlImagemSelecionada = "";

    private DatabaseReference firebaseRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        auth = ConfigFirebase.getReferenciaAutenticacao();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);

        firebaseRef = ConfigFirebase.getReferenciaFirebase();
        userIdLogged = UserFirebase.getUserId();

        initializingComponents();

        /**
         * Setting name on NavigationView
         */
        TextView nameNav = headerView.findViewById(R.id.navNameText);
        ImageView photoProfileNav = headerView.findViewById(R.id.imageViewProfile);

        DatabaseReference userProfileRef = firebaseRef
                .child("users")
                .child(userIdLogged);
        userProfileRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);

                String firstname = userProfile.getFirstname();
                String lastname = userProfile.getLastname();

                String name = firstname + " " + lastname;

                if(dataSnapshot.getValue() != null){

                    nameNav.setText(name);
                    urlImagemSelecionada = userProfile.getUrlProfile();
                }

                if(urlImagemSelecionada != ""){
                    Picasso.get()
                            .load(urlImagemSelecionada)
                            .into(photoProfileNav);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        /**
         * Setting email on NavigationView
         */

        TextView emailNav = headerView.findViewById(R.id.textViewEmail);
        emailNav.setText(auth.getCurrentUser().getEmail());

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_settings) {

            startActivity( new Intent(getApplicationContext(), AddEditParametersActivity.class) );

        } else if (id == R.id.nav_profile) {

            startActivity( new Intent(getApplicationContext(), UserProfileActivity.class) );

        } else if (id == R.id.nav_logout) {

            userLogout();
            finish();

        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    private void initializingComponents(){

        nameText = findViewById(R.id.navNameText);
    }



    private void userLogout(){
        try {
            auth.signOut();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
