package com.example.geeraffa.ripetizioni;

import java.util.Date;

public class Prenotazione {
    int idPrenotazione, studente, docente;
    String corso;
    Date DTInizio, DTFine;

    //togliete pure quello che non serve
    public Prenotazione(int id, int stud, int doc,String corso, Date inizio, Date fine){
        this.idPrenotazione=id;
        this.studente=stud;
        this.docente=doc;
        this.corso=corso;
        this.DTInizio=inizio;
        this.DTFine=fine;
    }
    public int getIdPrenotazione() {
        return idPrenotazione;
    }

    public Date getDTFine() {
        return DTFine;
    }

    public Date getDTInizio() {
        return DTInizio;
    }

    public int getDocente() {
        return docente;
    }

    public int getStudente() {
        return studente;
    }

    public String getCorso() {
        return corso;
    }
}
