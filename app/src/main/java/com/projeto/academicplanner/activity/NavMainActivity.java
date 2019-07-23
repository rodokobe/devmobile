package com.projeto.academicplanner.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.projeto.academicplanner.R;
import com.projeto.academicplanner.adapter.Adapter_Classes_Calendar;
import com.projeto.academicplanner.helper.ConfigFirebase;
import com.projeto.academicplanner.model.Classes;
import com.projeto.academicplanner.model.Discipline;
import com.projeto.academicplanner.model.UserProfile;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;

public class NavMainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Serializable {

    private FirebaseAuth auth;
    private FirebaseDatabase firebaseDatabase;
    private String userIdLogged, idDiscipline;
    private TextView nameText, topicEvents;
    private HorizontalCalendar horizontalCalendar;
    private String urlImagemSelecionada = "", dateSelected;
    private List<Classes> classesList;
    private RecyclerView recyclerEvents;
    private RecyclerView.LayoutManager layout;
    private Adapter_Classes_Calendar adapter;
    private DatabaseReference databaseReference;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Next Events");

        auth = ConfigFirebase.getReferenciaAutenticacao();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        databaseReference = ConfigFirebase.getReferenciaFirebase();

        initializingComponents();

        /**
         * Initializing Google permission
         */
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // [END config_signin]

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        /**
         * Setting name and photo on NavigationView
         */
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);

        TextView nameNav = headerView.findViewById(R.id.navNameText);
        ImageView photoProfileNav = headerView.findViewById(R.id.imageViewProfile);

        DatabaseReference userProfileRef = databaseReference
                .child("users")
                .child(userIdLogged);
        userProfileRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);

                String firstname = userProfile.getFirstname();
                String lastname = userProfile.getLastname();

                String name = firstname + " " + lastname;

                if (dataSnapshot.getValue() != null) {

                    nameNav.setText(name);
                    urlImagemSelecionada = userProfile.getUrlProfile();
                }

                if (urlImagemSelecionada != "") {
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

        /**
         * Implementing Calendar
         */
        /* starts before 1 month from now */
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, -3);

        /* ends after 1 month from now */
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 3);

        //DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Calendar cal = Calendar.getInstance();
        //Log.i("Onde estou? ", "Aqui: " + dateFormat.format(cal));

        // Default Date set to Today.
        final Calendar defaultSelectedDate = Calendar.getInstance();

        horizontalCalendar = new HorizontalCalendar.Builder(this, R.id.calendarView)
                .range(startDate, endDate)
                .datesNumberOnScreen(5)
                .configure()
                .formatTopText("MMM")
                .formatMiddleText("dd")
                .formatBottomText("EEE")
                .showTopText(true)
                .showBottomText(true)
                .textColor(Color.LTGRAY, Color.WHITE)
                .colorTextMiddle(Color.LTGRAY, Color.parseColor("#ffd54f"))
                .end()
                .defaultSelectedDate(defaultSelectedDate)
                .build();

        calendarListener();
    }

    /**
     * Pressing Navigation Menu
     */
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * Implementing calendar on main activity
     */
    public void calendarListener() {

        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {

            @Override
            public void onDateSelected(Calendar date, int position) {

                int daySelected = date.get(Calendar.DAY_OF_MONTH);
                int monthSelected = date.get(Calendar.MONTH);
                int yearSelected = date.get(Calendar.YEAR);

                classesList = new ArrayList<>();

                dateSelected = String.format("%02d/%02d/%04d", daySelected, monthSelected + 1, yearSelected);

                DatabaseReference disciplineRef = databaseReference
                        .child("disciplines")
                        .child(userIdLogged);

                disciplineRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        classesList.clear();

                        for (DataSnapshot snap : dataSnapshot.getChildren()) {
                            Discipline discipline = snap.getValue(Discipline.class);
                            idDiscipline = discipline.getIdDiscipline();

                            DatabaseReference classesRef = disciplineRef.child(idDiscipline).child("classes");

                            classesRef.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                    for (DataSnapshot snap : dataSnapshot.getChildren()) {

                                        Classes aula = snap.getValue(Classes.class);

                                        String dataAula = aula.getClassDate();

                                        try {

                                            if (dataAula.equals(dateSelected)) {
                                                classesList.add(aula);
                                                topicEvents.setText("Events to selected date:");
                                            } else {
                                            }

                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    adapter = new Adapter_Classes_Calendar(classesList, getApplicationContext());
                                    recyclerEvents.setAdapter(adapter);
                                    recyclerEvents.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                                    recyclerEvents.setHasFixedSize(true);
                                    Collections.reverse(classesList);

                                    adapter.setOnItemClickListener(new Adapter_Classes_Calendar.ClickListener() {
                                        @Override
                                        public void onItemClick(Adapter_Classes_Calendar adapter_disciplines, View v, int position) {

                                        }

                                        @Override
                                        public void onItemLongClick(Adapter_Classes_Calendar adapter_disciplines, View v, int position) {
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

                                    adapter.notifyDataSetChanged();

                                    //adapterConstructor();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

        });
    }

    public void toastMsgLong(String text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        /**
         * Setting navigation menu items
         */
        if (id == R.id.nav_events) {
            startActivity(new Intent(getApplicationContext(), NavMainActivity.class));
        } else if (id == R.id.nav_settings) {
            startActivity(new Intent(getApplicationContext(), SettingsMainActivity.class));
        } else if (id == R.id.nav_navigation) {
            startActivity(new Intent(getApplicationContext(), NavigationActivity.class));
        } else if (id == R.id.nav_messages) {
            startActivity(new Intent(getApplicationContext(), SendEmailActivity.class));
        } else if (id == R.id.nav_preferences) {
            startActivity(new Intent(getApplicationContext(), PreferencesActivity.class));
        } else if (id == R.id.nav_profile) {
            startActivity(new Intent(getApplicationContext(), UserProfileActivity.class));
        } else if (id == R.id.nav_logout) {

            userLogout();
            finish();

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void addRegularClass(View view) {
        startActivity(new Intent(this, ClassAddActivity.class));
    }

    public void addSpecialClass(View view) {
        startActivity(new Intent(this, SpecialClassActivity.class));
    }

    public void addContactHour(View view) {
        startActivity(new Intent( this, SpecialEventActivity.class));
    }

    private void initializingComponents() {
        nameText = findViewById(R.id.navNameText);
        recyclerEvents = findViewById(R.id.recyclerEvents);
        topicEvents = findViewById(R.id.topicEvents);

        //recovery logged user ID
        userIdLogged = ConfigFirebase.getUserId();
    }

    private void userLogout() {
        try {
            auth.signOut();
            mGoogleSignInClient.signOut();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        calendarListener();
    }

}