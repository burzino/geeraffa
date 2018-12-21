package com.example.prova.ripetizioni;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
        String id =pref.getString("userId", null); // getting String
        editor = pref.edit();
        txtUtente.setText(pref.getString("nome", null)+" "+pref.getString("cognome", null));
        txtEmail.setText(pref.getString("email", null));


        Prenotazione storico=new Prenotazione(id,"storico");
        List<Prenotazione> prenotazioni=new ArrayList<Prenotazione>();
        prenotazioni=storico.getPrenotazioni();
        RecyclerView rv = (RecyclerView)rootView.findViewById(R.id.rvStorico);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        StoricoAdapter adapter= new StoricoAdapter(prenotazioni);
        rv.setAdapter(adapter);

    }
}
