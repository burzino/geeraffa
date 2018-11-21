package com.example.geeraffa.ripetizioni;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
                JLogin.doit(txtUser.getText().toString(),txtPwd.getText().toString());
            }
        });
    }
}
