package com.projeto.academicplanner.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.projeto.academicplanner.R;
import com.projeto.academicplanner.helper.ConfigFirebase;

public class LoginActivity extends AppCompatActivity {

    private EditText emailLogin, senhaLogin;
    private Button botaoLogin;
    private TextView createAccount;
    private ImageView imageFaceLogin, imageGoogleLogin, imageAbout;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        startComponents();
        auth = ConfigFirebase.getReferenciaAutenticacao();

        checkLoggedInUser();

        botaoLogin.setOnClickListener( v -> {
                signIn();
        });


        imageFaceLogin.setOnClickListener( v ->  {
                toastMsgLong("Realizar o login com uma conta do FACEBOOK");
        });

        imageGoogleLogin.setOnClickListener( v ->  {
                toastMsgLong("Realizar o login com uma conta do GOOGLE");
        });

        imageAbout.setOnClickListener( v ->  {
                Intent about = new Intent(LoginActivity.this, AboutActivity.class);
                startActivity(about);
        });

        createAccount.setOnClickListener( v ->  {
                Intent createUserAccount = new Intent(LoginActivity.this, CreateAccountActivity.class);
                startActivity(createUserAccount);
        });

    }

    public void signIn() {

        String email = emailLogin.getText().toString();
        String senha = senhaLogin.getText().toString();

        if (!email.isEmpty()) {
            if (!senha.isEmpty()) {

                auth.signInWithEmailAndPassword(email, senha).addOnCompleteListener( task -> {
                    if (task.isSuccessful()){
                        toastMsgLong("Login realizado com sucesso");
                        openMainScreen();

                    } else {
                        toastMsgLong("Erro ao fazer login" + task.getException());
                    }
                });
            } else {
                toastMsgLong("Insert your password");
            }
        } else {
            toastMsgLong("Insert a valid e-mail address");
        }
    }

    private void openMainScreen(){
        startActivity(new Intent(getApplicationContext(), NavMenuActivity.class));
    }

    private void checkLoggedInUser(){
        FirebaseUser user = auth.getCurrentUser();

        if(user != null){
            openMainScreen();
        }
    }

    public void startComponents() {


        emailLogin = findViewById(R.id.userEmail);
        senhaLogin = findViewById(R.id.userPassword);
        botaoLogin = findViewById(R.id.botaoLogin);
        createAccount = findViewById(R.id.createAccount);
        imageFaceLogin = findViewById(R.id.imageFaceLogin);
        imageGoogleLogin = findViewById(R.id.imageGoogleLogin);
        imageAbout = findViewById(R.id.imageAbout);

    }

    public void toastMsgLong(String text) {

        Toast toastError = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG);
        toastError.setGravity(Gravity.CENTER, 0, 800);
        toastError.show();

    }
}
