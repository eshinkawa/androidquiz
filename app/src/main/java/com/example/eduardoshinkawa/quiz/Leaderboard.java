package com.example.eduardoshinkawa.quiz;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.firebase.client.*;


public class Leaderboard extends Activity {





    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        Intent i = getIntent();
        Bundle b = i.getBundleExtra("shared");
        int acertos = b.getInt("acertos");

        String numerocertas = Integer.valueOf(acertos).toString();


        setContentView(R.layout.activity_leaderboard);

        final TextView certas = (TextView) findViewById(R.id.resultado);
        final TextView condview = (TextView) findViewById(R.id.resultView);
        final TextView apelido = (TextView) findViewById(R.id.apelido);
        final TextView refazer = (TextView) findViewById(R.id.refazerquiz);

        apelido.setText(Propriedades.alias);
        certas.setText(numerocertas);
        refazer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        if(acertos < 8){
            condview.setText("Precisa estudar mais cabeção!!!");
        }
        else{
            condview.setText("Caramba, você é muito nerd!!!");
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_leaderboard, menu);
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
