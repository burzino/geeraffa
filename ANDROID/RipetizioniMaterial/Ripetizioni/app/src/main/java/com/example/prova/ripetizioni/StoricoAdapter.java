package com.example.prova.ripetizioni;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;
import java.util.concurrent.ExecutionException;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class StoricoAdapter extends RecyclerView.Adapter<StoricoAdapter.CardViewHolder> {

    public static class CardViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView corso,data,ora,docente,disdetta;


        CardViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cvStorico);
            corso = (TextView)itemView.findViewById(R.id.txtCorso3);
            data = (TextView)itemView.findViewById(R.id.txtData2);
            ora = (TextView)itemView.findViewById(R.id.txtOra2);
            docente = (TextView)itemView.findViewById(R.id.txtDocente2);
            disdetta = (TextView)itemView.findViewById(R.id.txtDisdetta);


        }
    }
    List<Prenotazione> prenotazioni;
    StoricoAdapter(List<Prenotazione> prenotazioni) {
        this.prenotazioni = prenotazioni;

    }
    @Override
    public int getItemCount() {
        return prenotazioni.size();
    }
    @NonNull
    @Override
    public StoricoAdapter.CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.cards_storico_layout, parent,false);
        StoricoAdapter.CardViewHolder cvh = new StoricoAdapter.CardViewHolder(v);
        return cvh;
    }

    @Override
    public void onBindViewHolder(@NonNull final StoricoAdapter.CardViewHolder holder, int position) {
        holder.corso.setText(prenotazioni.get(position).titCorso);
        holder.docente.setText(prenotazioni.get(position).docente);
        holder.data.setText(prenotazioni.get(position).dataCorso);
        String a=prenotazioni.get(position).disdetta;
        if (a.contains("1"))
            holder.disdetta.setText("prenotazione disdetta");
        else
            holder.disdetta.setText("Appuntamento sostenuto");
        holder.ora.setText("Dalle: "+prenotazioni.get(position).oraIni+" alle: "+ prenotazioni.get(position).oraFin);


    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }



}
