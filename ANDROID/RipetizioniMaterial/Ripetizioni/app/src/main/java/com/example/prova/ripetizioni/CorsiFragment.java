package com.example.prova.ripetizioni;

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

public class CorsiFragment extends Fragment implements View.OnClickListener  {
    View rootView;
    public static CorsiFragment newInstance() {
        CorsiFragment fragment = new CorsiFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView=inflater.inflate(R.layout.fragment_visualizza_corsi,container,false);
        Corso corso=new Corso();
        List<Corso>corsi=new ArrayList<Corso>();
        corsi=corso.getCorsi();

        RecyclerView rv = (RecyclerView)rootView.findViewById(R.id.rv);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        CorsiAdapter adapter= new CorsiAdapter(corsi);
        rv.setAdapter(adapter);
        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

        }
    }
}
