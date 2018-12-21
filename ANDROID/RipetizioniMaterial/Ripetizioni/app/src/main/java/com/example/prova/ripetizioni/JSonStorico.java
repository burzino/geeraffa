package com.example.prova.ripetizioni;

import android.net.Uri;
import android.os.AsyncTask;
import android.text.format.DateFormat;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;

public class JSonStorico {
    private final String LOG_TAG = JSonStorico.class.getSimpleName();
    private class StoricoTask extends AsyncTask<String, Void, String[]> {

        private String[] getStoricoDataFromJson(String forecastJsonStr)
                throws JSONException, ParseException {

            // These is a name of the JSON objects that need to be extracted.
            final String OWN_PRENOTAZIONI = "prenotazioni";
            final String OWM_CORSO = "corso";
            final String OWM_DOCENTE = "docente";
            final String OWM_DATAINI="dataIni";
            final String OWM_DATAFIN="dataFin";
            final String OWM_ID="id";
            final String OWM_DISDETTA="disdetta";

            JSONObject forecastJson = new JSONObject(forecastJsonStr);
            JSONArray prenotazioinArray = forecastJson.getJSONArray(OWN_PRENOTAZIONI);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String[] resultStrs = new String[prenotazioinArray.length()];
            for (int i =0;i<prenotazioinArray.length();i++){
                String corso, docente,data,oraInizio,oraFine,id,disdetta;
                JSONObject prenotazioniForecast=prenotazioinArray.getJSONObject(i);
                corso=prenotazioniForecast.getString(OWM_CORSO);
                docente=prenotazioniForecast.getString(OWM_DOCENTE);

                Date date=format.parse(prenotazioniForecast.getString(OWM_DATAINI));
                String day          = (String) DateFormat.format("dd",   date);
                String monthNumber  = (String) DateFormat.format("MM",   date);
                String year         = (String) DateFormat.format("yyyy", date);
                data=day+"/"+monthNumber+"/"+year;
                oraInizio = (String) DateFormat.format("HH",date);
                date=format.parse(prenotazioniForecast.getString(OWM_DATAFIN));
                oraFine = (String) DateFormat.format("HH",date);
                id=prenotazioniForecast.getString(OWM_ID);
                disdetta=prenotazioniForecast.getString(OWM_DISDETTA);


                resultStrs[i]=id+"-"+corso+"-"+docente+"-"+data+"-"+oraInizio+"-"+oraFine+"-"+disdetta;
            }
            return resultStrs;
        }

        @Override
        protected void onPostExecute(String[] s) {
            super.onPostExecute(s);
        }


        @Override
        protected String[] doInBackground(String... params) {

            if (params.length == 0) {
                return null;
            }
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            // Will contain the raw JSON response as a string.
            String forecastJsonStr = null;
            String format = "json";
            String units = "metric";
            try {

                final String FORECAST_BASE_URL =
                        "http://192.168.1.39:8080/Ripetizioni/Controller?toDo=elencoStorico&mobile=y";
                Uri builtUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                        .appendQueryParameter("userId", params[0])
                        .build();
                URL url = new URL(builtUri.toString());
                Log.v(LOG_TAG, "Built URI " + builtUri.toString());

                // Create the request to controller, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                forecastJsonStr = buffer.toString();

                Log.v(LOG_TAG, "Forecast string: " + forecastJsonStr);

            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attemping
                // to parse it.
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }

            try {

                return getStoricoDataFromJson(forecastJsonStr);
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }

            // This will only happen if there was an error getting or parsing the forecast.
            return null;
        }
    }
    public String[] doit(String id) throws ExecutionException, InterruptedException {
        JSonStorico.StoricoTask prenotazioniTask = new JSonStorico.StoricoTask();
        String[] prenotazioni = prenotazioniTask.execute(id).get();
        return prenotazioni;

    }
}
