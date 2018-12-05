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
public class Docente {
    public int ID_Docente;
    public String Cognome;
    public String Nome;
    public String Email;
    public int Attivo;

    public Docente(int ID_Docente, String Cognome, String Nome, String Email, int Attivo) {
        this.ID_Docente = ID_Docente;
        this.Cognome = Cognome;
        this.Nome = Nome;
        this.Email = Email;
        this.Attivo = Attivo;
    }

    public int getID_Docente() {
        return ID_Docente;
    }

    public void setID_Docente(int ID_Docente) {
        this.ID_Docente = ID_Docente;
    }

    public String getCognome() {
        return Cognome;
    }

    public void setCognome(String Cognome) {
        this.Cognome = Cognome;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String Nome) {
        this.Nome = Nome;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public int getAttivo() {
        return Attivo;
    }

    public void setAttivo(int Attivo) {
        this.Attivo = Attivo;
    }
    
    
}
