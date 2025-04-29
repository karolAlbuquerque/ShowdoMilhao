package com.example.showdomilhao;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    private TextView txtScore, txtMessage;
    private Button btnRestart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        txtScore = findViewById(R.id.txtScore);
        txtMessage = findViewById(R.id.txtMessage);
        btnRestart = findViewById(R.id.btnRestart);

        int score = getIntent().getIntExtra("score", 0);

        txtScore.setText("Sua pontuação: " + score);

        if (score >= 2) {
            txtMessage.setText("Parabéns!");
        } else {
            txtMessage.setText("Tente novamente!");
        }

        btnRestart.setOnClickListener(view -> {
            Intent intent = new Intent(ResultActivity.this, WelcomeActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
