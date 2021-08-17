package com.moutamid.themotivitaionhub;

import static com.moutamid.themotivitaionhub.Utils.*;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class QuotesActivity extends AppCompatActivity {
    private static final String TAG = "QuotesActivity";
    private Context context = QuotesActivity.this;

    private TextView quoteTextView, quoteAuthorTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotes);
        Log.d(TAG, "onCreate: ");
        quoteTextView = findViewById(R.id.quoteTextView);
        quoteAuthorTextView = findViewById(R.id.quoteAuthorTextview);

        findViewById(R.id.settingsBtnQuotes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, SettingsActivity.class));

            }
        });

        // EVERY DAY A NEW REQUEST US TRIGGERED
        // HERE CHECKING IF TODAY IS A NEW DAY THEN A A NEW QUOTE WILL GET DOWNLOADED INTO MEMORY
        if (!Utils.getString("date").equals(getDate())) {
            Log.d(TAG, "onCreate:         if (!Utils.getString(\"date\").equals(getDate())) {\n");
            GetQuote getQuote = new GetQuote();
            getQuote.execute();
            return;
        }

        quoteTextView.setText(Utils.getString("quote"));
        quoteAuthorTextView.setText(Utils.getString("author"));
        Log.d(TAG, "onCreate: finished");
    }

// THIS IS THE CLASS WHICH IS USED TO DOWNLOAD A QUOTE FROM AN API
    private class GetQuote extends AsyncTask<String, Void, String> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d(TAG, "onPreExecute: ");
            progressDialog = new ProgressDialog(context);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            HttpHandler sh = new HttpHandler();
            Log.d(TAG, "doInBackground: ");
//            String url = "https://www.youtube.com/oembed?format=json&url=" + id;//https://www.youtube.com/watch?v=" + id;
            String url = "https://zenquotes.io/api/today";

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);
            Log.d(TAG, "\n\n\ndoInBackground: "+jsonStr+"\n\n\n");
            return jsonStr;

//            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            String jsonStr = s;

            String quoteStr = "null";
            String authorStr = "null";

            Log.d(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
//                    JSONObject o = new JSONObject(jsonStr);
                    JSONArray o = new JSONArray(jsonStr);

//                    quoteStr = o.getString(0);
//                    authorStr = o.getString(1);

                    JSONObject object = new JSONObject(o.getString(0));

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toast(o.length() + "");
                        }
                    });

                    quoteStr = object.getString("q");
                    authorStr = object.getString("a");

                    Log.d(TAG, "onPostExecute: quote:"+quoteStr);
                    Log.d(TAG, "onPostExecute: author:"+authorStr);

                    // STORING THE QUOTE AND AUTHOR NAME IN MEMORY
                    store("date", getDate());
                    store("quote", quoteStr);
                    store("author", authorStr);

                    quoteTextView.setText(quoteStr);
                    quoteAuthorTextView.setText(authorStr);

//                    thumbnailUrl = o.getString("thumbnail_url");

//                    return quoteStr;

                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.dismiss();
                        }
                    });

                    e.printStackTrace();
                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                    }
                });

            }
            progressDialog.dismiss();
            Log.d(TAG, "onPostExecute: finished");
        }
    }

    // THIS IS THE CLASS WHICH IS USED TO CONNECT TO THE API WEBSITE
    private static class HttpHandler {

//        private String TAG = "HttpHandler";

        public HttpHandler() {
        }

        public String makeServiceCall(String reqUrl) {
            String response = null;
            try {
                Log.d(TAG, "makeServiceCall: ");
                URL url = new URL(reqUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                // read the response
                InputStream in = new BufferedInputStream(conn.getInputStream());

                response = convertStreamToString(in);
                Log.d(TAG, "makeServiceCall: response " + response);
            } catch (MalformedURLException e) {
                Log.e(TAG, "MalformedURLException: " + e.getMessage());
            } catch (ProtocolException e) {
                Log.e(TAG, "ProtocolException: " + e.getMessage());
            } catch (IOException e) {
                Log.e(TAG, "IOException: " + e.getMessage());
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
            return response;
        }

        private String convertStreamToString(InputStream is) {
            Log.d(TAG, "convertStreamToString: ");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();

            String line;
            try {

                while ((line = reader.readLine()) != null) {

                    sb.append(line).append('\n');

                }

            } catch (IOException e) {

                e.printStackTrace();

            } finally {

                try {

                    is.close();

                } catch (IOException e) {

                    e.printStackTrace();
                }
            }

            return sb.toString();
        }
    }

}