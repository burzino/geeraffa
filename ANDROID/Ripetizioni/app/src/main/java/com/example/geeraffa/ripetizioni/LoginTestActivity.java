package com.example.geeraffa.ripetizioni;

import android.content.Intent;
import android.content.SharedPreferences;
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

        Intent myintent = getIntent();
        String user = myintent.getStringExtra("user");
        String pwd = myintent.getStringExtra("pwd");

        final TextView txtUser = (TextView) findViewById(R.id.userName);
        final EditText txtPwd =(EditText) findViewById(R.id.password);
        Button btnLogin = (Button) findViewById(R.id.email_sign_in_button);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getApplicationContext().getSharedPreferences("LoginPref", 0);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("user", txtUser.getText().toString()); // Storing string
                editor.putString("pwd", txtPwd.getText().toString()); // Storing string
                editor.commit();
                returnToMain(txtUser.getText().toString(),txtPwd.getText().toString());
            }
        });
        if (user!=null&&pwd!=null&&user!=""&&pwd!="")
        {
            returnToMain(user,pwd);
        }
    }
    private void returnToMain(String usr,String pwd){
        JSonLogin JLogin = new JSonLogin();
        try {
            String[] logged = JLogin.doit(usr,pwd);
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
}
