package activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.projeto.academicplanner.R;

import helper.ConfigFirebase;
import model.UserProfile;

public class CreateAccountActivity extends AppCompatActivity {

    private FirebaseAuth auth;

    private EditText userFirstName;
    private EditText userLastName;
    private EditText userEmail;
    private EditText userPassword;
    private Button buttonCreate;
    private String urlImagemSelecionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        startComponents();

        buttonCreate.setOnClickListener( v -> {
                createAccount();
        });

    }

    private void createAccount() {

        String firstname = userFirstName.getText().toString();
        String lastname = userLastName.getText().toString();
        String email = userEmail.getText().toString();
        String senha = userPassword.getText().toString();

        if (!firstname.isEmpty()) {
            if (!lastname.isEmpty()) {
                if (!email.isEmpty()) {
                    if (!senha.isEmpty()) {

                        auth.createUserWithEmailAndPassword(
                                email,
                                senha
                        ).addOnCompleteListener( task ->  {

                                if (task.isSuccessful()) {

                                    UserProfile user = new UserProfile();
                                    user.setFirstname(firstname);
                                    user.setLastname(lastname);
                                    user.setEmail(email);
                                    user.setUrlProfile("https://firebasestorage.googleapis.com/v0/b/academicplanner2019.appspot.com/o/images%2Fdefault%2Fperfil.jpg?alt=media&token=646cf32b-bd9f-4eb3-bda4-30bdc5d6b022");
                                    user.setIdUser(ConfigFirebase.getUserId());
                                    user.save();

                                    toastMessageLong("User created successfully");

                                    openMainScreen();

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

                                    toastMessageLong("Error found: " + errorException);
                                }
                        });
                    } else { toastMessageLong("Enter a valid password"); }

                } else { toastMessageLong("Enter a valid e-mail"); }

            } else { toastMessageLong("Enter a Last Name"); }

        } else { toastMessageLong("Enter a First Name"); }

    }

    private void openMainScreen(){
        startActivity(new Intent(getApplicationContext(), AddEditParametersActivity.class));
    }

    public void startComponents() {

        userFirstName = findViewById(R.id.userFirstName);
        userLastName = findViewById(R.id.userLastName);
        userEmail = findViewById(R.id.userEmail);
        userPassword = findViewById(R.id.userPassword);
        buttonCreate = findViewById(R.id.buttonCreate);

        auth = ConfigFirebase.getReferenciaAutenticacao();

    }

    private void toastMessageShort(String msg) {
        Toast.makeText(getApplicationContext(), msg,
                Toast.LENGTH_SHORT).show();
    }

    private void toastMessageLong(String msg) {
        Toast.makeText(getApplicationContext(), msg,
                Toast.LENGTH_LONG).show();
    }

}
