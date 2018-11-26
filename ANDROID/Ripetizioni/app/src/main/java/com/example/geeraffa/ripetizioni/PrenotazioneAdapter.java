package com.example.geeraffa.ripetizioni;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

public class PrenotazioneAdapter extends ArrayAdapter<Prenotazione> {
    private Context context;
    private int layout_ID;
    private List<Prenotazione> list;

    public PrenotazioneAdapter(Context context, int layout_ID, List<Prenotazione> objects)
    {
        super(context, layout_ID, objects);

        this.context = context;
        this.layout_ID = layout_ID;
        this.list = objects; //Puntatore alla lista
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return super.getView(position, convertView, parent);
    }
}
