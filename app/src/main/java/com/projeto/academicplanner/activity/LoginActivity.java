package com.projeto.academicplanner.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.projeto.academicplanner.R;
import com.projeto.academicplanner.helper.ConfigFirebase;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private EditText emailLogin, senhaLogin;
    private Button botaoLogin;
    private TextView createAccount;
    private ImageView imageFaceLogin, imageGoogleLogin, imageAbout;

    private FirebaseAuth auth;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


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
            signInGoogle();

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
                        toastMsgLong("Erro ao fazer login: " + task.getException().getMessage());
                    }
                });
            } else {
                toastMsgLong("Insert your password");
            }
        } else {
            toastMsgLong("Insert a valid e-mail address");
        }
    }

    public void signInGoogle() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            firebaseLogin(result);
        }

    }

    private void firebaseLogin(GoogleSignInResult result) {

        if (result.isSuccess()) {

            GoogleSignInAccount account = result.getSignInAccount();
            AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(),null);
            startActivity(new Intent(getApplicationContext(), NavMainActivity.class));
            toastMsgLong(account.getDisplayName() + " connected!");

        } else {
            toastMsgLong("Conection Failed by " + result);
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

        toastMsgLong("Conection Failed! " + connectionResult);

    }



    private void openMainScreen(){
        startActivity(new Intent(getApplicationContext(), NavMainActivity.class));
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
