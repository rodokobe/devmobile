package activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.projeto.academicplanner.R;

import helper.ConfigFirebase;
import model.User;

public class CreateAccountActivity extends AppCompatActivity {

    private FirebaseAuth authentication;

    private EditText userFirstName;
    private EditText userLastName;
    private EditText userEmail;
    private EditText userPassword;
    private Button buttonCreate;
    private User userCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        startComponents();

        buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userCreate = new User();
                userCreate.setUserFirstName(userFirstName.getText().toString());
                userCreate.setUserLastName(userLastName.getText().toString());
                userCreate.setUserEmail(userEmail.getText().toString());
                userCreate.setUserPassword(userPassword.getText().toString());

                createAccount();
            }

        });

    }

    private void createAccount() {

        String firstNameCheck = userFirstName.getText().toString();
        String lastNameCheck = userLastName.getText().toString();
        String emailCheck = userEmail.getText().toString();
        String passWordCheck = userPassword.getText().toString();

        if (!firstNameCheck.isEmpty()) {
            if (!lastNameCheck.isEmpty()) {
                if (!emailCheck.isEmpty()) {
                    if (!passWordCheck.isEmpty()) {

                        authentication.createUserWithEmailAndPassword(
                                userCreate.getUserEmail(),
                                userCreate.getUserPassword()
                        ).addOnCompleteListener(CreateAccountActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful()) {

                                    FirebaseUser userFirebase = task.getResult().getUser();
                                    userCreate.setIdUser(userFirebase.getUid());
                                    userCreate.saveUserData();
                                    toastMsg("User created successfully");

                                    Intent back = new Intent(CreateAccountActivity.this, LoginActivity.class);
                                    startActivity(back);

                                } else {

                                    String errorException = "";

                                    try {
                                        throw task.getException();
                                    } catch (FirebaseAuthWeakPasswordException e) {
                                        errorException = "Enter a Stronger password";
                                    } catch (FirebaseAuthInvalidCredentialsException e) {
                                        errorException = "Enter a valid e-mail address";
                                    } catch (FirebaseAuthUserCollisionException e) {
                                        errorException = "Account already registered";
                                    } catch (Exception e) {
                                        errorException = "Error on create user: " + e.getMessage();
                                        e.printStackTrace();
                                    }

                                    Toast.makeText(CreateAccountActivity.this, "Error found: " + errorException, Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    } else { toastMsg("Enter a valid password"); }

                } else { toastMsg("Enter a valid e-mail"); }

            } else { toastMsg("Enter a Last Name"); }

        } else { toastMsg("Enter a First Name"); }

    }

    public void startComponents() {

        userFirstName = findViewById(R.id.userFirstName);
        userLastName = findViewById(R.id.userLastName);
        userEmail = findViewById(R.id.userEmail);
        userPassword = findViewById(R.id.userPassword);
        buttonCreate = findViewById(R.id.buttonCreate);

        authentication = ConfigFirebase.getReferenciaAutenticacao();

    }

    public void toastMsg(String text) {

        Toast toastError = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG);
        toastError.setGravity(Gravity.CENTER, 0, 800);
        toastError.show();

    }

}
