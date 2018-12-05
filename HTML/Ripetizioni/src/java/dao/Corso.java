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

    public Corso(String Titolo, String Descrizione) {
        this.Titolo = Titolo;
        this.Descrizione = Descrizione;
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
    
    
}
