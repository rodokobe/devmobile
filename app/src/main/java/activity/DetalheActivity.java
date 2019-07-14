package activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.projeto.academicplanner.R;

public class DetalheActivity extends AppCompatActivity {

    private ImageView botaoVoltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe);

        botaoVoltar = findViewById(R.id.botaoVoltar);

        botaoVoltar.setOnClickListener( v-> {
                Intent voltar = new Intent(DetalheActivity.this, PlannerMainActivity.class);
                startActivity(voltar);
        });

    }
}
