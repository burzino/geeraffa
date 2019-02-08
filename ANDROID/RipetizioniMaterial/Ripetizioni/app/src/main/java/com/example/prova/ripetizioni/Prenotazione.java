package com.example.prova.ripetizioni;

import android.text.format.DateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Prenotazione {
    String docente, titCorso,dataCorso,oraIni,oraFin,id, disdetta;
    private List<Prenotazione> prenotazioni;
    String userId;
    String docenteScelto,giorno;
    String[] oreJson;

    public Prenotazione(String id,String titCorso,String docente,  String dataCorso, String oraIni, String oraFin,String disdetta) {
        this.docente = docente;
        this.titCorso = titCorso;
        this.dataCorso = dataCorso;
        this.oraIni = oraIni;
        this.oraFin = oraFin;
        this.id=id;
        this.disdetta=disdetta;

    }
    public Prenotazione(String userId,String operazione) throws ParseException {

        if (operazione=="prenotazioni") {
            this.userId=userId;
            initializeData();
        }
        else if (operazione=="storico") {
            this.userId=userId;
            storico();
        }else{
            //userId=docente, operazione=giorno
            this.docenteScelto=userId;
            this.giorno=operazione;
            prenotazioniDocenteGiornaliere();
        }

    }

    public String[] getOreJson() {
        return oreJson;
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
            String[] corsiJson = JPrenotazioni.doit(userId);
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

    private void prenotazioniDocenteGiornaliere() throws ParseException {
        prenotazioni=new ArrayList<Prenotazione>();
        JSonOreDocenteGiornaliere JOre= new JSonOreDocenteGiornaliere();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        Date date=format.parse(giorno);
        String day          = (String) DateFormat.format("dd",   date);
        String monthNumber  = (String) DateFormat.format("MM",   date);
        String year         = (String) DateFormat.format("yyyy", date);
        giorno=year+"/"+monthNumber+"/"+day;

        try {
            this.oreJson=JOre.doit(docenteScelto,giorno);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void storico()
    {
        prenotazioni=new ArrayList<Prenotazione>();
        JSonStorico JStorico=new JSonStorico();
        try {
            String[] corsiJson = JStorico.doit(userId);
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
             JElimina.doit(codice);



        }  catch (InterruptedException e) {
        e.printStackTrace();
    } catch (ExecutionException e) {
        e.printStackTrace();
    }
        return eliminato;
    }


}
