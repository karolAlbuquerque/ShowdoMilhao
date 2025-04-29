package com.example.showdomilhao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.ContentValues;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "quiz.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_QUESTIONS = "questions";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_QUESTION = "question";
    public static final String COLUMN_OPTION1 = "option1";
    public static final String COLUMN_OPTION2 = "option2";
    public static final String COLUMN_OPTION3 = "option3";
    public static final String COLUMN_OPTION4 = "option4";
    public static final String COLUMN_ANSWER = "answer";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_QUESTIONS_TABLE = "CREATE TABLE " + TABLE_QUESTIONS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_QUESTION + " TEXT,"
                + COLUMN_OPTION1 + " TEXT,"
                + COLUMN_OPTION2 + " TEXT,"
                + COLUMN_OPTION3 + " TEXT,"
                + COLUMN_OPTION4 + " TEXT,"
                + COLUMN_ANSWER + " INTEGER"
                + ")";
        db.execSQL(CREATE_QUESTIONS_TABLE);

        // Inserindo perguntas iniciais
        insertInitialQuestions(db);
    }

    private void insertInitialQuestions(SQLiteDatabase db) {
        addQuestion(db, "Qual é a capital do Brasil?", "São Paulo", "Brasília", "Rio de Janeiro", "Salvador", 2);
        addQuestion(db, "Quanto é 2 + 2?", "3", "4", "5", "6", 2);
        addQuestion(db, "Qual a cor do céu em um dia claro?", "Azul", "Verde", "Vermelho", "Amarelo", 1);
        addQuestion(db, "Qual planeta é conhecido como Planeta Vermelho?", "Terra", "Marte", "Júpiter", "Vênus", 2);
        addQuestion(db, "Quem escreveu 'Dom Quixote'?", "Machado de Assis", "Cervantes", "Shakespeare", "Eça de Queiroz", 2);
        addQuestion(db, "Qual é o maior oceano da Terra?", "Atlântico", "Ártico", "Pacífico", "Índico", 3);
        addQuestion(db, "Em que ano o homem pisou na Lua pela primeira vez?", "1965", "1969", "1972", "1959", 2);
        addQuestion(db, "Qual a capital do Canadá?", "Toronto", "Vancouver", "Ottawa", "Montreal", 3);
        addQuestion(db, "Qual é a fórmula da água?", "H2O", "CO2", "NaCl", "H2SO4", 1);
        addQuestion(db, "Quem pintou a Mona Lisa?", "Van Gogh", "Leonardo da Vinci", "Picasso", "Michelangelo", 2);
        addQuestion(db, "Quantos lados tem um hexágono?", "Cinco", "Seis", "Sete", "Oito", 2);
        addQuestion(db, "Qual é o menor país do mundo?", "Mônaco", "Malta", "Vaticano", "San Marino", 3);
        addQuestion(db, "Quem descobriu o Brasil?", "Pedro Álvares Cabral", "Cristóvão Colombo", "Dom Pedro I", "Américo Vespúcio", 1);

    }

    private void addQuestion(SQLiteDatabase db, String question, String option1, String option2, String option3, String option4, int answer) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_QUESTION, question);
        values.put(COLUMN_OPTION1, option1);
        values.put(COLUMN_OPTION2, option2);
        values.put(COLUMN_OPTION3, option3);
        values.put(COLUMN_OPTION4, option4);
        values.put(COLUMN_ANSWER, answer);

        db.insert(TABLE_QUESTIONS, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTIONS);
        onCreate(db);
    }

    public List<Question> getAllQuestions() {
        List<Question> questionList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_QUESTIONS, null);

        if (cursor.moveToFirst()) {
            do {
                Question question = new Question();
                question.setId(cursor.getInt(0));
                question.setQuestion(cursor.getString(1));
                question.setOption1(cursor.getString(2));
                question.setOption2(cursor.getString(3));
                question.setOption3(cursor.getString(4));
                question.setOption4(cursor.getString(5));
                question.setAnswer(cursor.getInt(6));
                questionList.add(question);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return questionList;
    }
}
