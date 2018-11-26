package com.example.geeraffa.ripetizioni;
import android.content.Intent;
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

public class JSonLogin {
    private final String LOG_TAG = JSonLogin.class.getSimpleName();
    public class LoginTask extends AsyncTask<String, String, String> {



        private String getLoginDataFromJson(String forecastJsonStr)
                throws JSONException {

            // These is a name of the JSON objects that need to be extracted.
            final String OWM_LOGGED = "logged";
            final String OWM_NOME = "nome";
            final String OWM_COGNOME = "cognome";
            final String OWM_EMAIL = "email";

            JSONObject forecastJson = new JSONObject(forecastJsonStr);

            return forecastJson.getString(OWM_LOGGED)+ "-" +forecastJson.getString(OWM_NOME)+ "-"+forecastJson.getString(OWM_COGNOME)+ "-"+forecastJson.getString(OWM_EMAIL);


        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
        @Override
        protected String doInBackground(String... strings) {

            // If there's no zip code, there's nothing to look up.  Verify size of params.
            if (strings.length == 0) {
                return null;
            }

            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            // Will contain the raw JSON response as a string.
            String forecastJsonStr = null;
            String format = "json";
            String units = "metric";
            try {

                final String FORECAST_BASE_URL =
                        "http://192.168.1.27:8080/Ripetizioni/Controller?toDo=login&mobile=y";
                Uri builtUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                        .appendQueryParameter("username", strings[0])
                        .appendQueryParameter("pwd", strings[1])
                        .build();

                URL url = new URL(builtUri.toString());
                Log.v(LOG_TAG, "Built URI " + builtUri.toString());

                // Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
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

                return getLoginDataFromJson(forecastJsonStr);
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }

            // This will only happen if there was an error getting or parsing the forecast.
            return null;
        }
    }
    public  String[] doit(String s1,String s2) throws ExecutionException, InterruptedException {
        LoginTask loginTask = new LoginTask();
        String[] logged = loginTask.execute(s1,s2).get().split("-");
        return logged;
    }
}
