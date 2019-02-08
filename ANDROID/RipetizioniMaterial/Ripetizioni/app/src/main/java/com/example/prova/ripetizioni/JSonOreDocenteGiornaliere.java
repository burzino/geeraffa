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

public class JSonOreDocenteGiornaliere {
    private final String LOG_TAG = JSonOreDocenteGiornaliere.class.getSimpleName();
    private class OreDocenteTask extends AsyncTask<String, Void, String[]> {

        private String[] getOreDocenteDataFromJson(String forecastJsonStr)
                throws JSONException, ParseException {

            // These is a name of the JSON objects that need to be extracted.
            final String OWN_PRENOTAZIONI = "prenotazioni";


            final String OWM_DATAINI="dataIni";
            final String OWM_DATAFIN="dataFin";


            JSONObject forecastJson = new JSONObject(forecastJsonStr);
            JSONArray prenotazioinArray = forecastJson.getJSONArray(OWN_PRENOTAZIONI);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String[] resultStrs = new String[prenotazioinArray.length()];
            for (int i =0;i<prenotazioinArray.length();i++){
                String oraInizio,oraFine;
                JSONObject prenotazioniForecast=prenotazioinArray.getJSONObject(i);



                Date date=format.parse(prenotazioniForecast.getString(OWM_DATAINI));
                oraInizio = (String) DateFormat.format("HH",date);
                date=format.parse(prenotazioniForecast.getString(OWM_DATAFIN));
                oraFine = (String) DateFormat.format("HH",date);



                resultStrs[i]=oraInizio+"-"+oraFine;
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
                        "http://dfgghome.ddns.net:8080/Ripetizioni/Controller?toDo=elencoOreDocente&mobile=y";
                Uri builtUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                        .appendQueryParameter("docente", params[0])
                        .appendQueryParameter("giorno", params[1])
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

                return getOreDocenteDataFromJson(forecastJsonStr);
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
    public String[] doit(String docente,String giorno) throws ExecutionException, InterruptedException {
        JSonOreDocenteGiornaliere.OreDocenteTask oreDocenteTask = new JSonOreDocenteGiornaliere.OreDocenteTask();
        String[] oreDocenteGiorno = oreDocenteTask.execute(docente,giorno).get();
        return oreDocenteGiorno;

    }
}
