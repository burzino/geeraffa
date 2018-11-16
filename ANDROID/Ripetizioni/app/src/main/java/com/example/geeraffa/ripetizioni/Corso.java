package com.example.geeraffa.ripetizioni;

public class Corso {
    String titolo;
    String descrizione;
    public Corso(String tit, String desc){
        this.descrizione=desc;
        this.titolo=tit;
    }
    public String getTitolo(){
        return titolo;
    }

    public String getDescrizione() {
        return descrizione;
    }
}
