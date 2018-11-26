package com.example.geeraffa.ripetizioni;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class CorsiAdapter  extends ArrayAdapter<Corso> {

    private Context context;
    private int layout_ID;
    private List<Corso> list;

    public CorsiAdapter(Context context, int layout_ID, List<Corso> objects)
    {
        super(context, layout_ID, objects);

        this.context = context;
        this.layout_ID = layout_ID;
        this.list = objects; //Puntatore alla lista
    }


    @Override
    public View getView(int position,View convertView, ViewGroup parent) {
        //Riferimento all'object RecordLayout
        View v = null;
        if(convertView == null)
        {
            //Inflater : oggetto che permette di recuperare un puntatore al layout partendo dal suo id
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(this.layout_ID,parent,false);
        }
        else
            v = convertView;

        Button btn_Titolo = (Button) v.findViewById(R.id.btnTitolo);
        //TextView lbl_Descrizione = (TextView) v.findViewById(R.id.lblDescrizione);

        Corso corso = list.get(position);

        btn_Titolo.setText(corso.titolo);

        //lbl_Descrizione.setText(corso.descrizione);
         return v;
    }

}
