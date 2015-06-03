package com.example.eduardoshinkawa.quiz;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.firebase.client.*;
import com.firebase.client.realtime.util.StringListReader;

import android.content.Intent;

import org.w3c.dom.Text;


public class MainActivity extends Activity {

    private Firebase mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Firebase.setAndroidContext(this);
    }

    @Override
    protected void onStart(){

        super.onStart();

        final EditText username = (EditText) findViewById(R.id.editText);
        final EditText password = (EditText) findViewById(R.id.editText2);
        final TextView signup = (TextView) findViewById(R.id.signup);
        Button buttonLogin = (Button) findViewById(R.id.buttonLogin);





        signup.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(v.getContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mRef = new Firebase("https://<YOUR-FIREBASE-URL>.firebaseio.com/");

                Firebase.AuthResultHandler authResultHandler = new Firebase.AuthResultHandler() {
                    @Override
                    public void onAuthenticated(AuthData authData) {
                        // Authenticated successfully with payload authData

                        mRef = new Firebase("https://<YOUR-FIREBASE-URL>.firebaseio.com/usuario/" + authData.getUid().toString() + "/alias");

                        mRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot snapshot) {
                                System.out.println("The read failed: " + snapshot.getValue().toString());
                                final String alias = snapshot.getValue().toString();
                                Propriedades.alias = alias;
                            }
                            @Override
                            public void onCancelled(FirebaseError firebaseError) {
                                System.out.println("The read failed: " + firebaseError.getMessage());
                            }
                        });

                        //Toast.makeText(getApplicationContext(), "Bem-vindo " + Propriedades.alias + "!",
                        //        Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(MainActivity.this, QuizActivity.class);

                        Bundle b = new Bundle();
                        b.putString("email", username.getText().toString());
                        i.putExtra("shared", b);
                        startActivity(i);
                    }

                    @Override
                    public void onAuthenticationError(FirebaseError firebaseError) {
                        // Authenticated failed with error firebaseError
                        Toast.makeText(getApplicationContext(), "Usuário e/ou Senha inválidos!",
                                Toast.LENGTH_LONG).show();
                    }
                };
                    String user = username.getText().toString();
                    String pass = password.getText().toString();
                    // Or with an email/password combination
                    mRef.authWithPassword(user, pass, authResultHandler);


            }
        });

 /*     Button mButtonSunny = (Button) findViewById(R.id.buttonSunny);
        Button mButtonFoggy = (Button) findViewById(R.id.buttonFoggy);
        final TextView mTextCondition = (TextView) findViewById(R.id.textViewCondition);

        mRef = new Firebase("https://brilliant-fire-3725.firebaseio.com/condition");

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String newCondition = (String) dataSnapshot.getValue();
                mTextCondition.setText(newCondition);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        mButtonFoggy.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                mRef.setValue("foggy");

            }
        });

        mButtonSunny.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                mRef.setValue("sunny");

            }
        });
*/

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
