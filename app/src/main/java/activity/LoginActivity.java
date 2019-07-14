package activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.projeto.academicplanner.R;

import helper.ConfigFirebase;

public class LoginActivity extends AppCompatActivity {

    private EditText emailLogin, senhaLogin;
    private Button botaoLogin;
    private TextView createAccount;
    private ImageView imageFaceLogin, imageGoogleLogin, imageAbout;

    private FirebaseAuth authentication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        startComponents();

        checkLoggedInUser();

        botaoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
                //authentication.signInAnonymously();
                //startActivity(new Intent(getApplicationContext(), AddEditParametersActivity.class));
            }
        });


        imageFaceLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toastMsgLong("Realizar o login com uma conta do FACEBOOK");
            }
        });

        imageGoogleLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toastMsgLong("Realizar o login com uma conta do GOOGLE");
            }
        });

        imageAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent about = new Intent(LoginActivity.this, AboutActivity.class);
                startActivity(about);
            }
        });

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent createUserAccount = new Intent(LoginActivity.this, CreateAccountActivity.class);
                startActivity(createUserAccount);
            }
        });

    }

    public void signIn() {

        String email = emailLogin.getText().toString();
        String senha = senhaLogin.getText().toString();

        if (!email.isEmpty()) {
            if (!senha.isEmpty()) {

                authentication.signInWithEmailAndPassword(email, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            toastMsgLong("Login efetuado com sucesso");
                            openMainScreen();
                        } else {
                            toastMsgLong("Favor preencher os campos com credenciais válidas");
                        }
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
        startActivity(new Intent(getApplicationContext(), PlannerMainActivity.class));
    }

    private void checkLoggedInUser(){
        FirebaseUser user = authentication.getCurrentUser();

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

        authentication = ConfigFirebase.getReferenciaAutenticacao();

    }

    public void toastMsgLong(String text) {

        Toast toastError = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG);
        toastError.setGravity(Gravity.CENTER, 0, 800);
        toastError.show();

    }
}
