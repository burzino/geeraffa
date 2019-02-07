/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

/**
 *
 * @author luca
 */
public class Corso {
    public String Titolo;
    public String Descrizione;
    public String path;
    public int Attivo;

    public Corso(String Titolo, String Descrizione, String path, int Attivo) {
        this.Titolo = Titolo;
        this.Descrizione = Descrizione;
        this.path = path;
        this.Attivo = Attivo;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTitolo() {
        return Titolo;
    }

    public void setTitolo(String Titolo) {
        this.Titolo = Titolo;
    }

    public String getDescrizione() {
        return Descrizione;
    }

    public void setDescrizione(String Descrizione) {
        this.Descrizione = Descrizione;
    }

    public int getAttivo() {
        return Attivo;
    }

    public void setAttivo(int Attivo) {
        this.Attivo = Attivo;
    }
    
    
}
