package com.example.prova.ripetizioni;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.concurrent.ExecutionException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class LoginFragment extends Fragment  implements View.OnClickListener {

    View RootView;
    JSonLogin JLogin;
    TextInputEditText txtUser;
    TextInputEditText txtPwd;
    Button btnLogin;


    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentManager fragmentManager=getFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RootView = inflater.inflate(R.layout.fragment_login, container, false);

        btnLogin = (Button) RootView.findViewById(R.id.material_button);
        btnLogin.setOnClickListener(this);

        return RootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextInputLayout textInputLayoutUser = (TextInputLayout) RootView.findViewById(R.id.field_user_layout);
        txtUser =(TextInputEditText)RootView.findViewById(R.id.profile_field_user);
        TextInputLayout textInputLayoutPwd = (TextInputLayout) RootView.findViewById(R.id.field_pwd_layout);
        txtPwd = (TextInputEditText)RootView.findViewById(R.id.profile_field_password);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.material_button:
                try {
                    JLogin = new JSonLogin();
                    String user =txtUser.getText().toString();
                    String pwd=txtPwd.getText().toString();
                    String[] logged = JLogin.doit(user,pwd);
                    if(logged[0].equals("Y"))
                    {
                        int duration = Toast.LENGTH_LONG;

                        Toast.makeText(getActivity(), "login effettuato",duration).show();

                        SharedPreferences pref = getActivity().getSharedPreferences("LoginPref", 0);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("user", user); // Storing string
                        editor.putString("pwd", pwd);
                        editor.putString("nome", logged[1]);
                        editor.putString("cognome", logged[2]);
                        editor.putString("email", logged[3]); // Storing string
                        editor.commit();
                        ((MainActivity)getActivity()).checkLogin();

                    }

                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }

    }
}
