package com.example.prova.ripetizioni;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Prenotazione {
    String docente, titCorso,dataCorso,oraIni,oraFin,id, disdetta;
    private List<Prenotazione> prenotazioni;
    String email;

    public Prenotazione(String id,String titCorso,String docente,  String dataCorso, String oraIni, String oraFin,String disdetta) {
        this.docente = docente;
        this.titCorso = titCorso;
        this.dataCorso = dataCorso;
        this.oraIni = oraIni;
        this.oraFin = oraFin;
        this.id=id;
        this.disdetta=disdetta;
    }
    public Prenotazione(String email){
        this.email=email;
        initializeData();
    }

    public List<Prenotazione> getPrenotazioni() {
        return prenotazioni;
    }

    public String getDocente() {
        return docente;
    }

    public void setDocente(String docente) {
        this.docente = docente;
    }

    public String getTitCorso() {
        return titCorso;
    }

    public void setTitCorso(String titCorso) {
        this.titCorso = titCorso;
    }

    public String getDataCorso() {
        return dataCorso;
    }

    public void setDataCorso(String dataCorso) {
        this.dataCorso = dataCorso;
    }

    public String getOraIni() {
        return oraIni;
    }

    public void setOraIni(String oraIni) {
        this.oraIni = oraIni;
    }

    public String getOraFin() {
        return oraFin;
    }

    public void setOraFin(String oraFin) {
        this.oraFin = oraFin;
    }

    private void initializeData(){
        prenotazioni=new ArrayList<Prenotazione>();
        JSonPrenotazioni JPrenotazioni=new JSonPrenotazioni();
        try {
            String[] corsiJson = JPrenotazioni.doit(email);
            if (corsiJson!=null) {
                for (int i = 0; i < corsiJson.length; i++) {
                    String[] splitPrenotazione = corsiJson[i].split("-");
                    prenotazioni.add(new Prenotazione(splitPrenotazione[0], splitPrenotazione[1], splitPrenotazione[2], splitPrenotazione[3], splitPrenotazione[4], splitPrenotazione[5], splitPrenotazione[6]));
                }
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public String deletePrenotazione(String codice)
    {
        String eliminato=null;
        JSonEliminaPrenotazione JElimina= new JSonEliminaPrenotazione();
        try{
            eliminato = JElimina.doit(codice);



        }  catch (InterruptedException e) {
        e.printStackTrace();
    } catch (ExecutionException e) {
        e.printStackTrace();
    }
        return eliminato;
    }


}
