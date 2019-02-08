package com.example.prova.ripetizioni;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import androidx.annotation.NonNull;

public class Corso {

    String titolo;
    String descrizione;
    private List<Corso> corsi;

    public Corso(String tit, String desc){
        this.descrizione=desc;
        this.titolo=tit;

    }



    public Corso(){
        initializeData();
    }
    public String getTitolo(){
        return titolo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public void setCorsi(List<Corso> corsi) {

        this.corsi = corsi;
    }

    public List<Corso> getCorsi() {
        return corsi;
    }

    private void initializeData(){
        corsi=new ArrayList<Corso>();
        JSonCorsi JCorsi=new JSonCorsi();
        try {
            String[] corsiJson = JCorsi.doit();
            for (int i=0;i<corsiJson.length;i++){
                String[] splitCorso = corsiJson[i].split("-");
                corsi.add(new Corso(splitCorso[0],splitCorso[1]));
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @NonNull
    @Override
    public String toString() {
        return titolo;
    }
}
