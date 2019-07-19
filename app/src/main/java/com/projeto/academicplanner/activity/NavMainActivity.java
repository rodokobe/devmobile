package com.projeto.academicplanner.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

//import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.projeto.academicplanner.R;
import com.projeto.academicplanner.adapter.Adapter_Classes_Calendar;
import com.projeto.academicplanner.helper.ConfigFirebase;
import com.projeto.academicplanner.model.Classes;
import com.projeto.academicplanner.model.UserProfile;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.model.CalendarEvent;
import devs.mulham.horizontalcalendar.utils.CalendarEventsPredicate;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;

import com.github.clans.fab.FloatingActionButton;

public class NavMainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth auth;

    private String userIdLogged;
    private TextView nameText;
    private HorizontalCalendar horizontalCalendar;
    private FloatingActionButton fabRClass, fabSClass, fabContactHour;

    private String urlImagemSelecionada = "";


    private List<Classes> classes = new ArrayList<>();
    private RecyclerView recyclerEvents;
    private RecyclerView.LayoutManager layout;
    private Adapter_Classes_Calendar adapter;

    private DatabaseReference firebaseRef;


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

        firebaseRef = ConfigFirebase.getReferenciaFirebase();
        userIdLogged = UserFirebase.getUserId();

        //FloatingActionButton fab_menu = findViewById(R.id.fab_menu);
        //fab_menu.bringToFront();

        initializingComponents();

        /**
         * Setting name and photo on NavigationView
         */
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);

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

        /**
         * Implementing Calendar
         */

        /* starts before 1 month from now */
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, -12);

        /* ends after 1 month from now */
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 12);

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
                .addEvents(new CalendarEventsPredicate() {

                    Random rnd = new Random();

                    @Override
                    public List<CalendarEvent> events(Calendar date) {
                        List<CalendarEvent> events = new ArrayList<>();

                        //int count = rnd.nextInt(6);

                        /*for (int i = 0; i <= count; i++) {
                            events.add(new CalendarEvent(Color.rgb(rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256)), "event"));
                        }*/

                        events.add(new CalendarEvent(Color.rgb( 255, 255, 0), "event"));

                        return events;
                    }
                })
                .build();

        Log.i("Default Date", DateFormat.format("EEE, MMM d, yyyy", defaultSelectedDate).toString());

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


    public void calendarListener(){
        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {

            @Override
            public void onDateSelected(Calendar date, int position) {

                int daySelected = date.get(Calendar.DAY_OF_MONTH);
                int monthSelected = date.get(Calendar.MONTH);
                int yearSelected = date.get(Calendar.YEAR);

                final String dateSelected = String.format("%02d/%02d/%04d", daySelected,monthSelected+1,yearSelected);

                //String selectedDateStr = DateFormat.format("EEE, MMM d, yyyy", date).toString();
                Toast.makeText(NavMainActivity.this, dateSelected, Toast.LENGTH_SHORT).show();
                //Log.i("onDateSelected", selectedDateStr + " - Position = " + position);
            }

        });
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_events) {

        } else if (id == R.id.nav_messages) {

        } else if (id == R.id.nav_settings) {
            startActivity( new Intent(getApplicationContext(), SettingsMainActivity.class) );
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

    private void adapterConstructor() {

        //recycler view configuration
        layout = new LinearLayoutManager(this);
        adapter = new Adapter_Classes_Calendar(classes, this);
        recyclerEvents.setAdapter(adapter);
        recyclerEvents.setLayoutManager(layout);
        recyclerEvents.setHasFixedSize(true);

        //recyclerEvents.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));

        adapter.setOnItemClickListener(new Adapter_Classes_Calendar.ClickListener() {
            @Override
            public void onItemClick(Adapter_Classes_Calendar adapter_classes_calendar, View v, final int position) {

                final Classes objectToAction = classes.get(position);
            }
        });

    }

    public void addRegularClass(View view){
            startActivity( new Intent(this, ClassMainActivity.class) );
    }

    private void initializingComponents(){

        nameText = findViewById(R.id.navNameText);
        recyclerEvents = findViewById(R.id.recyclerEvents);

        //recovery logged user ID
        userIdLogged = ConfigFirebase.getUserId();

        //call methods
        adapterConstructor();

        //create object and fill recyclerViewCourses
        Classes classe = new Classes();
        classe.recovery(userIdLogged, classes, adapter);

    }

    private void userLogout(){
        try {
            auth.signOut();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}