package com.example.prova.ripetizioni;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Docente {
    String id,cognome, nome,email;
    private List<Docente> docenti;

    public Docente(String id,String cognome, String nome, String email) {
        this.cognome = cognome;
        this.nome = nome;
        this.email = email;
        this.id=id;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Docente> getDocenti() {
        return docenti;
    }

    public void setDocenti(List<Docente> docenti) {
        this.docenti = docenti;
    }

    public Docente(String corso){
        docenti=new ArrayList<Docente>();
        JSonDocente JDoc = new JSonDocente();
        try {
            String[] docentiJSon = JDoc.doit(corso);
            for (int i=0;i<docentiJSon.length;i++){
                String[] splitDocente = docentiJSon[i].split("-");
                docenti.add(new Docente(splitDocente[0],splitDocente[1],splitDocente[2],splitDocente[3]));
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return  id + ' '+ cognome + ' ' +nome ;
    }
}
