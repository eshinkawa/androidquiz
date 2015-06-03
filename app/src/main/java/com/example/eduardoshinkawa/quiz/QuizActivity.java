package com.example.eduardoshinkawa.quiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.*;
import android.widget.TextView;
import android.widget.Toast;
import java.util.*;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.*;

import java.util.Map;


public class QuizActivity extends Activity {

    int perguntaId;
    int acertos;
    String email;

    public class User {
        private int acertos;
        private String email;

        public User() {}

        public User(String email, int acertos) {
            this.email = email;
            this.acertos = acertos;
        }

        public long getAcertos() {
            return acertos;
        }

        public String getEmail() {
            return email;
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
    }

    @Override
    protected void onStart() {
        perguntaId = 0;
        acertos = 0;

        Intent i = getIntent();
        Bundle b = i.getBundleExtra("shared");
        email = b.getString("email");
        super.onStart();
        carregaPergunta();

    }

    public void carregaPergunta(){

        final TextView perguntaquiz = (TextView) findViewById(R.id.perguntaquiz);
        final RadioButton resposta1 = (RadioButton) faindViewById(R.id.resposta1);
        final RadioButton resposta2 = (RadioButton) findViewById(R.id.resposta2);
        final RadioButton resposta3 = (RadioButton) findViewById(R.id.resposta3);
        final RadioButton resposta4 = (RadioButton) findViewById(R.id.resposta4);
        final Button buttonNext = (Button) findViewById(R.id.nextQuiz);
        final RadioGroup radio = (RadioGroup) findViewById(R.id.radio);

        buttonNext.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {



                final Firebase resp1 = new Firebase("https://<YOUR-FIREBASE-URL>.firebaseio.com/questions/" + perguntaId + "/respostas/0/correto");
                final Firebase resp2 = new Firebase("https://<YOUR-FIREBASE-URL>.firebaseio.com/questions/" + perguntaId + "/respostas/1/correto");
                final Firebase resp3 = new Firebase("https://<YOUR-FIREBASE-URL>.firebaseio.com/questions/" + perguntaId + "/respostas/2/correto");
                final Firebase resp4 = new Firebase("https://<YOUR-FIREBASE-URL>.firebaseio.com/questions/" + perguntaId + "/respostas/3/correto");

                final Firebase arrayQuestions = new Firebase("https://<YOUR-FIREBASE-URL>.firebaseio.com/questions/");

                if (resposta1.isChecked()) {
                    resp1.addValueEventListener(new ValueEventListener() {

                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            final String alternativa = snapshot.getValue().toString();
                            if (alternativa.equals("true")) {
                                acertos++;
                                radio.clearCheck();

                            } else {
                                radio.clearCheck();
                            }


                        }

                        @Override
                        public void onCancelled(FirebaseError error) {
                        }

                    });

                }

                if (resposta2.isChecked()) {
                    resp2.addValueEventListener(new ValueEventListener() {

                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            final String alternativa = snapshot.getValue().toString();
                            if (alternativa.equals("true")) {
                                acertos++;
                                radio.clearCheck();

                            } else {
                                radio.clearCheck();
                            }


                        }

                        @Override
                        public void onCancelled(FirebaseError error) {
                        }

                    });

                }

                if (resposta3.isChecked()) {
                    resp3.addValueEventListener(new ValueEventListener() {

                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            final String alternativa = snapshot.getValue().toString();
                            if (alternativa.equals("true")) {
                                acertos++;
                                radio.clearCheck();

                            } else {
                                radio.clearCheck();
                            }


                        }

                        @Override
                        public void onCancelled(FirebaseError error) {
                        }

                    });

                }

                if (resposta4.isChecked()) {
                    resp4.addValueEventListener(new ValueEventListener() {

                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            final String alternativa = snapshot.getValue().toString();
                            if (alternativa.equals("true")) {
                                acertos++;
                                radio.clearCheck();

                            } else {
                                radio.clearCheck();
                            }


                        }

                        @Override
                        public void onCancelled(FirebaseError error) {
                        }

                    });

                }

                perguntaId++;
                if(perguntaId < 16) {
                    carregaPergunta();
                }

                else{

                    //Toast.makeText(getApplicationContext(), "Você acertou " + acertos + " perguntas",
                    //        Toast.LENGTH_LONG).show();
                    final Firebase leaderboard = new Firebase("https://<YOUR-FIREBASE-URL>.firebaseio.com/leaderboard/");

                    Intent i = new Intent(QuizActivity.this, Leaderboard.class);

                    Bundle b = new Bundle();
                    b.putInt("acertos", acertos);
                    i.putExtra("shared", b);
                    startActivity(i);

                }
            }
        });


        Firebase ref = new Firebase("https://<YOUR-FIREBASE-URL>.firebaseio.com/questions/" + perguntaId + "/pergunta");

        ref.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                perguntaquiz.setText(snapshot.getValue().toString());
            }

            @Override public void onCancelled(FirebaseError error) { }

        });

        Firebase ref1 = new Firebase("https://<YOUR-FIREBASE-URL>.firebaseio.com/questions/" + perguntaId + "/respostas/0/resposta");

        ref1.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                resposta1.setText(snapshot.getValue().toString());
            }

            @Override public void onCancelled(FirebaseError error) { }

        });

        Firebase ref2 = new Firebase("https://<YOUR-FIREBASE-URL>.firebaseio.com/questions/" + perguntaId + "/respostas/1/resposta");

        ref2.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                resposta2.setText(snapshot.getValue().toString());
            }

            @Override public void onCancelled(FirebaseError error) { }

        });

        Firebase ref3 = new Firebase("https://<YOUR-FIREBASE-URL>.firebaseio.com/questions/" + perguntaId + "/respostas/2/resposta");

        ref3.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                resposta3.setText(snapshot.getValue().toString());
            }

            @Override public void onCancelled(FirebaseError error) { }

        });

        Firebase ref4 = new Firebase("https://<YOUR-FIREBASE-URL>.firebaseio.com/questions/" + perguntaId + "/respostas/3/resposta");

        ref4.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                resposta4.setText(snapshot.getValue().toString());
            }

            @Override public void onCancelled(FirebaseError error) { }

        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_quiz, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
