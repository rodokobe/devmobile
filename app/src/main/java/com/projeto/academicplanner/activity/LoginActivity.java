package com.projeto.academicplanner.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.projeto.academicplanner.R;
import com.projeto.academicplanner.helper.ConfigFirebase;

public class LoginActivity extends AppCompatActivity {

    private EditText emailLogin, senhaLogin;
    private Button botaoLogin;
    private SignInButton googleLoginButton;
    private TextView createAccount;
    private ImageView imageAbout;

    private FirebaseAuth auth;
    private FirebaseUser user;

    //GOOGLE SIGNIN PARAMS
    private static final String TAG = "GoogleActivity";
    private static final int RC_SIGN_IN = 9001;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        startComponents();

        googleLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();

            }
        });

        auth = ConfigFirebase.getReferenciaAutenticacao();

        checkLoggedInUser();

        botaoLogin.setOnClickListener( v -> {
                signInNormal();
        });

        imageAbout.setOnClickListener( v ->  {
                Intent about = new Intent(LoginActivity.this, AboutActivity.class);
                startActivity(about);
        });

        createAccount.setOnClickListener( v ->  {
                Intent createUserAccount = new Intent(LoginActivity.this, CreateAccountActivity.class);
                startActivity(createUserAccount);
        });


        /**
         * GOOGLE LOGIN CONFIGURATION BEGIN
         */


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();


        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        mAuth = FirebaseAuth.getInstance();

    }

    /**
     * SignIn via email and password
     */
    public void signInNormal() {

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

    private void openMainScreen(){
        startActivity(new Intent(getApplicationContext(), NavMainActivity.class));
    }

    private void checkLoggedInUser(){
        user = auth.getCurrentUser();

        if(user != null){
            openMainScreen();
        }
    }

    public void startComponents() {

        emailLogin = findViewById(R.id.userEmail);
        senhaLogin = findViewById(R.id.userPassword);
        botaoLogin = findViewById(R.id.botaoLogin);
        createAccount = findViewById(R.id.createAccount);
        googleLoginButton = findViewById(R.id.googleLogin);
        imageAbout = findViewById(R.id.imageAbout);

    }

    public void toastMsgLong(String text) {

        Toast toastError = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG);
        toastError.setGravity(Gravity.CENTER, 0, 800);
        toastError.show();

    }

    /**
     * SignIn via Google
     */

    // [START on_start_check_user]
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        user = mAuth.getCurrentUser();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            toastMsgLong("Sign In with Google Success");
                            user = mAuth.getCurrentUser();
                            startActivity(new Intent(getApplicationContext(), NavMainActivity.class));
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            toastMsgLong("Authentication Failed.");
                        }
                    }
                });
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }



    //GOOGLE LOGIN CONFIGURATION END


}
