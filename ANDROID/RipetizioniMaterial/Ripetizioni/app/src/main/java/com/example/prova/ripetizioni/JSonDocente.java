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

public class JSonDocente {
    private final String LOG_TAG = JSonDocente.class.getSimpleName();
    private class DocentiTask extends AsyncTask<String, Void, String[]> {

        private String[] getDocentiDataFromJson(String forecastJsonStr)
                throws JSONException {

            // These is a name of the JSON objects that need to be extracted.
            final String OWM_ID = "id";
            final String OWM_DOCENTI = "docenti";
            final String OWM_NOME = "nome";
            final String OWM_COGNOME = "cognome";
            final String OWM_EMAIL="email";


            JSONObject forecastJson = new JSONObject(forecastJsonStr);
            JSONArray docentiArray = forecastJson.getJSONArray(OWM_DOCENTI);
            String[] resultStrs = new String[docentiArray.length()];
            for (int i =0;i<docentiArray.length();i++){
                String id,nome, cognome, email;
                JSONObject corsiForecast=docentiArray.getJSONObject(i);
                nome=corsiForecast.getString(OWM_NOME);
                id=corsiForecast.getString(OWM_ID);
                cognome=corsiForecast.getString(OWM_COGNOME);
                email=corsiForecast.getString(OWM_EMAIL);
                resultStrs[i]=id+"-"+cognome+"-"+nome+"-"+email;
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
                        "http://dfgghome.ddns.net:8080/Ripetizioni/Controller?toDo=elencoDocenti&mobile=y";
                Uri builtUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                        .appendQueryParameter("corso", params[0])
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

                return getDocentiDataFromJson(forecastJsonStr);
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }

            // This will only happen if there was an error getting or parsing the forecast.
            return null;
        }
    }
    public String[] doit(String corso) throws ExecutionException, InterruptedException {
        JSonDocente.DocentiTask docentiTask = new JSonDocente.DocentiTask ();
        String[] corsi = docentiTask.execute(corso).get();
        return corsi;

    }
}
