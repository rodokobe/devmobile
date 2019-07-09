package activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.projeto.academicplanner.R;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                voltarTelaLogin();
            }
        }, 5000);

    }

    private void voltarTelaLogin() {
        Intent login = new Intent(AboutActivity.this, LoginActivity.class);
        startActivity(login);
        finish();
    }
}
