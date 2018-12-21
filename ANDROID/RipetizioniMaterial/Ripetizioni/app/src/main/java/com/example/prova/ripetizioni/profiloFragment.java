package com.example.prova.ripetizioni;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class profiloFragment extends Fragment {
    TextView txtUtente;
    TextView txtEmail;
    View rootView;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    public static profiloFragment newInstance() {
        profiloFragment fragment = new profiloFragment();
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView=inflater.inflate(R.layout.fragment_profilo,container,false);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txtUtente=(TextView)rootView.findViewById(R.id.txtUtente);
        txtEmail=(TextView)rootView.findViewById(R.id.txtEmail);
        pref = getActivity().getSharedPreferences("LoginPref", 0);
        editor = pref.edit();
        txtUtente.setText(pref.getString("nome", null)+" "+pref.getString("cognome", null));
        txtEmail.setText(pref.getString("email", null));

    }
}
