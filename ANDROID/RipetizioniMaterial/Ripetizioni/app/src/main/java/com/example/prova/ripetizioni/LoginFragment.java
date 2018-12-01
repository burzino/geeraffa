package com.example.prova.ripetizioni;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.concurrent.ExecutionException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class LoginFragment extends Fragment {
    String user,pwd;
    View RootView;

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        RootView = inflater.inflate(R.layout.fragment_login, container, false);
        TextInputLayout textInputLayoutUser= (TextInputLayout) RootView.findViewById(R.id.field_user_layout);
        final TextInputEditText txtUser=new TextInputEditText(textInputLayoutUser.getContext());
        TextInputLayout textInputLayoutPwd= (TextInputLayout) RootView.findViewById(R.id.field_pwd_layout);
        final TextInputEditText txtPwd=new TextInputEditText(textInputLayoutUser.getContext());
        Button btnLogin=(Button)RootView.findViewById(R.id.material_button);
        /*JSonLogin JLogin = new JSonLogin();
        try {
            String[] logged = JLogin.doit("fabio","fabio");
            if(logged[0].equals("Y"))
            {
                txtUser.setHint("loggato");


            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return inflater.inflate(R.layout.fragment_login,container,false);
    }

}
