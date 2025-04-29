package com.example.showdomilhao;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class QuizActivity extends AppCompatActivity {

    private TextView txtQuestion;
    private RadioGroup radioGroup;
    private RadioButton rb1, rb2, rb3, rb4;
    private Button btnAnswer;
    private List<Question> questionList;
    private int currentQuestionIndex = 0;
    private int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        txtQuestion = findViewById(R.id.txtQuestion);
        radioGroup = findViewById(R.id.radioGroup);
        rb1 = findViewById(R.id.rb1);
        rb2 = findViewById(R.id.rb2);
        rb3 = findViewById(R.id.rb3);
        rb4 = findViewById(R.id.rb4);
        btnAnswer = findViewById(R.id.btnAnswer);

        DBHelper dbHelper = new DBHelper(this);
        questionList = dbHelper.getAllQuestions();

        showQuestion();

        btnAnswer.setOnClickListener(view -> checkAnswer());
    }

    private void showQuestion() {
        if (currentQuestionIndex < questionList.size()) {
            Question q = questionList.get(currentQuestionIndex);
            txtQuestion.setText(q.getQuestion());
            rb1.setText(q.getOption1());
            rb2.setText(q.getOption2());
            rb3.setText(q.getOption3());
            rb4.setText(q.getOption4());
            radioGroup.clearCheck();
        } else {
            // Quiz finalizado
            Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
            intent.putExtra("score", score);
            startActivity(intent);
            finish();
        }
    }

    private void checkAnswer() {
        int selectedId = radioGroup.getCheckedRadioButtonId();

        if (selectedId == -1) {
            Toast.makeText(this, "Selecione uma resposta!", Toast.LENGTH_SHORT).show();
            return;
        }

        RadioButton selectedRadioButton = findViewById(selectedId);
        int answerIndex = radioGroup.indexOfChild(selectedRadioButton) + 1;

        if (answerIndex == questionList.get(currentQuestionIndex).getAnswer()) {
            Toast.makeText(this, "Correto!", Toast.LENGTH_SHORT).show();
            score++;
        } else {
            Toast.makeText(this, "Errado! Resposta correta: " +
                    getCorrectAnswerText(questionList.get(currentQuestionIndex)), Toast.LENGTH_SHORT).show();
        }

        currentQuestionIndex++;
        showQuestion();
    }

    private String getCorrectAnswerText(Question question) {
        switch (question.getAnswer()) {
            case 1: return question.getOption1();
            case 2: return question.getOption2();
            case 3: return question.getOption3();
            case 4: return question.getOption4();
            default: return "";
        }
    }
}
