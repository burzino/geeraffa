package com.example.prova.ripetizioni;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class CorsiAdapter extends RecyclerView.Adapter<CorsiAdapter.CardViewHolder> {

    public static class CardViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView titolo;
        TextView desc;

        CardViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            titolo = (TextView)itemView.findViewById(R.id.txtCorso);
            desc = (TextView)itemView.findViewById(R.id.txtDescrizione);

        }


    }

    List<Corso> corsi;
    CorsiAdapter(List<Corso> corsi){
        this.corsi=corsi;

    }

    @Override
    public int getItemCount() {
        return corsi.size();
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.cards_corsi_layout, parent,false);
        CardViewHolder cvh = new CardViewHolder(v);
        return cvh;
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        holder.titolo.setText(corsi.get(position).titolo);
        holder.desc.setText(corsi.get(position).descrizione);
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }




}
