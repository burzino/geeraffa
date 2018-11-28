/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

/**
 *
 * @author GEERAFFA
 */
public class Prenotazione {
    public int ID_Prenotazione;
    public int Studente;
    public int Docente;
    public String Corso;
    public String dataInizio;
    public String dataFine;
    public int Disdetta;

    public Prenotazione(int ID_Prenotazione, int Studente, int Docente, String Corso, String dataInizio, String dataFine, int Disdetta) {
        this.ID_Prenotazione = ID_Prenotazione;
        this.Studente = Studente;
        this.Docente = Docente;
        this.Corso = Corso;
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
        this.Disdetta = Disdetta;
    }

    public int getID_Prenotazione() {
        return ID_Prenotazione;
    }

    public void setID_Prenotazione(int ID_Prenotazione) {
        this.ID_Prenotazione = ID_Prenotazione;
    }

    public int getStudente() {
        return Studente;
    }

    public void setStudente(int Studente) {
        this.Studente = Studente;
    }

    public int getDocente() {
        return Docente;
    }

    public void setDocente(int Docente) {
        this.Docente = Docente;
    }

    public String getCorso() {
        return Corso;
    }

    public void setCorso(String Corso) {
        this.Corso = Corso;
    }

    public String getDataInizio() {
        return dataInizio;
    }

    public void setDataInizio(String dataInizio) {
        this.dataInizio = dataInizio;
    }

    public String getDataFine() {
        return dataFine;
    }

    public void setDataFine(String dataFine) {
        this.dataFine = dataFine;
    }

    public int getDisdetta() {
        return Disdetta;
    }

    public void setDisdetta(int Disdetta) {
        this.Disdetta = Disdetta;
    }
}
