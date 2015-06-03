package com.example.eduardoshinkawa.quiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.*;
import com.firebase.client.*;


public class SignUpActivity extends Activity {

    public void changeIntent(View v){
        Intent intent = new Intent(v.getContext(), MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }

    @Override
    protected void onStart() {

        super.onStart();
        final EditText username = (EditText) findViewById(R.id.emailField);
        final EditText password = (EditText) findViewById(R.id.passlField);
        final EditText usuario = (EditText) findViewById(R.id.userField);
        Button buttonLogin = (Button) findViewById(R.id.signupButton);
        final TextView loginScreen = (TextView) findViewById(R.id.loginScreen);

        loginScreen.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){


                Intent intent = new Intent(v.getContext(), MainActivity.class);
                intent.putExtra("name", username.getText().toString());
                startActivity(intent);
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String user = username.getText().toString();
                final String pass = password.getText().toString();
                final String usu = usuario.getText().toString();
                final Firebase ref = new Firebase("https://<YOUR-FIREBASE-URL>.firebaseio.com/");
                Propriedades.username = username.getText().toString();
                ref.authWithPassword(user, pass,
                        new Firebase.AuthResultHandler() {

                            @Override
                            public void onAuthenticated(AuthData authData) {
                                // Authentication just completed successfully :)
                                Map<String, String> map = new HashMap<String, String>();
                                map.put("provider", authData.getProvider());
                                if(authData.getProviderData().containsKey("id")) {
                                    map.put("provider_id", authData.getProviderData().get("id").toString());
                                }
                                if(authData.getProviderData().containsKey("displayName")) {
                                    map.put("displayName", authData.getProviderData().get("displayName").toString());
                                }

                            }

                            @Override
                            public void onAuthenticationError(FirebaseError error) {
                                // Something went wrong :(
                            }
                        });


                ref.createUser(user, pass, new Firebase.ValueResultHandler<Map<String, Object>>() {

                    @Override
                    public void onSuccess(Map<String, Object> result) {
                        ref.child("usuario").child(result.get("uid").toString()).child("email").setValue(user);
                        ref.child("usuario").child(result.get("uid").toString()).child("alias").setValue(usu);
                        Toast.makeText(getApplicationContext(), "User registered!!!",
                                Toast.LENGTH_LONG).show();

                        Intent abc = new Intent(SignUpActivity.this, Leaderboard.class);

                        Bundle b = new Bundle();
                        b.putString("usuario", usu);
                        abc.putExtra("shared", b);

                        Intent login = new Intent(SignUpActivity.this, MainActivity.class);
                        startActivity(login);


                    }

                    @Override
                    public void onError(FirebaseError firebaseError) {
                        // there was an error
                        System.out.println("Deu ruim irmão");
                    }

                });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign_up, menu);
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
