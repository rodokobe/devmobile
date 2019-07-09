package activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.projeto.academicplanner.R;

public class PlannerMainActivity extends AppCompatActivity {

    private Button botaoDetalhe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planner_main);

        configuraNavigationView();

   /*     botaoDetalhe = findViewById(R.id.botaoDetalhe);

        botaoDetalhe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detalhe = new Intent(PlannerMainActivity.this, DetalheActivity.class);
                startActivity(detalhe);
            }
        }); */

    }


    public void configuraNavigationView() {

        BottomNavigationViewEx topNavigation = findViewById(R.id.topNavigation);
        BottomNavigationViewEx bottomNavigation = findViewById(R.id.bottomNavigation);
        topNavigation.enableShiftingMode(true);
        bottomNavigation.setTextVisibility(true);


    }
}
