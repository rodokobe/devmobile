package com.projeto.academicplanner.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.projeto.academicplanner.R;
import com.projeto.academicplanner.helper.SendMail;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

public class SendEmailActivity extends AppCompatActivity implements View.OnClickListener {

    //Declaring EditText
    private EditText editTextEmail;
    private EditText editTextSubject;
    private EditText editTextMessage;

    //Spinner with search
    private SearchableSpinner spinnerDisciplines;

    //Send button
    private Button buttonSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_email);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Send Mail");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initializingComponents();
    }

    private void sendEmail() {
        //Getting content for email
        String email = editTextEmail.getText().toString().trim();
        String subject = editTextSubject.getText().toString().trim();
        String message = editTextMessage.getText().toString().trim();

        //Creating SendMail object
        SendMail sm = new SendMail(this, email, subject, message);

        //Executing sendmail to send email
        sm.execute();
    }

    private void initializingComponents(){
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextSubject = (EditText) findViewById(R.id.editTextSubject);
        editTextMessage = (EditText) findViewById(R.id.editTextMessage);

        buttonSend = (Button) findViewById(R.id.buttonSend);

        //Adding click listener
        buttonSend.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        sendEmail();
    }
}
