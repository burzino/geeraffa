package com.example.prova.ripetizioni;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class PrenotazioniFragment extends Fragment {
    View rootView;


    String  id;

    public static PrenotazioniFragment newInstance() {
        PrenotazioniFragment fragment = new PrenotazioniFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView=inflater.inflate(R.layout.fragment_visualizza_prenotazioni,container,false);
        SharedPreferences pref =getActivity().getSharedPreferences("LoginPref", 0);
        SharedPreferences.Editor editor = pref.edit();
        id =pref.getString("userId", null); // getting String
        Prenotazione prenotazione=new Prenotazione(id,"prenotazioni");
        List<Prenotazione>prenotazioni=new ArrayList<Prenotazione>();
        prenotazioni=prenotazione.getPrenotazioni();
        RecyclerView rv = (RecyclerView)rootView.findViewById(R.id.rvPrenotazioni);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        PrenotazioneAdapter adapter= new PrenotazioneAdapter(prenotazioni);
        rv.setAdapter(adapter);

        return rootView;
    }
}
