package com.example.geeraffa.ripetizioni;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class InviaRichiestaHttp extends AsyncTask<String,Void,String>
{
    View v;
    Context context;
    public InviaRichiestaHttp(View v,Context context)
    {
        this.v = v;
        this.context = context;
    }

    @Override
    protected String doInBackground(String... args) {
        String metodo =  args[0];
        String risorsa =  args[1];
        String parametro = args[2];
        String ris = "";
        String riga = "";
        URLConnection conn = null;
        String url = "localhost:8080/Controller";
        url += risorsa;
        try
        {
            if(metodo == "get")
            {
                url +=parametro;
                //invio richiesta
                URL _url = new URL(url);
                conn = _url.openConnection();
            }
            else if(metodo == "post")
            {
                URL _url = new URL(url);
                conn = _url.openConnection();
                //Aggiunta Parametri Post
                String data = URLEncoder.encode("user","UTF-8")
                        + "=" + URLEncoder.encode(parametro,"UTF-8");
                conn.setDoOutput(true);
                //Apre stream di output e scrive i parametri post
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write(data);
                wr.flush();
                wr.close();
            }
            //Lettura Risposta
            BufferedReader reader = new BufferedReader(new InputStreamReader((conn.getInputStream())));


            while((ris = reader.readLine()) != null)
                riga += "\n" + ris;

            reader.close();
        }
        catch (Exception ex)
        {
            Toast.makeText(this.context,ex.getMessage(),Toast.LENGTH_LONG).show();
        }

        return riga;
    }






}
