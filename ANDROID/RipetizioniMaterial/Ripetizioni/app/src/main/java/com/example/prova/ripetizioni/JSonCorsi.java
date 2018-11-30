package com.example.prova.ripetizioni;

import android.net.Uri;
import android.os.AsyncTask;
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
import java.util.concurrent.ExecutionException;

public class JSonCorsi {
    private final String LOG_TAG = JSonCorsi.class.getSimpleName();
    private class CorsiTask extends AsyncTask<String, Void, String[]> {

        private String[] getCorsiDataFromJson(String forecastJsonStr)
                throws JSONException {

            // These is a name of the JSON objects that need to be extracted.
            final String OWM_TITOLO = "titolo";
            final String OWM_DESCRIZIONE = "descrizione";
            final String OWM_CORSI="corsi";
            JSONObject forecastJson = new JSONObject(forecastJsonStr);
            JSONArray corsiArray = forecastJson.getJSONArray(OWM_CORSI);
            String[] resultStrs = new String[corsiArray.length()];
            for (int i =0;i<corsiArray.length();i++){
                String titolo, descrizione;
                JSONObject corsiForecast=corsiArray.getJSONObject(i);
                titolo=corsiForecast.getString(OWM_TITOLO);
                descrizione=corsiForecast.getString(OWM_DESCRIZIONE);
                resultStrs[i]=titolo+"-"+descrizione;
            }
            return resultStrs;
        }

        @Override
        protected void onPostExecute(String[] s) {
            super.onPostExecute(s);
        }


        @Override
        protected String[] doInBackground(String... params) {

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            // Will contain the raw JSON response as a string.
            String forecastJsonStr = null;
            String format = "json";
            String units = "metric";
            try {

                final String FORECAST_BASE_URL =
                        "http://192.168.1.37:8080/Ripetizioni/Controller?toDo=elencoCorsi&mobile=y";
                Uri builtUri = Uri.parse(FORECAST_BASE_URL).buildUpon().build();
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

                return getCorsiDataFromJson(forecastJsonStr);
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }

            // This will only happen if there was an error getting or parsing the forecast.
            return null;
        }
    }
    public String[] doit() throws ExecutionException, InterruptedException {
        JSonCorsi.CorsiTask corsiTask = new JSonCorsi.CorsiTask();
        String[] corsi = corsiTask.execute().get();
        return corsi;

    }
}
