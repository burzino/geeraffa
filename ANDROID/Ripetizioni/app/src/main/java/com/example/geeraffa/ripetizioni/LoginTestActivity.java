package com.example.geeraffa.ripetizioni;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

public class LoginTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_test);
        final TextView txtUser = (TextView) findViewById(R.id.userName);
        final EditText txtPwd =(EditText) findViewById(R.id.password);
        Button btnLogin = (Button) findViewById(R.id.email_sign_in_button);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSonLogin JLogin = new JSonLogin();
                try {
                    String[] logged = JLogin.doit(txtUser.getText().toString(),txtPwd.getText().toString());
                    if(logged[0].equals("Y"))
                    {

                        Intent myIntent = new Intent(LoginTestActivity.this, Main2Activity.class);
                        //Optional parameters
                        myIntent.putExtra("logged", logged[0]);
                        myIntent.putExtra("nome", logged[1]);
                        myIntent.putExtra("cognome", logged[2]);
                        myIntent.putExtra("email", logged[3]);

                        LoginTestActivity.this.startActivity(myIntent);
                    }
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
