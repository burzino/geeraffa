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

public class PrenotazioneAdapter  extends RecyclerView.Adapter<PrenotazioneAdapter.CardViewHolder> {

    public static class CardViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView corso,data,ora,docente,codice;
        Button btnDisdici;


        CardViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cvPrenotazioni);
            corso = (TextView)itemView.findViewById(R.id.txtCorso2);
            data = (TextView)itemView.findViewById(R.id.txtData);
            ora = (TextView)itemView.findViewById(R.id.txtOra);
            docente = (TextView)itemView.findViewById(R.id.txtDocente);
            codice = (TextView)itemView.findViewById(R.id.txtCodice);
            btnDisdici=(Button)itemView.findViewById(R.id.btnDisdici);

        }


    }
    List<Prenotazione> prenotazioni;
    PrenotazioneAdapter(List<Prenotazione> prenotazioni){
        this.prenotazioni=prenotazioni;

    }
    @Override
    public int getItemCount() {
        return prenotazioni.size();
    }
    @NonNull
    @Override
    public PrenotazioneAdapter.CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.cards_prenotazioni_layout, parent,false);
        PrenotazioneAdapter.CardViewHolder cvh = new PrenotazioneAdapter.CardViewHolder(v);
        return cvh;
    }

    @Override
    public void onBindViewHolder(@NonNull final PrenotazioneAdapter.CardViewHolder holder, int position) {
        holder.corso.setText(prenotazioni.get(position).titCorso);
        holder.docente.setText(prenotazioni.get(position).docente);
        holder.data.setText(prenotazioni.get(position).dataCorso);
        holder.codice.setText(prenotazioni.get(position).id);
        holder.ora.setText("Dalle: "+prenotazioni.get(position).oraIni+" alle: "+ prenotazioni.get(position).oraFin);
        holder.btnDisdici.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSonEliminaPrenotazione JElimina = new JSonEliminaPrenotazione();
                try {
                    String a= JElimina.doit(holder.codice.getText().toString());
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
